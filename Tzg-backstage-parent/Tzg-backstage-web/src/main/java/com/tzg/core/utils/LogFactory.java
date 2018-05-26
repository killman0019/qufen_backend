package com.tzg.core.utils;

import com.tzg.common.log.LogConvert;
import org.apache.log4j.Logger;

/**
 * Created by cfour on 1/19/15.
 */
public class LogFactory {
	private static Logger log = Logger.getLogger("MessageLogger");
	private static Logger errorlog = Logger.getLogger("ErrorLogger");

	public static void logMessage(String message) {
		String loginName = "";
		if (HttpSessionUtil.getLoginSession() != null) {
			loginName = HttpSessionUtil.getLoginSession().getVcLoginName();
		}
		log.warn(loginName + message);
	}

	public static void logError(String message, Exception e) {
		String loginName = "";
		if (HttpSessionUtil.getLoginSession() != null) {
			loginName = HttpSessionUtil.getLoginSession().getVcLoginName();
		}
		errorlog.error(loginName + "--->" + message);
		errorlog.error(LogConvert.getErrorInfoFromException(e));
	}
}
