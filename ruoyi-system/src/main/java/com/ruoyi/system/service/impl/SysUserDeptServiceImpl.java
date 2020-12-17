package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysUserDeptMapper;
import com.ruoyi.system.domain.SysUserDept;
import com.ruoyi.system.service.ISysUserDeptService;
import com.ruoyi.common.core.text.Convert;

/**
 * 用户部门关系表Service业务层处理
 * 
 * @author hsy
 * @date 2020-12-01
 */
@Service
public class SysUserDeptServiceImpl implements ISysUserDeptService 
{
    @Autowired
    private SysUserDeptMapper sysUserDeptMapper;

    /**
     * 查询用户部门关系表
     * 
     * @param userDeptId 用户部门关系表ID
     * @return 用户部门关系表
     */
    @Override
    public SysUserDept selectSysUserDeptById(String userDeptId)
    {
        return sysUserDeptMapper.selectSysUserDeptById(userDeptId);
    }

    /**
     * 查询用户部门关系表列表
     * 
     * @param sysUserDept 用户部门关系表
     * @return 用户部门关系表
     */
    @Override
    public List<SysUserDept> selectSysUserDeptList(SysUserDept sysUserDept)
    {
        return sysUserDeptMapper.selectSysUserDeptList(sysUserDept);
    }

    /**
     * 新增用户部门关系表
     * 
     * @param sysUserDept 用户部门关系表
     * @return 结果
     */
    @Override
    public int insertSysUserDept(SysUserDept sysUserDept)
    {
        return sysUserDeptMapper.insertSysUserDept(sysUserDept);
    }

    /**
     * 修改用户部门关系表
     * 
     * @param sysUserDept 用户部门关系表
     * @return 结果
     */
    @Override
    public int updateSysUserDept(SysUserDept sysUserDept)
    {
        return sysUserDeptMapper.updateSysUserDept(sysUserDept);
    }

    /**
     * 删除用户部门关系表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysUserDeptByIds(String ids)
    {
        return sysUserDeptMapper.deleteSysUserDeptByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除用户部门关系表信息
     * 
     * @param userDeptId 用户部门关系表ID
     * @return 结果
     */
    @Override
    public int deleteSysUserDeptById(String userDeptId)
    {
        return sysUserDeptMapper.deleteSysUserDeptById(userDeptId);
    }

	@Override
	public List<Long> selectConfigDeptIds(Long userId) {
		return sysUserDeptMapper.selectConfigDeptIds(userId);
	}

	@Override
	public int deleteConfigDepts(Long userId) {
		return sysUserDeptMapper.deleteConfigDepts(userId);
	}

	@Override
	public List<Long> selectUserIdByDeptId(Long deptId) {
		return sysUserDeptMapper.selectUserIdByDeptId(deptId);
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return sysUserDeptMapper.deleteAll();
	}
}
