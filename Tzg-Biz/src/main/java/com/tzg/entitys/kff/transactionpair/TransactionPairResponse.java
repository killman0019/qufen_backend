package com.tzg.entitys.kff.transactionpair;

import java.io.Serializable;
import java.util.Date;

public class TransactionPairResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5143201119288407401L;

	private String exchangeDisplayName;

	private Integer transactionPairId;

	private Integer exchangeId;

	// private Integer status;

	private String mainCode;

	private String coinpair;

	private String exchangeName;

	private Integer vaild;
	private Double last;

	private Double high;

	private Double low;

	private Double baseVolume;

	private Double vol;

	private Double changeDaily;

	private Double usdRate;

	/*private Date createTime;
	private String createTimeStr;

	private Date updateTime;
	private String updateTimeStr;*/

	public Double getLast() {
		return last;
	}

	public void setLast(Double last) {
		this.last = last;
	}

	public Double getHigh() {
		return high;
	}

	public void setHigh(Double high) {
		this.high = high;
	}

	public Double getLow() {
		return low;
	}

	public void setLow(Double low) {
		this.low = low;
	}

	public Double getBaseVolume() {
		return baseVolume;
	}

	public void setBaseVolume(Double baseVolume) {
		this.baseVolume = baseVolume;
	}

	public Double getVol() {
		return vol;
	}

	public void setVol(Double vol) {
		this.vol = vol;
	}

	public Double getChangeDaily() {
		return changeDaily;
	}

	public void setChangeDaily(Double changeDaily) {
		this.changeDaily = changeDaily;
	}

	public Double getUsdRate() {
		return usdRate;
	}

	public void setUsdRate(Double usdRate) {
		this.usdRate = usdRate;
	}

	public Integer getVaild() {
		return vaild;
	}

	public void setVaild(Integer vaild) {
		this.vaild = vaild;
	}

	public String getExchangeDisplayName() {
		return exchangeDisplayName;
	}

	public void setExchangeDisplayName(String exchangeDisplayName) {
		this.exchangeDisplayName = exchangeDisplayName;
	}

	public Integer getTransactionPairId() {
		return transactionPairId;
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

	/*	public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}*/

	public String getMainCode() {
		return mainCode;
	}

	public void setMainCode(String mainCode) {
		this.mainCode = mainCode;
	}

	public String getCoinpair() {
		return coinpair;
	}

	public void setCoinpair(String coinpair) {
		this.coinpair = coinpair;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	/*public Date getCreateTime() {
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
	*/
}
