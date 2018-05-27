package com.tzg.wap.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tzg.common.utils.CookieUtil;
import com.tzg.common.utils.MD5Util;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.wap.utils.HttpUtil.HttpResp;


/**
 * 把用户对象放入session和从session获取用户对象
 */
public class WapSessionUtil
{
    public static void setSession(String key,Object obj)
    {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(true);
        session.setAttribute(key, obj);
    }   
    
    public static Object getSession(String key)
    {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if(session == null) {
            return null;    
        }
        Object obj = (Object) session.getAttribute(key);
        return obj;
    }
    
     public static void removeSession(String key) {
            HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            session.removeAttribute(key);
    }
	/**
	* 把用户对象放入session
	* @param LoginAccount     
	*     
	*/
	public static void setLoginSession(Loginaccount account)
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		//删除原来的登录信息
		removeLoginSession();
		session.setAttribute("wapLoginSessionInfo", account);
		session.setAttribute("_userid", SHAUtil.encode(account.getId().toString() + TzgConstant.LOGIN_SIGN_KEY));
	}
	
	/**
	 * 从session中获取用户对象
	 * @return ConsoleLoginAccount
	 */
	public static Loginaccount getLoginSession()
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        if(session == null) {
        	return null;	
        }
        Loginaccount account = (Loginaccount) session.getAttribute("wapLoginSessionInfo");
		return account;
	}
	
	/**
     * 清除当前用户
     */
    public static void removeLoginSession() {
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        session.removeAttribute("wapLoginSessionInfo");
        session.removeAttribute("_userid");
    }
    
    /**
     * 验证手机动态码 SESSION
     * @param phoneNumber
     * @param code
     */
    public static void setDynamicValidateCodeSession(String phoneNumber,String code)
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		session.setAttribute("DYNAMIC_VALIDATE_CODE", phoneNumber+code);
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
        String dynamicValidateCode = (String) session.getAttribute("DYNAMIC_VALIDATE_CODE");
		if(dynamicValidateCode!=null && dynamicValidateCode.equalsIgnoreCase(phoneNumber+code)){
			return true;
		}
        return false;
	}
	
	public static void removeDynamicValidateCodeSession(String phoneNumber) {
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        session.removeAttribute("DYNAMIC_VALIDATE_CODE");
        session.removeAttribute("SET_CODE_TIME_"+phoneNumber);
    }
	
	/**
     * 获取指定手机是否可以重发验证码的标识
     * 以手机号码为主键，同一个手机号码，一分钟内只能发送一次
     * @return
     */
    public static Boolean isSendDynamicValidateCode(String phone) {
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        Long lastTime = (Long) session.getAttribute("SET_CODE_TIME_"+phone);
        if (null != lastTime) {
        	Long dis = (new Date().getTime() - lastTime)/1000;
        	if(dis < 60){
        		return true;
        	}
        } 
        return false;
    }
    
    /**
	 * 微信登录控制
	 * @param request
	 * @param response
	 * @param loginAccountId
	 */
	public static void wechatLogin(HttpServletRequest request,HttpServletResponse response,Integer loginAccountId){
		String uid = loginAccountId+"";
		String openid = (String)request.getSession().getAttribute("wechat_openid");
		if(openid != null){
			//保存cookie
			CookieUtil.addCookie(response, "wechat_login_info_uid", uid, 2592000);
			//异步通知绑定成功
			String notifyUrl = (String)request.getSession().getAttribute("wechat_notifyUrl");
			Map<String,String> params = new HashMap<String,String>();
			params.put("openid", openid);
			params.put("uid", uid);
			params.put("sign", MD5Util.md5Hex(openid+TzgConstant.WECHAT_KEY));
			for(int i=0;i<3;i++){
				HttpResp httpResp = HttpUtil.post(notifyUrl, params);
				if(httpResp.getStatus().equals("200")){
					break;
				}
			}
		}
	}
}
