package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.RegistrationMaster;

/**
 * 报名主表Service接口
 * 
 * @author Maxing
 * @date 2023-05-06
 */
public interface IRegistrationMasterService 
{
    /**
     * 查询报名主表
     * 
     * @param id 报名主表主键
     * @return 报名主表
     */
    public RegistrationMaster selectRegistrationMasterById(Long id);

    /**
     * 查询报名主表列表
     * 
     * @param registrationMaster 报名主表
     * @return 报名主表集合
     */
    public List<RegistrationMaster> selectRegistrationMasterList(RegistrationMaster registrationMaster);

    /**
     * 新增报名主表
     * 
     * @param registrationMaster 报名主表
     * @return 结果
     */
    public int insertRegistrationMaster(RegistrationMaster registrationMaster);

    /**
     * 修改报名主表
     * 
     * @param registrationMaster 报名主表
     * @return 结果
     */
    public int updateRegistrationMaster(RegistrationMaster registrationMaster);

    /**
     * 批量删除报名主表
     * 
     * @param ids 需要删除的报名主表主键集合
     * @return 结果
     */
    public int deleteRegistrationMasterByIds(Long[] ids);

    /**
     * 删除报名主表信息
     * 
     * @param id 报名主表主键
     * @return 结果
     */
    public int deleteRegistrationMasterById(Long id);
}
