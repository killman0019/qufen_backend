package com.tzg.wap.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tzg.common.utils.Assert;
import com.tzg.common.utils.CookieUtil;
import com.tzg.common.utils.MD5Util;
import com.tzg.common.utils.TzgConstant;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.wap.utils.WapSessionUtil;

@Controller
@RequestMapping("/wechat")
public class WeChatController extends BaseController {
	private static final Log LOG = LogFactory.getLog(WeChatController.class);
	
	
	/**
	 * 微信绑定跳转
	 * @param openid
	 * @param notifyUrl
	 * @param sign
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toLogin", method = RequestMethod.POST )
	public void page(String openid,String notifyUrl,String redirectUrl,String sign,
			HttpServletRequest request,HttpServletResponse response){
		try {
			Assert.isTrue(!this.checkSign(openid, sign), "效验码错误");
			request.getSession().setAttribute("wechat_notifyUrl", notifyUrl);
			request.getSession().setAttribute("wechat_openid", openid);
			response.sendRedirect(redirectUrl.trim());
		} catch (Exception e) {
			LOG.info(e);
		}
	}
	
	/**
	 * 微信授权登录
	 * @param request
	 * @param response
	 * @param uid
	 * @param redirectUrl
	 * @param openid
	 * @param sign
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public void autologin(HttpServletRequest request,HttpServletResponse response,
			String uid,String redirectUrl,String openid,String sign){
		try {
			Assert.isBlank(uid, "用户ID不能为空");
			Assert.isBlank(openid, "openid不能为空");
			Assert.isBlank(sign, "效验码不能为空");
			Assert.isTrue(!this.checkSign(openid, sign), "签名验证失败");
			Loginaccount loginaccount = null;
			Assert.isNull(loginaccount, "用户不存在");
			Assert.isTrue(loginaccount.getIvalid().intValue()!=1, "此账户已冻结，请联系客服");
			//是否是投资人
			Assert.isTrue(loginaccount.getItype()!=2, "用户类型不对");
			//保存登录信息至session
			WapSessionUtil.setLoginSession(loginaccount);
			request.getSession().setAttribute("wechat_openid", openid);
			CookieUtil.addCookie(response, "wechat_login_info_uid", uid+"", 2592000);
			response.sendRedirect(redirectUrl.trim());
		} catch (Exception e) {
			LOG.info(e);
		}
	}
	
	/**
	 * 微信解绑
	 * @param req
	 * @param res
	 * @param uid
	 * @param redirectUrl
	 * @param openid
	 * @param sign
	 * @return
	 */
	@RequestMapping(value = "/unwrap", method = RequestMethod.POST)
	public void unwrap(HttpServletRequest req,HttpServletResponse res,
		String redirectUrl,String openid,String sign){
		try {
			Assert.isBlank(openid, "openid不能为空");
			Assert.isBlank(sign, "效验码不能为空");
			Assert.isTrue(!this.checkSign(openid, sign), "签名验证失败");
			WapSessionUtil.removeLoginSession();
			req.getSession().removeAttribute("wechat_openid");
			CookieUtil.remove(req, res, "wechat_login_info_uid");
			res.sendRedirect(redirectUrl.trim());
		} catch (Exception e) {
			LOG.info(e);
		}
	}
	
	/**
	 * 效验效验码
	 * @param openid
	 * @param sign
	 * @return
	 */
	private boolean checkSign(String openid,String sign){
		String s=MD5Util.md5Hex(openid+TzgConstant.WECHAT_KEY);
		if(s.equals(sign)){
			return true;
		}
		return false;
	}
	
	
}
