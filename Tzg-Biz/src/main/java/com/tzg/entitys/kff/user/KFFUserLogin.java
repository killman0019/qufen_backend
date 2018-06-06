package com.tzg.entitys.kff.user;

import java.io.Serializable;

public class KFFUserLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8971531252922372927L;
	// {"loginName":"asdas","password":"scdafcas"}:
	private String loginName;
	private String password;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
