package com.tzg.entitys.cerifycataloglist;

import java.io.Serializable;

public class Cerifycataloglist implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * itypeId
	 */
	private java.lang.Integer iTypeID;
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

	private Integer uploadFileType;

	public Integer getUploadFileType() {
		return uploadFileType;
	}

	public void setUploadFileType(Integer uploadFileType) {
		this.uploadFileType = uploadFileType;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}

	public java.lang.String getVcName() {
		return this.vcName;
	}

	public java.lang.Integer getiTypeID() {
		return iTypeID;
	}

	public void setiTypeID(java.lang.Integer iTypeID) {
		this.iTypeID = iTypeID;
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
