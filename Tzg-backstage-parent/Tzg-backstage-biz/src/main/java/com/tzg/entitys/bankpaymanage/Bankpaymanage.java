package com.tzg.entitys.bankpaymanage;

import java.io.Serializable;
import java.math.BigDecimal;

public class Bankpaymanage implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * ibankInfoId
     */ 	
	private java.lang.Integer ibankInfoId;
	private java.lang.String vcName;
	private String vcShortName;
	private String vcBankCode;
    /**
     * 1:连连 2:易宝
     */ 	
	private java.lang.Integer ichannelType;
	private java.lang.String vcChannelType;
    /**
     * 1:pc 2:wap 3:ios 4:android
     */ 	
	private java.lang.Integer iterminalType;
    /**
     * 1:网银 2:认证
     */ 	
	private java.lang.Integer ipayType;
	private java.lang.String vcPayType;
    /**
     * 升序
     */ 	
	private java.lang.Integer ipriority;
	/**
     * irateType
     */ 	
	private java.lang.Integer irateType;
    /**
     * numRate
     */ 	
	private BigDecimal numRate;
    /**
     * vcSingleLimit
     */ 	
	private BigDecimal numSingleLimit;
    /**
     * vcDayLimit
     */ 	
	private BigDecimal numDayLimit;
    /**
     * vcMonthLimit
     */ 	
	private BigDecimal numMonthLimit;
    /**
     * vcMemo
     */ 	
	private java.lang.String vcMemo;
    /**
     * 升序
     */ 	
	private java.lang.Integer isort;
    /**
     * 1:开通 2:关闭
     */ 	
	private java.lang.Integer istate;

	private String numSingleLimitStr;
	private String numDayLimitStr;
	private String numMonthLimitStr;
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIbankInfoId(java.lang.Integer value) {
		this.ibankInfoId = value;
	}
	
	public java.lang.Integer getIbankInfoId() {
		return this.ibankInfoId;
	}
	



	public java.lang.String getVcName() {
		return vcName;
	}

	public void setVcName(java.lang.String vcName) {
		this.vcName = vcName;
	}

	public String getVcShortName() {
		return vcShortName;
	}

	public void setVcShortName(String vcShortName) {
		this.vcShortName = vcShortName;
	}

	public String getVcBankCode() {
		return vcBankCode;
	}

	public void setVcBankCode(String vcBankCode) {
		this.vcBankCode = vcBankCode;
	}

	public void setIchannelType(java.lang.Integer value) {
		this.ichannelType = value;
		if(value==1){
			this.vcChannelType = "连连支付";
		}else if(value==2){
			this.vcChannelType = "易宝支付";
		}
	}
	
	public java.lang.Integer getIchannelType() {
		return this.ichannelType;
	}
	
	public java.lang.String getVcChannelType() {
		return vcChannelType;
	}

	public void setIterminalType(java.lang.Integer value) {
		this.iterminalType = value;
	}
	
	public java.lang.Integer getIterminalType() {
		return this.iterminalType;
	}
	
	public void setIpayType(java.lang.Integer value) {
		this.ipayType = value;
		if(value==1){
			this.vcPayType = "网银支付";
		}else if(value==2){
			this.vcPayType = "认证支付";
		}
	}
	
	public java.lang.Integer getIpayType() {
		return this.ipayType;
	}
	
	public java.lang.String getVcPayType() {
		return vcPayType;
	}

	public void setIpriority(java.lang.Integer value) {
		this.ipriority = value;
	}
	
	public java.lang.Integer getIpriority() {
		return this.ipriority;
	}
	
	public java.lang.Integer getIrateType() {
		return irateType;
	}

	public void setIrateType(java.lang.Integer irateType) {
		this.irateType = irateType;
	}

	public void setNumRate(BigDecimal value) {
		this.numRate = value;
	}
	
	public BigDecimal getNumRate() {
		return this.numRate;
	}
	
	
	

	public BigDecimal getNumSingleLimit() {
		return numSingleLimit;
	}

	public void setNumSingleLimit(BigDecimal numSingleLimit) {
		this.numSingleLimit = numSingleLimit;
	}

	public BigDecimal getNumDayLimit() {
		return numDayLimit;
	}

	public void setNumDayLimit(BigDecimal numDayLimit) {
		this.numDayLimit = numDayLimit;
	}

	public BigDecimal getNumMonthLimit() {
		return numMonthLimit;
	}

	public void setNumMonthLimit(BigDecimal numMonthLimit) {
		this.numMonthLimit = numMonthLimit;
	}

	public void setVcMemo(java.lang.String value) {
		this.vcMemo = value;
	}
	
	public java.lang.String getVcMemo() {
		return this.vcMemo;
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

	public String getNumSingleLimitStr() {
		return numSingleLimitStr;
	}

	public void setNumSingleLimitStr(String numSingleLimitStr) {
		this.numSingleLimitStr = numSingleLimitStr;
	}

	public String getNumDayLimitStr() {
		return numDayLimitStr;
	}

	public void setNumDayLimitStr(String numDayLimitStr) {
		this.numDayLimitStr = numDayLimitStr;
	}

	public String getNumMonthLimitStr() {
		return numMonthLimitStr;
	}

	public void setNumMonthLimitStr(String numMonthLimitStr) {
		this.numMonthLimitStr = numMonthLimitStr;
	}
}

