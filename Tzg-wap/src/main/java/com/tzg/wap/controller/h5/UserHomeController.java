package com.tzg.wap.controller.h5;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.base.BaseRequest;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserHomeResponse;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.KFFUserRmiService;

@Controller(value = "KFFUserHomeController")
@RequestMapping("/kff/userhome")
public class UserHomeController extends BaseController {
	private static Logger logger = Logger.getLogger(UserHomeController.class);

	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private KFFUserRmiService kffUserService;
	
	/**
	 * 
	 * @Title: 用户首页
	 * @Description: 用户首页 基本信息展示
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity index(HttpServletRequest request,
			HttpServletResponse response,  Integer userId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer loginUserId = getUserIdByToken(token);
			KFFUserHomeResponse userResp = null;
			userResp = kffRmiService.findUserHomeByUserId(loginUserId, userId);
			map.put("user", userResp);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("UserHomeController index:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("UserHomeController index:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: evaluationList
	 * @Description: 获取评测列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/evaluationList", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity evaluationList(HttpServletRequest request,
			HttpServletResponse response, BaseRequest baseRequest) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer loginUserId = getUserIdByToken(token);
			Integer userId = baseRequest.getUserId();
			PaginationQuery query = new PaginationQuery();
			if (userId != null && userId > 0) {
				query.addQueryData("userId", userId + "");
			} else {
				query.addQueryData("userId", loginUserId + "");
			}
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "1");
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			Integer type = 2;// 取关注人
			KFFUser loginUser = null;
			if(StringUtils.isNotBlank(token)) {
				loginUser = kffUserService.findById(loginUserId);
			}
			PageResult<EvaluationDetailResponse> evaluations = kffRmiService
					.findPageEvaluationList(query,type,loginUser);
			map.put("evaluations", evaluations);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("UserHomeController evaluationList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("UserHomeController evaluationList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 主页列表,讨论列表
	 * 
	 * @param request
	 * @param response
	 * @param baseRequest
	 * @return
	 */
	@RequestMapping(value = "/discussList", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity discussList(HttpServletRequest request,
			HttpServletResponse response, BaseRequest baseRequest) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer loginUserId = getUserIdByToken(token);
			Integer userId = baseRequest.getUserId();
			PaginationQuery query = new PaginationQuery();
			if (userId != null && userId > 0) {
				query.addQueryData("userId", userId + "");
			} else {
				query.addQueryData("userId", loginUserId + "");
			}
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "2");
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<PostResponse> discusses = kffRmiService
					.findPageDisscussList(query);
			map.put("discusses", discusses);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("UserHomeController discussList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("UserHomeController discussList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 项目主页,文章列表
	 * 
	 * @param request
	 * @param response
	 * @param baseRequest
	 * @return
	 */
	@RequestMapping(value = "/articleList", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity articleList(HttpServletRequest request,
			HttpServletResponse response, BaseRequest baseRequest) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer loginUserId = getUserIdByToken(token);
			Integer userId = baseRequest.getUserId();
			PaginationQuery query = new PaginationQuery();
			if (userId != null && userId > 0) {
				query.addQueryData("userId", userId + "");
			} else {
				query.addQueryData("userId", loginUserId + "");
			}
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "3");
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<PostResponse> articles = kffRmiService
					.findPageArticleList(query);
			map.put("articles", articles);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("UserHomeController articleList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("UserHomeController articleList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
