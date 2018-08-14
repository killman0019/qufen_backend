package com.tzg.common.enums;

/** 
* @ClassName: DiscussType 
* @Description: TODO<精评类型> 
* @author linj<作者>
* @date 2018年7月4日 上午9:23:48 
* @version v1.0.0 
*  精评类型：0-点赞，1-认证账号发布，2-人工精选,3-普通爆料
*/
public enum DiscussType{
	/**
	 * 0-点赞
	 */
	DOTPRAISE(0,"点赞"),
	/**
	 * 1-认证账号发布
	 */
	AUTHACCOUNTPUBLISH(1,"认证账号发布"),
	/**
	 * 2-人工精选
	 */
	PEOPLECHOICE(2,"人工精选"),
	/**
	 * 3-普通爆料
	 */
	ORDINARYBURST(3,"普通爆料");
	
	private Integer value;
	private String desc;
		
	private DiscussType(Integer value, String desc){
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
