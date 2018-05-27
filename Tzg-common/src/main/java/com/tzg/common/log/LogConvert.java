package com.tzg.common.log;

import org.apache.log4j.DailyRollingFileAppender;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogConvert extends DailyRollingFileAppender {

    public static String getErrorInfoFromException(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}
