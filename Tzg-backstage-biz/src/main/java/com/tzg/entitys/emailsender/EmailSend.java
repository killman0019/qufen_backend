package com.tzg.entitys.emailsender;

import java.io.Serializable;

public class EmailSend implements Serializable {
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
	 * vcEmail
	 */
	private java.lang.String vcEmail;
    /**
     * vcTitle
     */ 	
	private java.lang.String vcTitle;
    /**
     * vcContent
     */ 	
	private java.lang.String vcContent;
	private java.util.Date dtCreate;
	private java.lang.String vcMemo;
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getImanageId() {
		return imanageId;
	}
	public void setImanageId(java.lang.Integer imanageId) {
		this.imanageId = imanageId;
	}
	public java.lang.Integer getIloginAccountId() {
		return iloginAccountId;
	}
	public void setIloginAccountId(java.lang.Integer iloginAccountId) {
		this.iloginAccountId = iloginAccountId;
	}
	public java.lang.String getVcEmail() {
		return vcEmail;
	}
	public void setVcEmail(java.lang.String vcEmail) {
		this.vcEmail = vcEmail;
	}
	public java.lang.String getVcTitle() {
		return vcTitle;
	}
	public void setVcTitle(java.lang.String vcTitle) {
		this.vcTitle = vcTitle;
	}
	public java.lang.String getVcContent() {
		return vcContent;
	}
	public void setVcContent(java.lang.String vcContent) {
		this.vcContent = vcContent;
	}
	public java.util.Date getDtCreate() {
		return dtCreate;
	}
	public void setDtCreate(java.util.Date dtCreate) {
		this.dtCreate = dtCreate;
	}
	public java.lang.String getVcMemo() {
		return vcMemo;
	}
	public void setVcMemo(java.lang.String vcMemo) {
		this.vcMemo = vcMemo;
	}
	
}

