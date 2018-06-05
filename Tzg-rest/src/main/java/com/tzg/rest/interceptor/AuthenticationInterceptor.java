package com.tzg.rest.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.rest.constant.RestRequestHead;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.utils.HttpRequestDeviceUtils;
import com.tzg.rest.vo.BaseResponseEntity;

/**
 * 统一认证拦截器
 * @version 1.0
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	
	protected static Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

	
	@Autowired
	private RedisService redisService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		BaseResponseEntity resp = new BaseResponseEntity();
		String currentURL = request.getRequestURI();
		if (currentURL.indexOf("/tzgCredit/") == -1 ||currentURL.indexOf("/tzgCredit/")>=0){
			return true;
		}
		try {
			String secId = request.getHeader("secId");
			String token = request.getHeader("token");
			
			if(StringUtil.isBlank(token)) {
				logger.info("{设备:"+secId+",data:缺少token参数}");
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			 
			//token验证失败
			
			if(null == AccountTokenUtil.decodeAccountToken(token)){
				if(HttpRequestDeviceUtils.getTerminalType(request)==4){//Android 强制退出方式
					response.setStatus(401);
				}else{
					throw  new RestServiceException(RestErrorCode.NO_AUTH);
				}
			}else{ //存在token
        			 /*翁耀中添加--开始---t统一登录信息放到redis中，bbs，人人赚 调用********************************************/
			
			Integer uid = AccountTokenUtil.decodeAccountToken(token);
                    String key =  SHAUtil.encode(uid + TzgConstant.LOGIN_SIGN_KEY);
                    this.redisService.put(key+TzgConstant.TZG_REST, key, 60 * 60 * 24); 
                    /*翁耀中添加--结束********************************************/
			}
		
		 
			
			return Boolean.TRUE;
		 } catch (RestServiceException e) {
			 logger.error("AuthenticationInterceptor preHandle error:{}", e);
			 resp.setCode(e.getErrorCode());
			 resp.setMsg(e.getMessage());
			 response.setContentType("application/json;charset=UTF-8");
             response.getWriter().write(JSON.toJSONString(resp, SerializerFeature.PrettyFormat));
             return Boolean.FALSE;
		 } catch (Exception e) {
			 e.printStackTrace();
			 resp.setCode(RestErrorCode.SYS_ERROR.getValue());
			 resp.setMsg(RestErrorCode.SYS_ERROR.getErrorReason());
             response.setContentType("application/json;charset=UTF-8");
             response.getWriter().write(JSON.toJSONString(resp, SerializerFeature.PrettyFormat));
             return Boolean.FALSE;
         }
	}
	
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
     private void logUerOperation(HttpServletRequest request, 
     		HttpServletResponse response, Exception ex) {
    	String currentURL = request.getRequestURI();
 		if (currentURL.indexOf("/about/update/checkUpdate") != -1){
 			return ;
 		}
      	String path = this.getURLQueryStr(request);
      	String secId = request.getHeader("secId");
		String token = request.getHeader("token");
      	String userId = StringUtil.isBlank(token) ? null : AccountTokenUtil.decodeAccountToken(token)+"";
      	String ip = this.getIpAddr(request);
      	RestRequestHead head = HttpRequestDeviceUtils.getUserAgent(request);
   		
      	StringBuffer logInfo = new StringBuffer();
   		logInfo.append("[用户操作1--ios个人版,2 安卓,3 ios企业版]");
   		logInfo.append("{secId:");
   		logInfo.append(secId);
   		logInfo.append(",userId:");
   		logInfo.append(userId);
   		logInfo.append(",path:");
   		logInfo.append(path);
   		logInfo.append(",ip:");
   		logInfo.append(ip);
   		logInfo.append(",deviceType:");
   		logInfo.append(head.getPlatform());
   		logInfo.append(",appVersion:");
   		logInfo.append(head.getAppVersion());
   		logInfo.append("}");
   		logger.info(logInfo.toString());
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
}
