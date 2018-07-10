package com.tzg.wap.controller.h5;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.common.utils.EnumConstant.SmsBuss;
import com.tzg.common.utils.rest.RestConstants;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.SmsSendRmiService;

@Controller(value = "KFFDynamicValidateCodeController")
@RequestMapping("/kff/dynamicValidateCode")
public class DynamicValidateCodeController extends BaseController {
	protected static Logger logger = Logger.getLogger(DynamicValidateCodeController.class);
	@Autowired
	private SmsSendRmiService smsSendRmiService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private KFFRmiService kffRmiService;
	
	/**
	 * 发送手机验证码
	 * 
	 * @param request
	 * @param response
	 * @param phone
	 * @param module
	 * @return
	 */
//	@RequestMapping(value = "/send", method = { RequestMethod.POST, RequestMethod.GET })
//	@ResponseBody
//	public BaseResponseEntity sendencrypt(HttpServletRequest request, HttpServletResponse response, String phone, String module) {
//		BaseResponseEntity bre = new BaseResponseEntity();
//		Map<String, Object> data = new HashMap<String, Object>();
//		try {
//
//			if (StringUtils.isBlank(module) || StringUtils.isBlank(phone)) {
//				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
//			}
//
//			// logger.info("****app传来的 phone:" + phone );
//			// 如果 app中传来的电话中含有*号 就用 用户表中的真实电话号码 替换
//			// phone = this.checkAppPhoneNumber(request, phone);
//			// 统一用手机号白名单验证正则表达式
//			String phonefmt = RegexUtil.PHONEREGEX;
//			if (!phone.matches(phonefmt)) {
//				throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
//			}
//
//			if (module.equals(SmsBuss.新手机效验码.getBus()) || module.equals(SmsBuss.注册效验码.getBus())) {
//				if (kffRmiService.verifyLoginaccount("mobile", phone)) {
//					throw new RestServiceException(RestErrorCode.PHONE_ALREADY_EXIST);
//				}
//			}
//			String cacheKey = new StringBuffer(RestConstants.key_rest).append(module).append(phone).toString();
//			String smsStormCheckKey = cacheKey + "sms";
//			String cacheCode = this.redisService.get(cacheKey);
//			String dynamicValidateCode = cacheCode;
//			if (StringUtils.isBlank(cacheCode)) {
//				dynamicValidateCode = RandomUtil.produceNumber(6) + "";
//
//			}
//			logger.info("dynamicValidateCode:rest:" + module + ":" + dynamicValidateCode);
//
//			this.redisService.put(cacheKey, dynamicValidateCode, 60 * 1);// 有效期10min
//			logger.info("cacheKey:" + cacheKey);
//
//			// 防止短信轰炸
//			if (StringUtils.isBlank(this.redisService.get(smsStormCheckKey))) {
//				smsSendRmiService.sendMSG(phone, dynamicValidateCode, module);
//			}
//
//			this.redisService.put(smsStormCheckKey, "1", 50); // 有效期50秒防止短信轰炸
//
//			data.put("dynamicCode", dynamicValidateCode);
//			bre.setData(data);
//		} catch (RestServiceException e) {
//			logger.error("DynamicValidateCodeController send:{}", e);
//			return this.resResult(e.getErrorCode(), e.getMessage());
//		} catch (Exception e) {
//			logger.error("DynamicValidateCodeController send:{}", e);
//			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
//		}
//		return bre;
//	}

