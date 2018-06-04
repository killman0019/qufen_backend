package com.tzg.entitys.kff.tokenaward;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.sun.el.parser.Token;
import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;


public class TokenawardReturn extends Tokenrecords implements Serializable {

	/**
	 *  奖励明细的分页查询
	 */
	private static final long serialVersionUID = -3884437919463088370L;
	/**
     * 用户id
     *  
     */ 	
	private java.lang.Integer user_id;
	
	/**
	 * token类型
	 */ 	
	private java.lang.Integer function_type;
	
	private java.lang.String function_desc;
	/**
	 * token的数量
	 */
	private BigDecimal amount;
	 /**
     * 创建时间
     */ 	
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
	/**
     * token类型
     */ 	
	private java.lang.Integer tradeType;
	/**
	 * 发放类型
	 * 
	 */
	private java.lang.Integer rewardGrantType;
	
	/**
	 * token总数
	 * @return
	 */
	private BigDecimal sum;
	
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	public java.lang.Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(java.lang.Integer user_id) {
		this.user_id = user_id;
	}
	public java.lang.String getFunction_desc() {
		return function_desc;
	}
	public void setFunction_desc(java.lang.String function_desc) {
		this.function_desc = function_desc;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public java.util.Date getCreateTime() {
		this.createTimeStr =DateUtil.getDate(createTime, "yyyy-MM-dd HH:mm:ss");
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(java.lang.String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public java.lang.Integer getTradeType() {
		return tradeType;
	}
	public void setTradeType(java.lang.Integer tradeType) {
		this.tradeType = tradeType;
	}
	public java.lang.Integer getRewardGrantType() {
		return rewardGrantType;
	}
	public void setRewardGrantType(java.lang.Integer rewardGrantType) {
		this.rewardGrantType = rewardGrantType;
	}
	
	public java.lang.Integer getFunction_type() {
		return function_type;
	}
	public void setFunction_type(java.lang.Integer function_type) {
		this.function_type = function_type;
	}
}

