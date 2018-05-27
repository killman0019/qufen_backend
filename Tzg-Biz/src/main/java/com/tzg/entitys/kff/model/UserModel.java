package com.tzg.entitys.kff.model;

public class UserModel {

	private String userNick;
	// 用户头像绝对路径
	private String icon;

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
