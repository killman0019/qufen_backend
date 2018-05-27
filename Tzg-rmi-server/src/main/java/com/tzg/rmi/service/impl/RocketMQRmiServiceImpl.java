package com.tzg.rmi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.rocketmqlog.RocketmqlogService;
import com.tzg.common.service.rocketmqmsgreceive.RocketmqmsgreceiveService;
import com.tzg.entitys.rocketmqlog.Rocketmqlog;
import com.tzg.entitys.rocketmqmsgreceive.Rocketmqmsgreceive;
import com.tzg.rmi.service.RocketMQRmiService;

public class RocketMQRmiServiceImpl implements RocketMQRmiService {
	
	@Autowired
	private RocketmqlogService rocketmqlogService;
	
	@Autowired
	private RocketmqmsgreceiveService rocketmqmsgreceiveService;
	
	public Rocketmqlog findByKey(String key) throws Exception{
		return rocketmqlogService.findByKey(key);
	}
	
	
	public void update(Rocketmqlog rocketmqlog) throws Exception{
		rocketmqlogService.update(rocketmqlog);
	}
	
	public Rocketmqmsgreceive findByKey(String key, String mqtag) throws Exception{
		return rocketmqmsgreceiveService.findByKeyMqTag(key,mqtag);
	}

	public void saveRocketmqmsgreceive(Rocketmqmsgreceive rocketmqmsgreceive) throws Exception{
		rocketmqmsgreceiveService.save(rocketmqmsgreceive);
	}
	
}
