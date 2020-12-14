package com.ruoyi.common.wechat;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

public class WxMpServiceInstance {
	private WxMpService wxMpService;
	private WxMpConfigStorage wxMpConfigStorage;
	private WxMpMessageRouter wxMpMessageRouter;

	private static WxMpServiceInstance instance = null;

	public static WxMpServiceInstance getInstance() {
		if (instance == null) {
			try {
				instance = new WxMpServiceInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	private WxMpServiceInstance() throws Exception {
		wxMpService = new WxMpServiceImpl();
		wxMpConfigStorage = wxMpConfigStorage();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
		wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
	}

	public WxMpService getWxMpService() {
		return wxMpService;
	}

	public WxMpConfigStorage getWxMpConfigStorage() {
		return wxMpConfigStorage;
	}

	public WxMpMessageRouter getWxMpMessageRouter() {
		return wxMpMessageRouter;
	}
	
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
		
	    wxMpInMemoryConfigStorage.setAppId("wxb46e5c71a795750a");
	    wxMpInMemoryConfigStorage.setSecret("a0adad96625ff3db6e56491d48c98aed");
	   /* wxMpInMemoryConfigStorage.setToken(wechatConfig.token);
	    wxMpInMemoryConfigStorage.setAesKey(wechatConfig.aesKey);*/
	    return wxMpInMemoryConfigStorage;
	}
}
