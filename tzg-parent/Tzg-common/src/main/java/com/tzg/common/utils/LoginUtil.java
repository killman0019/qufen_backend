package com.tzg.common.utils;

import org.apache.log4j.Logger;

import com.tzg.common.bbs.util.Base64;
import com.tzg.common.utils.Assert;
import com.tzg.common.utils.MD5Util;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.TzgConstant;

public class LoginUtil {
	private static Logger logger = Logger.getLogger(LoginUtil.class);
	
	public static String decodeAppLoginKey(String key) {
		String iloginId=null;
		try {
			Assert.isBlank(key, "登录帐号为空");
			String[] str=Base64.base64_decode(key).split(":");
			Assert.isBlank(str[0], "登录帐号为空");
			Assert.isBlank(str[1], "登录时间为空");
			Assert.isBlank(str[2], "key为空");
			String newkey=MD5Util.md5Hex(str[0]+str[1]+TzgConstant.LOGIN_SIGN_KEY);
			if(str[2].equals(newkey)){
				iloginId=str[0];
			}
		} catch (Exception e) {
			logger.info("app-active-登陆解码失败"+key+" Eception:"+e);
		}
		
		return iloginId;
	}
	
	public static String decodeOtherLoginKey(String key) throws Exception{
		logger.info("other-active-登陆解码开始 key="+key);
		String iloginId=null;
		Assert.isBlank(key, "登录帐号为空");
		String str[] = key.split("-");
		String sign = SHAUtil.encode(str[0]+ TzgConstant.USERID_SIGN);
		if(sign.equals(str[1])){
			return str[0];
		}
		logger.info("other-active-登陆解码结束 key="+key+";iloginId="+iloginId);
		return iloginId;
	}
	
}
