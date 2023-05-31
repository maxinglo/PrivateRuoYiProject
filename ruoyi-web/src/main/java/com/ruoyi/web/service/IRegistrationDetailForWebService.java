package com.ruoyi.web.service;

import java.util.List;
import com.ruoyi.web.domain.RegistrationDetailForWeb;

/**
 * 报名子表接口Service接口
 * 
 * @author Maxing
 * @date 2023-05-27
 */
public interface IRegistrationDetailForWebService 
{
    /**
     * 查询报名子表接口
     * 
     * @param id 报名子表接口主键
     * @return 报名子表接口
     */
    public RegistrationDetailForWeb selectRegistrationDetailForWebById(Long id);

    /**
     * 查询报名子表接口
     *
     * @param email 报名子表邮箱
     * @return 报名子表接口
     */
    public RegistrationDetailForWeb selectRegistrationDetailForWebByEmail(String email);

    /**
     * 查询报名子表接口列表
     * 
     * @param registrationDetailForWeb 报名子表接口
     * @return 报名子表接口集合
     */
    public List<RegistrationDetailForWeb> selectRegistrationDetailForWebList(RegistrationDetailForWeb registrationDetailForWeb);

    /**
     * 新增报名子表接口
     * 
     * @param registrationDetailForWeb 报名子表接口
     * @return 结果
     */
    public int insertRegistrationDetailForWeb(RegistrationDetailForWeb registrationDetailForWeb);

    /**
     * 修改报名子表接口
     * 
     * @param registrationDetailForWeb 报名子表接口
     * @return 结果
     */
    public int updateRegistrationDetailForWeb(RegistrationDetailForWeb registrationDetailForWeb);

    /**
     * 批量删除报名子表接口
     * 
     * @param email 需要更新的报名子表接口邮箱
     * @return 结果
     */

    public int updateRegistrationDetailForWebByEmail(RegistrationDetailForWeb registrationDetailForWeb);
    public int deleteRegistrationDetailForWebByIds(Long[] ids);

    /**
     * 删除报名子表接口信息
     * 
     * @param id 报名子表接口主键
     * @return 结果
     */
    public int deleteRegistrationDetailForWebById(Long id);

    /**
     * 删除报名子表接口信息
     *
     * @param email 报名子表接口邮箱
     * @return 结果
     */
    public int deleteRegistrationDetailForWebByEmail(String email);
}
