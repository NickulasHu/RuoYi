package com.ruoyi.web.controller.wechat;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.system.service.ISysUserService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;

@Controller
public class WechatController extends BaseController{

	/* @Autowired
	 private ISysUserService sysUserService;
	 
	 @Autowired
	 private WxMpService wxmpService;
	 
	 @Autowired
	 WxMpUserService wxmpUserService;
	 
	 @RequestMapping(value = "/wechat/getUserOPenIdList", method = { RequestMethod.GET,RequestMethod.POST })
	 public void saveSubscribeInfo() throws Exception {
		 
		 ArrayList<WxMpUserList> openIds=new ArrayList<>();
		 String accessToken=wxmpService.getAccessToken();
		 
		 boolean getAlluserId=false;
		 while (!getAlluserId) {
			 WxMpUserList userList=new WxMpUserList();
			 
			 
			 openIds.add(userList);
			 if(userList.getOpenids().size()<10000) {
				 getAlluserId=true;
			 }
		 }
	 }
	 */
	 
}
