package com.tzg.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.utils.Assert;
import com.tzg.common.utils.EnumConstant.SmsBuss;
import com.tzg.common.utils.RandomUtil;
import com.tzg.core.utils.HttpSessionUtil;


import com.tzg.entitys.leopard.console.ConsoleLoginAccount;

import com.tzg.service.consoleLoginAccount.ConsoleLoginAccountService;

import com.tzg.service.smssend.SmssendService;


@RequestMapping("/dynamicValidateCode")
@Controller
public class DynamicValidateCodeController extends BaseController {
	private static Logger log = Logger.getLogger(DynamicValidateCodeController.class);
	
	@Autowired
    private ConsoleLoginAccountService consoleLoginAccountService;
	@Autowired
	private SmssendService smssendService;

	/**
	 * 后台帐号动态码
	 * @param loginname
	 * @param bus
	 * @return
	 */
	@RequestMapping("/toConsoleAccount")
	@ResponseBody
	public Map<String, Object> toConsoleAccount(String loginname,@RequestParam(required=false)String bus){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.isBlank(loginname)){
				throw new Exception("用户名不能为空");
			}
			ConsoleLoginAccount consoleLoginAccount =  consoleLoginAccountService.getLoginAccount(loginname);
			if(consoleLoginAccount == null){
				throw new Exception("该用户不存在");
			}
			if(consoleLoginAccount.getiValid()!=1){
				throw new Exception("该用户已失效，暂时无法使用");
	     	}
			String dynamicValidateCode = RandomUtil.produceNumber(6)+"";
			System.out.println(dynamicValidateCode);
			HttpSessionUtil.setDynamicValidateCodeSession(consoleLoginAccount.getVcPhone(),dynamicValidateCode);
			this.sendMSG(consoleLoginAccount.getVcPhone(), dynamicValidateCode, bus);
			map.put(SUCCESS, true);
		} catch (Exception e) {
			map.put(SUCCESS, false);
			map.put(MESSAGE, e.getMessage());
		}
		
		return map;
	}
	
	
	
	
	@RequestMapping("/financialrepayAudit/verifyCode")
	@ResponseBody
	public Map<String, Object> verifyCode(String code){
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	Assert.notTrue(this.verifyDynamicValidateCode(SmsBuss.还款审核效验码.getBus(),code),"手机动态验证码错误");
        	map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error(e);
			map.put(SUCCESS, false);
			map.put(MESSAGE, e.getMessage());
		}
        return map;
	}
	

	
	@RequestMapping("/financialmakeupAudit/verifyCode")
	@ResponseBody
	public Map<String, Object> verifyCodeMakeup(String code){
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	Assert.notTrue(this.verifyDynamicValidateCode(SmsBuss.补还审核效验码.getBus(),code),"手机动态验证码错误");
        	map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error(e);
			map.put(SUCCESS, false);
			map.put(MESSAGE, e.getMessage());
		}
        return map;
	}


	
	@RequestMapping("/financialcashAudit/verifyCode")
	@ResponseBody
	public Map<String, Object> verifyCodeForCash(String code){
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	Assert.notTrue(this.verifyDynamicValidateCode(SmsBuss.提现单审核效验码.getBus(),code),"手机动态验证码错误");
        	map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error(e);
			map.put(SUCCESS, false);
			map.put(MESSAGE, e.getMessage());
		}
        return map;
	}
	//*/
	
	

		
		@RequestMapping("/financialcashfailureAudit/verifyCode")
		@ResponseBody
		public Map<String, Object> verifyCodeForCashFailure(String code){
			Map<String, Object> map = new HashMap<String, Object>();
	        try {
	        	Assert.notTrue(this.verifyDynamicValidateCode(SmsBuss.退款审核效验码.getBus(),code),"手机动态验证码错误");
	        	map.put(SUCCESS, true);
			} catch (Exception e) {
				log.error(e);
				map.put(SUCCESS, false);
				map.put(MESSAGE, e.getMessage());
			}
	        return map;
		}
		//*/
	
	
		
		@RequestMapping("/loginaccountaudit/verifyCode")
		@ResponseBody
		public Map<String, Object> verifyCodeForAccountLockAudit(String code){
			Map<String, Object> map = new HashMap<String, Object>();
	        try {
	        	Assert.notTrue(this.verifyDynamicValidateCode(SmsBuss.用户冻结解冻审核效验码.getBus(),code),"手机动态验证码错误");
	        	map.put(SUCCESS, true);
			} catch (Exception e) {
				log.error(e);
				map.put(SUCCESS, false);
				map.put(MESSAGE, e.getMessage());
			}
	        return map;
		}
		

		
		@RequestMapping("/changephoneaudit/verifyCode")
		@ResponseBody
		public Map<String, Object> verifyCodeForChangePhoneAudit(String code){
			Map<String, Object> map = new HashMap<String, Object>();
	        try {
	        	Assert.notTrue(this.verifyDynamicValidateCode(SmsBuss.投资人手机号码更换审核效验码.getBus(),code),"手机动态验证码错误");
	        	map.put(SUCCESS, true);
			} catch (Exception e) {
				log.error(e);
				map.put(SUCCESS, false);
				map.put(MESSAGE, e.getMessage());
			}
	        return map;
		}

		
		@RequestMapping("/identityaudit/verifyCode")
		@ResponseBody
		public Map<String, Object> verifyCodeForIdentityAudit(String code){
			Map<String, Object> map = new HashMap<String, Object>();
	        try {
	        	Assert.notTrue(this.verifyDynamicValidateCode(SmsBuss.投资人身份认证审核效验码.getBus(),code),"手机动态验证码错误");
	        	map.put(SUCCESS, true);
			} catch (Exception e) {
				log.error(e);
				map.put(SUCCESS, false);
				map.put(MESSAGE, e.getMessage());
			}
	        return map;
		}
		

		


		
		@RequestMapping("/carddeleteaudit/verifyCode")
		@ResponseBody
		public Map<String, Object> verifyCodeForCardDeleteAudit(String code){
			Map<String, Object> map = new HashMap<String, Object>();
	        try {
	        	Assert.notTrue(this.verifyDynamicValidateCode(SmsBuss.投资人绑卡删除审核效验码.getBus(),code),"手机动态验证码错误");
	        	map.put(SUCCESS, true);
			} catch (Exception e) {
				log.error(e);
				map.put(SUCCESS, false);
				map.put(MESSAGE, e.getMessage());
			}
	        return map;
		}

	
	@RequestMapping("/projectAudit/verifyCode")
	@ResponseBody
	public Map<String, Object> verifyCodeForProject(String code){
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	Assert.notTrue(this.verifyDynamicValidateCode(SmsBuss.项目审核效验码.getBus(),code),"手机动态验证码错误");
        	map.put(SUCCESS, true);
		} catch (Exception e) {
			log.error(e);
			map.put(SUCCESS, false);
			map.put(MESSAGE, e.getMessage());
		}
        return map;
	}
	
	

	
	
	private void sendMSG(String phoneNumber, String dynamicValidateCode, String bus) throws Exception{
		String vcContent = dynamicValidateCode;
		Integer itype = null;
		if(bus.equals("consoleLogin")){
			itype = 28;
		}
		String smsContent = smssendService.getSmsContent(itype, dynamicValidateCode);
		if(smsContent != null){
			vcContent = smsContent;
		}
		smssendService.save(phoneNumber,vcContent,2);
	}
}
