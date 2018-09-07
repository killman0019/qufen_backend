package com.tzg.common.enums;

/** 
* @ClassName: RewardDetailStatus 
* @Description: TODO<悬赏的状态> 
* @author linj<作者>
* @date 2018年7月4日 上午9:23:48 
* @version v1.0.0 
*  悬赏的状态：0-进行中，1-已结束，2-已撤销
*/
public enum RewardActivityState{
	/**
	 * 0-进行中
	 */
	STARTING(0,"进行中"),
	/**
	 * 1-已结束
	 */
	ENDING(1,"已结束"),
	/**
	 * 2-已撤销
	 */
	REVOKEING(2,"已撤销");
	
	private int value;
	private String desc;
		
	private RewardActivityState(int value, String desc){
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
