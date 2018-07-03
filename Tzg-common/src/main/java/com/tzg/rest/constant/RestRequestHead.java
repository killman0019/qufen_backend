package com.tzg.rest.constant;

import java.io.Serializable;

/**
 * Created by jdw on 2015/12/10 0010. 请求头信息中的参数
 */
public class RestRequestHead implements Serializable {
	/***
	 * 平台1、IOS，2、Android
	 */
	private Integer platform;
	/***
	 * 机型(5s、HUAWEI P7 ...)
	 */
	private String phoneModel;
	/***
	 * 系统版本号
	 */
	private String systemVersion;
	/***
	 * 学乐app版本号
	 */
	private String appVersion;
	/***
	 * 网络运营商
	 */
	private String operator;
	/***
	 * 网络状态
	 */
	private String networkType;

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	@Override
	public String toString() {
		return "platform=" + platform + ", phoneModel='" + phoneModel + '\'' + ", systemVersion='" + systemVersion + '\'' + ", appVersion='" + appVersion
				+ '\'' + ", operator='" + operator + '\'' + ", networkType='" + networkType + '\'';
	}
}
