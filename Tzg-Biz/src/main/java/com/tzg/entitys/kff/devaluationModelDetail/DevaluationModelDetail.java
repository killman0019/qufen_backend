package com.tzg.entitys.kff.devaluationModelDetail;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class DevaluationModelDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -469978368917407802L;
	/**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * modelId
     */ 	
	private java.lang.Integer modelId;
    /**
     * 维度名称
     */ 	
	private java.lang.String detailName;
    /**
     * 维度说明
     */ 	
	private java.lang.String detailDesc;
    /**
     * 占比，百分比2位数
     */ 	
	private java.lang.Integer detailWeight = 0;
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
     * 0-删除；1有效
     */ 	
	private java.lang.Integer status;

	
	//分项总分 和 评论人数
	
	private BigDecimal totalScore = BigDecimal.ZERO;
	
	private Integer raterNum = 0;
	
	
	
	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getRaterNum() {
		return raterNum;
	}

	public void setRaterNum(Integer raterNum) {
		this.raterNum = raterNum;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setModelId(java.lang.Integer value) {
		this.modelId = value;
	}
	
	public java.lang.Integer getModelId() {
		return this.modelId;
	}
	
	public void setDetailName(java.lang.String value) {
		this.detailName = value;
	}
	
	public java.lang.String getDetailName() {
		return this.detailName;
	}
	
	public void setDetailDesc(java.lang.String value) {
		this.detailDesc = value;
	}
	
	public java.lang.String getDetailDesc() {
		return this.detailDesc;
	}
	
	public void setDetailWeight(java.lang.Integer value) {
		this.detailWeight = value;
	}
	
	public java.lang.Integer getDetailWeight() {
		return this.detailWeight;
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
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}

	
}

