package com.tzg.common.base.exception;

/**
 * 
 * Filename:    ParameterException.java  
 * Description: 参数异常,用于Controller层.参数非空、类型...等   
 * Copyright:   Copyright (c) 2015-2018 All Rights Reserved.
 * Company:     tzg.cn Inc.
 * @author:     heyiwu 
 * @version:    1.0  
 * Create at:   2016年1月22日 下午5:08:03  
 *
 */
public class ParameterException extends RuntimeException {

    private static final long serialVersionUID = -9069450688745416436L;
    private int               code;

    public ParameterException() {
        super();
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(Throwable throwable) {
        super(throwable);
    }

    public ParameterException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
