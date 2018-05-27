package com.tzg.entitys.kff.projectevastat;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class Projectevastat implements Serializable {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 维度说明
     */ 	
	private java.lang.Integer projectId;
    /**
     * modelDetailId
     */ 	
	private java.lang.Integer modelDetailId;
    /**
     * 分项总得分
     */ 	
	private BigDecimal totalScore;
    /**
     * 分项打分总人数
     */ 	
	private java.lang.Integer raterNum;
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
     * 0-删除；1有效
     */ 	
	private java.lang.Integer status;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}
	
	public java.lang.Integer getProjectId() {
		return this.projectId;
	}
	
	public void setModelDetailId(java.lang.Integer value) {
		this.modelDetailId = value;
	}
	
	public java.lang.Integer getModelDetailId() {
		return this.modelDetailId;
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

	
}

