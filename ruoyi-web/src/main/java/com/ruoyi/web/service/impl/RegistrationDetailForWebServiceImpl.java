package com.ruoyi.web.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.web.mapper.RegistrationDetailForWebMapper;
import com.ruoyi.web.domain.RegistrationDetailForWeb;
import com.ruoyi.web.service.IRegistrationDetailForWebService;

/**
 * 报名子表接口Service业务层处理
 * 
 * @author Maxing
 * @date 2023-05-27
 */
@Service
public class RegistrationDetailForWebServiceImpl implements IRegistrationDetailForWebService 
{
    @Autowired
    private RegistrationDetailForWebMapper registrationDetailForWebMapper;

    /**
     * 查询报名子表接口
     * 
     * @param id 报名子表接口主键
     * @return 报名子表接口
     */
    @Override
    public RegistrationDetailForWeb selectRegistrationDetailForWebById(Long id)
    {
        return registrationDetailForWebMapper.selectRegistrationDetailForWebById(id);
    }

    /**
     * 查询报名子表接口列表
     * 
     * @param registrationDetailForWeb 报名子表接口
     * @return 报名子表接口
     */
    @Override
    public List<RegistrationDetailForWeb> selectRegistrationDetailForWebList(RegistrationDetailForWeb registrationDetailForWeb)
    {
        return registrationDetailForWebMapper.selectRegistrationDetailForWebList(registrationDetailForWeb);
    }

    /**
     * 新增报名子表接口
     * 
     * @param registrationDetailForWeb 报名子表接口
     * @return 结果
     */
    @Override
    public int insertRegistrationDetailForWeb(RegistrationDetailForWeb registrationDetailForWeb)
    {
        return registrationDetailForWebMapper.insertRegistrationDetailForWeb(registrationDetailForWeb);
    }

    /**
     * 修改报名子表接口
     * 
     * @param registrationDetailForWeb 报名子表接口
     * @return 结果
     */
    @Override
    public int updateRegistrationDetailForWeb(RegistrationDetailForWeb registrationDetailForWeb)
    {
        return registrationDetailForWebMapper.updateRegistrationDetailForWeb(registrationDetailForWeb);
    }

    /**
     * 批量删除报名子表接口
     * 
     * @param ids 需要删除的报名子表接口主键
     * @return 结果
     */
    @Override
    public int deleteRegistrationDetailForWebByIds(Long[] ids)
    {
        return registrationDetailForWebMapper.deleteRegistrationDetailForWebByIds(ids);
    }

    /**
     * 删除报名子表接口信息
     * 
     * @param id 报名子表接口主键
     * @return 结果
     */
    @Override
    public int deleteRegistrationDetailForWebById(Long id)
    {
        return registrationDetailForWebMapper.deleteRegistrationDetailForWebById(id);
    }
}
