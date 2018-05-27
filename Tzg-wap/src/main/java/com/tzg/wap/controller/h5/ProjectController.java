package com.tzg.wap.controller.h5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.base.BaseRequest;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.dprojectType.DprojectType;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.project.SubmitKFFProjectRequest;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "KFFProjectController")
@RequestMapping("/kff/project")
public class ProjectController extends BaseController {
	private static Logger log = Logger.getLogger(ProjectController.class);

	@Autowired
	private KFFRmiService kffRmiService;

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
	public BaseResponseEntity index(HttpServletRequest request, HttpServletResponse response, Integer projectId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer userId = getUserIdByToken(token);
			if (projectId == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_PROJID);
			}
			ProjectResponse project = kffRmiService.findProjectById(userId, projectId);
			map.put("project", project);
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
	public BaseResponseEntity evaluationList(HttpServletRequest request, HttpServletResponse response, BaseRequest baseRequest) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
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
			PageResult<EvaluationDetailResponse> evaluations = kffRmiService.findPageEvaluationList(query);
			map.put("evaluations", evaluations);
			bre.setData(map);
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
	public BaseResponseEntity discussList(HttpServletRequest request, HttpServletResponse response, BaseRequest baseRequest) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			String token = (String) request.getSession().getAttribute("token");
			Integer loginUserId = getUserIdByToken(token);
			Integer projectId = baseRequest.getProjectId();
			if (projectId == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_PROJID);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("projectId", projectId + "");
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "2");
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<PostResponse> discusses = kffRmiService.findPageDisscussList(query);
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
	public BaseResponseEntity articleList(HttpServletRequest request, HttpServletResponse response, BaseRequest baseRequest) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			String token = (String) request.getSession().getAttribute("token");
			Integer loginUserId = getUserIdByToken(token);
			Integer projectId = baseRequest.getProjectId();
			if (projectId == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_PROJID);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("projectId", projectId + "");
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "3");
			query.addQueryData("sortField", "praiseNum");
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<PostResponse> articles = kffRmiService.findPageArticleList(query);
			map.put("articles", articles);
			bre.setData(map);
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
	 * 提交项目
	 * 
	 * @param request
	 * @param response
	 * @param submitRequest
	 * @return
	 */
	@RequestMapping(value = "/submitProject", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity submitProject(HttpServletRequest request, HttpServletResponse response, SubmitKFFProjectRequest submitRequest) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
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
	 * 获得项目类型列表
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/projectTypeList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity projectTypeList(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String token = (String) request.getSession().getAttribute("token");
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
	 * 搜索项目
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param projectCode
	 * @param sortType
	 * @return
	 */
	@RequestMapping(value = "/searchProjects", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity searchProjects(HttpServletRequest request, HttpServletResponse response, String projectCode, Integer sortType) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer userId = getUserIdByToken(token);
			if (null == sortType) {
				sortType = 2;
			}

			if (StringUtils.isBlank(projectCode)) {
				throw new RestServiceException("查询内容不能为空");
			}
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
	 * 遍历所有项目名称prject_code / project _chanese_name
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/selectProjectName", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity selectProjectName(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		/*String token = (String) request.getSession().getAttribute("token");
		Integer userId = getUserIdByToken(token);*/
		/*if (null == userId) {
			throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		}*/
		List<KFFProject> kffProjects = kffRmiService.findProjectName();
		if (null == kffProjects) {
			throw new RestServiceException(RestErrorCode.SYS_ERROR);
		}
		try {
			List<String> projectCodeAndCName = new ArrayList<String>();
			for (KFFProject kffProject : kffProjects) {
				projectCodeAndCName.add(kffProject.getProjectChineseName() + "/" + kffProject.getProjectCode());
			}

			map.put("projectCodeAndCName", projectCodeAndCName);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ProjectController selectProjectName:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ProjectController selectProjectName:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

}