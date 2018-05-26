package com.tzg.core.utils;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tzg.entitys.leopard.console.ConsoleLoginAccount;

/**
 * 把用户对象放入session和从session获取用户对象
 */
public class HttpSessionUtil
{
	/**
	* 把用户对象放入session
	* @param ConsoleLoginAccount     
	*     
	*/
	public static void setLoginSession(ConsoleLoginAccount account)
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		session.setAttribute("loginSessionInfo", account);
	}
	
	/**
	 * 从session中获取用户对象
	 * @return ConsoleLoginAccount
	 */
	public static ConsoleLoginAccount getLoginSession()
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        if(session == null) {
        	return null;	
        }
        ConsoleLoginAccount account = (ConsoleLoginAccount) session.getAttribute("loginSessionInfo");
		return account;
	}
	
	/**
     * 清除当前用户
     */
    public static void removeLoginSession() {
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        session.removeAttribute("loginSessionInfo");
    }
    
   
	public static void setResourceSession(List<String> resources)
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		session.setAttribute("resourceSessionInfo", resources);
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<String> getResourceSession()
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        if(session == null) {
        	return null;	
        }
        List<String> resources = (List<String>) session.getAttribute("resourceSessionInfo");
		return resources;
	}
	
	
    public static void removeResourceSession() {
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        session.removeAttribute("resourceSessionInfo");
    }
    
    
    /**
     * 设置手机动态码 SESSION
     * @param phoneNumber
     * @param code
     */
    public static void setDynamicValidateCodeSession(String phoneNumber,String code)
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		session.setAttribute("dynamicValidateCode", phoneNumber+code);
		//用于记录发送时间
		session.setAttribute("SET_CODE_TIME_"+phoneNumber,new Date().getTime());
	}
	
	/**
	 * 验证手机动态码是否正确且和手机匹配
	 * @param phone
	 * @param code
	 * @return
	 */
	public static boolean verifyDynamicValidateCode(String phoneNumber,String code)
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        if(session == null) {
        	return false;	
        }
        String dynamicValidateCode = (String) session.getAttribute("dynamicValidateCode");
		if(dynamicValidateCode!=null && dynamicValidateCode.equalsIgnoreCase(phoneNumber+code)){
			return true;
		}
        return false;
	}
   	
   	
   	public static void removeDynamicValidateCodeSession() {
       	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
   		HttpSession session = request.getSession();
           session.removeAttribute("dynamicValidateCode");
       }

}
