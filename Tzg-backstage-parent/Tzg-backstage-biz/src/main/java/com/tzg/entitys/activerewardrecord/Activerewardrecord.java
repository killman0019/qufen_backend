package com.tzg.entitys.activerewardrecord;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Activerewardrecord implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 活动id
     */ 	
	private java.lang.Integer iactiveId;
    /**
     * 用户id
     */ 	
	private java.lang.Integer iloginAccountId;
    /**
     * 奖励等级
     */ 	
	private java.lang.Integer ilevel;
    /**
     * 创建时间
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * 更新时间
     */ 	
	private java.util.Date dtUpdate;
	private java.lang.String dtUpdateStr;
	
	private java.lang.String phone;
	private java.lang.String prize;
	private java.lang.String useName;
	private java.lang.String vcLoginName;

	
	public java.lang.String getVcLoginName() {
		return vcLoginName;
	}

	public void setVcLoginName(java.lang.String vcLoginName) {
		this.vcLoginName = vcLoginName;
	}

	public java.lang.String getPhone() {
		return phone;
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getPrize() {
		return prize;
	}

	public void setPrize(java.lang.String prize) {
		this.prize = prize;
	}



	public java.lang.String getUseName() {
		return useName;
	}

	public void setUseName(java.lang.String useName) {
		this.useName = useName;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIactiveId(java.lang.Integer value) {
		this.iactiveId = value;
	}
	
	public java.lang.Integer getIactiveId() {
		return this.iactiveId;
	}
	
	public void setIloginAccountId(java.lang.Integer value) {
		this.iloginAccountId = value;
	}
	
	public java.lang.Integer getIloginAccountId() {
		return this.iloginAccountId;
	}
	
	public void setIlevel(java.lang.Integer value) {
		this.ilevel = value;
	}
	
	public java.lang.Integer getIlevel() {
		return this.ilevel;
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

	
}

