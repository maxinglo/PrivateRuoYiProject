package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.RegistrationDetail;
import com.ruoyi.system.mapper.RegistrationMasterMapper;
import com.ruoyi.system.domain.RegistrationMaster;
import com.ruoyi.system.service.IRegistrationMasterService;

/**
 * 报名主表Service业务层处理
 * 
 * @author Maxing
 * @date 2023-05-06
 */
@Service
public class RegistrationMasterServiceImpl implements IRegistrationMasterService 
{
    @Autowired
    private RegistrationMasterMapper registrationMasterMapper;

    /**
     * 查询报名主表
     * 
     * @param id 报名主表主键
     * @return 报名主表
     */
    @Override
    public RegistrationMaster selectRegistrationMasterById(Long id)
    {
        return registrationMasterMapper.selectRegistrationMasterById(id);
    }

    /**
     * 查询报名主表列表
     * 
     * @param registrationMaster 报名主表
     * @return 报名主表
     */
    @Override
    public List<RegistrationMaster> selectRegistrationMasterList(RegistrationMaster registrationMaster)
    {
        return registrationMasterMapper.selectRegistrationMasterList(registrationMaster);
    }

    /**
     * 新增报名主表
     * 
     * @param registrationMaster 报名主表
     * @return 结果
     */
    @Transactional
    @Override
    public int insertRegistrationMaster(RegistrationMaster registrationMaster)
    {
        int rows = registrationMasterMapper.insertRegistrationMaster(registrationMaster);
        insertRegistrationDetail(registrationMaster);
        return rows;
    }

    /**
     * 修改报名主表
     * 
     * @param registrationMaster 报名主表
     * @return 结果
     */
    @Transactional
    @Override
    public int updateRegistrationMaster(RegistrationMaster registrationMaster)
    {
        registrationMasterMapper.deleteRegistrationDetailById(registrationMaster.getId());
        insertRegistrationDetail(registrationMaster);
        return registrationMasterMapper.updateRegistrationMaster(registrationMaster);
    }

    /**
     * 批量删除报名主表
     * 
     * @param ids 需要删除的报名主表主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteRegistrationMasterByIds(Long[] ids)
    {
        registrationMasterMapper.deleteRegistrationDetailByIds(ids);
        return registrationMasterMapper.deleteRegistrationMasterByIds(ids);
    }

    /**
     * 删除报名主表信息
     * 
     * @param id 报名主表主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteRegistrationMasterById(Long id)
    {
        registrationMasterMapper.deleteRegistrationDetailById(id);
        return registrationMasterMapper.deleteRegistrationMasterById(id);
    }

    /**
     * 新增报名子表信息
     * 
     * @param registrationMaster 报名主表对象
     */
    public void insertRegistrationDetail(RegistrationMaster registrationMaster)
    {
        List<RegistrationDetail> registrationDetailList = registrationMaster.getRegistrationDetailList();
        Long id = registrationMaster.getId();
        if (StringUtils.isNotNull(registrationDetailList))
        {
            List<RegistrationDetail> list = new ArrayList<RegistrationDetail>();
            for (RegistrationDetail registrationDetail : registrationDetailList)
            {
                registrationDetail.setId(id);
                list.add(registrationDetail);
            }
            if (list.size() > 0)
            {
                registrationMasterMapper.batchRegistrationDetail(list);
            }
        }
    }
}
