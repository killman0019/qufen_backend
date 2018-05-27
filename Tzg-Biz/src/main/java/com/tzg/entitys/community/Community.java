package com.tzg.entitys.community;

import java.io.Serializable;

import com.tzg.common.utils.DateUtil;

/**
 * 
 * 
 * @author lz-h@tzg.cn
 * @version $Id: Community.java, v 0.1 2015年8月28日 上午10:18:28 lz-h@tzg.cn Exp $
 */
public class Community implements Serializable {
	
	/**
	 * 状态：置顶
	 */
	public static final int STATE_TOP_INT = 1<<0;
	
	private static final long serialVersionUID = 952596256148188791L;
	
	private java.lang.Integer id;
	private java.lang.String vcTitle;	
	private java.lang.String vcUrl;
	private java.util.Date dtUpdate;
	private java.lang.String dtUpdateStr;
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
	private java.lang.Integer istate;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcTitle(java.lang.String value) {
		this.vcTitle = value;
	}
	
	public java.lang.String getVcTitle() {
		return this.vcTitle;
	}
	
	
	public void setDtUpdate(java.util.Date value) {
		this.dtUpdateStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtUpdate = value;
	}
	
	public java.util.Date getDtUpdate() {
		return this.dtUpdate;
	}
	
	public java.lang.String getDtUpdateStr() {
		return this.dtUpdateStr;
	}
	
	
	public void setDtCreate(java.util.Date value) {
		this.dtCreateStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtCreate = value;
	}
	
	public java.util.Date getDtCreate() {
		return this.dtCreate;
	}
	
	public java.lang.String getDtCreateStr() {
		return this.dtCreateStr;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
	}

	public java.lang.String getVcUrl() {
		return vcUrl;
	}

	public void setVcUrl(java.lang.String vcUrl) {
		this.vcUrl = vcUrl;
	}
	
}

