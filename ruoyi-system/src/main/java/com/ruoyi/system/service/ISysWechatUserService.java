package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SysWechatUser;

/**
 * 微信用户信息Service接口
 * 
 * @author hsy
 * @date 2020-12-02
 */
public interface ISysWechatUserService 
{
    /**
     * 查询微信用户信息
     * 
     * @param openId 微信用户信息ID
     * @return 微信用户信息
     */
    public SysWechatUser selectSysWechatUserById(String openId);

    /**
     * 查询微信用户信息列表
     * 
     * @param sysWechatUser 微信用户信息
     * @return 微信用户信息集合
     */
    public List<SysWechatUser> selectSysWechatUserList(SysWechatUser sysWechatUser);

    /**
     * 新增微信用户信息
     * 
     * @param sysWechatUser 微信用户信息
     * @return 结果
     */
    public int insertSysWechatUser(SysWechatUser sysWechatUser);

    /**
     * 修改微信用户信息
     * 
     * @param sysWechatUser 微信用户信息
     * @return 结果
     */
    public int updateSysWechatUser(SysWechatUser sysWechatUser);

    /**
     * 批量删除微信用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSysWechatUserByIds(String ids);

    /**
     * 删除微信用户信息信息
     * 
     * @param openId 微信用户信息ID
     * @return 结果
     */
    public int deleteSysWechatUserById(String openId);

	public int deleteAllSysWechatUserByIds();

	public String selectSysWechatUserByUserId(Long userId);

	public int relieveRelationByIds(String wechatUserId);
}
