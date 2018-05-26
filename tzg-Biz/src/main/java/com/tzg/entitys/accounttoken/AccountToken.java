package com.tzg.entitys.accounttoken;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class AccountToken implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */ 	
	private java.lang.Integer id;
    /**
     * 登录用户ID
     */ 	
	private java.lang.Integer accountId;
    /**
     * 终端唯一串号
     */ 	
	private java.lang.String secId;
    /**
     * 用户Key
     */ 	
	private java.lang.String secKey;
    /**
     * 访问标识
     */ 	
	private java.lang.String token;
    /**
     * [登录时间]
     */ 	
	private java.util.Date dtLogin;
	private java.lang.String dtLoginStr;
    /**
     * [登录平台]
     */ 	
	private java.lang.String vcLoginDevice;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setAccountId(java.lang.Integer value) {
		this.accountId = value;
	}
	
	public java.lang.Integer getAccountId() {
		return this.accountId;
	}
	
	public void setSecId(java.lang.String value) {
		this.secId = value;
	}
	
	public java.lang.String getSecId() {
		return this.secId;
	}
	
	public void setSecKey(java.lang.String value) {
		this.secKey = value;
	}
	
	public java.lang.String getSecKey() {
		return this.secKey;
	}
	
	public void setToken(java.lang.String value) {
		this.token = value;
	}
	
	public java.lang.String getToken() {
		return this.token;
	}
	
	public void setDtLogin(java.util.Date value) {
		this.dtLoginStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtLogin = value;
	}
	
	public java.util.Date getDtLogin() {
		return this.dtLogin;
	}
	
	public java.lang.String getDtLoginStr() {
		return this.dtLoginStr;
	}
	
	public void setVcLoginDevice(java.lang.String value) {
		this.vcLoginDevice = value;
	}
	
	public java.lang.String getVcLoginDevice() {
		return this.vcLoginDevice;
	}
	
}

