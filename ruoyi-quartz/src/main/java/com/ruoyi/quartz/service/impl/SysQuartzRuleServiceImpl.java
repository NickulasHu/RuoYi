package com.ruoyi.quartz.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.quartz.mapper.SysQuartzRuleMapper;
import com.ruoyi.quartz.domain.SysQuartzRule;
import com.ruoyi.quartz.service.ISysQuartzRuleService;
import com.ruoyi.common.core.text.Convert;

/**
 * 定时任务模块的规则类Service业务层处理
 * 
 * @author hsy
 * @date 2020-12-03
 */
@Service
public class SysQuartzRuleServiceImpl implements ISysQuartzRuleService 
{
    @Autowired
    private SysQuartzRuleMapper sysQuartzRuleMapper;

    /**
     * 查询定时任务模块的规则类
     * 
     * @param ruleId 定时任务模块的规则类ID
     * @return 定时任务模块的规则类
     */
    @Override
    public SysQuartzRule selectSysQuartzRuleById(String ruleId)
    {
        return sysQuartzRuleMapper.selectSysQuartzRuleById(ruleId);
    }

    /**
     * 查询定时任务模块的规则类列表
     * 
     * @param sysQuartzRule 定时任务模块的规则类
     * @return 定时任务模块的规则类
     */
    @Override
    public List<SysQuartzRule> selectSysQuartzRuleList(SysQuartzRule sysQuartzRule)
    {
        return sysQuartzRuleMapper.selectSysQuartzRuleList(sysQuartzRule);
    }

    /**
     * 新增定时任务模块的规则类
     * 
     * @param sysQuartzRule 定时任务模块的规则类
     * @return 结果
     */
    @Override
    public int insertSysQuartzRule(SysQuartzRule sysQuartzRule)
    {
        return sysQuartzRuleMapper.insertSysQuartzRule(sysQuartzRule);
    }

    /**
     * 修改定时任务模块的规则类
     * 
     * @param sysQuartzRule 定时任务模块的规则类
     * @return 结果
     */
    @Override
    public int updateSysQuartzRule(SysQuartzRule sysQuartzRule)
    {
        return sysQuartzRuleMapper.updateSysQuartzRule(sysQuartzRule);
    }

    /**
     * 删除定时任务模块的规则类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysQuartzRuleByIds(String ids)
    {
        return sysQuartzRuleMapper.deleteSysQuartzRuleByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除定时任务模块的规则类信息
     * 
     * @param ruleId 定时任务模块的规则类ID
     * @return 结果
     */
    @Override
    public int deleteSysQuartzRuleById(String ruleId)
    {
        return sysQuartzRuleMapper.deleteSysQuartzRuleById(ruleId);
    }
}
