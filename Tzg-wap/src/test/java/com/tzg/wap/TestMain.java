package com.tzg.wap;

import java.util.Timer;
import java.util.TimerTask;

public class TestMain {

	public static void main(String[] args) {
		Timer timer = new Timer();// 实例化Timer类
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("退出");
				this.cancel();
			}
		}, 5000 * 60 * 5);// 这里百毫秒
		System.out.println("本程序存在5秒后自动退出");
	}
}
