package com.tzg.entitys.kff.user;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class KFFUserHomeResponse implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = -5798363993620053676L;
    
	/**
	 * 我的主页 或者  XXX的主页
	 */
	private String homePageTitle;
	
	/**
	 * 是否显示 关注按钮 0- 不显示；1-显示关注  2-显示取消关注
	 */
	private java.lang.Integer showFollow = 0 ;
	/**
	 * 总回答数量
	 */
	private java.lang.Integer totalPostNum = 0;
	
	
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
	private java.lang.Integer sex = 1;
    /**
     * 头像
     */ 	
	private java.lang.String icon;



    /**
     * 用户类型:1-普通用户；2-项目方；3-评测机构；4-机构用户
     */ 	
	private java.lang.Integer userType = 1;
    /**
     * 预留字段 用户等级：1-普通用户；2-高级用户;3-VIP
     */ 	
	private java.lang.Integer userDegree;
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
	private java.lang.Integer praiseNum = 0;
    /**
     * 发表评测数量
     */ 	
	private java.lang.Integer evaluationNum = 0;
    /**
     * 发表讨论数量
     */ 	
	private java.lang.Integer discussNum  = 0;
    /**
     * 发表文章数量
     */ 	
	private java.lang.Integer articleNum  = 0;
    /**
     * 账号币值
     */ 	
	private java.lang.Integer kffCoinNum  = 0;
    /**
     * 所在地区域名称:浙江省杭州市
     */ 	
	private java.lang.String areaName;
  
    /**
     * 状态：0-删除；1-有效
     */ 	
	private java.lang.Integer status = 1;
   
  
	
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
	
	public void setKffCoinNum(java.lang.Integer value) {
		this.kffCoinNum = value;
	}
	
	public java.lang.Integer getKffCoinNum() {
		return this.kffCoinNum;
	}
	
	public void setAreaName(java.lang.String value) {
		this.areaName = value;
	}
	
	public java.lang.String getAreaName() {
		return this.areaName;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
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

	public String getHomePageTitle() {
		return homePageTitle;
	}

	public void setHomePageTitle(String homePageTitle) {
		this.homePageTitle = homePageTitle;
	}

	public java.lang.Integer getShowFollow() {
		return showFollow;
	}

	public void setShowFollow(java.lang.Integer showFollow) {
		this.showFollow = showFollow;
	}

	public java.lang.Integer getTotalPostNum() {
		return totalPostNum;
	}

	public void setTotalPostNum(java.lang.Integer totalPostNum) {
		this.totalPostNum = totalPostNum;
	}

	
	
	

	
}

