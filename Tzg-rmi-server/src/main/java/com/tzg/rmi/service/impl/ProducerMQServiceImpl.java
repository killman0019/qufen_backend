package com.tzg.rmi.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.tzg.common.rocketMQ.RocketMQProperties;
import com.tzg.common.service.rocketmqlog.RocketmqlogService;
import com.tzg.entitys.rocketmqlog.Rocketmqlog;
import com.tzg.rmi.service.ProducerMQService;

/**
 * rocketMQ 消息生产 
 * @author Administrator
 *
 */
public class ProducerMQServiceImpl implements ProducerMQService {

	private static final Log LOG = LogFactory.getLog(ProducerMQServiceImpl.class);
	
	@Autowired
	private RocketmqlogService rocketmqlogService;

	/**
	 * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
	 * 注意：ProducerGroupName需要由应用来保证唯一<br>
	 * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
	 * 因为服务器会回查这个Group下的任意一个Producer
	 * FIXME  DefaultMQProducer配置为spring bean,配置参数可调整、可增加
	 */
	DefaultMQProducer producer ;
	
	private DefaultMQProducer getProducer() {
		if(producer == null){
			producer = new DefaultMQProducer("CREDITProducerGroupName");
			/**
			 * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
			 * 注意：切记不可以在每次发送消息时，都调用start方法
			 */
			producer.setNamesrvAddr(RocketMQProperties.getRocketMqPath());
			try {
				producer.start();
				//生产者实现优雅关机 
				Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                    	LOG.info("生产者关闭中 ...");
                    	if(producer != null) {
                    		producer.shutdown();
                    	}
                    }
				});
			} catch (MQClientException e) {
			    LOG.error("mq producer MQClientException:",e);
			}
		}
		
		return producer;
	}
	
	
	@Override
	public void sendMSG(Message msg, String vcSender, String vcReceiver) {
		Rocketmqlog rocketmqlog = this.getRocketmqlog(msg, vcSender, vcReceiver);
		try {
			//持久化 FIXME 性能优化2点：1、save和update合并，发送成功之后save 2、改用异步save，即提交信息到队列、开启单线程或多线程处理队列.
		    //问题2点：1、send和save分为2个try catch 如改为队列持久化 不要抛出异常，一个catch也可 
		    //		   2、catch处理 区分异常，是send异常还是save异常,send异常判断sendResult状态,根据业务需要分别处理消息是否需要重发.
			rocketmqlogService.save(rocketmqlog);
			//发消息
			SendResult sendResult = getProducer().send(msg);
			String log = "生产消息topic="+msg.getTopic()+"消息tag="+msg.getTags()+"消息body="+
					new String(msg.getBody())+"消息keys=" + msg.getKeys() + "发送结果为" + sendResult.getSendStatus();
			LOG.info(log);
			//更新发送状态
			this.updateRocketmqlogSuccess(rocketmqlog, sendResult.getSendStatus().toString());
		} catch (Exception e) {
			String log = "生产消息topic="+msg.getTopic()+"消息tag="+msg.getTags()+"消息body="+
					new String(msg.getBody())+"消息keys=" + msg.getKeys() + "发送失败"+e;
			LOG.error(log);
			//更新发送状态
			this.updateRocketmqlogFailure(rocketmqlog);
			//更新状态
			
			
		}
	}
	
	/**
	 * 持久化消息
	 * @param msg
	 * @param vcSender
	 * @param vcReceiver
	 * @throws Exception
	 */
	private Rocketmqlog getRocketmqlog(Message msg, String vcSender, String vcReceiver){
		Rocketmqlog rocketmqlog = new Rocketmqlog();
		rocketmqlog.setVcTopic(msg.getTopic());
		rocketmqlog.setVcTag(msg.getTags());
		rocketmqlog.setVcKey(msg.getKeys());
		rocketmqlog.setVcBody(new String(msg.getBody()));
		rocketmqlog.setVcSender(vcSender);
		rocketmqlog.setVcReceiver(vcReceiver);
		rocketmqlog.setDtCreate(new Date());
		return rocketmqlog;
	}
	
	
	/**
	 * 消息发送后修改状态--成功
	 */
	private void updateRocketmqlogSuccess(Rocketmqlog rocketmqlog, String sendStatus) throws Exception{
		rocketmqlog.setDtSend(new Date());
		rocketmqlog.setVcSendState(sendStatus);
		rocketmqlogService.update(rocketmqlog);
	}
	
	/**
	 * 消息发送后修改状态--失败
	 */
	private void updateRocketmqlogFailure(Rocketmqlog rocketmqlog){
		rocketmqlog.setVcSendState("failure"); 
		try {
			rocketmqlogService.update(rocketmqlog);
		} catch (Exception e) {
		    LOG.error("修改mq消息["+(null!=rocketmqlog?rocketmqlog.getId():"")+"]状态失败:",e);
		}
	}
}
