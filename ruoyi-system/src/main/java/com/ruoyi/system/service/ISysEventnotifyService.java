package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysEventnotify;

/**
 * 异常事件Service接口
 * 
 * @author hsy
 * @date 2020-11-30
 */
public interface ISysEventnotifyService 
{
    /**
     * 查询异常事件
     * 
     * @param eventId 异常事件ID
     * @return 异常事件
     */
    public SysEventnotify selectSysEventnotifyById(String eventId);

    /**
     * 查询异常事件列表
     * 
     * @param sysEventnotify 异常事件
     * @return 异常事件集合
     */
    public List<SysEventnotify> selectSysEventnotifyList(SysEventnotify sysEventnotify);

    /**
     * 新增异常事件
     * 
     * @param sysEventnotify 异常事件
     * @return 结果
     */
    public int insertSysEventnotify(SysEventnotify sysEventnotify);

    /**
     * 修改异常事件
     * 
     * @param sysEventnotify 异常事件
     * @return 结果
     */
    public int updateSysEventnotify(SysEventnotify sysEventnotify);

    /**
     * 批量删除异常事件
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysEventnotifyByIds(String ids);

    /**
     * 删除异常事件信息
     * 
     * @param eventId 异常事件ID
     * @return 结果
     */
    public int deleteSysEventnotifyById(String eventId);
}
