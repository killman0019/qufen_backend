package com.tzg.entitys.kff.commendation;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.base.BaseRequest;
import com.tzg.common.utils.DateUtil;

public class CommendationRequest extends BaseRequest implements Serializable {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4790946982797816047L;
	
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
	private BigDecimal amount;
	public java.lang.Integer getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(java.lang.Integer sendUserId) {
		this.sendUserId = sendUserId;
	}
	public java.lang.String getSendUserIcon() {
		return sendUserIcon;
	}
	public void setSendUserIcon(java.lang.String sendUserIcon) {
		this.sendUserIcon = sendUserIcon;
	}
	public java.lang.Integer getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(java.lang.Integer receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public java.lang.Integer getPostId() {
		return postId;
	}
	public void setPostId(java.lang.Integer postId) {
		this.postId = postId;
	}
	public java.lang.Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(java.lang.Integer projectId) {
		this.projectId = projectId;
	}
	public java.lang.Integer getPostType() {
		return postType;
	}
	public void setPostType(java.lang.Integer postType) {
		this.postType = postType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	

	
}

