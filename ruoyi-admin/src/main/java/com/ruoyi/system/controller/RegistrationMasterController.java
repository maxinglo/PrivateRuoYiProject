package com.ruoyi.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.system.domain.RegistrationDetail;
import com.ruoyi.system.service.IRegistrationDetailService;
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
import com.ruoyi.system.domain.RegistrationMaster;
import com.ruoyi.system.service.IRegistrationMasterService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报名主表Controller
 * 
 * @author Maxing
 * @date 2023-05-06
 */
@RestController
@RequestMapping("/system/RegistrationMaster")
public class RegistrationMasterController extends BaseController
{
    @Autowired
    private IRegistrationMasterService registrationMasterService;

    @Autowired
    private IRegistrationDetailService registrationDetailService;

    /**
     * 查询报名主表列表
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationMaster:list')")
    @GetMapping("/list")
    public TableDataInfo list(RegistrationMaster registrationMaster)
    {
        startPage();
        List<RegistrationMaster> list = registrationMasterService.selectRegistrationMasterList(registrationMaster);
        return getDataTable(list);
    }

    /**
     * 导出报名主表列表
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationMaster:export')")
    @Log(title = "报名主表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RegistrationMaster registrationMaster)
    {
        List<RegistrationMaster> list = registrationMasterService.selectRegistrationMasterList(registrationMaster);
        ExcelUtil<RegistrationMaster> util = new ExcelUtil<RegistrationMaster>(RegistrationMaster.class);
        util.exportExcel(response, list, "报名主表数据");
    }

    /**
     * 获取报名主表详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationMaster:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(registrationMasterService.selectRegistrationMasterById(id));
    }

    /**
     * 新增报名主表
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationMaster:add')")
    @Log(title = "报名主表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RegistrationMaster registrationMaster)
    {
        return toAjax(registrationMasterService.insertRegistrationMaster(registrationMaster));
    }

    /**
     * 修改报名主表
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationMaster:edit')")
    @Log(title = "报名主表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RegistrationMaster registrationMaster)
    {
        return toAjax(registrationMasterService.updateRegistrationMaster(registrationMaster));
    }

    /**
     * 删除报名主表
     */
    @PreAuthorize("@ss.hasPermi('system:RegistrationMaster:remove')")
    @Log(title = "报名主表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(registrationMasterService.deleteRegistrationMasterByIds(ids));
    }

    @Anonymous
    @PostMapping("/exportExcel")
    public AjaxResult exportExcel(RegistrationMaster registrationMaster)
    {
        List<RegistrationMaster> list = registrationMasterService.selectRegistrationMasterList(registrationMaster);
        HashMap<RegistrationMaster, List> map = new HashMap<>();
        for (RegistrationMaster registrationMaster1 : list){
            map.put(registrationMaster1,registrationDetailService.selectRegistrationDetailByMasterId(registrationMaster1.getId()));
        }
        for (RegistrationMaster registrationMaster1 : map.keySet()) {
            System.out.println(registrationMaster1);
            System.out.println(map.get(registrationMaster1));
        }
        return AjaxResult.success("查询成功");
    }
}
