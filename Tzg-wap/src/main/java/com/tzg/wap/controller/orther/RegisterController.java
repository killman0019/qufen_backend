package com.tzg.wap.controller.orther;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.Assert;
import com.tzg.common.utils.CookieUtil;
import com.tzg.common.utils.EnumConstant.SmsBuss;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.common.utils.wap.WapConstants;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.facade.rmi.RmiFacade;
import com.tzg.rmi.service.SystemParamRmiService;
import com.tzg.wap.utils.WapSessionUtil;

@Controller
public class RegisterController extends BaseController {
	private static final Log LOG = LogFactory.getLog(RegisterController.class);

	@Autowired
	private SystemParamRmiService systemParamRmiService;
	@Autowired
	private RedisService redisService;
	@Autowired
    private RmiFacade rmiFacade;
	
	@RequestMapping("/register/page")
	public String page(Model model,HttpServletRequest req){
		return "/registerphone";
	}
		
	/**
	 * 注册 用于落地页
	 * @param registerRequest
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> register(RegisterRequest registerRequest,HttpServletRequest req,HttpServletResponse res,Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(registerRequest.getPhoneNumber(), "手机号不能为空");
			Assert.isBlank(registerRequest.getPassword(), "密码不能为空");
			Assert.isBlank(registerRequest.getConfirmPassword(), "确认密码不能为空");
			Assert.isBlank(registerRequest.getDynamicVerifyCode(), "手机动态验证码不能为空");
			Assert.isTrue(isBlank(registerRequest.getAgreeProtocol()) || !"true".equals(registerRequest.getAgreeProtocol()), "不同意注册协议");
			if(!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())){
				Assert.isTrue(true,"两次输入密码不一致");
			}
			
			//效验验证码
//			Assert.notTrue(WapSessionUtil.verifyDynamicValidateCode(registerRequest.getPhoneNumber(),registerRequest.getDynamicVerifyCode()),"手机动态验证码错误");
			Assert.notTrue(this.verifyDynamicValidateCode(registerRequest.getPhoneNumber(), registerRequest.getDynamicVerifyCode(), SmsBuss.注册效验码.getBus()),"手机动态验证码错误");

			
			//记录来源  
			String suc = (String)req.getSession().getAttribute(TzgConstant.SOURCE_STORE_KEY);
			if(StringUtils.isBlank(suc)){
				String surcByCookie = CookieUtil.getCookieValueByName(req, TzgConstant.SOURCE_STORE_KEY);
				suc = StringUtils.isNotBlank(surcByCookie)&&surcByCookie.indexOf("fbaba")!=-1 ?surcByCookie:null;
			}
			
			if(StringUtils.isNotBlank(suc)){
				registerRequest.setRegisterSource(suc);
			}
			
			//有邀请码的他的推荐渠道就是为人人赚
			if(StringUtils.isNotBlank(registerRequest.getInviteCode())){
				registerRequest.setRegisterSource(TzgConstant.RENRENZHUAN);
			}
			
			
			registerRequest.setRegisterDevice("wap");
            Loginaccount loginaccount = rmiFacade.register(registerRequest);
			//保存登录信息至session
			WapSessionUtil.setLoginSession(loginaccount);
			// 释放验证码
//			WapSessionUtil.removeDynamicValidateCodeSession(registerRequest.getPhoneNumber());
			this.redisService.del(new StringBuffer(WapConstants.key_wap).append(SmsBuss.注册效验码.getBus()).append(registerRequest.getPhoneNumber()).toString());
				
			//释放cookie
			CookieUtil.remove(req, res, TzgConstant.SOURCE_STORE_KEY);
			
			//跳转
			String redirectUrl = (String)req.getSession().getAttribute("LOGIN_AFTER_REDIRECT_URL");
			if(StringUtils.isNotBlank(redirectUrl)){
				req.getSession().removeAttribute("LOGIN_AFTER_REDIRECT_URL");
	        }
			if (StringUtils.isBlank(redirectUrl)) {
				redirectUrl = "/registerSuccess" ;
				
			}
			map.put(TzgConstant.REDIRECT_URL,redirectUrl);
			
			//微信判断
			WapSessionUtil.wechatLogin(req, res, loginaccount.getId());
			map.put(TzgConstant.SUCCESS, true);
			
		} catch (Exception e) {
			LOG.error("", e);
            map.put(TzgConstant.SUCCESS, false);
            map.put(TzgConstant.MESSAGE, e.getMessage());
		}
		return map;
	}
	/**
	 * 注册
	 * @param registerRequest
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/registernew",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerNew(RegisterRequest registerRequest,HttpServletRequest req,HttpServletResponse res,Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isTimeOut =false;
		try {
			Assert.isBlank(registerRequest.getPassword(), "密码不能为空");
			Assert.isTrue(isBlank(registerRequest.getAgreeProtocol()) || !"true".equals(registerRequest.getAgreeProtocol()), "不同意注册协议");
			
			String phoneNamber= (String) req.getSession().getAttribute("phone_number");
			String validCode=(String) req.getSession().getAttribute("valid_code");
			if(phoneNamber==null || validCode==null){
				isTimeOut =true;
				Assert.isTrue(isTimeOut,"操作超时，请重新注册");
			}
			registerRequest.setPhoneNumber(phoneNamber);
			registerRequest.setDynamicVerifyCode(validCode);
			//记录来源   当没有推荐码的时候
			String suc = (String)req.getSession().getAttribute(TzgConstant.SOURCE_STORE_KEY);
			if(StringUtils.isBlank(suc)){
				String surcByCookie = CookieUtil.getCookieValueByName(req, TzgConstant.SOURCE_STORE_KEY);
				suc = StringUtils.isNotBlank(surcByCookie)&&surcByCookie.indexOf("fbaba")!=-1 ?surcByCookie:null;
			}
			if(StringUtils.isNotBlank(suc)){
				registerRequest.setRegisterSource(suc);
			}
			
			//有邀请码的他的推荐渠道就是为人人赚
			if(StringUtils.isNotBlank(registerRequest.getInviteCode())){
				registerRequest.setRegisterSource(TzgConstant.RENRENZHUAN);
			}
			
			
			registerRequest.setRegisterDevice("wap");
			Loginaccount loginaccount = rmiFacade.register(registerRequest);
			//保存登录信息至session
			WapSessionUtil.removeLoginSession();
			WapSessionUtil.setLoginSession(loginaccount);
			// 释放验证码
//			WapSessionUtil.removeDynamicValidateCodeSession(registerRequest.getPhoneNumber());
			this.redisService.del(new StringBuffer(WapConstants.key_wap).append(SmsBuss.注册效验码.getBus()).append(registerRequest.getPhoneNumber()).toString());
			
			//释放cookie
			CookieUtil.remove(req, res, TzgConstant.SOURCE_STORE_KEY);
			
			//清除Session
			if(null!=phoneNamber || !"".equals(phoneNamber)){
				req.getSession().removeAttribute("phone_number");
			}
			if(null != validCode || !"".equals(validCode)){
				req.getSession().removeAttribute("valid_code");
			}
			//跳转
			String redirectUrl = (String)req.getSession().getAttribute("LOGIN_AFTER_REDIRECT_URL");
			if(StringUtils.isNotBlank(redirectUrl)){
				req.getSession().removeAttribute("LOGIN_AFTER_REDIRECT_URL");
	        }
			if (StringUtils.isBlank(redirectUrl)) {
				redirectUrl = "/registerSuccess" ;
				
			}
			map.put(TzgConstant.REDIRECT_URL,redirectUrl);
			//微信判断
			WapSessionUtil.wechatLogin(req, res, loginaccount.getId());
			map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
			LOG.error("", e);
            map.put(TzgConstant.SUCCESS, false);
            if(isTimeOut){
            	map.put(TzgConstant.REDIRECT_URL, "/register/page");
            }
            map.put(TzgConstant.MESSAGE, e.getMessage());
		}
		return map;
	}
	
	@RequestMapping(value="/registervalidphone",method=RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> registerPhoneNumber(RegisterRequest registerRequest,HttpServletRequest req,Model model){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(registerRequest.getPhoneNumber(), "手机号不能为空");
			char[] phone=registerRequest.getPhoneNumber().toCharArray();
			if(!String.valueOf(phone[0]).equals("1")){
				Assert.isTrue(true,"请输入正确的手机号");
			}
			int phonekey = Integer.parseInt(String.valueOf(phone[1]));
			switch (phonekey) {
			case 0:case 1:case 2:case 6:case 9:
				Assert.isTrue(true,"请输入正确的手机号");
				break;
			default:
				break;
			}
			req.getSession().setAttribute("phone_number", registerRequest.getPhoneNumber());
			map.put(TzgConstant.REDIRECT_URL,"/registervalid");
			map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
			LOG.error("", e);
			map.put(TzgConstant.SUCCESS, false);
			map.put(TzgConstant.MESSAGE, e.getMessage());
		}	
		return map;
	}
	
	/**
	 * 注册跳转短信验证页面
	 * @param registerRequest
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/registervalid")
	public String registerValid(RegisterRequest registerRequest,HttpServletRequest req,Model model) throws Exception{
		String token = UUID.randomUUID().toString();
		req.getSession().setAttribute("captcha_token", token);
		String protocolUrl = systemParamRmiService.findByCode("registration_protocol_url").getVcParamValue();
		model.addAttribute("token",token);
		model.addAttribute("protocolUrl", protocolUrl);
		return "/registervalid";
	}
	
	@RequestMapping(value="/registervalidcode",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerValidCode(RegisterRequest registerRequest,HttpServletRequest req,HttpServletResponse res,Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(registerRequest.getDynamicVerifyCode(), "手机动态验证码不能为空");
//			Assert.notTrue(WapSessionUtil.verifyDynamicValidateCode(registerRequest.getPhoneNumber(),registerRequest.getDynamicVerifyCode()),"手机动态验证码错误");
			Assert.notTrue(this.verifyDynamicValidateCode(registerRequest.getPhoneNumber(), registerRequest.getDynamicVerifyCode(), SmsBuss.注册效验码.getBus()),"手机动态验证码错误");
			req.getSession().setAttribute("valid_code", registerRequest.getDynamicVerifyCode());
			map.put(TzgConstant.REDIRECT_URL,"/registerfrom");
			map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
			LOG.error("", e);
			map.put(TzgConstant.SUCCESS, false);
			map.put(TzgConstant.MESSAGE, e.getMessage());
		}
		return map;
	}
	/**
	 * 注册---跳转注册主界面
	 * @return
	 */
	@RequestMapping("/registerfrom")
	public String registerfrom(Model model, HttpServletRequest req){
		String toURL = (String)req.getSession().getAttribute("toURL");
		model.addAttribute("toURL", toURL);
		return "/register";
	}
	
