package com.tzg.entitys.photo;

import java.io.Serializable;

public class PhotoIview implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4525932723801995444L;

	private String url;
	
	private String name;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
