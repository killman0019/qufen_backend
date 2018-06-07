package com.tzg.wap.controller.h5;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.Create2Code;
import com.tzg.common.utils.EnumConstant.SmsBuss;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.common.utils.rest.Base64Util;
import com.tzg.common.utils.rest.RestConstants;
import com.tzg.entitys.kff.authentication.Authentication;
import com.tzg.entitys.kff.collect.CollectPostResponse;
import com.tzg.entitys.kff.dareas.Dareas;
import com.tzg.entitys.kff.follow.FollowResponse;
import com.tzg.entitys.kff.model.UserModel;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserLogin;
import com.tzg.entitys.kff.userInvation.UserInvation;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.entitys.photo.PhotoIview;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.SystemParamRmiService;
import com.tzg.wap.utils.DateUtil;
import com.tzg.wap.utils.HexUtil;

@Controller(value = "KFFUserController")
@RequestMapping("/kff/user")
public class UserController extends BaseController {
	// 前台春晓的注册链接
	@Value("#{paramConfig['registerUrl']}")
	private String registerUrl;
	// "http://192.168.10.196:5000/user/registerSmp?invaUIH=";

	private static Logger log = Logger.getLogger(UserController.class);

	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private SystemParamRmiService systemParamRmiService;
	@Autowired
	private RedisService redisService;

