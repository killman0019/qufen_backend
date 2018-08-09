package com.tzg.wap;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class TestMain {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 20; i++) {
			list.add(i);
		}
		list.add(1, 33);
		System.err.println(JSON.toJSONString(list));
	}
}
