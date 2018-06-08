package com.tzg.entitys.kff.authentication;

public class AuthenticationData extends Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2397406521927781073L;
	private String token;
	private Integer flag;

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
