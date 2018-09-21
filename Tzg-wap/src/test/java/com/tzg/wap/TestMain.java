package com.tzg.wap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.tzg.common.utils.DateUtil;

public class TestMain {

	public static void main(String[] args) throws ParseException {
		System.err.println(new Date().getTime());
		ScheduledExecutorService executorService = null;

		executorService = Executors.newScheduledThreadPool(1);
		executorService.schedule(new Runnable() {

			@Override
			public void run() {
				System.err.println("aaaaa");

			}
		}, 3, TimeUnit.SECONDS);
		System.err.println(new Date().getTime());
	}
}
