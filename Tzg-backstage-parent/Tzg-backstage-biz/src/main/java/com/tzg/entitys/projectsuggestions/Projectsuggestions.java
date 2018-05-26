package com.tzg.entitys.projectsuggestions;

import java.io.Serializable;

public class Projectsuggestions implements Serializable {
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
	 * vcOpinionGuarantee
	 */
	private java.lang.String vcOpinionGuarantee;
	/**
	 * vcOpinionControl
	 */
	private java.lang.String vcOpinionControl;
	/**
	 * vcOpinionLawyer
	 */
	private java.lang.String vcOpinionLawyer;

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public java.lang.Integer getiProjectID() {
		return iProjectID;
	}

	public void setiProjectID(java.lang.Integer iProjectID) {
		this.iProjectID = iProjectID;
	}

	public void setVcOpinionGuarantee(java.lang.String value) {
		this.vcOpinionGuarantee = value;
	}

	public java.lang.String getVcOpinionGuarantee() {
		return this.vcOpinionGuarantee;
	}

	public void setVcOpinionControl(java.lang.String value) {
		this.vcOpinionControl = value;
	}

	public java.lang.String getVcOpinionControl() {
		return this.vcOpinionControl;
	}

	public void setVcOpinionLawyer(java.lang.String value) {
		this.vcOpinionLawyer = value;
	}

	public java.lang.String getVcOpinionLawyer() {
		return this.vcOpinionLawyer;
	}

}
