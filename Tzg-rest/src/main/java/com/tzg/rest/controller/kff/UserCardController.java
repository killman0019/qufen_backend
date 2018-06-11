package com.tzg.rest.controller.kff;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.RegexUtil;
import com.tzg.entitys.kff.authentication.Authentication;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.usercard.UserCard;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "UserCardController")
@RequestMapping("/kff/userCard")
public class UserCardController extends BaseController {
	private static Logger logger = Logger.getLogger(UserCardController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	/**
	 * 首页审核展示状态 status 1待审核 2 审核成功 3 审核未通过 4 未提交
	 * 
	 * @return
	 */
	@RequestMapping(value = "/usercardStatus", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity usercardStatus(HttpServletRequest request, HttpServletResponse response) {

		// 创建返回体
		BaseResponseEntity bre = new BaseResponseEntity();
		// 创建返回的map
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject map1 = getParamMapFromRequestPolicy(request);
			String token = (String) map1.get("token");

			// 根据用户token 查找到用户的userID
			Integer userId = AccountTokenUtil.decodeAccountToken(token);

			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}

			if (null == token) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			// 返回状态
			Integer statusNum = kffRmiService.selectUserCardStatusByUserId(userId);

			// 移动端登陆
			KFFUser user = kffRmiService.findUserById(userId);

			// 用户没有进行审核
			if (4 == statusNum) {
				// 用户信息审核通过 // 已经进行实名验证
				// 就直接查询usercard表返回查询结果
				map.put("status", 4);
				bre.setData(map);
				return bre;
			}

			if (3 == statusNum) {
				// 审核没有通过
				//
				map.put("status", 3);
				UserCard userCard = kffRmiService.selectUserCardByUserId(userId);
				map.put("reason", userCard.getNotPassReason());
				bre.setData(map);
				return bre;
			}
			if (1 == statusNum) {
				// 审核中
				//
				map.put("status", 1);
				bre.setData(map);
				return bre;
			}
			if (2 == statusNum) {
				// 审核成功
				//
				map.put("status", 2);
				UserCard userCard = kffRmiService.selectUserCardByUserId(userId);
				map.put("uesrRealName", userCard.getUserrealname());
				map.put("uesrcardNum", userCard.getUsercardNum());
				bre.setData(map);
				return bre;
			}
			if (5 == statusNum) {
				// 移动端没有进行初始化表
				kffRmiService.setUserCardAuthentication(user.getUserId(), user.getMobile());
				map.put("status", 4);
				bre.setData(map);
				return bre;
			}
		} catch (RestServiceException e) {
			logger.error("UserCardController usercardStatus reason:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("UserCardController usercardStatus, reason：{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
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
	@ResponseBody
	public BaseResponseEntity uploadUserCord(HttpServletRequest request, HttpServletResponse response) {
		// 创建返回体
		BaseResponseEntity bre = new BaseResponseEntity();
		// 创建返回的map
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject map1 = getParamMapFromRequestPolicy(request);
			String userRealName = (String) map1.get("userRealName");
			String userCardNum = (String) map1.get("userCardNum");
			String photoIviews = (String) map1.get("photoIviews");
			String token = (String) map1.get("token");
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

			/*	String userRealNamefmt = RegexUtil.LOGIN_NAME;
				// 判断身份证号是否符合标准
				if (!userRealName.matches(userRealNamefmt)) {
					throw new RestServiceException(RestErrorCode.VCNAME_INCORRECT);
				}*/
			// 判断用户传入的身份证号是否重复提交 返回 身份证号码已被实名验证

			if (null != kffRmiService.selectUserCardNum(userCardNum)) {
				throw new RestServiceException(RestErrorCode.USER_IDCARD_SUBMITED);
			}

			// 并将status设置成待审核状态
			kffRmiService.updataUserIdStstus(userRealName, userCardNum, photoIviews, userId);
			map.put("status", 1);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("UserCardController uploadUserCard method:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("UserCardController uploadUserCard method:{}", e);
			e.printStackTrace();
			return this.resResult(RestErrorCode.SYS_ERROR);
		}
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
	@RequestMapping(value = "/submitUserCardTiFormAgain", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity submitUserCardTiFormAgain(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();

		// 点击重新提交 在数据库中重新插入新的一行数据
		try {
			JSONObject map1 = getParamMapFromRequestPolicy(request);
			String token = (String) map1.get("token");
			Integer userId = AccountTokenUtil.decodeAccountToken(token);
			UserCard userCard = new UserCard();
			userCard.setCreatetime(new Date());
			userCard.setStatus(4);
			userCard.setValid(1);
			userCard.setUpdatatime(new Date());
			userCard.setUserid(userId);
			kffRmiService.saveUserIdCard(userCard);

			// 向前台展示信息
			List<Authentication> authenticationFromDB = kffRmiService.selectAuthenticationByUserId(userId);
			if (CollectionUtils.isNotEmpty(authenticationFromDB)) {
				resMap.put("status", authenticationFromDB.get(0).getStatus());
				resMap.put("notpassreason", authenticationFromDB.get(0).getNotpassreason());
			}

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
