package com.tzg.rest.controller.kff;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.base.BaseRequest;
import com.tzg.common.constants.KFFConstants;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.SyseUtil;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.dprojectType.DprojectType;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.project.SubmitKFFProjectRequest;
import com.tzg.entitys.kff.projectManage.ProjectManageTabResponse;
import com.tzg.entitys.kff.transactionpair.TransactionPairResponse;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFProjectPostRmiService;
import com.tzg.rmi.service.KFFProjectRmiService;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.KFFUserRmiService;

@Controller(value = "KFFProjectController")
@RequestMapping("/kff/project")
public class ProjectController extends BaseController {
	private static Logger log = Logger.getLogger(ProjectController.class);

	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private KFFProjectPostRmiService kffProjectPostRmiService;
	@Autowired
	private KFFProjectRmiService kFFProjectRmiService;
	@Autowired
	private KFFUserRmiService kffUserService;

	/**
	 * 
	 * @Title: index
	 * @Description: 项目主页
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
			Integer projectId = (Integer) params.get("projectId");
			Integer userId = null;
			KFFUser loginUser = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
				loginUser = kffUserService.findById(userId);
			}
			Integer type = 2;// 取关注人
			if (projectId == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_PROJID);
			}
			ProjectResponse project = kffRmiService.findProjectById(userId, projectId);
			map.put("project", project);
			// System.err.println(JSON.toJSONString(project));
			// 20180613 去掉，改为 精选评测，精选打假（讨论）
			// https://www.tapd.cn/21950911/bugtrace/bugs/view?bug_id=1121950911001000461
			// 2条7天内回复数最高的讨论帖子
			List<PostResponse> hotDiscuss = kffRmiService.findHotDiscussList(projectId, type, loginUser);
			map.put("hotDiscuss", hotDiscuss);
			// 点赞量超过10 & 排名前2的内容
			List<PostResponse> hotEva = kffProjectPostRmiService.findHotEvaList(projectId, type, loginUser);
			map.put("hotEva", hotEva);

			// 项目专业评测统计信息
			Map<String, Object> statMap = kffProjectPostRmiService.selectProjectEvaStatSelf(projectId);
			map.put("projectEvaStat", statMap.get("projectEvaStat"));
			map.put("totalProEvaRaterNum", statMap.get("totalProEvaRaterNum"));
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController index:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController index:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: index
	 * @Description: 项目分数统计
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/evaStatScore", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity evaStatScore(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer projectId = (Integer) params.get("projectId");
			Integer userId = getUserIdByToken(token);
			if (projectId == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_PROJID);
			}
			ProjectResponse project = kffRmiService.findProjectById(userId, projectId);
			map.put("project", project);
			// 2条7天内回复数最高的讨论帖子
			// List<PostResponse> hotDiscuss = kffRmiService.findHotDiscussList(projectId);
			// map.put("hotDiscuss", hotDiscuss);

			PaginationQuery hotQuery = new PaginationQuery();
			hotQuery.addQueryData("status", "1");
			hotQuery.addQueryData("projectId", projectId + "");
			hotQuery.addQueryData("postType", KFFConstants.POST_TYPE_EVALUATION + "");
			hotQuery.addQueryData("parentCommentsIdNull", "YES");

			// 点赞数最多的2个评论
			hotQuery.addQueryData("sortField", "praise_num");
			hotQuery.setPageIndex(1);
			hotQuery.setRowsPerPage(2);
			List<Comments> hotComments = kffRmiService.findPageHotCommentsList(userId, projectId, hotQuery);
			map.put("hotComments", hotComments);

			// 项目简单评测统计信息
			map = kffProjectPostRmiService.findProjectEvaStatScore(projectId);// 宋总代码
			// map.put("evaGradeStat", proEvaStat);
			KFFProject kffProject = kffProjectPostRmiService.selectProjectByProjectId(projectId);
			if (null != kffProject) {
				BigDecimal totalScore = kffProject.getTotalScore();
				float totalScoreFlo = totalScore.floatValue();
				map.put("totalScore", totalScoreFlo);
			}
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController index:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController index:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: evaluationList
	 * @Description: 项目 分页获取简单评测列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/simpleEvaluationList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity simpleEvaluationList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer loginUserId = getUserIdByToken(token);
			Integer projectId = baseRequest.getProjectId();
			if (projectId == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_PROJID);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("projectId", projectId + "");

			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "1");
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			if (StringUtils.isNotBlank(baseRequest.getSortField())) {
				query.addQueryData("sortField", baseRequest.getSortField());
			}
			PageResult<EvaluationDetailResponse> evaluations = kffProjectPostRmiService.findPageSimpleEvaluationList(query, loginUserId);
			map.put("evaluations", evaluations);
			bre.setData(map);
			SyseUtil.systemErrOutJson(bre);
		} catch (RestServiceException e) {
			logger.error("ProjectController evaluationList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController evaluationList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: evaluationList
	 * @Description: 项目 获取评测列表
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
			Integer projectId = baseRequest.getProjectId();
			if (projectId == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_PROJID);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("projectId", projectId + "");

			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "1");
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());

			if (StringUtils.isNotBlank(baseRequest.getSortField())) {
				query.addQueryData("sql_keyword_orderBy", "tp." + baseRequest.getSortField());
				query.addQueryData("sql_keyword_sort", "desc");
				query.addQueryData("sql_keyword_orderByc", "tp.post_id");
				query.addQueryData("sql_keyword_sortc", "desc");
			} else {
				query.addQueryData("sql_keyword_orderBy", "tp.post_id");
				query.addQueryData("sql_keyword_sort", "desc");
				query.addQueryData("sql_keyword_orderByc", "tp.praise_num");
				query.addQueryData("sql_keyword_sortc", "desc");
			}
			Integer type = 2;// 取关注人
			KFFUser loginUser = null;
			if (StringUtils.isNotBlank(token)) {
				Integer userId = getUserIdByToken(token);
				loginUser = kffUserService.findById(userId);
			}
			PageResult<EvaluationDetailResponse> evaluations = kffRmiService.findPageEvaluationList(query, type, loginUser);
			map.put("evaluations", evaluations);
			bre.setData(map);
			SyseUtil.systemErrOutJson(bre);
		} catch (RestServiceException e) {
			logger.error("ProjectController evaluationList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController evaluationList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: discussList
	 * @Description: 项目主页获取讨论列表
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
			Integer projectId = baseRequest.getProjectId();
			if (projectId == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_PROJID);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("projectId", projectId + "");
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "2");
			if (StringUtils.isNotBlank(baseRequest.getSortField())) {
				query.addQueryData("sql_keyword_orderBy", baseRequest.getSortField());
				query.addQueryData("sql_keyword_sort", "desc");
				query.addQueryData("sql_keyword_orderByc", "post_id");
				query.addQueryData("sql_keyword_sortc", "desc");
			} else {
				query.addQueryData("sql_keyword_orderBy", "post_id");
				query.addQueryData("sql_keyword_sort", "desc");
				query.addQueryData("sql_keyword_orderByc", "praise_num");
				query.addQueryData("sql_keyword_sortc", "desc");
			}

			KFFUser loginUser = null;
			if (StringUtils.isNotBlank(token)) {
				Integer userId = getUserIdByToken(token);
				loginUser = kffUserService.findById(userId);
			}
			Integer type = 2;// 取关注人
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<PostResponse> discusses = kffRmiService.findPageDisscussList(query, type, loginUser);
			map.put("discusses", discusses);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController discussList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController discussList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: messageList
	 * @Description: 项目主页获取文章列表
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
			Integer projectId = baseRequest.getProjectId();
			if (projectId == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_PROJID);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("projectId", projectId + "");
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "3");
			if (StringUtils.isNotBlank(baseRequest.getSortField())) {
				query.addQueryData("sql_keyword_orderBy", baseRequest.getSortField());
				query.addQueryData("sql_keyword_sort", "desc");
				query.addQueryData("sql_keyword_orderByc", "post_id");
				query.addQueryData("sql_keyword_sortc", "desc");

			} else {
				query.addQueryData("sql_keyword_orderBy", "post_id");
				query.addQueryData("sql_keyword_sort", "desc");
				query.addQueryData("sql_keyword_orderByc", "praise_num");
				query.addQueryData("sql_keyword_sortc", "desc");
			}
			KFFUser loginUser = null;
			if (StringUtils.isNotBlank(token)) {
				Integer userId = getUserIdByToken(token);
				// query.addQueryData("createUserId", "userId");
				loginUser = kffUserService.findById(userId);
			}
			Integer type = 2;// 取关注人
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<PostResponse> articles = kffRmiService.findPageArticleList(query, type, loginUser);
			map.put("articles", articles);
			bre.setData(map);
			SyseUtil.systemErrOutJson(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController articleList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController articleList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * @Title: submitProject
	 * @Description: 提交项目,默认待审核状态
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/submitProject", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity submitProject(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			SubmitKFFProjectRequest submitRequest = getParamMapFromRequestPolicy(request, SubmitKFFProjectRequest.class);
			String token = submitRequest.getToken();
			Integer userId = getUserIdByToken(token);
			submitRequest.setSubmitUserId(userId);
			kffRmiService.submitProject(submitRequest);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController submitProject:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController submitProject:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: projectTypeList
	 * @Description: 获取项目类型列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/projectTypeList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity projectTypeList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			List<DprojectType> projectTypes = kffRmiService.findAllProjectTypes();
			map.put("projectTypes", projectTypes);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController projectTypeList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController projectTypeList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: searchProjects
	 * @Description: 搜索获取项目类型列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/searchProjects", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity searchProjects(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			String projectCode = (String) params.get("projectCode");
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			// 1-按关注数量倒序；2-按名称排序
			int sortType = (Integer) params.get("sortType") == null ? 2 : (Integer) params.get("sortType");

			List<ProjectResponse> projects = kffRmiService.findProjectByCode(sortType, userId, projectCode);
			map.put("projects", projects);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController searchProjects:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController searchProjects:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: getProjectPage
	 * @Description: 分页搜索获取项目类型列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/getProjectPage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getProjectPage(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			String projectCode = (String) params.get("projectCode");
			Integer pageIndex = (Integer) params.get("pageIndex");
			Integer pageSize = (Integer) params.get("pageSize");
			pageIndex = pageIndex == null || pageIndex < 1 ? 1 : pageIndex;
			pageSize = pageSize == null || pageSize < 1 ? 20 : pageSize;
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			// 1-按关注数量倒序；2-按名称排序
			int sortType = (Integer) params.get("sortType") == null ? 2 : (Integer) params.get("sortType");
			sortType = 1;
			PageResult<ProjectResponse> projects = kffRmiService.findProjectByCodePage(sortType, userId, projectCode, pageIndex, pageSize);
			map.put("projects", projects);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController searchProjects:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController searchProjects:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 获得项目tab栏
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getProjectTab", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getProjectTab(HttpServletRequest request) {

		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {

			List<ProjectManageTabResponse> tabs = kFFProjectRmiService.showTab();
			// System.err.println("tabs+++++++++++++++++" + JSON.toJSONString(tabs));
			map.put("tabs", tabs);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController getProjectTab:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController getProjectTab:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 根据前台传来的tabid展示相关的project;列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getProjectByTabId", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getProjectByTabId(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer pageIndex = (Integer) params.get("pageIndex");
			Integer pageSize = (Integer) params.get("pageSize");
			Integer tabId = (Integer) params.get("tabId");
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			if (null == tabId) {
				throw new RestServiceException("项目tab栏异常,请联系客服");
			}
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<ProjectResponse> projectResponsePage = kFFProjectRmiService.showProjectListNew(tabId, userId, query);
			// System.err.println("projectResponsePage+++++++++++++++++++++++" +
			// JSON.toJSONString(projectResponsePage));
			map.put("projectResponsePage", projectResponsePage);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController getProjectTab:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController getProjectTab:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 根据传来的projectId获取交易所和相关交易对
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getExchangeAndTranPair", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getExchangeAndTranPair(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			Integer pageIndex = (Integer) params.get("pageIndex");
			Integer pageSize = (Integer) params.get("pageSize");
			Integer projectId = (Integer) params.get("projectId");
			if (null == projectId) {
				throw new RestServiceException("项目相关信息出错");
			}
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<TransactionPairResponse> exchangeAndPtranPairPage = kFFProjectRmiService.selectExchangeAndTranPair(projectId, query);
			map.put("TransactionPairResponse", exchangeAndPtranPairPage);
			bre.setData(map);
			SyseUtil.systemErrOutJson(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController getExchangeAndTranPair:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController getExchangeAndTranPair:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
