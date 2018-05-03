package com.tzg.rest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 获取当前年月（格式：yyyyMM）
	 * 
	 * @return
	 */
	public static String getCurrentYearMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Date date = new Date();
		String now = sdf.format(date);
		return now;
	}
	/**
	 * 获取当前年月（格式：yyyyMMhh）
	 * 
	 * @return
	 */
	public static String getCurrentYearMonthDay() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String now = sdf.format(date);
		return now;
	}

	/**
	 * 获取当前时间(格式：yyyyMMddHHmmss)
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String now = sdf.format(date);
		return now;
	}

	/**
	 * 比较两个日期时间的大小
	 * 
	 * @param t1
	 *            格式：yyyy-MM-dd HH:mm:ss
	 * @param t2
	 *            格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static Integer compareDateTime(String t1, String t2)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(sdf.parse(t1));
		c2.setTime(sdf.parse(t2));
		Integer result = c1.compareTo(c2);
		return result;
	}

	/**
	 * 比较两个日期时间的大小
	 * 
	 * @param t1
	 *            格式：yyyy-MM-dd HH:mm:ss
	 * @param t2
	 *            格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static Integer compareDateTime(String t1, String t2, String format)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(sdf.parse(t1));
		c2.setTime(sdf.parse(t2));
		Integer result = c1.compareTo(c2);
		return result;
	}

	// public static void main(String[] args){
	// String sbStr = "7.8.6.";
	// sbStr = sbStr.substring(0, sbStr.length()-1);
	// System.err.println(sbStr);
	//
	// }
	
	
	  /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));           
    }   

}
