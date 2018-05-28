package com.tzg.entitys.kff.message;

import java.io.Serializable;

import com.tzg.common.utils.DateUtil;

public class KFFMessage implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3293107342589234046L;
	/**
     * messageId
     */ 	
	private java.lang.Integer messageId;
    /**
     * 消息类型：1-关注；2-点赞；3-评论；4-赞赏；5-评论被回复；6-上榜单；7-奖励token
     */ 	
	private java.lang.Integer type;
    /**
     * 状态：0-删除；1-有效
     */ 	
	private java.lang.Integer status;
    /**
     * 状态：1-未读；2-已读
     */ 	
	private java.lang.Integer state;
    /**
     * createTime
     */ 	
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
    /**
     * updateTime
     */ 	
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;
    /**
     * userId 消息接受人id
     */ 	
	private java.lang.Integer userId;
	
	/**
	 * 消息发送人头像
	 */
	private java.lang.Integer senderUserId;
	
	//返给前端用 消息发送方icon，默认头像
	private java.lang.String senderUserIcon = "/upload/avatars/avatar.png";
    /**
     * 标题
     */ 	
	private java.lang.String title;
    /**
     * 内容
     */ 	
	private java.lang.String content;
    /**
     * 跳转用到的信息
     */ 	
	private java.lang.String jumpInfo;

	


	public java.lang.Integer getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(java.lang.Integer senderUserId) {
		this.senderUserId = senderUserId;
	}

	public java.lang.String getSenderUserIcon() {
		return senderUserIcon;
	}

	public void setSenderUserIcon(java.lang.String senderUserIcon) {
		this.senderUserIcon = senderUserIcon;
	}

	public void setMessageId(java.lang.Integer value) {
		this.messageId = value;
	}
	
	public java.lang.Integer getMessageId() {
		return this.messageId;
	}
	
	
	
	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	
	public java.lang.Integer getState() {
		return state;
	}

	public void setState(java.lang.Integer state) {
		this.state = state;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public java.lang.String getCreateTimeStr() {
		return this.createTimeStr;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public java.lang.String getUpdateTimeStr() {
		return this.updateTimeStr;
	}
	
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setContent(java.lang.String value) {
		this.content = value;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setJumpInfo(java.lang.String value) {
		this.jumpInfo = value;
	}
	
	public java.lang.String getJumpInfo() {
		return this.jumpInfo;
	}

	
}

