package com.tzg.entitys.guaranteeaccount;

import java.io.Serializable;
import java.math.BigDecimal;

public class Guaranteeaccount implements Serializable {
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
     * numAvailable
     */ 	
	private BigDecimal numAvailable;
    /**
     * numTotalBorrow
     */ 	
	private BigDecimal numTotalBorrow;
    /**
     * numTotalFee
     */ 	
	private BigDecimal numTotalFee;
    /**
     * numTotalCash
     */ 	
	private BigDecimal numTotalCash;
    /**
     * numTotalRecharge
     */ 	
	private BigDecimal numTotalRecharge;
    /**
     * 担保中待还金额
     */ 	
	private BigDecimal numInsured;
    /**
     * numOverdue
     */ 	
	private BigDecimal numOverdue;
    /**
     * numTotalAdvance
     */ 	
	private BigDecimal numTotalAdvance;
    /**
     * 1 -- 有效；             2 -- 冻结中；             3 -- 无效。
     */ 	
	private java.lang.Integer istatus;

	
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
	
	public void setNumAvailable(BigDecimal value) {
		this.numAvailable = value;
	}
	
	public BigDecimal getNumAvailable() {
		return this.numAvailable;
	}
	
	public void setNumTotalBorrow(BigDecimal value) {
		this.numTotalBorrow = value;
	}
	
	public BigDecimal getNumTotalBorrow() {
		return this.numTotalBorrow;
	}
	
	public void setNumTotalFee(BigDecimal value) {
		this.numTotalFee = value;
	}
	
	public BigDecimal getNumTotalFee() {
		return this.numTotalFee;
	}
	
	public void setNumTotalCash(BigDecimal value) {
		this.numTotalCash = value;
	}
	
	public BigDecimal getNumTotalCash() {
		return this.numTotalCash;
	}
	
	public void setNumTotalRecharge(BigDecimal value) {
		this.numTotalRecharge = value;
	}
	
	public BigDecimal getNumTotalRecharge() {
		return this.numTotalRecharge;
	}
	
	public void setNumInsured(BigDecimal value) {
		this.numInsured = value;
	}
	
	public BigDecimal getNumInsured() {
		return this.numInsured;
	}
	
	public void setNumOverdue(BigDecimal value) {
		this.numOverdue = value;
	}
	
	public BigDecimal getNumOverdue() {
		return this.numOverdue;
	}
	
	public void setNumTotalAdvance(BigDecimal value) {
		this.numTotalAdvance = value;
	}
	
	public BigDecimal getNumTotalAdvance() {
		return this.numTotalAdvance;
	}
	
	public void setIstatus(java.lang.Integer value) {
		this.istatus = value;
	}
	
	public java.lang.Integer getIstatus() {
		return this.istatus;
	}

	
}

