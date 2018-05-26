package com.tzg.entitys.dtags;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Dtags implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * tagId
     */ 	
	private java.lang.Integer tagId;
    /**
     * tagName
     */ 	
	private java.lang.String tagName;
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
     * 状态:0-删除;1-有效
     */ 	
	private java.lang.Integer status;

	private String memo;
	
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setTagId(java.lang.Integer value) {
		this.tagId = value;
	}
	
	public java.lang.Integer getTagId() {
		return this.tagId;
	}
	
	public void setTagName(java.lang.String value) {
		this.tagName = value;
	}
	
	public java.lang.String getTagName() {
		return this.tagName;
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

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}
	
	
	
}

