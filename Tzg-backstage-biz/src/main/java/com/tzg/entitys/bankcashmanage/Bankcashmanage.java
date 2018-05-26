package com.tzg.entitys.bankcashmanage;

import java.io.Serializable;
import java.math.BigDecimal;

public class Bankcashmanage implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * ibankInfoId
     */ 	
	private java.lang.Integer ibankInfoId;
	private java.lang.String vcbankInfoId;
	private java.lang.String vcName;
	private String vcShortName;
	private String vcBankCode;
    /**
     * 1:连连 2:易宝
     */ 	
	private java.lang.Integer ichannelType;
	private java.lang.String vcChannelType;
    /**
     * irateType
     */ 	
	private java.lang.Integer irateType;
    /**
     * numRate
     */ 	
	private BigDecimal numRate;
    /**
     * vcMemo
     */ 	
	private java.lang.String vcMemo;

	
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
	
	public java.lang.String getVcbankInfoId() {
		return vcbankInfoId;
	}

	public void setVcbankInfoId(java.lang.String vcbankInfoId) {
		this.vcbankInfoId = vcbankInfoId;
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
	
	public void setIrateType(java.lang.Integer value) {
		this.irateType = value;
	}
	
	public java.lang.Integer getIrateType() {
		return this.irateType;
	}
	
	public void setNumRate(BigDecimal value) {
		this.numRate = value;
	}
	
	public BigDecimal getNumRate() {
		return this.numRate;
	}
	
	public void setVcMemo(java.lang.String value) {
		this.vcMemo = value;
	}
	
	public java.lang.String getVcMemo() {
		return this.vcMemo;
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
	
}

