package com.tzg.entitys.kff.mobileversionupdate;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Mobileversionupdate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6128090009533321882L;
	/**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     *  IOS APPSTORE  ANDROID
     */ 	
	private java.lang.String mtype;
    /**
     * 新版本主版本号
     */ 	
	private java.lang.Integer baseVersion;
    /**
     * 新版本次版本号
     */ 	
	private java.lang.Integer alphaVersion;
    /**
     * 新版本修订版本号
     */ 	
	private java.lang.Integer betaVersion;
    /**
     * 强制升级主版本号
     */ 	
	private java.lang.Integer fbaseVersion;
    /**
     * 强制升级次版本号
     */ 	
	private java.lang.Integer falphaVersion;
    /**
     * 强制升级修订版本号
     */ 	
	private java.lang.Integer fbetaVersion;
    /**
     * 升级apk地址
     */ 	
	private java.lang.String upgradeUrl;
    /**
     * 强制升级apk地址
     */ 	
	private java.lang.String guideUrl;
    /**
     * 更新内容描述
     */ 	
	private java.lang.String upexplain;
    /**
     * 是否生效 1 生效 0 失效
     */ 	
	private java.lang.Integer menable;
    /**
     * 描述信息
     */ 	
	private java.lang.String memo;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setMtype(java.lang.String value) {
		this.mtype = value;
	}
	
	public java.lang.String getMtype() {
		return this.mtype;
	}
	
	public void setBaseVersion(java.lang.Integer value) {
		this.baseVersion = value;
	}
	
	public java.lang.Integer getBaseVersion() {
		return this.baseVersion;
	}
	
	public void setAlphaVersion(java.lang.Integer value) {
		this.alphaVersion = value;
	}
	
	public java.lang.Integer getAlphaVersion() {
		return this.alphaVersion;
	}
	
	public void setBetaVersion(java.lang.Integer value) {
		this.betaVersion = value;
	}
	
	public java.lang.Integer getBetaVersion() {
		return this.betaVersion;
	}
	
	public void setFbaseVersion(java.lang.Integer value) {
		this.fbaseVersion = value;
	}
	
	public java.lang.Integer getFbaseVersion() {
		return this.fbaseVersion;
	}
	
	public void setFalphaVersion(java.lang.Integer value) {
		this.falphaVersion = value;
	}
	
	public java.lang.Integer getFalphaVersion() {
		return this.falphaVersion;
	}
	
	public void setFbetaVersion(java.lang.Integer value) {
		this.fbetaVersion = value;
	}
	
	public java.lang.Integer getFbetaVersion() {
		return this.fbetaVersion;
	}
	
	public void setUpgradeUrl(java.lang.String value) {
		this.upgradeUrl = value;
	}
	
	public java.lang.String getUpgradeUrl() {
		return this.upgradeUrl;
	}
	
	public void setGuideUrl(java.lang.String value) {
		this.guideUrl = value;
	}
	
	public java.lang.String getGuideUrl() {
		return this.guideUrl;
	}
	
	public void setUpexplain(java.lang.String value) {
		this.upexplain = value;
	}
	
	public java.lang.String getUpexplain() {
		return this.upexplain;
	}
	
	public void setMenable(java.lang.Integer value) {
		this.menable = value;
	}
	
	public java.lang.Integer getMenable() {
		return this.menable;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	public java.lang.String getMemo() {
		return this.memo;
	}

	
}

