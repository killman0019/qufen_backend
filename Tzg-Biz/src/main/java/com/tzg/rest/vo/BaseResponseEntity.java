package com.tzg.rest.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class BaseResponseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	//0-接口正常 ，其他数字是错误码
	private Integer status;
	//返回消息
	private String msg;
	//预留字段 错误码
	private Integer code;
	//预留字段 错误原因
	private String reason;
	//来源uri
	private String fromuri;
	//用户token
	private String token;
	//通用返回体
	private Object data;
	private long serverDatetime; //服务器当前时间

	public long getServerDatetime() {
		return serverDatetime;
	}

	public void setServerDatetime(long serverDatetime) {
		this.serverDatetime = serverDatetime;
	}

	public BaseResponseEntity() {
		this.setCode(0);
		this.setMsg("Success");
		this.setData("");
		this.setServerDatetime(new Date().getTime());//设置系统当前时间
	}

	public BaseResponseEntity(Integer status, String msg, Integer code,
			String reason, String fromuri) {
		super();
		this.status = status;
		this.msg = msg;
		this.code = code;
		this.reason = reason;
		this.fromuri = fromuri;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		if (StringUtils.isNotBlank(msg)
				&& (msg.indexOf("Exception") != -1 || msg.equals("null"))) {
			this.msg = "网络连接超时";
		} else {
			this.msg = msg;
		}
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getFromuri() {
		return fromuri;
	}

	public void setFromuri(String fromuri) {
		this.fromuri = fromuri;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
