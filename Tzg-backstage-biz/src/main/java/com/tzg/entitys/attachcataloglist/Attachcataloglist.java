package com.tzg.entitys.attachcataloglist;

import java.io.Serializable;

public class Attachcataloglist implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * vcName
	 */
	private java.lang.String vcName;
	/**
	 * 排列顺序
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

	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}

	public java.lang.String getVcName() {
		return this.vcName;
	}

	public void setiSort(java.lang.Integer value) {
		this.iSort = value;
	}

	public java.lang.Integer getiSort() {
		return this.iSort;
	}

	public java.lang.Integer getiState() {
		return iState;
	}

	public void setiState(java.lang.Integer iState) {
		this.iState = iState;
	}

}
