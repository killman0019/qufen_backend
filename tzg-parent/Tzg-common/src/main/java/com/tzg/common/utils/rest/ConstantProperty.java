package com.tzg.common.utils.rest;

/**
 * 
* @ClassName: ConstantProperty
* @Description:常量属性类，实质是一个键值对 对象 
* @author chy@tzg.cn
* @date 2015-1-6 下午2:48:35
*
 */
public class ConstantProperty {
	/**
	 * 属性键值，String类型
	 */
	public Object propertyKey;
	/**
	 * 属性值，String类型
	 */
	public Object propertyValue;
	
	public ConstantProperty() {
		super();
	}
	public ConstantProperty(Object propertyKey, Object propertyValue) {
		super();
		this.propertyKey = propertyKey;
		this.propertyValue = propertyValue;
		
	}

	public Object getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Object getPropertyKey() {
		return propertyKey;
	}

	public void setPropertyKey(Object propertyKey) {
		this.propertyKey = propertyKey;
	}
	

}
