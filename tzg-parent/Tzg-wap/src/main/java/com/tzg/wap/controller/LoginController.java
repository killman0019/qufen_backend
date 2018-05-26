package com.tzg.wap.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.Assert;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.entitys.loginaccount.LoginUser;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.rmi.service.SystemParamRmiService;
import com.tzg.wap.utils.WapSessionUtil;

@Controller
public class LoginController extends BaseController {
	private static final Log LOG = LogFactory.getLog(LoginController.class);
	
	@Autowired
	private RedisService redisService;  
	@Autowired
	private SystemParamRmiService systemParamRmiService;

	@RequestMapping("/login/page")
	public String page(HttpServletRequest request, HttpServletResponse response, Model model,String toURL) throws IOException{
		if(StringUtil.isNotBlank(toURL)){
            toURL = java.net.URLDecoder.decode(toURL,"UTF-8").replaceAll("&amp;", "&");; //url 解码
			Enumeration<String> enu=request.getParameterNames();  
			while(enu.hasMoreElements()){  
				String paraName=(String)enu.nextElement();  
				if(!StringUtil.equals("toURL", paraName)){
					toURL = toURL +"&"+paraName + "=" +request.getParameter(paraName);
				}
			} 
			if(toURL.indexOf("?")>0){
				toURL = toURL+"&source=tzg";
	    	}else {
	    		toURL = toURL+"?source=tzg";
	    	}
			request.getSession().setAttribute("LOGIN_AFTER_REDIRECT_URL", toURL);
		}
		return "/login";
	}
	
