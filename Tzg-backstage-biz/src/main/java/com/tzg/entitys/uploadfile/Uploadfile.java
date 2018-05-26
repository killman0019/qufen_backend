package com.tzg.entitys.uploadfile;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Uploadfile implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 1 -- 合同模板；             2 -- 风控审核目录清单文件（PC适用）；             3 -- 焦点图；             4 -- 保障措施图片；             5 -- 风控审核目录清单文件（非PC适用）；             
     */ 	
	private java.lang.Integer itype;
    /**
     * vcName
     */ 	
	private java.lang.String vcName;
    /**
     * vcOrnName
     */ 	
	private java.lang.String vcOrnName;
    /**
     * 存放地址+新的文件名             文件名规则：20150117162350******.后缀             4位年 2位月 2位日期 2位小时 2位分  2位秒 6位随机数
     */ 	
	private java.lang.String vcUrl;
    /**
     * blobSource
     */ 	
	private byte[] blobSource;
    /**
     * 1 -- 未同步；             2 -- 已同步。
     */ 	
	private java.lang.Integer istate;
    /**
     * dtCreate
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}
	
	public java.lang.Integer getItype() {
		return this.itype;
	}
	
	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}
	
	public java.lang.String getVcName() {
		return this.vcName;
	}
	
	public void setVcOrnName(java.lang.String value) {
		this.vcOrnName = value;
	}
	
	public java.lang.String getVcOrnName() {
		return this.vcOrnName;
	}
	
	public void setVcUrl(java.lang.String value) {
		this.vcUrl = value;
	}
	
	public java.lang.String getVcUrl() {
		return this.vcUrl;
	}
	
	public void setBlobSource(byte[] value) {
		this.blobSource = value;
	}
	
	public byte[] getBlobSource() {
		return this.blobSource;
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

	
}

