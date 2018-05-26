package com.tzg.entitys.rocketmqlog;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface RocketmqlogMapper extends BaseMapper<Rocketmqlog, java.lang.Integer> {	
	
	/**
     * 根据消息key查询消息
     * @param key 消息key
     */
	public Rocketmqlog findByKey(String key);

}
