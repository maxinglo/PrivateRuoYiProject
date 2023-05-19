package com.ruoyi.system.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报名主表对象 registration_master
 * 
 * @author Maxing
 * @date 2023-05-06
 */
public class RegistrationMaster extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标识符 */
    private Long id;

    /** 组别 */
    @Excel(name = "组别")
    private String teamGroup;

    /** 参赛队名 */
    @Excel(name = "参赛队名")
    private String teamName;

    /** 团长 */
    @Excel(name = "团长")
    private String teamLeader;

    /** 领队 */
    @Excel(name = "领队")
    private String teamCaptain;

    /** 教练 */
    @Excel(name = "教练")
    private String coach;

    /** 工作人员 */
    @Excel(name = "工作人员")
    private String staff;

    /** 队医 */
    @Excel(name = "队医")
    private String teamDoctor;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactInfo;

    /** 报名子表信息 */
    private List<RegistrationDetail> registrationDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTeamGroup(String teamGroup) 
    {
        this.teamGroup = teamGroup;
    }

    public String getTeamGroup() 
    {
        return teamGroup;
    }
    public void setTeamName(String teamName) 
    {
        this.teamName = teamName;
    }

    public String getTeamName() 
    {
        return teamName;
    }
    public void setTeamLeader(String teamLeader) 
    {
        this.teamLeader = teamLeader;
    }

    public String getTeamLeader() 
    {
        return teamLeader;
    }
    public void setTeamCaptain(String teamCaptain) 
    {
        this.teamCaptain = teamCaptain;
    }

    public String getTeamCaptain() 
    {
        return teamCaptain;
    }
    public void setCoach(String coach) 
    {
        this.coach = coach;
    }

    public String getCoach() 
    {
        return coach;
    }
    public void setStaff(String staff) 
    {
        this.staff = staff;
    }

    public String getStaff() 
    {
        return staff;
    }
    public void setTeamDoctor(String teamDoctor) 
    {
        this.teamDoctor = teamDoctor;
    }

    public String getTeamDoctor() 
    {
        return teamDoctor;
    }
    public void setContactInfo(String contactInfo) 
    {
        this.contactInfo = contactInfo;
    }

    public String getContactInfo() 
    {
        return contactInfo;
    }

    public List<RegistrationDetail> getRegistrationDetailList()
    {
        return registrationDetailList;
    }

    public void setRegistrationDetailList(List<RegistrationDetail> registrationDetailList)
    {
        this.registrationDetailList = registrationDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("teamGroup", getTeamGroup())
            .append("teamName", getTeamName())
            .append("teamLeader", getTeamLeader())
            .append("teamCaptain", getTeamCaptain())
            .append("coach", getCoach())
            .append("staff", getStaff())
            .append("teamDoctor", getTeamDoctor())
            .append("contactInfo", getContactInfo())
            .append("registrationDetailList", getRegistrationDetailList())
            .toString();
    }
}
