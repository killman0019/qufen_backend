package com.tzg.entitys.smssend;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Smssend implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * vcPhone
     */ 	
	private java.lang.String vcPhone;
    /**
     * vcContent
     */ 	
	private java.lang.String vcContent;
    /**
     * 1 -- 通知；             2 -- 验证码。
     */ 	
	private java.lang.Integer itype;
    /**
     * dtCreate
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * 1--优先；             2--正常。             目的：系统正常跑短信的时候，优先发送优先等级为1的短信。允许优先等级为1的短信插队发送。
     */ 	
	private java.lang.Integer ipriority;
    /**
     * vcMemo
     */ 	
	private java.lang.String vcMemo;
	 /**
     * 短信类型:1:文本;2:语音
     */ 
	private java.lang.Integer sType;
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcPhone(java.lang.String value) {
		this.vcPhone = value;
	}
	
	public java.lang.String getVcPhone() {
		return this.vcPhone;
	}
	
	public void setVcContent(java.lang.String value) {
		this.vcContent = value;
	}
	
	public java.lang.String getVcContent() {
		return this.vcContent;
	}
	
	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}
	
	public java.lang.Integer getItype() {
		return this.itype;
	}
	
	public void setDtCreate(java.util.Date value) {
		this.dtCreateStr =DateUtil.getDate(value, "yyyy-MM-dd");
		this.dtCreate = value;
	}
	
	public java.util.Date getDtCreate() {
		return this.dtCreate;
	}
	
	public java.lang.String getDtCreateStr() {
		return this.dtCreateStr;
	}
	
	public void setIpriority(java.lang.Integer value) {
		this.ipriority = value;
	}
	
	public java.lang.Integer getIpriority() {
		return this.ipriority;
	}
	
	public void setVcMemo(java.lang.String value) {
		this.vcMemo = value;
	}
	
	public java.lang.String getVcMemo() {
		return this.vcMemo;
	}

	public java.lang.Integer getsType() {
		return sType;
	}

	public void setsType(java.lang.Integer sType) {
		this.sType = sType;
	}

	
}

