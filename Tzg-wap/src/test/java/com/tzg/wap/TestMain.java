package com.tzg.wap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import com.tzg.common.utils.DateUtil;

public class TestMain {
	private static String firstDay;
	private static String lastDay;

	public static void main(String[] args) throws ParseException {
		System.err.println(DateUtil.getFirstDayLastMonth());
		System.err.println(DateUtil.getLastDayLasyMonth());
	}
}
