package com.ruoyi.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
import com.ruoyi.common.config.HikvisionConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.SysBuilding;
import com.ruoyi.system.service.ISysBuildingService;

/**
 * 【请填写功能名称】Controller
 * 
 * @author hsy
 * @date 2020-11-22
 */
@Controller
@RequestMapping("/system/building")
public class SysBuildingController extends BaseController
{
    private String prefix = "system/building";

    @Autowired
    private ISysBuildingService sysBuildingService;
    
    @Autowired
    private HikvisionConfig hikvisionConfig;

    @RequiresPermissions("system:building:view")
    @GetMapping()
    public String building()
    {
        return prefix + "/building";
    }

    /**
     * 查询【请填写功能名称】列表
     */
    @RequiresPermissions("system:building:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysBuilding> list(SysBuilding sysBuilding)
    {
        List<SysBuilding> list = sysBuildingService.selectSysBuildingList(sysBuilding);
        return list;
    }
    
    /**导入用户数据**/
    @RequiresPermissions("system:building:view")
    @PostMapping("/syncBuildINfo")
    @ResponseBody
    public AjaxResult syncBuildINfo()
    {
    	String mesg =getBuildFromHiKvision();
    	if("success".equals(mesg)) {
    		return AjaxResult.success("success");
    	}else {
    		return AjaxResult.error(mesg);
    	}
    }
    
    @Async
    private String getBuildFromHiKvision() {
    	String mesg=StringUtils.EMPTY;
    	ArtemisConfig.host=hikvisionConfig.host;
    	ArtemisConfig.appKey=hikvisionConfig.appKey;
    	ArtemisConfig.appSecret=hikvisionConfig.appSecret;
    	
    	String getSecurityApi = "/artemis" + "/api/dcms/v1/openApiService/dorm/dormTree"; // 接口路径
    	 @SuppressWarnings("serial")
		Map<String, String> path = new HashMap<String, String>(2) {
    	 {
    		 put("https://", getSecurityApi);
    	 }
    	 };
    	 
    	 JSONObject jsonBody = new JSONObject();
    	 jsonBody.put("pageNo",1);
    	 jsonBody.put("pageSize",1000);
    	 String body = jsonBody.toJSONString();
    	 String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null,null,"application/json");
    	 JSONObject jsonData = JSONObject.parseObject(result);
    	 
    	 if("0".equals(jsonData.getString("code"))&&jsonData.getString("data")!=null) {
    		 jsonData=JSONObject.parseObject(jsonData.getString("data"));
    		 saveBuildFromJsonObject(jsonData);
    		 mesg="success";
    	 }else {
    		 mesg=jsonData.getString("msg");
    	 }
    	return mesg;
    }

    private void saveBuildFromJsonObject(JSONObject jsonData) {
		 SysBuilding build=new SysBuilding();
		 
		 if(jsonData.getString("dormId")!=null) {
			 build.setDormId(jsonData.getString("dormId"));
		 }
		 
		 if(jsonData.getString("dormCode")!=null) {
			 build.setDormCode(jsonData.getString("dormCode"));
		 }
		 
		 if(jsonData.getString("dormName")!=null) {
			 build.setDormName(jsonData.getString("dormName"));
		 }
		 
		 if(jsonData.getString("dormLevel")!=null) {
			 build.setDormLevel(Long.parseLong(jsonData.getString("dormLevel")));
		 }
		 
		 if(jsonData.getString("dormSortId")!=null) {
			 build.setDormSortId(Long.parseLong(jsonData.getString("dormSortId")));
		 }
		 
		 if(jsonData.getString("dormNum")!=null) {
			 build.setDormNum(Long.parseLong(jsonData.getString("dormNum")));
		 }
		 
		 if(jsonData.getString("parentDormId")!=null) {
			 build.setParentDormId(jsonData.getString("parentDormId"));
		 }
		
		 if(jsonData.getString("parentDormCode")!=null) {
			 build.setParentDormCode(jsonData.getString("parentDormCode"));
		 }
		 
		 build.setCreateBy(ShiroUtils.getLoginName());
		 SysBuilding oldBuild=sysBuildingService.selectSysBuildingById(build.getDormId());
		 if(oldBuild!=null) {
			 sysBuildingService.updateSysBuilding(oldBuild);
		 }else {
			 sysBuildingService.insertSysBuilding(build);
		 }
		 
		 if(jsonData.getString("children")!=null) {
			 JSONArray subBuildList=JSONArray.parseArray(jsonData.getString("children"));
			 if(subBuildList!=null&&subBuildList.size()>0) {
				 for (int i = 0; i < subBuildList.size(); i++) {
					 JSONObject jsonSubBuild = (JSONObject)subBuildList.get(i);
					 saveBuildFromJsonObject(jsonSubBuild);
				 }
			 }
		 }
	}

	/**
     * 导出【请填写功能名称】列表
     */
    @RequiresPermissions("system:building:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysBuilding sysBuilding)
    {
        List<SysBuilding> list = sysBuildingService.selectSysBuildingList(sysBuilding);
        ExcelUtil<SysBuilding> util = new ExcelUtil<SysBuilding>(SysBuilding.class);
        return util.exportExcel(list, "building");
    }

    /**
     * 新增【请填写功能名称】
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") String parentId, ModelMap mmap)
    {
    	mmap.put("building", sysBuildingService.selectSysBuildingById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存【请填写功能名称】
     */
    @RequiresPermissions("system:building:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysBuilding sysBuilding)
    {
        return toAjax(sysBuildingService.insertSysBuilding(sysBuilding));
    }

    /**
     * 修改【请填写功能名称】
     */
    @GetMapping("/edit/{dormId}")
    public String edit(@PathVariable("dormId") String dormId, ModelMap mmap)
    {
        SysBuilding sysBuilding = sysBuildingService.selectSysBuildingById(dormId);
        mmap.put("sysBuilding", sysBuilding);
        return prefix + "/edit";
    }

    /**
     * 修改保存【请填写功能名称】
     */
    @RequiresPermissions("system:building:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysBuilding sysBuilding)
    {
        return toAjax(sysBuildingService.updateSysBuilding(sysBuilding));
    }

    /**
     * 删除子建筑
     */
    @RequiresPermissions("system:building:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @GetMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String id)
    {
        return toAjax(sysBuildingService.deleteAllSysBuilding());
    }
}
