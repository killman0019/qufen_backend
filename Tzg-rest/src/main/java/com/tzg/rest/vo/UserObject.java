package com.tzg.rest.vo;

import java.io.Serializable;

public class UserObject implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 4649308613299958387L;
	
	 /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 登陆账号
     */ 	
	private java.lang.String vcLoginName;
    /**
     * 手机号
     */ 	
	private java.lang.String vcPhone;
    /**
     * 邮箱账号
     */ 	
	private java.lang.String vcEmail;
	private java.lang.Integer itype;
	/**
     * 是否修改过密码 1 -- 修改密码；             2 -- 未修改过密码。             只有投资人注册的账号默认为1。             其它账号类型刚生成账号时都默认为2，此类型的账号在登陆系统之后强制修改密码和交易密码
     */ 	
	private java.lang.Integer ioriginal;
    /**
     * 是否手机认证 1:已认证，2未认证
     */ 	
	private java.lang.Integer iverifyPhone;
    /**
     * 是否邮箱认证 1:已认证，2未认证
     */ 	
	private java.lang.Integer iverifyEmail;
    /**
     * 是否实名认证 1:已认证，2未认证
     */ 	
	private java.lang.Integer iverifyIdcard;
	 /**
     * 姓名
     */ 	
	private java.lang.String vcName;
	 /**
     * 证件号码
     */ 	
	private java.lang.String vcCardCode;
	
	/**
	 * 快捷支付密码
	 */
	private String vcFastPayPassword;
	
	/**
     * 支付密码
     */ 	
	private java.lang.String vcPaymentcipher;

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getVcLoginName() {
		return vcLoginName;
	}

	public void setVcLoginName(java.lang.String vcLoginName) {
		this.vcLoginName = vcLoginName;
	}

	public java.lang.String getVcPhone() {
		return vcPhone;
	}

	public void setVcPhone(java.lang.String vcPhone) {
		this.vcPhone = vcPhone;
	}

	public java.lang.String getVcEmail() {
		return vcEmail;
	}

	public void setVcEmail(java.lang.String vcEmail) {
		this.vcEmail = vcEmail;
	}

	public java.lang.Integer getItype() {
		return itype;
	}

	public void setItype(java.lang.Integer itype) {
		this.itype = itype;
	}

	public java.lang.Integer getIoriginal() {
		return ioriginal;
	}

	public void setIoriginal(java.lang.Integer ioriginal) {
		this.ioriginal = ioriginal;
	}

	public java.lang.Integer getIverifyPhone() {
		return iverifyPhone;
	}

	public void setIverifyPhone(java.lang.Integer iverifyPhone) {
		this.iverifyPhone = iverifyPhone;
	}

	public java.lang.Integer getIverifyEmail() {
		return iverifyEmail;
	}

	public void setIverifyEmail(java.lang.Integer iverifyEmail) {
		this.iverifyEmail = iverifyEmail;
	}

	public java.lang.Integer getIverifyIdcard() {
		return iverifyIdcard;
	}

	public void setIverifyIdcard(java.lang.Integer iverifyIdcard) {
		this.iverifyIdcard = iverifyIdcard;
	}

	public java.lang.String getVcName() {
		return vcName;
	}

	public void setVcName(java.lang.String vcName) {
		this.vcName = vcName;
	}

	public java.lang.String getVcCardCode() {
		return vcCardCode;
	}

	public void setVcCardCode(java.lang.String vcCardCode) {
		this.vcCardCode = vcCardCode;
	}

	public String getVcFastPayPassword() {
		return vcFastPayPassword;
	}

	public void setVcFastPayPassword(String vcFastPayPassword) {
		this.vcFastPayPassword = vcFastPayPassword;
	}

	public java.lang.String getVcPaymentcipher() {
		return vcPaymentcipher;
	}

	public void setVcPaymentcipher(java.lang.String vcPaymentcipher) {
		this.vcPaymentcipher = vcPaymentcipher;
	}
	
	
}
