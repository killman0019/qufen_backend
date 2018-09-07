package com.tzg.wap;

import com.tzg.common.utils.DateUtil;

public class TestMain {

	public static void main(String[] args) {

		// 100--------300
		// 20---60
		// 0----100//0----20
		String postCreateBegin = DateUtil.getSpecifiedDayBeforeOrAfter(1);
		System.err.println(postCreateBegin);
	}
}
