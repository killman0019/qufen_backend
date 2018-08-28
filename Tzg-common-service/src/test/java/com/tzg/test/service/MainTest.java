package com.tzg.test.service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import com.tzg.common.utils.HttpUtil;

public class MainTest {
	public static void test() throws KeyManagementException, NoSuchAlgorithmException {
		// AesWapUtils.
		String param = "phoneNumber=15856983323&password=1qaz2wsx&dynamicVerifyCode=1qa2wszx";
		String url = "http://192.168.10.153:803/kff/user/registerInva?" + param;
		HttpUtil.doGet(url);
	}
}
