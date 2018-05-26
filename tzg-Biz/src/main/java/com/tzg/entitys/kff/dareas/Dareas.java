package com.tzg.entitys.kff.dareas;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Dareas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1006722097641595345L;
	/**
     * 区划编号
     */ 	
	private java.lang.String code;
    /**
     * 区划名称
     */ 	
	private java.lang.String name;

	
	public void setCode(java.lang.String value) {
		this.code = value;
	}
	
	public java.lang.String getCode() {
		return this.code;
	}
	
	public void setName(java.lang.String value) {
		this.name = value;
	}
	
	public java.lang.String getName() {
		return this.name;
	}

	
}

