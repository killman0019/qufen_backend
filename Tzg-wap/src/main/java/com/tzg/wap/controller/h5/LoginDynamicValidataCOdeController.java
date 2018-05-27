package com.tzg.wap.controller.h5;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.EnumConstant.SmsBuss;
import com.tzg.common.utils.rest.RestConstants;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.SmsSendRmiService;

@Controller(value = "LoginDynamicValidata")
@RequestMapping("/kff/LoginDynamicValidata")
public class LoginDynamicValidataCOdeController extends BaseController {
	protected static Logger logger = Logger.getLogger(LoginDynamicValidataCOdeController.class);
	@Autowired
	private SmsSendRmiService smsSendRmiService;
	@Autowired
	private static RedisService redisService;
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
	@RequestMapping(value = "/send", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity sendencrypt(HttpServletRequest request, HttpServletResponse response, String phone) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> data = new HashMap<String, Object>();
		String module = "register";
		try {

			if (StringUtils.isBlank(module) || StringUtils.isBlank(phone)) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}

			String phonefmt = RegexUtil.PHONEREGEX;
			if (!phone.matches(phonefmt)) {
				throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
			}

			if (module.equals(SmsBuss.新手机效验码.getBus()) || module.equals(SmsBuss.注册效验码.getBus())) {
				if (kffRmiService.verifyLoginaccount("mobile", phone)) {
					throw new RestServiceException(RestErrorCode.PHONE_ALREADY_EXIST);
				}
			}
			String cacheKey = new StringBuffer(RestConstants.key_rest).append(module).append(phone).toString();
			String smsStormCheckKey = cacheKey + "sms";
			String cacheCode = redisService.get(cacheKey);
			String dynamicValidateCode = cacheCode;
			if (StringUtils.isBlank(cacheCode)) {
				dynamicValidateCode = RandomUtil.produceNumber(6) + "";

			}
			logger.info("dynamicValidateCode:rest:" + module + ":" + dynamicValidateCode);

			this.redisService.put(cacheKey, dynamicValidateCode, 60 * 10);// 有效期10min
			logger.info("cacheKey:" + cacheKey);

			// 防止短信轰炸
			if (StringUtils.isBlank(this.redisService.get(smsStormCheckKey))) {
				smsSendRmiService.sendMSG(phone, dynamicValidateCode, module);
			}

			this.redisService.put(smsStormCheckKey, "1", 50); // 有效期50秒防止短信轰炸

			data.put("dynamicCode", dynamicValidateCode);
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

	public BaseResponseEntity verify(String phone, String code) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> data = new HashMap<String, Object>();

		String module = "register";

		if (StringUtils.isBlank(module) || StringUtils.isBlank(phone) || StringUtils.isBlank(code)) {
			throw new RestServiceException(RestErrorCode.MISSING_ARGS);
		}

		String cacheKey = new StringBuffer(RestConstants.key_rest).append(module).append(phone).toString();
		String cacheCode;
		try {
			cacheCode = redisService.get(cacheKey);

			if (!StringUtils.isEmpty(cacheCode)) {
				if (!(cacheCode).equals(code)) {
					throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_ERROR);
				}
			} else {
				throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_EXPIRED);
			}
		} catch (Exception e) {
			logger.error("DynamicValidateCodeController send:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}

		return null;

	}
}
