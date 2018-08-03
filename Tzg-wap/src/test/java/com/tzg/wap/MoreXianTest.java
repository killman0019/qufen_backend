package com.tzg.wap;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tzg.common.utils.sysGlobals;
import com.tzg.wap.BaseTest.ExcutDemo;

/**
 * 
 * ClassName: MoreXianTest  
 * Function: TODO 
 * date: 2018年8月3日 下午5:18:13  
 * 
 * @author zhangdd 
 * @version  
 * @since JDK 1.7 
 *
 */
@SuppressWarnings("unused")
public class MoreXianTest {

	private static final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);

	private static final ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();

	public static void text() {
		final CountDownLatch countDownLatch = new CountDownLatch(1);

		try {
			for (int i = 0; i < 30000; i++) {
				final int j = i;
				newFixedThreadPool.execute(new Runnable() {

					@Override
					public void run() {
						// TODO 进行多线程的运行程序
						try {
							prinl(countDownLatch, j);
							Thread.sleep(2000);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.err.println(e.getMessage());
						}
					}
				});

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			newFixedThreadPool.shutdown();
			// countDownLatch.countDown();

		}
	}

	public static void text1() {
		final CountDownLatch countDownLatch = new CountDownLatch(3);

		try {
			for (int i = 0; i < 10; i++) {
				final int j = i;
				newFixedThreadPool.execute(new Runnable() {

					@Override
					public void run() {
						// TODO 进行多线程的运行程序
						try {
							prinl2(countDownLatch, j);
							Thread.sleep(2000);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							System.err.println(e.getMessage());
						}
					}
				});

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// newFixedThreadPool.shutdown();
			countDownLatch.countDown();

		}
	}

	/**
	 * 
	 * TODO 
	 * @param countDownLatch
	 * @param i
	 * @throws Exception
	 * @author zhangdd
	 * @data 2018年8月3日
	 *
	 */
	protected static void prinl(CountDownLatch countDownLatch, int i) throws Exception {
		// TODO 进行输出打印
		System.err.println("打印");
		System.err.println(i);
		i = i + 1;

		countDownLatch.countDown();

	}

	/**
	 * 
	 * TODO 
	 * @param countDownLatch
	 * @param i
	 * @throws Exception
	 * @author Administrator
	 * @data 2018年8月3日
	 *
	 */
	protected static void prinl2(CountDownLatch countDownLatch, int i) throws Exception {

		System.err.println("打印2");
		System.err.println(i);
		i = i + 1;

		countDownLatch.countDown();

	}

	/**
	 * 
	 * TODO
	 * @param args
	 * @author zhangdd
	 * @data 2018年8月3日
	 *
	 */
	public static void main(String[] args) {
		text();
		// text1();
	}
}
