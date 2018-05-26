package com.tzg.entitys.safeguardmeasures;

import java.io.Serializable;

public class Safeguardmeasures implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * vcCode
	 */
	private java.lang.String vcCode;
	/**
	 * vcName
	 */
	private java.lang.String vcName;
	
	/**
	 * 系统参数指定的目录+文件名
	 */
	private java.lang.String vcURL;
	/**
	 * isort
	 */
	private java.lang.Integer iSort;
	/**
	 * 1-- 有效； 2-- 无效。
	 */
	private java.lang.Integer iState;

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setVcCode(java.lang.String value) {
		this.vcCode = value;
	}

	public java.lang.String getVcCode() {
		return this.vcCode;
	}

	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}

	public java.lang.String getVcName() {
		return this.vcName;
	}

	public java.lang.String getVcURL() {
		return vcURL;
	}

	public void setVcURL(java.lang.String vcURL) {
		this.vcURL = vcURL;
	}

	public java.lang.Integer getiSort() {
		return iSort;
	}

	public void setiSort(java.lang.Integer iSort) {
		this.iSort = iSort;
	}

	public java.lang.Integer getiState() {
		return iState;
	}

	public void setiState(java.lang.Integer iState) {
		this.iState = iState;
	}

}
