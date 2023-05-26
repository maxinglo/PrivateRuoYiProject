package com.ruoyi.web.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.web.domain.RegistrationDetailForWeb;
import com.ruoyi.web.service.IRegistrationDetailForWebService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报名子表接口Controller
 *
 * @author Maxing
 * @date 2023-05-27
 */
@Anonymous
@RestController
@RequestMapping("/web/RegistrationDetailsForWeb")
public class RegistrationDetailForWebController extends BaseController
{
    @Autowired
    private IRegistrationDetailForWebService registrationDetailForWebService;

    @Autowired
    private RedisCache redisCache;
    /**
     * 查询报名子表接口列表
     */
    @PreAuthorize("@ss.hasPermi('web:RegistrationDetailsForWeb:list')")
    @GetMapping("/list")
    public TableDataInfo list(RegistrationDetailForWeb registrationDetailForWeb)
    {
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
    public void export(HttpServletResponse response, RegistrationDetailForWeb registrationDetailForWeb)
    {
        List<RegistrationDetailForWeb> list = registrationDetailForWebService.selectRegistrationDetailForWebList(registrationDetailForWeb);
        ExcelUtil<RegistrationDetailForWeb> util = new ExcelUtil<RegistrationDetailForWeb>(RegistrationDetailForWeb.class);
        util.exportExcel(response, list, "报名子表接口数据");
    }

    /**
     * 获取报名子表接口详细信息
     */
    @PreAuthorize("@ss.hasPermi('web:RegistrationDetailsForWeb:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(registrationDetailForWebService.selectRegistrationDetailForWebById(id));
    }

    /**
     * 新增报名子表接口
     */
    @Anonymous
    @PostMapping
    public AjaxResult add(@RequestBody RegistrationDetailForWeb registrationDetailForWeb)
    {
        String cacheKey = "verification_code:" + registrationDetailForWeb.getEmail();
        try{
            String verificationCode = redisCache.getCacheObject(cacheKey);
            if(verificationCode.equalsIgnoreCase(registrationDetailForWeb.getVerificationCode())){
                redisCache.deleteObject(cacheKey);
                return toAjax(registrationDetailForWebService.insertRegistrationDetailForWeb(registrationDetailForWeb));
            }else {
                return AjaxResult.error("验证码错误");
            }
        }catch (Exception e){
            return AjaxResult.error("验证码已过期");
        }
    }

    /**
     * 修改报名子表接口
     */
    @PreAuthorize("@ss.hasPermi('web:RegistrationDetailsForWeb:edit')")
    @Log(title = "报名子表接口", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegistrationDetailForWeb registrationDetailForWeb)
    {
        return toAjax(registrationDetailForWebService.updateRegistrationDetailForWeb(registrationDetailForWeb));
    }

    /**
     * 删除报名子表接口
     */
    @PreAuthorize("@ss.hasPermi('web:RegistrationDetailsForWeb:remove')")
    @Log(title = "报名子表接口", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(registrationDetailForWebService.deleteRegistrationDetailForWebByIds(ids));
    }
}
