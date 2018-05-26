package com.tzg.entitys.smswhitelist;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Smswhitelist implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * vcPhone
     */ 	
	private java.lang.String vcPhone;
    /**
     * 该条记录第一次创建的时间
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcPhone(java.lang.String value) {
		this.vcPhone = value;
	}
	
	public java.lang.String getVcPhone() {
		return this.vcPhone;
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

