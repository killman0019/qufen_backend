package com.tzg.wap;

import java.sql.Time;
import java.util.Random;

import com.tzg.common.utils.sysGlobals;

public class TestMain {
	private final static Random random2 = new Random();

	public static void main(String[] args) {
		Random random1 = new Random();

		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			System.err.println(random2.nextInt(100) + 1);
		}
		long end = System.currentTimeMillis();
		System.err.println(end - begin);
		System.err.println("++++++++++++++");
		long begin1 = System.currentTimeMillis();
		for (int i = 0; i < 1000; i++) {
			System.err.println(random2.nextInt(100) + 1);
		}
		long end1 = System.currentTimeMillis();
		System.err.println(end - begin);

		/*long begin1 = System.currentTimeMillis();
		for (int i = 0; i < 1010000000; i++) {
			int j = (int) (Math.random() * 100) + 1;
			// System.err.println(j);
		}
		long end1 = System.currentTimeMillis();
		System.err.println(end1 - begin1);*/

	}
}
