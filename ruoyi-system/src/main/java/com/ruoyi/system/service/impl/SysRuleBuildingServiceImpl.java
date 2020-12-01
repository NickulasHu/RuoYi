package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysRuleBuildingMapper;
import com.ruoyi.system.domain.SysRuleBuilding;
import com.ruoyi.system.service.ISysRuleBuildingService;
import com.ruoyi.common.core.text.Convert;

/**
 * 规则楼栋关系Service业务层处理
 * 
 * @author hsy
 * @date 2020-11-30
 */
@Service
public class SysRuleBuildingServiceImpl implements ISysRuleBuildingService 
{
    @Autowired
    private SysRuleBuildingMapper sysRuleBuildingMapper;

    /**
     * 查询规则楼栋关系
     * 
     * @param ruleBuildId 规则楼栋关系ID
     * @return 规则楼栋关系
     */
    @Override
    public SysRuleBuilding selectSysRuleBuildingById(String ruleBuildId)
    {
        return sysRuleBuildingMapper.selectSysRuleBuildingById(ruleBuildId);
    }

    /**
     * 查询规则楼栋关系列表
     * 
     * @param sysRuleBuilding 规则楼栋关系
     * @return 规则楼栋关系
     */
    @Override
    public List<SysRuleBuilding> selectSysRuleBuildingList(SysRuleBuilding sysRuleBuilding)
    {
        return sysRuleBuildingMapper.selectSysRuleBuildingList(sysRuleBuilding);
    }

    /**
     * 新增规则楼栋关系
     * 
     * @param sysRuleBuilding 规则楼栋关系
     * @return 结果
     */
    @Override
    public int insertSysRuleBuilding(SysRuleBuilding sysRuleBuilding)
    {
        return sysRuleBuildingMapper.insertSysRuleBuilding(sysRuleBuilding);
    }

    /**
     * 修改规则楼栋关系
     * 
     * @param sysRuleBuilding 规则楼栋关系
     * @return 结果
     */
    @Override
    public int updateSysRuleBuilding(SysRuleBuilding sysRuleBuilding)
    {
        return sysRuleBuildingMapper.updateSysRuleBuilding(sysRuleBuilding);
    }

    /**
     * 删除规则楼栋关系对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysRuleBuildingByIds(String ids)
    {
        return sysRuleBuildingMapper.deleteSysRuleBuildingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除规则楼栋关系信息
     * 
     * @param ruleBuildId 规则楼栋关系ID
     * @return 结果
     */
    @Override
    public int deleteSysRuleBuildingById(String ruleBuildId)
    {
        return sysRuleBuildingMapper.deleteSysRuleBuildingById(ruleBuildId);
    }

	@Override
	public List<String> selectConfigBuildingIds(String ruleId) {
		return sysRuleBuildingMapper.selectConfigBuildingIds(ruleId);
	}

	@Override
	public int deleteConfigBuildings(String ruleId) {
		return sysRuleBuildingMapper.deleteConfigBuildings(ruleId);
	}
}
