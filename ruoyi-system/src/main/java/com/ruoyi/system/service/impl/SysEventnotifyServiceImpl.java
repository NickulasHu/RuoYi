package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysEventnotifyMapper;
import com.ruoyi.system.domain.SysEventnotify;
import com.ruoyi.system.service.ISysEventnotifyService;
import com.ruoyi.common.core.text.Convert;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author hsy
 * @date 2020-11-30
 */
@Service
public class SysEventnotifyServiceImpl implements ISysEventnotifyService 
{
    @Autowired
    private SysEventnotifyMapper sysEventnotifyMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param eventId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SysEventnotify selectSysEventnotifyById(String eventId)
    {
        return sysEventnotifyMapper.selectSysEventnotifyById(eventId);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysEventnotify 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SysEventnotify> selectSysEventnotifyList(SysEventnotify sysEventnotify)
    {
        return sysEventnotifyMapper.selectSysEventnotifyList(sysEventnotify);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysEventnotify 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSysEventnotify(SysEventnotify sysEventnotify)
    {
        return sysEventnotifyMapper.insertSysEventnotify(sysEventnotify);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysEventnotify 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSysEventnotify(SysEventnotify sysEventnotify)
    {
        return sysEventnotifyMapper.updateSysEventnotify(sysEventnotify);
    }

    /**
     * 删除【请填写功能名称】对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysEventnotifyByIds(String ids)
    {
        return sysEventnotifyMapper.deleteSysEventnotifyByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param eventId 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSysEventnotifyById(String eventId)
    {
        return sysEventnotifyMapper.deleteSysEventnotifyById(eventId);
    }
}
