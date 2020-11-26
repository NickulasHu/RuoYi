package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sys_rule
 * 
 * @author hsy
 * @date 2020-11-22
 */
public class SysRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 规则ID */
    private String ruleId;

    /** 规则名称 */
    @Excel(name = "规则名称")
    private String ruleName;

    /** 开始时间 */
    @Excel(name = "开始时间")
    private String inDormStart;

    /** 结束时间 */
    @Excel(name = "结束时间")
    private String inDormEnd;

    /** 最迟时间 */
    @Excel(name = "最迟时间")
    private String laterTime;

    /** 对应工作日 */
    @Excel(name = "对应工作日")
    private String period;

    /** 适用者 */
    @Excel(name = "适用者")
    private String ruleUsers;

    public void setRuleId(String ruleId) 
    {
        this.ruleId = ruleId;
    }

    public String getRuleId() 
    {
        return ruleId;
    }
    public void setRuleName(String ruleName) 
    {
        this.ruleName = ruleName;
    }

    public String getRuleName() 
    {
        return ruleName;
    }
    public String getInDormStart() {
		return inDormStart;
	}

	public void setInDormStart(String inDormStart) {
		this.inDormStart = inDormStart;
	}

	public String getInDormEnd() {
		return inDormEnd;
	}

	public void setInDormEnd(String inDormEnd) {
		this.inDormEnd = inDormEnd;
	}

	public String getLaterTime() {
		return laterTime;
	}

	public void setLaterTime(String laterTime) {
		this.laterTime = laterTime;
	}

	public void setPeriod(String period) 
    {
        this.period = period;
    }

    public String getPeriod() 
    {
        return period;
    }
    public void setRuleUsers(String ruleUsers) 
    {
        this.ruleUsers = ruleUsers;
    }

    public String getRuleUsers() 
    {
        return ruleUsers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ruleId", getRuleId())
            .append("ruleName", getRuleName())
            .append("inDormStart", getInDormStart())
            .append("inDormEnd", getInDormEnd())
            .append("laterTime", getLaterTime())
            .append("period", getPeriod())
            .append("ruleUsers", getRuleUsers())
            .toString();
    }
}
