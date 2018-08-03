package com.tzg.entitys.kff.transactionpair;

import java.io.Serializable;
import java.util.Date;

public class TransactionPair implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 533951133195552988L;

	private Integer transactionPairId;

	private Integer exchangeId;

	private Integer status;

	private String mainCode;

	private String coinpair;

	private String exchangeName;
	private Date createTime;
	private String createTimeStr;

	private Date updateTime;
	private String updateTimeStr;

	private String exchangeDisplayName;
	private Integer vaild;

	public Integer getVaild() {
		return vaild;
	}

	public void setVaild(Integer vaild) {
		this.vaild = vaild;
	}

	public Integer getTransactionPairId() {
		return transactionPairId;
	}

	public String getExchangeDisplayName() {
		return exchangeDisplayName;
	}

	public void setExchangeDisplayName(String exchangeDisplayName) {
		this.exchangeDisplayName = exchangeDisplayName;
	}

	public void setTransactionPairId(Integer transactionPairId) {
		this.transactionPairId = transactionPairId;
	}

	public Integer getExchangeId() {
		return exchangeId;
	}

	public void setExchangeId(Integer exchangeId) {
		this.exchangeId = exchangeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMainCode() {
		return mainCode;
	}

	public void setMainCode(String mainCode) {
		this.mainCode = mainCode == null ? null : mainCode.trim();
	}

	public String getCoinpair() {
		return coinpair;
	}

	public void setCoinpair(String coinpair) {
		this.coinpair = coinpair == null ? null : coinpair.trim();
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName == null ? null : exchangeName.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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