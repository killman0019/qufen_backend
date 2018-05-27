package com.tzg.entitys.notice;

import java.io.Serializable;

import com.tzg.common.utils.DateUtil;

public class Notice implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 1 -- 还款公告； 2 -- 平台公告。
     */ 	
	private java.lang.Integer itype;
    /**
     * vcTitle
     */ 	
	private java.lang.String vcTitle;
    /**
     * vcSubtitle
     */ 	
	private java.lang.String vcSubtitle;
    /**
     * dtNotice
     */ 	
	private java.util.Date dtNotice;
	private java.lang.String dtNoticeStr;
    /**
     * vcContent
     */ 	
	private java.lang.String vcContent;
    /**
     * dtCreate
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * 1 -- 有效； 2 -- 无效。
     */ 	
	private java.lang.Integer istate;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}
	
	public java.lang.Integer getItype() {
		return this.itype;
	}
	
	public void setVcTitle(java.lang.String value) {
		this.vcTitle = value;
	}
	
	public java.lang.String getVcTitle() {
		return this.vcTitle;
	}
	
	public void setVcSubtitle(java.lang.String value) {
		this.vcSubtitle = value;
	}
	
	public java.lang.String getVcSubtitle() {
		return this.vcSubtitle;
	}
	
	public void setDtNotice(java.util.Date value) {
		this.dtNoticeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtNotice = value;
	}
	
	public java.util.Date getDtNotice() {
		return this.dtNotice;
	}
	
	public java.lang.String getDtNoticeStr() {
		return this.dtNoticeStr;
	}
	
	public void setVcContent(java.lang.String value) {
		this.vcContent = value;
	}
	
	public java.lang.String getVcContent() {
		return this.vcContent;
	}
	
	public void setDtCreate(java.util.Date value) {
		this.dtCreateStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtCreate = value;
	}
	
	public java.util.Date getDtCreate() {
		return this.dtCreate;
	}
	
	public java.lang.String getDtCreateStr() {
		return this.dtCreateStr;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
	}

	
}