	/**
	 * 验证验证码是否正确
	 * 
	 * @param request
	 * @param response
	 * @param phone
	 * @param module
	 * @param code
	 * @return
	 */
//	@RequestMapping(value = "/verify", method = { RequestMethod.POST, RequestMethod.GET })
//	@ResponseBody
//	public BaseResponseEntity verify(HttpServletRequest request, HttpServletResponse response, String phone, String module, String code) {
//		BaseResponseEntity bre = new BaseResponseEntity();
//		try {
//			JSONObject map = new JSONObject();
//			map.put("phone", phone);
//			map.put("module", module);
//			map.put("code", code);
//			if (StringUtils.isBlank(module) || StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
//				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
//			}
//
//			String cacheKey = new StringBuffer(RestConstants.key_rest).append(module).append(phone).toString();
//			String cacheCode = this.redisService.get(cacheKey);
//			if (!StringUtils.isEmpty(cacheCode)) {
//				if (!(cacheCode).equalsIgnoreCase(code)) {
//					throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_ERROR);
//				}
//			} else {
//				throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_EXPIRED);
//			}
//
//			KFFUser user = null;
//			if (SmsBuss.登录校验码.getBus().equals(module)) {
//				if (kffRmiService.verifyLoginaccount("mobile", phone)) {
//					// 老用户登录
//					user = kffRmiService.findUserByPhoneNumber(phone);
//
//				} else {
//					// 新用户注册
//					RegisterRequest registerRequest = new RegisterRequest();
//					registerRequest.setPhoneNumber(phone);
//					user = kffRmiService.registerRest(registerRequest);
//
//				}
//				if (user != null) {
//					// 生成account token
//					String token = AccountTokenUtil.getAccountToken(user.getUserId());
//					map.put("user", user);
//					map.put("token", token);
//				}
//			}
//
//			// this.redisService.del(cacheKey);
//			bre.setData(map);
//		} catch (RestServiceException e) {
//			logger.error("DynamicValidateCodeController verify:{}", e);
//			return this.resResult(e.getErrorCode(), e.getMessage());
//		} catch (Exception e) {
//			logger.error("DynamicValidateCodeController verify:{}", e);
//			return this.resResult(RestErrorCode.SYS_ERROR);
//		}
//		return bre;
//	}

	/**
	 * 阿里云发送手机验证码
	 * 
	 * @param request
	 * @param response
	 * @param phone
	 * @param module
	 * @return
	 */
	@RequestMapping(value = "/sendAliyun", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity sendAliyun(HttpServletRequest request, HttpServletResponse response, String phone, String module) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> data = new HashMap<String, Object>();
		// String module = "register";
		try {

			if (StringUtils.isBlank(module) || StringUtils.isBlank(phone)) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}

			String phonefmt = RegexUtil.PHONEREGEX;
			if (!phone.matches(phonefmt)) {
				throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
			}
			/******************** 验证码业务逻辑begin *****************/
			/*if (module.equals(SmsBuss.新手机效验码.getBus()) || module.equals(SmsBuss.注册效验码.getBus())) {
				if (kffRmiService.verifyLoginaccount("mobile", phone)) {
					throw new RestServiceException(RestErrorCode.PHONE_ALREADY_EXIST);
				}
			}*/
			/******************** 验证码业务逻辑end *****************/
			String cacheKey = new StringBuffer(RestConstants.key_rest).append(module).append(phone).toString();
			String smsStormCheckKey = cacheKey + "sms";
			String cacheCode = redisService.get(cacheKey);
			String dynamicValidateCode = cacheCode;
			if (StringUtils.isBlank(cacheCode)) {
				dynamicValidateCode = RandomUtil.produceNumber(6) + "";

			}
			// 防止短信轰炸 阿里大鱼已经设置防止短信轰炸
			/*
			 * 
			 * 默认流控：短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时 ，累计10条/天
			 * */
			//读取配置文件中的DEV_ENVIRONMENT值为FORMAL，则去发短信
			if (StringUtils.isBlank(this.redisService.get(smsStormCheckKey))) {
				smsSendRmiService.sendMSG(phone, dynamicValidateCode, module);
			}
			this.redisService.put(smsStormCheckKey, "1", 50); // 有效期50秒防止短信轰炸
			logger.info("dynamicValidateCode:rest:" + module + ":" + dynamicValidateCode);
			kffRmiService.aLiYunSmsApi(phone, module, dynamicValidateCode, cacheKey, smsStormCheckKey);

			logger.info("cacheKey:" + cacheKey);

			//data.put("dynamicCode", dynamicValidateCode);
			bre.setData(data);
		} catch (RestServiceException e) {
			logger.error("DynamicValidateCodeController send:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("DynamicValidateCodeController send:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

}
