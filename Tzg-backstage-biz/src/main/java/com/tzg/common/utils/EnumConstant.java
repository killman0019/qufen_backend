package com.tzg.common.utils;


public class EnumConstant{
	
	/**
	 * 短信bus 和 短信模版类型 对接
	 *
	 */
	public static enum SmsBuss{
		空("null",null),
		注册效验码("register",1),
		提现效验码("cash",3),
		修改手机效验码("phoneNumberChange",30),
		新手机效验码("newPhoneNumber",31),
        忘记交易密码效验码("forgetPayPassword",35),
        忘记密码效验码("forgetPassword",5),
        还款审核效验码("repayAudit",36),
        补还审核效验码("makeupAudit",38),
        提现单审核效验码("cashAudit",37),
        项目审核效验码("projectAudit",43),
        标的审核效验码("subjectAudit",42),
        自动标的审核效验码("subjectAuditOpen",74),
        运营部自动标的审核效验码("subjectAuditOpenYyb",75),
        体验标的审核效验码("experiencesubjectAudit",59),
        退款审核效验码("cashFailureAudit",44),
        用户冻结解冻审核效验码("loginAccountAudit",46),
        投资人手机号码更换审核效验码("changePhoneAudit",49),
        投资人身份认证审核效验码("identityAudit",50),
        投资人提现金额转移审核效验码("cashTransferAudit",51),
        投资人绑卡删除审核效验码("cardDeleteAudit",52),
        平台充值操作审批单校验码("platformRechargeAudit",61),
        平台提现操作审批单校验码("platformCashAudit",62),
        备用金充值操作审批单校验码("reserveRechargeAudit",63),
        备用金提现操作审批单校验码("reserveCashAudit",64),
        投资公司充值操作审批单校验码("investcompanyRechargeAudit",68),
        投资公司提现操作审批单校验码("investcompanyWithdrawalsAudit",69),
        定期宝的审核效验码("fixedbaosubjectAudit",123),
        活期宝的审核效验码("currentbaosubjectAudit",223)
        ;

    	private String bus;
    	private Integer smstemplateType;
	    	
	    SmsBuss(String bus,Integer smstemplateType){
	    	this.bus = bus;
	    	this.smstemplateType = smstemplateType;
	    }
		public String getBus() {
			return bus;
		}
		public Integer getSmstemplateType() {
			return smstemplateType;
		}
		
		public static SmsBuss getSmsBuss(String bus) {
			if(bus.equals("register")){
				return SmsBuss.注册效验码;
			}else if(bus.equals("cash")){
				return SmsBuss.提现效验码;
			}else if(bus.equals("phoneNumberChange")){
				return SmsBuss.修改手机效验码;
			}else if(bus.equals("newPhoneNumber")){
				return SmsBuss.新手机效验码;
			}else if(bus.equals("forgetPayPassword")){
				return SmsBuss.忘记交易密码效验码;
			}else if(bus.equals("forgetPassword")){
				return SmsBuss.忘记密码效验码;
			}else if(bus.equals("repayAudit")){
				return SmsBuss.还款审核效验码;
			}else if(bus.equals("makeupAudit")){
				return SmsBuss.补还审核效验码;
			}else if(bus.equals("cashAudit")){
				return SmsBuss.提现单审核效验码;
			}else if(bus.equals("projectAudit")){
				return SmsBuss.项目审核效验码;
			}else if(bus.equals("subjectAudit")){
				return SmsBuss.标的审核效验码;
			}else if(bus.equals("subjectAuditOpen")){
			    return SmsBuss.自动标的审核效验码;
			}else if(bus.equals("subjectAuditOpenYyb")){
                return SmsBuss.运营部自动标的审核效验码;
            }else if(bus.equals("experiencesubjectAudit")){
				return SmsBuss.体验标的审核效验码;
			}else if(bus.equals("cashFailureAudit")){
				return SmsBuss.退款审核效验码;
			}else if(bus.equals("loginAccountAudit")){
				return SmsBuss.用户冻结解冻审核效验码;
			}else if(bus.equals("changePhoneAudit")){
				return SmsBuss.投资人手机号码更换审核效验码;
			}else if(bus.equals("identityAudit")){
				return SmsBuss.投资人身份认证审核效验码;
			}else if(bus.equals("cashTransferAudit")){
				return SmsBuss.投资人提现金额转移审核效验码;
			}else if(bus.equals("cardDeleteAudit")){
				return SmsBuss.投资人绑卡删除审核效验码;
			}else if(bus.equals("platformRechargeAudit")){
				return SmsBuss.平台充值操作审批单校验码;
			}else if(bus.equals("platformCashAudit")){
				return SmsBuss.平台提现操作审批单校验码;
			}else if(bus.equals("reserveRechargeAudit")){
				return SmsBuss.备用金充值操作审批单校验码;
			}else if(bus.equals("reserveCashAudit")){
				return SmsBuss.备用金提现操作审批单校验码;
			}else if(bus.equals("investcompanyRechargeAudit")){
				return SmsBuss.投资公司充值操作审批单校验码;
			}else if(bus.equals("investcompanyWithdrawalsAudit")){
				return SmsBuss.投资公司提现操作审批单校验码;
			}else if(bus.equals("fixedbaosubjectAudit")){
				return SmsBuss.定期宝的审核效验码;
			}else if(bus.equals("currentbaosubjectAudit")){
				return SmsBuss.活期宝的审核效验码;
			}
			else{
				return SmsBuss.空;
			}
		}
    }
	
	
	
}
