package com.tzg.common.enums;

/** 
* @ClassName: FollowType 
* @Description: TODO<用户是否进行了操作> 
* @author linj<作者>
* @date 2018年7月4日 上午9:23:48 
* @version v1.0.0 
*  用户是否关注了该项目:0-未关注，1-已关注
*/
public enum UserOperation{
	/**
	 * 0-未操作
	 */
	UNOPERATION(0,"未操作"),
	/**
	 * 1-已操作
	 */
	OPERATIONING(1,"已操作");
	
	private int value;
	private String desc;
		
	private UserOperation(int value, String desc){
		this.value = value;
		this.desc = desc;
	}
		
	public int getValue(){
		return value;
	}
		
	public String getDesc(){
			return desc;
	}
}
