package com.tzg.common.base.exception;

import org.mybatis.spring.MyBatisSystemException;

/**
 * 
 * Filename:    MyBatisDataAccessException.java  
 * Description: 持久层异常   
 * Copyright:   Copyright (c) 2015-2018 All Rights Reserved.
 * Company:     tzg.cn Inc.
 * @author:     heyiwu 
 * @version:    1.0  
 * Create at:   2016年1月22日 下午5:51:09  
 *
 */
public class MyBatisDataAccessException  extends MyBatisSystemException{
 
    private static final long serialVersionUID = 1690149524976969803L;

    public MyBatisDataAccessException(Throwable cause) {
        super(cause);
    }
    
}
