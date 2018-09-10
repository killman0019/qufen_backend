package com.tzg.wap;

import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.SyseUtil;

public class TestMain {

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			Integer count = RandomUtil.randomNumber(0, 2);
			SyseUtil.systemErrOutJson(count);
		}

		int hour = DateUtil.getHour();
		System.err.println(hour);
	}
}
