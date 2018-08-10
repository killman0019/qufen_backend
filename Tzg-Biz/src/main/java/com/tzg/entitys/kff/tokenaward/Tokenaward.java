package com.tzg.entitys.kff.tokenaward;

import java.io.Serializable;
import java.util.Date;

import com.tzg.common.utils.DateUtil;

public class Tokenaward implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3884437919463088370L;
	/**
	 * tokenawardId
	 */
	private java.lang.Integer tokenAwardId;
	/**
	 * userId
	 */
	private java.lang.Integer userId;
	/**
	 * recordsId;
	 */
	private java.lang.Integer tokenRecordsId;
	/**
	 * praiseId
	 */
	private java.lang.Integer praiseId;
	/**
	 * 交易描述:0-充值1-评测奖励2-讨论奖励3-文章奖励4-榜单奖励5-用户赞赏6-注册奖励7-点赞奖励8-邀请好友奖励21-提现22-赞赏他人
	 */
	private java.lang.String tokenAwardFunctionDesc;
	/**
	 * 10-充值11-评测奖励12-讨论奖励13-文章奖励14-榜单奖励15-用户赞赏16-注册奖励17-点赞奖励18-邀请好友奖励21-提现22-赞赏他人
	 */
	private java.lang.Integer tokenAwardFunctionType;
	/**
	 * 邀请奖励,线性发放
	 */
	private Double inviteRewards;
	/**
	 * 返还时间
	 */
	private java.util.Date giveTime;
	private java.lang.String giveTimeStr;
	/**
	 * 点赞奖励
	 */
	private java.lang.Double priaiseAward;
	/**
	 * balance
	 */
	private java.lang.Double awardBalance;
	/**
	 * 平台奖励
	 */
	private java.lang.Integer containerAward;
	/**
	 * createTime
	 */
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
	/**
	 * 返还次数
	 */
	private java.lang.Double giveNext;
	/**
	 * updateTime
	 */
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;
	/**
	 * 奖励总数
	 */
	private java.lang.Double rewardToken;
	/**
	 * 邀请总人数
	 * 
	 * @return
	 */
	private java.lang.Integer inviteNumber;
	/**
	 * 发放类型 1代表线性发放 2代表一次性发放
	 * 
	 * @return
	 */
	private java.lang.Integer distributionType;
	/**
	 * 线性奖励发放的次数
	 * 
	 * @return
	 */
	private java.lang.Integer counter;
	/**
	 * 奖励发放状态
	 * 
	 * @return
	 */
	private java.lang.Integer grantType;
	/**
	 * 用户名
	 * 
	 * @return
	 */
	private java.lang.String userName;
	/**
	 * 用户手机号
	 * 
	 * @return
	 */
	private java.lang.String mobile;
	/**
	 * 后台奖励发放人
	 * 
	 * @return
	 */
	private java.lang.String issuer;
	/**
	 * 后台奖励发放备注
	 * 
	 * @return
	 */
	private java.lang.String remark;

	private Integer postId;

	private Integer awardType;

	public Integer getAwardType() {
		return awardType;
	}

	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}

	public Integer getAwardTypeId() {
		return awardTypeId;
	}

	public void setAwardTypeId(Integer awardTypeId) {
		this.awardTypeId = awardTypeId;
	}

	private Integer awardTypeId;

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public java.lang.String getIssuer() {
		return issuer;
	}

	public void setIssuer(java.lang.String issuer) {
		this.issuer = issuer;
	}

	public java.lang.String getRemark() {
		return remark;
	}

	public void setRemark(java.lang.String remark) {
		this.remark = remark;
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

	public java.lang.Integer getGrantType() {
		return grantType;
	}

	public void setGrantType(java.lang.Integer grantType) {
		this.grantType = grantType;
	}

	public java.lang.Integer getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(java.lang.Integer distributionType) {
		this.distributionType = distributionType;
	}

	public java.lang.Integer getCounter() {
		return counter;
	}

	public void setCounter(java.lang.Integer counter) {
		this.counter = counter;
	}

	public java.lang.Integer getInviteNumber() {
		return inviteNumber;
	}

	public void setInviteNumber(java.lang.Integer inviteNumber) {
		this.inviteNumber = inviteNumber;
	}

	public java.lang.Integer getTokenAwardId() {
		return tokenAwardId;
	}

	public void setTokenAwardId(java.lang.Integer tokenAwardId) {
		this.tokenAwardId = tokenAwardId;
	}

	public java.lang.Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}

	public java.lang.Integer getTokenRecordsId() {
		return tokenRecordsId;
	}

	public void setTokenRecordsId(java.lang.Integer tokenRecordsId) {
		this.tokenRecordsId = tokenRecordsId;
	}

	public java.lang.Integer getPraiseId() {
		return praiseId;
	}

	public void setPraiseId(java.lang.Integer praiseId) {
		this.praiseId = praiseId;
	}

	public java.lang.String getTokenAwardFunctionDesc() {
		return tokenAwardFunctionDesc;
	}

	public void setTokenAwardFunctionDesc(java.lang.String tokenAwardFunctionDesc) {
		this.tokenAwardFunctionDesc = tokenAwardFunctionDesc;
	}

	public java.lang.Integer getTokenAwardFunctionType() {
		return tokenAwardFunctionType;
	}

	public void setTokenAwardFunctionType(java.lang.Integer tokenAwardFunctionType) {
		this.tokenAwardFunctionType = tokenAwardFunctionType;
	}

	public Double getInviteRewards() {
		return inviteRewards;
	}

	public void setInviteRewards(java.lang.Double inviteRewards) {
		this.inviteRewards = inviteRewards;
	}

	public java.util.Date getGiveTime() {
		return giveTime;
	}

	public void setGiveTime(java.util.Date giveTime) {
		this.giveTimeStr = DateUtil.getDate(giveTime, "yyyy-MM-dd HH:mm:ss");
		this.giveTime = giveTime;
	}

	public java.lang.Double getPriaiseAward() {
		return priaiseAward;
	}

	public void setPriaiseAward(java.lang.Double priaiseAward) {
		this.priaiseAward = priaiseAward;
	}

	public java.lang.Double getAwardBalance() {
		return awardBalance;
	}

	public void setAwardBalance(java.lang.Double awardBalance) {
		this.awardBalance = awardBalance;
	}

	public java.lang.Integer getContainerAward() {
		return containerAward;
	}

	public void setContainerAward(java.lang.Integer containerAward) {
		this.containerAward = containerAward;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date data) {
		this.createTimeStr = DateUtil.getDate(data, "yyyy-MM-dd HH:mm:ss");
		this.createTime = data;
	}

	public java.lang.Double getGiveNext() {
		return giveNext;
	}

	public void setGiveNext(java.lang.Double giveNext) {
		this.giveNext = giveNext;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTimeStr = DateUtil.getDate(updateTime, "yyyy-MM-dd HH:mm:ss");
		this.updateTime = updateTime;
	}

	public java.lang.String getGiveTimeStr() {
		return giveTimeStr;
	}

	public java.lang.String getCreateTimeStr() {
		return createTimeStr;
	}

	public java.lang.String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public java.lang.Double getRewardToken() {
		return rewardToken;
	}

	public void setRewardToken(java.lang.Double rewardToken) {
		this.rewardToken = rewardToken;
	}

}
