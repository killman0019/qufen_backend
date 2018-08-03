package com.tzg.entitys.kff.user;

import java.io.Serializable;

public class UserRegister implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2296678180381726021L;
	private String phoneNumberqdhtml;
	private String checkCodeqdhtml;
	private String phoneCodeqdhtml;
	private String dynamicVerifyCodeqdhtml;
	private String invaUserIdHexqdhtml;

	public String getPhoneNumberqdhtml() {
		return phoneNumberqdhtml;
	}

	public void setPhoneNumberqdhtml(String phoneNumberqdhtml) {
		this.phoneNumberqdhtml = phoneNumberqdhtml;
	}

	public String getCheckCodeqdhtml() {
		return checkCodeqdhtml;
	}

	public void setCheckCodeqdhtml(String checkCodeqdhtml) {
		this.checkCodeqdhtml = checkCodeqdhtml;
	}

	public String getPhoneCodeqdhtml() {
		return phoneCodeqdhtml;
	}

	public void setPhoneCodeqdhtml(String phoneCodeqdhtml) {
		this.phoneCodeqdhtml = phoneCodeqdhtml;
	}

	public String getDynamicVerifyCodeqdhtml() {
		return dynamicVerifyCodeqdhtml;
	}

	public void setDynamicVerifyCodeqdhtml(String dynamicVerifyCodeqdhtml) {
		this.dynamicVerifyCodeqdhtml = dynamicVerifyCodeqdhtml;
	}

	public String getInvaUserIdHexqdhtml() {
		return invaUserIdHexqdhtml;
	}

	public void setInvaUserIdHexqdhtml(String invaUserIdHexqdhtml) {
		this.invaUserIdHexqdhtml = invaUserIdHexqdhtml;
	}

}
