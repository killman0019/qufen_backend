package com.tzg.entitys.rocketmqlog;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Rocketmqlog implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id 
     */ 	
	private java.lang.Integer id;
    /**
     * vcTopic 
     */ 	
	private java.lang.String vcTopic;
    /**
     * vcTag
     */ 	
	private java.lang.String vcTag;
    /**
     * vcKey
     */ 	
	private java.lang.String vcKey;
    /**
     * vcBody
     */ 	
	private java.lang.String vcBody;
    /**
     * dtCreate 创建时间
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * vcSender 发送者
     */ 	
	private java.lang.String vcSender;
    /**
     * dtSend 发送时间
     */ 	
	private java.util.Date dtSend;
	private java.lang.String dtSendStr;
    /**
     * isendState 发送状态
     */ 	
	private java.lang.String vcSendState;
    /**
     * vcReceiver 接收者
     */ 	
	private java.lang.String vcReceiver;
    /**
     * dtReceive接收时间
     */ 	
	private java.util.Date dtReceive;
	private java.lang.String dtReceiveStr;
    /**
     * ireceiveState 接收状态
     */ 	
	private java.lang.String vcReceiveState;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcTopic(java.lang.String value) {
		this.vcTopic = value;
	}
	
	public java.lang.String getVcTopic() {
		return this.vcTopic;
	}
	
	public void setVcTag(java.lang.String value) {
		this.vcTag = value;
	}
	
	public java.lang.String getVcTag() {
		return this.vcTag;
	}
	
	public void setVcKey(java.lang.String value) {
		this.vcKey = value;
	}
	
	public java.lang.String getVcKey() {
		return this.vcKey;
	}
	
	public void setVcBody(java.lang.String value) {
		this.vcBody = value;
	}
	
	public java.lang.String getVcBody() {
		return this.vcBody;
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
	
	public void setVcSender(java.lang.String value) {
		this.vcSender = value;
	}
	
	public java.lang.String getVcSender() {
		return this.vcSender;
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
	
	
	public void setVcReceiver(java.lang.String value) {
		this.vcReceiver = value;
	}
	
	public java.lang.String getVcReceiver() {
		return this.vcReceiver;
	}
	
	public void setDtReceive(java.util.Date value) {
		this.dtReceiveStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtReceive = value;
	}
	
	public java.util.Date getDtReceive() {
		return this.dtReceive;
	}
	
	public java.lang.String getDtReceiveStr() {
		return this.dtReceiveStr;
	}

	public java.lang.String getVcSendState() {
		return vcSendState;
	}

	public void setVcSendState(java.lang.String vcSendState) {
		this.vcSendState = vcSendState;
	}

	public java.lang.String getVcReceiveState() {
		return vcReceiveState;
	}

	public void setVcReceiveState(java.lang.String vcReceiveState) {
		this.vcReceiveState = vcReceiveState;
	}
	

	
}

