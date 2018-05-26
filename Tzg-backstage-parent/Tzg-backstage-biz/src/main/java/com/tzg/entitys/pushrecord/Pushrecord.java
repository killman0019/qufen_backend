package com.tzg.entitys.pushrecord;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Pushrecord implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * vcContent
     */ 	
	private java.lang.String vcContent;
    /**
     * vcPagetype
     */ 	
	private java.lang.Integer vcPagetype;
    /**
     * vcPlatform
     */ 	
	private java.lang.String vcPlatform;
    /**
     * vcAudience
     */ 	
	private java.lang.String vcAudience;
    /**
     * dtCreate
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcContent(java.lang.String value) {
		this.vcContent = value;
	}
	
	public java.lang.String getVcContent() {
		return this.vcContent;
	}
	
	public void setVcPagetype(java.lang.Integer value) {
		this.vcPagetype = value;
	}
	
	public java.lang.Integer getVcPagetype() {
		return this.vcPagetype;
	}
	
	public void setVcPlatform(java.lang.String value) {
		this.vcPlatform = value;
	}
	
	public java.lang.String getVcPlatform() {
		return this.vcPlatform;
	}
	
	public void setVcAudience(java.lang.String value) {
		this.vcAudience = value;
	}
	
	public java.lang.String getVcAudience() {
		return this.vcAudience;
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

	
}

