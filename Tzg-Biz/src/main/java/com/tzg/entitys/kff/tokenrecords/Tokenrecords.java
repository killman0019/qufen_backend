package com.tzg.entitys.kff.tokenrecords;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class Tokenrecords implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3884437919463088370L;
	/**
     * tokenRecordsId
     */ 	
	private java.lang.Integer tokenRecordsId;
    /**
     * userId
     */ 	
	private java.lang.Integer userId;
    /**
     * 交易流水号=交易类型（2位） 交易时间年月日（8位） 业务记录ID（10位）
     */ 	
	private java.lang.String tradeCode;
    /**
     * 交易类型:1-收入；2-支出
     */ 	
	private java.lang.Integer tradeType;
    /**
     * 交易描述:0-充值1-评测奖励2-讨论奖励3-文章奖励4-榜单奖励5-用户赞赏6-注册奖励7-点赞奖励8-邀请好友奖励21-提现22-赞赏他人
     */ 	
	private java.lang.String functionDesc;
    /**
     * 10-充值11-评测奖励12-讨论奖励13-文章奖励14-榜单奖励15-用户赞赏16-注册奖励17-点赞奖励18-邀请好友奖励21-提现22-赞赏他人
     */ 	
	private Integer functionType;
    /**
     * token数量
     */ 	
	private BigDecimal amount;
    /**
     * tradeDate
     */ 	
	private java.util.Date tradeDate;
	private java.lang.String tradeDateStr;
    /**
     * balance
     */ 	
	private java.math.BigDecimal balance;
    /**
     * createTime
     */ 	
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
    /**
     * updateTime
     */ 	
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;
    /**
     * status
     */ 	
	private java.lang.Integer status = 1;
    /**
     * 备注
     */ 	
	private java.lang.String memo;
	/**
	 * 发放类型 1-一次性发放 2-线性发放
	 * @param Wang
	 */
	private java.lang.Integer rewardGrantType;
	
	
	public java.lang.Integer getRewardGrantType() {
		return rewardGrantType;
	}

	public void setRewardGrantType(java.lang.Integer rewardGrantType) {
		this.rewardGrantType = rewardGrantType;
	}
	public java.lang.Integer getRewardToken() {
		return rewardGrantType;
	}

	public void setRewardToken(java.lang.Integer rewardToken) {
		this.rewardGrantType = rewardToken;
	}
	public void setTokenRecordsId(java.lang.Integer value) {
		this.tokenRecordsId = value;
	}
	
	public java.lang.Integer getTokenRecordsId() {
		return this.tokenRecordsId;
	}
	
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	
	public void setTradeCode(java.lang.String value) {
		this.tradeCode = value;
	}
	
	public java.lang.String getTradeCode() {
		return this.tradeCode;
	}
	

	
	public java.lang.Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(java.lang.Integer tradeType) {
		this.tradeType = tradeType;
	}

	public void setFunctionDesc(java.lang.String value) {
		this.functionDesc = value;
	}
	
	public java.lang.String getFunctionDesc() {
		return this.functionDesc;
	}
	
	public void setFunctionType(Integer value) {
		this.functionType = value;
	}
	
	public Integer getFunctionType() {
		return this.functionType;
	}
	

	
	public void setTradeDate(java.util.Date value) {
		this.tradeDateStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.tradeDate = value;
	}
	
	public java.util.Date getTradeDate() {
		return this.tradeDate;
	}
	
	public java.lang.String getTradeDateStr() {
		return this.tradeDateStr;
	}
	

	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public java.math.BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(java.math.BigDecimal balance) {
		this.balance = balance;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public java.lang.String getCreateTimeStr() {
		return this.createTimeStr;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public java.lang.String getUpdateTimeStr() {
		return this.updateTimeStr;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	
	public java.lang.String getMemo() {
		return this.memo;
	}

	
}

