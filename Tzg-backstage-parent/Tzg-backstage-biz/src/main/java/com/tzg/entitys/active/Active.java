package com.tzg.entitys.active;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Active implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 活动名称
     */ 	
	private java.lang.String vcName;
    /**
     * 开始时间
     */ 	
	private java.util.Date dtbegin;
	private java.lang.String dtbeginStr;
    /**
     * 结束时间
     */ 	
	private java.util.Date dtend;
	private java.lang.String dtendStr;
    /**
     * 描述
     */ 	
	private java.lang.String vcDesc;
    /**
     * 基数
     */ 	
	private java.lang.Integer numBase;
    /**
     * 状态
     */ 	
	private java.lang.Integer istate;

	
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
	
	public void setDtbegin(java.util.Date value) {
		this.dtbeginStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtbegin = value;
	}
	
	public java.util.Date getDtbegin() {
		return this.dtbegin;
	}
	
	public java.lang.String getDtbeginStr() {
		return this.dtbeginStr;
	}
	
	public void setDtend(java.util.Date value) {
		this.dtendStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtend = value;
	}
	
	public java.util.Date getDtend() {
		return this.dtend;
	}
	
	public java.lang.String getDtendStr() {
		return this.dtendStr;
	}
	
	public void setVcDesc(java.lang.String value) {
		this.vcDesc = value;
	}
	
	public java.lang.String getVcDesc() {
		return this.vcDesc;
	}
	
	public void setNumBase(java.lang.Integer value) {
		this.numBase = value;
	}
	
	public java.lang.Integer getNumBase() {
		return this.numBase;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
	}

	
}

