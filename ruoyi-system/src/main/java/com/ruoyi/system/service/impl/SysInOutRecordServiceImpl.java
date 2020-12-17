package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysInOutRecordMapper;
import com.ruoyi.system.domain.SysInOutRecord;
import com.ruoyi.system.service.ISysInOutRecordService;
import com.ruoyi.common.core.text.Convert;

/**
 * 宿舍进出记录Service业务层处理
 * 
 * @author hsy
 * @date 2020-12-07
 */
@Service
public class SysInOutRecordServiceImpl implements ISysInOutRecordService 
{
    @Autowired
    private SysInOutRecordMapper sysInOutRecordMapper;

    /**
     * 查询宿舍进出记录
     * 
     * @param recordId 宿舍进出记录ID
     * @return 宿舍进出记录
     */
    @Override
    public SysInOutRecord selectSysInOutRecordById(String recordId)
    {
        return sysInOutRecordMapper.selectSysInOutRecordById(recordId);
    }

    /**
     * 查询宿舍进出记录列表
     * 
     * @param sysInOutRecord 宿舍进出记录
     * @return 宿舍进出记录
     */
    @Override
    public List<SysInOutRecord> selectSysInOutRecordList(SysInOutRecord sysInOutRecord)
    {
        return sysInOutRecordMapper.selectSysInOutRecordList(sysInOutRecord);
    }

    /**
     * 新增宿舍进出记录
     * 
     * @param sysInOutRecord 宿舍进出记录
     * @return 结果
     */
    @Override
    public int insertSysInOutRecord(SysInOutRecord sysInOutRecord)
    {
        return sysInOutRecordMapper.insertSysInOutRecord(sysInOutRecord);
    }

    /**
     * 修改宿舍进出记录
     * 
     * @param sysInOutRecord 宿舍进出记录
     * @return 结果
     */
    @Override
    public int updateSysInOutRecord(SysInOutRecord sysInOutRecord)
    {
        return sysInOutRecordMapper.updateSysInOutRecord(sysInOutRecord);
    }

    /**
     * 删除宿舍进出记录对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysInOutRecordByIds(String ids)
    {
        return sysInOutRecordMapper.deleteSysInOutRecordByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除宿舍进出记录信息
     * 
     * @param recordId 宿舍进出记录ID
     * @return 结果
     */
    @Override
    public int deleteSysInOutRecordById(String recordId)
    {
        return sysInOutRecordMapper.deleteSysInOutRecordById(recordId);
    }

	@Override
	public SysInOutRecord selectLastRecordByCode(SysInOutRecord searchRecord) {
		// TODO Auto-generated method stub
		return sysInOutRecordMapper.selectLastRecordByCode(searchRecord);
	}

	@Override
	public int deleteAllRecord() {
		return sysInOutRecordMapper.deleteAllRecord();
	}
}
