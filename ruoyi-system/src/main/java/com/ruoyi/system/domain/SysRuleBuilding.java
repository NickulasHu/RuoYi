package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 规则楼栋关系对象 sys_rule_building
 * 
 * @author hsy
 * @date 2020-11-30
 */
public class SysRuleBuilding extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private String ruleBuildId;

    /** 规则Id */
    @Excel(name = "规则Id")
    private String ruleId;

    /** 楼栋Id */
    @Excel(name = "楼栋Id")
    private String buildId;

    public void setRuleBuildId(String ruleBuildId) 
    {
        this.ruleBuildId = ruleBuildId;
    }

    public String getRuleBuildId() 
    {
        return ruleBuildId;
    }
    public void setRuleId(String ruleId) 
    {
        this.ruleId = ruleId;
    }

    public String getRuleId() 
    {
        return ruleId;
    }
    public void setBuildId(String buildId) 
    {
        this.buildId = buildId;
    }

    public String getBuildId() 
    {
        return buildId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ruleBuildId", getRuleBuildId())
            .append("ruleId", getRuleId())
            .append("buildId", getBuildId())
            .toString();
    }
}
