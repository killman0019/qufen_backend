package com.tzg.rest.controller.kff;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzg.common.base.BaseRequest;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.entitys.kff.commendation.CommendationRequest;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.message.KFFMessage;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.suggest.SuggestRequest;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserHomeResponse;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "KFFUserHomeController")
@RequestMapping("/kff/userhome")
public class UserHomeController extends BaseController {
	private static Logger logger = Logger.getLogger(UserHomeController.class);

	@Autowired
	private KFFRmiService kffRmiService;

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
	@RequestMapping(value = "/index", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity index(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer userId = (Integer) params.get("userId");
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
	@RequestMapping(value = "/evaluationList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity evaluationList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer loginUserId = getUserIdByToken(token);
			Integer userId = baseRequest.getUserId();
			PaginationQuery query = new PaginationQuery();
			if (userId != null && userId > 0) {
				query.addQueryData("createUserId", userId + "");
			} else {
				query.addQueryData("createUserId", loginUserId + "");
			}
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "1");
			if (StringUtils.isNotBlank(baseRequest.getSortField())) {
				query.addQueryData("sortField", baseRequest.getSortField());
			}
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			query.addQueryData("sql_keyword_orderBy", "createTime");
			query.addQueryData("sql_keyword_sort", "desc");
			PageResult<EvaluationDetailResponse> evaluations = kffRmiService.findPageEvaluationListUserHome(query);
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
	 * 
	 * @Title: discussList
	 * @Description: 获取讨论列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/discussList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity discussList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer loginUserId = getUserIdByToken(token);
			Integer userId = baseRequest.getUserId();
			PaginationQuery query = new PaginationQuery();
			if (userId != null && userId > 0) {
				query.addQueryData("createUserId", userId + "");
			} else {
				query.addQueryData("createUserId", loginUserId + "");
			}
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "2");
			if (StringUtils.isNotBlank(baseRequest.getSortField())) {
				query.addQueryData("sortField", baseRequest.getSortField());
			}
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			query.addQueryData("sql_keyword_orderBy", "createTime");
			query.addQueryData("sql_keyword_sort", "desc");
			PageResult<PostResponse> discusses = kffRmiService.findPageDisscussList(query);
			// 20180507志远去除 我的最新讨论记录 微信群
			// PostResponse myLatestDiscuss = kffRmiService.findMyLatestDiscuss(loginUserId);
			// System.err.println("discusses++++++++++++++" + JSONObject.toJSONString(discusses));
			map.put("discusses", discusses);
			// map.put("myLatestDiscuss", myLatestDiscuss);
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
	 * 
	 * @Title: messageList
	 * @Description: 获取文章列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/articleList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity articleList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer loginUserId = getUserIdByToken(token);
			Integer userId = baseRequest.getUserId();
			PaginationQuery query = new PaginationQuery();
			if (userId != null && userId > 0) {
				query.addQueryData("createUserId", userId + "");
			} else {
				query.addQueryData("createUserId", loginUserId + "");
			}
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "3");
			if (StringUtils.isNotBlank(baseRequest.getSortField())) {
				query.addQueryData("sortField", baseRequest.getSortField());
			}
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			query.addQueryData("sql_keyword_orderBy", "createTime");
			query.addQueryData("sql_keyword_sort", "desc");
			PageResult<PostResponse> articles = kffRmiService.findPageArticleList(query);
			System.err.println(JSON.toJSONString(articles));
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
