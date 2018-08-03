package com.tzg.common.enums;

/** 
* @ClassName: RewardDetailStatus 
* @Description: TODO<活动奖励发放状态> 
* @author linj<作者>
* @date 2018年7月4日 上午9:23:48 
* @version v1.0.0 
*  状态：0-待发放，1-已发放，2-发放失败
*/
public enum RewardDetailStatus{
	/**
	 * 0-待发放
	 */
	UNSEND(0,"待发放"),
	/**
	 * 1-已发放
	 */
	SENDSUCCESS(1,"已发放"),
	/**
	 * 2-发放失败
	 */
	SENDFAIL(2,"发放失败");
	
	private int value;
	private String desc;
		
	private RewardDetailStatus(int value, String desc){
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
