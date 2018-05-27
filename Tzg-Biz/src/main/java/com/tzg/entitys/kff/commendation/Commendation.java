package com.tzg.entitys.kff.commendation;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Commendation implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8642138308866094633L;
	/**
     * commendationId
     */ 	
	private java.lang.Integer commendationId;
    /**
     * status
     */ 	
	private java.lang.Integer status = 1;
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
     * 捐赠人用户ID
     */ 	
	private java.lang.Integer sendUserId;
    /**
     * 发送人头像url
     */ 	
	private java.lang.String sendUserIcon;
    /**
     * 接受人id
     */ 	
	private java.lang.Integer receiveUserId;
    /**
     * 帖子ID
     */ 	
	private java.lang.Integer postId;
    /**
     * 项目ID
     */ 	
	private java.lang.Integer projectId;
    /**
     * 帖子类型：1-评测；2-讨论；3-文章
     */ 	
	private java.lang.Integer postType;
    /**
     * 捐赠金额
     */ 	
	private java.math.BigDecimal amount;

	
	public void setCommendationId(java.lang.Integer value) {
		this.commendationId = value;
	}
	
	public java.lang.Integer getCommendationId() {
		return this.commendationId;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
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
	
	public void setSendUserId(java.lang.Integer value) {
		this.sendUserId = value;
	}
	
	public java.lang.Integer getSendUserId() {
		return this.sendUserId;
	}
	
	public void setSendUserIcon(java.lang.String value) {
		this.sendUserIcon = value;
	}
	
	public java.lang.String getSendUserIcon() {
		return this.sendUserIcon;
	}
	
	public void setReceiveUserId(java.lang.Integer value) {
		this.receiveUserId = value;
	}
	
	public java.lang.Integer getReceiveUserId() {
		return this.receiveUserId;
	}
	
	public void setPostId(java.lang.Integer value) {
		this.postId = value;
	}
	
	public java.lang.Integer getPostId() {
		return this.postId;
	}
	
	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}
	
	public java.lang.Integer getProjectId() {
		return this.projectId;
	}
	
	public void setPostType(java.lang.Integer value) {
		this.postType = value;
	}
	
	public java.lang.Integer getPostType() {
		return this.postType;
	}

	public java.math.BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}
	


	
}

