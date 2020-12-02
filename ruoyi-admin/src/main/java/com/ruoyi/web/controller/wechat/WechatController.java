package com.ruoyi.web.controller.wechat;

import org.springframework.stereotype.Controller;

import com.ruoyi.common.core.controller.BaseController;

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
