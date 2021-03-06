package com.tzg.wap.controller.h5;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.tzg.common.utils.AesWapUtils;
import com.tzg.common.utils.EnumConstant.SmsBuss;
import com.tzg.common.utils.HtmlUtils;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.SyseUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.common.utils.rest.Base64Util;
import com.tzg.common.utils.rest.RestConstants;
import com.tzg.entitys.kff.collect.CollectPostResponse;
import com.tzg.entitys.kff.dareas.Dareas;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.follow.FollowResponse;
import com.tzg.entitys.kff.model.UserModel;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserLogin;
import com.tzg.entitys.kff.userInvation.UserInvation;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.FollowRmiService;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.KFFUserRmiService;
import com.tzg.rmi.service.SystemParamRmiService;
import com.tzg.wap.utils.DateUtil;
import com.tzg.wap.utils.HexUtil;
import com.vdurmont.emoji.EmojiParser;

@Controller(value = "KFFUserController")
@RequestMapping("/kff/user")
public class UserController extends BaseController {
	// 前台春晓的注册链接
	@Value("#{paramConfig['registerUrl']}")
	private String registerUrl;
	// "http://192.168.10.196:5000/user/registerSmp?invaUIH=";
	@Value("#{paramConfig['enveUrl']}")
	private String enveUrl;
	private static Logger logger = Logger.getLogger(UserController.class);
	private static final String KEY = "abcdefgabcdefg12";
	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private SystemParamRmiService systemParamRmiService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private KFFUserRmiService kffUserRmiService;
	@Autowired
	private FollowRmiService followRmiService;

