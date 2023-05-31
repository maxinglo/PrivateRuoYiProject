package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.RegistrationDetailMapper;
import com.ruoyi.system.domain.RegistrationDetail;
import com.ruoyi.system.service.IRegistrationDetailService;

/**
 * 报名子表Service业务层处理
 * 
 * @author Maxing
 * @date 2023-05-26
 */
@Service
public class RegistrationDetailServiceImpl implements IRegistrationDetailService 
{
    @Autowired
    private RegistrationDetailMapper registrationDetailMapper;

    /**
     * 查询报名子表
     * 
     * @param id 报名子表主键
     * @return 报名子表
     */
    @Override
    public RegistrationDetail selectRegistrationDetailById(Long id)
    {
        return registrationDetailMapper.selectRegistrationDetailById(id);
    }

    /**
     * 查询报名子表列表
     * 
     * @param registrationDetail 报名子表
     * @return 报名子表
     */
    @Override
    public List<RegistrationDetail> selectRegistrationDetailList(RegistrationDetail registrationDetail)
    {
        return registrationDetailMapper.selectRegistrationDetailList(registrationDetail);
    }

    /**
     * 新增报名子表
     * 
     * @param registrationDetail 报名子表
     * @return 结果
     */
    @Override
    public int insertRegistrationDetail(RegistrationDetail registrationDetail)
    {
            return registrationDetailMapper.insertRegistrationDetail(registrationDetail);
    }

    /**
     * 修改报名子表
     * 
     * @param registrationDetail 报名子表
     * @return 结果
     */
    @Override
    public int updateRegistrationDetail(RegistrationDetail registrationDetail)
    {
        return registrationDetailMapper.updateRegistrationDetail(registrationDetail);
    }

    /**
     * 批量删除报名子表
     * 
     * @param ids 需要删除的报名子表主键
     * @return 结果
     */
    @Override
    public int deleteRegistrationDetailByIds(Long[] ids)
    {
        return registrationDetailMapper.deleteRegistrationDetailByIds(ids);
    }

    /**
     * 删除报名子表信息
     * 
     * @param id 报名子表主键
     * @return 结果
     */
    @Override
    public int deleteRegistrationDetailById(Long id)
    {
        return registrationDetailMapper.deleteRegistrationDetailById(id);
    }
    /**
     * 依据主表ID查询子表
     *
     * @param masterId 需要查询的主表ID
     * @return 结果
     */
    @Override
    public List<RegistrationDetail> selectRegistrationDetailByMasterId(Long masterId)
    {
        return registrationDetailMapper.selectRegistrationDetailByMasterId(masterId);
    }
}
