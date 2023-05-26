package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.RegistrationDetail;

/**
 * 报名子表Mapper接口
 * 
 * @author Maxing
 * @date 2023-05-26
 */
public interface RegistrationDetailMapper 
{
    /**
     * 查询报名子表
     * 
     * @param id 报名子表主键
     * @return 报名子表
     */
    public RegistrationDetail selectRegistrationDetailById(Long id);

    /**
     * 查询报名子表列表
     * 
     * @param registrationDetail 报名子表
     * @return 报名子表集合
     */
    public List<RegistrationDetail> selectRegistrationDetailList(RegistrationDetail registrationDetail);

    /**
     * 新增报名子表
     * 
     * @param registrationDetail 报名子表
     * @return 结果
     */
    public int insertRegistrationDetail(RegistrationDetail registrationDetail);

    /**
     * 修改报名子表
     * 
     * @param registrationDetail 报名子表
     * @return 结果
     */
    public int updateRegistrationDetail(RegistrationDetail registrationDetail);

    /**
     * 删除报名子表
     * 
     * @param id 报名子表主键
     * @return 结果
     */
    public int deleteRegistrationDetailById(Long id);

    /**
     * 批量删除报名子表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRegistrationDetailByIds(Long[] ids);
}
