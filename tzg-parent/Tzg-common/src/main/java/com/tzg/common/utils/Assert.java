package com.tzg.common.utils;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.tzg.common.base.exception.ParameterException;

public class Assert {
    private Assert() {
    }

    public static void isNull(Object object, String message) throws Exception  {
        if (object == null) {
        	throwException(message);
        }
    }

    public static void notNull(Object object, String message) throws Exception  {
        if (object != null) {
        	throwException(message);
        }
    }


    public static void isBlank(String text, String message) throws Exception  {
        if (StringUtils.isBlank(text)) {
        	throwException(message);
        }
    }

    
    public static void notBlank(String text, String message) throws Exception  {
        if (StringUtils.isNotBlank(text)) {
        	throwException(message);
        }
    }

   
    public static void isTrue(boolean expression, String message) throws Exception  {
        if (expression) {
        	throwException(message);
        }
    }
    
    public static void notTrue(boolean expression, String message) throws Exception  {
        if (!expression) {
        	throwException(message);
        }
    }

   
    @SuppressWarnings("rawtypes")
	public static void isEmpty(Collection collection, String message) throws Exception  {
        if (collection != null && !collection.isEmpty()) {
        	throwException(message);
        }
    }

    
    @SuppressWarnings("rawtypes")
	public static void notEmpty(Collection collection, String message) throws Exception {
        if (collection == null || collection.isEmpty()) {
            throwException(message);
        }
    }
    
    public static  void notEmpty(Object[] collection, String message) throws Exception  {
        if (collection == null || collection.length ==0) {
        	throwException(message);
        }
    }

   

    private static void throwException(String message) throws Exception {
    	throw new ParameterException(message);
    }
}
