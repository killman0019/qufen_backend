package com.tzg.facade.rocketMq.impl;

import java.math.BigDecimal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.common.message.Message;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.facade.rocketMq.RocketMqFacade;
import com.tzg.rmi.service.ProducerMQService;
import com.tzg.rmi.service.SystemParamRmiService;

/**
 * 人人赚Facade
 *
 */
@Service("rocketMqFacade")
public class RocketMqFacadeImpl implements RocketMqFacade {
    protected static Logger             logger = Logger.getLogger(RocketMqFacadeImpl.class);

    @Autowired
    private ProducerMQService           producerMQService;
    @Autowired
    private SystemParamRmiService       systemParamRmiService;

    @Override
    public void investSuccess(Integer investrecordId, Integer loginaccountId, String type) {
        try {
            //因为投资完成之后才会更新投资次数，这里需重新查询表获取投资次数
            Loginaccount account = null;
            //投资次数
            Integer numInvestTimes = account.getNumInvestTimes() == null ? 1 : account.getNumInvestTimes();

            StringBuffer msgBody = new StringBuffer(); //消息内容
            msgBody.append(investrecordId).append(";").append(numInvestTimes).append(";").append(type).append(";").append(account.getId());
            if (StringUtil.equals(type, "tbinvestrecord") || StringUtil.equals(type, "tbfixedbaoinvestrecord")) {
                SystemParam systemParam = systemParamRmiService.findByCode("msg_pyramid");
                if (null != systemParam && systemParam.getVcParamValue().equalsIgnoreCase("on")) {
                    //key的格式yyyyMMddHHmmss+5位随机数
                    String msgKey = System.currentTimeMillis() + "-" + RandomUtil.produceNumber(5) + "-" + account.getId();
                    String msgTopic = "tzg-invest";
                    String msgTag = "investSuccess";
                    Message msg = new Message(msgTopic, msgTag, msgKey, msgBody.toString().getBytes());// body
                    producerMQService.sendMSG(msg, "INVEST", "WEB");
                }
                
                systemParam = systemParamRmiService.findByCode("msg_activity");
                if (null != systemParam && systemParam.getVcParamValue().equalsIgnoreCase("on")) {
                    String msgKey1 = System.currentTimeMillis() + "-" + RandomUtil.produceNumber(5) + "-" + account.getId();
                    String msgTopic1 = "tzg-act";
                    String msgTag1 = "investSuccess";
                    Message msg1 = new Message(msgTopic1, msgTag1, msgKey1, msgBody.toString().getBytes());// body
                    producerMQService.sendMSG(msg1, "INVEST", "ACT");
                }
            }

            SystemParam systemParam = systemParamRmiService.findByCode("msg_message");
            if (null != systemParam && systemParam.getVcParamValue().equalsIgnoreCase("on")) {
                //发送到站内信
                String msgKey1 = System.currentTimeMillis() + "-" + RandomUtil.produceNumber(5) + "-" + account.getId();
                String msgTopic1 = "tzg-msg";
                String msgTag1 = "investSuccess";
                Message msg1 = new Message(msgTopic1, msgTag1, msgKey1, msgBody.toString().getBytes());// body
                producerMQService.sendMSG(msg1, "INVEST", "MSG");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("投资完成发消息失败" + e);
        }
    }

    @Override
    public void redSendSuccessNtf(Integer iloginAccountId, BigDecimal numAmt, String vcDesp) {
        try {
            SystemParam systemParam = systemParamRmiService.findByCode("msg_message");
            if (null != systemParam && systemParam.getVcParamValue().equalsIgnoreCase("on")) {
                StringBuffer msgBody = new StringBuffer(); // 消息内容
                msgBody.append(iloginAccountId).append(";").append("null").append(";").append(numAmt).append(";").append(vcDesp);

                // key的格式yyyyMMddHHmmss+5位随机数
                String msgKey = System.currentTimeMillis() + "-" + RandomUtil.produceNumber(5) + "-" + iloginAccountId;
                // topic
                String msgTopic = "tzg-msg";
                String msgTag = "redSendSuccess";

                Message msg = new Message(msgTopic, msgTag, msgKey, msgBody.toString().getBytes());// body
                producerMQService.sendMSG(msg, "msg", "all");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("红包发送完成发消息失败" + e);
        }
    }

    @Override
    public void registerSuccessNtf(Integer iloginAccountId) {
        try {
            StringBuffer msgBody = new StringBuffer(); // 消息内容
            msgBody.append(iloginAccountId);

            SystemParam systemParam = systemParamRmiService.findByCode("msg_message");
            if (null != systemParam && systemParam.getVcParamValue().equalsIgnoreCase("on")) {
                String msgKey = System.currentTimeMillis() + "-" + RandomUtil.produceNumber(5) + "-" + iloginAccountId;
                String msgTopic = "tzg-msg";
                String msgTag = "registerSuccess";
                Message msg = new Message(msgTopic, msgTag, msgKey, msgBody.toString().getBytes());// body
                producerMQService.sendMSG(msg, "msg", "all");
            }

            systemParam = systemParamRmiService.findByCode("msg_activity");
            if (null != systemParam && systemParam.getVcParamValue().equalsIgnoreCase("on")) {
                String msgKey1 = System.currentTimeMillis() + "-" + RandomUtil.produceNumber(5) + "-" + iloginAccountId;
                // topic
                String msgTopic1 = "tzg-act";
                String msgTag1 = "registerSuccess";
                Message msg1 = new Message(msgTopic1, msgTag1, msgKey1, msgBody.toString().getBytes());// body
                producerMQService.sendMSG(msg1, "register", "ACT");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("注册成功完成发消息失败" + e);
        }
    }

    @Override
    public void cashSuccessNtf(Integer iloginAccountId, Integer cashId) {
        try {
            StringBuffer msgBody = new StringBuffer(); // 消息内容
            msgBody.append(iloginAccountId).append(";").append(cashId);

            // key的格式yyyyMMddHHmmss+5位随机数
            String msgKey = System.currentTimeMillis() + "-" + RandomUtil.produceNumber(5) + "-" + iloginAccountId;
            // topic
            String msgTopic = "tzg-msg";
            String msgTag = "cashSuccess";

            Message msg = new Message(msgTopic, msgTag, msgKey, msgBody.toString().getBytes());// body
            producerMQService.sendMSG(msg, "msg", "all");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("提现成功完成发消息失败" + e);
        }
    }

    @Override
    public void rechargeSuccessNtf(Integer iloginAccountId, Integer rechargeId, BigDecimal numAmt) {
        try {
            StringBuffer msgBody = new StringBuffer(); // 消息内容
            msgBody.append(iloginAccountId).append(";").append(rechargeId).append(";").append(numAmt);
            SystemParam systemParam = systemParamRmiService.findByCode("msg_message");
            if (null != systemParam && systemParam.getVcParamValue().equalsIgnoreCase("on")) {
                // key的格式yyyyMMddHHmmss+5位随机数
                String msgKey = System.currentTimeMillis() + "-" + RandomUtil.produceNumber(5) + "-" + iloginAccountId;
                // topic
                String msgTopic = "tzg-msg";
                String msgTag = "rechargeSuccess";

                Message msg = new Message(msgTopic, msgTag, msgKey, msgBody.toString().getBytes());// body
                producerMQService.sendMSG(msg, "msg", "all");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("充值成功完成发消息失败" + e);
        }
    }
}
