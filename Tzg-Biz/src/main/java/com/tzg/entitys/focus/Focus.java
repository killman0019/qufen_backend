package com.tzg.entitys.focus;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Focus implements Serializable {
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
	 * vcUrl
	 */
	private java.lang.String vcUrl;
	
	private java.lang.String vcChannel;
	
	/**
	 * isort
	 */
	private java.lang.Integer isort;
	/**
	 * 1 -- 有效； 2 -- 无效。
	 */
	private java.lang.Integer istate;
	/**
	 * dtCreate
	 */
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;

	private java.lang.String vcJumpUrl;
	
	/**
     * itype
     */ 	
	private java.lang.Integer itype;
	/**
     * iblankWindow
     */ 	
	private java.lang.Integer iblankWindow;
	
	public java.lang.Integer getItype() {
		return itype;
	}

	public void setItype(java.lang.Integer itype) {
		this.itype = itype;
	}

	public java.lang.String getVcJumpUrl() {
		return vcJumpUrl;
	}

	public void setVcJumpUrl(java.lang.String vcJumpUrl) {
		this.vcJumpUrl = vcJumpUrl;
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

	public void setVcUrl(java.lang.String value) {
		this.vcUrl = value;
	}

	public java.lang.String getVcUrl() {
		return this.vcUrl;
	}

	
	public java.lang.String getVcChannel() {
		return vcChannel;
	}

	public void setVcChannel(java.lang.String vcChannel) {
		this.vcChannel = vcChannel;
	}

	public void setIsort(java.lang.Integer value) {
		this.isort = value;
	}

	public java.lang.Integer getIsort() {
		return this.isort;
	}

	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}

	public java.lang.Integer getIstate() {
		return this.istate;
	}

	public void setDtCreate(java.util.Date value) {
		this.dtCreateStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtCreate = value;
	}

	public java.util.Date getDtCreate() {
		return this.dtCreate;
	}

	public java.lang.String getDtCreateStr() {
		return this.dtCreateStr;
	}
	
	public void setIblankWindow(java.lang.Integer value) {
		this.iblankWindow = value;
	}
	
	public java.lang.Integer getIblankWindow() {
		return this.iblankWindow;
	}
}
