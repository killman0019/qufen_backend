package com.tzg.entitys.smsrecord;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Smsrecord implements Serializable {
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
     * dtSend
     */ 	
	private java.util.Date dtSend;
	private java.lang.String dtSendStr;
    /**
     * 1:成功；2：失败
     */ 	
	private java.lang.Integer istate;
    /**
     * vcMemo
     */ 	
	private java.lang.String vcMemo;
    /**
     * 短信接口:漫道;建周
     */ 	
	private java.lang.String sinterface;
    /**
     * 短信类型:1:文本;2:语音
     */ 	
	private java.lang.Integer stype;

	/**
	 * 运营商返回结果
	 */
	private java.lang.String sResult;
	/**
	 * 运营商返回状态码
	 */
	private java.lang.String sState;
	
	public java.lang.String getsResult() {
		return sResult;
	}

	public void setsResult(java.lang.String sResult) {
		this.sResult = sResult;
	}

	public java.lang.String getsState() {
		return sState;
	}

	public void setsState(java.lang.String sState) {
		this.sState = sState;
	}

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
		this.dtCreateStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
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
	
	public void setDtSend(java.util.Date value) {
		this.dtSendStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtSend = value;
	}
	
	public java.util.Date getDtSend() {
		return this.dtSend;
	}
	
	public java.lang.String getDtSendStr() {
		return this.dtSendStr;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
	}
	
	public void setVcMemo(java.lang.String value) {
		this.vcMemo = value;
	}
	
	public java.lang.String getVcMemo() {
		return this.vcMemo;
	}
	
	public void setSinterface(java.lang.String value) {
		this.sinterface = value;
	}
	
	public java.lang.String getSinterface() {
		return this.sinterface;
	}
	
	public void setStype(java.lang.Integer value) {
		this.stype = value;
	}
	
	public java.lang.Integer getStype() {
		return this.stype;
	}

	
}