	/**
	 * 登录  同步方式
	 * @param req
	 * @param rememberPassword
	 * @param loginName
	 * @param password
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest req,HttpServletResponse res,
			String loginName,String password,Model model){
		try {
			Assert.isBlank(loginName, "登录帐号不能为空");
			Assert.isBlank(password, "密码不能为空");
			Loginaccount loginaccount = null;
			Assert.isNull(loginaccount, "登录帐号或密码错误");
			Assert.isTrue(loginaccount.getIvalid().intValue()!=1, "此账户已冻结，请联系客服");
			//是否是投资人
			Assert.isTrue(loginaccount.getItype()!=2, "用户类型不对");
			//保存登录信息至session
			WapSessionUtil.setLoginSession(loginaccount);
			
			//跳转
			String url = (String)req.getSession().getAttribute("LOGIN_AFTER_REDIRECT_URL");
			if(StringUtils.isNotBlank(url)){
				req.getSession().removeAttribute("LOGIN_AFTER_REDIRECT_URL");
	        }
			if (StringUtils.isBlank(url)) {
				url = "/user/home";
			}
			//微信判断
			WapSessionUtil.wechatLogin(req, res, loginaccount.getId());
			return "redirect:"+url;
		} catch (Exception e) {
			LOG.error("", e);
            model.addAttribute(TzgConstant.SUCCESS, false);
			model.addAttribute(TzgConstant.MESSAGE, e.getMessage());
            return "redirect:/login/page";
		}
	}
	
	/**
	 * 登录  异步方式
	 * @param req
	 * @param loginName
	 * @param password
	 * @return
	 */
	@RequestMapping("/login.json")
	@ResponseBody
	public Map<String, Object> loginJson(HttpServletRequest req,HttpServletResponse res,
			String loginName,String password){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(loginName, "登录帐号不能为空");
			Assert.isBlank(password, "密码不能为空");
			Loginaccount loginaccount = null;
			Assert.isNull(loginaccount, "登录帐号或密码错误");
			Assert.isTrue(loginaccount.getIvalid().intValue()!=1, "此账户已冻结，请联系客服");
			//是否是投资人
			Assert.isTrue(loginaccount.getItype()!=2, "用户类型不对");
			//保存登录信息至session
			WapSessionUtil.setLoginSession(loginaccount);
			
			//跳转
			String url = (String)req.getSession().getAttribute("LOGIN_AFTER_REDIRECT_URL");
			if(StringUtils.isNotBlank(url)){
				req.getSession().removeAttribute("LOGIN_AFTER_REDIRECT_URL");
	        }
			if (StringUtils.isBlank(url)) {
				url = "/user/home";
			}

			map.put(TzgConstant.REDIRECT_URL, url);
			map.put(TzgConstant.SUCCESS, true);
			//微信判断
			WapSessionUtil.wechatLogin(req, res, loginaccount.getId());
		} catch (Exception e) {
			LOG.error("", e);
            map.put(TzgConstant.SUCCESS, false);
            map.put(TzgConstant.MESSAGE, e.getMessage());
		}
		return map;
	}
	
	/**
	 * 注销
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(){
		WapSessionUtil.removeLoginSession();
		return "redirect:/";
	}
	
	/**
	 * app 登录wap
	 * @param request
	 * @param response
	 * @param key 用户信息 用户id-签名
	 * @param toURL 跳转地址
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/login/appLogin")
	public void appLogin(String key, String toURL, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String wapPath = systemParamRmiService.getWapPath();
		try {
			Assert.isBlank(key, "用户信息不能为空");
			Assert.isBlank(toURL, "跳转地址不能为空");
			
			
			String[] keys = key.split("-");
			String sign = SHAUtil.encode(keys[0]+ TzgConstant.USERID_SIGN);
			if(StringUtil.equals(sign, keys[1])){
				
				String rediskey = SHAUtil.encode(keys[0] + TzgConstant.LOGIN_SIGN_KEY)+TzgConstant.TZG_REST;
				
				if(redisService.exists(rediskey)){
					Loginaccount loginaccount = null;
					//保存登录信息至session
					WapSessionUtil.setLoginSession(loginaccount);
					response.sendRedirect(toURL); 
					return;
				}
			
			}
			
		} catch (Exception e) {
			LOG.error("app登录wap错误"+e);
		}
		response.sendRedirect(wapPath); 
	}
	
	
	
	/**
	 * 根据用户的id获取用户的基本信息
	 */
	@RequestMapping("/getUser")
	@ResponseBody
	public Map<String, Object> getUser(String userid, String sign) {
		Map<String, Object> map = new HashMap<String,Object>();
		try {
			Assert.isBlank(userid, "用户id不能为空");
			Assert.isBlank(sign, "签名不能为空");
			if(StringUtil.equals(sign, SHAUtil.encode(userid+ TzgConstant.USERID_SIGN))){
				Loginaccount loginaccount = null;
				
				LoginUser user = new LoginUser(); //返回给其他用户的信息，包含的信息量较少
				
				user.setId(loginaccount.getId());
				user.setVcLoginName(loginaccount.getVcLoginName());
				//user.setVcPassword(loginaccount.getVcPassword());
				//user.setVcPhone(loginaccount.getVcPhone());
				
				map.put("user",user);
				return map;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
	/**
	 * 获取当前用户的id
	 */
	@RequestMapping("/jsonp")
	@ResponseBody
	public String jsonp(HttpServletRequest req, HttpServletResponse response) throws Exception {
		String userid = ""; //用户id
		String sign = "";   //加密签名
		//返回jsonp格式
		String callback = req.getParameter("callback");
		Loginaccount loginaccount = WapSessionUtil.getLoginSession();
		if(loginaccount != null ){
			userid = loginaccount.getId().toString();
			sign = SHAUtil.encode(userid+ TzgConstant.USERID_SIGN);
			
			return callback + "('"+userid+"-"+sign+"')";
			 
		}
		return callback + "(1)";
	}
	
	/**
	 * 验证redis
	 */
	@RequestMapping("/checkredis")
	@ResponseBody
	public String checkredis(String token) throws Exception {

		String re = "false";
		if(StringUtils.isBlank(token)){
			return re;
		}
		
		if(redisService.exists(token)){
			re="success";
		}
	
		return re;
	}
	
	/***
	 * 活动页登陆
	 */
	/**
	 * 验证redis
	 */
	@RequestMapping("/checkredis/{version}/{userid}")
	@ResponseBody
	public String checkredisByIloginId(@PathVariable String version,@PathVariable String userid) throws Exception {
		String re = "false";
		try {
			if(userid==null||StringUtils.isBlank(userid)){
				WapSessionUtil.removeLoginSession();
				return re;
			}
			//登陆jsonp返回判断
			String str[] = userid.split("-");
			if(str.length<2){
				WapSessionUtil.removeLoginSession();
				return re;
			}
			String sign = SHAUtil.encode(str[0]+ TzgConstant.USERID_SIGN);
			if(!sign.equals(str[1])){
				WapSessionUtil.removeLoginSession();
				return re;
			}
			 
			Loginaccount loginaccount=WapSessionUtil.getLoginSession();
			int iloginid=Integer.parseInt(str[0]);
			if(loginaccount!=null){
				if(loginaccount.getId()!=iloginid){
					WapSessionUtil.removeLoginSession();
					if(getLoginState(iloginid,version)){
						 re = "success";
						 loginaccount=null;
						 WapSessionUtil.setLoginSession(loginaccount);
					}
				}else{
					re="success";
				}	
			}else{
				if(getLoginState(iloginid,version)){
					 re = "success";
					 loginaccount=null;
					 WapSessionUtil.setLoginSession(loginaccount);
				}
			}
		} catch (Exception e) {
			WapSessionUtil.removeLoginSession();
		}
		return re;
	}
	
	/**
	 * 	判断redis是否存在用户信息 根据浏览器和用户信息
	 * @param iloginid
	 * @param version
	 * @return
	 * @throws Exception
	 */
	private boolean getLoginState(int iloginid,String version) throws Exception{
		boolean flag= false;
		String  token = SHAUtil.encode(iloginid + TzgConstant.LOGIN_SIGN_KEY);
		
		if(StringUtils.isBlank(token)){
			return flag;
		}
		
		if(StringUtils.isBlank(version)){
			@SuppressWarnings("unchecked")
			Set<String> set = (Set<String>) redisService.keys(token+"*");
			Iterator<String> it = set.iterator();  
			while (it.hasNext()) {  
			  String str = it.next();  
			  if(StringUtils.isNotBlank(str)&&str.startsWith(token)){
				  flag = true;
				  break;
			  }
			}  
		}else{
			if(redisService.exists(token+version)){
				 flag = true;
			}
		}
		return  flag ;
	}
	
}
