package com.tzg.entitys.loginaccount;

import java.io.Serializable;
import java.util.Date;

import com.tzg.common.utils.DateUtil;

public class Loginaccount implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 登陆账号
     */ 	
	private java.lang.String vcLoginName;
    /**
     * 手机号
     */ 	
	private java.lang.String vcPhone;
	private java.lang.String vcPhone_secret;
    /**
     * 邮箱账号
     */ 	
	private java.lang.String vcEmail;
    /**
     * 登陆密码
     */ 	
	private java.lang.String vcPassword;
    /**
     * 密码等级 1 -- 简单级(纯数字或英文8位数以上);             2 -- 复杂级(数字+英文8位数以上).
     */ 	
	private java.lang.Integer ilevel;
    /**
     * 支付密码
     */ 	
	private java.lang.String vcPaymentcipher;
    /**
     * 1 -- 借款人；             2 -- 投资人；             3 -- 担保机构；             4 -- 平台账户；             5-- 渠道。
     */ 	
	private java.lang.Integer itype;
    /**
     * 是否修改过密码 1 -- 修改密码；             2 -- 未修改过密码。             只有投资人注册的账号默认为1。             其它账号类型刚生成账号时都默认为2，此类型的账号在登陆系统之后强制修改密码和交易密码
     */ 	
	private java.lang.Integer ioriginal;
    /**
     * 是否手机认证 1:已认证，2未认证
     */ 	
	private java.lang.Integer iverifyPhone;
    /**
     * 是否邮箱认证 1:已认证，2未认证
     */ 	
	private java.lang.Integer iverifyEmail;
    /**
     * 是否实名认证 1:已认证，2未认证
     */ 	
	private java.lang.Integer iverifyIdcard;
	private java.lang.String iverifyIdcardStr;
    /**
     * 姓名
     */ 	
	private java.lang.String vcName;
    /**
     * 证件类型 1 -- 身份证；             
     */ 	
	private java.lang.Integer icardType;
    /**
     * 证件号码
     */ 	
	private java.lang.String vcCardCode;
    /**
     * 账号来源
     */ 	
	private java.lang.String vcSource;
    /**
     * 首次投资时间
     */ 	
	private java.util.Date dtFirstInvest;
	private java.lang.String dtFirstInvestStr;
	
	/**投资次数*/
	private java.lang.Integer numInvestTimes;
    /**
     * 1 -- 有效；             2 -- 无效。
     */ 	
	private java.lang.Integer ivalid;
	private java.lang.String ivalidStr;
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
    /**
     * 注册时用的设备 可选值：             pc             wap             ios             android                          -------------             ios和android是指手机app
     */ 	
	private java.lang.String vcRegisterDevice;
	private java.lang.String vcRegisterDeviceStr;
    /**
     * 手机端采用
     */ 	
	private java.lang.String vcDeviceId;
    /**
     * 推荐码
     */ 	
	private java.lang.String vcRecommendCode;
	
	/**
     * 上次登录时间
     */ 	
	private java.util.Date dtLastLogin;
	private java.lang.String dtLastLoginStr;
	
	 /**
     * 交易密码状态  1:正常,2:被冻结
     */ 	
	private java.lang.Integer ipaymentcipherState;
    /**
     * 交易密码连续错误次数
     */ 	
	private java.lang.Integer numPayErrorTimes;
    /**
     * 最后一次交易密码错误时间
     */ 	
	private java.util.Date dtLastPayError;
	private java.lang.String dtLastPayErrorStr;

	private Date dtLastSale;
	private String dtLastSaleStr;
	
	/**
	 * 快捷支付密码
	 */
	private String vcFastPayPassword;
	
	/**
     * 快捷支付密码状态  1:正常,2:被冻结
     */
	private java.lang.Integer iFastPayState;
	
	/**
     * 快捷支付交易密码连续错误次数
     */
	private java.lang.Integer iFastPayErrorTimes;
	
	/**
     * 快捷支付交易密码最后输错时间
     */
	private Date dtFastLastPayErrorTime;
	
	/**
     * 修改快捷支付密码时间
     */
	private Date dtUpFastPayTime;
	
	/**
	 * 用户头像
	 */
	private String vcIcon;
	
	
	
	public String getVcIcon() {
		return vcIcon;
	}

	public void setVcIcon(String vcIcon) {
		this.vcIcon = vcIcon;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcLoginName(java.lang.String value) {
		this.vcLoginName = value;
	}
	
	public String getVcFastPayPassword() {
		return vcFastPayPassword;
	}

	public void setVcFastPayPassword(String vcFastPayPassword) {
		this.vcFastPayPassword = vcFastPayPassword;
	}

	public java.lang.Integer getiFastPayState() {
		return iFastPayState;
	}

	public void setiFastPayState(java.lang.Integer iFastPayState) {
		this.iFastPayState = iFastPayState;
	}

	public java.lang.Integer getiFastPayErrorTimes() {
		return iFastPayErrorTimes;
	}

	public void setiFastPayErrorTimes(java.lang.Integer iFastPayErrorTimes) {
		this.iFastPayErrorTimes = iFastPayErrorTimes;
	}

	public Date getDtFastLastPayErrorTime() {
		return dtFastLastPayErrorTime;
	}

	public void setDtFastLastPayErrorTime(Date dtFastLastPayErrorTime) {
		this.dtFastLastPayErrorTime = dtFastLastPayErrorTime;
	}

	public Date getDtUpFastPayTime() {
		return dtUpFastPayTime;
	}

	public void setDtUpFastPayTime(Date dtUpFastPayTime) {
		this.dtUpFastPayTime = dtUpFastPayTime;
	}

	public java.lang.String getVcLoginName() {
		return this.vcLoginName;
	}
	
	public void setVcPhone(java.lang.String value) {
		this.vcPhone = value;
		this.vcPhone_secret = value.substring(0, 3)+"****"+value.substring(7);
	}
	
	public java.lang.String getVcPhone() {
		return this.vcPhone;
	}
	
	public java.lang.String getVcPhone_secret() {
		return vcPhone_secret;
	}

	public void setVcEmail(java.lang.String value) {
		this.vcEmail = value;
	}
	
	public java.lang.String getVcEmail() {
		return this.vcEmail;
	}
	
	public void setVcPassword(java.lang.String value) {
		this.vcPassword = value;
	}
	
	public java.lang.String getVcPassword() {
		return this.vcPassword;
	}
	
	public void setIlevel(java.lang.Integer value) {
		this.ilevel = value;
	}
	
	public java.lang.Integer getIlevel() {
		return this.ilevel;
	}
	
	public void setVcPaymentcipher(java.lang.String value) {
		this.vcPaymentcipher = value;
	}
	
	public java.lang.String getVcPaymentcipher() {
		return this.vcPaymentcipher;
	}
	
	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}
	
	public java.lang.Integer getItype() {
		return this.itype;
	}
	
	public void setIoriginal(java.lang.Integer value) {
		this.ioriginal = value;
	}
	
	public java.lang.Integer getIoriginal() {
		return this.ioriginal;
	}
	
	public void setIverifyPhone(java.lang.Integer value) {
		this.iverifyPhone = value;
	}
	
	public java.lang.Integer getIverifyPhone() {
		return this.iverifyPhone;
	}
	
	public void setIverifyEmail(java.lang.Integer value) {
		this.iverifyEmail = value;
	}
	
	public java.lang.Integer getIverifyEmail() {
		return this.iverifyEmail;
	}
	
	public void setIverifyIdcard(java.lang.Integer value) {
		this.iverifyIdcard = value;
		if(value==1){
			this.iverifyIdcardStr = "已认证";
		}else if(value==2){
			this.iverifyIdcardStr = "未认证";
		}
	}
	
	public java.lang.Integer getIverifyIdcard() {
		return this.iverifyIdcard;
	}
	
	public java.lang.String getIverifyIdcardStr() {
		return iverifyIdcardStr;
	}

	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}
	
	public java.lang.String getVcName() {
		return this.vcName;
	}
	
	public void setIcardType(java.lang.Integer value) {
		this.icardType = value;
	}
	
	public java.lang.Integer getIcardType() {
		return this.icardType;
	}
	
	public void setVcCardCode(java.lang.String value) {
		this.vcCardCode = value;
	}
	
	public java.lang.String getVcCardCode() {
		return this.vcCardCode;
	}
	
	public void setVcSource(java.lang.String value) {
		this.vcSource = value;
	}
	
	public java.lang.String getVcSource() {
		return this.vcSource;
	}
	
	public void setDtFirstInvest(java.util.Date value) {
		this.dtFirstInvestStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtFirstInvest = value;
	}
	
	public java.util.Date getDtFirstInvest() {
		return this.dtFirstInvest;
	}
	
	public java.lang.String getDtFirstInvestStr() {
		return this.dtFirstInvestStr;
	}
	
	public void setIvalid(java.lang.Integer value) {
		this.ivalid = value;
		if(value==1){
			this.ivalidStr = "正常";
		}else if(value==2){
			this.ivalidStr = "冻结";
		}
	}
	
	public java.lang.Integer getIvalid() {
		return this.ivalid;
	}
	
	public java.lang.String getIvalidStr() {
		return ivalidStr;
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
	
	public void setVcRegisterDevice(java.lang.String value) {
		this.vcRegisterDevice = value;
		if(value.equals("pc")){
			this.vcRegisterDeviceStr = "PC";
		}else if(value.equals("wap")){
			this.vcRegisterDeviceStr = "WAP";
		}else if(value.equals("ios")){
			this.vcRegisterDeviceStr = "IOS";
		}else if(value.equals("android")){
			this.vcRegisterDeviceStr = "安卓";
		}
	}
	
	public java.lang.String getVcRegisterDevice() {
		return this.vcRegisterDevice;
	}
	
	public java.lang.String getVcRegisterDeviceStr() {
		return vcRegisterDeviceStr;
	}

	public void setVcDeviceId(java.lang.String value) {
		this.vcDeviceId = value;
	}
	
	public java.lang.String getVcDeviceId() {
		return this.vcDeviceId;
	}

	public java.lang.String getVcRecommendCode() {
		return vcRecommendCode;
	}

	public void setVcRecommendCode(java.lang.String vcRecommendCode) {
		this.vcRecommendCode = vcRecommendCode;
	}

	public java.util.Date getDtLastLogin() {
		return dtLastLogin;
	}

	public void setDtLastLogin(java.util.Date dtLastLogin) {
		this.dtLastLoginStr = DateUtil.getDate(dtLastLogin, "yyyy-MM-dd HH:mm:ss");
		this.dtLastLogin = dtLastLogin;
	}

	public java.lang.String getDtLastLoginStr() {
		return dtLastLoginStr;
	}
	
	public void setIpaymentcipherState(java.lang.Integer value) {
		this.ipaymentcipherState = value;
	}
	
	public java.lang.Integer getIpaymentcipherState() {
		return this.ipaymentcipherState;
	}
	
	public void setNumPayErrorTimes(java.lang.Integer value) {
		this.numPayErrorTimes = value;
	}
	
	public java.lang.Integer getNumPayErrorTimes() {
		return this.numPayErrorTimes;
	}
	
	public void setDtLastPayError(java.util.Date value) {
		this.dtLastPayErrorStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtLastPayError = value;
	}
	
	public java.util.Date getDtLastPayError() {
		return this.dtLastPayError;
	}
	
	public java.lang.String getDtLastPayErrorStr() {
		return this.dtLastPayErrorStr;
	}

	public java.lang.Integer getNumInvestTimes() {
		return numInvestTimes;
	}

	public void setNumInvestTimes(java.lang.Integer numInvestTimes) {
		this.numInvestTimes = numInvestTimes;
	}

	public Date getDtLastSale() {
		return dtLastSale;
	}

	public void setDtLastSale(Date dtLastSale) {
		this.dtLastSaleStr =DateUtil.getDate(dtLastSale, "yyyy-MM-dd HH:mm:ss");
		this.dtLastSale = dtLastSale;
	}

	public String getDtLastSaleStr() {
		return dtLastSaleStr;
	}


	
	
}

