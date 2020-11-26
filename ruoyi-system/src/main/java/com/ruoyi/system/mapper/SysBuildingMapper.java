package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysBuilding;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author hsy
 * @date 2020-11-22
 */
public interface SysBuildingMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param dormId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysBuilding selectSysBuildingById(String dormId);

    /**
     * 查询【请填写功能名称】列表
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
     * 删除【请填写功能名称】
     * 
     * @param dormId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysBuildingById(String dormId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param dormIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysBuildingByIds(String[] dormIds);

	public int deleteAllSysBuilding();
}
