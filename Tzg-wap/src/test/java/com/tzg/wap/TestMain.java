package com.tzg.wap;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class TestMain {
	private static int i = 0;
	static {

		System.err.println("代码块启动!");
		i = 3;

	}

	public static void sys() {

		System.err.println(i);
	}

	public static void main(String[] args) {
		sys();
	}
}
