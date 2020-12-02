package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.shiro.service.SysPasswordService;
import com.ruoyi.system.domain.SysUserDept;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserDeptService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.web.core.config.HikvisionConfig;

/**
 * 用户信息
 * 
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController
{
    private String prefix = "system/user";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private SysPasswordService passwordService;
    
    @Autowired
    private HikvisionConfig hikvisionConfig;
    
    @Autowired
    private ISysUserDeptService sysUserDeptService;
    
    @Autowired
    private ISysDeptService deptService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user()
    {
        return prefix + "/user";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysUser user)
    {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        return util.importTemplateExcel("用户数据");
    }
    
    /**同步用户数据**/
    @RequiresPermissions("system:user:view")
    @PostMapping("/syncUserINfo")
    @ResponseBody
    public AjaxResult syncUserINfo(String deptId,String deptName)
    {
    	//删除所有用户，除了管理员
    	DeleteAllUsers();
    	getUserFromHiKvision(deptId,deptName);
    	//如果请求参数出错，需报出异常
    	boolean result=false;
    	if(result) {
    		return AjaxResult.error("请检查组织名称是否一致");
    	}else {
    		return AjaxResult.success("success");
    	}
    }
    
    /**同步前删除原有人员**/
    private void DeleteAllUsers() {
    	try {
			@SuppressWarnings("unused")
			int result=userService.deleteAllUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Async
    private String getUserFromHiKvision(String deptId,String deptName) {
    	String mesg=StringUtils.EMPTY;
    	ArtemisConfig.host=hikvisionConfig.host;
    	ArtemisConfig.appKey=hikvisionConfig.appKey;
    	ArtemisConfig.appSecret=hikvisionConfig.appSecret;
    	
    	String getSecurityApi="/artemis" + "/api/resource/v2/person/personList";
    	 @SuppressWarnings("serial")
		 Map<String, String> path = new HashMap<String, String>(2) {
    	 {
    		 put("https://", getSecurityApi);
    	 }
    	 };
    	 
    	 JSONObject jsonBody = new JSONObject();
    	 boolean getAll=false;
    	 int pageIndex=1;
    	 
    	 while(!getAll) {
    		 jsonBody.put("pageNo",pageIndex++);
        	 jsonBody.put("pageSize",1000);
        	 String body = jsonBody.toJSONString();
    		 List<SysUser> sysUsers=new ArrayList<SysUser>();
    		 
    		 String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null,null,"application/json");
        	 JSONObject jsonData = JSONObject.parseObject(result);
        	 if("0".equals(jsonData.getString("code"))&&jsonData.getString("data")!=null) {
        		 jsonData=JSONObject.parseObject(jsonData.getString("data"));
        		 if(jsonData.getString("list")!=null) {
        			 JSONArray deptList=JSONArray.parseArray(jsonData.getString("list"));
        			 
        			 if(deptList==null||deptList.size()<1000) {
        				 getAll=true;
        			 }
        			 
        			 if(deptList!=null&&deptList.size()>0) {
        				 for (int i = 0; i < deptList.size(); i++) {
        					 JSONObject jsonUser = (JSONObject)deptList.get(i);
        					 SysUser user=new SysUser();
        					 
        					 if(jsonUser.getString("personId")!=null) {
        						 user.setUserId(IdUtils.getLongFormString(jsonUser.getString("personId"))); 
        					 }
        					 
        					 if(jsonUser.getString("personName")!=null) {
        						 user.setUserName(jsonUser.getString("personName")); 
        						 user.setLoginName(jsonUser.getString("personName")+jsonUser.getString("personId")); 
        					 }
        					 user.setPassword("000000");
        					 if(jsonUser.getString("orgIndexCode")!=null) {
        						 user.setDeptId(IdUtils.getLongFormString(jsonUser.getString("orgIndexCode")));
        					 }
        					 
        					user.setSalt(ShiroUtils.randomSalt());
    			            user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
    			            user.setCreateBy(ShiroUtils.getLoginName());
    			            sysUsers.add(user);
    					}
        			 }
        		 }
        	 }else {
        		 mesg+=jsonData.getString("msg");
        	 }
        	 
        	String operName = ShiroUtils.getSysUser().getLoginName();
        	mesg+=userService.importUser(sysUsers,true,operName);
    	 }
    	
    	return mesg;
    }
    
    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("roles", roleService.selectRoleAll().stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SysUser user)
    {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user.getLoginName())))
        {
            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return error("新增用户'" + user.getLoginName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return error("新增用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        mmap.put("user", userService.selectUserById(userId));
        mmap.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SysUser user)
    {
        userService.checkUserAllowed(user);
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return error("修改用户'" + user.getLoginName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return error("修改用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(userService.updateUser(user));
    }
    
    /**
     * 配置管理者对应的组织
     */
    @GetMapping("/config/{userId}")
    public String config(@PathVariable("userId") Long userId, ModelMap mmap)
    {
    	SysUser sysUser = userService.selectUserById(userId);
        //查询所有组织
        SysDept sysDept=new SysDept();
        List<SysDept> sysDepts=deptService.selectDeptList(sysDept);
        
        //查询已配置的组织
        List<Long> configDeptIdList=sysUserDeptService.selectConfigDeptIds(userId);
        String configDeptIds="";
        if(configDeptIdList.size()>0) {
        	for (int i = 0; i < configDeptIdList.size(); i++) {
        		if(i==0)configDeptIds+=configDeptIdList.get(i).toString();
        		configDeptIds += ","+ configDeptIdList.get(i).toString();
			}
        }
        
        mmap.put("sysUser", sysUser);
        mmap.put("sysDepts", sysDepts);
        mmap.put("configDeptIds", configDeptIds);
        return prefix + "/config";
    }
    
    /**
     * 保存规则配置
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @PostMapping("/config")
    @ResponseBody
    public AjaxResult configSave(SysUser sysUser,String deptId)
    {
    	Long userId=sysUser.getUserId();
    	//删除此规则原关系项
    	sysUserDeptService.deleteConfigDepts(userId);
    	//保存新的关系项
    	if(!StringUtils.isEmpty(deptId)) {
    		String[] deptIds=deptId.split(",");
        	
        	if(deptIds.length>0) {
        		for (int i = 0; i < deptIds.length; i++) {
    				SysUserDept item=new SysUserDept();
    				item.setUserDeptId(IdUtils.randomUUID());
    				item.setUserId(userId);
    				item.setDeptId(Long.parseLong(deptIds[i]));
    				item.setCreateBy(ShiroUtils.getLoginName());
    				sysUserDeptService.insertSysUserDept(item);
    			}
        	}
    	}
    	
    	return AjaxResult.success("请求已发送");
    }

    @RequiresPermissions("system:user:resetPwd")
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SysUser user)
    {
        userService.checkUserAllowed(user);
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        if (userService.resetUserPwd(user) > 0)
        {
            if (ShiroUtils.getUserId().longValue() == user.getUserId().longValue())
            {
                ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
            }
            return success();
        }
        return error();
    }

    /**
     * 进入授权角色页
     */
    @GetMapping("/authRole/{userId}")
    public String authRole(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        SysUser user = userService.selectUserById(userId);
        // 获取用户所属的角色列表
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        mmap.put("user", user);
        mmap.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return prefix + "/authRole";
    }

    /**
     * 用户授权角色
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PostMapping("/authRole/insertAuthRole")
    @ResponseBody
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds)
    {
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(userService.deleteUserByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(SysUser user)
    {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SysUser user)
    {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(SysUser user)
    {
        return userService.checkEmailUnique(user);
    }

    /**
     * 用户状态修改
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:user:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(SysUser user)
    {
        userService.checkUserAllowed(user);
        return toAjax(userService.changeStatus(user));
    }
}