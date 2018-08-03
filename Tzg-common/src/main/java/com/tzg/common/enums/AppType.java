package com.tzg.common.enums;

/** 
* @ClassName: AppType 
* @Description: TODO<链接类型> 
* @author linj<作者>
* @date 2018年7月4日 上午9:23:48 
* @version v1.0.0 
*  app的类型:0-android,1-iOS
*/
public enum AppType{
	/**
	 * 0-android
	 */
	ANDROID(0,"android"),
	/**
	 * 1-iOS
	 */
	IOS(1,"iOS");
	
	private Integer value;
	private String desc;
		
	private AppType(Integer value, String desc){
		this.value = value;
		this.desc = desc;
	}
		
	public Integer getValue(){
		return value;
	}
		
	public String getDesc(){
			return desc;
	}
}
