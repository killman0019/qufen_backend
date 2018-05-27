package com.tzg.wap.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.Assert;
import com.tzg.common.utils.EnumConstant.SmsBuss;
import com.tzg.common.utils.TzgConstant;
import com.tzg.common.utils.wap.WapConstants;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.wap.utils.WapSessionUtil;

@Controller
public class ForgetPasswordController extends BaseController {
    private static final String PREV_VALIDATION_KEY="PREV_VALIDATION_FLAG";

	@Autowired
	private RedisService redisService;
	
	@RequestMapping("/forgetPassword/page")
	public String page(Model model){
		return "/forgetPasswordFirst";
	}
	
	/**
	 * 忘记密码 第一步
	 * @param req
	 * @param phoneNumber
	 * @param dynamicVerifyCode
	 * @return
	 */
	@RequestMapping("/forgetPasswordFirst")
	@ResponseBody
	public Map<String, Object> forgetPasswordFirst(HttpServletRequest req,String phoneNumber,String dynamicVerifyCode){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(phoneNumber, "手机号码不能为空");
			Assert.isBlank(dynamicVerifyCode, "手机动态验证码不能为空");
			Loginaccount account = null;
			Assert.isNull(account,"手机号码不存在");
			Assert.isTrue(account.getItype()!=2,"手机端仅提供投资人使用");
			//效验验证码
			boolean isValidateCode = this.verifyDynamicValidateCode(phoneNumber, dynamicVerifyCode, SmsBuss.忘记密码效验码.getBus());
			//释放验证码
			this.redisService.del(new StringBuffer(WapConstants.key_wap).append(SmsBuss.忘记密码效验码.getBus()).append(phoneNumber).toString());
			
			Assert.notTrue(isValidateCode,"验证码错误,请重新获取");
			
			String token = UUID.randomUUID().toString();
			req.getSession().setAttribute("forgetPassword_token", token);
			req.getSession().setAttribute("forgetPassword_id", account.getId());
			//跳转
			map.put(TzgConstant.REDIRECT_URL, "/forgetPasswordSecond/page");
			//第一步的手机号验证通过,在session中设置标示位，方便第二步判断验证
			WebUtils.setSessionAttribute(req, PREV_VALIDATION_KEY, "1");
			map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
            map.put(TzgConstant.SUCCESS, false);
            map.put(TzgConstant.MESSAGE, e.getMessage());
		}
		return map;
	}
	
	
	@RequestMapping("/forgetPasswordSecond/page")
	public String forgetPasswordSecondPage(Model model){
	    //第一步的原手机号验证是否通过
        Object  object=WapSessionUtil.getSession(PREV_VALIDATION_KEY);
        if(null==object||!"1".equals(object)){
            return this.page(model);
        }
		return "/forgetPasswordSecond";
	}
	/**
	 * 忘记密码 第二步
	 * @param req
	 * @param newPassword
	 * @param confirmNewPassword
	 * @param token
	 * @return
	 */
	@RequestMapping("/forgetPasswordSecond")
	@ResponseBody
	public Map<String, Object> forgetPasswordSecond(HttpServletRequest req,String password,String confirmPassword,String token){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String forgetPasswordToken = (String)req.getSession().getAttribute("forgetPassword_token");
			if(StringUtils.isBlank(forgetPasswordToken)||StringUtils.isBlank(token)||!token.equals(forgetPasswordToken)){
				Assert.isTrue(true,"操作非法");
			}
			Assert.isBlank(password, "密码不能为空");
			Assert.isBlank(confirmPassword, "确认密码不能为空");
			if(!password.equals(confirmPassword)){
				Assert.isTrue(true,"两次输入密码不一致");
			}
			//更新密码
			Integer id = (Integer)req.getSession().getAttribute("forgetPassword_id");
			//loginAccountRmiService.updatePassword(id,password);
			//释放Session
			req.getSession().removeAttribute("forgetPassword_token");
			req.getSession().removeAttribute("forgetPassword_id");
			//跳转
			map.put(TzgConstant.REDIRECT_URL, "/forgetPasswordSuccess/page");
			// 第一步的手机号验证通过，移除session中的标示
            WapSessionUtil.removeSession(PREV_VALIDATION_KEY);
			map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
            map.put(TzgConstant.SUCCESS, false);
            map.put(TzgConstant.MESSAGE, e.getMessage());
		}
		return map;
	}
	
	@RequestMapping("/forgetPasswordSuccess/page")
	public String forgetPasswordSuccess(){
		return "/forgetPasswordSuccess";
	}
	
}
