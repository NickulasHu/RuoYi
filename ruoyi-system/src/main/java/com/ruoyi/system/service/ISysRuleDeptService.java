package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysRuleDept;

/**
 * 规则部门关系表Service接口
 * 
 * @author hsy
 * @date 2020-11-30
 */
public interface ISysRuleDeptService 
{
    /**
     * 查询规则部门关系表
     * 
     * @param ruleDeptId 规则部门关系表ID
     * @return 规则部门关系表
     */
    public SysRuleDept selectSysRuleDeptById(String ruleDeptId);

    /**
     * 查询规则部门关系表列表
     * 
     * @param sysRuleDept 规则部门关系表
     * @return 规则部门关系表集合
     */
    public List<SysRuleDept> selectSysRuleDeptList(SysRuleDept sysRuleDept);

    /**
     * 新增规则部门关系表
     * 
     * @param sysRuleDept 规则部门关系表
     * @return 结果
     */
    public int insertSysRuleDept(SysRuleDept sysRuleDept);

    /**
     * 修改规则部门关系表
     * 
     * @param sysRuleDept 规则部门关系表
     * @return 结果
     */
    public int updateSysRuleDept(SysRuleDept sysRuleDept);

    /**
     * 批量删除规则部门关系表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysRuleDeptByIds(String ids);

    /**
     * 删除规则部门关系表信息
     * 
     * @param ruleDeptId 规则部门关系表ID
     * @return 结果
     */
    public int deleteSysRuleDeptById(String ruleDeptId);

	public List<Long> selectConfigDeptIds(String ruleId);

	public int deleteConfigDepts(String ruleId);
}
