package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报名子表对象 registration_detail
 * 
 * @author Maxing
 * @date 2023-05-06
 */
public class RegistrationDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 标识符 */
    private Long id;

    /** 主表ID */
    @Excel(name = "主表ID")
    private Long masterId;

    /** 学工号 */
    @Excel(name = "学工号")
    private String studentId;

    /** 名字 */
    @Excel(name = "名字")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String idCardNumber;

    /** 项目一 */
    @Excel(name = "项目一")
    private String project1;

    /** 项目二 */
    @Excel(name = "项目二")
    private String project2;

    /** 接力赛 */
    @Excel(name = "接力赛")
    private String relayRace;

    /** 拔河比赛 */
    @Excel(name = "拔河比赛")
    private String tugOfWar;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMasterId(Long masterId) 
    {
        this.masterId = masterId;
    }

    public Long getMasterId() 
    {
        return masterId;
    }
    public void setStudentId(String studentId) 
    {
        this.studentId = studentId;
    }

    public String getStudentId() 
    {
        return studentId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setIdCardNumber(String idCardNumber) 
    {
        this.idCardNumber = idCardNumber;
    }

    public String getIdCardNumber() 
    {
        return idCardNumber;
    }
    public void setProject1(String project1) 
    {
        this.project1 = project1;
    }

    public String getProject1() 
    {
        return project1;
    }
    public void setProject2(String project2) 
    {
        this.project2 = project2;
    }

    public String getProject2() 
    {
        return project2;
    }
    public void setRelayRace(String relayRace) 
    {
        this.relayRace = relayRace;
    }

    public String getRelayRace() 
    {
        return relayRace;
    }
    public void setTugOfWar(String tugOfWar) 
    {
        this.tugOfWar = tugOfWar;
    }

    public String getTugOfWar() 
    {
        return tugOfWar;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("masterId", getMasterId())
            .append("studentId", getStudentId())
            .append("name", getName())
            .append("gender", getGender())
            .append("idCardNumber", getIdCardNumber())
            .append("project1", getProject1())
            .append("project2", getProject2())
            .append("relayRace", getRelayRace())
            .append("tugOfWar", getTugOfWar())
            .append("remark", getRemark())
            .toString();
    }
}
