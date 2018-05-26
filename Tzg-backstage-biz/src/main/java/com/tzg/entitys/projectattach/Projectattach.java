package com.tzg.entitys.projectattach;

import java.io.Serializable;

public class Projectattach implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * iprojectId
	 */
	private java.lang.Integer iProjectID;
	/**
	 * vcAttachName
	 */
	private java.lang.String vcAttachName;
	/**
	 * 系统参数设置存放路径+文件名称
	 */
	private java.lang.String vcFilePath;
	
	private java.lang.Integer iType;
	
	/**
	 * isort
	 */
	private java.lang.Integer iSort;
	/**
	 * 1-- 有效； 2-- 无效。
	 */
	private java.lang.Integer iState;
	
	//
	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setVcAttachName(java.lang.String value) {
		this.vcAttachName = value;
	}

	public java.lang.String getVcAttachName() {
		return this.vcAttachName;
	}

	public void setVcFilePath(java.lang.String value) {
		this.vcFilePath = value;
	}

	public java.lang.String getVcFilePath() {
		return this.vcFilePath;
	}

	public java.lang.Integer getiProjectID() {
		return iProjectID;
	}

	public void setiProjectID(java.lang.Integer iProjectID) {
		this.iProjectID = iProjectID;
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

	public java.lang.Integer getiType() {
		return iType;
	}

	public void setiType(java.lang.Integer iType) {
		this.iType = iType;
	}

	
}
