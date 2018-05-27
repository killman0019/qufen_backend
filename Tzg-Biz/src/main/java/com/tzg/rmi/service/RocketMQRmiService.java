package com.tzg.rmi.service;

import com.tzg.entitys.rocketmqlog.Rocketmqlog;
import com.tzg.entitys.rocketmqmsgreceive.Rocketmqmsgreceive;


/**
 * 消息处理的
 * @author Administrator
 *
 */
public interface RocketMQRmiService {
	
	/**
	 * 根据key查询RocketMQ的消息日志
	 * @param key 消息的key
	 * @return
	 */
	public Rocketmqlog findByKey(String key) throws Exception;
	
	/**
	 * 更新消息日志
	 * @param rocketmqlog
	 */
	public void update(Rocketmqlog rocketmqlog) throws Exception;
	
	/**
	 * 根据消息key和mqtag返回消息处理结果
	 * @param key 消息key
	 * @param mqtag 消息标示，属于什么系统用
	 * @return 消息处理结果
	 * @throws Exception
	 */
	public Rocketmqmsgreceive findByKey(String key, String mqtag) throws Exception;
	
	/**
	 * 保存消息处理结果
	 * @param rocketmqmsgreceive
	 * @throws Exception
	 */
	public void saveRocketmqmsgreceive(Rocketmqmsgreceive rocketmqmsgreceive) throws Exception;
	
}