	/**
	 * 用户注册后生成邀请链接 (免密码登陆**** 手机号码*** 图片生成验证码*** 手机验证码*** 发到手机的验证码 密码) 邀请码 app 端注册 adasdadad
	 * 
	 * @param phoneNumber
	 * @param checkCode
	 * @param phoneCode
	 * @param dynamicVerifyCode
	 * @param invaUserIdHex
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/registerInva", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity registerInva(HttpServletRequest response, HttpServletRequest request, String phoneNumber, String checkCode, String phoneCode,
			String dynamicVerifyCode, String invaUIH, String password) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 验证手机号的合法性
		if (null == phoneNumber) {
			throw new RestServiceException(RestErrorCode.PHONE_NULL);
		}
		String phonefmt = RegexUtil.PHONEREGEX;
		// 判断手机号码手机是否符合标注
		if (!phoneNumber.matches(phonefmt)) {
			throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
		}
		// 验证图片验证码的合法性
		if (null == phoneCode) {
			throw new RestServiceException(RestErrorCode.CHECK_CODE_IS_NULL);
		}
		// 输入的验证码格式不正确
		// ^[A-Za-z0-9]+$;
		String checkCodefmt = RegexUtil.CHECKCODE;
		if (!phoneCode.matches(checkCodefmt)) {
			throw new RestServiceException(RestErrorCode.CHECK_CODE_FORMAT_ERROR);
		}
		// 将输入的验证码和生成的验证码进行对比,

		if (!checkCode.equalsIgnoreCase(phoneCode)) {
			throw new RestServiceException(RestErrorCode.CHECK_CODE_ERROE);
		}
		// 验证手机是否已经注册
		KFFUser user = kffRmiService.findUserByPhoneNumber(phoneNumber);
		if (null != user) {
			throw new RestServiceException(RestErrorCode.PHONE_ALREADY_EXIST);

		}
		if (null == password) {
			throw new RestServiceException("登陆密码不能为空");
		}
		// 判断密码的合法性
		// String pwdFmt =
		// "^((?=.*?\\d)(?=.*?[A-Za-z])|(?=.*?\\d)(?=.*?[!@#$%^&])|(?=.*?[A-Za-z])(?=.*?[!@#$%^&]))[\\dA-Za-z!@#$%^&]{8,20}$";
		// //由字母、数字组成，8-20位
		String pwdFmt = "^.{8,20}$";
		if (!password.matches(pwdFmt)) {
			throw new RestServiceException(RestErrorCode.PASSWORD_FORMAT_ERROR);
		}

		// 校验手机验证 判断输入手机验证码和送到手机的验证码是否一致
		String cacheCode = null;
		try {
			String module = "register";
			// key_rest_sms_login15537791297sms
			String cacheKey = new StringBuffer(RestConstants.key_rest).append(module).append(phoneNumber).toString();
			cacheCode = redisService.get(cacheKey);
		} catch (Exception e) {

			logger.error("RegisterInvaController registerInva：", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		if (!dynamicVerifyCode.equals(cacheCode)) {

			map.put("reStatus", 0);// 1注册成功 0 注册不成功
			map.put("reason", "短信验证码输入不正确");
			bre.setData(map);

			return bre;
		}
		// 手机验证码成功 保存用户信息
		// 将邀请二维码字符进行转码转化成对应的userID
		Integer invaUserId = null;
		if (null != invaUIH) {
			invaUserId = HexUtil.code2ToUserId(invaUIH);
		}

		KFFUser KffUser = kffRmiService.saveUserByphonePass(phoneNumber, invaUserId, password);
		if (null == KffUser) {
			map.put("reStatus", 0);// 1注册成功 0 注册不成功
			map.put("reason", "此用户已注册");
			bre.setData(map);

			throw new RestServiceException("注册失败请稍后重试!");

		}
		if (null != KffUser) {
			map.put("reStatus", 1);// 注册成功
			// 根据用户的ID 生成token
			String token = AccountTokenUtil.getAccountToken(KffUser.getUserId());
			map.put("token", token);
			bre.setData(map);
			return bre;
		}
		// 1是成功
		return null;
	}

	/**
	 * 点击注册后跳转到url
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/createUrlInRegister", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity createUrlInRegister(HttpServletRequest request, HttpServletResponse response, String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		if (null == token) {
			throw new RestServiceException("参数错误,请重新注册!");
		}
		// 根据token获得userId
		Integer userId = AccountTokenUtil.decodeAccountToken(token);
		// 根据userID生成code2
		String userIdTo2code = HexUtil.userIdTo2code(userId);
		// 将生成的2code 放在数据库中
		kffRmiService.saveUserInvation(userId, userIdTo2code);
		// 生成URL注册链接
		String user2codeUrl = registerUrl + userIdTo2code;
		System.out.println(user2codeUrl);
		map.put("url", user2codeUrl);
		bre.setData(map);
		return bre;
	}

	/**
	 * 生成user 的相关海报
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/createPoster", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity createPoster(HttpServletRequest request, HttpServletResponse response, String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if (null == token) {
				throw new RestServiceException("参数错误,请重新注册!");
			}
			// 根据token获得userId
			Integer userId = AccountTokenUtil.decodeAccountToken(token);
			// 根据userID 的code2生成相关的海报存在相对应的位置
			// 查询数据库判断是否已经生成专属海报
			String posterUrl = null;
			UserInvation userInvation = kffRmiService.selectUseInvation(userId);
			if (StringUtils.isEmpty(userInvation.getUserposterpic())) {
				// 生成海报
				posterUrl = kffRmiService.creat2Code(userId);
				String code2Url = null;
				kffRmiService.updataUserInvation(userId, posterUrl, code2Url);
				map.put("url", posterUrl);
			} else {
				posterUrl = userInvation.getUserposterpic();
				map.put("url", posterUrl);
			}
		} catch (RestServiceException e) {
			logger.error("createPoster register：", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("createPoster register：", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		bre.setData(map);
		return bre;
	}

	/**
	 * 用户注册后产生邀请链接
	 * 
	 * @param request
	 * @param response
	 * @param phoneNumber
	 * @param password
	 * @param dynamicVerifyCode
	 * @return
	 */
	@RequestMapping(value = "/registerInvation", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity registerInvation(HttpServletRequest request, HttpServletResponse response, String phoneNumber, String password,
			String dynamicVerifyCode) {
		// 创建返回体
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			RegisterRequest registerRequest = new RegisterRequest();
			registerRequest.setPassword(password);
			registerRequest.setPhoneNumber(phoneNumber);
			registerRequest.setDynamicVerifyCode(dynamicVerifyCode);
			// 验证传入参数是否合法
			validateForm(registerRequest);
			KFFUser user = kffRmiService.registerRest(registerRequest);
			// 释放验证码
			this.redisService.del(new StringBuffer(RestConstants.key_rest).append(SmsBuss.注册效验码.getBus()).append(registerRequest.getPhoneNumber()).toString());
			// 全局公用的用户登录信息
			String key = SHAUtil.encode(user.getUserId() + TzgConstant.LOGIN_SIGN_KEY);
			redisService.put(key, key, 60 * 60 * 1); // 存放一个小时
			// 生成account token 根据用户的ID 生成唯一的token值
			String token = AccountTokenUtil.getAccountToken(user.getUserId());
			map.put("token", token);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("RegisterController register：", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RegisterController register：", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 用户注册 web电脑网页版
	 * 
	 * @param request
	 * @param response
	 * @param phoneNumber
	 * @param password
	 * @param dynamicVerifyCode
	 * @return
	 */
	@RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity register(HttpServletRequest request, HttpServletResponse response, String phoneNumber, String password, String dynamicVerifyCode) {
		// 创建返回体
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			RegisterRequest registerRequest = new RegisterRequest();
			registerRequest.setPassword(password);
			registerRequest.setPhoneNumber(phoneNumber);
			registerRequest.setDynamicVerifyCode(dynamicVerifyCode);
			// 验证传入参数是否合法
			validateForm(registerRequest);
			KFFUser user = kffRmiService.registerRest(registerRequest);

			// 释放验证码
			this.redisService.del(new StringBuffer(RestConstants.key_rest).append(SmsBuss.注册效验码.getBus()).append(registerRequest.getPhoneNumber()).toString());
			// 全局公用的用户登录信息
			String key = SHAUtil.encode(user.getUserId() + TzgConstant.LOGIN_SIGN_KEY);
			redisService.put(key, key, 60 * 60 * 1); // 存放一个小时

			// 生成account token 根据用户的ID 生成唯一的token值
			String token = AccountTokenUtil.getAccountToken(user.getUserId());
			// 将用户是否进行身份审核等信息存放在authentication表中
			kffRmiService.setUserCardAuthentication(user.getUserId(), phoneNumber);
			// 将用户信息插入认证表中
			kffRmiService.saveAuthenticationByUseId(user.getUserId());
			// 根据ID 生成token生成account token
			map.put("token", token);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("RegisterController register：", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RegisterController register：", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 验证手机号是否已经存在
	 * 
	 * @param request
	 * @param response
	 * @param phone
	 *            手机号
	 *
	 * @return
	 */
	@RequestMapping(value = "/register/phoneAvailable", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity phoneAvailable(HttpServletRequest request, HttpServletResponse response, String phone) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			Map<String, Object> resMap = new HashMap<String, Object>();
			if (StringUtils.isBlank(phone)) {
				new RestServiceException(RestErrorCode.PHONE_NULL);
			} else {
				String phonefmt = RegexUtil.PHONEREGEX;
				if (!phone.matches(phonefmt)) {
					new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
				}
			}
			if (kffRmiService.verifyLoginaccount("mobile", phone)) {
				resMap.put("isRegister", 1);// 已注册
			} else {
				resMap.put("isRegister", 0);// 未注册
			}
			bre.setData(resMap);
		} catch (RestServiceException e) {
			logger.error("RegisterController phoneAvailable reason:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RegisterController phoneAvailable, reason：{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		System.out.println(bre);
		return bre;
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @param loginName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity login(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject();

		// 验证密码是否符合规定
		if (StringUtils.isEmpty(data)) {
			throw new RestServiceException("登陆参数异常,请联系客服!");
		}
		KFFUserLogin userLogin = JSON.parseObject(data, KFFUserLogin.class);
		String password = userLogin.getPassword();
		String loginName = userLogin.getLoginName();
		jsonObject.put("password", password);
		try {
			if (StringUtils.isBlank(loginName)) {
				throw new RestServiceException(RestErrorCode.LOGIN_NAME_NULL);
			}
			if (StringUtils.isBlank(password)) {
				throw new RestServiceException(RestErrorCode.PASSWORD_NULL);
			}
			// 验证密码合法性
			String pwdFmt = "^.{8,20}$";
			if (!password.matches(pwdFmt)) {
				throw new RestServiceException(RestErrorCode.PASSWORD_FORMAT_ERROR);
			}
			KFFUser loginaccount = null;
			try {
				// 用户登陆
				loginaccount = kffRmiService.login(loginName, password);
				if (null == loginaccount) {
					throw new RestServiceException(RestErrorCode.LOGIN_NAME_OR_PASSWORD_INCORRECT);
				}

			} catch (RestServiceException e) {
				return this.resResult(e.getErrorCode(), e.getMessage());
			} catch (Exception e) {
				return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
			}

			// 设置字段 将支付密码和登陆密码设置成true
			// map.put("user", this.formatLoginaccount(loginaccount));

			String token = AccountTokenUtil.getAccountToken(loginaccount.getUserId());
			// 把token放在session中
			System.out.println("token" + token);
			// 查询用户的认证表和身份认证表中的是否有数据
			// 5 表示首次登陆
			if (5 == kffRmiService.selectUserCardStatusByUserId(loginaccount.getUserId())) {
				// 身份验证表中没有该用户
				// 将用户是否进行身份审核等信息存放在authentication表中
				kffRmiService.setUserCardAuthentication(loginaccount.getUserId(), loginaccount.getMobile());
			}
			// 5表示首次登陆
			if (5 == kffRmiService.selectAuthenticationStatusByUserId(loginaccount.getUserId())) {
				// 将用户信息插入认证表中
				kffRmiService.saveAuthenticationByUseId(loginaccount.getUserId());
			}

			// 创建usermodel模型 将相关数据传递给前台
			UserModel userModel = new UserModel();
			userModel.setUserIdStatus(kffRmiService.selectUserCardStatusByUserId(loginaccount.getUserId()));
			// 根据用户的ID 查询用户的认证审核状态
			userModel.setAuthenticationStatus(kffRmiService.selectAuthenticationStatusByUserId(loginaccount.getUserId()));
			userModel.setUserNick(loginaccount.getUserName());
			userModel.setIcon(loginaccount.getIcon());
			// tokenSession.setAttribute("userModel", userModel);
			map.put("userModel", userModel);
			map.put("s", token);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.warn("login warn:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("login error:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 忘记密码
	 * 
	 * @param request
	 * @param response
	 * @param phone
	 * @param code
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/forgetPassword", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity forgetPassword(HttpServletRequest request, HttpServletResponse response, String phone, String code, String password) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {

			if (StringUtils.isBlank(phone)) {
				throw new RestServiceException(RestErrorCode.PHONE_NULL);
			} else {
				String phonefmt = RegexUtil.PHONEREGEX;
				if (!phone.matches(phonefmt)) {
					throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
				}
			}

			if (StringUtils.isBlank(code)) {
				throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_NULL);
			}

			KFFUser account = kffRmiService.findUserByPhoneNumber(phone);
			if (null == account) {
				throw new RestServiceException(RestErrorCode.MOBILE_NOT_EXIST);
			}
			if (StringUtils.isBlank(password)) {
				throw new RestServiceException(RestErrorCode.PASSWORD_NULL);
			}
			String pwdFmt = "^.{8,20}$";
			if (!password.matches(pwdFmt)) {
				throw new RestServiceException(RestErrorCode.PASSWORD_FORMAT_ERROR);
			}
			// 效验验证码
			String codeForSend = this.redisService.get(new StringBuffer(RestConstants.key_rest).append(SmsBuss.忘记密码效验码.getBus()).append(phone).toString());
			// 释放验证码 漏洞排查 防止不停输入错误验证码来暴库
			this.redisService.del(new StringBuffer(RestConstants.key_rest).append(SmsBuss.忘记密码效验码.getBus()).append(phone).toString());
			if (StringUtils.isEmpty(code)) {
				throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_INVALID);
			}
			if (!code.equalsIgnoreCase((String) codeForSend)) {
				throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_ERROR);
			}

			// 更新密码
			KFFUser accounts = new KFFUser();
			accounts.setUserId(account.getUserId());
			accounts.setPassword(SHAUtil.encode(password));
			accounts.setUpdateTime(new Date());
			kffRmiService.updateUser(accounts);
		} catch (RestServiceException e) {
			logger.error("UserController forgetPassword:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("UserController forgetPassword:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR);
		}
		return bre;
	}

	/**
	 * 用户注册协议
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/register/protocol", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity registerProtocol(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();

		try {
			SystemParam systemParam = systemParamRmiService.findByCode("registration_protocol_url");
			if (null != systemParam) {
				resMap.put("protocolUrl", systemParam.getVcParamValue());
			}
			bre.setData(resMap);
		} catch (RestServiceException e) {
			logger.error("RegisterController method registerProtocol reason：{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RegisterController method registerProtocol reason：{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 上传用户头像
	 * 
	 * @param request
	 * @param response
	 * @param imgdata
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/uploadUserIcon", method = { RequestMethod.POST, RequestMethod.GET }, produces = "text/html; charset=utf-8")
	public BaseResponseEntity uploadUserIcon(HttpServletRequest request, HttpServletResponse response, String imgdata) {
		BaseResponseEntity bre = new BaseResponseEntity();
		// Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			String token = (String) request.getSession().getAttribute("token");
			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			if (StringUtils.isBlank(imgdata)) {
				throw new RestServiceException(RestErrorCode.USER_ICON_DATA_EMPTY);
			}
			Integer userId = AccountTokenUtil.decodeAccountToken(token);
			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}
			SystemParam systemParam = systemParamRmiService.findByCode("upload_local_path");

			String path = systemParam.getVcParamValue() + "avatars/" + DateUtil.getCurrentYearMonth() + "/";
			String file = path + userId + ".jpg";
			Base64Util.decoderBase64File(imgdata, file);

			// 更新用户头像url
			systemParam = systemParamRmiService.findByCode("upload_file_path");
			path = systemParam.getVcParamValue() + "/avatars/" + DateUtil.getCurrentYearMonth() + "/";
			file = path + userId + ".jpg";
			KFFUser account = new KFFUser();
			account.setUserId(userId);
			account.setUpdateTime(new Date());
			account.setIcon(file);
			kffRmiService.updateUser(account);

		} catch (RestServiceException e) {
			logger.error("error in uploadUserIcon method:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("uncatched error in uploadUserIcon method:{}", e);
			e.printStackTrace();
			return this.resResult(RestErrorCode.SYS_ERROR);
		}
		return bre;
	}

	/*
	 * ========================================身份证上传begin========================
	 * ====== =========================
	 */
	/**
	 * 首页审核展示状态 status 1 审核成功 2 审核中 3 审核不通过 4 未提交审核
	 * 
	 * @return
	 */
	@RequestMapping(value = "/usercardStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity usercardStatus(HttpServletRequest request, HttpServletResponse response, String token) {

		// 创建返回体
		BaseResponseEntity bre = new BaseResponseEntity();
		// 创建返回的map
		HashMap<String, Object> map = new HashMap<String, Object>();

		// 根据用户token 查找到用户的userID
		Integer userId = AccountTokenUtil.decodeAccountToken(token);

		if (userId == null) {
			throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
		}

		if (null == token) {
			throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		}

		// 移动端登陆
		KFFUser user = kffRmiService.findUserById(userId);
		if (null == kffRmiService.selectUserCardByUserId(userId)) {
			// usercard表中没有数据表示是移动端登陆
			// 需要新建数据表
			kffRmiService.setUserCardAuthentication(user.getUserId(), user.getMobile());

		}
		Integer statusNum = kffRmiService.selectUserCardStatusByUserId(user.getUserId());
		// 用户没有进行审核
		if (4 == statusNum) {
			// 未进行实名审核
			// 就直接查询usercard表返回查询结果
			map.put("status", statusNum);
			bre.setData(map);
			return bre;
		}

		if (3 == statusNum) {
			// 审核没有通过
			//
			map.put("status", statusNum);
			bre.setData(map);
			return bre;
		}
		if (2 == statusNum) {
			// 审核中
			//
			map.put("status", statusNum);
			bre.setData(map);
			return bre;
		}
		if (1 == statusNum) {
			// 审核成功
			//
			map.put("status", statusNum);
			bre.setData(map);
			return bre;
		}
		if (null == statusNum) {
			map.put("status", 4);
			bre.setData(map);
			return bre;
		}

		return null;
	}

	/**
	 * 身份证上传(真实姓名,用户身份证号,正面身份证URL,用户token) (flag 为true 显示执行查询是否通过 false 显示上传图片界面)
	 * 
	 * @param request
	 * @param response
	 * @param userRealName
	 * @param userCardNum
	 * @param uploadfile
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/uploadUserCard", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity uploadUserCord(HttpServletRequest request, HttpServletResponse response, String userRealName, String userCardNum,
			String photoIviews, String token) {
		// 创建返回体
		BaseResponseEntity bre = new BaseResponseEntity();
		// 创建返回的map
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {

			// 根据用户token 查找到用户的userID
			Integer userId = AccountTokenUtil.decodeAccountToken(token);
			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}
			if (null == userRealName) {
				throw new RestServiceException(RestErrorCode.VCNAME_NULL);
			}
			if (null == userCardNum) {
				throw new RestServiceException(RestErrorCode.VCCARD_NULL);
			}
			/*
			 * // 根据用户ID查询用户是否已经进行实名验证 Integer userStatus =
			 * kffRmiService.selectStatusByUserID(userId);
			 */
			// status 1 审核成功 2 审核中 3 审核不通过 4 未提交审核
			// 如果在表能查询到信息, 说明 此用户曾经进行过身份审核

			// 如果查询的card是空 则表明此用户没有进行身份验证 进行身份验证等工作流程 验证身份证号是否符合标注
			String userCardfmt = RegexUtil.IDCARD;
			// 判断身份证号是否符合标准
			if (!userCardNum.matches(userCardfmt)) {
				throw new RestServiceException(RestErrorCode.USER_IDCARD_IS_FALSE);
			}

			String userRealNamefmt = RegexUtil.LOGIN_NAME;
			// 判断身份证号是否符合标准
			if (!userRealName.matches(userRealNamefmt)) {
				throw new RestServiceException(RestErrorCode.VCNAME_INCORRECT);
			}
			// 判断用户传入的身份证号是否重复提交 返回 身份证号码已被实名验证

			if (null == kffRmiService.selectUserCardNum(userCardNum)) {
				throw new RestServiceException(RestErrorCode.USER_IDCARD_SUBMITED);
			}

			// 并将status设置成待审核状态
			kffRmiService.updataUserIdStstus(userRealName, userCardNum, photoIviews, userId);
			map.put("status", 2);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("error in uploadUserIcon method:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("uncatched error in uploadUserIcon method:{}", e);
			e.printStackTrace();
			return this.resResult(RestErrorCode.SYS_ERROR);
		}
		return bre;
	}

	/*
	 * ========================================身份证上传end==========================
	 * ==== =========================
	 */

	/**
	 * 更换用户信息(头像.昵称)
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param userName
	 * @param sex
	 * @param userSignature
	 * @param areaName
	 * @return
	 */
	@RequestMapping(value = "/updateUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody BaseResponseEntity updateUserInfo(HttpServletRequest request, HttpServletResponse response, String userName, Integer sex,
			String userSignature, String areaName) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String token = (String) request.getSession().getAttribute("token");
			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			if (sex != null && sex != 1 && sex != 2) {
				throw new RestServiceException(RestErrorCode.USER_SEX_WRONG);
			}

			Integer userId = null;
			try {
				userId = AccountTokenUtil.decodeAccountToken(token);
			} catch (Exception e) {
				logger.error("updateUserInfo decodeAccountToken error:{}", e);
				return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR, e.getMessage());
			}
			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}

			KFFUser loginaccount = kffRmiService.findUserById(userId);

			if (loginaccount == null) {
				return this.resResult(RestErrorCode.USER_NOT_EXIST);
			}

			KFFUser account = new KFFUser();
			account.setUserId(loginaccount.getUserId());
			if (StringUtils.isNotBlank(userName)) {
				account.setUserName(userName);
			}
			if (StringUtils.isNotBlank(userSignature)) {
				account.setUserSignature(userSignature);
			}
			if (StringUtils.isNotBlank(areaName)) {
				account.setAreaName(areaName);
			}
			if (sex != null) {
				account.setSex(sex);
			}
			account.setUpdateTime(new Date());
			kffRmiService.updateUser(account);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("UserController updateUserInfo error,reason：{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("UserController updateUserInfo error,reason:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 我的关注列表，包括 话题和用户，分2次请求。
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param followType
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/myFollowList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity myFollowList(HttpServletRequest request, HttpServletResponse response, Integer followType, Integer pageIndex, Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String token = (String) request.getSession().getAttribute("token");
			if (null == pageIndex) {
				pageIndex = 1;
			}
			if (null == pageSize) {
				pageSize = 10;
			}
			if (null == followType) {
				followType = 2;// 关注类型 2 关注话题 3关注用户
			}

			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			Integer userId = null;
			try {
				userId = AccountTokenUtil.decodeAccountToken(token);
			} catch (Exception e) {
				logger.error("myFollowList decodeAccountToken error:{}", e);
				return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR, e.getMessage());
			}
			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}

			KFFUser loginaccount = kffRmiService.findUserById(userId);

			if (loginaccount == null) {
				return this.resResult(RestErrorCode.USER_NOT_EXIST);
			}

			PaginationQuery query = new PaginationQuery();
			query.addQueryData("followUserId", userId + "");
			query.addQueryData("followType", followType + "");

			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<FollowResponse> result = kffRmiService.findPageMyFollow(query);

			map.put("myFollows", result);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.warn("myFollowList warn:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("myFollowList error:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 我的收藏列表，当前用户收藏的评测、讨论、文章，按照时间排序（倒序）
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/myCollectList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity myCollectList(HttpServletRequest request, HttpServletResponse response, Integer pageIndex, Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String token = (String) request.getSession().getAttribute("token");
			if (null == pageIndex) {
				pageIndex = 1;
			}
			if (null == pageSize) {
				pageSize = 10;
			}
			Integer userId = getUserIdByToken(token);
			KFFUser loginaccount = kffRmiService.findUserById(userId);

			if (loginaccount == null) {
				return this.resResult(RestErrorCode.USER_NOT_EXIST);
			}

			PaginationQuery query = new PaginationQuery();
			query.addQueryData("collectUserId", userId + "");
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<CollectPostResponse> result = kffRmiService.findPageMyCollectRecords(query);

			map.put("myTokenRecords", result);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.warn("myCollectList warn:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("myCollectList error:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 我的资产明细列表
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/myTokenRecordsList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity myTokenRecordsList(HttpServletRequest request, HttpServletResponse response, Integer pageIndex, Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String token = (String) request.getSession().getAttribute("token");
			if (null == pageIndex) {
				pageIndex = 1;
			}
			if (null == pageSize) {
				pageSize = 10;
			}
			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			Integer userId = null;
			try {
				userId = AccountTokenUtil.decodeAccountToken(token);
			} catch (Exception e) {
				logger.error("updateUserInfo decodeAccountToken error:{}", e);
				return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR, e.getMessage());
			}
			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}

			KFFUser loginaccount = kffRmiService.findUserById(userId);

			if (loginaccount == null) {
				return this.resResult(RestErrorCode.USER_NOT_EXIST);
			}

			PaginationQuery query = new PaginationQuery();
			query.addQueryData("userId", userId + "");
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<Tokenrecords> result = kffRmiService.findPageMyTokenRecords(query);

			map.put("myTokenRecords", result);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.warn("myTokenRecordsList warn:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("myTokenRecordsList error:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 获得H5相关页面的URL接口
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/getH5URLs", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody BaseResponseEntity getH5URLs(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String token = (String) request.getSession().getAttribute("token");
			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			Integer userId = null;
			try {
				userId = AccountTokenUtil.decodeAccountToken(token);
			} catch (Exception e) {
				logger.error("getAreaList decodeAccountToken error:{}", e);
				return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR, e.getMessage());
			}
			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}

			SystemParam systemParam = systemParamRmiService.findByCode("kff_invite_user_url");

			map.put("inviteUserUrl", systemParam.getVcParamValue());

			systemParam = systemParamRmiService.findByCode("kff_about_us_url");
			map.put("aboutUsUrl", systemParam.getVcParamValue());

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("UserController getH5URLs error,reason：{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("UserController getH5URLs error,reason:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 获取省市份列表
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param code
	 *            区域编码
	 * @return
	 */
	@RequestMapping(value = "/getAreaList", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody BaseResponseEntity getAreaList(HttpServletRequest request, HttpServletResponse response, String code) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String token = (String) request.getSession().getAttribute("token");
			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			Integer userId = null;
			try {
				userId = AccountTokenUtil.decodeAccountToken(token);
			} catch (Exception e) {
				logger.error("getAreaList decodeAccountToken error:{}", e);
				return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR, e.getMessage());
			}
			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}

			List<Dareas> areas = kffRmiService.getAreaListByCode(code);
			map.put("areas", areas);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("UserController getAreaList error,reason：{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("UserController getAreaList error,reason:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 验证手机号码,姓名等信息是否符合要求
	 * 
	 * @param registerRequest
	 * @throws Exception
	 */
	private void validateForm(RegisterRequest registerRequest) throws Exception {
		if (StringUtils.isBlank(registerRequest.getPhoneNumber())) {
			throw new RestServiceException(RestErrorCode.PHONE_NULL);
		} else {
			// String phonefmt
			// ="^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
			// 统一用手机号白名单验证正则表达式
			String phonefmt = RegexUtil.PHONEREGEX;
			if (registerRequest.getPassword().matches(phonefmt)) {
				throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
			}
		}
		if (kffRmiService.verifyLoginaccount("mobile", registerRequest.getPhoneNumber())) {
			throw new RestServiceException(RestErrorCode.PHONE_ALREADY_EXIST);
		}

		if (StringUtils.isBlank(registerRequest.getPassword())) {
			throw new RestServiceException(RestErrorCode.PASSWORD_NULL);
		} else {
			// String pwdFmt =
			// "^((?=.*?\\d)(?=.*?[A-Za-z])|(?=.*?\\d)(?=.*?[!@#$%^&])|(?=.*?[A-Za-z])(?=.*?[!@#$%^&]))[\\dA-Za-z!@#$%^&]{8,20}$";
			// //由字母、数字组成，8-20位
			String pwdFmt = "^.{8,20}$";
			if (!registerRequest.getPassword().matches(pwdFmt)) {
				throw new RestServiceException(RestErrorCode.PASSWORD_FORMAT_ERROR);
			}
		}
		if (StringUtils.isBlank(registerRequest.getDynamicVerifyCode())) {
			// throw new
			// RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_ERROR);
		} else {
			String code = this.redisService.get(new StringBuffer(RestConstants.key_rest).append(SmsBuss.注册效验码.getBus())
					.append(registerRequest.getPhoneNumber()).toString());
			if (!registerRequest.getDynamicVerifyCode().equalsIgnoreCase(code)) {
				throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_ERROR);
			}
		}
	}

	/**
	 * 验证信息是否合法,验证手机信息,姓名等信息是否合法
	 * 
	 * @param jsonObject
	 * @throws Exception
	 */
	private void checkParam(JSONObject jsonObject) throws Exception {
		// 判断手机号码是否为空
		if (StringUtils.isNotBlank((String) jsonObject.get("phoneNumber"))) {
			throw new RestServiceException(RestErrorCode.PHONE_NULL);
		} else {
			// String phonefmt =
			// "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
			// 统一用手机号白名单验证正则表达式
			String phonefmt = RegexUtil.PHONEREGEX;
			// 判断手机号码手机是否符合标注
			if (!(((String) jsonObject.get("phoneNumber")).matches(phonefmt))) {
				throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
			}
		}
		// 验证账号型号
		if (kffRmiService.verifyLoginaccount("mobile", (String) jsonObject.get("phoneNumber"))) {
			throw new RestServiceException(RestErrorCode.PHONE_ALREADY_EXIST);
		}

		if (StringUtils.isNotBlank((String) jsonObject.get("password"))) {
			throw new RestServiceException(RestErrorCode.PASSWORD_NULL);
		} else {

			String pwdFmt = "^.{8,20}$";
			if (!((String) jsonObject.get("password")).matches(pwdFmt)) {
				throw new RestServiceException(RestErrorCode.PASSWORD_FORMAT_ERROR);
			}
		}
		// 验证验证码是否为空
		if (StringUtils.isNotBlank((String) jsonObject.get("dynamicVerifyCode"))) {

			throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_ERROR);
		} else {
			String code = this.redisService.get(new StringBuffer(RestConstants.key_rest).append(SmsBuss.注册效验码.getBus())
					.append(jsonObject.get("dynamicVerifyCode")).toString());

			String dynamicVerifyCodeFlg = (String) (jsonObject.get("dynamicVerifyCode"));

			if (!dynamicVerifyCodeFlg.equalsIgnoreCase(code)) {
				throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_ERROR);
			}
		}

	}

	private KFFUser formatLoginaccount(KFFUser loginaccount) {
		loginaccount.setPassword("true");
		loginaccount.setPayPassword("true");
		return loginaccount;
	}
}
