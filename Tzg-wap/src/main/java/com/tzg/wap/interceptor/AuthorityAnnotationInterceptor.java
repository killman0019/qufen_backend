package com.tzg.wap.interceptor;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.CookieUtil;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.wap.utils.WapSessionUtil;

/**
 * 拦截器，拦截一些想要的数据，例如：用户访问路径等。
 */
public class AuthorityAnnotationInterceptor extends HandlerInterceptorAdapter {
    /**
     * 后缀黑名单:不允许的非法后缀,如：.old
     * 一般应该采用白名单：限定请求后缀比如.htm,由于是已有系统,限定后缀代码改动量会比较大，所以不得已采用黑名单
     */
    private String[] notAllowedSuffixes;

	@Autowired
	private RedisService redisService;  
	/**
    * 重写 preHandle()方法，在业务处理器处理请求之前对该请求进行拦截处理  
    * @param request
    * @param response
    * @param handler
    * @throws Exception    
    *     
    */
	@Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler)
            		throws Exception {
        return preparedSessionUser(request, response);
    }
    
    /**
    * 查看用户是否登录
    * @param request
    * @param response
    * @throws IOException 
    * @throws ServletException 
    *     
    */
    private boolean preparedSessionUser(HttpServletRequest request, 
    		HttpServletResponse response) throws Exception, ServletException {

    	String currentURL = request.getRequestURI(); // 取得根目录所对应的绝对路径:
    	String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"";
		//静态资源
		if (currentURL.indexOf("/assets/") != -1){
			return true;
		}
		String suffix =StringUtils.lowerCase(StringUtils.substringAfterLast(currentURL, "."));
        if(ArrayUtils.contains(notAllowedSuffixes,"."+suffix)){
            // XXX 非法后缀请求跳转页面
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.sendRedirect(basePath+"/error/500");
            return false;
        }
		//来源
		String surc = request.getParameter("surc");
		if(StringUtils.isNotBlank(surc)){
			request.getSession().setAttribute(TzgConstant.SOURCE_STORE_KEY, surc);
			CookieUtil.addCookie(response, TzgConstant.SOURCE_STORE_KEY, surc, 2592000); //保存一个月
		}
		//app来源表示
		String source = request.getParameter("source");
		if(StringUtil.equals("app", source)){
			String key = request.getParameter("key");
			//登出
			if(StringUtil.isBlank(key)){
	    		WapSessionUtil.removeLoginSession();
	    		
	    		String loginUrl =basePath+"/login/page";
				String url = request.getRequestURI().substring(request.getContextPath().length());
				StringBuffer redirectURL = new StringBuffer(url);
				String queryString = request.getQueryString();
				if (StringUtils.isNotBlank(queryString)) {
					redirectURL.append('?').append(queryString);
			    }
				String toURL = basePath + redirectURL.toString();
				
				toURL = toURL.replaceAll("source=app", "");
				
				if(toURL.indexOf("?")>0){
					toURL = toURL + "&@app:waplogin";
				}else{
					toURL = toURL + "?@app:waplogin";
				}
				response.sendRedirect(loginUrl + "?toURL="+toURL);
				return false;
	    	}else{
	    		this.appLogin(key);
	    	}
			
			
		}
		
		//
		Loginaccount loginAccount = WapSessionUtil.getLoginSession();
		if(loginAccount == null){
			//未登录下，记录登录后的跳转地址(目前：标的列表 或 标的详情页 或user 有效)
			if(StringUtils.isNotBlank(currentURL)
					&& currentURL.indexOf("/list") ==-1
					&& currentURL.indexOf("/app/subject") ==-1
					&& currentURL.indexOf("/app/article") ==-1
					&& currentURL.indexOf("/app/investRecord") ==-1
					&& (currentURL.indexOf("/user") !=-1 || currentURL.indexOf("/subject") !=-1) ){
				String url = request.getRequestURI().substring(request.getContextPath().length());
				StringBuffer redirectURL = new StringBuffer(url);
				String queryString = request.getQueryString();
				if (StringUtils.isNotBlank(queryString)) {
					redirectURL.append('?').append(queryString);
			    }
//				request.getSession().setAttribute("LOGIN_AFTER_REDIRECT_URL", redirectURL.toString());
			}
			//跳转登录页
			if (currentURL.indexOf("/user") !=-1){
				String loginUrl =basePath+"/login/page";
				String url = request.getRequestURI().substring(request.getContextPath().length());
				StringBuffer redirectURL = new StringBuffer(url);
				String queryString = request.getQueryString();
				if (StringUtils.isNotBlank(queryString)) {
					redirectURL.append('?').append(queryString);
			    }
				
				String toURL = basePath + redirectURL.toString();
				if(toURL.indexOf("?")>0){
					toURL = toURL + "&@app:waplogin";
				}else{
					toURL = toURL + "?@app:waplogin";
				}
				response.sendRedirect(loginUrl + "?toURL="+toURL);
	    		return false;
			}
		}
		
		return true;
    }
    
    /**
     * 
     * @param key 秘钥
     */
    public void appLogin(String key) throws Exception{
    	
		try {
			//登录
			String[] keys = key.split("-");
			String sign = SHAUtil.encode(keys[0]+ TzgConstant.USERID_SIGN);
			if(StringUtil.equals(sign, keys[1])){
				String rediskey = SHAUtil.encode(keys[0] + TzgConstant.LOGIN_SIGN_KEY)+TzgConstant.TZG_REST;
				if(redisService.exists(rediskey)){
					Loginaccount loginaccount = null;
					//保存登录信息至session
					WapSessionUtil.setLoginSession(loginaccount);
				}
			}
		} catch (Exception e) {
			LOG.error("app登录wap错误"+e);
		}
	}
    
    
    
    /**
     * 完成对页面的render以后调用
     * @param request
     * @param response
     * @param ex    
     *     
     */
    @Override
    public void afterCompletion(
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    		Object handler, 
    		Exception ex)
    				throws Exception {
    	logUerOperation(request, response, ex);
    }
    
    /**
     * 用户操作记载日志
     * @param request
     * @param response
     * @param ex    
     *     
     */
     private static final Log LOG = LogFactory.getLog(AuthorityAnnotationInterceptor.class);
     private void logUerOperation(HttpServletRequest request, 
     		HttpServletResponse response, Exception ex) {
    	 String currentURL = request.getRequestURI();
      	//静态资源
   		if (currentURL.indexOf("/assets/") != -1){
   			return;
   		}
      	StringBuffer logInfo = new StringBuffer();
      	String path = this.getURLQueryStr(request);
      	Map<String,String[]> paramMap = request.getParameterMap();
      	String id = "";
      	String sessionId = request.getSession().getId();
      	String ip = this.getIpAddr(request);
      	
   		Loginaccount loginAccount = WapSessionUtil.getLoginSession();
   		if(loginAccount != null){
   			id = loginAccount.getId()+"";
   		}
   		logInfo.append("[用户操作]--userId:");
   		logInfo.append(id);
   		logInfo.append("--path:");
   		logInfo.append(path);
   		logInfo.append("--paramMap:");
   		String paramStr = JSON.toJSON(paramMap).toString();
   		if(paramStr.toLowerCase().indexOf("password")!=-1){
   			paramStr = "{}";
   		}
   		logInfo.append(paramStr);
   		logInfo.append("--sessionId:");
   		logInfo.append(sessionId);
   		logInfo.append("--ip:");
   		logInfo.append(ip);
   		LOG.info(logInfo.toString());
     }
    
     
     /**
      * get参数
      * @param request
      * @return
      */
     private String getURLQueryStr(HttpServletRequest request){
     	String url = request.getRequestURI().substring(request.getContextPath().length());
 		StringBuffer redirectURL = new StringBuffer(url);
 		String queryString = request.getQueryString();
 		if (StringUtils.isNotBlank(queryString)) {
 			redirectURL.append('?').append(queryString);
 	    }
 		return redirectURL.toString();
     }
     
     /**
 	 * 功能描述：获取真实的IP地址
 	 */
 	public String getIpAddr(HttpServletRequest request ) {
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
 	
    public String[] getNotAllowedSuffixes() {
        return notAllowedSuffixes;
    }

    public void setNotAllowedSuffixes(String[] notAllowedSuffixes) {
        this.notAllowedSuffixes = notAllowedSuffixes;
    }
}
