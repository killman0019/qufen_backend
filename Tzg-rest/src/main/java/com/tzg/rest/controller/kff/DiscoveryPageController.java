package com.tzg.rest.controller.kff;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFProjectRmiService;
import com.tzg.rmi.service.KFFUserRmiService;

@Controller(value = "RankingListController")
@RequestMapping("/kff/rankingList")
public class DiscoveryPageController extends BaseController {
	private static Logger logger = Logger.getLogger(DiscoveryPageController.class);
	@Autowired
	private KFFProjectRmiService kFFProjectRmiService;

	@Autowired
	private KFFUserRmiService kffUserRmiService;

	/**
	 * 获得最热项目榜
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getHotProjectPage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getHotProjectPage(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer pageIndex = (Integer) params.get("pageIndex");
			Integer pageSize = (Integer) params.get("pageSize");
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			/*if (null == userId) {
				throw new RestServiceException("用户没有登录!请重新登录");
			}*/

			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<ProjectResponse> projectResponsePage = kFFProjectRmiService.selectHotProjectPage(userId, query);
			System.err.println("projectResponsePage" + JSON.toJSONString(projectResponsePage));
			map.put("projectHot", projectResponsePage);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("RankingListController getHotProjectPage:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RankingListController getHotProjectPage:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 获得项目评分榜
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getEvaProjectPage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getEvaProjectPage(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer pageIndex = (Integer) params.get("pageIndex");
			Integer pageSize = (Integer) params.get("pageSize");
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			/*if (null != userId) {
				throw new RestServiceException("用户没有登录!请重新登录");
			}*/

			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<ProjectResponse> projectResponsePage = kFFProjectRmiService.selectEvaProjectPage(userId, query);
			System.err.println("projectResponsePage" + JSON.toJSONString(projectResponsePage));
			map.put("EvaProjectPage", projectResponsePage);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("RankingListController getEvaProjectPage:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RankingListController getEvaProjectPage:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 获得KOL榜
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getKOLProjectPage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getKOLProjectPage(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer pageIndex = (Integer) params.get("pageIndex");
			Integer pageSize = (Integer) params.get("pageSize");
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			/*	if (null != userId) {
					throw new RestServiceException("用户没有登录!请重新登录");
				}*/

			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<KFFUser> kFFUserPage = kffUserRmiService.selectKOLProjectPage(userId, query);
			System.err.println("kFFUserPage" + JSON.toJSONString(kFFUserPage));
			map.put("kFFUserPage", kFFUserPage);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("RankingListController getKOLProjectPage:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RankingListController getKOLProjectPage:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

}
