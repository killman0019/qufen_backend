package com.tzg.rest.exception;

/**
 * 系统级异常
 * 当系统发生不可预料错误时，需要抛出该异常
 * @author 
 * @since 2.0
 *
 */
public class SystemException extends MyRuntimeException {

	private static final long serialVersionUID = 1L;

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
