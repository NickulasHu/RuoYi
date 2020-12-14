package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysUserDept;

/**
 * 用户部门关系表Mapper接口
 * 
 * @author hsy
 * @date 2020-12-01
 */
public interface SysUserDeptMapper 
{
    /**
     * 查询用户部门关系表
     * 
     * @param userDeptId 用户部门关系表ID
     * @return 用户部门关系表
     */
    public SysUserDept selectSysUserDeptById(String userDeptId);

    /**
     * 查询用户部门关系表列表
     * 
     * @param sysUserDept 用户部门关系表
     * @return 用户部门关系表集合
     */
    public List<SysUserDept> selectSysUserDeptList(SysUserDept sysUserDept);

    /**
     * 新增用户部门关系表
     * 
     * @param sysUserDept 用户部门关系表
     * @return 结果
     */
    public int insertSysUserDept(SysUserDept sysUserDept);

    /**
     * 修改用户部门关系表
     * 
     * @param sysUserDept 用户部门关系表
     * @return 结果
     */
    public int updateSysUserDept(SysUserDept sysUserDept);

    /**
     * 删除用户部门关系表
     * 
     * @param userDeptId 用户部门关系表ID
     * @return 结果
     */
    public int deleteSysUserDeptById(String userDeptId);

    /**
     * 批量删除用户部门关系表
     * 
     * @param userDeptIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysUserDeptByIds(String[] userDeptIds);

	public List<Long> selectConfigDeptIds(Long userId);

	public int deleteConfigDepts(Long userId);

	public List<Long> selectUserIdByDeptId(Long deptId);
}
