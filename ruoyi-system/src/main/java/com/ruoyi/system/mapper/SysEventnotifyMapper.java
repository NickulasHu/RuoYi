package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysEventnotify;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author hsy
 * @date 2020-11-30
 */
public interface SysEventnotifyMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param eventId 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SysEventnotify selectSysEventnotifyById(String eventId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param sysEventnotify 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SysEventnotify> selectSysEventnotifyList(SysEventnotify sysEventnotify);

    /**
     * 新增【请填写功能名称】
     * 
     * @param sysEventnotify 【请填写功能名称】
     * @return 结果
     */
    public int insertSysEventnotify(SysEventnotify sysEventnotify);

    /**
     * 修改【请填写功能名称】
     * 
     * @param sysEventnotify 【请填写功能名称】
     * @return 结果
     */
    public int updateSysEventnotify(SysEventnotify sysEventnotify);

    /**
     * 删除【请填写功能名称】
     * 
     * @param eventId 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSysEventnotifyById(String eventId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param eventIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysEventnotifyByIds(String[] eventIds);
}
