package com.tzg.entitys.cash;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.Numbers;

public class Cash implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * iloginAccountId
     */ 	
	private java.lang.Integer iloginAccountId;
	private java.lang.String iloginAccountName;
	private java.lang.String iloginAccountPhone;
   

	

	/**
     * numCash
     */ 	
	private BigDecimal numCash;
    /**
     * dtCash
     */ 	
	private java.util.Date dtCash;
	private java.lang.String dtCashStr;
    /**
     * vcCardNo
     */ 	
	private java.lang.String vcCardNo;
    /**
     * vcCardBank
     */ 	
	private java.lang.String vcCardBank;
    /**
     * 开户行所在城市编码
     */ 	
	private java.lang.String cityCode;
	private java.lang.String provinceName; //省份
	private java.lang.String cityName;    //城市
    /** 
     * 开户支行名称
     */ 	
	private java.lang.String brabankName;
    /**
     * numCashFee
     */ 	
	private BigDecimal numCashFee;
    /**
     * 1--处理中； 2--处理成功； 3--处理失败。
     */ 	
	private java.lang.Integer istate;
    /**
     * 该条记录第一次创建的时间。
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * vcBillCode
     */ 	
	private java.lang.String vcBillCode;
    /**
     * 1 -- 借款人； 2 -- 投资人； 3 -- 担保机构； 4 -- 平台账户； 5-- 渠道。
     */ 	
	private java.lang.Integer itype;
    /**
     * 1、认证； 2、网银。
     */ 	
	private java.lang.Integer vcPayType;
    /**
     * vcResultBillCode
     */ 	
	private java.lang.String vcResultBillCode;

	private java.lang.Integer ifinancialCashFailureId;
	
	
	private java.lang.Integer iExport;
	private java.util.Date dtExport;
	/**
	 * 提现时用的设备 pc,wap,ios,android
	 */
	private String vcCashDevice;
	private java.lang.Integer iChannelType;
	private String vcUserIP;
	private String vcResultDesc;
	/**
	 * 提现金额统计
	 */
	private BigDecimal totalnumCash;
	/**
	 * 提现手续费统计
	 */
	private BigDecimal totalnumCashFee;
	
	
	public java.lang.String getIloginAccountPhone() {
		return iloginAccountPhone;
	}

	public void setIloginAccountPhone(java.lang.String iloginAccountPhone) {
		this.iloginAccountPhone = iloginAccountPhone;
	}
	
	   /**
     * 实际提现金额
     */ 	
	@SuppressWarnings("unused")
	private BigDecimal numCashActual;
	
	public BigDecimal getNumCashActual() {
		return Numbers.subtract(numCash, numCashFee);
	}

	public void setNumCashActual(BigDecimal numCashActual) {
		this.numCashActual = numCashActual;
	}

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
	
	public void setNumCash(BigDecimal value) {
		this.numCash = value;
	}
	
	public BigDecimal getNumCash() {
		return this.numCash;
	}
	
	public void setDtCash(java.util.Date value) {
		this.dtCashStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtCash = value;
	}
	
	public java.util.Date getDtCash() {
		return this.dtCash;
	}
	
	public java.lang.String getDtCashStr() {
		return this.dtCashStr;
	}
	
	public void setVcCardNo(java.lang.String value) {
		this.vcCardNo = value;
	}
	
	public java.lang.String getVcCardNo() {
		return this.vcCardNo;
	}
	
	public void setVcCardBank(java.lang.String value) {
		this.vcCardBank = value;
	}
	
	public java.lang.String getVcCardBank() {
		return this.vcCardBank;
	}
	
	public void setCityCode(java.lang.String value) {
		this.cityCode = value;
	}
	
	public java.lang.String getCityCode() {
		return this.cityCode;
	}
	
	public void setBrabankName(java.lang.String value) {
		this.brabankName = value;
	}
	
	public java.lang.String getBrabankName() {
		return this.brabankName;
	}
	
	public void setNumCashFee(BigDecimal value) {
		this.numCashFee = value;
	}
	
	public BigDecimal getNumCashFee() {
		return this.numCashFee;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
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
	
	public void setVcBillCode(java.lang.String value) {
		this.vcBillCode = value;
	}
	
	public java.lang.String getVcBillCode() {
		return this.vcBillCode;
	}
	
	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}
	
	public java.lang.Integer getItype() {
		return this.itype;
	}
	
	public void setVcPayType(java.lang.Integer value) {
		this.vcPayType = value;
	}
	
	public java.lang.Integer getVcPayType() {
		return this.vcPayType;
	}
	
	public void setVcResultBillCode(java.lang.String value) {
		this.vcResultBillCode = value;
	}
	
	public java.lang.String getVcResultBillCode() {
		return this.vcResultBillCode;
	}

	public java.lang.String getIloginAccountName() {
		return iloginAccountName;
	}

	public void setIloginAccountName(java.lang.String iloginAccountName) {
		this.iloginAccountName = iloginAccountName;
	}

	public void setIfinancialCashFailureId(java.lang.Integer value) {
		this.ifinancialCashFailureId = value;
	}
	
	public java.lang.Integer getIfinancialCashFailureId() {
		return this.ifinancialCashFailureId;
	}

	public java.lang.Integer getiExport() {
		return iExport;
	}

	public void setiExport(java.lang.Integer iExport) {
		this.iExport = iExport;
	}

	public java.util.Date getDtExport() {
		return dtExport;
	}

	public void setDtExport(java.util.Date dtExport) {
		this.dtExport = dtExport;
	}

	public java.lang.String getCityName() {
		return cityName;
	}

	public void setCityName(java.lang.String cityName) {
		this.cityName = cityName;
	}

	public java.lang.String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(java.lang.String provinceName) {
		this.provinceName = provinceName;
	}

	public String getVcCashDevice() {
		return vcCashDevice;
	}

	public void setVcCashDevice(String vcCashDevice) {
		this.vcCashDevice = vcCashDevice;
	}

	public String getVcUserIP() {
		return vcUserIP;
	}

	public void setVcUserIP(String vcUserIP) {
		this.vcUserIP = vcUserIP;
	}

	public java.lang.Integer getiChannelType() {
		return iChannelType;
	}

	public void setiChannelType(java.lang.Integer iChannelType) {
		this.iChannelType = iChannelType;
	}

	public String getVcResultDesc() {
		return vcResultDesc;
	}

	public void setVcResultDesc(String vcResultDesc) {
		this.vcResultDesc = vcResultDesc;
	}

	public BigDecimal getTotalnumCash() {
		return totalnumCash;
	}

	public void setTotalnumCash(BigDecimal totalnumCash) {
		this.totalnumCash = totalnumCash;
	}

	public BigDecimal getTotalnumCashFee() {
		return totalnumCashFee;
	}

	public void setTotalnumCashFee(BigDecimal totalnumCashFee) {
		this.totalnumCashFee = totalnumCashFee;
	}


}

