package com.tzg.entitys.llpaycard;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class Llpaycard implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * P2P 平台唯一用户编号
     */ 	
	private java.lang.String userId;
    /**
     * 银行信息ID
     */ 	
	private java.lang.Integer ibankId;
	private java.lang.String vcName; //银行名称
	/**
     * 支持的支付类型 1-认证支付；             2-网银支付。
     */ 	
	private java.lang.Integer itype;
    /**
     * 连连支付标示
     */ 	
	private java.lang.String vcBankCode;
    /**
     * 限额说明
     */ 	
	private java.lang.String vcLimiteDescription;
	private String vcMemo; //备注
	
    /**
     * 连连支付用户号
     */ 	
	private java.lang.String oidUserno;
    /**
     * 银行卡号
     */ 	
	private java.lang.String cardNo;
    /**
     * 大额行号 注： 三个参数prcptcd 、city_code 、brabank_name。 如果是工、农、中、建(对公)，招、光大、浦 发(对私)银行，三个参数均可不传。 其他银行大额行号和（城市编码及支行名称） 二者必传其一。
     */ 	
	private java.lang.String prcptcd;
    /**
     * 开户行所在城市编码 标准地市编码杭州：330100 请参看城市编码表
     */ 	
	private java.lang.String cityCode;
	private String provinceCode;
	private String provinceName;
	private String cityName;
    /**
     * 开户支行名称 例如:中国工商银行杭州市文三路支行文三路 支行
     */ 	
	private java.lang.String brabankName;
    /**
     * 连连支付提现卡编号
     */ 	
	private java.lang.String withdrawNo;
    /**
     * 累计充值金额
     */ 	
	private BigDecimal numTotalRecharge;
    /**
     * 累计提现金额
     */ 	
	private BigDecimal numTotalWithdraw;
	private BigDecimal maxCashAmt; //最高可提现金额
    /**
     * 累计提现收益
     */ 	
	private BigDecimal numTotalProfit;
    /**
     * 创建时间
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * 连连支付返回结果时间
     */ 	
	private java.util.Date dtResult;
	private java.lang.String dtResultStr;
    /**
     * 状态 1--处理中； 2--处理成功； 3--处理失败； 4--待处理； 5-- 已提交连连支付解绑认证； 6-- 解除绑定失败； 7-- 解除绑定成功。
     */ 	
	private java.lang.Integer istate;
	
	private java.lang.Integer ichannelType;
	
	/**
     * 易宝绑卡请求号 
     */ 
	private java.lang.String vcRequestCode;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setUserId(java.lang.String value) {
		this.userId = value;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setIbankId(java.lang.Integer value) {
		this.ibankId = value;
	}
	
	public java.lang.Integer getIbankId() {
		return this.ibankId;
	}
	
	public void setOidUserno(java.lang.String value) {
		this.oidUserno = value;
	}
	
	public java.lang.String getOidUserno() {
		return this.oidUserno;
	}
	
	public void setCardNo(java.lang.String value) {
		this.cardNo = value;
	}
	
	public java.lang.String getCardNo() {
		return this.cardNo;
	}
	
	public void setPrcptcd(java.lang.String value) {
		this.prcptcd = value;
	}
	
	public java.lang.String getPrcptcd() {
		return this.prcptcd;
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
	
	public void setWithdrawNo(java.lang.String value) {
		this.withdrawNo = value;
	}
	
	public java.lang.String getWithdrawNo() {
		return this.withdrawNo;
	}
	
	public void setNumTotalRecharge(BigDecimal value) {
		this.numTotalRecharge = value;
	}
	
	public BigDecimal getNumTotalRecharge() {
		return this.numTotalRecharge;
	}
	
	public void setNumTotalWithdraw(BigDecimal value) {
		this.numTotalWithdraw = value;
	}
	
	public BigDecimal getNumTotalWithdraw() {
		return this.numTotalWithdraw;
	}
	
	public void setNumTotalProfit(BigDecimal value) {
		this.numTotalProfit = value;
	}
	
	public BigDecimal getNumTotalProfit() {
		return this.numTotalProfit;
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
	
	public void setDtResult(java.util.Date value) {
		this.dtResultStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtResult = value;
	}
	
	public java.util.Date getDtResult() {
		return this.dtResult;
	}
	
	public java.lang.String getDtResultStr() {
		return this.dtResultStr;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
	}

	public java.lang.Integer getItype() {
		return itype;
	}

	public void setItype(java.lang.Integer itype) {
		this.itype = itype;
	}

	public java.lang.String getVcName() {
		return vcName;
	}

	public void setVcName(java.lang.String vcName) {
		this.vcName = vcName;
	}

	public java.lang.String getVcBankCode() {
		return vcBankCode;
	}

	public void setVcBankCode(java.lang.String vcBankCode) {
		this.vcBankCode = vcBankCode;
	}

	public java.lang.String getVcLimiteDescription() {
		return vcLimiteDescription;
	}

	public void setVcLimiteDescription(java.lang.String vcLimiteDescription) {
		this.vcLimiteDescription = vcLimiteDescription;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public BigDecimal getMaxCashAmt() {
		return maxCashAmt;
	}

	public void setMaxCashAmt(BigDecimal maxCashAmt) {
		this.maxCashAmt = maxCashAmt;
	}

	public String getVcMemo() {
		return vcMemo;
	}

	public void setVcMemo(String vcMemo) {
		this.vcMemo = vcMemo;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public java.lang.Integer getIchannelType() {
		return ichannelType;
	}

	public void setIchannelType(java.lang.Integer ichannelType) {
		this.ichannelType = ichannelType;
	}

	public java.lang.String getVcRequestCode() {
		return vcRequestCode;
	}

	public void setVcRequestCode(java.lang.String vcRequestCode) {
		this.vcRequestCode = vcRequestCode;
	}
	
}

