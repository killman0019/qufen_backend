package com.tzg.entitys.kff.suggest;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Suggest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2512090074525373764L;
	/**
     * suggestId
     */ 	
	private java.lang.Integer suggestId;
    /**
     * 公告内容
     */ 	
	private java.lang.String content;
    /**
     * 用户联系方式
     */ 	
	private java.lang.String contactInfo;
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
     * 状态1有效0删除
     */ 	
	private java.lang.Integer status;

	
	public void setSuggestId(java.lang.Integer value) {
		this.suggestId = value;
	}
	
	public java.lang.Integer getSuggestId() {
		return this.suggestId;
	}
	
	public void setContent(java.lang.String value) {
		this.content = value;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContactInfo(java.lang.String value) {
		this.contactInfo = value;
	}
	
	public java.lang.String getContactInfo() {
		return this.contactInfo;
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
	

	
}

