package com.tzg.entitys.promotechannel;

import java.io.Serializable;

public class Promotechannel implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * vcName
     */ 	
	private java.lang.String vcName;
    /**
     * vcCode
     */ 	
	private java.lang.String vcCode;
    /**
     * vcDesp
     */ 	
	private java.lang.String vcDesp;
    /**
     * vcMemo
     */ 	
	private java.lang.String vcMemo;
    /**
     * ios             android             pc             wap
     */ 	
	private java.lang.String vcType;
	private java.lang.String rownum;
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}
	
	public java.lang.String getVcName() {
		return this.vcName;
	}
	
	public void setVcCode(java.lang.String value) {
		this.vcCode = value;
	}
	
	public java.lang.String getVcCode() {
		return this.vcCode;
	}
	
	public void setVcDesp(java.lang.String value) {
		this.vcDesp = value;
	}
	
	public java.lang.String getVcDesp() {
		return this.vcDesp;
	}
	
	public void setVcMemo(java.lang.String value) {
		this.vcMemo = value;
	}
	
	public java.lang.String getVcMemo() {
		return this.vcMemo;
	}
	
	public void setVcType(java.lang.String value) {
		this.vcType = value;
	}
	
	public java.lang.String getVcType() {
		return this.vcType;
	}

	public java.lang.String getRownum() {
		return rownum;
	}

	public void setRownum(java.lang.String rownum) {
		this.rownum = rownum;
	}

	
}

