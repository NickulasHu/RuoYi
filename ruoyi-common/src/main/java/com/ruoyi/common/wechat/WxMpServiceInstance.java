package com.ruoyi.common.wechat;
import org.springframework.stereotype.Component;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@Component
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
		
	    wxMpInMemoryConfigStorage.setAppId("wx34c892e588853d99");
	    wxMpInMemoryConfigStorage.setSecret("354178fc64010d64ae8f3f2e07d0008f");
	    return wxMpInMemoryConfigStorage;
	}
}
