package com.tzg.entitys.tokenrecords;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class TokenrecordsNew implements Serializable {

	/**
	 * 这是通用返回实体
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
	private java.lang.Double amount;
    /**
     * tradeDate
     */ 	
	private java.util.Date tradeDate;
	private java.lang.String tradeDateStr;
    /**
     * balance
     */ 	
	private Double balance;
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
	/**
	 * 新加字段(奖励的token)
	 * @param Wang
	 */
	private java.lang.Integer rewardGrantType;
	
    /**
     * 昵称
     */ 	
	private java.lang.String userName;
    /**
     * 性别:1男；2女
     */ 	
	private java.lang.Integer sex = 1;
    /**
     * 头像
     */ 	
	private java.lang.String icon;
    /**
     * mobile
     */ 	
	private java.lang.String mobile;
    /**
     * email
     */ 	
	private java.lang.String email;
    /**
     * 微信号
     */ 	
	private java.lang.String wechat;
    /**
     * password
     */ 	
	private java.lang.String password;
    /**
     * payPassword
     */ 	
	private java.lang.String payPassword;
    /**
     * 用户类型:1-普通用户；2-项目方；3-评测机构；4-机构用户
     */ 	
	private java.lang.Integer userType = 1;
    /**
     * 预留字段 用户等级：1-普通用户；2-高级用户;3-VIP
     */ 	
	private java.lang.Integer userDegree = 1;
    /**
     * 个人简介
     */ 	
	private java.lang.String userSignature;
    /**
     * 粉丝数量
     */ 	
	private java.lang.Integer fansNum = 0;
    /**
     * 收获点赞数量
     */ 	
	private java.lang.Integer praiseNum  = 0;
    /**
     * 发表评测数量
     */ 	
	private java.lang.Integer evaluationNum = 0;
    /**
     * 发表讨论数量
     */ 	
	private java.lang.Integer discussNum = 0;
    /**
     * 发表文章数量
     */ 	
	private java.lang.Integer articleNum = 0;
    /**
     * 账号币值
     */ 	
	private java.lang.Integer kffCoinNum = 0;
    /**
     * 所在地区域名称:浙江省杭州市
     */ 	
	private java.lang.String areaName;
    /**
     * 状态：0-删除；1-有效
     */ 	
	private java.lang.Integer status = 1;
    /**
     * 备注信息
     */ 	
	private java.lang.String memo;
    /**
     * 省编码
     */ 	
	private java.lang.String provinceCode;
    /**
     * 城市编码
     */ 	
	private java.lang.String cityCode;
    /**
     * areaCode
     */ 	
	private java.lang.String areaCode;
    /**
     * referUserId
     */ 	
	private java.lang.Integer referUserId  ;
    /**
     * 推荐等级:0-没有推荐人;1-一级推荐人...
     */ 	
	private java.lang.Integer referLevel  = 0;
	
	
	private java.lang.Integer tokenAwardId;
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
	 * 返还次数
	 */
	private java.lang.Double giveNext;
	/**
	 * 奖励总数
	 */
	private java.lang.Double rewardToken;
	/**
	 * 邀请总人数
	 * @return
	 */
	private java.lang.Integer inviteNumber;
	/**
	 * 发放类型 1代表线性发放 2代表一次性发放
	 * @return
	 */
	private java.lang.Integer distributionType;
	/**
	 * 线性奖励发放的次数
	 * @return
	 */
	private java.lang.Integer counter;
	/**
	 * 奖励发放状态
	 * @return
	 */
	private java.lang.Integer grantType;
	
	
	
	public java.lang.String getUserName() {
		return userName;
	}

	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	public java.lang.Integer getSex() {
		return sex;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}

	public java.lang.String getIcon() {
		return icon;
	}

	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getWechat() {
		return wechat;
	}

	public void setWechat(java.lang.String wechat) {
		this.wechat = wechat;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public java.lang.String getPayPassword() {
		return payPassword;
	}

	public void setPayPassword(java.lang.String payPassword) {
		this.payPassword = payPassword;
	}

	public java.lang.Integer getUserType() {
		return userType;
	}

	public void setUserType(java.lang.Integer userType) {
		this.userType = userType;
	}

	public java.lang.Integer getUserDegree() {
		return userDegree;
	}

	public void setUserDegree(java.lang.Integer userDegree) {
		this.userDegree = userDegree;
	}

	public java.lang.String getUserSignature() {
		return userSignature;
	}

	public void setUserSignature(java.lang.String userSignature) {
		this.userSignature = userSignature;
	}

	public java.lang.Integer getFansNum() {
		return fansNum;
	}

	public void setFansNum(java.lang.Integer fansNum) {
		this.fansNum = fansNum;
	}

	public java.lang.Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(java.lang.Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public java.lang.Integer getEvaluationNum() {
		return evaluationNum;
	}

	public void setEvaluationNum(java.lang.Integer evaluationNum) {
		this.evaluationNum = evaluationNum;
	}

	public java.lang.Integer getDiscussNum() {
		return discussNum;
	}

	public void setDiscussNum(java.lang.Integer discussNum) {
		this.discussNum = discussNum;
	}

	public java.lang.Integer getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(java.lang.Integer articleNum) {
		this.articleNum = articleNum;
	}

	public java.lang.Integer getKffCoinNum() {
		return kffCoinNum;
	}

	public void setKffCoinNum(java.lang.Integer kffCoinNum) {
		this.kffCoinNum = kffCoinNum;
	}

	public java.lang.String getAreaName() {
		return areaName;
	}

	public void setAreaName(java.lang.String areaName) {
		this.areaName = areaName;
	}

	public java.lang.String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(java.lang.String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public java.lang.String getCityCode() {
		return cityCode;
	}

	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}

	public java.lang.String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(java.lang.String areaCode) {
		this.areaCode = areaCode;
	}

	public java.lang.Integer getReferUserId() {
		return referUserId;
	}

	public void setReferUserId(java.lang.Integer referUserId) {
		this.referUserId = referUserId;
	}

	public java.lang.Integer getReferLevel() {
		return referLevel;
	}

	public void setReferLevel(java.lang.Integer referLevel) {
		this.referLevel = referLevel;
	}

	public java.lang.Integer getTokenAwardId() {
		return tokenAwardId;
	}

	public void setTokenAwardId(java.lang.Integer tokenAwardId) {
		this.tokenAwardId = tokenAwardId;
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

	public void setInviteRewards(Double inviteRewards) {
		this.inviteRewards = inviteRewards;
	}

	public java.util.Date getGiveTime() {
		return giveTime;
	}

	public void setGiveTime(java.util.Date giveTime) {
		this.giveTime = giveTime;
	}

	public java.lang.String getGiveTimeStr() {
		return giveTimeStr;
	}

	public void setGiveTimeStr(java.lang.String giveTimeStr) {
		this.giveTimeStr = giveTimeStr;
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

	public java.lang.Double getGiveNext() {
		return giveNext;
	}

	public void setGiveNext(java.lang.Double giveNext) {
		this.giveNext = giveNext;
	}

	public java.lang.Integer getInviteNumber() {
		return inviteNumber;
	}

	public void setInviteNumber(java.lang.Integer inviteNumber) {
		this.inviteNumber = inviteNumber;
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

	public java.lang.Integer getGrantType() {
		return grantType;
	}

	public void setGrantType(java.lang.Integer grantType) {
		this.grantType = grantType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setTradeDateStr(java.lang.String tradeDateStr) {
		this.tradeDateStr = tradeDateStr;
	}

	public void setCreateTimeStr(java.lang.String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public void setUpdateTimeStr(java.lang.String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public void setRewardToken(java.lang.Double rewardToken) {
		this.rewardToken = rewardToken;
	}

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
	
	public void setAmount(java.lang.Double value) {
		this.amount = value;
	}
	
	public java.lang.Double getAmount() {
		return this.amount;
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
	
	public void setBalance(java.lang.Double value) {
		this.balance = value;
	}
	
	public java.lang.Double getBalance() {
		return this.balance;
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

