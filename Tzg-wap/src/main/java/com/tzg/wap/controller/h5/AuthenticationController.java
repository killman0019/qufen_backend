package com.tzg.wap.controller.h5;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.entitys.kff.authentication.Authentication;
import com.tzg.entitys.kff.authentication.AuthenticationData;
import com.tzg.entitys.kff.token.Token;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "AuthenticationController")
@RequestMapping("/kff/authentication")
public class AuthenticationController extends BaseController {

	private static Logger log = Logger.getLogger(AuthenticationController.class);
	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private RedisService redisService;

	/**
	 * 根据token查找在审核表中查询此用户是否已经提交审核或者没有提交过审核 ++++++++++首页点审核按钮
	 * 
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/reviewed", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity reviewed(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) {
		// 创建返回结果
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		Token tokenData = JSON.parseObject(data, Token.class);
		String token = tokenData.getToken();
		System.out.println(token);
		// 根据token获得userID
		// resMap.put("token", token);
		if (null == token) {
			throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		}

		Integer userId = AccountTokenUtil.decodeAccountToken(token);
		if (userId < 0 || null == userId) {
			throw new RestServiceException(RestErrorCode.SYS_ERROR);
		}

		// 根据userID查询申请表的 查询状态表,根据表的ID进行降序排列
		// 如果 statuses 为空 说明 此用户是首次进行认证操作 直接跳转到操作页面
		// statuses不为空
		// 第一个数为 '1 通过审核 2 未通过审核 3 待审核 4未提交审核 ',

		try {
			List<Authentication> authentications = kffRmiService.selectAuthenticatiobByUserId(userId);
			if (null == authentications) {
				// 用户从未进行审核操作
				resMap.put("status", 4);
				bre.setData(resMap);
				return bre;
			}
			if (4 == authentications.get(0).getStatus()) {
				// 用户从未进行审核操作
				resMap.put("status", 4);
				bre.setData(resMap);
				return bre;
			}
			if (1 == authentications.get(0).getStatus()) {
				// 通过审核
				resMap.put("status", 1);
				bre.setData(resMap);
				return bre;
			}
			if (3 == authentications.get(0).getStatus()) {
				// 未审核
				resMap.put("status", 3);
				resMap.put("notpassreason", authentications.get(0).getNotpassreason());
				bre.setData(resMap);
				return bre;
			}
			if (2 == authentications.get(0).getStatus()) {
				// 未通过审核
				resMap.put("status", 2);
				bre.setData(resMap);
				return bre;
			}
		} catch (RestServiceException e) {
			logger.warn("reviewed warn:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("reviewed error:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return null;

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @param authentication
	 *            审核类型
	 * @param token
	 *            用户token
	 * @param flag
	 *            申请的审核类型
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submitAuthenTiForm", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity submitAuthenTiForm(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		AuthenticationData authenticationData = JSON.parseObject(data, AuthenticationData.class);
		String token = authenticationData.getToken();
		Integer type = authenticationData.getType();
		Authentication authentication = new Authentication();
		authentication.setAssistpic(authenticationData.getAssistpic());
		authentication.setAuthinformation(authenticationData.getAuthinformation());
		authentication.setCompany(authenticationData.getCompany());
		authentication.setLicencepic(authenticationData.getLicencepic());
		authentication.setLink(authenticationData.getLink());
		authentication.setMail(authenticationData.getMail());
		authentication.setMediachannel(authenticationData.getMediachannel());
		authentication.setMediaintroduce(authenticationData.getMediaintroduce());
		authentication.setMedianame(authenticationData.getMedianame());
		authentication.setMissivepic(authenticationData.getMissivepic());
		authentication.setNumber(authenticationData.getNumber());
		authentication.setOperatorname(authenticationData.getOperatorname());
		authentication.setQufennickname(authenticationData.getQufennickname());
		authentication.setRegistrationnum(authenticationData.getRegistrationnum());
		authentication.setWechat(authenticationData.getWechat());
		authentication.setType(type);
		// 根据token查询用户
		if (null == token) {
			throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		}
		Integer userId = AccountTokenUtil.decodeAccountToken(token);
		if (userId < 0 || null == userId) {
			throw new RestServiceException(RestErrorCode.SYS_ERROR);
		}
		// 分类进行参数验证

		// 将authentication插入数据库
		authentication.setUserid(userId);
		try {
			kffRmiService.updataAuthentication(authentication);
			List<Authentication> authenticationFromDB = kffRmiService.selectAuthenticationByUserId(userId);
			resMap.put("status", authenticationFromDB.get(0).getStatus());
			resMap.put("Reason", authenticationFromDB.get(0).getNotpassreason());
		} catch (RestServiceException e) {
			logger.warn("AuthenticationController authenticationSubmit warn:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("AuthenticationController authenticationSubmit error:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		// 向前台展示待审核状态

		bre.setData(resMap);
		return bre;

	}

	/**
	 * 重新提交
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param authentication
	 * @param flag
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submitAuthenTiFormAgain", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity submitAuthenTiFormAgain(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		Token tokenData = JSON.parseObject(data, Token.class);
		String token = tokenData.getToken();
		System.out.println(token);
		Integer userId = AccountTokenUtil.decodeAccountToken(token);
		// 点击重新提交 在数据库中重新插入新的一行数据
		try {
			kffRmiService.saveAuthenticationByUseId(userId);

			// 向前台展示信息
			List<Authentication> authenticationFromDB = kffRmiService.selectAuthenticationByUserId(userId);
			resMap.put("status", authenticationFromDB.get(0).getStatus());
			resMap.put("notpassreason", authenticationFromDB.get(0).getNotpassreason());
		} catch (RestServiceException e) {
			logger.error("AuthenticationController submitAuthenTiFormAgain:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("AuthenticationController submitAuthenTiFormAgain:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		bre.setData(resMap);
		return bre;
	}
}
