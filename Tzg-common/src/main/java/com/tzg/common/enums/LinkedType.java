package com.tzg.common.enums;

/** 
* @ClassName: NewsFlashType 
* @Description: TODO<链接类型> 
* @author linj<作者>
* @date 2018年7月4日 上午9:23:48 
* @version v1.0.0 
*  链接类型：0-完整版专业评测，1-自定义评测，2-文章，3-打假，4-单项评测,5-关注
*/
public enum LinkedType{
	/**
	 * 0-完整版专业评测
	 */
	COMPLETEMAJOREVALUATING(0,"完整版专业评测"),
	/**
	 * 1-自定义评测
	 */
	CUSTOMEVALUATING(1,"自定义评测"),
	/**
	 * 2-文章
	 */
	ARTICLE(2,"文章"),
	/**
	 * 3-打假
	 */
	COUNTERFEIT(3,"打假"),
	/**
	 * ，4-单项评测
	 */
	SINGLEEVALUATING(4,"单项评测"),
	/**
	 * ，5-关注
	 */
	FOLLOW(5,"关注");
	
	private int value;
	private String desc;
		
	private LinkedType(int value, String desc){
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
