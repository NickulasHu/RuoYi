package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysRule;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author hsy
 * @date 2020-11-22
 */
public interface ISysRuleService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param ruleId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysRule selectSysRuleById(String ruleId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysRule 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysRule> selectSysRuleList(SysRule sysRule);

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysRule 【请填写功能名称】
     * @return 结果
     */
    public int insertSysRule(SysRule sysRule);

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysRule 【请填写功能名称】
     * @return 结果
     */
    public int updateSysRule(SysRule sysRule);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysRuleByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param ruleId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysRuleById(String ruleId);

	public int deleteAllSysRuleByIds();
}