	/** 
	* @Title: getUserInfo 
	* @Description: TODO <获取用户信息接口>
	* @author linj <方法创建作者>
	* @create 上午10:17:38
	* @param @param request
	* @param @param response
	* @param @param userId 需要获取哪个用户的信息
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午10:17:38
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/getUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity getUserInfo(HttpServletRequest request, HttpServletResponse response, Integer userId, String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (userId == null && StringUtils.isBlank(token)) {
			JSONObject requestContent = HtmlUtils.getRequestContent(request);
			userId = (Integer) requestContent.get("userId");
			token = (String) requestContent.get("token");
		}
		if (null == userId) {
			bre.setNoRequstData();
			return bre;
		}
		KFFUser user = kffUserRmiService.findById(userId);
		if (null == user) {
			bre.setNoDataMsg();
			return bre;
		}
		// 查询用户关注状态
		Map<String, Object> seMap = new HashMap<String, Object>();
		Integer userIdc = null;
		if (StringUtils.isNotBlank(token)) {
			userIdc = getUserIdByToken(token);
		}
		if (null != userIdc) {
			seMap.put("followUserId", userIdc);
			seMap.put("followedUserId", userId);
			seMap.put("status", 1);
			seMap.put("followType", 3);// 关注类型：1-关注项目;2-关注帖子；3-关注用户
			List<Follow> follows = followRmiService.findListByAttr(seMap);
			if (follows.isEmpty()) {
				user.setFollowStatus(0);
			} else {
				user.setFollowStatus(1);
			}
		} else {
			user.setFollowStatus(0);
		}
		user.setPassword(null);
		user.setPassword(null);
		map.put("user", user);
		bre.setData(map);
		return bre;
	}

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
			String dynamicVerifyCode, String invaUIH, String password, Boolean isRobot) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 验证手机号的合法性
		if (null == password) {
			map.put("reStatus", 0);// 1注册成功 0 注册不成功
			map.put("reason", "请输入密码!");
			bre.setData(map);
			return bre;
		}
		SyseUtil.systemErrOutJson(isRobot);
		String passwordAes = null;
		if (isRobot != null) {
			if (isRobot) {
				passwordAes = password;
				System.err.println("robot注册");
			} else {
				try {
					passwordAes = AesWapUtils.aesDecrypt(password, KEY);
					System.out.println("passwordAes" + passwordAes);
				} catch (Exception e1) {
					logger.info("解密失败!");
					map.put("reStatus", 0);// 1注册成功 0 注册不成功
					map.put("reason", "请重新输入密码!");
					bre.setData(map);
					return bre;
				}
			}
		}
		if (isRobot == null) {
			try {
				passwordAes = AesWapUtils.aesDecrypt(password, KEY);
				System.out.println("passwordAes" + passwordAes);
			} catch (Exception e1) {
				logger.info("解密失败!");
				map.put("reStatus", 0);// 1注册成功 0 注册不成功
				map.put("reason", "请重新输入密码!");
				bre.setData(map);
				return bre;
			}

		}
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
		Integer userStatus = kffRmiService.selectUserStatusByPhone(phoneNumber);
		if (userStatus == 0) {
			logger.info("手机号码已经被禁用!");
			map.put("reStatus", 0);// 1注册成功 0 注册不成功
			map.put("reason", "手机号已被禁用,请联系客服!");
			bre.setData(map);
			return bre;
		}
		// 验证手机是否已经注册
		KFFUser user = kffRmiService.findUserByPhoneNumber(phoneNumber);
		if (null != user) {
			// throw new RestServiceException(RestErrorCode.PHONE_ALREADY_EXIST);
			map.put("reStatus", 0);// 1注册成功 0 注册不成功
			map.put("reason", "该手机已经被注册!");
			bre.setData(map);
			return bre;
		}

		// 判断密码的合法性
		// String pwdFmt =
		// "^((?=.*?\\d)(?=.*?[A-Za-z])|(?=.*?\\d)(?=.*?[!@#$%^&])|(?=.*?[A-Za-z])(?=.*?[!@#$%^&]))[\\dA-Za-z!@#$%^&]{8,20}$";
		// //由字母、数字组成，8-20位
		if (null != passwordAes) {
			String pwdFmt = RegexUtil.PASSWORD_LOGIN_REGISTER;// "^.{8,20}$";
			if (!passwordAes.matches(pwdFmt)) {
				// throw new RestServiceException(RestErrorCode.PASSWORD_FORMAT_ERROR);
				map.put("reStatus", 0);// 1注册成功 0 注册不成功
				map.put("reason", "密码不符合要求，必须为8-20位，且数字和字母的组合");
				bre.setData(map);
				return bre;
			}
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
		if (isRobot != null) {
			if (!isRobot) {
				if (!dynamicVerifyCode.equals(cacheCode)) {

					map.put("reStatus", 0);// 1注册成功 0 注册不成功
					map.put("reason", "短信验证码输入不正确");
					bre.setData(map);

					return bre;
				}
			}
		}

		if (isRobot == null) {

			if (!dynamicVerifyCode.equals(cacheCode)) {

				map.put("reStatus", 0);// 1注册成功 0 注册不成功
				map.put("reason", "短信验证码输入不正确");
				bre.setData(map);

				return bre;

			}
		}

		// 手机验证码成功 保存用户信息
		// 将邀请二维码字符进行转码转化成对应的userID
		Integer invaUserId = null;
		if (null != invaUIH) {
			invaUserId = HexUtil.code2ToUserId(invaUIH);
		}

		KFFUser KffUser = kffRmiService.saveUserByphonePass(phoneNumber, invaUserId, passwordAes);
		if (null == KffUser) {
			map.put("reStatus", 0);// 1注册成功 0 注册不成功
			map.put("reason", "此用户已注册");
			bre.setData(map);

			// throw new RestServiceException("注册失败请稍后重试!");

		}
		if (null != KffUser) {
			map.put("reStatus", 1);// 注册成功
			// 根据用户的ID 生成token
			String token = AccountTokenUtil.getAccountToken(KffUser.getUserId());
			map.put("token", token);
			bre.setData(map);
			kffRmiService.registerAward(KffUser.getUserId());
			return bre;
		}
		// 1是成功
		return null;
	}

	/**
	 * 
	* @Title: regAnLogin 
	* @Description: TODO <注册登录接口只有手机号码和手机验证码>
	* @author zhangdd <方法创建作者>
	* @create 下午3:49:34
	* @param @param response
	* @param @param request
	* @param @param phoneNumber
	* @param @param dynamicVerifyCode
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午3:49:34
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	@RequestMapping(value = "/regAnLogin", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity regAnLogin(HttpServletRequest response, HttpServletRequest request, String phoneNumber, String dynamicVerifyCode, String invaUIH) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		// 验证手机号的合法性

		String phonefmt = RegexUtil.PHONEREGEX;
		// 判断手机号码手机是否符合标注
		if (!phoneNumber.matches(phonefmt)) {
			throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
		}

		Integer userStatus = kffRmiService.selectUserStatusByPhone(phoneNumber);
		if (userStatus == 0) {
			logger.info("手机号码已经被禁用!");
			// map.put("reStatus", 0);// 1注册成功 0 注册不成功
			// map.put("reason", "手机号已被禁用,请联系客服!");
			bre.setData(map);
			bre.setCode(11035);
			bre.setMsg("手机号已被禁用,请联系客服!");
			return bre;
		}

		String cacheCode = null;
		try {
			String module = "reganlogin";
			// key_rest_sms_login15537791297sms
			String cacheKey = new StringBuffer(RestConstants.key_rest).append(module).append(phoneNumber).toString();
			cacheCode = redisService.get(cacheKey);
		} catch (Exception e) {

			logger.error("RegisterInvaController registerInva：", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}

		if (!dynamicVerifyCode.equals(cacheCode)) {

			// map.put("reStatus", 0);// 1注册成功 0 注册不成功
			// map.put("reason", "短信验证码输入不正确");
			bre.setData(map);
			bre.setMsg("短信验证码输入不正确");
			bre.setCode(11012);

			return bre;
		}
		// 验证手机是否已经注册
		KFFUser user = kffRmiService.findUserByPhoneNumber(phoneNumber);
		if (null != user) {// 已经注册
			// 进行登录操作
			KFFUser loginaccount = kffRmiService.login(phoneNumber, null, null, null);

			map.put("reStatus", 1);// 1注册成功 0 注册不成功
			String token = AccountTokenUtil.getAccountToken(loginaccount.getUserId());
			map.put("token", token);
			String userIdTo2code = HexUtil.userIdTo2code(loginaccount.getUserId());

			map.put("invaUIH", userIdTo2code);
			bre.setData(map);
			return bre;
		}
		if (null == user) {// 未注册
			// 手机验证码成功 保存用户信息
			// 将邀请二维码字符进行转码转化成对应的userID
			Integer invaUserId = null;
			if (!"undefined".equals(invaUIH)) {
				if (StringUtils.isNotEmpty(invaUIH)) {
					invaUserId = HexUtil.code2ToUserId(invaUIH);
				}
			}
			KFFUser KffUser = kffRmiService.saveUserByphonePass(phoneNumber, invaUserId, null);

			if (null != KffUser) {
				map.put("reStatus", 1);// 注册成功

				String token = AccountTokenUtil.getAccountToken(KffUser.getUserId());
				map.put("token", token);
				bre.setData(map);
				kffRmiService.registerAward(KffUser.getUserId());
				// 根据token获得userId
				Integer userId = AccountTokenUtil.decodeAccountToken(token);
				// 根据userID生成code2
				String userIdTo2code = HexUtil.userIdTo2code(userId);
				// 将生成的2code 放在数据库中
				kffRmiService.saveUserInvation(userId, userIdTo2code);
				// 生成URL注册链接
				String user2codeUrl = enveUrl + userIdTo2code;
				logger.info(user2codeUrl);
				map.put("invaUIH", userIdTo2code);
				return bre;
			}
			// 1是成功
		}
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
		logger.info(user2codeUrl);
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
			if (null == userInvation) {
				throw new RestServiceException("生成海拔出错请联系客服!");
			}
			if (StringUtils.isEmpty(userInvation.getUserposterpic())) {
				// 生成海报
				posterUrl = kffRmiService.creat2Code(userId);
				String code2Url = null;
				kffRmiService.updataUserInvation(userId, posterUrl, code2Url);
				System.out.println("posterUrl" + posterUrl);
				map.put("url", posterUrl);
			} else {
				posterUrl = userInvation.getUserposterpic();
				System.out.println("posterUrlbd" + posterUrl);
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
	 * @param phoneNumber 手机号码
	 * @param password 密码
	 * @param dynamicVerifyCode 手机验证码
	 * @return
	 */
	@RequestMapping(value = "/register", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity register(HttpServletRequest request, HttpServletResponse response, String phoneNumber, String password, String dynamicVerifyCode) {
		// 创建返回体
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if (phoneNumber == null && password == null && dynamicVerifyCode == null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				phoneNumber = (String) requestContent.get("phoneNumber");
				password = (String) requestContent.get("password");
				dynamicVerifyCode = (String) requestContent.get("dynamicVerifyCode");
			}
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
			// String token = AccountTokenUtil.getAccountToken(user.getUserId());
			// 将用户是否进行身份审核等信息存放在authentication表中
			// kffRmiService.setUserCardAuthentication(user.getUserId(), phoneNumber);
			// 将用户信息插入认证表中
			// kffRmiService.saveAuthenticationByUseId(user.getUserId());
			// 根据ID 生成token生成account token
			// map.put("token", token);
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
				loginaccount = kffRmiService.login(loginName, password, null, null);
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
			Integer userCardStatus = kffRmiService.selectUserCardStatusByUserId(loginaccount.getUserId());
			if (5 == userCardStatus) {
				// 身份验证表中没有该用户
				// 将用户是否进行身份审核等信息存放在authentication表中
				kffRmiService.setUserCardAuthentication(loginaccount.getUserId(), loginaccount.getMobile());
			}
			// 5表示首次登陆
			Integer authenticationStatus = kffRmiService.selectAuthenticationStatusByUserId(loginaccount.getUserId());
			if (5 == authenticationStatus) {
				// 将用户信息插入认证表中
				kffRmiService.saveAuthenticationByUseId(loginaccount.getUserId());
			}

			// 创建usermodel模型 将相关数据传递给前台
			UserModel userModel = new UserModel();
			userModel.setUserIdStatus(userCardStatus);
			// 根据用户的ID 查询用户的认证审核状态
			userModel.setAuthenticationStatus(authenticationStatus);
			userModel.setUserNick(loginaccount.getUserName());
			userModel.setIcon(loginaccount.getIcon());
			userModel.setUserType(loginaccount.getUserType());// 用户类型:1-普通用户；2-项目方；3-评测机构；4-机构用户
			userModel.setUid(loginaccount.getUserId());
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
	 * 首页审核展示状态 status 1 待审核 2 审核通过 3 审核不通过 4 未提交审核
	 * 
	 * @return
	 */
	@RequestMapping(value = "/usercardStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity usercardStatus(HttpServletRequest request, HttpServletResponse response, String token) {

		// 创建返回体
		BaseResponseEntity bre = new BaseResponseEntity();
		// 创建返回的map
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (null == token) {
			throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		}
		// 根据用户token 查找到用户的userID
		Integer userId = AccountTokenUtil.decodeAccountToken(token);
		if (userId == null) {
			throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
		}
		// 移动端登陆
		KFFUser user = kffRmiService.findUserById(userId);
		if (null == kffRmiService.selectUserCardByUserId(userId)) {
			// usercard表中没有数据表示是移动端登陆
			// 需要新建数据表
			kffRmiService.setUserCardAuthentication(user.getUserId(), user.getMobile());
		}
		Integer statusNum = kffRmiService.selectUserCardStatusByUserId(user.getUserId());
		if (null == statusNum) {
			map.put("status", 4);
		} else if (null != statusNum) {
			map.put("status", statusNum);
		}
		bre.setData(map);
		return bre;
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
			// status 1 待审核 2 审核通过 3 未通过 4 未提交
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
			map.put("status", 1);
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

	@RequestMapping(value = "/updateUserInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody BaseResponseEntity updateUserInfo(HttpServletRequest request, String token, String userName, String userIcon, String userSignature,
			Integer sex, String areaName) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			JSONObject requestContent = HtmlUtils.getRequestContent(request);
			if (StringUtils.isEmpty(token) && StringUtils.isEmpty(userName) && sex == null && StringUtils.isEmpty(userSignature)
					&& StringUtils.isEmpty(areaName) && StringUtils.isEmpty(userIcon)) {
				token = (String) requestContent.get("token");
				userName = (String) requestContent.get("userName");
				sex = (Integer) requestContent.get("sex");
				userSignature = (String) requestContent.get("userSignature");
				areaName = (String) requestContent.get("areaName");
				userIcon = (String) requestContent.get("userIcon");
			}
			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			if (sex != null && sex != 1 && sex != 2) {
				throw new RestServiceException(RestErrorCode.USER_SEX_WRONG);
			}
			// 去除表情
			if (StringUtils.isNotEmpty(userName)) {
				userName = EmojiParser.removeAllEmojis(userName);
			}
			if (StringUtils.isNotEmpty(userSignature)) {
				userSignature = EmojiParser.removeAllEmojis(userSignature);
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
			com.tzg.common.utils.DozerMapperUtils.map(loginaccount, account);
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
			if (StringUtils.isNotBlank(userIcon)) {
				account.setIcon(userIcon);
			}
			account.setUpdateTime(new Date());
			kffRmiService.updateUserInfo(account);

			KFFUser newuser = kffRmiService.findUserById(userId);
			map.put("user", newuser);
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
			Integer type = 2;// 取关注人
			PageResult<FollowResponse> result = kffRmiService.findPageMyFollow(query, type, loginaccount);
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
			Integer type = 2;// 取关注人
			PageResult<CollectPostResponse> result = kffRmiService.findPageMyCollectRecords(query, type, loginaccount);
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
