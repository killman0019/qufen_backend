package com.tzg.entitys.kff.post;

import java.io.Serializable;
import java.util.List;

public class PostShare implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2403904292292429156L;

	private java.lang.String projectCode;

	private java.lang.String projectIcon;

	private java.lang.String projectEnglishName;

	private java.lang.String postTitle;

	/**
	 * 帖子类型：1-评测；2-讨论；3-文章
	 */
	private java.lang.Integer postType;
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
	 * createTime
	 */
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
	/**
	 * updateTime
	 */
	private java.util.Date updateTime;

	public java.lang.String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(java.lang.String projectCode) {
		this.projectCode = projectCode;
	}

	public java.lang.String getProjectIcon() {
		return projectIcon;
	}

	public void setProjectIcon(java.lang.String projectIcon) {
		this.projectIcon = projectIcon;
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

	public java.lang.Integer getPostType() {
		return postType;
	}

	public void setPostType(java.lang.Integer postType) {
		this.postType = postType;
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

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(java.lang.String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(java.lang.String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public List<String> getSendUserIcon() {
		return sendUserIcon;
	}

	public void setSendUserIcon(List<String> sendUserIcon) {
		this.sendUserIcon = sendUserIcon;
	}

	private java.lang.String updateTimeStr;

	private List<String> sendUserIcon;

}
