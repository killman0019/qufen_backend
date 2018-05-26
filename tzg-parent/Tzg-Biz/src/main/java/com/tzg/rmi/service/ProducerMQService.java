package com.tzg.rmi.service;

import com.alibaba.rocketmq.common.message.Message;


/**
 * rocketMQ消息生产
 * @author Administrator
 *
 */
public interface ProducerMQService {
	

	/**
	 * 发送消息
	 * @param msg 消息主体
	 * @param vcSender 发送者
	 * @param vcReceiver 接收者
	 */
	public void sendMSG(Message msg, String vcSender, String vcReceiver);



}
