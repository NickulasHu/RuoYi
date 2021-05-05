package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 宿舍进出记录对象 sys_in_out_record
 * 
 * @author hsy
 * @date 2020-12-07
 */
public class SysInOutRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录Id */
    private String recordId;

    /** 学号 */
    @Excel(name = "学号")
    private String studentCode;

    /** 行为类型: 出or入 */
    @Excel(name = "记录类型")
    private String entryType;
    
    /** 考勤类型: 宿舍考勤or校门考勤 */
    @Excel(name = "考勤类型")
    private String recordType;

    /** 姓名 */
    @Excel(name = "姓名")
    private String studentName;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 组织 */
    @Excel(name = "组织")
    private String orgId;

    /** 楼栋 */
    @Excel(name = "楼栋")
    private String dormId;

    /** 时间 */
    @Excel(name = "时间")
    private String alarmTime;
    
    private String startTime;
    
    private String endTime;

    public void setRecordId(String recordId) 
    {
        this.recordId = recordId;
    }

    public String getRecordId() 
    {
        return recordId;
    }
    public void setStudentCode(String studentCode) 
    {
        this.studentCode = studentCode;
    }

    public String getStudentCode() 
    {
        return studentCode;
    }
    public void setEntryType(String entryType) 
    {
        this.entryType = entryType;
    }

    public String getEntryType() 
    {
        return entryType;
    }
    
    public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public void setStudentName(String studentName) 
    {
        this.studentName = studentName;
    }

    public String getStudentName() 
    {
        return studentName;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setOrgId(String orgId) 
    {
        this.orgId = orgId;
    }

    public String getOrgId() 
    {
        return orgId;
    }
    public void setDormId(String dormId) 
    {
        this.dormId = dormId;
    }

    public String getDormId() 
    {
        return dormId;
    }
    public void setAlarmTime(String alarmTime) 
    {
        this.alarmTime = alarmTime;
    }

    public String getAlarmTime() 
    {
        return alarmTime;
    }

    public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("studentCode", getStudentCode())
            .append("entryType", getEntryType())
            .append("studentName", getStudentName())
            .append("sex", getSex())
            .append("orgId", getOrgId())
            .append("dormId", getDormId())
            .append("alarmTime", getAlarmTime())
            .toString();
    }
}
