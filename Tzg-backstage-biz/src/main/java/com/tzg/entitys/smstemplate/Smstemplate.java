package com.tzg.entitys.smstemplate;

import java.io.Serializable;

import com.tzg.common.utils.DateUtil;

public class Smstemplate implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 1 -- 注册验证码短信；             2 -- 充值成功短信；             3 -- 提现验证码短信；             4 -- 提现成功短信；             5 -- 找回密码验证码短信；             6 -- 自动投资成功短信；             7 -- 投资成功短信；             8 -- 投资人回款成功短信；             9 -- 项目提交最终审核人短信；             10 -- 项目最终审核人审核结果短信；             11 -- 标的提交最终审核人短信；             12 -- 标的最终审核人审核结果短信；             13 -- 提前三天催借款人短信；             14 -- 提前两天催借款人短信；             15 -- 提前一天上午催借款人短信；             16 -- 提前一天下午催借款人短信；             17 -- 借款逾期发给借款人短信；             18 -- 提前一天上午发给风控短信；             19 -- 借款逾期发给风控短信             20 -- 提前两天上午发给担保方短信；             21 -- 提前一天上午发给担保方短信；             22 -- 还款时间当天上午发给担保方短信；             23 -- 还款时间当天下午发给担保方短信；             24 -- 担保方偿还成功短信；             25 -- 还款时间当天上午发给公司内部人员短信；             26 -- 募集满分账短信；             27 -- 还款成功短信；             28 -- 后台登陆验证短信。
     */ 	
	private java.lang.Integer itype;
    /**
     * 多个手机用 英文";"分隔             如：13000000008;1788888888;188777666
     */ 	
	private java.lang.String vcPhones;
    /**
     * vcTemplate
     */ 	
	private java.lang.String vcTemplate;
    /**
     * 1 -- 有效；             2 -- 无效。
     */ 	
	private java.lang.Integer istate;
    /**
     * dtModify
     */ 	
	private java.util.Date dtModify;
	private java.lang.String dtModifyStr;

	
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
	
	public void setVcPhones(java.lang.String value) {
		this.vcPhones = value;
	}
	
	public java.lang.String getVcPhones() {
		return this.vcPhones;
	}
	
	public void setVcTemplate(java.lang.String value) {
		this.vcTemplate = value;
	}
	
	public java.lang.String getVcTemplate() {
		return this.vcTemplate;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
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

	
}

