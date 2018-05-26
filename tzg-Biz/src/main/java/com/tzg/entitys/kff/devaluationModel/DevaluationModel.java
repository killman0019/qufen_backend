package com.tzg.entitys.kff.devaluationModel;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class DevaluationModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4255410424129563944L;
	/**
     * modelId
     */ 	
	private java.lang.Integer modelId;
    /**
     * 描述
     */ 	
	private java.lang.String modelName;
    /**
     * 描述
     */ 	
	private java.lang.String modelDesc;
    /**
     * 占比，百分比2位数
     */ 	
	private Integer modelWeight;
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
    /**
     * status
     */ 	
	private java.lang.Integer status = 1;
    /**
     * 评测类型：1-简单测评；2-系统定义专业测评;3-用户自定义专业测评
     */ 	
	private java.lang.Integer modelType = 3;
    /**
     * 批次号
     */ 	
	private java.lang.Integer batchId;

	
	public void setModelId(java.lang.Integer value) {
		this.modelId = value;
	}
	
	public java.lang.Integer getModelId() {
		return this.modelId;
	}
	
	public void setModelName(java.lang.String value) {
		this.modelName = value;
	}
	
	public java.lang.String getModelName() {
		return this.modelName;
	}
	
	public void setModelDesc(java.lang.String value) {
		this.modelDesc = value;
	}
	
	public java.lang.String getModelDesc() {
		return this.modelDesc;
	}
	

	
	public Integer getModelWeight() {
		return modelWeight;
	}

	public void setModelWeight(Integer modelWeight) {
		this.modelWeight = modelWeight;
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
	

	
	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public java.lang.Integer getModelType() {
		return modelType;
	}

	public void setModelType(java.lang.Integer modelType) {
		this.modelType = modelType;
	}

	public void setBatchId(java.lang.Integer value) {
		this.batchId = value;
	}
	
	public java.lang.Integer getBatchId() {
		return this.batchId;
	}

	
}

