package com.tzg.entitys.rocketmqmsgreceive;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface RocketmqmsgreceiveMapper extends BaseMapper<Rocketmqmsgreceive, java.lang.Integer> {	

	/**
     * 根据消息key查询消息
     * @param key 消息key
     */
	public Rocketmqmsgreceive findByKey(String key);
	
	/**
     * 根据消息key查询消息
     * @param key 消息key
     */
	public Rocketmqmsgreceive findByKeyMqTag(Map<String, String> key);
}
