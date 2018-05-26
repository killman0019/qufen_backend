package com.tzg.entitys.projectsafeguard;

import java.io.Serializable;

public class Projectsafeguard implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * iprojectId
     */ 	
	private java.lang.Integer iprojectId;
    /**
     * vcName
     */ 	
	private java.lang.String vcName;
    /**
     * 系统参数指定的目录+文件名
     */ 	
	private java.lang.String vcUrl;
    /**
     * isort
     */ 	
	private java.lang.Integer isort;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIprojectId(java.lang.Integer value) {
		this.iprojectId = value;
	}
	
	public java.lang.Integer getIprojectId() {
		return this.iprojectId;
	}
	
	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}
	
	public java.lang.String getVcName() {
		return this.vcName;
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

	
}

