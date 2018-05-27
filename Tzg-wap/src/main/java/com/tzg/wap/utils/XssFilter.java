package com.tzg.wap.utils;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XssFilter implements Filter {

	private static final Log log = LogFactory.getLog(XssFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		// log.debug(req.getRequestURI());
		String uris = req.getRequestURI().toLowerCase();
		if (!(uris.endsWith(".css") || uris.endsWith(".js") || uris.endsWith(".png"))) {
			// System.out.println("============" + uris);
		}
		// 获取参数名称集合
		Enumeration<String> enumer = request.getParameterNames();

		// 遍历所有参数判断是否含有xss或sql注入
		while (enumer.hasMoreElements()) {

			// 获取参数值
			String name = (String) enumer.nextElement();
			String val = request.getParameter(name);
			// XssUtil.hasXssCode(val) || XssUtil.hasXssCode(name)
			// 验证是否含有XSS代码，有则直接跳转
			if (false) {
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + req.getContextPath() + "/";
				log.warn("请求含Xss攻击或SQL注入内容: " + name + " = " + val);
				log.warn("redirect : " + basePath);
				res.sendRedirect(basePath);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}