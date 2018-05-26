package com.tzg.rest.vo;

import java.io.Serializable;

public class Icon implements Serializable{
	
	

	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	private String iconName;
	/**
	 * 图片地址
	 */
	private String iconImg;
	/**
	 * 访问地址
	 */
	private String iconUrl;
	
	
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	public String getIconImg() {
		return iconImg;
	}
	public void setIconImg(String iconImg) {
		this.iconImg = iconImg;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	

}
