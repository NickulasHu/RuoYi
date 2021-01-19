package com.ruoyi.web.controller.eventRcv;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.config.WechatConfig;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.common.wechat.WxMpServiceInstance;
import com.ruoyi.system.domain.SysEventnotify;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysEventnotifyService;
import com.ruoyi.system.service.ISysUserDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.ISysWechatUserService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

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
	
	@Autowired
	private WechatConfig wechatConfig;
	
	@Autowired
	private ISysWechatUserService wechatUserService;
	
	@Autowired
	private ISysUserService userService;
	
	@Autowired
	private ISysUserDeptService userDeptService;
	
	@Autowired
	private ISysDeptService deptService;
	
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
	    		if(eventList!=null && eventList.size()>0) {
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
	    					 Date date = new Date();
	    					 eventnotify.setHappenTime(date);
	    				 }
	    				 
	    				 if(jsonEvent.getString("srcName")!=null) {
	    					 eventnotify.setSrcName(jsonEvent.getString("srcName"));
	    				 }
	    				
	    				 if(jsondata.getString("ExtEventCardNo")!=null) {
	    					 eventnotify.setExtEventCardNo(jsondata.getString("ExtEventCardNo"));
	    				 }
	    				
	    				 if(jsondata.getString("ExtEventPersonNo")!=null) {
	    					 eventnotify.setExtEventPersonNo(IdUtils.getLongFormString(jsondata.getString("ExtEventPersonNo")));
	    				 }
	    				 
	    				 if(jsondata.getString("ExtTemp")!=null) {
	    					 JSONObject jsonExtTemp=JSONObject.parseObject(jsondata.getString("ExtTemp"));
	    					 if(jsonExtTemp.getString("temp")!=null) {
		    					 eventnotify.setTemp(jsonExtTemp.getString("temp"));
		    				 }
	    				 }
	    				 sysEventNotifyService.insertSysEventnotify(eventnotify);
	    				 sendEventMessage(eventnotify);
	    			}
	    		}
	    	}
	    }
    }
	
	private void sendEventMessage(SysEventnotify eventnotify) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(eventnotify.getHappenTime());
		SysUser user;
		String userName="未知";
		String userDept="未知";
		String tempStr=eventnotify.getTemp().substring(0, 3)+" °C";
		if(eventnotify.getExtEventPersonNo()!=null) {
			user = userService.selectUserById(eventnotify.getExtEventPersonNo());
			if(user!=null) {
				userName = user.getUserName();
				SysDept userdept=deptService.selectDeptById(user.getDeptId());
				if(userdept!=null) {
					userDept=userdept.getDeptName();
				}
			}
		}
		
		String messagetempId=wechatConfig.templateIdTwo;
		WxMpService wxMpService = WxMpServiceInstance.getInstance().getWxMpService();
		
		//获取防疫管理组ID
		Long deptId = deptService.selectDeptByName("疫情管控");
		List<Long> useIds = userDeptService.selectUserIdByDeptId(deptId);
		if(useIds==null ||useIds.size()==0)return;
		for (int j = 0; j < useIds.size(); j++) {
			String openId=wechatUserService.selectSysWechatUserByUserId(useIds.get(j));
			if(openId==null)continue;
			//SysWechatUser wechatUser=wechatUserService.selectSysWechatUserById(openId);
			WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
											      .toUser(openId)
											      .templateId(messagetempId)
											      .build();
			templateMessage.addWxMpTemplateData(new WxMpTemplateData("first", "您好，刚检测到人员体温异常，请及时处理！"));
			templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword1", userName));
			templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword2", tempStr));
			templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword3", dateString));
			templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword4", eventnotify.getSrcName()));
			templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword5", userDept));
			templateMessage.addWxMpTemplateData(new WxMpTemplateData("remark", "感谢您的使用"));
		    try {
				wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
			} catch (WxErrorException e) {
				e.printStackTrace();
			}
		}
	}
}
