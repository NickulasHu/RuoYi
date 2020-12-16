package com.ruoyi.quartz.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.ruoyi.common.config.HikvisionConfig;
import com.ruoyi.common.config.WechatConfig;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.wechat.WxMpServiceInstance;
import com.ruoyi.system.domain.SysInOutRecord;
import com.ruoyi.system.domain.SysRule;
import com.ruoyi.system.domain.SysWechatUser;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysInOutRecordService;
import com.ruoyi.system.service.ISysRuleService;
import com.ruoyi.system.service.ISysUserDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.system.service.ISysWechatUserService;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * 定时任务调度测试
 * 
 * @author ruoyi
 */
@Component("ryTask")
public class RyTask
{
	@Autowired
    private HikvisionConfig hikvisionConfig;
	
	@Autowired
	private WechatConfig wechatConfig;
	
	@Autowired
	private ISysRuleService ruleService;
	
	@Autowired
	private ISysDeptService deptService;
	
	@Autowired
	private ISysUserService userService;
	
	@Autowired
	private ISysWechatUserService wechatUserService;
	
	@Autowired
	private ISysInOutRecordService inOutRecordService;
	
	@Autowired
	private ISysUserDeptService userDeptService;
	
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
    {
        System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params)
    {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }
    
    public void syncRecordAndSendMesg(String ruleId) {
    	syncRecord(ruleId);
    	sendMesg(ruleId);
    }
    
