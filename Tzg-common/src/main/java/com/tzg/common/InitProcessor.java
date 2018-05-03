package com.tzg.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;

import com.dangdang.config.service.zookeeper.ZookeeperConfigGroup;
import com.tzg.common.bbs.client.Client;
import com.tzg.common.rocketMQ.RocketMQProperties;

public class InitProcessor implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = LoggerFactory.getLogger(InitProcessor.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ZookeeperConfigGroup sysConfig = getZookeeperConfigGroup(event);
        if (null == sysConfig) {
            logger.warn("ZookeeperConfigGroup bean is null ,event source:{},", event.getSource().getClass());
            return;
        }

        mqConfInit(sysConfig);

        clientConfInit(sysConfig);
    }

    private ZookeeperConfigGroup getZookeeperConfigGroup(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        try {
            if (context.getParent() == null) {
                context = ((WebApplicationContext) event.getSource());
                //springmvc优先采用id获取bean
                return (ZookeeperConfigGroup) context.getBean("sysConfig");
            }
            //spring中优先根据class获取bean
            return context.getBean(ZookeeperConfigGroup.class);
        } catch (Exception ignore) {
        }
        return null;
    }

    private void mqConfInit(ZookeeperConfigGroup sysConfig) {
        String path = sysConfig.get("rocketMqPath");
        logger.info("init mq conf:{}", path);
        RocketMQProperties.setRocketMqPath(path);
    }

    private void clientConfInit(ZookeeperConfigGroup sysConfig) {
        logger.info("init client conf...");
        Client.setUC_API(sysConfig.get("UC_API"));
        Client.setUC_IP(sysConfig.get("UC_IP"));
        Client.setUC_KEY(sysConfig.get("UC_KEY"));
        Client.setUC_APPID(sysConfig.get("UC_APPID"));
        Client.setUC_CONNECT(sysConfig.get("UC_CONNECT"));
    }

}
