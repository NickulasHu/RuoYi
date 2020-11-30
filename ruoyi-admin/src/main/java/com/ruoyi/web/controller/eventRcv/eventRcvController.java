package com.ruoyi.web.controller.eventRcv;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 订阅事件接收
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/")
public class eventRcvController
{
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
	    				JSONObject jsonEvent = (JSONObject)eventList.get(i);
	    				
	    			
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
