package com.tzg.entitys.kff.user;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class KFFUser implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2037513184296398486L;
	
	//返回结果 用到
	private java.lang.Integer followStatus = 0;
	
	
	/**
     * userId
     */ 	
	private java.lang.Integer userId;
    /**
     * 昵称
     */ 	
	private java.lang.String userName;
    /**
     * 性别:1男；2女
     */ 	
	private java.lang.Integer sex;
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
	private java.lang.Integer userDegree ;
    /**
     * 个人简介
     */ 	
	private java.lang.String userSignature;
    /**
     * 粉丝数量
     */ 	
	private java.lang.Integer fansNum ;
    /**
     * 收获点赞数量
     */ 	
	private java.lang.Integer praiseNum ;
    /**
     * 发表评测数量
     */ 	
	private java.lang.Integer evaluationNum ;
    /**
     * 发表讨论数量
     */ 	
	private java.lang.Integer discussNum  ;
    /**
     * 发表文章数量
     */ 	
	private java.lang.Integer articleNum  ;
    /**
     * 账号币值
     */ 	
	private BigDecimal kffCoinNum ;
    /**
     * 所在地区域名称:浙江省杭州市
     */ 	
	private java.lang.String areaName;
    /**
     * 创建时间
     */ 	
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
	
	private java.util.Date  lastLoginDateTime;
	
    /**
     * 更新时间
     */ 	
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;
    /**
     * 状态：0-删除；1-有效
     */ 	
	private java.lang.Integer status ;
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
	private java.lang.Integer referUserId;
    /**
     * 推荐等级:0-没有推荐人;1-一级推荐人...
     */ 	
	private java.lang.Integer referLevel ;

	
	
	public java.util.Date getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(java.util.Date lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	
	public void setUserName(java.lang.String value) {
		this.userName = value;
	}
	
	public java.lang.String getUserName() {
		return this.userName;
	}
	
	
	
	public void setIcon(java.lang.String value) {
		this.icon = value;
	}
	
	public java.lang.String getIcon() {
		return this.icon;
	}
	
	public void setMobile(java.lang.String value) {
		this.mobile = value;
	}
	
	public java.lang.String getMobile() {
		return this.mobile;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setWechat(java.lang.String value) {
		this.wechat = value;
	}
	
	public java.lang.String getWechat() {
		return this.wechat;
	}
	
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getPassword() {
		return this.password;
	}
	
	public void setPayPassword(java.lang.String value) {
		this.payPassword = value;
	}
	
	public java.lang.String getPayPassword() {
		return this.payPassword;
	}
	

	
	public void setUserDegree(java.lang.Integer value) {
		this.userDegree = value;
	}
	
	public java.lang.Integer getUserDegree() {
		return this.userDegree;
	}
	
	public void setUserSignature(java.lang.String value) {
		this.userSignature = value;
	}
	
	public java.lang.String getUserSignature() {
		return this.userSignature;
	}
	
	public void setFansNum(java.lang.Integer value) {
		this.fansNum = value;
	}
	
	public java.lang.Integer getFansNum() {
		return this.fansNum;
	}
	
	public void setPraiseNum(java.lang.Integer value) {
		this.praiseNum = value;
	}
	
	public java.lang.Integer getPraiseNum() {
		return this.praiseNum;
	}
	
	public void setEvaluationNum(java.lang.Integer value) {
		this.evaluationNum = value;
	}
	
	public java.lang.Integer getEvaluationNum() {
		return this.evaluationNum;
	}
	
	public void setDiscussNum(java.lang.Integer value) {
		this.discussNum = value;
	}
	
	public java.lang.Integer getDiscussNum() {
		return this.discussNum;
	}
	
	public void setArticleNum(java.lang.Integer value) {
		this.articleNum = value;
	}
	
	public java.lang.Integer getArticleNum() {
		return this.articleNum;
	}
	
	
	
	public BigDecimal getKffCoinNum() {
		return kffCoinNum;
	}

	public void setKffCoinNum(BigDecimal kffCoinNum) {
		this.kffCoinNum = kffCoinNum;
	}

	public void setAreaName(java.lang.String value) {
		this.areaName = value;
	}
	
	public java.lang.String getAreaName() {
		return this.areaName;
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
	
	public void setProvinceCode(java.lang.String value) {
		this.provinceCode = value;
	}
	
	public java.lang.String getProvinceCode() {
		return this.provinceCode;
	}
	
	public void setCityCode(java.lang.String value) {
		this.cityCode = value;
	}
	
	public java.lang.String getCityCode() {
		return this.cityCode;
	}
	
	public void setAreaCode(java.lang.String value) {
		this.areaCode = value;
	}
	
	public java.lang.String getAreaCode() {
		return this.areaCode;
	}
	
	public void setReferUserId(java.lang.Integer value) {
		this.referUserId = value;
	}
	
	public java.lang.Integer getReferUserId() {
		return this.referUserId;
	}

	public java.lang.Integer getSex() {
		return sex;
	}

	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}

	public java.lang.Integer getUserType() {
		return userType;
	}

	public void setUserType(java.lang.Integer userType) {
		this.userType = userType;
	}

	public java.lang.Integer getReferLevel() {
		return referLevel;
	}

	public void setReferLevel(java.lang.Integer referLevel) {
		this.referLevel = referLevel;
	}

	public java.lang.Integer getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(java.lang.Integer followStatus) {
		this.followStatus = followStatus;
	}
	
	

	
}

