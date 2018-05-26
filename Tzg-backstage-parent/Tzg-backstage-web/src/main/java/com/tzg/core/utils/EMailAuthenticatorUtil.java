package com.tzg.core.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EMailAuthenticatorUtil extends Authenticator {
	String userName = null;
	String password = null;

	public EMailAuthenticatorUtil() {
	}

	public EMailAuthenticatorUtil(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}
