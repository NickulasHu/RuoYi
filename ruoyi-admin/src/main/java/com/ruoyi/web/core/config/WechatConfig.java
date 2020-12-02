package com.ruoyi.web.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WechatConfig {
	
    @Value("${wechat.appId}")
    public String appId;

    @Value("${wechat.secret}")
    public String secret;

    @Value("${wechat.token}")
    public String token;
    
    @Value("${wechat.aesKey}")
    public String aesKey;
}
