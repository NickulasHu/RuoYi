package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.CxSelect;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.wechat.WxMpServiceInstance;
import com.ruoyi.system.domain.SysWechatUser;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.ISysWechatUserService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

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
    
    @Autowired
    private ISysDeptService deptService;
    
    @Autowired
    private ISysUserService userService;
    
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
    
    /**删除所有用户数据**/
    @RequiresPermissions("system:wechatUser:remove")
    @PostMapping("/removeAll")
    @ResponseBody
    public AjaxResult removeAll()
    {
    	return toAjax(sysWechatUserService.deleteAllSysWechatUserByIds());
    }
    
    /**解绑关系**/
    @RequiresPermissions("system:wechatUser:edit")
    @PostMapping("/relieveRelation")
    @ResponseBody
    public AjaxResult relieveRelation(String wechatUserId)
    {
    	return toAjax(sysWechatUserService.relieveRelationByIds(wechatUserId));
    }
    
    /**同步用户数据**/
    @RequiresPermissions("system:wechatUser:view")
    @PostMapping("/syncUserINfo")
    @ResponseBody
    public AjaxResult syncUserINfo()
    {
    	getWechatUserFromTencet();
    	//如果请求参数出错，需报出异常
    	return AjaxResult.success("success");
    }

    @Async
	private void getWechatUserFromTencet() {
		// 获取openIds
    	WxMpService wxMpService = WxMpServiceInstance.getInstance().getWxMpService();
    	try {
			 WxMpUserList openIdsData=wxMpService.getUserService().userList(null);
			 List<String> openids=openIdsData.getOpenids();
			 if(openids!=null&&openids.size()>0) {
				 for (int i = 0; i < openids.size(); i++) {
					 WxMpUser wxMpUser=wxMpService.getUserService().userInfo(openids.get(i));
					 
					 if(wxMpUser!=null) {
						//先查询，是否已存在，存在则更新
						 SysWechatUser orignalUser=sysWechatUserService.selectSysWechatUserById(wxMpUser.getOpenId());
						 if(orignalUser!=null) {
							 orignalUser.setNickName(wxMpUser.getNickname());
							 orignalUser.setHeadimgurl(wxMpUser.getHeadImgUrl());
							 sysWechatUserService.updateSysWechatUser(orignalUser);
						 }else {
							//不存在则创建
							 SysWechatUser item=new SysWechatUser();
							 item.setOpenId(wxMpUser.getOpenId());
							 item.setNickName(wxMpUser.getNickname());
							 item.setHeadimgurl(wxMpUser.getHeadImgUrl());
							 item.setSex(wxMpUser.getSex());
							 item.setUnionid(wxMpUser.getUnionId());
							 sysWechatUserService.insertSysWechatUser(item);
						 }
					 }
				}
			 }

		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * 配置管理者对应的组织
     */
    @GetMapping("/config/{openId}")
    public String config(@PathVariable("openId") String openId, ModelMap mmap)
    {
    	SysWechatUser sysWechatUser = sysWechatUserService.selectSysWechatUserById(openId);
        
      //查询所有二级联动数据
        List<CxSelect> cxList = new ArrayList<CxSelect>();
        cxList=getDeptAndUserData();
        
        mmap.put("data", JSON.toJSON(cxList));
        mmap.put("sysWechatUser", sysWechatUser);
        return prefix + "/config";
    }
    
    private List<CxSelect> getDeptAndUserData() 
    {
        List<CxSelect> cxList = new ArrayList<CxSelect>();
        
        SysDept sysDept=new SysDept();
        List<SysDept> sysDepts=deptService.selectDeptList(sysDept);
        if(sysDepts==null||sysDepts.size()==0)return null;
        for (int i = 0; i < sysDepts.size(); i++) {
        	SysDept sysDeptItem= sysDepts.get(i);
			CxSelect item = new CxSelect();
			List<CxSelect> subList;
			
			item.setN(sysDeptItem.getDeptName());
			item.setV(Long.toString(sysDeptItem.getDeptId()));
			
			List<SysUser> uList = userService.selectClassMates(sysDeptItem.getDeptId());
			if(uList==null||uList.size()==0) {
				subList = new ArrayList<CxSelect>();
			}else {
				subList = new ArrayList<CxSelect>();
				for (int j = 0; j < uList.size(); j++) {
					SysUser sysUser=uList.get(j);
					CxSelect userItem = new CxSelect();
					userItem.setN(sysUser.getUserName());
					userItem.setV(Long.toString(sysUser.getUserId()));
					subList.add(userItem);
				}
				item.setS(subList);
			}
			cxList.add(item);
		}
		return cxList;
	}

	/**
     * 保存规则配置
     */
    @RequiresPermissions("system:wechatUser:edit")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PostMapping("/config")
    @ResponseBody
    public AjaxResult configSave(SysWechatUser sysWechatUser)
    {
    	if(sysWechatUser.getUserId()==null)return AjaxResult.error("failed");
    	SysUser user=userService.selectUserById(sysWechatUser.getUserId());
		String openId= sysWechatUserService.selectSysWechatUserByUserId(sysWechatUser.getUserId());
		if(user==null || !StringUtils.isEmpty(openId))return AjaxResult.error("failed");
		
		sysWechatUser.setUserName(user.getUserName());
    	if(sysWechatUserService.updateSysWechatUser(sysWechatUser)>0) {
    		return AjaxResult.success("success");
    	}else {
    		return AjaxResult.error("failed");
    	}
    }
}
