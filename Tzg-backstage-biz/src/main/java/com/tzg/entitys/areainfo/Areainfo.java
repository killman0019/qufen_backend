package com.tzg.entitys.areainfo;

import java.io.Serializable;

public class Areainfo implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * vcCode
     */ 	
	private java.lang.String vcCode;
    /**
     * vcName
     */ 	
	private java.lang.String vcName;
    /**
     * isort
     */ 	
	private java.lang.Integer isort;
    /**
     * 是否有效。             1 -- 有效 （默认）             2 -- 无效
     */ 	
	private java.lang.Integer ivalid;
    /**
     * vcParentCode
     */ 	
	private java.lang.String vcParentCode;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcCode(java.lang.String value) {
		this.vcCode = value;
	}
	
	public java.lang.String getVcCode() {
		return this.vcCode;
	}
	
	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}
	
	public java.lang.String getVcName() {
		return this.vcName;
	}
	
	public void setIsort(java.lang.Integer value) {
		this.isort = value;
	}
	
	public java.lang.Integer getIsort() {
		return this.isort;
	}
	
	public void setIvalid(java.lang.Integer value) {
		this.ivalid = value;
	}
	
	public java.lang.Integer getIvalid() {
		return this.ivalid;
	}
	
	public void setVcParentCode(java.lang.String value) {
		this.vcParentCode = value;
	}
	
	public java.lang.String getVcParentCode() {
		return this.vcParentCode;
	}

	
	
}

