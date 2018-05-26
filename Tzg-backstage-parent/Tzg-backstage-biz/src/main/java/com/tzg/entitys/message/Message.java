package com.tzg.entitys.message;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * imanageId
     */ 	
	private java.lang.Integer imanageId;
    /**
     * iloginAccountId
     */ 	
	private java.lang.Integer iloginAccountId;
    /**
     * vcTitle
     */ 	
	private java.lang.String vcTitle;
    /**
     * vcSubTitle
     */ 	
	private java.lang.String vcSubTitle;
    /**
     * vcContent
     */ 	
	private java.lang.String vcContent;
    /**
     * dtPublish
     */ 	
	private java.util.Date dtPublish;
	private java.lang.String dtPublishStr;
    /**
     * 1 -- 未读； 2 -- 已读。
     */ 	
	private java.lang.Integer istate;
	
	/**
	 * 1 -- 系统通知；2 -- 活动公告。
	 */
	private java.lang.Integer imode;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setImanageId(java.lang.Integer value) {
		this.imanageId = value;
	}
	
	public java.lang.Integer getImanageId() {
		return this.imanageId;
	}
	
	public void setIloginAccountId(java.lang.Integer value) {
		this.iloginAccountId = value;
	}
	
	public java.lang.Integer getIloginAccountId() {
		return this.iloginAccountId;
	}
	
	public void setVcTitle(java.lang.String value) {
		this.vcTitle = value;
	}
	
	public java.lang.String getVcTitle() {
		return this.vcTitle;
	}
	
	public void setVcSubTitle(java.lang.String value) {
		this.vcSubTitle = value;
	}
	
	public java.lang.String getVcSubTitle() {
		return this.vcSubTitle;
	}
	
	public void setVcContent(java.lang.String value) {
		this.vcContent = value;
	}
	
	public java.lang.String getVcContent() {
		return this.vcContent;
	}
	
	public void setDtPublish(java.util.Date value) {
		this.dtPublishStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtPublish = value;
	}
	
	public java.util.Date getDtPublish() {
		return this.dtPublish;
	}
	
	public java.lang.String getDtPublishStr() {
		return this.dtPublishStr;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
	}

	public java.lang.Integer getImode() {
		return imode;
	}

	public void setImode(java.lang.Integer imode) {
		this.imode = imode;
	}

	
}

