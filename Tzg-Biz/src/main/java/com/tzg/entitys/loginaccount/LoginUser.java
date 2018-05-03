package com.tzg.entitys.loginaccount;

import java.io.Serializable;
import java.util.Date;

import com.tzg.common.utils.DateUtil;

public class LoginUser implements Serializable {
	private static final long serialVersionUID = 1L;
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
     * 登陆密码
     */ 	
	private java.lang.String vcPassword;


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


	public java.lang.String getVcPassword() {
		return vcPassword;
	}


	public void setVcPassword(java.lang.String vcPassword) {
		this.vcPassword = vcPassword;
	}
    
	
	
	
	
	
	
	
	
}

