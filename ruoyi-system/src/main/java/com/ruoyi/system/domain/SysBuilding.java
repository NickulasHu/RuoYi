package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sys_building
 * 
 * @author hsy
 * @date 2020-11-22
 */
public class SysBuilding extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String dormId;

    /** 楼栋名称 */
    @Excel(name = "楼栋名称")
    private String dormName;

    /** 楼编码 */
    @Excel(name = "楼编码")
    private String dormCode;

    /** 等级 */
    @Excel(name = "等级")
    private Long dormLevel;

    /** 排序ID */
    @Excel(name = "排序ID")
    private Long dormSortId;

    /** 楼号 */
    @Excel(name = "楼号")
    private Long dormNum;

    /** 父级ID */
    @Excel(name = "父级ID")
    private String parentDormId;

    /** 父级名称 */
    @Excel(name = "父级编码")
    private String parentDormCode;
    
    private SysBuilding[] subBuildings;

    public SysBuilding[] getSubBuildings() {
		return subBuildings;
	}

	public void setSubBuildings(SysBuilding[] subBuildings) {
		this.subBuildings = subBuildings;
	}

	public void setDormId(String dormId) 
    {
        this.dormId = dormId;
    }

    public String getDormId() 
    {
        return dormId;
    }
    public void setDormName(String dormName) 
    {
        this.dormName = dormName;
    }

    public String getDormName() 
    {
        return dormName;
    }
    public void setDormCode(String dormCode) 
    {
        this.dormCode = dormCode;
    }

    public String getDormCode() 
    {
        return dormCode;
    }
    public void setDormLevel(Long dormLevel) 
    {
        this.dormLevel = dormLevel;
    }

    public Long getDormLevel() 
    {
        return dormLevel;
    }
    public void setDormSortId(Long dormSortId) 
    {
        this.dormSortId = dormSortId;
    }

    public Long getDormSortId() 
    {
        return dormSortId;
    }
    public void setDormNum(Long dormNum) 
    {
        this.dormNum = dormNum;
    }

    public Long getDormNum() 
    {
        return dormNum;
    }
    public void setParentDormId(String parentDormId) 
    {
        this.parentDormId = parentDormId;
    }

    public String getParentDormId() 
    {
        return parentDormId;
    }
    public void setParentDormCode(String parentDormCode) 
    {
        this.parentDormCode = parentDormCode;
    }

    public String getParentDormCode() 
    {
        return parentDormCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dormId", getDormId())
            .append("dormName", getDormName())
            .append("dormCode", getDormCode())
            .append("dormLevel", getDormLevel())
            .append("dormSortId", getDormSortId())
            .append("dormNum", getDormNum())
            .append("parentDormId", getParentDormId())
            .append("parentDormCode", getParentDormCode())
            .toString();
    }
}
