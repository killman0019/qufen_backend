package com.tzg.common.enums;

/** 
* @ClassName: PostType 
* @Description: TODO<帖子类型> 
* @author linj<作者>
* @date 2018年7月4日 上午9:23:48 
* @version v1.0.0 
*  帖子类型：1-评测；2-讨论；3-文章,4-悬赏
*/
public enum PostType{
	/**
	 * 1-评测
	 */
	EVALUATION(1,"评测"),
	/**
	 * 2-讨论
	 */
	DICCUSS(2,"讨论"),
	/**
	 * 3-文章
	 */
	ARTICLE(3,"文章"),
	/**
	 * 4-悬赏
	 */
	REWARD(4,"悬赏");
	
	private Integer value;
	private String desc;
		
	private PostType(Integer value, String desc){
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
