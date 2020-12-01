package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysRuleBuilding;

/**
 * 规则楼栋关系Mapper接口
 * 
 * @author hsy
 * @date 2020-11-30
 */
public interface SysRuleBuildingMapper 
{
    /**
     * 查询规则楼栋关系
     * 
     * @param ruleBuildId 规则楼栋关系ID
     * @return 规则楼栋关系
     */
    public SysRuleBuilding selectSysRuleBuildingById(String ruleBuildId);

    /**
     * 查询规则楼栋关系列表
     * 
     * @param sysRuleBuilding 规则楼栋关系
     * @return 规则楼栋关系集合
     */
    public List<SysRuleBuilding> selectSysRuleBuildingList(SysRuleBuilding sysRuleBuilding);

    /**
     * 新增规则楼栋关系
     * 
     * @param sysRuleBuilding 规则楼栋关系
     * @return 结果
     */
    public int insertSysRuleBuilding(SysRuleBuilding sysRuleBuilding);

    /**
     * 修改规则楼栋关系
     * 
     * @param sysRuleBuilding 规则楼栋关系
     * @return 结果
     */
    public int updateSysRuleBuilding(SysRuleBuilding sysRuleBuilding);

    /**
     * 删除规则楼栋关系
     * 
     * @param ruleBuildId 规则楼栋关系ID
     * @return 结果
     */
    public int deleteSysRuleBuildingById(String ruleBuildId);

    /**
     * 批量删除规则楼栋关系
     * 
     * @param ruleBuildIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysRuleBuildingByIds(String[] ruleBuildIds);

	public List<String> selectConfigBuildingIds(String ruleId);

	public int deleteConfigBuildings(String ruleId);
}
