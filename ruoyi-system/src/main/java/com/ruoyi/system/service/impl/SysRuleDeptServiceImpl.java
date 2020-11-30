package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysRuleDeptMapper;
import com.ruoyi.system.domain.SysRuleDept;
import com.ruoyi.system.service.ISysRuleDeptService;
import com.ruoyi.common.core.text.Convert;

/**
 * 规则部门关系表Service业务层处理
 * 
 * @author hsy
 * @date 2020-11-30
 */
@Service
public class SysRuleDeptServiceImpl implements ISysRuleDeptService 
{
    @Autowired
    private SysRuleDeptMapper sysRuleDeptMapper;

    /**
     * 查询规则部门关系表
     * 
     * @param ruleDeptId 规则部门关系表ID
     * @return 规则部门关系表
     */
    @Override
    public SysRuleDept selectSysRuleDeptById(String ruleDeptId)
    {
        return sysRuleDeptMapper.selectSysRuleDeptById(ruleDeptId);
    }

    /**
     * 查询规则部门关系表列表
     * 
     * @param sysRuleDept 规则部门关系表
     * @return 规则部门关系表
     */
    @Override
    public List<SysRuleDept> selectSysRuleDeptList(SysRuleDept sysRuleDept)
    {
        return sysRuleDeptMapper.selectSysRuleDeptList(sysRuleDept);
    }

    /**
     * 新增规则部门关系表
     * 
     * @param sysRuleDept 规则部门关系表
     * @return 结果
     */
    @Override
    public int insertSysRuleDept(SysRuleDept sysRuleDept)
    {
        return sysRuleDeptMapper.insertSysRuleDept(sysRuleDept);
    }

    /**
     * 修改规则部门关系表
     * 
     * @param sysRuleDept 规则部门关系表
     * @return 结果
     */
    @Override
    public int updateSysRuleDept(SysRuleDept sysRuleDept)
    {
        return sysRuleDeptMapper.updateSysRuleDept(sysRuleDept);
    }

    /**
     * 删除规则部门关系表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysRuleDeptByIds(String ids)
    {
        return sysRuleDeptMapper.deleteSysRuleDeptByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除规则部门关系表信息
     * 
     * @param ruleDeptId 规则部门关系表ID
     * @return 结果
     */
    @Override
    public int deleteSysRuleDeptById(String ruleDeptId)
    {
        return sysRuleDeptMapper.deleteSysRuleDeptById(ruleDeptId);
    }
}
