package com.tzg.entitys.kff.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

import com.tzg.common.base.BaseRequest;
import com.tzg.common.utils.DateUtil;

public class SubmitKFFProjectRequest extends BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1653053637605366265L;

	/**
	 * 项目图标
	 */
	private java.lang.String projectIcon;
	/**
	 * 审核状态：1；待审核；2-审核通过；3-拒绝
	 */
	private java.lang.Integer state = 1;
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
	/**
	 * 项目标题
	 */
	private java.lang.String projectSignature;
	/**
	 * 官方网址
	 */
	private java.lang.String websiteUrl;
	/**
	 * 是否上市：0-未上市；1-已上市
	 */
	private java.lang.Integer listed = 0;
	/**
	 * 发行时间
	 */
	private java.lang.String issueDateStr;
	/**
	 * 发行数量
	 */
	private java.lang.Long issueNum = 0L;
	/**
	 * 白皮书地址
	 */
	private java.lang.String whitepaperUrl;
	/**
	 * 项目分类名称
	 */
	private java.lang.String projectTypeName;
	/**
	 * 项目分类
	 */
	private java.lang.Integer projectTypeId;
	/**
	 * 项目描述
	 */
	private java.lang.String projectDesc;
	/**
	 * 提交用户ID
	 */
	private java.lang.Integer submitUserId;
	/*
	 * 提交用户联系信息
	 */
	private java.lang.String submitUserContactInfo;
	// 提交用户类型:1-普通用户;2-利益相关;3-项目方;4-投资者
	private java.lang.Integer submitUserType = 1;

	/**
	 * 推荐理由
	 */
	private java.lang.String submitReason;
	/**
	 * 状态：0-删除；1-有效
	 */
	private java.lang.Integer status = 1;
	/**
	 * createTime
	 */
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
	/**
	 * 发布时间，对应审核通过时间
	 */
	private java.util.Date publishTime;
	private java.lang.String publishTimeStr;
	/**
	 * updateTime
	 */
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;
	/**
	 * 项目总评分：1-10精确到1位
	 */
	private BigDecimal totalScore = BigDecimal.ZERO;
	/**
	 * 评分用户人数
	 */
	private java.lang.Integer raterNum = 0;
	/**
	 * 关注用户人数
	 */
	private java.lang.Integer followerNum = 0;
	/**
	 * 评论数量
	 */
	private java.lang.Integer commentsNum = 0;
	/**
	 * 收藏人数
	 */
	private java.lang.Integer collectNum = 0;

	private Integer totalRaterNum = 0;
	
	private Integer cmcId;
	
	private String github;
	
	private String bsjCirculateData;
	
	private Integer projectUserId;
	private String projectMobile;
	private Integer projectUserType;
	
	public Integer getProjectUserId() {
		return projectUserId;
	}

	public void setProjectUserId(Integer projectUserId) {
		this.projectUserId = projectUserId;
	}

	public String getProjectMobile() {
		return projectMobile;
	}

	public void setProjectMobile(String projectMobile) {
		this.projectMobile = projectMobile;
	}

	public Integer getProjectUserType() {
		return projectUserType;
	}

	public void setProjectUserType(Integer projectUserType) {
		this.projectUserType = projectUserType;
	}

	public Integer getCmcId() {
		return cmcId;
	}

	public void setCmcId(Integer cmcId) {
		this.cmcId = cmcId;
	}

	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public String getBsjCirculateData() {
		return bsjCirculateData;
	}

	public void setBsjCirculateData(String bsjCirculateData) {
		this.bsjCirculateData = bsjCirculateData;
	}

	public Integer getTotalRaterNum() {
		return totalRaterNum;
	}

	public void setTotalRaterNum(Integer totalRaterNum) {
		this.totalRaterNum = totalRaterNum;
	}

	public java.lang.String getSubmitUserContactInfo() {
		return submitUserContactInfo;
	}

	public void setSubmitUserContactInfo(java.lang.String submitUserContactInfo) {
		this.submitUserContactInfo = submitUserContactInfo;
	}

	public java.lang.Integer getSubmitUserType() {
		return submitUserType;
	}

	public void setSubmitUserType(java.lang.Integer submitUserType) {
		this.submitUserType = submitUserType;
	}

	public void setProjectIcon(java.lang.String value) {
		this.projectIcon = value;
	}

	public java.lang.String getProjectIcon() {
		return this.projectIcon;
	}

	public java.lang.Integer getState() {
		return state;
	}

	public void setState(java.lang.Integer state) {
		this.state = state;
	}

	public void setProjectCode(java.lang.String value) {
		this.projectCode = value;
	}

	public java.lang.String getProjectCode() {
		return this.projectCode;
	}

	public void setProjectEnglishName(java.lang.String value) {
		this.projectEnglishName = value;
	}

	public java.lang.String getProjectEnglishName() {
		return this.projectEnglishName;
	}

	public void setProjectChineseName(java.lang.String value) {
		this.projectChineseName = value;
	}

	public java.lang.String getProjectChineseName() {
		return this.projectChineseName;
	}

	public void setProjectSignature(java.lang.String value) {
		this.projectSignature = value;
	}

	public java.lang.String getProjectSignature() {
		return this.projectSignature;
	}

	public void setWebsiteUrl(java.lang.String value) {
		this.websiteUrl = value;
	}

	public java.lang.String getWebsiteUrl() {
		return this.websiteUrl;
	}

	public java.lang.Integer getListed() {
		return listed;
	}

	public void setListed(java.lang.Integer listed) {
		this.listed = listed;
	}

	public java.lang.String getIssueDateStr() {
		return this.issueDateStr;
	}

	public void setIssueNum(java.lang.Long value) {
		this.issueNum = value;
	}

	public void setIssueDateStr(String value) {
		this.issueDateStr = value;

	}

	public java.lang.Long getIssueNum() {
		return this.issueNum;
	}

	public void setWhitepaperUrl(java.lang.String value) {
		this.whitepaperUrl = value;
	}

	public java.lang.String getWhitepaperUrl() {
		return this.whitepaperUrl;
	}

	public void setProjectTypeName(java.lang.String value) {
		this.projectTypeName = value;
	}

	public java.lang.String getProjectTypeName() {
		return this.projectTypeName;
	}

	public void setProjectTypeId(java.lang.Integer value) {
		this.projectTypeId = value;
	}

	public java.lang.Integer getProjectTypeId() {
		return this.projectTypeId;
	}

	public void setProjectDesc(java.lang.String value) {
		this.projectDesc = value;
	}

	public java.lang.String getProjectDesc() {
		return this.projectDesc;
	}

	public void setSubmitUserId(java.lang.Integer value) {
		this.submitUserId = value;
	}

	public java.lang.Integer getSubmitUserId() {
		return this.submitUserId;
	}

	public void setSubmitReason(java.lang.String value) {
		this.submitReason = value;
	}

	public java.lang.String getSubmitReason() {
		return this.submitReason;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTimeStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.createTime = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public java.lang.String getCreateTimeStr() {
		return this.createTimeStr;
	}

	public void setPublishTime(java.util.Date value) {
		this.publishTimeStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.publishTime = value;
	}

	public java.util.Date getPublishTime() {
		return this.publishTime;
	}

	public java.lang.String getPublishTimeStr() {
		return this.publishTimeStr;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTimeStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.updateTime = value;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public java.lang.String getUpdateTimeStr() {
		return this.updateTimeStr;
	}

	public void setTotalScore(BigDecimal value) {
		this.totalScore = value;
	}

	public BigDecimal getTotalScore() {
		return this.totalScore;
	}

	public void setRaterNum(java.lang.Integer value) {
		this.raterNum = value;
	}

	public java.lang.Integer getRaterNum() {
		return this.raterNum;
	}

	public void setFollowerNum(java.lang.Integer value) {
		this.followerNum = value;
	}

	public java.lang.Integer getFollowerNum() {
		return this.followerNum;
	}

	public void setCommentsNum(java.lang.Integer value) {
		this.commentsNum = value;
	}

	public java.lang.Integer getCommentsNum() {
		return this.commentsNum;
	}

	public void setCollectNum(java.lang.Integer value) {
		this.collectNum = value;
	}

	public java.lang.Integer getCollectNum() {
		return this.collectNum;
	}

}
