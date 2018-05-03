package com.tzg.common.log;

import org.apache.log4j.Logger;

/**
 * 日志分类
 * @author wuws
 *
 */
public class LogFactory {
	
    private static Logger errorLog = Logger.getLogger("ErrorLogger");
    private static Logger infoLog = Logger.getLogger("InfoLogger");
    private static Logger debugLog = Logger.getLogger("DebugLog");
    private static Logger warrnLog = Logger.getLogger("WarrnLog");    
       
	public static void warn(Object message){
		warrnLog.warn(message);
	}
	
	public static void warn(Object message,Throwable t){
		warrnLog.warn(message,t);
	}      
 
	public static void debug(Object message){
		debugLog.debug(message);
	}
	
	public static void debug(Object message,Throwable t){
		debugLog.debug(message,t);
	}    
	
	public static void info(Object message){
		infoLog.info(message);
	}
	
	public static void info(Object message,Throwable t){
		infoLog.info(message,t);
	}
	
	public static void error(Object message){
		errorLog.error(message);
	}
	
	public static void error(Object message,Throwable t){
		errorLog.error(message,t);
	}	
    
}
