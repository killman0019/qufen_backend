package com.tzg.rest.exception;

/**
 * 
* @ClassName: MyRuntimeException
* @Description: TODO
* @author chy@tzg.cn
* @date 2014-12-29 下午12:03:33
*
 */
public class MyRuntimeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String message;

	public MyRuntimeException(){}
	
	public MyRuntimeException(String message) {
		super(message);
		this.message = message;
	}

	public MyRuntimeException(Throwable cause) {
		super(cause);
	}

	public MyRuntimeException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	protected void setMessage(String message) {
		this.message = message;
	}
}
