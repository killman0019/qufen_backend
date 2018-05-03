package com.tzg.rest.exception;

import com.tzg.rest.utils.SystemUtils;


/**
 * 
* @ClassName: LogicException
* @Description:   逻辑错误异常
 * 当系统发生可预料错误时，需要抛出该异常
* @author chy@tzg.cn
* @date 2014-12-29 下午12:44:10
*
 */
public class LogicException extends MyRuntimeException {

	private static final long serialVersionUID = 1L;

	private String messageCode;

	private Object[] args;

	private String fullMessage;

	private String simpleMessage;

	public LogicException(String messageCode) {
		this(messageCode, null, null);
	}

	public LogicException(String messageCode, Object... args) {
		this(messageCode, args, null);
	}

	public LogicException(Throwable cause) {
		this("", null, cause);
	}

	public LogicException(String messageCode, Throwable cause) {
		this(messageCode, null, cause);
	}

	public LogicException(String messageCode, Object[] args, Throwable cause) {

		super(cause);

		this.messageCode   = messageCode;
		if(args != null) {
			this.args          = args.clone();
		}
		this.simpleMessage = SystemUtils.getAppMessage(this.messageCode, args);
		this.fullMessage   = "[" + this.messageCode + "]" + this.simpleMessage;

	}

	public final String getMessageCode() {
		return messageCode;
	}

	public final Object[] getArgs() {
		if(args == null) {
			return null;
		}
		return args.clone();
	}

	public final String getMessage() {
		return fullMessage;
	}

	public final String getSimpleMessage() {
		return simpleMessage;
	}

	protected void setMessage(String message) {
		this.fullMessage   = message;
		this.simpleMessage = message;
	}
	
}
