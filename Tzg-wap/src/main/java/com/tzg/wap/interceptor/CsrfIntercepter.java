package com.tzg.wap.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tzg.common.utils.TzgConstant;

public class CsrfIntercepter extends HandlerInterceptorAdapter {
    private static final Logger logger    = LoggerFactory.getLogger(CsrfIntercepter.class);
    /**
     * csrf的token名称
     */
    private String              tokenName = TzgConstant.CSRF_PARAM_NAME;
    /**
     * 错误页面
     */
    private String              errorPage = "/error/500";
    /**
     * 排除url
     */
    private String[]  excludeUrls;

    /**
     * 判断请求是否合法：是否存在CSRF攻击
     * 登录或打开首页时，生成token放入cookie和session中
     * 判断请求、cookie、session中的token值是否一致，不一致视为不合法请求
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      
        if (request.getMethod().equals("GET")) {
            return true;
        }
        //优先检测请求参数、http请求头自定义属性、cookie
        String paramValue = request.getParameter(tokenName);
        if (StringUtils.isBlank(paramValue)) {
            paramValue = getCookieVal(request, tokenName);
        }
        if (StringUtils.isBlank(paramValue)) {
            paramValue = request.getHeader(tokenName);
        }

        Object sessionVal = request.getSession().getAttribute(TzgConstant.CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
        if (StringUtils.isNotBlank(paramValue) && paramValue.equals(sessionVal)) {
            return true;
        }
        //验证HTTP Referer 字段：Referer字段依赖浏览器，部分浏览器不支持或可被篡改（ie6、ff2），也可被用户设置不提供referer，故不可靠
        String referer = request.getHeader("Referer");
        int port = request.getServerPort();
        String urlPrefix = request.getScheme() + "://" + request.getServerName() + (80 == port ? "" : "");
        String uri = request.getRequestURI();
        if ("/".equals(uri) || uri.equals(errorPage) || StringUtils.startsWith(referer, urlPrefix) || urlPrefix.contains(TzgConstant.TZG_DOMAIN_NAME)) {
            return true;
        }
        if (null != excludeUrls && excludeUrls.length > 0) {
            for (int i = 0; i < excludeUrls.length; i++) {
                if ( StringUtils.startsWith(referer, excludeUrls[i]) || StringUtils.startsWith(urlPrefix, excludeUrls[i])) {
                    return true;
                }
            }
        }
        logger.info("csrf token paramVal=[{}],sessionVal=[{}]", paramValue, sessionVal);
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "非法请求!");
//        response.sendRedirect(request.getContextPath()+errorPage);
        return false;
    }

    private String getCookieVal(HttpServletRequest request, String tokenName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            if (tokenName.equals(name)) {
                return cookie.getValue();
            }
        }
        return "";
    }

    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

    }

    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
    
    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}