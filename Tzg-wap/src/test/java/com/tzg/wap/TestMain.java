package com.tzg.wap;

import java.util.Timer;
import java.util.TimerTask;

import com.tzg.common.utils.DateUtil;

public class TestMain {
	public static void main(String[] args) {

		String postCreateBegin = DateUtil.getSpecifiedDayBeforeOrAfter(1);
		System.err.println(postCreateBegin);
	}
}
