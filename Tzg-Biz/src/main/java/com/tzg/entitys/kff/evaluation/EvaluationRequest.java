package com.tzg.entitys.kff.evaluation;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class EvaluationRequest implements Serializable {

    private String token;
    
    private String postTitle;
	/**
     * evaluationId
     */ 	
	private java.lang.Integer evaluationId;
    /**
     * projectId
     */ 	
	private java.lang.Integer projectId;
    /**
     * 1-简单测评；2-全面系统专业测评;3-部分系统专业评测；4-用户自定义专业测评
     */ 	
	private java.lang.Integer modelType;
    /**
     * 1-10，小数点1位
     */ 	
	private BigDecimal totalScore = BigDecimal.ZERO;
    /**
     * 专业评测各子项评测内容详情json字符串
     */ 	
	private java.lang.String professionalEvaDetail;
    /**
     * 评测描述信息
     */ 	
	private java.lang.String evauationContent;
    /**
     * 评测表标签
     */ 	
	private java.lang.String evaluationTags;
    /**
     * 0删除；1有效
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
     * createUserId
     */ 	
	private java.lang.Integer createUserId;

	private String postSmallImages;
	
	public String getPostSmallImages() {
		return postSmallImages;
	}

	public void setPostSmallImages(String postSmallImages) {
		this.postSmallImages = postSmallImages;
	}

	public void setEvaluationId(java.lang.Integer value) {
		this.evaluationId = value;
	}
	
	public java.lang.Integer getEvaluationId() {
		return this.evaluationId;
	}
	
	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}
	
	public java.lang.Integer getProjectId() {
		return this.projectId;
	}
	

	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public java.lang.Integer getModelType() {
		return modelType;
	}

	public void setModelType(java.lang.Integer modelType) {
		this.modelType = modelType;
	}

	public void setTotalScore(BigDecimal value) {
		this.totalScore = value;
	}
	
	public BigDecimal getTotalScore() {
		return this.totalScore;
	}
	
	public void setProfessionalEvaDetail(java.lang.String value) {
		this.professionalEvaDetail = value;
	}
	
	public java.lang.String getProfessionalEvaDetail() {
		return this.professionalEvaDetail;
	}
	
	public void setEvauationContent(java.lang.String value) {
		this.evauationContent = value;
	}
	
	public java.lang.String getEvauationContent() {
		return this.evauationContent;
	}
	
	public void setEvaluationTags(java.lang.String value) {
		this.evaluationTags = value;
	}
	
	public java.lang.String getEvaluationTags() {
		return this.evaluationTags;
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
	
	public void setCreateUserId(java.lang.Integer value) {
		this.createUserId = value;
	}
	
	public java.lang.Integer getCreateUserId() {
		return this.createUserId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	
}

