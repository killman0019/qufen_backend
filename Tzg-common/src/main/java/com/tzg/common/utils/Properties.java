package com.tzg.common.utils;

public class Properties {
	
	/**radis 地址*/
	public  String redis_ip = "192.168.1.10";
	/**radis 端口*/
	public  Integer redis_port = 6379;
	
	
	public Properties(String redis_ip, Integer redis_port) {
		super();
		this.redis_ip = redis_ip;
		this.redis_port = redis_port;
	}


	public String getRedis_ip() {
		return redis_ip;
	}


	public void setRedis_ip(String redis_ip) {
		this.redis_ip = redis_ip;
	}


	public Integer getRedis_port() {
		return redis_port;
	}


	public void setRedis_port(Integer redis_port) {
		this.redis_port = redis_port;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
