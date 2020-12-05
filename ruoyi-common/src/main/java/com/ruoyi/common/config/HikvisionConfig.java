package com.ruoyi.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikvisionConfig {
	
    @Value("${hikvision.host}")
    public String host;

    @Value("${hikvision.appKey}")
    public String appKey;

    @Value("${hikvision.appSecret}")
    public String appSecret;
    
    @Value("${hikvision.localIp}")
    public String localIp;
}
