package com.tzg.common.utils;

/**
 * 
 *全局的常量 写在这里
 *
 */
public final class sysGlobals{
	/**状态启用-0*/
	public static final Integer ENABLE = 0;
	/**状态禁用-1*/
	public static final Integer DISABLE = 1;
	/**判断正式环境才发送短信*/
	public static final String DEV_ENVIRONMENT="FORMAL";
	
	
	/**推送给全部用户默认一次取多少消息*/
	public static Integer DEFAULT_MSG_COUNT=1000;
	/**启动的线程数*/
	public static Integer START_THREAD_COUNT=10;
	/**每次往个推那边推送的个数*/
	public static Integer SEND_GETUI_COUNT=100;
	/**个推appId*/
	public static String appId = "bbhc5OhXzz7HtrqZ2coeN1";
	/**个推appKey*/
	public static String appKey = "FTfDlpO5ea5WaRk2LldqW5";
	/**个推masterSecret*/
	public static String masterSecret = "Aaio28TYk9ADMo54UOQtS5";
    /**个推请求地址*/
	public static String host = "http://sdk.open.api.igexin.com/apiex.htm";
	/**区分的网络图标*/
	public static String QF_IMG_URL = "https://pic.qufen.top/projects1531359616526360779?imageView2/0/format/png";
	/**个推定时Code*/
	public static Integer GETUI_SET_TIME=1;
	/**个推通知标题*/
	public static String GETUI_NOTIFY="区分";
}