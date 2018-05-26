package com.tzg.common.utils;


/**
 * 校验工具类
 * 
 * @author yangpeiliang
 * @version 1.0
 */
public class FormatterUtil {

	/**
	 * 手机号码格式校验
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean checkPhoneNumber(String phoneNumber) {
		boolean isRest = true;
		String phonefmt = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$";
		if(!phoneNumber.matches(phonefmt)) {
			isRest = false;
		}
		return isRest;
	}
	
	/**
	 * 验证字符串是否连续
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isContinuous(String pwd) {
		String def = "0123456789";
		String def2 = "9876543210";
		if(def.indexOf(pwd) != -1 || def2.indexOf(pwd) != -1) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是否相同的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isSameChars(String str) {
		char first = str.charAt(0);
		for (int i = 1; i < str.length(); i++) {
			if (str.charAt(i) != first) {
				return false;
			}
		}
		return true;
	}
}
