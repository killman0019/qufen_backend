package com.tzg.entitys.activereward;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Activereward implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 活动id
     */ 	
	private java.lang.Integer iactiveId;
    /**
     * 奖励等级
     */ 	
	private java.lang.Integer ilevel;
    /**
     * 奖励名称
     */ 	
	private java.lang.String vcName;
    /**
     * 奖励数量
     */ 	
	private java.lang.Integer allNum;
    /**
     * 已发送数量
     */ 	
	private java.lang.Integer sendNum;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIactiveId(java.lang.Integer value) {
		this.iactiveId = value;
	}
	
	public java.lang.Integer getIactiveId() {
		return this.iactiveId;
	}
	
	public void setIlevel(java.lang.Integer value) {
		this.ilevel = value;
	}
	
	public java.lang.Integer getIlevel() {
		return this.ilevel;
	}
	
	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}
	
	public java.lang.String getVcName() {
		return this.vcName;
	}
	
	public void setAllNum(java.lang.Integer value) {
		this.allNum = value;
	}
	
	public java.lang.Integer getAllNum() {
		return this.allNum;
	}
	
	public void setSendNum(java.lang.Integer value) {
		this.sendNum = value;
	}
	
	public java.lang.Integer getSendNum() {
		return this.sendNum;
	}

	
}

