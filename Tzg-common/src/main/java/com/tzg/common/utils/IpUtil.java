package com.tzg.common.utils;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

public class IpUtil {
	/**
  	 * 功能描述：获取真实的IP地址
  	 */
  	public static  String getIpAddr(HttpServletRequest request ) {
  		String ip = request.getHeader("x-forwarded-for");
  		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
  			ip = request.getHeader("Proxy-Client-IP");
  		}
  		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
  			ip = request.getHeader("WL-Proxy-Client-IP");
  		}
  		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
  			ip = request.getRemoteAddr();
  		}
  		if (StringUtils.isNotBlank(ip) && ip.contains(",")) {
  			String[] ips = ip.split(",");
  			ip = ips[ips.length - 1];
  		}
  		return ip;
  	}

}
