package com.tzg.entitys.kff.model;

import java.io.Serializable;

public class UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3745098197050153069L;
	private String userNick;
	// 用户头像绝对路径
	private String icon;
	// 用户的类型
	private Integer userType;
	private Integer uid;

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	// 身份验证状态
	private Integer userIdStatus;

	// 认证状态
	private Integer authenticationStatus;

	public String getUserNick() {
		return userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	public Integer getAuthenticationStatus() {
		return authenticationStatus;
	}

	public void setAuthenticationStatus(Integer authenticationStatus) {
		this.authenticationStatus = authenticationStatus;
	}

	public Integer getUserIdStatus() {
		return userIdStatus;
	}

	public void setUserIdStatus(Integer userIdStatus) {
		this.userIdStatus = userIdStatus;
	}

}
