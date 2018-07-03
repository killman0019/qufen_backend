package com.tzg.wap.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tzg.wap.filter.wrapper.XssHttpServletRequestWrapper;

/**
 * 
 * Filename: XssFilter.java Description: XSS跨站脚本攻击漏洞拦截器，配置：
 * <p>
 * <filter> <filter-name>XssEscape</filter-name>
 * <filter-class>com.tzg.web.filter.XssFilter</filter-class> </filter> <filter-mapping>
 * <filter-name>XssEscape</filter-name> <url-pattern>/*</url-pattern>
 * <dispatcher>REQUEST</dispatcher> </filter-mapping>
 * </p>
 * Copyright: Copyright (c) 2015-2018 All Rights Reserved. Company: tzg.cn Inc.
 * 
 * @author: heyiwu
 * @version: 1.0 Create at: 2015年12月21日 下午5:06:14
 *
 */
public class XssFilter implements Filter {
	private static final Logger logger = LoggerFactory.getLogger(XssFilter.class);

	private String[] excludeURLs;
	private String[] excludeStartWithURLs;

	@Override
	public void init(FilterConfig config) throws ServletException {
		excludeURLs = str2Array(config.getInitParameter("exclude"));
		excludeStartWithURLs = str2Array(config.getInitParameter("excludeGETStartWith"));
	}

	private String[] str2Array(String exclude) {
		if (StringUtils.isBlank(exclude)) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		return exclude.split(",");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		// 解决跨域问题
		/**
		 * 自己写的解决跨域问题 ,上线之前注释掉 会引起CSRF攻击
		 */
		// 上线之前进行注释,本地运行打开注释,Nginx已经进行跨域处理,此处再次处理将会引起跨域问题
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "*");
		resp.setHeader("Access-Control-Max-Age", "3600");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-FILE-NAME,XFILECATEGORY,XFILESIZE");//Content-Type,
		resp.setHeader("Access-Control-Allow-Credentials", "true");

		String path = req.getServletPath();
		if (ArrayUtils.contains(excludeURLs, path)) {
			logger.debug("XSS filter ignored ,servlet path:{}", path);
			chain.doFilter(req, resp);
			return;
		}
		for (String url : excludeStartWithURLs) {
			if (path.startsWith(url)) {
				logger.debug("XSS filter ignored ,servlet path:{}", path);
				chain.doFilter(req, resp);
				return;
			}
		}
		logger.debug("XSS filter controled ,servlet path:{}", path);
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
	}

	@Override
	public void destroy() {
	}
}