	private void syncRecord(String ruleId) {
		SysRule rule = ruleService.selectSysRuleById(ruleId);
    	String endDate=rule.getLaterTime();
    	String startDate=rule.getInDormStart();
    	
    	SimpleDateFormat getShortDate = new SimpleDateFormat("yyyy-MM-dd");
    	String strOfToday = getShortDate.format(DateUtils.getNowDate());
    	endDate=strOfToday + " "+endDate;
    	startDate=strOfToday + " "+startDate;
    	
    	//查询异常学生信息，并发送模板消息
    	ArtemisConfig.host=hikvisionConfig.host;
    	ArtemisConfig.appKey=hikvisionConfig.appKey;
    	ArtemisConfig.appSecret=hikvisionConfig.appSecret;
    	
    	String getSecurityApi = "/artemis" + "/api/dcms/v1/openApiService/record/queryinOutInfo"; // 接口路径
	   	 @SuppressWarnings("serial")
			Map<String, String> path = new HashMap<String, String>(2) {
	   	 {
	   		 put("https://", getSecurityApi);
	   	 }
	   	 };
    	
    	//同步所有班级学生进出记录
    	//获取所有班级
    	List<SysDept> allClass = deptService.selectChildDeptList(new SysDept());
    	if(allClass!=null && allClass.size()>0) {
    		for (int i = 0; i < allClass.size(); i++) {
				//获取班级中所有同学
    			List<SysUser> calssMates=userService.selectClassMates(allClass.get(i).getDeptId());
    			if(calssMates!=null && calssMates.size()>0) {
    				for (int j = 0; j < calssMates.size(); j++) {
    					 JSONObject jsonBody = new JSONObject();
    			    	 jsonBody.put("endDate",endDate);
    			    	 jsonBody.put("startDate",startDate);
    			    	 jsonBody.put("pageNum",1);
    			    	 jsonBody.put("pageSize",50);
    			    	 jsonBody.put("orgId",calssMates.get(j).getOrgId());
    			    	 jsonBody.put("studentCode",calssMates.get(j).getJobNo());
    			    	 jsonBody.put("userId","admin");
    			    	 
    			    	 String body = jsonBody.toJSONString();
    			    	 String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null,null,"application/json");
    			    	 JSONObject jsonData = JSONObject.parseObject(result);
    			    	 if("0".equals(jsonData.getString("code"))&&jsonData.getString("data")!=null) {
    			    		 jsonData=JSONObject.parseObject(jsonData.getString("data"));
    			    		 if(jsonData.getString("records")!=null) {
    			    			 JSONArray records=JSONArray.parseArray(jsonData.getString("records"));
    			    			 if(records!=null&&records.size()>0) {
    			    				 for (int k = 0; k < records.size(); k++) {
    			    					JSONObject jsonRecord = (JSONObject)records.get(k);
										SysInOutRecord record = new SysInOutRecord();
										String recordId=jsonRecord.getString("alarmInfoId");
										if(StringUtils.isEmpty(recordId)) {
											record.setRecordId(recordId);
											record.setStudentCode(jsonRecord.getString("studentCode"));
											record.setEntryType(jsonRecord.getString("entryType"));
											record.setStudentName(jsonRecord.getString("studentName"));
											record.setSex(jsonRecord.getString("sexName"));
											record.setOrgId(jsonRecord.getString("orgId"));
											record.setDormId(jsonRecord.getString("dormId"));
											record.setAlarmTime(jsonRecord.getString("alarmTimeStr"));
											if(inOutRecordService.selectSysInOutRecordById(recordId)!=null) continue;
											inOutRecordService.insertSysInOutRecord(record);
										}
									}
    			    			 }
    			    		 }
    			    	 }
					}
    			}
			}
    	}
	}
	
	private void sendMesg(String ruleId) {
		SysRule rule = ruleService.selectSysRuleById(ruleId);
		List<SysDept> allDepts=deptService.selectChildDeptList(new SysDept());
		
    	String endDate=rule.getLaterTime();
    	String startDate=rule.getInDormStart();
    	SimpleDateFormat getShortDate = new SimpleDateFormat("yyyy-MM-dd");
    	String strOfToday = getShortDate.format(DateUtils.getNowDate());
    	endDate=strOfToday + " "+endDate;
    	startDate=strOfToday + " "+startDate;
		
		if(allDepts==null || allDepts.size()==0)return;
		String messagetempId=wechatConfig.templateId;
		WxMpService wxMpService = WxMpServiceInstance.getInstance().getWxMpService();
		for (int i = 0; i < allDepts.size(); i++) {
			List<SysUser> unnormalUser= new ArrayList<SysUser>();
			
			List<SysUser> classMatesHasNoRecord=getClassMateWithOutRecord(allDepts.get(i), startDate, endDate);
			if(classMatesHasNoRecord!=null && classMatesHasNoRecord.size()>0) {
				unnormalUser.addAll(classMatesHasNoRecord);
			}
			
			List<SysUser> classMateGoOut=getClassMateGoOut(allDepts.get(i), startDate, endDate);
			if(classMateGoOut!=null && classMateGoOut.size()>0) {
				unnormalUser.addAll(classMateGoOut);
			}
			//拼消息
			if(unnormalUser.size()==0) continue;
			StringBuffer studentInfo=new StringBuffer();
			for (int j = 0; j < unnormalUser.size(); j++) {
				studentInfo.append(unnormalUser.get(j).getUserName());
				studentInfo.append("、");
			}
			studentInfo.append("归寝异常");
			//调用方法发送
			//查找老师openId，模板ID,
			Long deptId = allDepts.get(i).getDeptId();
			List<Long> useIds = userDeptService.selectUserIdByDeptId(deptId);
			if(useIds==null ||useIds.size()==0)continue;
			for (int j = 0; j < useIds.size(); j++) {
				String openId=wechatUserService.selectSysWechatUserByUserId(useIds.get(j));
				SysWechatUser wechatUser=wechatUserService.selectSysWechatUserById(openId);
			
				WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
												      .toUser(openId)
												      .templateId(messagetempId)
												      .url(" ")
												      .build();
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("first", "尊敬的"+wechatUser.getUserName()+"老师,您的学生："+studentInfo.toString()));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword1", "湖北省宜昌市凝德总部"));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword2", "2020年12月14日15:00"));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword3", "250000元"));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("keyword4", "15213038372"));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("remark", "感谢您的使用"));
			    try {
					String msgId = wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private  List<SysUser> getClassMateWithOutRecord(SysDept sysDept, String startDate, String endDate){
		SysInOutRecord searchRecord = new SysInOutRecord();
		searchRecord.setOrgId(sysDept.getOrgIndexCode());
		searchRecord.setStartTime(startDate);
		searchRecord.setEndTime(endDate);
		
		List<SysInOutRecord> thisDeptUserRecord=inOutRecordService.selectSysInOutRecordList(searchRecord);
		SysUser searchUser = new SysUser();
		searchUser.setOrgId(sysDept.getOrgIndexCode());
		List<SysUser> classMates=userService.selectUserList(searchUser);
		HashMap<String, SysUser> classMateMap=new HashMap<String, SysUser>();
		
		if(classMates==null || classMates.size()==0) return null;
		for (int j = 0; j < classMates.size(); j++) {
			classMateMap.put(classMates.get(j).getJobNo(), classMates.get(j));
		}
		
		//剔除有记录的，找到没有记录的
		List<SysUser> classMatesHasNoRecord=new ArrayList<SysUser>();
		for (int j = 0; j < thisDeptUserRecord.size(); j++) {
			if(classMateMap.containsKey(thisDeptUserRecord.get(j).getStudentCode())) {
				classMateMap.remove(thisDeptUserRecord.get(j).getStudentCode());
			}
		}
		
		if(classMateMap.values().size()>0) {
			classMatesHasNoRecord=new ArrayList<SysUser>(classMateMap.values());
		}
		
		return classMatesHasNoRecord;
	}
	
	//找到有出去的,查最后一条
	private List<SysUser> getClassMateGoOut(SysDept sysDept, String startDate, String endDate){
		SysInOutRecord searchRecord = new SysInOutRecord();
		searchRecord.setOrgId(sysDept.getOrgIndexCode());
		searchRecord.setStartTime(startDate);
		searchRecord.setEndTime(endDate);
		searchRecord.setEntryType("out");
		
		List<SysUser> userGoOut=new ArrayList<SysUser>();
		List<SysInOutRecord> userGooutRecord=inOutRecordService.selectSysInOutRecordList(searchRecord);
		if(userGooutRecord!= null && userGooutRecord.size()>0) {
			for (int j = 0; j < userGooutRecord.size(); j++) {
				String studentCode=userGooutRecord.get(j).getStudentCode();
				searchRecord.setEntryType("");
				searchRecord.setStudentCode(studentCode);
				SysInOutRecord lastRecord= inOutRecordService.selectLastRecordByCode(searchRecord);
				if(lastRecord!=null && lastRecord.getEntryType().equals("out")) {
					SysUser item = userService.selectUserByJobNo(lastRecord.getStudentCode());
					userGoOut.add(item);
				}
			}
		}
		
		return userGoOut;
	}
}
