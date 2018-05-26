package com.tzg.entitys.article;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Article implements Serializable {

	//来自post部分
	 /**
   * projectCode
   */ 	
	private java.lang.String projectCode;
  /**
   * projectEnglishName
   */ 	
	private java.lang.String projectEnglishName;
  /**
   * 帖子标题
   */ 	
	private java.lang.String postTitle;
	
	   /**
   * 剪短描述
   */ 	
	private java.lang.String postShortDesc;
  /**
   * 缩略图json，目前最多三张图的url等信息
   */ 	
	private java.lang.String postSmallImages;
  /**
   * 评论数量
   */ 	
	private java.lang.Integer commentsNum;
  /**
   * 点赞数
   */ 	
	private java.lang.Integer praiseNum;
  /**
   * 浏览量
   */ 	
	private java.lang.Integer pageviewNum;
  /**
   * 捐赠人数
   */ 	
	private java.lang.Integer donateNum;
  /**
   * 收藏人数
   */ 	
	private java.lang.Integer collectNum;
	
   /**
    * createUserId
    */ 	
	private java.lang.Integer createUserId;
  /**
   * 用户头像
   */ 	
	private java.lang.String createUserIcon;
  /**
   * 用户签名
   */ 	
	private java.lang.String createUserSignature;
  /**
   * 用户昵称
   */ 	
	private java.lang.String createUserName;
	
    /**
     * 0删除；1有效
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
	
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 4853064426808780151L;
	/**
     * articleId
     */ 	
	private java.lang.Integer articleId;
    /**
     * postId
     */ 	
	private java.lang.Integer postId;
    /**
     * postUuid
     */ 	
	private java.lang.String postUuid;
    /**
     * 文章内容，目前限定30000字，图片信息用富文本保存在里边
     */ 	
	private java.lang.String articleContents;

	
	public void setArticleId(java.lang.Integer value) {
		this.articleId = value;
	}
	
	public java.lang.Integer getArticleId() {
		return this.articleId;
	}
	
	public void setPostId(java.lang.Integer value) {
		this.postId = value;
	}
	
	public java.lang.Integer getPostId() {
		return this.postId;
	}
	
	public void setPostUuid(java.lang.String value) {
		this.postUuid = value;
	}
	
	public java.lang.String getPostUuid() {
		return this.postUuid;
	}
	
	public void setArticleContents(java.lang.String value) {
		this.articleContents = value;
	}
	
	public java.lang.String getArticleContents() {
		return this.articleContents;
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

	public java.lang.String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(java.lang.String postTitle) {
		this.postTitle = postTitle;
	}

	public java.lang.String getPostShortDesc() {
		return postShortDesc;
	}

	public void setPostShortDesc(java.lang.String postShortDesc) {
		this.postShortDesc = postShortDesc;
	}

	public java.lang.String getPostSmallImages() {
		return postSmallImages;
	}

	public void setPostSmallImages(java.lang.String postSmallImages) {
		this.postSmallImages = postSmallImages;
	}

	public java.lang.Integer getCommentsNum() {
		return commentsNum;
	}

	public void setCommentsNum(java.lang.Integer commentsNum) {
		this.commentsNum = commentsNum;
	}

	public java.lang.Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(java.lang.Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public java.lang.Integer getPageviewNum() {
		return pageviewNum;
	}

	public void setPageviewNum(java.lang.Integer pageviewNum) {
		this.pageviewNum = pageviewNum;
	}

	public java.lang.Integer getDonateNum() {
		return donateNum;
	}

	public void setDonateNum(java.lang.Integer donateNum) {
		this.donateNum = donateNum;
	}

	public java.lang.Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(java.lang.Integer collectNum) {
		this.collectNum = collectNum;
	}

	public java.lang.Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(java.lang.Integer createUserId) {
		this.createUserId = createUserId;
	}

	public java.lang.String getCreateUserIcon() {
		return createUserIcon;
	}

	public void setCreateUserIcon(java.lang.String createUserIcon) {
		this.createUserIcon = createUserIcon;
	}

	public java.lang.String getCreateUserSignature() {
		return createUserSignature;
	}

	public void setCreateUserSignature(java.lang.String createUserSignature) {
		this.createUserSignature = createUserSignature;
	}

	public java.lang.String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(java.lang.String createUserName) {
		this.createUserName = createUserName;
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

