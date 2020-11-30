package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 规则部门关系表对象 sys_rule_dept
 * 
 * @author hsy
 * @date 2020-11-30
 */
public class SysRuleDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private String ruleDeptId;

    /** 规则Id */
    @Excel(name = "规则Id")
    private String ruleId;

    /** 部门Id */
    @Excel(name = "部门Id")
    private String deptId;

    public void setRuleDeptId(String ruleDeptId) 
    {
        this.ruleDeptId = ruleDeptId;
    }

    public String getRuleDeptId() 
    {
        return ruleDeptId;
    }
    public void setRuleId(String ruleId) 
    {
        this.ruleId = ruleId;
    }

    public String getRuleId() 
    {
        return ruleId;
    }
    public void setDeptId(String deptId) 
    {
        this.deptId = deptId;
    }

    public String getDeptId() 
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ruleDeptId", getRuleDeptId())
            .append("ruleId", getRuleId())
            .append("deptId", getDeptId())
            .toString();
    }
}
