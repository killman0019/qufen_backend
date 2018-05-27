package com.tzg.wap.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.Assert;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.common.utils.wap.WapConstants;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.rmi.service.SmsSendRmiService;
import com.tzg.rmi.service.SystemParamRmiService;
import com.tzg.wap.utils.CaptchPictureUtil;
import com.tzg.wap.utils.WapSessionUtil;

@RequestMapping("/dynamicValidateCode")
@Controller
public class DynamicValidateCodeController extends BaseController {
	private static final Log LOG = LogFactory.getLog(DynamicValidateCodeController.class);
	
	@Autowired
	private SmsSendRmiService smsSendRmiService;
	@Autowired
	private SystemParamRmiService systemParamRmiService;
	@Autowired
	private RedisService redisService;
	
	/**
	 * 发送手机动态码
	 * @param loginname
	 * @param bus
	 * @return
	 */
	@RequestMapping("/send")
	@ResponseBody
	public Map<String, Object> send(String phone,@RequestParam(required=false)String bus){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(phone, "手机号不能为空");
			Assert.isTrue(!RegexUtil.match(phone, RegexUtil.PHONEREGEX) , "请输入正确的手机号");
			Assert.isTrue(WapSessionUtil.isSendDynamicValidateCode(phone), "请勿频繁发送");
			String cacheKey = new StringBuffer(WapConstants.key_wap).append(bus).append(phone).toString();
			String cacheCode = this.redisService.get(cacheKey);
			String dynamicValidateCode = cacheCode;
			if(StringUtils.isBlank(cacheCode)){
				dynamicValidateCode = RandomUtil.produceNumber(6)+"";
				this.redisService.put(cacheKey,dynamicValidateCode, getValidateCodeLife()*60);//有效期
			}
			WapSessionUtil.setDynamicValidateCodeSession(phone, dynamicValidateCode);
			System.out.println("dynamicValidateCode:wap:"+bus+":"+dynamicValidateCode);
			smsSendRmiService.sendMSG(phone, dynamicValidateCode, bus);
			map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
			LOG.error("", e);
			map.put(TzgConstant.SUCCESS, false);
			map.put(TzgConstant.MESSAGE, e.getMessage());
		}
		
