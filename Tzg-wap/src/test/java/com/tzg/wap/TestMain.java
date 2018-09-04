package com.tzg.wap;

import com.tzg.common.utils.RandomUtil;

public class TestMain {

	public static void main(String[] args) {

		// 100--------300
		// 20---60
		// 0----100//0----20
		for (int j = 0; j <= 1000; j++) {
			int i = RandomUtil.randomNumber(0, 20) * 5;
			System.err.println(i);
		}
	}
}
