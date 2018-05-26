package com.tzg.entitys.lowlevelinvest;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Lowlevelinvest implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * [主键]
     */ 	
	private java.lang.Integer id;
    /**
     * [人人赚帐号编码]
     */ 	
	private java.lang.Integer ipyramidAccountId;
    /**
     * [一级下线人数]
     */ 	
	private java.lang.Integer ioneLevel;
    /**
     * [一级下线投资人数]
     */ 	
	private java.lang.Integer ioneLevelInvest;
    /**
     * [二级下线人数]
     */ 	
	private java.lang.Integer itwoLevel;
    /**
     * [二级下线投资人数]
     */ 	
	private java.lang.Integer itwoLevelInvest;
    /**
     * [统计时间]
     */ 	
	private java.util.Date dtCount;
	private java.lang.String dtCountStr;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIpyramidAccountId(java.lang.Integer value) {
		this.ipyramidAccountId = value;
	}
	
	public java.lang.Integer getIpyramidAccountId() {
		return this.ipyramidAccountId;
	}
	
	public void setIoneLevel(java.lang.Integer value) {
		this.ioneLevel = value;
	}
	
	public java.lang.Integer getIoneLevel() {
		return this.ioneLevel;
	}
	
	public void setIoneLevelInvest(java.lang.Integer value) {
		this.ioneLevelInvest = value;
	}
	
	public java.lang.Integer getIoneLevelInvest() {
		return this.ioneLevelInvest;
	}
	
	public void setItwoLevel(java.lang.Integer value) {
		this.itwoLevel = value;
	}
	
	public java.lang.Integer getItwoLevel() {
		return this.itwoLevel;
	}
	
	public void setItwoLevelInvest(java.lang.Integer value) {
		this.itwoLevelInvest = value;
	}
	
	public java.lang.Integer getItwoLevelInvest() {
		return this.itwoLevelInvest;
	}
	
	public void setDtCount(java.util.Date value) {
		this.dtCountStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtCount = value;
	}
	
	public java.util.Date getDtCount() {
		return this.dtCount;
	}
	
	public java.lang.String getDtCountStr() {
		return this.dtCountStr;
	}

	
}

