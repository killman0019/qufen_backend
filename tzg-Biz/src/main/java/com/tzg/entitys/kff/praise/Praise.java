package com.tzg.entitys.kff.praise;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Praise implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3378375137579270658L;
	/**
     * praiseId
     */ 	
	private java.lang.Integer praiseId;
    /**
     * praiseUserId
     */ 	
	private java.lang.Integer praiseUserId;
    /**
     * projectId
     */ 	
	private java.lang.Integer projectId;
	
    /**
     * 1-帖子点赞；2-评论点赞
     */ 	
	private java.lang.Integer praiseType = 1 ;
	
    /**
     * praiseType = 2 时存 commentsId
     */ 	
	private java.lang.Integer postId;
    /**
     * 帖子类型：1-评测；2-讨论；3-文章
     */ 	
	private java.lang.Integer postType;
    /**
     * bepraiseUserId
     */ 	
	private java.lang.Integer bepraiseUserId;
    /**
     * 状态:0-取消点赞;1-点赞有效
     */ 	
	private java.lang.Integer status ;
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

	
	public void setPraiseId(java.lang.Integer value) {
		this.praiseId = value;
	}
	
	public java.lang.Integer getPraiseId() {
		return this.praiseId;
	}
	
	public void setPraiseUserId(java.lang.Integer value) {
		this.praiseUserId = value;
	}
	
	public java.lang.Integer getPraiseUserId() {
		return this.praiseUserId;
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

	public void setBepraiseUserId(java.lang.Integer value) {
		this.bepraiseUserId = value;
	}
	
	public java.lang.Integer getBepraiseUserId() {
		return this.bepraiseUserId;
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

	public java.lang.Integer getPraiseType() {
		return praiseType;
	}

	public void setPraiseType(java.lang.Integer praiseType) {
		this.praiseType = praiseType;
	}

	
}

