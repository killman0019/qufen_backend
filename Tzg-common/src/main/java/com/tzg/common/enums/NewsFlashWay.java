package com.tzg.common.enums;

/** 
* @ClassName: NewsFlashWay 
* @Description: TODO<轮播图的展示位> 
* @author linj<作者>
* @date 2018年7月4日 上午9:23:48 
* @version v1.0.0 
*  轮播图的展示位：0-资讯列表，1-发现列表
*/
public enum NewsFlashWay{
	/**
	 * 0-资讯列表
	 */
	NEWSFLASHLIST(0,"资讯列表"),
	/**
	 * 1-发现列表
	 */
	FOUNDLIST(1,"发现列表");
	
	private int value;
	private String desc;
		
	private NewsFlashWay(int value, String desc){
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
