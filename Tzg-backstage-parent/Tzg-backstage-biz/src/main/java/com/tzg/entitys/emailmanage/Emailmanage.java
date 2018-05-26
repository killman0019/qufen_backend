package com.tzg.entitys.emailmanage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.tzg.common.utils.DateUtil;

public class Emailmanage implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * vcTitle
     */ 	
	private java.lang.String vcTitle;
    /**
     * vcContent
     */ 	
	private java.lang.String vcContent;
    /**
     * 1 -- 所有人； 2 -- 投资者； 3 -- 借款人；
     */ 	
	private java.lang.Integer itype;
    /**
     * 1 -- 刚录入； 2 -- 发布中； 3 -- 已发布。
     */ 	
	private java.lang.Integer istate;
    /**
     * dtCreate
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * dtPublish
     */ 	
	private java.util.Date dtPublish;
	private java.lang.String dtPublishStr;
    /**
     * 界面点击发布时，查询前台登陆账号中的最大ID。
     */ 	
	private java.lang.Integer imaxId;
    /**
     * 发布时，登陆账号按ID从小到大排序。记录当前发布的登陆账号ID。任务中断时，允许从此字段继续。
     */ 	
	private java.lang.Integer icurrentId;

	private Date dtRegisterStart;// 注册开始时间
	private String dtRegisterStartStr;
	private Date dtRegisterEnd;// 注册结束时间
	private String dtRegisterEndStr;
	private Date dtLastLoginStart;// 最后开始登录时间
	private String dtLastLoginStartStr;
	private Date dtLastLoginEnd;// 最后登录结束时间
	private String dtLastLoginEndStr;
	private Date dtLastRechargeStart;// 最后开始充值时间
	private String dtLastRechargeStartStr;
	private Date dtLastRechargeEnd;// 最后结束充值时间
	private String dtLastRechargeEndStr;
	private Date dtLastInvestStart;// 最后开始投资时间
	private String dtLastInvestStartStr;
	private Date dtLastInvestEnd;// 最后结束投资时间
	private String dtLastInvestEndStr;
	private BigDecimal minTotalRecharge;// 最小累计充值金额
	private BigDecimal maxTotalRecharge;// 最大累计充值金额
	private BigDecimal minTotalInvest;// 最小累计投资金额
	private BigDecimal maxTotalInvest;// 最大累计投资金额
	private BigDecimal minTotalBalance;// 最小账户余额
	private BigDecimal maxTotalBalance;// 最大账户余额
	private Integer sType;// 发送类型：1：立即；2：定时
	private Date dtSend;// 定时时间
	private String dtSendStr;
	
	public Date getDtRegisterStart() {
		return dtRegisterStart;
	}

	public void setDtRegisterStart(Date dtRegisterStart) {
		this.dtRegisterStartStr = DateUtil.getDate(dtRegisterStart, "yyyy-MM-dd");
		this.dtRegisterStart = dtRegisterStart;
	}

	public String getDtRegisterStartStr() {
		return dtRegisterStartStr;
	}

	public void setDtRegisterStartStr(String dtRegisterStartStr) {
		this.dtRegisterStart = DateUtil.getDate(dtRegisterStartStr, "yyyy-MM-dd");
		this.dtRegisterStartStr = dtRegisterStartStr;
	}

	public Date getDtRegisterEnd() {
		return dtRegisterEnd;
	}

	public void setDtRegisterEnd(Date dtRegisterEnd) {
		this.dtRegisterEndStr = DateUtil.getDate(dtRegisterEnd, "yyyy-MM-dd");
		this.dtRegisterEnd = dtRegisterEnd;
	}

	public String getDtRegisterEndStr() {
		return dtRegisterEndStr;
	}

	public void setDtRegisterEndStr(String dtRegisterEndStr) {
		this.dtRegisterEnd = DateUtil.getDate(dtRegisterEndStr, "yyyy-MM-dd");
		this.dtRegisterEndStr = dtRegisterEndStr;
	}

	public Date getDtLastLoginStart() {
		return dtLastLoginStart;
	}

	public void setDtLastLoginStart(Date dtLastLoginStart) {
		this.dtLastLoginStartStr = DateUtil.getDate(dtLastLoginStart, "yyyy-MM-dd");
		this.dtLastLoginStart = dtLastLoginStart;
	}

	public String getDtLastLoginStartStr() {
		return dtLastLoginStartStr;
	}

	public void setDtLastLoginStartStr(String dtLastLoginStartStr) {
		this.dtLastLoginStart = DateUtil.getDate(dtLastLoginStartStr, "yyyy-MM-dd");
		this.dtLastLoginStartStr = dtLastLoginStartStr;
	}

	public Date getDtLastLoginEnd() {
		return dtLastLoginEnd;
	}

	public void setDtLastLoginEnd(Date dtLastLoginEnd) {
		this.dtLastLoginEndStr = DateUtil.getDate(dtLastLoginEnd, "yyyy-MM-dd");
		this.dtLastLoginEnd = dtLastLoginEnd;
	}

	public String getDtLastLoginEndStr() {
		return dtLastLoginEndStr;
	}

	public void setDtLastLoginEndStr(String dtLastLoginEndStr) {
		this.dtLastLoginEnd = DateUtil.getDate(dtLastLoginEndStr, "yyyy-MM-dd");
		this.dtLastLoginEndStr = dtLastLoginEndStr;
	}

	public Date getDtLastRechargeStart() {
		return dtLastRechargeStart;
	}

	public void setDtLastRechargeStart(Date dtLastRechargeStart) {
		this.dtLastRechargeStartStr = DateUtil.getDate(dtLastRechargeStart, "yyyy-MM-dd");
		this.dtLastRechargeStart = dtLastRechargeStart;
	}

	public String getDtLastRechargeStartStr() {
		return dtLastRechargeStartStr;
	}

	public void setDtLastRechargeStartStr(String dtLastRechargeStartStr) {
		this.dtLastRechargeStart = DateUtil.getDate(dtLastRechargeStartStr, "yyyy-MM-dd");
		this.dtLastRechargeStartStr = dtLastRechargeStartStr;
	}

	public Date getDtLastRechargeEnd() {
		return dtLastRechargeEnd;
	}

	public void setDtLastRechargeEnd(Date dtLastRechargeEnd) {
		this.dtLastRechargeEndStr = DateUtil.getDate(dtLastRechargeEnd, "yyyy-MM-dd");
		this.dtLastRechargeEnd = dtLastRechargeEnd;
	}

	public String getDtLastRechargeEndStr() {
		return dtLastRechargeEndStr;
	}

	public void setDtLastRechargeEndStr(String dtLastRechargeEndStr) {
		this.dtLastRechargeEnd = DateUtil.getDate(dtLastRechargeEndStr, "yyyy-MM-dd");
		this.dtLastRechargeEndStr = dtLastRechargeEndStr;
	}

	public Date getDtLastInvestStart() {
		return dtLastInvestStart;
	}

	public void setDtLastInvestStart(Date dtLastInvestStart) {
		this.dtLastInvestStartStr = DateUtil.getDate(dtLastInvestStart, "yyyy-MM-dd");
		this.dtLastInvestStart = dtLastInvestStart;
	}

	public String getDtLastInvestStartStr() {
		return dtLastInvestStartStr;
	}

	public void setDtLastInvestStartStr(String dtLastInvestStartStr) {
		this.dtLastInvestStart = DateUtil.getDate(dtLastInvestStartStr, "yyyy-MM-dd");
		this.dtLastInvestStartStr = dtLastInvestStartStr;
	}

	public Date getDtLastInvestEnd() {
		return dtLastInvestEnd;
	}

	public void setDtLastInvestEnd(Date dtLastInvestEnd) {
		this.dtLastInvestEndStr = DateUtil.getDate(dtLastInvestEnd, "yyyy-MM-dd");
		this.dtLastInvestEnd = dtLastInvestEnd;
	}

	public String getDtLastInvestEndStr() {
		return dtLastInvestEndStr;
	}

	public void setDtLastInvestEndStr(String dtLastInvestEndStr) {
		this.dtLastInvestEnd = DateUtil.getDate(dtLastInvestEndStr, "yyyy-MM-dd");
		this.dtLastInvestEndStr = dtLastInvestEndStr;
	}

	public BigDecimal getMinTotalRecharge() {
		return minTotalRecharge;
	}

	public void setMinTotalRecharge(BigDecimal minTotalRecharge) {
		this.minTotalRecharge = minTotalRecharge;
	}

	public BigDecimal getMaxTotalRecharge() {
		return maxTotalRecharge;
	}

	public void setMaxTotalRecharge(BigDecimal maxTotalRecharge) {
		this.maxTotalRecharge = maxTotalRecharge;
	}

	public BigDecimal getMinTotalInvest() {
		return minTotalInvest;
	}

	public void setMinTotalInvest(BigDecimal minTotalInvest) {
		this.minTotalInvest = minTotalInvest;
	}

	public BigDecimal getMaxTotalInvest() {
		return maxTotalInvest;
	}

	public void setMaxTotalInvest(BigDecimal maxTotalInvest) {
		this.maxTotalInvest = maxTotalInvest;
	}

	public BigDecimal getMinTotalBalance() {
		return minTotalBalance;
	}

	public void setMinTotalBalance(BigDecimal minTotalBalance) {
		this.minTotalBalance = minTotalBalance;
	}

	public BigDecimal getMaxTotalBalance() {
		return maxTotalBalance;
	}

	public void setMaxTotalBalance(BigDecimal maxTotalBalance) {
		this.maxTotalBalance = maxTotalBalance;
	}

	public Integer getsType() {
		return sType;
	}

	public void setsType(Integer sType) {
		this.sType = sType;
	}

	public Date getDtSend() {
		return dtSend;
	}

	public void setDtSend(Date dtSend) {
		this.dtSendStr = DateUtil.getDate(dtSend, "yyyy-MM-dd HH:mm:ss");
		this.dtSend = dtSend;
	}
	public void setDtCreateStr(java.lang.String dtCreateStr) {
		this.dtCreate = DateUtil.getDate(dtCreateStr, "yyyy-MM-dd HH:mm:ss");
		this.dtCreateStr = dtCreateStr;
	}

	public String getDtSendStr() {
		return dtSendStr;
	}

	public void setDtSendStr(String dtSendStr) {
		this.dtSend = DateUtil.getDate(dtSendStr, "yyyy-MM-dd HH:mm:ss");
		this.dtSendStr = dtSendStr;
	}

	public void setDtPublishStr(java.lang.String dtPublishStr) {
		this.dtPublish = DateUtil.getDate(dtPublishStr, "yyyy-MM-dd HH:mm:ss");
		this.dtPublishStr = dtPublishStr;
	}


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
	
	public void setVcContent(java.lang.String value) {
		this.vcContent = value;
	}
	
	public java.lang.String getVcContent() {
		return this.vcContent;
	}
	
	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}
	
	public java.lang.Integer getItype() {
		return this.itype;
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
	
	public void setDtPublish(java.util.Date value) {
		this.dtPublishStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtPublish = value;
	}
	
	public java.util.Date getDtPublish() {
		return this.dtPublish;
	}
	
	public java.lang.String getDtPublishStr() {
		return this.dtPublishStr;
	}
	
	public void setImaxId(java.lang.Integer value) {
		this.imaxId = value;
	}
	
	public java.lang.Integer getImaxId() {
		return this.imaxId;
	}
	
	public void setIcurrentId(java.lang.Integer value) {
		this.icurrentId = value;
	}
	
	public java.lang.Integer getIcurrentId() {
		return this.icurrentId;
	}

	
}

