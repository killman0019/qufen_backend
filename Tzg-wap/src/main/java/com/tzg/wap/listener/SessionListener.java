package com.tzg.wap.listener;


import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.TzgConstant;
						   

/**
 * session监听器. <br>
 * 在WEB容器的web.xml中添加本监听器的调用,具体格式如下：(其中的"[","]"分别用" <",">"替换) <br>
 * 注意在web.xml中配置的位置. <br>
 * 
 * @author wyz
 * @version 1.00
 * @see javax.servlet.http.HttpSessionAttributeListener
 */
public class SessionListener implements HttpSessionAttributeListener {
	private static final Log LOG = LogFactory.getLog(SessionListener.class);
	/**
	 * 定义监听的session属性名.
	 */
	public final static String LISTENER_NAME = "_userid";
	
	private RedisService redisService; 

	/**
	 * 加入session时的监听方法.
	 */
	public void attributeAdded(HttpSessionBindingEvent sbe) {
		//把当前登录的用户id放在radis中
		if(StringUtil.equals(LISTENER_NAME, sbe.getName())){
			try {
				getRedisService(sbe).put(sbe.getValue().toString()+TzgConstant.TZG_WAP, sbe.getValue().toString(),0);
			} catch (Exception e) {
				LOG.error("", e);
			}
			
		}
	}

	/**
	 * session失效时的监听方法.
	 */
	public void attributeRemoved(HttpSessionBindingEvent sbe) {
		//用户退出是，把用户id从radis中删除
		if(StringUtil.equals(LISTENER_NAME, sbe.getName())){
			try {
				getRedisService(sbe).del(sbe.getValue().toString()+TzgConstant.TZG_WAP);
			} catch (Exception e) {
				LOG.error("", e);
			}
		}
	}

	/**
	 * session覆盖时的监听方法.
	 */
	public void attributeReplaced(HttpSessionBindingEvent sbe) {
	}

	private RedisService getRedisService(HttpSessionBindingEvent sbe){
		if(redisService == null){
			ApplicationContext springCtx = WebApplicationContextUtils.getWebApplicationContext(sbe.getSession().getServletContext());
			redisService = (RedisService)springCtx.getBean("redisService");
		}
		return redisService;
	}
}


