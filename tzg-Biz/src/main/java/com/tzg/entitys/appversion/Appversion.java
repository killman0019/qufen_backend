package com.tzg.entitys.appversion;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Appversion implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 1 -- 安卓；             2 -- 苹果。
     */ 	
	private java.lang.Integer itype;
    /**
     * vcVersion
     */ 	
	private java.lang.String vcVersion;
    /**
     * 1 -- 不强制升级;             2 -- 强制升级。
     */ 	
	private java.lang.Integer ilevelType;
    /**
     * vcPackageUrl
     */ 	
	private java.lang.String vcPackageUrl;
	
	private java.lang.String vcVersionDesc;
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
	
	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}
	
	public java.lang.Integer getItype() {
		return this.itype;
	}
	
	public void setVcVersion(java.lang.String value) {
		this.vcVersion = value;
	}
	
	public java.lang.String getVcVersion() {
		return this.vcVersion;
	}
	
	public void setIlevelType(java.lang.Integer value) {
		this.ilevelType = value;
	}
	
	public java.lang.Integer getIlevelType() {
		return this.ilevelType;
	}
	
	public void setVcPackageUrl(java.lang.String value) {
		this.vcPackageUrl = value;
	}
	
	public java.lang.String getVcPackageUrl() {
		return this.vcPackageUrl;
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

	public java.lang.String getVcVersionDesc() {
		return vcVersionDesc;
	}

	public void setVcVersionDesc(java.lang.String vcVersionDesc) {
		this.vcVersionDesc = vcVersionDesc;
	}

	
}

