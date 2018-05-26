package com.tzg.entitys.kff.collect;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Collect implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5007831947115249275L;
	/**
     * collectId
     */ 	
	private java.lang.Integer collectId;
    /**
     * collectUserId
     */ 	
	private java.lang.Integer collectUserId;
    /**
     * projectId
     */ 	
	private java.lang.Integer projectId;
    /**
     * postId
     */ 	
	private java.lang.Integer postId;
    /**
     * 帖子类型：1-评测；2-讨论；3-文章
     */ 	
	private java.lang.Integer postType;
    /**
     * 状态:0-取消收藏；1-收藏
     */ 	
	private java.lang.Integer status;
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

	
	public void setCollectId(java.lang.Integer value) {
		this.collectId = value;
	}
	
	public java.lang.Integer getCollectId() {
		return this.collectId;
	}
	
	public void setCollectUserId(java.lang.Integer value) {
		this.collectUserId = value;
	}
	
	public java.lang.Integer getCollectUserId() {
		return this.collectUserId;
	}
	
	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}
	
	public java.lang.Integer getProjectId() {
		return this.projectId;
	}
	
	public void setPostId(java.lang.Integer value) {
		this.postId = value;
	}
	
	public java.lang.Integer getPostId() {
		return this.postId;
	}
	
	
	
	public java.lang.Integer getPostType() {
		return postType;
	}

	public void setPostType(java.lang.Integer postType) {
		this.postType = postType;
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

	
}

