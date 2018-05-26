package com.tzg.entitys.projectcataloglist;

import java.io.Serializable;

public class Projectcataloglist implements Serializable {
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
	 * vcCatalogTypeName
	 */
	private java.lang.String vcCatalogTypeName;
	/**
	 * icatalogTypeSort
	 */
	private java.lang.Integer iCatalogTypeSort;
	/**
	 * vcCatalogName
	 */
	private java.lang.String vcCatalogName;
	/**
	 * icatalogSort
	 */
	private java.lang.Integer iCatalogSort;
	/**
	 * 如： /tzg/p2p/cate/abc.jpg
	 */
	private java.lang.String vcCatalogIconURL;

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setVcCatalogTypeName(java.lang.String value) {
		this.vcCatalogTypeName = value;
	}

	public java.lang.String getVcCatalogTypeName() {
		return this.vcCatalogTypeName;
	}

	public void setVcCatalogName(java.lang.String value) {
		this.vcCatalogName = value;
	}

	public java.lang.String getVcCatalogName() {
		return this.vcCatalogName;
	}

	public java.lang.Integer getiProjectID() {
		return iProjectID;
	}

	public void setiProjectID(java.lang.Integer iProjectID) {
		this.iProjectID = iProjectID;
	}

	public java.lang.Integer getiCatalogTypeSort() {
		return iCatalogTypeSort;
	}

	public void setiCatalogTypeSort(java.lang.Integer iCatalogTypeSort) {
		this.iCatalogTypeSort = iCatalogTypeSort;
	}

	public java.lang.Integer getiCatalogSort() {
		return iCatalogSort;
	}

	public void setiCatalogSort(java.lang.Integer iCatalogSort) {
		this.iCatalogSort = iCatalogSort;
	}

	public java.lang.String getVcCatalogIconURL() {
		return vcCatalogIconURL;
	}

	public void setVcCatalogIconURL(java.lang.String vcCatalogIconURL) {
		this.vcCatalogIconURL = vcCatalogIconURL;
	}

}
