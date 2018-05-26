package com.tzg.entitys.registemessage;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Registemessage implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * vcContent
     */ 	
	private java.lang.String vcContent;
    /**
     * dtStartTime
     */ 	
	private java.util.Date dtStartTime;
	private java.lang.String dtStartTimeStr;
    /**
     * dtEndTime
     */ 	
	private java.util.Date dtEndTime;
	private java.lang.String dtEndTimeStr;
    /**
     * 1 -- 有效；             2 -- 无效。
     */ 	
	private java.lang.Integer istate;
    /**
     * dtCreate
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * dtModify
     */ 	
	private java.util.Date dtModify;
	private java.lang.String dtModifyStr;
	private java.lang.String rownum;
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcContent(java.lang.String value) {
		this.vcContent = value;
	}
	
	public java.lang.String getVcContent() {
		return this.vcContent;
	}
	
	public void setDtStartTime(java.util.Date value) {
		this.dtStartTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtStartTime = value;
	}
	
	public java.util.Date getDtStartTime() {
		return this.dtStartTime;
	}
	
	public java.lang.String getDtStartTimeStr() {
		return this.dtStartTimeStr;
	}
	
	public void setDtEndTime(java.util.Date value) {
		this.dtEndTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtEndTime = value;
	}
	
	public java.util.Date getDtEndTime() {
		return this.dtEndTime;
	}
	
	public java.lang.String getDtEndTimeStr() {
		return this.dtEndTimeStr;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
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
	
	public void setDtModify(java.util.Date value) {
		this.dtModifyStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtModify = value;
	}
	
	public java.util.Date getDtModify() {
		return this.dtModify;
	}
	
	public java.lang.String getDtModifyStr() {
		return this.dtModifyStr;
	}

	public void setDtStartTimeStr(java.lang.String dtStartTimeStr) {
		this.dtStartTime = DateUtil.getDate(dtStartTimeStr, "yyyy-MM-dd HH:mm:ss");
		this.dtStartTimeStr = dtStartTimeStr;
	}

	public void setDtEndTimeStr(java.lang.String dtEndTimeStr) {
		this.dtEndTime = DateUtil.getDate(dtEndTimeStr, "yyyy-MM-dd HH:mm:ss");
		this.dtEndTimeStr = dtEndTimeStr;
	}

	public void setDtCreateStr(java.lang.String dtCreateStr) {
		this.dtCreateStr = dtCreateStr;
	}

	public void setDtModifyStr(java.lang.String dtModifyStr) {
		this.dtModifyStr = dtModifyStr;
	}

	public java.lang.String getRownum() {
		return rownum;
	}

	public void setRownum(java.lang.String rownum) {
		this.rownum = rownum;
	}

	
}

