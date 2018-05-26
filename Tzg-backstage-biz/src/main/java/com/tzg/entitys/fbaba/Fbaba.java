package com.tzg.entitys.fbaba;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class Fbaba implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 固定值： action
     */ 	
	private java.lang.String vcAction;
    /**
     * 固定值：78
     */ 	
	private java.lang.Integer iplanid;
    /**
     * 对应于铜掌柜投资记录中的合同编号
     */ 	
	private java.lang.String vcOrder;
    /**
     * 固定值： 1
     */ 	
	private java.lang.String vcGoodsmark;
    /**
     * 投资金额
     */ 	
	private BigDecimal vcGoodsprice;
    /**
     * 包括             标的名称，类型，投资周期              格式要求 ：名称 :XXXX ,类型 :xx 标,周期 :xx 月/天 (:,英文符号 )
     */ 	
	private java.lang.String vcGoodsname;
    /**
     * 数据签名（ 数据签名（ 32 位小写 MD5 加密） ，将订单号码和 ，将订单号码和 ，将订单号码和 Key 进行加密             Md5( order+key) 注意订单号和 Key 之间没 有任何字符(orderkey）                                       签名： tongzhanggui@111
     */ 	
	private java.lang.String vcSig;
    /**
     * 格式：首单 【1000 元:已付款】
     */ 	
	private java.lang.String vcStatus;
    /**
     * vcUid
     */ 	
	private java.lang.Integer vcUid;
    /**
     * 1 -- 未提交；             2 -- 提交成功；             3 -- 订单重复；             4 -- 签名错误；             5 -- 其它错误。
     */ 	
	private java.lang.Integer iresult;
    /**
     * dtInvest
     */ 	
	private java.util.Date dtInvest;
	private java.lang.String dtInvestStr;
    /**
     * dtCreate
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
    /**
     * 发送并且接收到富爸爸金融联盟系统返回结果的时间
     */ 	
	private java.util.Date dtSend;
	private java.lang.String dtSendStr;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setVcAction(java.lang.String value) {
		this.vcAction = value;
	}
	
	public java.lang.String getVcAction() {
		return this.vcAction;
	}
	
	public void setIplanid(java.lang.Integer value) {
		this.iplanid = value;
	}
	
	public java.lang.Integer getIplanid() {
		return this.iplanid;
	}
	
	public void setVcOrder(java.lang.String value) {
		this.vcOrder = value;
	}
	
	public java.lang.String getVcOrder() {
		return this.vcOrder;
	}
	
	public void setVcGoodsmark(java.lang.String value) {
		this.vcGoodsmark = value;
	}
	
	public java.lang.String getVcGoodsmark() {
		return this.vcGoodsmark;
	}
	
	public void setVcGoodsprice(BigDecimal value) {
		this.vcGoodsprice = value;
	}
	
	public BigDecimal getVcGoodsprice() {
		return this.vcGoodsprice;
	}
	
	public void setVcGoodsname(java.lang.String value) {
		this.vcGoodsname = value;
	}
	
	public java.lang.String getVcGoodsname() {
		return this.vcGoodsname;
	}
	
	public void setVcSig(java.lang.String value) {
		this.vcSig = value;
	}
	
	public java.lang.String getVcSig() {
		return this.vcSig;
	}
	
	public void setVcStatus(java.lang.String value) {
		this.vcStatus = value;
	}
	
	public java.lang.String getVcStatus() {
		return this.vcStatus;
	}
	
	public void setVcUid(java.lang.Integer value) {
		this.vcUid = value;
	}
	
	public java.lang.Integer getVcUid() {
		return this.vcUid;
	}
	
	public void setIresult(java.lang.Integer value) {
		this.iresult = value;
	}
	
	public java.lang.Integer getIresult() {
		return this.iresult;
	}
	
	public void setDtInvest(java.util.Date value) {
		this.dtInvestStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtInvest = value;
	}
	
	public java.util.Date getDtInvest() {
		return this.dtInvest;
	}
	
	public java.lang.String getDtInvestStr() {
		return this.dtInvestStr;
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
	
	public void setDtSend(java.util.Date value) {
		this.dtSendStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtSend = value;
	}
	
	public java.util.Date getDtSend() {
		return this.dtSend;
	}
	
	public java.lang.String getDtSendStr() {
		return this.dtSendStr;
	}

	
}

