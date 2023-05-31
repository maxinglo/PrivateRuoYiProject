package com.ruoyi.system.controller;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.RegistrationDetail;
import com.ruoyi.system.service.IRegistrationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 报名子表Controller
 *
 * @author Maxing
 * @date 2023-05-26
 */
@Anonymous
@RestController
@RequestMapping("/system/RegistrationDetails")
public class RegistrationDetailController extends BaseController {
    @Autowired
    private IRegistrationDetailService registrationDetailService;

    /**
     * 查询报名子表列表
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationDetails:list')")
    @GetMapping("/list")
    public TableDataInfo list(RegistrationDetail registrationDetail) {
        startPage();
        List<RegistrationDetail> list = registrationDetailService.selectRegistrationDetailList(registrationDetail);
        return getDataTable(list);
    }

    /**
     * 导出报名子表列表
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationDetails:export')")
    @Log(title = "报名子表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegistrationDetail registrationDetail) {
        List<RegistrationDetail> list = registrationDetailService.selectRegistrationDetailList(registrationDetail);
        ExcelUtil<RegistrationDetail> util = new ExcelUtil<RegistrationDetail>(RegistrationDetail.class);
        util.exportExcel(response, list, "报名子表数据");
    }

    /**
     * 获取报名子表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationDetails:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(registrationDetailService.selectRegistrationDetailById(id));
    }

    /**
     * 新增报名子表
     */
    @Log(title = "报名子表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RegistrationDetail registrationDetail) {
        try {
            // 尝试插入报名信息
            return toAjax(registrationDetailService.insertRegistrationDetail(registrationDetail));
        } catch (Exception e) {
            // 插入失败
            // 判断是否是因为邮箱重复
            if (e.getMessage().contains("Duplicate entry") && (e.getMessage().contains("email")||e.getMessage().contains("student_id"))) {
                // 邮箱重复
                // 根据邮箱更新报名信息
                return toAjax(registrationDetailService.updateRegistrationDetail(registrationDetail));
            } else {
                // 其他原因
                return AjaxResult.error("此学工号或邮箱已报名");
            }
        }
    }


    /**
     * 修改报名子表
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationDetails:edit')")
    @Log(title = "报名子表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegistrationDetail registrationDetail) {
        return toAjax(registrationDetailService.updateRegistrationDetail(registrationDetail));
    }

    /**
     * 删除报名子表
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationDetails:remove')")
    @Log(title = "报名子表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(registrationDetailService.deleteRegistrationDetailByIds(ids));
    }
}
