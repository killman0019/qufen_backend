package com.tzg.common.enums;

/** 
* @ClassName: FollowType 
* @Description: TODO<关注类型> 
* @author linj<作者>
* @date 2018年7月4日 上午9:23:48 
* @version v1.0.0 
*  关注类型：1-关注项目;2-关注帖子；3-关注用户
*/
public enum FollowType{
	/**
	 * 1-关注项目
	 */
	PROJECTFOLLOW(1,"关注项目"),
	/**
	 * 2-关注帖子
	 */
	POSTFOLLOW(2,"关注帖子"),
	/**
	 * 3-关注用户
	 */
	USERFOLLOW(3,"关注用户");
	
	private Integer value;
	private String desc;
		
	private FollowType(Integer value, String desc){
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
