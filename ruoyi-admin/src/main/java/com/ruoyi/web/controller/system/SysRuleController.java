package com.ruoyi.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysBuilding;
import com.ruoyi.system.domain.SysRule;
import com.ruoyi.system.service.ISysBuildingService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysRuleService;
import com.ruoyi.web.core.config.HikvisionConfig;

/**
 * 【请填写功能名称】Controller
 * 
 * @author hsy
 * @date 2020-11-22
 */
@Controller
@RequestMapping("/system/rule")
public class SysRuleController extends BaseController
{
    private String prefix = "system/rule";

    @Autowired
    private ISysRuleService sysRuleService;
    
    @Autowired
    private ISysDeptService sysDeptService;
    
    @Autowired
    private ISysBuildingService sysBuildingService;
    
    @Autowired
    private HikvisionConfig hikvisionConfig;

    @RequiresPermissions("system:rule:view")
    @GetMapping()
    public String rule()
    {
        return prefix + "/rule";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:rule:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysRule sysRule)
    {
        startPage();
        List<SysRule> list = sysRuleService.selectSysRuleList(sysRule);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:rule:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysRule sysRule)
    {
        List<SysRule> list = sysRuleService.selectSysRuleList(sysRule);
        ExcelUtil<SysRule> util = new ExcelUtil<SysRule>(SysRule.class);
        return util.exportExcel(list, "rule");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:rule:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysRule sysRule)
    {
        return toAjax(sysRuleService.insertSysRule(sysRule));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{ruleId}")
    public String edit(@PathVariable("ruleId") String ruleId, ModelMap mmap)
    {
        SysRule sysRule = sysRuleService.selectSysRuleById(ruleId);
        mmap.put("sysRule", sysRule);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:rule:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysRule sysRule)
    {
        return toAjax(sysRuleService.updateSysRule(sysRule));
    }

    /**
     * 配置规则匹配的楼栋和组织
     */
    @GetMapping("/config/{ruleId}")
    public String config(@PathVariable("ruleId") String ruleId, ModelMap mmap)
    {
        SysRule sysRule = sysRuleService.selectSysRuleById(ruleId);
        //查询所有组织、楼栋
        SysDept sysDept=new SysDept();
        SysBuilding sysBuilding=new SysBuilding();
        List<SysDept> sysDepts=sysDeptService.selectDeptList(sysDept);
        List<SysBuilding> sysBuildings=sysBuildingService.selectSysBuildingList(sysBuilding);
        
        //查询已配置的楼栋和组织
        List<SysDept> configSysDepts=sysDeptService.selectDeptList(sysDept);
        List<SysBuilding> configSysBuildings=sysBuildingService.selectSysBuildingList(sysBuilding);
        
        
        
        mmap.put("sysRule", sysRule);
        mmap.put("sysDepts", sysDepts);
        mmap.put("sysBuildings", sysBuildings);
        return prefix + "/config";
    }
    
    /**
     * 保存规则配置
     */
    @RequiresPermissions("system:rule:edit")
    @Log(title = "规则", businessType = BusinessType.UPDATE)
    @PostMapping("/config")
    @ResponseBody
    public AjaxResult configSave(SysRule sysRule,String dormId,String deptId)
    {
    	System.err.println(dormId);
    	System.err.println(deptId);
        //return toAjax(sysRuleService.updateSysRule(sysRule));
    	return AjaxResult.success("请求已发送");
    }

    /**
     * 删除【请填写功能名称】
     */
    @RequiresPermissions("system:rule:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysRuleService.deleteAllSysRuleByIds());
    }
    
    /**导入用户数据**/
    @RequiresPermissions("system:rule:view")
    @PostMapping("/syncRuleINfo")
    @ResponseBody
    public AjaxResult syncRuleINfo()
    {
    	String mesg =getRuleFromHiKvision();
    	if("success".equals(mesg)) {
    		return AjaxResult.success("请求已发送");
    	}else {
    		return AjaxResult.error(mesg);
    	}
    }

	private String getRuleFromHiKvision() {
		String mesg=StringUtils.EMPTY;
    	ArtemisConfig.host=hikvisionConfig.host;
    	ArtemisConfig.appKey=hikvisionConfig.appKey;
    	ArtemisConfig.appSecret=hikvisionConfig.appSecret;
    	
    	String getSecurityApi = "/artemis" + "/api/dcms/v1/openApiService/record/selectRule"; // 接口路径
    	 @SuppressWarnings("serial")
		Map<String, String> path = new HashMap<String, String>(2) {
    	 {
    		 put("https://", getSecurityApi);
    	 }
    	 };
    	 
    	 JSONObject jsonBody = new JSONObject();
    	 String body = jsonBody.toJSONString();
    	 String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null,null,"application/json");
    	 JSONObject jsonData = JSONObject.parseObject(result);
    	 if("0".equals(jsonData.getString("code"))&&jsonData.getString("data")!=null) {
    		 JSONArray ruleList=JSONArray.parseArray(jsonData.getString("data"));
    		 if(ruleList!=null&&ruleList.size()>0) {
				 for (int i = 0; i < ruleList.size(); i++) {
					 JSONObject jsonRule = (JSONObject)ruleList.get(i);
					 SysRule rule=new SysRule();
					 
					 if(jsonRule.getString("ruleId")!=null) {
						 rule.setRuleId(jsonRule.getString("ruleId"));
					 }
					 
					 if(jsonRule.getString("ruleName")!=null) {
						 rule.setRuleName(jsonRule.getString("ruleName"));
					 }
					 
					 if(jsonRule.getString("inDormStart")!=null) {
						 rule.setInDormStart(jsonRule.getString("inDormStart"));
					 }
					 
					 if(jsonRule.getString("inDormEnd")!=null) {
						 rule.setInDormEnd(jsonRule.getString("inDormEnd"));
					 }
					 
					 if(jsonRule.getString("laterTime")!=null) {
						 rule.setLaterTime(jsonRule.getString("laterTime"));
					 }
					 
					 if(jsonRule.getString("period")!=null) {
						 rule.setPeriod(jsonRule.getString("period"));
					 }
					 
					 rule.setCreateBy(ShiroUtils.getLoginName());
					 SysRule oldRule=sysRuleService.selectSysRuleById(rule.getRuleId());
					 if(oldRule!=null) {
						 sysRuleService.updateSysRule(oldRule);
					 }else {
						 sysRuleService.insertSysRule(rule);
					 }
				}
			 }
    		 mesg="success";
    	 }else {
    		 mesg=jsonData.getString("msg");
    	 }
    	return mesg;
	}
}
