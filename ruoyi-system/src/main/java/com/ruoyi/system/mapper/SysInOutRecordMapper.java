package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SysInOutRecord;

/**
 * 宿舍进出记录Mapper接口
 * 
 * @author hsy
 * @date 2020-12-07
 */
public interface SysInOutRecordMapper 
{
    /**
     * 查询宿舍进出记录
     * 
     * @param recordId 宿舍进出记录ID
     * @return 宿舍进出记录
     */
    public SysInOutRecord selectSysInOutRecordById(String recordId);

    /**
     * 查询宿舍进出记录列表
     * 
     * @param sysInOutRecord 宿舍进出记录
     * @return 宿舍进出记录集合
     */
    public List<SysInOutRecord> selectSysInOutRecordList(SysInOutRecord sysInOutRecord);

    /**
     * 新增宿舍进出记录
     * 
     * @param sysInOutRecord 宿舍进出记录
     * @return 结果
     */
    public int insertSysInOutRecord(SysInOutRecord sysInOutRecord);

    /**
     * 修改宿舍进出记录
     * 
     * @param sysInOutRecord 宿舍进出记录
     * @return 结果
     */
    public int updateSysInOutRecord(SysInOutRecord sysInOutRecord);

    /**
     * 删除宿舍进出记录
     * 
     * @param recordId 宿舍进出记录ID
     * @return 结果
     */
    public int deleteSysInOutRecordById(String recordId);

    /**
     * 批量删除宿舍进出记录
     * 
     * @param recordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysInOutRecordByIds(String[] recordIds);

	public SysInOutRecord selectLastRecordByCode(SysInOutRecord searchRecord);

	public int deleteAllRecord();
}
