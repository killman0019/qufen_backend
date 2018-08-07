package com.tzg.entitys.kff.exchange;

import java.io.Serializable;
import java.util.Date;

public class Exchange implements Serializable {
	/**
	 */
	private static final long serialVersionUID = 1099015915816636958L;

	private Integer exchangeId;

	private Date createTime;
	private String createTimeStr;
	private Date updateTime;
	private String updateTimeStr;

	private Integer status;

	private String exchangeName;

	private String exchangeHomeUrl;

	private String exchangeCountry;

	private Integer transactionPairSum;

	private String exchangeLogo;

	private String exchangeDisplayName;

	private String exchangChineseName;

	private String memo;

	private String exchangeSignature;

	public String getExchangeSignature() {
		return exchangeSignature;
	}

	public void setExchangeSignature(String exchangeSignature) {
		this.exchangeSignature = exchangeSignature;
	}

	public Integer getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(Integer exchangeId) {
		this.exchangeId = exchangeId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName == null ? null : exchangeName.trim();
	}

	public String getExchangeHomeUrl() {
		return exchangeHomeUrl;
	}

	public void setExchangeHomeUrl(String exchangeHomeUrl) {
		this.exchangeHomeUrl = exchangeHomeUrl == null ? null : exchangeHomeUrl.trim();
	}

	public String getExchangeCountry() {
		return exchangeCountry;
	}

	public void setExchangeCountry(String exchangeCountry) {
		this.exchangeCountry = exchangeCountry == null ? null : exchangeCountry.trim();
	}

	public Integer getTransactionPairSum() {
		return transactionPairSum;
	}

	public void setTransactionPairSum(Integer transactionPairSum) {
		this.transactionPairSum = transactionPairSum;
	}

	public String getExchangeLogo() {
		return exchangeLogo;
	}

	public void setExchangeLogo(String exchangeLogo) {
		this.exchangeLogo = exchangeLogo == null ? null : exchangeLogo.trim();
	}

	public String getExchangeDisplayName() {
		return exchangeDisplayName;
	}

	public void setExchangeDisplayName(String exchangeDisplayName) {
		this.exchangeDisplayName = exchangeDisplayName == null ? null : exchangeDisplayName.trim();
	}

	public String getExchangChineseName() {
		return exchangChineseName;
	}

	public void setExchangChineseName(String exchangChineseName) {
		this.exchangChineseName = exchangChineseName == null ? null : exchangChineseName.trim();
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

}