package com.tzg.entitys.kff.notice;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class KFFNotice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2291506901418589407L;
	/**
     * noticeId
     */ 	
	private java.lang.Integer noticeId;
    /**
     * 标题
     */ 	
	private java.lang.String title;
    /**
     * 公告内容
     */ 	
	private java.lang.String content;
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

	
	public void setNoticeId(java.lang.Integer value) {
		this.noticeId = value;
	}
	
	public java.lang.Integer getNoticeId() {
		return this.noticeId;
	}
	
	public void setTitle(java.lang.String value) {
		this.title = value;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setContent(java.lang.String value) {
		this.content = value;
	}
	
	public java.lang.String getContent() {
		return this.content;
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

