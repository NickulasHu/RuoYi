package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysWechatUserMapper;
import com.ruoyi.system.domain.SysWechatUser;
import com.ruoyi.system.service.ISysWechatUserService;
import com.ruoyi.common.core.text.Convert;

/**
 * 微信用户信息Service业务层处理
 * 
 * @author hsy
 * @date 2020-12-02
 */
@Service
public class SysWechatUserServiceImpl implements ISysWechatUserService 
{
    @Autowired
    private SysWechatUserMapper sysWechatUserMapper;

    /**
     * 查询微信用户信息
     * 
     * @param openId 微信用户信息ID
     * @return 微信用户信息
     */
    @Override
    public SysWechatUser selectSysWechatUserById(String openId)
    {
        return sysWechatUserMapper.selectSysWechatUserById(openId);
    }

    /**
     * 查询微信用户信息列表
     * 
     * @param sysWechatUser 微信用户信息
     * @return 微信用户信息
     */
    @Override
    public List<SysWechatUser> selectSysWechatUserList(SysWechatUser sysWechatUser)
    {
        return sysWechatUserMapper.selectSysWechatUserList(sysWechatUser);
    }

    /**
     * 新增微信用户信息
     * 
     * @param sysWechatUser 微信用户信息
     * @return 结果
     */
    @Override
    public int insertSysWechatUser(SysWechatUser sysWechatUser)
    {
        return sysWechatUserMapper.insertSysWechatUser(sysWechatUser);
    }

    /**
     * 修改微信用户信息
     * 
     * @param sysWechatUser 微信用户信息
     * @return 结果
     */
    @Override
    public int updateSysWechatUser(SysWechatUser sysWechatUser)
    {
        return sysWechatUserMapper.updateSysWechatUser(sysWechatUser);
    }

    /**
     * 删除微信用户信息对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteSysWechatUserByIds(String ids)
    {
        return sysWechatUserMapper.deleteSysWechatUserByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除微信用户信息信息
     * 
     * @param openId 微信用户信息ID
     * @return 结果
     */
    @Override
    public int deleteSysWechatUserById(String openId)
    {
        return sysWechatUserMapper.deleteSysWechatUserById(openId);
    }

	@Override
	public int deleteAllSysWechatUserByIds() {
		return sysWechatUserMapper.deleteAllSysWechatUserByIds();
	}

	@Override
	public String selectSysWechatUserByUserId(Long userId) {
		return sysWechatUserMapper.selectSysWechatUserByUserId(userId);
	}

	@Override
	public int relieveRelationByIds(String wechatUserId) {
		// TODO Auto-generated method stub
		return sysWechatUserMapper.relieveRelationByIds(wechatUserId);
	}

}
