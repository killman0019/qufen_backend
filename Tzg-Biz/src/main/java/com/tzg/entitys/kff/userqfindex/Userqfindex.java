package com.tzg.entitys.kff.userqfindex;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Userqfindex implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8291560967617457880L;
	/**
     * qfindexId
     */ 	
	private java.lang.Integer qfindexId;
    /**
     * userId
     */ 	
	private java.lang.Integer userId;
    /**
     * 总区分指数
     */ 	
	private java.lang.Integer totalIndex;
    /**
     * 内容贡献权重
     */ 	
	private java.lang.Integer contentWeight;
    /**
     * 锁仓权重
     */ 	
	private java.lang.Integer locktokenWeight;
    /**
     * 活跃权重
     */ 	
	private java.lang.Integer activeWeight;
    /**
     * 影响力权重
     */ 	
	private java.lang.Integer influenceWeight;
    /**
     * 社区考核权重
     */ 	
	private java.lang.Integer communityWeight;
    /**
     * 健康度权重
     */ 	
	private java.lang.Integer healthyWeight;
    /**
     * 创建时间
     */ 	
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
    /**
     * 更新时间
     */ 	
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;
    /**
     * 状态：0-删除；1-有效
     */ 	
	private java.lang.Integer status;
    /**
     * 备注信息
     */ 	
	private java.lang.String memo;

	
	public void setQfindexId(java.lang.Integer value) {
		this.qfindexId = value;
	}
	
	public java.lang.Integer getQfindexId() {
		return this.qfindexId;
	}
	
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	
	public void setTotalIndex(java.lang.Integer value) {
		this.totalIndex = value;
	}
	
	public java.lang.Integer getTotalIndex() {
		return this.totalIndex;
	}
	
	public void setContentWeight(java.lang.Integer value) {
		this.contentWeight = value;
	}
	
	public java.lang.Integer getContentWeight() {
		return this.contentWeight;
	}
	
	public void setLocktokenWeight(java.lang.Integer value) {
		this.locktokenWeight = value;
	}
	
	public java.lang.Integer getLocktokenWeight() {
		return this.locktokenWeight;
	}
	
	public void setActiveWeight(java.lang.Integer value) {
		this.activeWeight = value;
	}
	
	public java.lang.Integer getActiveWeight() {
		return this.activeWeight;
	}
	
	public void setInfluenceWeight(java.lang.Integer value) {
		this.influenceWeight = value;
	}
	
	public java.lang.Integer getInfluenceWeight() {
		return this.influenceWeight;
	}
	
	public void setCommunityWeight(java.lang.Integer value) {
		this.communityWeight = value;
	}
	
	public java.lang.Integer getCommunityWeight() {
		return this.communityWeight;
	}
	
	public void setHealthyWeight(java.lang.Integer value) {
		this.healthyWeight = value;
	}
	
	public java.lang.Integer getHealthyWeight() {
		return this.healthyWeight;
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
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	public java.lang.String getMemo() {
		return this.memo;
	}

	
}

