package com.tzg.entitys.subjectplan;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class Subjectplan implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * iloginAccountId
     */ 	
	private java.lang.Integer iloginAccountId;
    /**
     * isubjectId
     */ 	
	private java.lang.Integer isubjectId;
    /**
     * 1 -- 本息；             2 -- 本金；             3 -- 利息。
     */ 	
	private java.lang.Integer itype;
    /**
     * numTotalPeriod
     */ 	
	private java.lang.Integer numTotalPeriod;
    /**
     * numCurrentPeriod
     */ 	
	private java.lang.Integer numCurrentPeriod;
    /**
     * 当期应还本金+当前应还利息
     */ 	
	private BigDecimal numRepayAmount;
    /**
     * numRepayCorpus
     */ 	
	private BigDecimal numRepayCorpus;
    /**
     * numRepayInterest
     */ 	
	private BigDecimal numRepayInterest;
    /**
     * dtRepay
     */ 	
	private java.util.Date dtRepay;
	private java.lang.String dtRepayStr;
    /**
     * 该条记录第一次创建的时间。
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIloginAccountId(java.lang.Integer value) {
		this.iloginAccountId = value;
	}
	
	public java.lang.Integer getIloginAccountId() {
		return this.iloginAccountId;
	}
	
	public void setIsubjectId(java.lang.Integer value) {
		this.isubjectId = value;
	}
	
	public java.lang.Integer getIsubjectId() {
		return this.isubjectId;
	}
	
	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}
	
	public java.lang.Integer getItype() {
		return this.itype;
	}
	
	public void setNumTotalPeriod(java.lang.Integer value) {
		this.numTotalPeriod = value;
	}
	
	public java.lang.Integer getNumTotalPeriod() {
		return this.numTotalPeriod;
	}
	
	public void setNumCurrentPeriod(java.lang.Integer value) {
		this.numCurrentPeriod = value;
	}
	
	public java.lang.Integer getNumCurrentPeriod() {
		return this.numCurrentPeriod;
	}
	
	public void setNumRepayAmount(BigDecimal value) {
		this.numRepayAmount = value;
	}
	
	public BigDecimal getNumRepayAmount() {
		return this.numRepayAmount;
	}
	
	public void setNumRepayCorpus(BigDecimal value) {
		this.numRepayCorpus = value;
	}
	
	public BigDecimal getNumRepayCorpus() {
		return this.numRepayCorpus;
	}
	
	public void setNumRepayInterest(BigDecimal value) {
		this.numRepayInterest = value;
	}
	
	public BigDecimal getNumRepayInterest() {
		return this.numRepayInterest;
	}
	
	public void setDtRepay(java.util.Date value) {
		this.dtRepayStr =DateUtil.getDate(value, "yyyy-MM-dd");
		this.dtRepay = value;
	}
	
	public java.util.Date getDtRepay() {
		return this.dtRepay;
	}
	
	public java.lang.String getDtRepayStr() {
		return this.dtRepayStr;
	}
	
	public void setDtCreate(java.util.Date value) {
		this.dtCreateStr =DateUtil.getDate(value, "yyyy-MM-dd");
		this.dtCreate = value;
	}
	
	public java.util.Date getDtCreate() {
		return this.dtCreate;
	}
	
	public java.lang.String getDtCreateStr() {
		return this.dtCreateStr;
	}

	
}

