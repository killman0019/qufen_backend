package com.tzg.common.enums;

/** 
* @ClassName: MiningActivityStatus 
* @Description: TODO<挖矿活动状态> 
* @author linj<作者>
* @date 2018年7月4日 上午9:23:48 
* @version v1.0.0 
*  活动状态：0-未开始，1-进行中，2-已结束，3-已终止
*/
public enum MiningActivityStatus{
	/**
	 * 0-未开始
	 */
	UNSTART(0,"未开始"),
	/**
	 * 1-进行中
	 */
	STARTING(1,"进行中"),
	/**
	 * 2-已结束
	 */
	END(2,"已结束"),
	/**
	 * 3-已终止
	 */
	OVER(3,"已终止"),
	/**
	 * 4-已挖完
	 */
	DIGOUT(4,"已挖完");
	
	private int value;
	private String desc;
		
	private MiningActivityStatus(int value, String desc){
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