	/**
	 * 注册成功
	 * @return
	 */
	@RequestMapping("/registerSuccess")
	public String registerSuccess(){
		return "/registerSuccess";
	}
	
	/**
	 * 验证手机号码
	 * @param phoneNumber
	 * @return
	 */
	@RequestMapping("/register/validPhone")
	@ResponseBody
	public Map<String,Object> validPhone(String phoneNumber){
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(TzgConstant.SUCCESS, false);
		}
		return map;
	}	
	 /**
	  * 记录推荐及来源链接，并存储推荐参数
	  * @param req
	  * @param key 推荐码
	  * @param sur 来源
	  * @return
	  */
	@RequestMapping("/r")
	public String r(HttpServletRequest req,
			@RequestParam(required=false)String key){
		if(StringUtils.isNotBlank(key)){
			req.getSession().setAttribute(TzgConstant.RECOMMEND_LINK_KEY, key);
			return "redirect:/register/invite?key="+key;
		}
		return "/registerphone";
	}
	
	
	/**
	 * 邀请好友注册
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping("/register/invite")
	public String invite(HttpServletRequest req,String key,Model model){
		try{
			if(StringUtil.isBlank(key)){
				Assert.isTrue(true,"邀请码不能为空");
			}
			Loginaccount loginaccount =null;
			if(loginaccount!=null){
				String phone = loginaccount.getVcPhone();
				String invitePhone= phone.substring(0,phone.length()-(phone.substring(3)).length())+"****"+phone.substring(7);
				req.getSession().setAttribute(TzgConstant.RECOMMEND_LINK_KEY, key);
				model.addAttribute("inviteCode", key);
				model.addAttribute("invitePhone",invitePhone);
			}else{
				Assert.isTrue(true,"邀请人不存在");
			}
		}catch(Exception e){
			LOG.error("", e);
		}
		return "/topics/register20151130/index";
	}
	
	/**
	 * 效验手机号码
	 * @param registerRequest
	 * @return
	 */
	@RequestMapping(value="/register/invitePhone",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerInvitePhome(RegisterRequest registerRequest){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			Assert.isBlank(registerRequest.getPhoneNumber(), "手机号不能为空");
			Assert.isTrue(isBlank(registerRequest.getAgreeProtocol()) || !"true".equals(registerRequest.getAgreeProtocol()), "不同意注册协议");
			char[] phone=registerRequest.getPhoneNumber().toCharArray();
			if(!String.valueOf(phone[0]).equals("1")){
				Assert.isTrue(true,"请输入正确的手机号");
			}
			int phonekey = Integer.parseInt(String.valueOf(phone[1]));
			switch (phonekey) {
			case 0:case 1:case 2:case 6:case 9:
				Assert.isTrue(true,"请输入正确的手机号");
				break;
			default:
				break;
			}

			
			map.put("registerRequest",registerRequest);
			map.put(TzgConstant.SUCCESS, true);
			map.put(TzgConstant.REDIRECT_URL, "/register/invitePassword");
		}catch(Exception e){
			LOG.error(e.getMessage());
			map.put(TzgConstant.SUCCESS, false);
		}
		return map;
	}
	
	/**
	 * 跳转注册密码输入页面
	 * @param registerRequest
	 * @param model
	 * @return
	 */
	@RequestMapping("/register/invitePassword")
	public String invitePassword(RegisterRequest registerRequest,Model model){
		model.addAttribute("registerRequest", registerRequest);
		return "/topics/register20151130/registerPassword";
	}
	
	/**
	 * 注册主页面
	 * @param registerRequest
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/register/inviteIndex",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inviteIndex(RegisterRequest registerRequest,HttpServletRequest req,HttpServletResponse res){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
		Assert.isBlank(registerRequest.getPassword(), "密码不能为空");
		Assert.isBlank(registerRequest.getDynamicVerifyCode(), "手机动态验证码不能为空");
		Assert.notTrue(this.verifyDynamicValidateCode(registerRequest.getPhoneNumber(), registerRequest.getDynamicVerifyCode(), SmsBuss.注册效验码.getBus()),"手机动态验证码错误");
	
		//记录来源   当没有推荐码的时候
		String suc = (String)req.getSession().getAttribute(TzgConstant.SOURCE_STORE_KEY);
		if(StringUtils.isBlank(suc)){
			String surcByCookie = CookieUtil.getCookieValueByName(req, TzgConstant.SOURCE_STORE_KEY);
			suc = StringUtils.isNotBlank(surcByCookie)&&surcByCookie.indexOf("fbaba")!=-1 ?surcByCookie:null;
		}
		
        if(StringUtils.isNotBlank(suc)){
            registerRequest.setRegisterSource(suc);
        }
        
        //有邀请码的他的推荐渠道就是为人人赚
        if(StringUtils.isNotBlank(registerRequest.getInviteCode())){
            registerRequest.setRegisterSource(TzgConstant.RENRENZHUAN);
        }
        
		
		
		//有邀请码的他的推荐渠道就是为人人赚
		if(StringUtils.isNotBlank(registerRequest.getInviteCode())){
			registerRequest.setRegisterSource(TzgConstant.RENRENZHUAN);
		}
		
		registerRequest.setRegisterDevice("wap");
		Loginaccount loginaccount = rmiFacade.register(registerRequest);
		//保存登录信息至session
		WapSessionUtil.removeLoginSession();
		WapSessionUtil.setLoginSession(loginaccount);
		// 释放验证码
		this.redisService.del(new StringBuffer(WapConstants.key_wap).append(SmsBuss.注册效验码.getBus()).append(registerRequest.getPhoneNumber()).toString());
		//释放cookie
		CookieUtil.remove(req, res, TzgConstant.SOURCE_STORE_KEY);
		
		if(loginaccount!=null){
			map.put("key",String.format("%06d", loginaccount.getId()));
		}
		map.put(TzgConstant.REDIRECT_URL,"/register/inviteShare");
		map.put(TzgConstant.SUCCESS, true);
		
		}catch(Exception e){
			LOG.error(e.getMessage());
			map.put(TzgConstant.SUCCESS, false);
		}
		return map;
	}
	
	/**
	 * 跳转注册成功页面
	 * @param key
	 * @param model
	 * @return
	 */
	@RequestMapping("/register/inviteShare")
	public String inviteShare(String key,Model model){
		model.addAttribute("key", key);
		return "/topics/register20151130/share";
	}
	
	
	
	
}
