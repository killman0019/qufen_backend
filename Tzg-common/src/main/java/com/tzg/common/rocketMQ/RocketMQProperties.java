package com.tzg.common.rocketMQ;

import org.apache.commons.lang.StringUtils;
import com.dangdang.config.service.zookeeper.ZookeeperConfigGroup;
import com.tzg.common.utils.SpringUtils;
public class RocketMQProperties {

	/** rocketMQ 链接地址 */
	private static String rocketMqPath;

	public static String getRocketMqPath() {
        if (StringUtils.isBlank(rocketMqPath)) {
            ZookeeperConfigGroup zkcg = SpringUtils.getBean(ZookeeperConfigGroup.class);
            rocketMqPath = zkcg.get("rocketMqPath");
        }
		return rocketMqPath;
	}

	public static void setRocketMqPath(String rocketMqPath) {
		RocketMQProperties.rocketMqPath = rocketMqPath;
	}
	
	
	
}
