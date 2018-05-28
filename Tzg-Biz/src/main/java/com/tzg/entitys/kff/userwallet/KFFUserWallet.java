package com.tzg.entitys.kff.userwallet;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class KFFUserWallet implements Serializable {

	
	/**
	 * 用户钱包
	 */
	private static final long serialVersionUID = 2037513184296398486L;
	
	//返回结果 用到
	private java.lang.Integer followStatus = 0;
	
	
	/**
     * userWalletId
     */ 	
	private java.lang.Integer userWalletId;
	/**
	 * userId
	 */
	private java.lang.Integer userId;
    /**
     * 昵称
     */ 	
	private java.lang.String userName;
    /**
     * 手机号
     */ 	
	private java.lang.String mobile;
    /**
     * 绑定时间
     */ 	
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
    /**
     * 更换时间
     */ 	
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;
    /**
     * 钱包地址
     */ 	
	private java.lang.String wallet;
    /**
     * 绑定状态 默认为0-未绑定    1-已绑定
     */ 	
	private java.lang.Integer walletType;
	
	public java.lang.Integer getFollowStatus() {
		return followStatus;
	}
	public void setFollowStatus(java.lang.Integer followStatus) {
		this.followStatus = followStatus;
	}
	public java.lang.Integer getUserWalletId() {
		return userWalletId;
	}
	public void setUserWalletId(java.lang.Integer userWalletId) {
		this.userWalletId = userWalletId;
	}
	public java.lang.Integer getUserId() {
		return userId;
	}
	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}
	public java.lang.String getUserName() {
		return userName;
	}
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}
	public java.lang.String getMobile() {
		return mobile;
	}
	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}
	public java.util.Date getCreatetime() {
		return createTime;
	}
	public void setCreatetime(java.util.Date createTime) {
		this.createTimeStr =DateUtil.getDate(createTime, "yyyy-MM-dd HH:mm:ss");
		this.createTime = createTime;
	}
	public java.util.Date getUpdatetime() {
		return updateTime;
	}
	public void setUpdatetime(java.util.Date updateTime) {
		this.updateTimeStr =DateUtil.getDate(updateTime, "yyyy-MM-dd HH:mm:ss");
		this.updateTime = updateTime;
	}
	public java.lang.String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(java.lang.String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public java.lang.String getUpdateTimeStr() {
		return updateTimeStr;
	}
	public void setUpdateTimeStr(java.lang.String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}
	public java.lang.String getWallet() {
		return wallet;
	}
	public void setWallet(java.lang.String wallet) {
		this.wallet = wallet;
	}
	public java.lang.Integer getWalletType() {
		return walletType;
	}
	public void setWalletType(Integer i) {
		this.walletType = i;
	}

	
}

