package com.ruoyi.quartz.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ruoyi.common.utils.uuid.IdUtils;
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
	private static final Logger log = LoggerFactory.getLogger(RyTask.class);
	
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

    public void ryNoParams() throws ParseException
    {
        System.out.println("执行无参方法");
        //从海康接口获取校门考勤记录
        //发送消息给班主任 
        synSchoolGateRecord();
        sendSchoolGateMesg();
    }
    
    public void syncRecordAndSendMesg(String ruleId) throws ParseException {
    	syncRecord(ruleId);
    	sendMesg(ruleId);
    }
    
	private void syncRecord(String ruleId) throws ParseException {
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
	   	//同步前删除以前的记录
	   	String recordType = "dormitory";
	   	inOutRecordService.deleteAllRecord(recordType);
    	//获取所有班级
    	List<SysDept> allClass = deptService.selectChildDeptList(new SysDept());
    	if(allClass!=null && allClass.size()>0) {
    		for (int i = 0; i < allClass.size(); i++) {
				//获取班级中所有同学
    			List<SysUser> calssMates=userService.selectClassMates(allClass.get(i).getDeptId());
    			if(calssMates!=null && calssMates.size()>0) {
    				for (int j = 0; j < calssMates.size(); j++) {
    					 JSONObject jsonBody = new JSONObject();
    					 jsonBody.put("dormId","-1");
    			    	 jsonBody.put("endDate",endDate);
    			    	 jsonBody.put("startDate",startDate);
    			    	 jsonBody.put("pageNum",1);
    			    	 jsonBody.put("pageSize",50);
    			    	 jsonBody.put("orgId",calssMates.get(j).getOrgId());
    			    	 jsonBody.put("studentCode",calssMates.get(j).getJobNo());
    			    	 jsonBody.put("userId","admin");
    			    	 
    			    	 String body = jsonBody.toJSONString();
    			    	 String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null,null,"application/json");
    			    	 if(result==null) return;
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
											recordId = IdUtils.randomUUID();
										}
										if(inOutRecordService.selectSysInOutRecordById(recordId)!=null) continue;
										
										if(jsonRecord.getString("alarmTime")!=null) {
											boolean isTime = isAttendanceTime(jsonRecord.getString("alarmTime"),startDate,endDate);
											if(!isTime) continue;
										}
										
										record.setAlarmTime(jsonRecord.getString("alarmTime"));
										record.setRecordId(recordId);
										record.setStudentCode(jsonRecord.getString("studentCode"));
										record.setEntryType(jsonRecord.getString("entryType"));
										record.setStudentName(jsonRecord.getString("studentName"));
										record.setSex(jsonRecord.getString("sexName"));
										record.setOrgId(jsonRecord.getString("orgId"));
										record.setDormId(jsonRecord.getString("dormId"));
										record.setRecordType(recordType);
										
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
	
	private boolean isAttendanceTime(String alarmTime, String startDate, String endDate) throws ParseException {
		boolean result= false;
		if(StringUtils.length(alarmTime)>0) {
			alarmTime = alarmTime.replace("T"," ");
			alarmTime = alarmTime.substring(0,19);
		}
		SimpleDateFormat normalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date alarmDate = normalDate.parse(alarmTime); 
		Date stDate = normalDate.parse(alarmTime);
		Date enDate = normalDate.parse(alarmTime);
		if(alarmDate.compareTo(stDate)>0 && enDate.compareTo(alarmDate)>0) {
			result = true;
		}
		
		return result;
	}

	private void sendMesg(String ruleId) throws ParseException {
		SysRule rule = ruleService.selectSysRuleById(ruleId);
		List<SysDept> allDepts=deptService.selectChildDeptList(new SysDept());
		
    	String normalEndDate =rule.getLaterTime();
    	String normalStartDate=rule.getInDormStart();
    	SimpleDateFormat getShortDate = new SimpleDateFormat("yyyy-MM-dd");
    	String strOfToday = getShortDate.format(DateUtils.getNowDate());
    	normalEndDate =strOfToday + " "+normalEndDate ;
    	normalStartDate=strOfToday + " "+normalStartDate;
    	
    	//日期字符串转date
    	SimpleDateFormat normalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date endDate = normalDate.parse(normalEndDate ); 
    	Date startDate= normalDate.parse(normalStartDate); 
    	
    	//date转特定格式的日期字符串
    	SimpleDateFormat requiredDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    	String finalEndDate = requiredDate.format(endDate);
    	String finalStartDate = requiredDate.format(startDate);
		
		if(allDepts==null || allDepts.size()==0)return;
		String messagetempId=wechatConfig.templateIdOne;
		WxMpService wxMpService = WxMpServiceInstance.getInstance().getWxMpService();
		for (int i = 0; i < allDepts.size(); i++) {
			List<SysUser> unnormalUser= new ArrayList<SysUser>();
			
			List<SysUser> classMatesHasNoRecord=getClassMateWithOutRecord(allDepts.get(i), finalStartDate, finalEndDate, "dormitory");
			if(classMatesHasNoRecord!=null && classMatesHasNoRecord.size()>0) {
				unnormalUser.addAll(classMatesHasNoRecord);
			}
			
			List<SysUser> classMateGoOut=getClassMateGoOut(allDepts.get(i), finalStartDate, finalEndDate, "dormitory");
			if(classMateGoOut!=null && classMateGoOut.size()>0) {
				unnormalUser.addAll(classMateGoOut);
			}
			//拼消息
			if(unnormalUser.size()==0) continue;
			StringBuffer studentInfo=new StringBuffer();
			for (int j = 0; j < unnormalUser.size(); j++) {
				studentInfo.append(unnormalUser.get(j).getUserName());
				if(j != unnormalUser.size()-1) {
					studentInfo.append("、");
				}
			}
			
			//调用方法发送
			//查找老师openId，模板ID,
			Long deptId = allDepts.get(i).getDeptId();
			SysDept currentDepte=deptService.selectDeptById(deptId);
			List<Long> useIds = userDeptService.selectUserIdByDeptId(deptId);
			if(useIds==null ||useIds.size()==0)continue;
			for (int j = 0; j < useIds.size(); j++) {
				String openId=wechatUserService.selectSysWechatUserByUserId(useIds.get(j));
				if(openId==null)continue;
				SysWechatUser wechatUser=wechatUserService.selectSysWechatUserById(openId);
				WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
												      .toUser(openId)
												      .templateId(messagetempId)
												      .build();
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("first", "您好，尊敬的"+wechatUser.getUserName()+"老师！"));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("date", strOfToday));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("class", currentDepte.getDeptName()));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("name", studentInfo.toString()));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("info", "归寝异常！"));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("remark", "感谢您的使用"));
			    try {
					wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private  List<SysUser> getClassMateWithOutRecord(SysDept sysDept, String startDate, String endDate, String recordType){
		SysInOutRecord searchRecord = new SysInOutRecord();
		searchRecord.setOrgId(sysDept.getOrgIndexCode());
		searchRecord.setStartTime(startDate);
		searchRecord.setEndTime(endDate);
		searchRecord.setRecordType(recordType);
		
		List<SysInOutRecord> thisDeptUserRecord=inOutRecordService.selectSysInOutRecordList(searchRecord);
		List<SysUser> classMates=userService.selectClassMates(sysDept.getDeptId());
		HashMap<String, SysUser> classMateMap=new HashMap<String, SysUser>();
		
		if(classMates==null || classMates.size()==0) return null;
		for (int j = 0; j < classMates.size(); j++) {
			classMateMap.put(classMates.get(j).getJobNo(), classMates.get(j));
		}
		
		//剔除有记录的，找到没有记录的
		List<SysUser> classMatesHasNoRecord=new ArrayList<SysUser>();
		if(thisDeptUserRecord!=null && thisDeptUserRecord.size()>0) {
			for (int j = 0; j < thisDeptUserRecord.size(); j++) {
				if(classMateMap.containsKey(thisDeptUserRecord.get(j).getStudentCode())) {
					classMateMap.remove(thisDeptUserRecord.get(j).getStudentCode());
				}
			}
		}
		
		if(classMateMap.values().size()>0) {
			classMatesHasNoRecord=new ArrayList<SysUser>(classMateMap.values());
		}
		
		return classMatesHasNoRecord;
	}
	
	//找到有出去的,查最后一条
	private List<SysUser> getClassMateGoOut(SysDept sysDept, String startDate, String endDate, String recordType){
		SysInOutRecord searchRecord = new SysInOutRecord();
		searchRecord.setOrgId(sysDept.getOrgIndexCode());
		searchRecord.setStartTime(startDate);
		searchRecord.setEndTime(endDate);
		searchRecord.setEntryType("out");
		searchRecord.setRecordType(recordType);
		
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
	
	private void synSchoolGateRecord() throws ParseException {
    	SimpleDateFormat getShortDate = new SimpleDateFormat("yyyy-MM-dd");
    	String strOfToday = getShortDate.format(DateUtils.getNowDate());
    	String normalEndDate = strOfToday + " 18:30:00";
    	String normalStartDate = strOfToday + " 08:00:00";
    	
    	//日期字符串转date
    	SimpleDateFormat normalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date endDate = normalDate.parse(normalEndDate); 
    	Date startDate= normalDate.parse(normalStartDate); 
    	
    	//date转特定格式的日期字符串
    	SimpleDateFormat requiredDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    	String finalEndDate = requiredDate.format(endDate);
    	String finalStartDate = requiredDate.format(startDate);
    	
    	//查询异常学生信息，并发送模板消息
    	ArtemisConfig.host=hikvisionConfig.host;
    	ArtemisConfig.appKey=hikvisionConfig.appKey;
    	ArtemisConfig.appSecret=hikvisionConfig.appSecret;
    	
    	String getSecurityApi = "/artemis/api/acs/v1/door/events"; // 接口路径
	   	 @SuppressWarnings("serial")
			Map<String, String> path = new HashMap<String, String>(2) {
		   	 {
		   		 put("https://", getSecurityApi);
		   	 }
	   	 };
	   	
    	//同步所有班级学生进出记录
	   	//同步前删除以前的记录
	   	String recordType = "schoolGate";
	   	inOutRecordService.deleteAllRecord(recordType);
    	//获取所有班级
    	List<SysDept> allClass = deptService.selectChildDeptList(new SysDept());
    	
    	if(allClass!=null && allClass.size()>0) {
    		for (int i = 0; i < allClass.size(); i++) {
				//获取班级中所有同学
    			List<SysUser> calssMates=userService.selectClassMates(allClass.get(i).getDeptId());
    			if(calssMates!=null && calssMates.size()>0) {
    				for (int j = 0; j < calssMates.size(); j++) {
    					 JSONObject jsonBody = new JSONObject();
    			    	 jsonBody.put("endTime",finalEndDate);
    			    	 jsonBody.put("startTime",finalStartDate);
    			    	 jsonBody.put("personIds",new String[]{calssMates.get(j).getPersonId()});
    			    	 jsonBody.put("pageNo",1);
    			    	 jsonBody.put("pageSize",100);
    			    	 
    			    	 String body = jsonBody.toJSONString();
    			    	 String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null,null,"application/json");
    			    	 if(result==null) return;
    			    	 JSONObject jsonData = JSONObject.parseObject(result);
    			    	 if("0".equals(jsonData.getString("code"))&&jsonData.getString("data")!=null) {
    			    		 jsonData=JSONObject.parseObject(jsonData.getString("data"));
    			    		 if(jsonData.getString("list")!=null) {
    			    			 JSONArray records=JSONArray.parseArray(jsonData.getString("list"));
    			    			 if(records!=null&&records.size()>0) {
    			    				 for (int k = 0; k < records.size(); k++) {
    			    					JSONObject jsonRecord = (JSONObject)records.get(k);
										SysInOutRecord record = new SysInOutRecord();
										record.setRecordId(IdUtils.randomUUID());
										
										if(jsonRecord.getString("personDetail")!=null) {
											JSONObject personDetailJson = JSONObject.parseObject(jsonRecord.getString("personDetail"));
											record.setStudentCode(personDetailJson.getString("jobNo"));
											record.setStudentName(personDetailJson.getString("personName"));
											record.setSex(personDetailJson.getString("sex"));
										}
										
										if("1".equals(jsonRecord.getString("inAndOutType"))) {
										    record.setEntryType("in");
										} else {
											record.setEntryType("out");
										}
										record.setOrgId(calssMates.get(j).getOrgId());
										record.setDormId(jsonRecord.getString("doorIndexCode"));
										record.setAlarmTime(jsonRecord.getString("eventTime"));
										record.setRecordType(recordType);
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
	
	/**
	 * 查询校门考勤记录，获取没有记录的学生
	 * @throws ParseException 
	 */
	private void sendSchoolGateMesg() throws ParseException {
		List<SysDept> allDepts=deptService.selectChildDeptList(new SysDept());
		
		SimpleDateFormat getShortDate = new SimpleDateFormat("yyyy-MM-dd");
    	String strOfToday = getShortDate.format(DateUtils.getNowDate());
    	String normalEndDate=strOfToday + " 18:30:00";
    	String normalStartDate =strOfToday + " 08:00:00";
    	
    	//日期字符串转date
    	SimpleDateFormat normalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date endDate = normalDate.parse(normalEndDate); 
    	Date startDate= normalDate.parse(normalStartDate); 
    	
    	//date转特定格式的日期字符串
    	SimpleDateFormat requiredDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    	String finalEndDate = requiredDate.format(endDate);
    	String finalStartDate = requiredDate.format(startDate);
    	
		if(allDepts==null || allDepts.size()==0)return;
		String messagetempId=wechatConfig.templateIdOne;
		WxMpService wxMpService = WxMpServiceInstance.getInstance().getWxMpService();
		for (int i = 0; i < allDepts.size(); i++) {
			List<SysUser> unnormalUser= new ArrayList<SysUser>();
			
			List<SysUser> classMatesHasNoRecord=getClassMateWithOutRecord(allDepts.get(i), finalStartDate, finalEndDate, "schoolGate");
			if(classMatesHasNoRecord!=null && classMatesHasNoRecord.size()>0) {
				unnormalUser.addAll(classMatesHasNoRecord);
			}
			
			/*List<SysUser> classMateGoOut=getClassMateGoOut(allDepts.get(i), finalStartDate, finalEndDate, "schoolGate");
			if(classMateGoOut!=null && classMateGoOut.size()>0) {
				unnormalUser.addAll(classMateGoOut);
			}*/
			
			//拼消息
			if(unnormalUser.size()==0) continue;
			StringBuffer studentInfo=new StringBuffer();
			for (int j = 0; j < unnormalUser.size(); j++) {
				studentInfo.append(unnormalUser.get(j).getUserName());
				if(j != unnormalUser.size()-1) {
					studentInfo.append("、");
				}
			}
			
			//调用方法发送
			//查找老师openId，模板ID,
			Long deptId = allDepts.get(i).getDeptId();
			SysDept currentDepte=deptService.selectDeptById(deptId);
			List<Long> useIds = userDeptService.selectUserIdByDeptId(deptId);
			if(useIds==null ||useIds.size()==0)continue;
			for (int j = 0; j < useIds.size(); j++) {
				String openId=wechatUserService.selectSysWechatUserByUserId(useIds.get(j));
				if(openId==null)continue;
				SysWechatUser wechatUser=wechatUserService.selectSysWechatUserById(openId);
				WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
												      .toUser(openId)
												      .templateId(messagetempId)
												      .build();
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("first", "您好，尊敬的"+wechatUser.getUserName()+"老师！"));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("date", strOfToday));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("class", currentDepte.getDeptName()));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("name", studentInfo.toString()));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("info", "返校异常！"));
				templateMessage.addWxMpTemplateData(new WxMpTemplateData("remark", "感谢您的使用"));
			    try {
					wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
				} catch (WxErrorException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
