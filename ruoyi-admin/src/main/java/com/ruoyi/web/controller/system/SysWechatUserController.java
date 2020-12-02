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
import com.ruoyi.system.domain.SysWechatUser;
import com.ruoyi.system.service.ISysWechatUserService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信用户信息Controller
 * 
 * @author hsy
 * @date 2020-12-02
 */
@Controller
@RequestMapping("/system/wechatUser")
public class SysWechatUserController extends BaseController
{
    private String prefix = "system/wechatUser";

    @Autowired
    private ISysWechatUserService sysWechatUserService;

    @RequiresPermissions("system:wechatUser:view")
    @GetMapping()
    public String wechatUser()
    {
        return prefix + "/wechatUser";
    }

    /**
     * 查询微信用户信息列表
     */
    @RequiresPermissions("system:wechatUser:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysWechatUser sysWechatUser)
    {
        startPage();
        List<SysWechatUser> list = sysWechatUserService.selectSysWechatUserList(sysWechatUser);
        return getDataTable(list);
    }

    /**
     * 导出微信用户信息列表
     */
    @RequiresPermissions("system:wechatUser:export")
    @Log(title = "微信用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysWechatUser sysWechatUser)
    {
        List<SysWechatUser> list = sysWechatUserService.selectSysWechatUserList(sysWechatUser);
        ExcelUtil<SysWechatUser> util = new ExcelUtil<SysWechatUser>(SysWechatUser.class);
        return util.exportExcel(list, "wechatUser");
    }

    /**
     * 新增微信用户信息
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存微信用户信息
     */
    @RequiresPermissions("system:wechatUser:add")
    @Log(title = "微信用户信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysWechatUser sysWechatUser)
    {
        return toAjax(sysWechatUserService.insertSysWechatUser(sysWechatUser));
    }

    /**
     * 修改微信用户信息
     */
    @GetMapping("/edit/{openId}")
    public String edit(@PathVariable("openId") String openId, ModelMap mmap)
    {
        SysWechatUser sysWechatUser = sysWechatUserService.selectSysWechatUserById(openId);
        mmap.put("sysWechatUser", sysWechatUser);
        return prefix + "/edit";
    }

    /**
     * 修改保存微信用户信息
     */
    @RequiresPermissions("system:wechatUser:edit")
    @Log(title = "微信用户信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysWechatUser sysWechatUser)
    {
        return toAjax(sysWechatUserService.updateSysWechatUser(sysWechatUser));
    }

    /**
     * 删除微信用户信息
     */
    @RequiresPermissions("system:wechatUser:remove")
    @Log(title = "微信用户信息", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysWechatUserService.deleteSysWechatUserByIds(ids));
    }
}
