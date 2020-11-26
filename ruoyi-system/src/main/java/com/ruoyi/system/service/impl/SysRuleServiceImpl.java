package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysRuleMapper;
import com.ruoyi.system.domain.SysRule;
import com.ruoyi.system.service.ISysRuleService;
import com.ruoyi.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author hsy
 * @date 2020-11-22
 */
@Service
public class SysRuleServiceImpl implements ISysRuleService 
{
    @Autowired
    private SysRuleMapper sysRuleMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param ruleId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysRule selectSysRuleById(String ruleId)
    {
        return sysRuleMapper.selectSysRuleById(ruleId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysRule 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysRule> selectSysRuleList(SysRule sysRule)
    {
        return sysRuleMapper.selectSysRuleList(sysRule);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysRule 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysRule(SysRule sysRule)
    {
        return sysRuleMapper.insertSysRule(sysRule);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysRule 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysRule(SysRule sysRule)
    {
        return sysRuleMapper.updateSysRule(sysRule);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysRuleByIds(String ids)
    {
        return sysRuleMapper.deleteSysRuleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param ruleId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysRuleById(String ruleId)
    {
        return sysRuleMapper.deleteSysRuleById(ruleId);
    }

	@Override
	public int deleteAllSysRuleByIds() {
		return sysRuleMapper.deleteAllSysRuleByIds();
	}
}
