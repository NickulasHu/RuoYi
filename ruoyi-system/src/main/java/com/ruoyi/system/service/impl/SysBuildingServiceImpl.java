package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysBuildingMapper;
import com.ruoyi.system.domain.SysBuilding;
import com.ruoyi.system.service.ISysBuildingService;
import com.ruoyi.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author hsy
 * @date 2020-11-22
 */
@Service
public class SysBuildingServiceImpl implements ISysBuildingService 
{
    @Autowired
    private SysBuildingMapper sysBuildingMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param dormId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysBuilding selectSysBuildingById(String dormId)
    {
        return sysBuildingMapper.selectSysBuildingById(dormId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysBuilding 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysBuilding> selectSysBuildingList(SysBuilding sysBuilding)
    {
        return sysBuildingMapper.selectSysBuildingList(sysBuilding);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysBuilding 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysBuilding(SysBuilding sysBuilding)
    {
        return sysBuildingMapper.insertSysBuilding(sysBuilding);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysBuilding 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysBuilding(SysBuilding sysBuilding)
    {
        return sysBuildingMapper.updateSysBuilding(sysBuilding);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysBuildingByIds(String ids)
    {
        return sysBuildingMapper.deleteSysBuildingByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param dormId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysBuildingById(String dormId)
    {
        return sysBuildingMapper.deleteSysBuildingById(dormId);
    }

	@Override
	public int deleteAllSysBuilding() {
		// TODO Auto-generated method stub
		return sysBuildingMapper.deleteAllSysBuilding();
	}
}
