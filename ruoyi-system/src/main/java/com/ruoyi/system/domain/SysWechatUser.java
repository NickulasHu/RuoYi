package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信用户信息对象 sys_wechat_user
 * 
 * @author hsy
 * @date 2020-12-02
 */
public class SysWechatUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** openId */
    private String openId;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 性别 */
    @Excel(name = "性别")
    private Long sex;

    /** 头像 */
    @Excel(name = "头像")
    private String headimgurl;

    /** unionid */
    @Excel(name = "unionid")
    private String unionid;

    public void setOpenId(String openId) 
    {
        this.openId = openId;
    }

    public String getOpenId() 
    {
        return openId;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setSex(Long sex) 
    {
        this.sex = sex;
    }

    public Long getSex() 
    {
        return sex;
    }
    public void setHeadimgurl(String headimgurl) 
    {
        this.headimgurl = headimgurl;
    }

    public String getHeadimgurl() 
    {
        return headimgurl;
    }
    public void setUnionid(String unionid) 
    {
        this.unionid = unionid;
    }

    public String getUnionid() 
    {
        return unionid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("openId", getOpenId())
            .append("nickName", getNickName())
            .append("userId", getUserId())
            .append("sex", getSex())
            .append("headimgurl", getHeadimgurl())
            .append("unionid", getUnionid())
            .toString();
    }
}
