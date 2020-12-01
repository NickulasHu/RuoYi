package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户部门关系表对象 sys_user_dept
 * 
 * @author hsy
 * @date 2020-12-01
 */
public class SysUserDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private String userDeptId;

    /** 用户Id */
    @Excel(name = "用户Id")
    private Long userId;

    /** 部门Id */
    @Excel(name = "部门Id")
    private Long deptId;

    public void setUserDeptId(String userDeptId) 
    {
        this.userDeptId = userDeptId;
    }

    public String getUserDeptId() 
    {
        return userDeptId;
    }
   
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userDeptId", getUserDeptId())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .toString();
    }
}
