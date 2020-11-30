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
import com.ruoyi.system.domain.SysEventnotify;
import com.ruoyi.system.service.ISysEventnotifyService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 异常事件Controller
 * 
 * @author hsy
 * @date 2020-11-30
 */
@Controller
@RequestMapping("/system/eventnotify")
public class SysEventnotifyController extends BaseController
{
    private String prefix = "system/eventnotify";

    @Autowired
    private ISysEventnotifyService sysEventnotifyService;

    @RequiresPermissions("system:eventnotify:view")
    @GetMapping()
    public String eventnotify()
    {
        return prefix + "/eventnotify";
    }

    /**
     * 查询异常事件列表
     */
    @RequiresPermissions("system:eventnotify:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysEventnotify sysEventnotify)
    {
        startPage();
        List<SysEventnotify> list = sysEventnotifyService.selectSysEventnotifyList(sysEventnotify);
        return getDataTable(list);
    }

    /**
     * 导出异常事件列表
     */
    @RequiresPermissions("system:eventnotify:export")
    @Log(title = "异常事件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysEventnotify sysEventnotify)
    {
        List<SysEventnotify> list = sysEventnotifyService.selectSysEventnotifyList(sysEventnotify);
        ExcelUtil<SysEventnotify> util = new ExcelUtil<SysEventnotify>(SysEventnotify.class);
        return util.exportExcel(list, "eventnotify");
    }

    /**
     * 新增异常事件
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存异常事件
     */
    @RequiresPermissions("system:eventnotify:add")
    @Log(title = "异常事件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysEventnotify sysEventnotify)
    {
        return toAjax(sysEventnotifyService.insertSysEventnotify(sysEventnotify));
    }

    /**
     * 修改异常事件
     */
    @GetMapping("/edit/{eventId}")
    public String edit(@PathVariable("eventId") String eventId, ModelMap mmap)
    {
        SysEventnotify sysEventnotify = sysEventnotifyService.selectSysEventnotifyById(eventId);
        mmap.put("sysEventnotify", sysEventnotify);
        return prefix + "/edit";
    }

    /**
     * 修改保存异常事件
     */
    @RequiresPermissions("system:eventnotify:edit")
    @Log(title = "异常事件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysEventnotify sysEventnotify)
    {
        return toAjax(sysEventnotifyService.updateSysEventnotify(sysEventnotify));
    }

    /**
     * 删除异常事件
     */
    @RequiresPermissions("system:eventnotify:remove")
    @Log(title = "异常事件", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysEventnotifyService.deleteSysEventnotifyByIds(ids));
    }
}
