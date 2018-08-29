package com.tzg.test.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzg.common.utils.AesWapUtils;
import com.tzg.common.utils.HttpUtil;
import com.tzg.common.utils.SyseUtil;

public class MainTest {
	private static final String KEY = "abcdefgabcdefg12";

	public static void main(String[] args) {
		try {
			String phone = "15852634454";
			String password = "1qaz2wsx";
			String passwordAes = AesWapUtils.aesEncrypt(password, KEY);

			String para = "phoneNumber=" + phone + "&password=" + passwordAes + "&dynamicVerifyCode=1qa2wszx&phoneCode=true&checkCode=true&isRobot=true";
			String str = "http://192.168.10.153:803/kff/user/registerInva?" + para;

			String doGet = HttpUtil.doGet(str);
			// SyseUtil.systemErrOutJson(doGet);
			JSONObject parseObject = JSON.parseObject(doGet);
			// SyseUtil.systemErrOutJson(parseObject);
			Object object = parseObject.get("data");
			SyseUtil.systemErrOutJson(object);
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
