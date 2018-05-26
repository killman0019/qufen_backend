package com.tzg.entitys.rocketmqmsgreceive;

import java.io.Serializable;

public class Rocketmqmsgreceive implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * 消息的key
     */ 	
	private java.lang.String vckey;
    /**
     * 结果success或者failure
     */ 	
	private java.lang.String vcresult;

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}

	public java.lang.String getVckey() {
		return vckey;
	}

	public void setVckey(java.lang.String vckey) {
		this.vckey = vckey;
	}

	public java.lang.String getVcresult() {
		return vcresult;
	}

	public void setVcresult(java.lang.String vcresult) {
		this.vcresult = vcresult;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

	
}

