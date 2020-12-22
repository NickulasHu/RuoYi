package com.ruoyi.system.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 异常事件对象 sys_eventnotify
 * 
 * @author hsy
 * @date 2020-11-30
 */
public class SysEventnotify extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String eventId;

    /** 类型 */
    @Excel(name = "类型")
    private Long eventType;

    /** 发生时间 */
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date happenTime;

    /** 发生地点 */
    @Excel(name = "发生地点")
    private String srcName;

    /** 触发人卡号 */
    @Excel(name = "触发人卡号")
    private String extEventCardNo;

    /** 触发人ID */
    @Excel(name = "触发人ID")
    private Long extEventPersonNo;
    
    /** 体温温度 */
    @Excel(name = "体温温度")
    private String temp;

    public void setEventId(String eventId) 
    {
        this.eventId = eventId;
    }

    public String getEventId() 
    {
        return eventId;
    }
    public void setEventType(Long eventType) 
    {
        this.eventType = eventType;
    }

    public Long getEventType() 
    {
        return eventType;
    }
    public void setHappenTime(Date happenTime) 
    {
        this.happenTime = happenTime;
    }

    public Date getHappenTime() 
    {
        return happenTime;
    }
    public void setSrcName(String srcName) 
    {
        this.srcName = srcName;
    }

    public String getSrcName() 
    {
        return srcName;
    }
    public void setExtEventCardNo(String extEventCardNo) 
    {
        this.extEventCardNo = extEventCardNo;
    }

    public String getExtEventCardNo() 
    {
        return extEventCardNo;
    }
    public void setExtEventPersonNo(Long extEventPersonNo) 
    {
        this.extEventPersonNo = extEventPersonNo;
    }

    public Long getExtEventPersonNo() 
    {
        return extEventPersonNo;
    }

    public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	@Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("eventId", getEventId())
            .append("eventType", getEventType())
            .append("happenTime", getHappenTime())
            .append("srcName", getSrcName())
            .append("extEventCardNo", getExtEventCardNo())
            .append("extEventPersonNo", getExtEventPersonNo())
            .toString();
    }
}
