package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysRuleBuilding;

/**
 * 规则楼栋关系Service接口
 * 
 * @author hsy
 * @date 2020-11-30
 */
public interface ISysRuleBuildingService 
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
     * 批量删除规则楼栋关系
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysRuleBuildingByIds(String ids);

    /**
     * 删除规则楼栋关系信息
     * 
     * @param ruleBuildId 规则楼栋关系ID
     * @return 结果
     */
    public int deleteSysRuleBuildingById(String ruleBuildId);

	public List<String> selectConfigBuildingIds(String ruleId);

	public int deleteConfigBuildings(String ruleId);
}
