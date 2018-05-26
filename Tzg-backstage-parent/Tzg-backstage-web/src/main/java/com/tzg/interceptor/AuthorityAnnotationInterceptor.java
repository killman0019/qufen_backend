package com.tzg.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tzg.core.utils.HttpSessionUtil;
import com.tzg.entitys.leopard.console.ConsoleLoginAccount;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.service.SystemParam.SystemParamService;

/**
 * 拦截器，拦截一些想要的数据，例如：用户访问路径等。
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {

	private List<String> uncheckUrls = new ArrayList<String>(); // 不被拦截的地址

	private static final String URL_CODE = "upload_file_path";

	@Autowired
	private SystemParamService systemParamService;

	/**
	 * 重写 preHandle()方法，在业务处理器处理请求之前对该请求进行拦截处理
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @throws Exception
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		return preparedSessionUser(request, response);
	}

	/**
	 * 查看用户是否登录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 */
	private boolean preparedSessionUser(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String currentURL = request.getRequestURI(); // 取得根目录所对应的绝对路径:
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		String paramId = request.getParameter("id");
		if (currentURL.substring(path.length()).equals("/")
				|| currentURL.indexOf("/avatars/") != -1
				|| currentURL.indexOf("/css/") != -1
				|| currentURL.indexOf("/js/") != -1
				|| currentURL.indexOf("/ueditor/") != -1
				|| currentURL.indexOf("/images/") != -1
				|| currentURL.indexOf("/fonts/") != -1
				|| currentURL.indexOf("/dynamicValidateCode") != -1
				|| currentURL.matches(".*/login")
				|| currentURL.indexOf("/logout") != -1
				|| currentURL.indexOf("/baseaccount") != -1
				|| currentURL.indexOf("/SMSSearch") != -1
				|| currentURL.indexOf("/projectrisk/upload") != -1
				) {
			return true;
		}
		ConsoleLoginAccount loginAccount = HttpSessionUtil.getLoginSession();
		if (loginAccount == null) {
			response.sendRedirect(basePath);
			return false;
		}
		if (loginAccount.getVcLoginName().equals("root")) {
			return true;
		}

		// 进入更改密码页面和执行修改密码动作。
		if ((currentURL.indexOf("/changepassword") != -1 || currentURL
				.indexOf("/updatepassword") != -1)

		&& paramId.equals(Integer.toString(loginAccount.getId()))) {
			return true;
		}
		if (currentURL.indexOf("/main") != -1
				|| currentURL.indexOf("/nopermission") != -1) {
			return true;
		}
		if (!checkPermission(request)) {
			response.sendRedirect(basePath + "nopermission");
			return false;
		}
		return true;
	}

	/**
	 * 权限检测
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private boolean checkPermission(final HttpServletRequest request)
			throws Exception {
		List<String> resources = HttpSessionUtil.getResourceSession();
		String checkURL = request.getRequestURI().substring(
				request.getContextPath().length());
		if (resources != null && resources.contains(checkURL)) {
			return true;
		} else {
			SystemParam systemParam = systemParamService.findByCode(URL_CODE);
			if (systemParam == null) {
				throw new Exception("系统参数" + URL_CODE + "未设置，请先设置！");
			}
			String codePathValue = systemParam.getVcParamValue();
			uncheckUrls.add(codePathValue);
			if (uncheckUrls.contains(checkURL)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 完成对页面的render以后调用
	 * 
	 * @param request
	 * @param response
	 * @param ex
	 * 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logUerOperation(request, response, ex);
	}

	/**
	 * 用户操作记载日志
	 * 
	 * @param request
	 * @param response
	 * @param ex
	 * 
	 */
	private void logUerOperation(HttpServletRequest request,
			HttpServletResponse response, Exception ex) {
	}

}
