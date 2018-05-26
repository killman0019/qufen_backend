package com.tzg.entitys.comments;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Comments implements Serializable {

	
	//来自project部分
	/**
     * 代币名称
     */ 	
	private java.lang.String projectCode;
    /**
     * 代币英文名称
     */ 	
	private java.lang.String projectEnglishName;
    /**
     * projectChineseName
     */ 	
	private java.lang.String projectChineseName;
	
	//来自post部分
	
	/**
     * 帖子标题
     */ 	
	private java.lang.String postTitle;
 
	
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = -8936085667586663330L;
	/**
     * commentsId
     */ 	
	private java.lang.Integer commentsId;
    /**
     * commentUserId
     */ 	
	private java.lang.Integer commentUserId;
    /**
     * commentUserIcon
     */ 	
	private java.lang.String commentUserIcon;
    /**
     * commentUserName
     */ 	
	private java.lang.String commentUserName;
    /**
     * 评论内容
     */ 	
	private java.lang.String commentContent;
	
	private java.lang.Integer projectId;
    /**
     * 帖子ID
     */ 	
	private java.lang.Integer postId;
    /**
     * 帖子类型：1-评测；2-讨论；3-文章
     */ 	
	private java.lang.Integer postType;
    /**
     * 点赞数量
     */ 	
	private java.lang.Integer praiseNum;
    /**
     * 评论ID
     */ 	
	private java.lang.Integer parentCommentsId;
    /**
     * becommentedUserId
     */ 	
	private java.lang.Integer becommentedUserId;
    /**
     * becommentedUserName
     */ 	
	private java.lang.String becommentedUserName;
    /**
     * becommentedUserIcon
     */ 	
	private java.lang.String becommentedUserIcon;
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
     * 状态:0-删除；1-有效
     */ 	
	private java.lang.Integer status;

	
	public void setCommentsId(java.lang.Integer value) {
		this.commentsId = value;
	}
	
	public java.lang.Integer getCommentsId() {
		return this.commentsId;
	}
	
	public void setCommentUserId(java.lang.Integer value) {
		this.commentUserId = value;
	}
	
	public java.lang.Integer getCommentUserId() {
		return this.commentUserId;
	}
	
	public void setCommentUserIcon(java.lang.String value) {
		this.commentUserIcon = value;
	}
	
	public java.lang.String getCommentUserIcon() {
		return this.commentUserIcon;
	}
	
	public void setCommentUserName(java.lang.String value) {
		this.commentUserName = value;
	}
	
	public java.lang.String getCommentUserName() {
		return this.commentUserName;
	}
	
	public void setCommentContent(java.lang.String value) {
		this.commentContent = value;
	}
	
	public java.lang.String getCommentContent() {
		return this.commentContent;
	}
	
	public void setPostId(java.lang.Integer value) {
		this.postId = value;
	}
	
	public java.lang.Integer getPostId() {
		return this.postId;
	}
	
	public void setPostType(java.lang.Integer value) {
		this.postType = value;
	}
	
	public java.lang.Integer getPostType() {
		return this.postType;
	}
	
	public void setPraiseNum(java.lang.Integer value) {
		this.praiseNum = value;
	}
	
	public java.lang.Integer getPraiseNum() {
		return this.praiseNum;
	}
	
	public void setParentCommentsId(java.lang.Integer value) {
		this.parentCommentsId = value;
	}
	
	public java.lang.Integer getParentCommentsId() {
		return this.parentCommentsId;
	}
	
	public void setBecommentedUserId(java.lang.Integer value) {
		this.becommentedUserId = value;
	}
	
	public java.lang.Integer getBecommentedUserId() {
		return this.becommentedUserId;
	}
	
	public void setBecommentedUserName(java.lang.String value) {
		this.becommentedUserName = value;
	}
	
	public java.lang.String getBecommentedUserName() {
		return this.becommentedUserName;
	}
	
	public void setBecommentedUserIcon(java.lang.String value) {
		this.becommentedUserIcon = value;
	}
	
	public java.lang.String getBecommentedUserIcon() {
		return this.becommentedUserIcon;
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
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}

	public java.lang.String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(java.lang.String projectCode) {
		this.projectCode = projectCode;
	}

	public java.lang.String getProjectEnglishName() {
		return projectEnglishName;
	}

	public void setProjectEnglishName(java.lang.String projectEnglishName) {
		this.projectEnglishName = projectEnglishName;
	}

	public java.lang.String getProjectChineseName() {
		return projectChineseName;
	}

	public void setProjectChineseName(java.lang.String projectChineseName) {
		this.projectChineseName = projectChineseName;
	}

	public java.lang.String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(java.lang.String postTitle) {
		this.postTitle = postTitle;
	}

	public java.lang.Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(java.lang.Integer projectId) {
		this.projectId = projectId;
	}

	
}

