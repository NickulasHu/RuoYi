package com.ruoyi.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
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
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.service.ISysDeptService;

/**
 * 部门信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController
{
    private String prefix = "system/dept";
    
    @Autowired
    private HikvisionConfig hikvisionConfig;

    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("system:dept:view")
    @GetMapping()
    public String dept()
    {
        return prefix + "/dept";
    }

    @RequiresPermissions("system:dept:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SysDept> list(SysDept dept)
    {
        List<SysDept> deptList = deptService.selectDeptList(dept);
        return deptList;
    }

    /**
     * 新增部门
     */
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptById(parentId));
        return prefix + "/add";
    }

    /**
     * 新增保存部门
     */
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dept:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysDept dept)
    {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        dept.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(deptService.insertDept(dept));
    }

    /**
     * 修改
     */
    @GetMapping("/edit/{deptId}")
    public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap)
    {
        SysDept dept = deptService.selectDeptById(deptId);
        if (StringUtils.isNotNull(dept) && 100L == deptId)
        {
            dept.setParentName("无");
        }
        mmap.put("dept", dept);
        return prefix + "/edit";
    }

    /**
     * 保存
     */
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dept:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysDept dept)
    {
        if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept)))
        {
            return error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        else if (dept.getParentId().equals(dept.getDeptId()))
        {
            return error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
                && deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0)
        {
            return AjaxResult.error("该部门包含未停用的子部门！");
        }
        dept.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(deptService.updateDept(dept));
    }
    
    /**导入用户数据**/
    @RequiresPermissions("system:dept:view")
    @PostMapping("/syncDeptINfo")
    @ResponseBody
    public AjaxResult syncDeptINfoINfo()
    {
    	String mesg =getDeptFromHiKvision();
    	if("success".equals(mesg)) {
    		return AjaxResult.success("请求已发送");
    	}else {
    		return AjaxResult.error(mesg);
    	}
    }
    
    @Async
    private String getDeptFromHiKvision() {
    	String mesg=StringUtils.EMPTY;
    	ArtemisConfig.host=hikvisionConfig.host;
    	ArtemisConfig.appKey=hikvisionConfig.appKey;
    	ArtemisConfig.appSecret=hikvisionConfig.appSecret;
    	
    	String getSecurityApi = "/artemis" + "/api/resource/v1/org/orgList"; // 接口路径
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
    	 if(result==null) return "";
    	 JSONObject jsonData = JSONObject.parseObject(result);
    	 if("0".equals(jsonData.getString("code"))&&jsonData.getString("data")!=null) {
    		 jsonData=JSONObject.parseObject(jsonData.getString("data"));
    		 if(jsonData.getString("list")!=null) {
    			 JSONArray deptList=JSONArray.parseArray(jsonData.getString("list"));
    			 if(deptList!=null&&deptList.size()>0) {
    				 for (int i = 0; i < deptList.size(); i++) {
    					 JSONObject jsonDept = (JSONObject)deptList.get(i);
    					 SysDept dept=new SysDept();
    					 
    					 if(jsonDept.getString("orgIndexCode")!=null) {
    						 if("root000000".equals(jsonDept.getString("orgIndexCode"))) {
    							 continue;
    						 }else {
    							 dept.setDeptId(IdUtils.getLongFormString(jsonDept.getString("orgIndexCode")));
    							 dept.setOrgIndexCode(jsonDept.getString("orgIndexCode"));
    						 }
    					 }
    					 
    					 if(jsonDept.getString("orgName")!=null) {
    						 dept.setDeptName(jsonDept.getString("orgName"));
    					 }
    					 
    					 if(jsonDept.getString("parentOrgIndexCode")!=null) {
    						 if("root000000".equals(jsonDept.getString("parentOrgIndexCode"))) {
    							 dept.setParentId((long) 100);
    						 }else {
    							 dept.setParentId(IdUtils.getLongFormString(jsonDept.getString("parentOrgIndexCode"))); 
    						 }
    					 }
    					 dept.setCreateBy(ShiroUtils.getLoginName());
    					
    					 SysDept oldDept=deptService.selectDeptById(dept.getDeptId());
    					 if(oldDept!=null) {
    						 deptService.updateDept(oldDept);
    					 }else {
    						 deptService.insertDept(dept);
    					 }
    					 
					}
    			 }
    		 }
    		 mesg="success";
    	 }else {
    		 mesg=jsonData.getString("msg");
    	 }
    	return mesg;
    }

    /**
     * 删除
     */
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dept:remove")
    @GetMapping("/remove/{deptId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("deptId") Long deptId)
    {
        if (deptService.selectDeptCount(deptId) > 0)
        {
            return AjaxResult.warn("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId))
        {
            return AjaxResult.warn("部门存在用户,不允许删除");
        }
        return toAjax(deptService.deleteDeptById(deptId));
    }
    
    /**批量删除某组织下的组织**/
    @RequiresPermissions("system:dept:remove")
    @PostMapping("/removeChildDept")
    @ResponseBody
    public AjaxResult removeChildDept(Long parentId)
    {
    	SysDept parentDept=deptService.selectDeptById(parentId);
    	if(parentDept==null) return AjaxResult.error("改组织不存在");
    	
    	List<SysDept> depts=deptService.selectChildrenDeptById(parentDept.getDeptId());
    	if(depts==null) return AjaxResult.error("该组织无下级组织");
    	
    	for (int i = 0; i < depts.size(); i++) {
    		if (deptService.checkDeptExistUser(depts.get(i).getDeptId()))
            {
                return AjaxResult.warn("部门存在用户,不允许删除");
            }
    		deptService.deleteDeptById(depts.get(i).getDeptId());
		}
    			
    	return AjaxResult.success("操作已完成");
    }

    /**
     * 校验部门名称
     */
    @PostMapping("/checkDeptNameUnique")
    @ResponseBody
    public String checkDeptNameUnique(SysDept dept)
    {
        return deptService.checkDeptNameUnique(dept);
    }

    /**
     * 选择部门树
     * 
     * @param deptId 部门ID
     * @param excludeId 排除ID
     */
    @GetMapping(value = { "/selectDeptTree/{deptId}", "/selectDeptTree/{deptId}/{excludeId}" })
    public String selectDeptTree(@PathVariable("deptId") Long deptId,
            @PathVariable(value = "excludeId", required = false) String excludeId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptById(deptId));
        mmap.put("excludeId", excludeId);
        return prefix + "/tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = deptService.selectDeptTree(new SysDept());
        return ztrees;
    }

    /**
     * 加载部门列表树（排除下级）
     */
    @GetMapping("/treeData/{excludeId}")
    @ResponseBody
    public List<Ztree> treeDataExcludeChild(@PathVariable(value = "excludeId", required = false) Long excludeId)
    {
        SysDept dept = new SysDept();
        dept.setDeptId(excludeId);
        List<Ztree> ztrees = deptService.selectDeptTreeExcludeChild(dept);
        return ztrees;
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/roleDeptTreeData")
    @ResponseBody
    public List<Ztree> deptTreeData(SysRole role)
    {
        List<Ztree> ztrees = deptService.roleDeptTreeData(role);
        return ztrees;
    }
}
