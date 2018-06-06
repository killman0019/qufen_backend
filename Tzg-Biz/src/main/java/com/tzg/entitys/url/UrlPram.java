package com.tzg.entitys.url;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;


public class UrlPram implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -216454853709485657L;

	@Value("${picUrl}")
	private String picUrl;

	@Value("${registerUrl}")
	private String registerUrl;

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getRegisterUrl() {
		return registerUrl;
	}

	public void setRegisterUrl(String registerUrl) {
		this.registerUrl = registerUrl;
	}

}