		return map;
	}
	
	@RequestMapping("/sendwithCaptcha")
	@ResponseBody
	public Map<String, Object> sendwithCaptcha(HttpServletRequest req,String phone,@RequestParam(required=false)String bus,@RequestParam(required=false)String validcode,String token){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(phone, "手机号不能为空");
			String captcha_token=(String)req.getSession().getAttribute("captcha_token");
			String token1 = UUID.randomUUID().toString();
			
			if(StringUtils.isBlank(captcha_token)||StringUtils.isBlank(token)||!token.equals(captcha_token)){
				req.getSession().setAttribute("captcha_token", token1);
				map.put("token",token1);
				Assert.isTrue(true,"请重新输入证码");
			}else{
				req.getSession().setAttribute("captcha_token", token1);
				map.put("token",token1);
			}
			int count=smsSendRmiService.findSendMSGCount(phone,30);
			boolean isShowPicCaptch = false;
			if(count>=2){
				isShowPicCaptch=true;
			}
			map.put("isShowPicCaptch", isShowPicCaptch);
			boolean a=false;
			if(StringUtil.isNotEmpty(validcode)){
				String captchaCode = req.getSession().getAttribute("captchaCode").toString();
				boolean picCaptchaWrong=false;
				picCaptchaWrong=!validcode.toLowerCase().equals(captchaCode.toLowerCase());
				map.put("picCaptchaWrong", picCaptchaWrong);
				Assert.isTrue(picCaptchaWrong, "图片验证码不正确");
				a=true;
			}
			Assert.isTrue(isShowPicCaptch&&!a, "请输入图片验证码");
			Assert.isTrue(WapSessionUtil.isSendDynamicValidateCode(phone), "请勿频繁发送");
			String cacheKey = new StringBuffer(WapConstants.key_wap).append(bus).append(phone).toString();
			String cacheCode = this.redisService.get(cacheKey);
			String dynamicValidateCode = cacheCode;
			if(StringUtils.isBlank(cacheCode)){
				dynamicValidateCode = RandomUtil.produceNumber(6)+"";
				this.redisService.put(cacheKey,dynamicValidateCode, getValidateCodeLife()*60);//有效期
			}
			WapSessionUtil.setDynamicValidateCodeSession(phone, dynamicValidateCode);
			System.out.println("dynamicValidateCode:wap:"+bus+":"+dynamicValidateCode);
			smsSendRmiService.sendMSG(phone, dynamicValidateCode, bus);
			map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
			LOG.error("", e);
			map.put(TzgConstant.SUCCESS, false);
			map.put(TzgConstant.MESSAGE, e.getMessage());
		}
		
		return map;
	}
	
	/**
	 * 发送 给已存在用户
	 * @param loginname
	 * @param bus
	 * @return
	 */
	@RequestMapping("/sendToExistAccount")
	@ResponseBody
	public Map<String, Object> sendToExistAccount(String phone,@RequestParam(required=false)String bus){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Assert.isBlank(phone, "手机号不能为空");
			Loginaccount loginaccount = null;
			Assert.isNull(loginaccount, "手机号码不存在,请确认");
			Assert.isTrue(WapSessionUtil.isSendDynamicValidateCode(phone), "请勿频繁发送");
			String cacheKey = new StringBuffer(WapConstants.key_wap).append(bus).append(phone).toString();
			String cacheCode = this.redisService.get(cacheKey);
			String dynamicValidateCode = cacheCode;
			if(StringUtils.isBlank(cacheCode)){
				dynamicValidateCode = RandomUtil.produceNumber(6)+"";
				this.redisService.put(cacheKey,dynamicValidateCode, getValidateCodeLife()*60);//有效期
			}
			WapSessionUtil.setDynamicValidateCodeSession(phone, dynamicValidateCode);
			System.out.println("dynamicValidateCode:wap:"+bus+":"+dynamicValidateCode);
			smsSendRmiService.sendMSG(phone, dynamicValidateCode, bus);
			map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
			LOG.error("", e);
			map.put(TzgConstant.SUCCESS, false);
			map.put(TzgConstant.MESSAGE, e.getMessage());
		}
		return map;
	}
	
	/**
	 * 发送 给登录账户
	 * @param bus
	 * @return
	 */
	@RequestMapping("/sendByLoginAccount")
	@ResponseBody
	public Map<String, Object> sendByLoginAccount(@RequestParam(required=false)String bus){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Loginaccount loginaccount = null;
			Assert.isNull(loginaccount, "未登录");
			Assert.isTrue(WapSessionUtil.isSendDynamicValidateCode(loginaccount.getVcPhone()), "请勿频繁发送");
			String cacheKey = new StringBuffer(WapConstants.key_wap).append(bus).append(loginaccount.getVcPhone()).toString();
			String cacheCode = this.redisService.get(cacheKey);
			String dynamicValidateCode = cacheCode;
			if(StringUtils.isBlank(cacheCode)){
				dynamicValidateCode = RandomUtil.produceNumber(6)+"";
				this.redisService.put(cacheKey,dynamicValidateCode, getValidateCodeLife()*60);//有效期
			}
			WapSessionUtil.setDynamicValidateCodeSession(loginaccount.getVcPhone(), dynamicValidateCode);
			System.out.println("dynamicValidateCode:wap:"+bus+":"+dynamicValidateCode);
			smsSendRmiService.sendMSG(loginaccount.getVcPhone(), dynamicValidateCode, bus);
			map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
			LOG.error("", e);
			map.put(TzgConstant.SUCCESS, false);
			map.put(TzgConstant.MESSAGE, e.getMessage());
		}
		
		return map;
	}
	
	
	/**
	 * 效验手机动态码
	 * @param code
	 * @return
	 */
	@RequestMapping("/verifyCodeWithNoLogin")
	@ResponseBody
	public Map<String, Object> verifyCodeWithNoLogin(String phoneNumber,String code,String bus){
		Map<String, Object> map = new HashMap<String, Object>();
        try {
        	//效验验证码
			boolean isValidateCode = this.verifyDynamicValidateCode(phoneNumber, code, bus);
			if(!isValidateCode){
				//释放验证码
				this.redisService.del(new StringBuffer(WapConstants.key_wap).append(bus).append(phoneNumber).toString());
				Assert.isTrue(true,"验证码错误,请重新获取");
			}
        	map.put(TzgConstant.SUCCESS, true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put(TzgConstant.SUCCESS, false);
			map.put(TzgConstant.MESSAGE, e.getMessage());
		}
        return map;
	}
	
	@RequestMapping("/showCaptchPic")
	public void showCaptchPic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CaptchPictureUtil c = new CaptchPictureUtil();
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(100, 40,
				BufferedImage.TYPE_INT_RGB);
		String randomCode = c.images(buffImg);
		// 将四位数字的验证码保存到Session中。
		HttpSession session = req.getSession();
		session.setAttribute("captchaCode", randomCode.toString());
		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");
		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}
	
	/**
	 * 验证码有效期默认15
	 * @return
	 * @throws Exception 
	 */
	private int getValidateCodeLife() throws Exception{
		SystemParam systemParam = systemParamRmiService.findByCode("validatecode_life");
		if(null != systemParam && StringUtils.isNumeric(systemParam.getVcParamValue())){
			return Integer.parseInt(systemParam.getVcParamValue());
		}
		return 15;
	}
}
