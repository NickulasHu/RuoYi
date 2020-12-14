package com.ruoyi.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SysInOutRecord;
import com.ruoyi.system.service.ISysInOutRecordService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 宿舍进出记录Controller
 * 
 * @author hsy
 * @date 2020-12-07
 */
@Controller
@RequestMapping("/system/record")
public class SysInOutRecordController extends BaseController
{
    private String prefix = "system/record";

    @Autowired
    private ISysInOutRecordService sysInOutRecordService;

    @RequiresPermissions("system:record:view")
    @GetMapping()
    public String record()
    {
        return prefix + "/record";
    }

    /**
     * 查询宿舍进出记录列表
     */
    @RequiresPermissions("system:record:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysInOutRecord sysInOutRecord)
    {
        startPage();
        List<SysInOutRecord> list = sysInOutRecordService.selectSysInOutRecordList(sysInOutRecord);
        return getDataTable(list);
    }

    /**
     * 导出宿舍进出记录列表
     */
    @RequiresPermissions("system:record:export")
    @Log(title = "宿舍进出记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysInOutRecord sysInOutRecord)
    {
        List<SysInOutRecord> list = sysInOutRecordService.selectSysInOutRecordList(sysInOutRecord);
        ExcelUtil<SysInOutRecord> util = new ExcelUtil<SysInOutRecord>(SysInOutRecord.class);
        return util.exportExcel(list, "record");
    }

    /**
     * 新增宿舍进出记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存宿舍进出记录
     */
    @RequiresPermissions("system:record:add")
    @Log(title = "宿舍进出记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysInOutRecord sysInOutRecord)
    {
        return toAjax(sysInOutRecordService.insertSysInOutRecord(sysInOutRecord));
    }

    /**
     * 修改宿舍进出记录
     */
    @GetMapping("/edit/{recordId}")
    public String edit(@PathVariable("recordId") String recordId, ModelMap mmap)
    {
        SysInOutRecord sysInOutRecord = sysInOutRecordService.selectSysInOutRecordById(recordId);
        mmap.put("sysInOutRecord", sysInOutRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存宿舍进出记录
     */
    @RequiresPermissions("system:record:edit")
    @Log(title = "宿舍进出记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysInOutRecord sysInOutRecord)
    {
        return toAjax(sysInOutRecordService.updateSysInOutRecord(sysInOutRecord));
    }

    /**
     * 删除宿舍进出记录
     */
    @RequiresPermissions("system:record:remove")
    @Log(title = "宿舍进出记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysInOutRecordService.deleteSysInOutRecordByIds(ids));
    }
}
