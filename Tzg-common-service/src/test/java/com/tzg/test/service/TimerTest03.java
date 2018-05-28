package com.tzg.test.service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest03 {
	Timer timer;

	public TimerTest03() {
		timer = new Timer();
		timer.schedule(new TimerTaskTest03(), 1000, 3000);
	}

	public static void main(String[] args) {
		new TimerTest03();
	}
}

 class TimerTaskTest03 extends TimerTask {

	@Override
	public void run() {
		Date date = new Date(this.scheduledExecutionTime());
		System.out.println("本次执行该线程的时间为：" + date);
	}
}