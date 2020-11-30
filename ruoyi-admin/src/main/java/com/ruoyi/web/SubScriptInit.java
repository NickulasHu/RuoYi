package com.ruoyi.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.ruoyi.web.core.config.HikvisionConfig;

@Component
public class SubScriptInit implements CommandLineRunner {
	@Autowired
    private HikvisionConfig hikvisionConfig;
	
	@Override
	public void run(String... args) throws Exception {
		new Thread(){
			public void run() {
				/*unsubscribe();
				subscribe();*/
			}
		}.start();
	}
	
	public void unsubscribe(){
        ArtemisConfig.host=hikvisionConfig.host;
    	ArtemisConfig.appKey=hikvisionConfig.appKey;
    	ArtemisConfig.appSecret=hikvisionConfig.appSecret;
    	
    	String getSecurityApi = "/artemis" + "/api/eventService/v1/eventUnSubscriptionByEventTypes"; // 接口路径
    	 @SuppressWarnings("serial")
		Map<String, String> path = new HashMap<String, String>(2) {
    	 {
    		 put("https://", getSecurityApi);
    	 }
    	 };
    	 
    	 JSONObject jsonBody = new JSONObject();
    	 jsonBody.put("eventTypes",new int[]{197106,197105});
    	 
    	 String body = jsonBody.toJSONString();
    	 ArtemisHttpUtil.doPostStringArtemis(path, body, null,null,"application/json");
    }

	public void subscribe(){
        ArtemisConfig.host=hikvisionConfig.host;
    	ArtemisConfig.appKey=hikvisionConfig.appKey;
    	ArtemisConfig.appSecret=hikvisionConfig.appSecret;
    	
    	String getSecurityApi = "/artemis" + "/api/eventService/v1/eventSubscriptionByEventTypes"; // 接口路径
    	 @SuppressWarnings("serial")
		Map<String, String> path = new HashMap<String, String>(2) {
    	 {
    		 put("https://", getSecurityApi);
    	 }
    	 };
    	 
    	 JSONObject jsonBody = new JSONObject();
    	 jsonBody.put("eventTypes",new int[]{197106,197105});
    	 jsonBody.put("eventDest","http://"+hikvisionConfig.localIp+"/eventRcv");
    	 jsonBody.put("subType",0);
    	 jsonBody.put("eventLvl",new int[]{2});
    	 String body = jsonBody.toJSONString();
    	 ArtemisHttpUtil.doPostStringArtemis(path, body, null,null,"application/json");
    }
}
