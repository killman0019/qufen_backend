package com.tzg.entitys.kff.coinproperty;

import java.io.Serializable;
import java.util.Date;

import com.tzg.common.utils.DateUtil;

public class CoinProperty implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3884437919463088370L;
	/**
	 *  资产id
	 */
	private java.lang.Integer coinPropertyId;
	/**
	 * userId
	 */
	private java.lang.Integer userId;
	/**
	 * 锁定中的币值
	 */
	private java.lang.Double coinLock;
	/**
	 * 解锁中的币
	 */
	private java.lang.Double coinUnlock;
	/**
	 * 申请解锁的时间
	 */
	private java.util.Date coinUnlockTime;
	private java.lang.String coinUnlockTimeStr;
	
	/**
	 * 解锁状态  1-解锁中  2-解锁完成  3-取消解锁
	 */
	private java.lang.Integer coinUnlockType;
	/**
	 * 发放中
	 */
	private java.lang.Double coinDistributed;
	/**
	 * 可用(解锁完成,可提现的,但是不能在平台流通的)
	 */
	private java.lang.Double coinUsable;
	/**
	 * 解锁完成时间
	 */
	private java.util.Date coinUnlockUptime;
	private java.lang.String coinUnlockUptimeStr;
	
	
	/**
	 * 总数
	 * @return
	 */
	private java.lang.Double totalAssets;
	
	
	
	
	
	public java.lang.Double getTotalAssets() {
		return totalAssets;
	}
	public void setTotalAssets(java.lang.Double totalAssets) {
		this.totalAssets = totalAssets;
	}

	
	public java.lang.String getCoinUnlockTimeStr() {
		return coinUnlockTimeStr;
	}
	public java.lang.String getCoinUnlockUptimeStr() {
		return coinUnlockUptimeStr;
	}
	public java.lang.Integer getCoinPropertyId() {
		return coinPropertyId;
	}
	public void setCoinPropertyId(java.lang.Integer coinPropertyId) {
		this.coinPropertyId = coinPropertyId;
	}
	public java.lang.Integer getUserId() {
		return userId;
	}
	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}
	public java.lang.Double getCoinLock() {
		return coinLock;
	}
	public void setCoinLock(java.lang.Double coinLock) {
		this.coinLock = coinLock;
	}
	public java.lang.Double getCoinUnlock() {
		return coinUnlock;
	}
	public void setCoinUnlock(java.lang.Double coinUnlock) {
		this.coinUnlock = coinUnlock;
	}
	public java.util.Date getCoinUnlockTime() {
		return coinUnlockTime;
	}
	public void setCoinUnlockTime(java.util.Date coinUnlockTime) {
		this.coinUnlockTimeStr =DateUtil.getDate(coinUnlockTime, "yyyy-MM-dd HH:mm:ss");
		this.coinUnlockTime = coinUnlockTime;
		
	}
	public java.lang.Integer getCoinUnlockType() {
		return coinUnlockType;
	}
	public void setCoinUnlockType(java.lang.Integer coinUnlockType) {
		this.coinUnlockType = coinUnlockType;
	}
	public java.lang.Double getCoinDistributed() {
		return coinDistributed;
	}
	public void setCoinDistributed(java.lang.Double coinDistributed) {
		this.coinDistributed = coinDistributed;
	}
	public java.lang.Double getCoinUsable() {
		return coinUsable;
	}
	public void setCoinUsable(java.lang.Double coinUsable) {
		this.coinUsable = coinUsable;
	}
	public java.util.Date getCoinUnlockUptime() {
		return coinUnlockUptime;
	}
	public void setCoinUnlockUptime(java.util.Date coinUnlockUptime) {
		this.coinUnlockUptimeStr =DateUtil.getDate(coinUnlockUptime, "yyyy-MM-dd HH:mm:ss");
		this.coinUnlockUptime = coinUnlockUptime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}