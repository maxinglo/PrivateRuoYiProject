package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.RegistrationMaster;
import com.ruoyi.system.domain.RegistrationDetail;

/**
 * 报名主表Mapper接口
 * 
 * @author Maxing
 * @date 2023-05-06
 */
public interface RegistrationMasterMapper 
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
     * 删除报名主表
     * 
     * @param id 报名主表主键
     * @return 结果
     */
    public int deleteRegistrationMasterById(Long id);

    /**
     * 批量删除报名主表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRegistrationMasterByIds(Long[] ids);

    /**
     * 批量删除报名子表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRegistrationDetailByIds(Long[] ids);
    
    /**
     * 批量新增报名子表
     * 
     * @param registrationDetailList 报名子表列表
     * @return 结果
     */
    public int batchRegistrationDetail(List<RegistrationDetail> registrationDetailList);
    

    /**
     * 通过报名主表主键删除报名子表信息
     * 
     * @param id 报名主表ID
     * @return 结果
     */
    public int deleteRegistrationDetailById(Long id);
}
