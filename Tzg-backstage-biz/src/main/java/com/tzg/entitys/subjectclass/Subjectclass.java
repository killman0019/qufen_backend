package com.tzg.entitys.subjectclass;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Subjectclass implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * [主键]
     */ 	
	private java.lang.Integer id;
    /**
     * [标的分类]             1-常规标的；             2-定期宝；             3-活期宝；             4-体验标
     */ 	
	private java.lang.Integer iclassType;
    /**
     * [标的分类名称]
     */ 	
	private java.lang.String vcClassName;
    /**
     * 根据分类的类型定义
     */ 	
	private java.lang.Integer itype;
    /**
     * [标的类型名称]
     */ 	
	private java.lang.String vcTypeName;
    /**
     * [创建人]
     */ 	
	private java.lang.Integer icreate;
    /**
     * [创建时间]
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * [复核人]
     */ 	
	private java.lang.Integer icheck;
    /**
     * [复核时间]
     */ 	
	private java.util.Date dtCheck;
	private java.lang.String dtCheckStr;
    /**
     * [状态]             1-可用；             2-停用；             3-复核（不可删除）             
     */ 	
	private java.lang.Integer istatus;
	
	/**
	 * [是否可用加息券] 1-可用  2-不可用
	 */
	private java.lang.Integer iticket;
	

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIclassType(java.lang.Integer value) {
		this.iclassType = value;
	}
	
	public java.lang.Integer getIclassType() {
		return this.iclassType;
	}
	
	public void setVcClassName(java.lang.String value) {
		this.vcClassName = value;
	}
	
	public java.lang.String getVcClassName() {
		return this.vcClassName;
	}
	
	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}
	
	public java.lang.Integer getItype() {
		return this.itype;
	}
	
	public void setVcTypeName(java.lang.String value) {
		this.vcTypeName = value;
	}
	
	public java.lang.String getVcTypeName() {
		return this.vcTypeName;
	}
	
	public void setIcreate(java.lang.Integer value) {
		this.icreate = value;
	}
	
	public java.lang.Integer getIcreate() {
		return this.icreate;
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
	
	public void setIcheck(java.lang.Integer value) {
		this.icheck = value;
	}
	
	public java.lang.Integer getIcheck() {
		return this.icheck;
	}
	
	public void setDtCheck(java.util.Date value) {
		this.dtCheckStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtCheck = value;
	}
	
	public java.util.Date getDtCheck() {
		return this.dtCheck;
	}
	
	public java.lang.String getDtCheckStr() {
		return this.dtCheckStr;
	}
	
	public void setIstatus(java.lang.Integer value) {
		this.istatus = value;
	}
	
	public java.lang.Integer getIstatus() {
		return this.istatus;
	}
	
	public java.lang.Integer getIticket() {
		return iticket;
	}

	public void setIticket(java.lang.Integer iticket) {
		this.iticket = iticket;
	}
	
	

}

