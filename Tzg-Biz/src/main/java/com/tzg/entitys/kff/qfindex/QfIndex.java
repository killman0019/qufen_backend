package com.tzg.entitys.kff.qfindex;

import java.io.Serializable;
import java.util.Date;

import com.tzg.common.utils.DateUtil;

public class QfIndex implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3884437919463088370L;
	/**
	 * qf_index_id
	 */
	private java.lang.Integer qfIndexId;
	/**
	 * userId
	 */
	private java.lang.Integer userId;
	/**
	 * 内容贡献指数;
	 */
	private java.lang.Double contributeContent;
	/**
	 * 锁仓评测指数
	 */
	private java.lang.Double lockedPosition;
	/**
	 * 活跃度指数
	 */
	private java.lang.Double liveness;
	/**
	 * 影响力指数
	 */
	private java.lang.Double influence;
	/**
	 * 社区考核指数
	 */
	private java.lang.Double communityAssessment;
	/**
	 * 健康度
	 */
	private java.lang.Double healthDegree;
	/**
	 * 0-99刁民 100-199平民 200-299元芳 300-499狄仁杰 500-699柯南 700-899福尔摩斯900-1000包青天
	 */
	private java.lang.String statusHierarchyDesc;
	/**
	 * 分数
	 */
	private java.lang.Integer statusHierarchyType;
	/**
	 * 个人有效赞
	 */
	private java.lang.Integer yxpraise;
	/**
	 * 个人有效评论次数
	 */
	private Integer yxComments;
	/**
	 * 个人有效分享次数
	 */
	private Integer yxSharePost;

	private Integer readingDegr;
	private Integer pushEvaDegr;

	public Integer getReadingDegr() {
		return readingDegr;
	}

	public void setReadingDegr(Integer readingDegr) {
		this.readingDegr = readingDegr;
	}

	public Integer getPushEvaDegr() {
		return pushEvaDegr;
	}

	public void setPushEvaDegr(Integer pushEvaDegr) {
		this.pushEvaDegr = pushEvaDegr;
	}

	public Integer getYxSharePost() {
		return yxSharePost;
	}

	public void setYxSharePost(Integer yxSharePost) {
		this.yxSharePost = yxSharePost;
	}

	public Integer getYxComments() {
		return yxComments;
	}

	public void setYxComments(Integer yxComments) {
		this.yxComments = yxComments;
	}

	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
	/**
	 * 修改更新时间
	 */
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;

	public java.lang.Integer getYxpraise() {
		return yxpraise;
	}

	public void setYxpraise(java.lang.Integer yxpraise) {
		this.yxpraise = yxpraise;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTimeStr = DateUtil.getDate(createTime, "yyyy-MM-dd HH:mm:ss");
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTimeStr = DateUtil.getDate(updateTime, "yyyy-MM-dd HH:mm:ss");
		this.updateTime = updateTime;
	}

	public java.lang.String getCreateTimeStr() {
		return createTimeStr;
	}

	public java.lang.String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public java.lang.Integer getQfIndexId() {
		return qfIndexId;
	}

	public void setQfIndexId(java.lang.Integer qfIndexId) {
		this.qfIndexId = qfIndexId;
	}

	public java.lang.Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.Double getContributeContent() {
		return contributeContent;
	}

	public void setContributeContent(java.lang.Double contributeContent) {
		this.contributeContent = contributeContent;
	}

	public java.lang.Double getLockedPosition() {
		return lockedPosition;
	}

	public void setLockedPosition(java.lang.Double lockedPosition) {
		this.lockedPosition = lockedPosition;
	}

	public java.lang.Double getLiveness() {
		return liveness;
	}

	public void setLiveness(java.lang.Double liveness) {
		this.liveness = liveness;
	}

	public java.lang.Double getInfluence() {
		return influence;
	}

	public void setInfluence(java.lang.Double influence) {
		this.influence = influence;
	}

	public java.lang.Double getCommunityAssessment() {
		return communityAssessment;
	}

	public void setCommunityAssessment(java.lang.Double communityAssessment) {
		this.communityAssessment = communityAssessment;
	}

	public java.lang.Double getHealthDegree() {
		return healthDegree;
	}

	public void setHealthDegree(java.lang.Double healthDegree) {
		this.healthDegree = healthDegree;
	}

	public java.lang.String getStatusHierarchyDesc() {
		return statusHierarchyDesc;
	}

	public void setStatusHierarchyDesc(java.lang.String statusHierarchyDesc) {
		this.statusHierarchyDesc = statusHierarchyDesc;
	}

	public java.lang.Integer getStatusHierarchyType() {
		return statusHierarchyType;
	}

	public void setStatusHierarchyType(java.lang.Integer statusHierarchyType) {
		this.statusHierarchyType = statusHierarchyType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}