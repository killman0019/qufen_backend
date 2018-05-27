package com.tzg.entitys.kff.dprojectType;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class DprojectType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 304940011248870032L;
	/**
     * projectTypeId
     */ 	
	private java.lang.Integer projectTypeId;
    /**
     * projectTypeName
     */ 	
	private java.lang.String projectTypeName;
    /**
     * createUserId
     */ 	
	private java.lang.Integer createUserId;
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
	private Integer status = 1;

	
	public void setProjectTypeId(java.lang.Integer value) {
		this.projectTypeId = value;
	}
	
	public java.lang.Integer getProjectTypeId() {
		return this.projectTypeId;
	}
	
	public void setProjectTypeName(java.lang.String value) {
		this.projectTypeName = value;
	}
	
	public java.lang.String getProjectTypeName() {
		return this.projectTypeName;
	}
	
	public void setCreateUserId(java.lang.Integer value) {
		this.createUserId = value;
	}
	
	public java.lang.Integer getCreateUserId() {
		return this.createUserId;
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
	
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}

	
}

