package com.tzg.wap.utils;

public class HexUtil {
	/**********************************************/
	/*
	 * int到Integer:
	 * 
	 * int a=3; Integer A=new Integer(a);
	 * 
	 * 或: Integer A=Integer.valueOf(a);
	 * 
	 * Integer到int:
	 * 
	 * Integer A=new Integer(5);
	 * 
	 * int a=A.intValue()
	 * 
	 * 
	 * 
	 * String hex = "fff"; Integer x = Integer.parseInt(hex,16);
	 * System.out.println(x);
	 */
	/*******************************************/
	/**
	 * 10进制转16进制 userid 转化成 2维码
	 * 
	 * @param userId
	 * @return 二维码
	 */
	public static String userIdTo2code(Integer userId) {
		int userIdInt = userId.intValue();
		int i = 95279527;
		userIdInt = userIdInt + i;
		return userId.toHexString(userIdInt);
	}

	public static void main(String[] args) {
		System.out.println(userIdTo2code(159357));// 1 ******5add9a8
		System.out.println(code2ToUserId("5b04824"));// 5add9a8 ******1
	}

	/**
	 * 16进制转10进制 2维码转化成userId
	 * 
	 * @param userId
	 * @return 二维码
	 */
	public static Integer code2ToUserId(String code2) {
		Integer x = Integer.parseInt(code2, 16);
		int intValue = x.intValue();
		int i = 95279527;
		intValue = intValue - i;
		return intValue;
	}

}
