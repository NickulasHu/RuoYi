package com.ruoyi.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.web.core.config.WechatConfig;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
@Controller
public class InitWxMpConfigStorage extends BaseController
{
	
	@Autowired
	private WechatConfig wechatConfig;
	
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
		
		String appId=wechatConfig.appId;
		
    	System.err.println(appId);
    	
	    wxMpInMemoryConfigStorage.setAppId(wechatConfig.appId);
	    wxMpInMemoryConfigStorage.setSecret(wechatConfig.secret);
	    wxMpInMemoryConfigStorage.setToken(wechatConfig.token);
	    wxMpInMemoryConfigStorage.setAesKey(wechatConfig.aesKey);
	    return wxMpInMemoryConfigStorage;
	}
}
