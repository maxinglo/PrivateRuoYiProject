package com.ruoyi.web.controller;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.web.domain.RegistrationDetailForWeb;
import com.ruoyi.web.service.IRegistrationDetailForWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报名子表接口Controller
 *
 * @author Maxing
 * @date 2023-05-27
 */
@Anonymous
@RestController
@RequestMapping("/web/RegistrationDetailsForWeb")
public class RegistrationDetailForWebController extends BaseController {
    @Autowired
    private IRegistrationDetailForWebService registrationDetailForWebService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询报名子表接口列表
     */
    @PreAuthorize("@ss.hasPermi('web:RegistrationDetailsForWeb:list')")
    @GetMapping("/list")
    public TableDataInfo list(RegistrationDetailForWeb registrationDetailForWeb) {
        startPage();
        List<RegistrationDetailForWeb> list = registrationDetailForWebService.selectRegistrationDetailForWebList(registrationDetailForWeb);
        return getDataTable(list);
    }

    /**
     * 导出报名子表接口列表
     */
    @PreAuthorize("@ss.hasPermi('web:RegistrationDetailsForWeb:export')")
    @Log(title = "报名子表接口", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegistrationDetailForWeb registrationDetailForWeb) {
        List<RegistrationDetailForWeb> list = registrationDetailForWebService.selectRegistrationDetailForWebList(registrationDetailForWeb);
        ExcelUtil<RegistrationDetailForWeb> util = new ExcelUtil<RegistrationDetailForWeb>(RegistrationDetailForWeb.class);
        util.exportExcel(response, list, "报名子表接口数据");
    }

    /**
     * 获取报名子表接口详细信息
     */
    @Anonymous
    @GetMapping(value = "/getInfo")
    public AjaxResult getInfo(@RequestParam("Email") String Email, @RequestParam("VerificationCode") String VerificationCode) {
        String cacheKey = "verification_code:" + Email;
        if (redisCache.hasKey(cacheKey)) {
            String verificationCode = redisCache.getCacheObject(cacheKey);
            if (verificationCode.equalsIgnoreCase(VerificationCode)) {
                redisCache.deleteObject(cacheKey);
                return success(registrationDetailForWebService.selectRegistrationDetailForWebByEmail(Email));
            } else {
                return AjaxResult.error("验证码错误");
            }
        } else {
            return AjaxResult.error("验证码已过期");
        }
    }

    /**
     * 新增报名子表接口
     */
    @Anonymous
    @PostMapping
    public AjaxResult add(@RequestBody RegistrationDetailForWeb registrationDetailForWeb) {
        String cacheKey = "verification_code:" + registrationDetailForWeb.getEmail();
        if (redisCache.hasKey(cacheKey)) {
            String verificationCode = redisCache.getCacheObject(cacheKey);
            if (verificationCode.equalsIgnoreCase(registrationDetailForWeb.getVerificationCode())) {
                try {
                    int result = registrationDetailForWebService.insertRegistrationDetailForWeb(registrationDetailForWeb);
                    if (result > 0) {
                        redisCache.deleteObject(cacheKey);
                        return toAjax(result);
                    }
                } catch (Exception e) {
                    redisCache.deleteObject(cacheKey);
                    if (e.getMessage().contains("Duplicate entry") && (e.getMessage().contains("email")||e.getMessage().contains("student_id"))) {
                        // 邮箱重复
                        // 根据邮箱更新报名信息
                        return toAjax(registrationDetailForWebService.updateRegistrationDetailForWebByEmail(registrationDetailForWeb));
                    }
                }

            } else {
                return AjaxResult.error("验证码错误");
            }
        } else {
            return AjaxResult.error("验证码已过期");
        }
        return AjaxResult.error("未知错误");
    }

    /**
     * 修改报名子表接口
     */
    @PreAuthorize("@ss.hasPermi('web:RegistrationDetailsForWeb:edit')")
    @Log(title = "报名子表接口", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegistrationDetailForWeb registrationDetailForWeb) {
        return toAjax(registrationDetailForWebService.updateRegistrationDetailForWeb(registrationDetailForWeb));
    }

    /**
     * 删除报名子表接口
     */
    @PreAuthorize("@ss.hasPermi('web:RegistrationDetailsForWeb:remove')")
    @Log(title = "报名子表接口", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(registrationDetailForWebService.deleteRegistrationDetailForWebByIds(ids));
    }
}
