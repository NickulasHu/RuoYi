package com.ruoyi.web.controller.eventRcv;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.SysEventnotify;
import com.ruoyi.system.service.ISysEventnotifyService;

/**
 * 订阅事件接收
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/")
public class eventRcvController
{
	@Autowired
	private ISysEventnotifyService sysEventNotifyService;
    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     * @throws IOException 
     */
	@ResponseBody
    @RequestMapping(value="eventRcv",method=RequestMethod.POST)
    public void eventRcv(HttpServletRequest request) throws IOException
    {
		BufferedReader br = request.getReader();

	    String str, wholeStr = "";
	    while((str = br.readLine()) != null){
	        wholeStr += str;
		}
	    
	    JSONObject jsonData = JSONObject.parseObject(wholeStr);
	    if(jsonData.getString("params")!=null) {
	    	JSONObject jsonParams=JSONObject.parseObject(jsonData.getString("params"));
	    	if(jsonParams.getString("events")!=null) {
	    		JSONArray eventList=JSONArray.parseArray(jsonParams.getString("events"));
	    		if(eventList!=null&&eventList.size()>0) {
	    			for (int i = 0; i < eventList.size(); i++) {
	    				SysEventnotify eventnotify=new SysEventnotify();
	    				JSONObject jsonEvent = (JSONObject)eventList.get(i);
	    				JSONObject jsondata=JSONObject.parseObject(jsonEvent.getString("data"));
	    				 if(jsonEvent.getString("eventId")!=null) {
	    					 eventnotify.setEventId(jsonEvent.getString("eventId"));
	    				 }
	    				 if(jsonEvent.getString("eventType")!=null) {
	    					 eventnotify.setEventType( Long.parseLong(jsonEvent.getString("eventType")));
	    				 }
	    				
	    				 if(jsonEvent.getString("happenTime")!=null) {
	    					 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 	    				try {
	 							eventnotify.setHappenTime(simpleDateFormat.parse(jsonEvent.getString("happenTime")));
	 						} catch (ParseException e) {
	 							e.printStackTrace();
	 						}
	    				 }
	    				 
	    				 if(jsonEvent.getString("srcName")!=null) {
	    					 eventnotify.setSrcName(jsonEvent.getString("srcName"));
	    				 }
	    				
	    				 if(jsonEvent.getString("extEventCardNo")!=null) {
	    					 eventnotify.setExtEventCardNo(jsondata.getString("extEventCardNo"));
	    				 }
	    				
	    				 if(jsonEvent.getString("extEventPersonNo")!=null) {
	    					 eventnotify.setExtEventPersonNo(IdUtils.getLongFormString(jsondata.getString("extEventPersonNo")));
	    				 }
	    				 sysEventNotifyService.insertSysEventnotify(eventnotify);
	    			}
	    		}
	    	}
	    }
	    System.out.println(wholeStr);
        try
        {
            
        }
        catch (Exception e)
        {
        }
    }
}
