package com.tzg.common.service.kff;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.rest.RestConstants;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.SmsSendRmiService;

@Service(value="AliyunMsgService")
@Transactional
public class AliyunMsgService {
	protected static Logger logger = Logger.getLogger(AliyunMsgService.class);

	@Autowired
	private RedisService redisService;	
	@Autowired
	private SmsSendRmiService smsSendRmiService;	
	@Autowired
	private KFFRmiService kffRmiService;	
	
	public boolean sendAliyun(String phone, String module) throws Exception{
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
		if (StringUtils.isBlank(this.redisService.get(smsStormCheckKey))) {
			smsSendRmiService.sendMSG(phone, dynamicValidateCode, module);
		}

		this.redisService.put(smsStormCheckKey, "1", 50); // 有效期50秒防止短信轰炸
		logger.info("dynamicValidateCode:rest:" + module + ":" + dynamicValidateCode);
		kffRmiService.aLiYunSmsApi(phone, module, dynamicValidateCode, cacheKey, smsStormCheckKey);
		/*if(sendType==1){//1代表是线上
			kffRmiService.aLiYunSmsApi(phone, module, dynamicValidateCode, cacheKey, smsStormCheckKey);
		}else {
			redisService.put(cacheKey, dynamicValidateCode, 60 * 10);// 设置10分钟
		}*/
		return true;
	}
	
}
