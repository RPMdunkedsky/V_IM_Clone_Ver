package com.ruoyi.vim.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * 聊天信息对象 im_message
 * 
 * @author 乐天
 * @since 2022-01-25
 */
public class ImMessage implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 接收人 */
    @Excel(name = "接收人")
    private Long toId;

    /** 发送人 */
    @Excel(name = "发送人")
    private Long fromId;

    /** 发送时间 */
    @Excel(name = "发送时间")
    private Long sendTime;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 读状态 */
    @Excel(name = "读状态")
    private String readStatus;



    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setToId(Long toId) 
    {
        this.toId = toId;
    }

    public Long getToId() 
    {
        return toId;
    }
    public void setFromId(Long fromId) 
    {
        this.fromId = fromId;
    }

    public Long getFromId() 
    {
        return fromId;
    }
    public void setSendTime(Long sendTime) 
    {
        this.sendTime = sendTime;
    }

    public Long getSendTime() 
    {
        return sendTime;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setReadStatus(String readStatus) 
    {
        this.readStatus = readStatus;
    }

    public String getReadStatus() 
    {
        return readStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("toId", getToId())
            .append("fromId", getFromId())
            .append("sendTime", getSendTime())
            .append("content", getContent())
            .append("type", getType())
            .append("readStatus", getReadStatus())
            .toString();
    }
}
