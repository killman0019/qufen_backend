package com.tzg.entitys.institution;

import java.io.Serializable;

public class Institution implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 从上传资料中选取
     */ 	
	private java.lang.String vcLogoUrl;
    /**
     * vcName
     */ 	
	private java.lang.String vcName;
    /**
     * vcDesc
     */ 	
	private java.lang.String vcDesc;
    /**
     * vcUrl
     */ 	
	private java.lang.String vcUrl;
    /**
     * isort
     */ 	
	private java.lang.Integer isort;
    /**
     * 1-- 有效；             2-- 无效。
     */ 	
	private java.lang.Integer istate;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcLogoUrl(java.lang.String value) {
		this.vcLogoUrl = value;
	}
	
	public java.lang.String getVcLogoUrl() {
		return this.vcLogoUrl;
	}
	
	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}
	
	public java.lang.String getVcName() {
		return this.vcName;
	}
	
	public void setVcDesc(java.lang.String value) {
		this.vcDesc = value;
	}
	
	public java.lang.String getVcDesc() {
		return this.vcDesc;
	}
	
	public void setVcUrl(java.lang.String value) {
		this.vcUrl = value;
	}
	
	public java.lang.String getVcUrl() {
		return this.vcUrl;
	}
	
	public void setIsort(java.lang.Integer value) {
		this.isort = value;
	}
	
	public java.lang.Integer getIsort() {
		return this.isort;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
	}

	
}

