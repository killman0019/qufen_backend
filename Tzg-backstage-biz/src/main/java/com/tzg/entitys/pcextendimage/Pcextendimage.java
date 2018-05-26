package com.tzg.entitys.pcextendimage;

import java.io.Serializable;

public class Pcextendimage implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 1--PC首页 2--注册页
     */ 	
	private java.lang.Integer iextendPlace;
    /**
     * 别名
     */ 	
	private java.lang.String vcName;
    /**
     * vcUrl
     */ 	
	private java.lang.String vcUrl;
    /**
     * vcImageUrl
     */ 	
	private java.lang.String vcImageUrl;
    /**
     * isortNo
     */ 	
	private java.lang.Integer isortNo;
    /**
     * istate
     */ 	
	private java.lang.Integer istate;
    /**
     * icategory
     */ 	
	private java.lang.Integer icategory;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIextendPlace(java.lang.Integer value) {
		this.iextendPlace = value;
	}
	
	public java.lang.Integer getIextendPlace() {
		return this.iextendPlace;
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
	
	public void setVcImageUrl(java.lang.String value) {
		this.vcImageUrl = value;
	}
	
	public java.lang.String getVcImageUrl() {
		return this.vcImageUrl;
	}
	
	public void setIsortNo(java.lang.Integer value) {
		this.isortNo = value;
	}
	
	public java.lang.Integer getIsortNo() {
		return this.isortNo;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
	}
	
	public void setIcategory(java.lang.Integer value) {
		this.icategory = value;
	}
	
	public java.lang.Integer getIcategory() {
		return this.icategory;
	}

	
}

