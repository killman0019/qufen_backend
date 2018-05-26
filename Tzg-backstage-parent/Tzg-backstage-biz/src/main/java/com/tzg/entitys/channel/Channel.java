package com.tzg.entitys.channel;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class Channel implements Serializable {
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
     * 1 -- 担保公司；             2 -- 小额贷款；             3 -- 资产管理；             4 -- 投资公司。
     */ 	
	private java.lang.Integer itype;

    /**
     * vcArea
     */ 	
	private java.lang.String vcArea;
    /**
     * ibusinessAreaId
     */ 	
	private java.lang.Integer ibusinessAreaId;
    /**
     * vcBusinessArea
     */ 	
	private java.lang.String vcBusinessArea;
    /**
     * numCredit
     */ 	
	private BigDecimal numCredit;
    /**
     * dtRegister
     */ 	
	private java.util.Date dtRegister;
	private java.lang.String dtRegisterStr;
    /**
     * numRegister
     */ 	
	private String vcRegister;
    /**
     * 国有企业；             国资背景；             民营企业。
     */ 	
	private java.lang.String vcCapital;
    /**
     * vcScale
     */ 	
	private java.lang.String vcScale;
    /**
     * 优良；             良好；             一般；             差；             极差。
     */ 	
	private java.lang.String vcState;
    /**
     * vcRate
     */ 	
	private java.lang.String vcRate;
    /**
     * vcLegalPerson
     */ 	
	private java.lang.String vcLegalPerson;
    /**
     * vcChairman
     */ 	
	private java.lang.String vcChairman;
    /**
     * vcCompanyTel
     */ 	
	private java.lang.String vcCompanyTel;
    /**
     * vcIntroduction
     */ 	
	private java.lang.String vcIntroduction;
    /**
     * vcComplain
     */ 	
	private java.lang.String vcComplain;
    /**
     * vcManageGroup
     */ 	
	private java.lang.String vcManageGroup;
    /**
     * vcContractName
     */ 	
	private java.lang.String vcContractName;
    /**
     * vcContractLevel
     */ 	
	private java.lang.String vcContractLevel;
    /**
     * vcContractTel
     */ 	
	private java.lang.String vcContractTel;
    /**
     * vcContractPhone
     */ 	
	private java.lang.String vcContractPhone;
    /**
     * vcContractEmail
     */ 	
	private java.lang.String vcContractEmail;
    /**
     * 1 -- 有效；             2 -- 无效。
     */ 	
	private java.lang.Integer ivalid;
    /**
     * 该条记录第一次创建的时间。
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * 最后一次修改时间，每次修改需更改此字段
     */ 	
	private java.util.Date dtModify;
	private java.lang.String dtModifyStr;

	
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
	
	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}
	
	public java.lang.Integer getItype() {
		return this.itype;
	}
	
	public void setVcArea(java.lang.String value) {
		this.vcArea = value;
	}
	
	public java.lang.String getVcArea() {
		return this.vcArea;
	}
	
	public void setIbusinessAreaId(java.lang.Integer value) {
		this.ibusinessAreaId = value;
	}
	
	public java.lang.Integer getIbusinessAreaId() {
		return this.ibusinessAreaId;
	}
	
	public void setVcBusinessArea(java.lang.String value) {
		this.vcBusinessArea = value;
	}
	
	public java.lang.String getVcBusinessArea() {
		return this.vcBusinessArea;
	}
	
	public void setNumCredit(BigDecimal value) {
		this.numCredit = value;
	}
	
	public BigDecimal getNumCredit() {
		return this.numCredit;
	}
	
	public void setDtRegister(java.util.Date value) {
		this.dtRegisterStr =DateUtil.getDate(value, "yyyy-MM-dd");
		this.dtRegister = value;
	}
	
	public java.util.Date getDtRegister() {
		return this.dtRegister;
	}
	
	public void setDtRegisterStr(java.lang.String dtRegisterStr) {
		this.dtRegister = DateUtil.getDate(dtRegisterStr, "yyyy-MM-dd");
		this.dtRegisterStr = dtRegisterStr;
	}

	public java.lang.String getDtRegisterStr() {
		return this.dtRegisterStr;
	}
	
	public String getVcRegister() {
		return vcRegister;
	}

	public void setVcRegister(String vcRegister) {
		this.vcRegister = vcRegister;
	}

	public void setVcCapital(java.lang.String value) {
		this.vcCapital = value;
	}
	
	public java.lang.String getVcCapital() {
		return this.vcCapital;
	}
	
	public void setVcScale(java.lang.String value) {
		this.vcScale = value;
	}
	
	public java.lang.String getVcScale() {
		return this.vcScale;
	}
	
	public void setVcState(java.lang.String value) {
		this.vcState = value;
	}
	
	public java.lang.String getVcState() {
		return this.vcState;
	}
	
	public void setVcRate(java.lang.String value) {
		this.vcRate = value;
	}
	
	public java.lang.String getVcRate() {
		return this.vcRate;
	}
	
	public void setVcLegalPerson(java.lang.String value) {
		this.vcLegalPerson = value;
	}
	
	public java.lang.String getVcLegalPerson() {
		return this.vcLegalPerson;
	}
	
	public void setVcChairman(java.lang.String value) {
		this.vcChairman = value;
	}
	
	public java.lang.String getVcChairman() {
		return this.vcChairman;
	}
	
	public void setVcCompanyTel(java.lang.String value) {
		this.vcCompanyTel = value;
	}
	
	public java.lang.String getVcCompanyTel() {
		return this.vcCompanyTel;
	}
	
	public void setVcIntroduction(java.lang.String value) {
		this.vcIntroduction = value;
	}
	
	public java.lang.String getVcIntroduction() {
		return this.vcIntroduction;
	}
	
	public void setVcComplain(java.lang.String value) {
		this.vcComplain = value;
	}
	
	public java.lang.String getVcComplain() {
		return this.vcComplain;
	}
	
	public void setVcManageGroup(java.lang.String value) {
		this.vcManageGroup = value;
	}
	
	public java.lang.String getVcManageGroup() {
		return this.vcManageGroup;
	}
	
	public void setVcContractName(java.lang.String value) {
		this.vcContractName = value;
	}
	
	public java.lang.String getVcContractName() {
		return this.vcContractName;
	}
	
	public void setVcContractLevel(java.lang.String value) {
		this.vcContractLevel = value;
	}
	
	public java.lang.String getVcContractLevel() {
		return this.vcContractLevel;
	}
	
	public void setVcContractTel(java.lang.String value) {
		this.vcContractTel = value;
	}
	
	public java.lang.String getVcContractTel() {
		return this.vcContractTel;
	}
	
	public void setVcContractPhone(java.lang.String value) {
		this.vcContractPhone = value;
	}
	
	public java.lang.String getVcContractPhone() {
		return this.vcContractPhone;
	}
	
	public void setVcContractEmail(java.lang.String value) {
		this.vcContractEmail = value;
	}
	
	public java.lang.String getVcContractEmail() {
		return this.vcContractEmail;
	}
	
	public void setIvalid(java.lang.Integer value) {
		this.ivalid = value;
	}
	
	public java.lang.Integer getIvalid() {
		return this.ivalid;
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
		this.dtModifyStr =DateUtil.getDate(value, "yyyy-MM-dd");
		this.dtModify = value;
	}
	
	public java.util.Date getDtModify() {
		return this.dtModify;
	}
	
	public java.lang.String getDtModifyStr() {
		return this.dtModifyStr;
	}

	
}

