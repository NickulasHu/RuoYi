package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.system.domain.SysBuilding;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author hsy
 * @date 2020-11-22
 */
public interface ISysBuildingService 
{
    /**
     * 查询楼栋
     * 
     * @param dormId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysBuilding selectSysBuildingById(String dormId);

    /**
     * 查询楼栋列表
     * 
     * @param sysBuilding 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysBuilding> selectSysBuildingList(SysBuilding sysBuilding);

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysBuilding 【请填写功能名称】
     * @return 结果
     */
    public int insertSysBuilding(SysBuilding sysBuilding);

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysBuilding 【请填写功能名称】
     * @return 结果
     */
    public int updateSysBuilding(SysBuilding sysBuilding);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysBuildingByIds(String ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param dormId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysBuildingById(String dormId);

	public int deleteAllSysBuilding();
}
