package com.tzg.entitys.kff.follow;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Follow implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5441793155517659401L;
	/**
     * followId
     */ 	
	private java.lang.Integer followId;
    /**
     * followUserId
     */ 	
	private java.lang.Integer followUserId;
    /**
     * 关注类型：1-关注项目;2-关注帖子；3-关注用户
     */ 	
	private java.lang.Integer followType;
    /**
     * followerUserName
     */ 	
	private java.lang.String followerUserName;
    /**
     * followedUserId
     */ 	
	private java.lang.Integer followedUserId;
    /**
     * followedUserSignature
     */ 	
	private java.lang.String followedUserSignature;
    /**
     * followedUserIcon
     */ 	
	private java.lang.String followedUserIcon;
    /**
     * followedUserName
     */ 	
	private java.lang.String followedUserName;
    /**
     * 关注项目为项目id，关注用户为uerid，关注帖子为postid
     */ 	
	private java.lang.Integer followedId;
 
    /**
     * 状态：1-有效；0-删除
     */ 	
	private java.lang.Integer status;
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

	
	public void setFollowId(java.lang.Integer value) {
		this.followId = value;
	}
	
	public java.lang.Integer getFollowId() {
		return this.followId;
	}
	
	public void setFollowUserId(java.lang.Integer value) {
		this.followUserId = value;
	}
	
	public java.lang.Integer getFollowUserId() {
		return this.followUserId;
	}
	
	
	
	public java.lang.Integer getFollowType() {
		return followType;
	}

	public void setFollowType(java.lang.Integer followType) {
		this.followType = followType;
	}

	public void setFollowerUserName(java.lang.String value) {
		this.followerUserName = value;
	}
	
	public java.lang.String getFollowerUserName() {
		return this.followerUserName;
	}
	
	public void setFollowedUserId(java.lang.Integer value) {
		this.followedUserId = value;
	}
	
	public java.lang.Integer getFollowedUserId() {
		return this.followedUserId;
	}
	
	public void setFollowedUserSignature(java.lang.String value) {
		this.followedUserSignature = value;
	}
	
	public java.lang.String getFollowedUserSignature() {
		return this.followedUserSignature;
	}
	
	public void setFollowedUserIcon(java.lang.String value) {
		this.followedUserIcon = value;
	}
	
	public java.lang.String getFollowedUserIcon() {
		return this.followedUserIcon;
	}
	
	public void setFollowedUserName(java.lang.String value) {
		this.followedUserName = value;
	}
	
	public java.lang.String getFollowedUserName() {
		return this.followedUserName;
	}
	

	
	public java.lang.Integer getFollowedId() {
		return followedId;
	}

	public void setFollowedId(java.lang.Integer followedId) {
		this.followedId = followedId;
	}

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
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

	
}

