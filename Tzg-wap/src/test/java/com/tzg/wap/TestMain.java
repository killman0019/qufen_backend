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

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 获取前月的第一天
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		long currentBegin = cal_1.getTime().getTime();
		long zeroBegin = currentBegin / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
		long twelveBegin = zeroBegin + 24 * 60 * 60 * 1000 - 1;// 今天23点59分59秒的毫秒数
		Date createTimeBegin = new Date(zeroBegin);
		firstDay = format.format(createTimeBegin);
		System.out.println("-----1------firstDay:" + firstDay);

		// 获取前月的最后一天
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月最后一天
		long currentEnd = cale.getTime().getTime();
		long zeroEnd = currentEnd / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
		long twelveEnd = zeroEnd + 24 * 60 * 60 * 1000 - 1;// 今天23点59分59秒的毫秒数
		Date createTimeEnd = new Date(twelveEnd);
		lastDay = format.format(createTimeEnd);
		System.out.println("-----2------lastDay:" + lastDay);

	}

}
