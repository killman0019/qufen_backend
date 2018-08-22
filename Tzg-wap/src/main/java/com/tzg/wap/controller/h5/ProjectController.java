package com.tzg.wap.controller.h5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.HtmlUtils;
import com.tzg.entitys.kff.dprojectType.DprojectType;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.project.SubmitKFFProjectRequest;
import com.tzg.entitys.kff.user.KFFUser;
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
	private static Logger logger = Logger.getLogger(ProjectController.class);

	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private KFFProjectRmiService kFFProjectRmiService;
	@Autowired
	private KFFProjectPostRmiService kffProjectPostRmiService;
	@Autowired
	private KFFUserRmiService kffUserService;
	
	
	/** 
	* @Title: getProjectByTabId 
	* @Description: TODO <项目列表接口>
	* @author linj <方法创建作者>
	* @create 上午10:57:16
	* @param @param request 
	* @param @param token 用户登录的token
	* @param @param pageIndex 第几页
	* @param @param pageSize 每页的条数
	* @param @param tabId tab栏Id:1-查询全部项目，2-查询用户关注的project列表，
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午10:57:16
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "getProjectByTabId", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity getProjectByTabId(HttpServletRequest request,String token,Integer pageIndex,Integer pageSize,
			Integer tabId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.isBlank(token)&&pageIndex==null&&pageSize==null&&tabId==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				token = (String) requestContent.get("token");
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
				tabId = (Integer) requestContent.get("tabId");
			}
			if (StringUtils.isBlank(token)||pageIndex==null||pageSize==null||tabId==null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			
			Integer userId = getUserIdByToken(token);
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<ProjectResponse> projectResponsePage = kFFProjectRmiService.showProjectListNew(tabId, userId, query);
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
	@ResponseBody
	@RequestMapping(value = "/index", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity index(HttpServletRequest request,String token,Integer projectId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.isBlank(token)&&projectId==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				token = (String) requestContent.get("token");
				projectId = (Integer) requestContent.get("projectId");
			}
			
			if(StringUtils.isBlank(token)||projectId==null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer userId = getUserIdByToken(token);
			ProjectResponse project = kffRmiService.findProjectById(userId, projectId);
			map.put("project", project);
			
			KFFUser loginUser = null;
			if(StringUtils.isNotBlank(token)) {
				loginUser = kffUserService.findById(userId);
			}
			Integer type = 2;// 取关注人
			// 20180613 去掉，改为 精选评测，精选打假（讨论）
			// https://www.tapd.cn/21950911/bugtrace/bugs/view?bug_id=1121950911001000461
			// 2条7天内回复数最高的讨论帖子
			List<PostResponse> hotDiscuss = kffRmiService.findHotDiscussList(projectId,type,loginUser);
			map.put("hotDiscuss", hotDiscuss);
			// 点赞量超过10 & 排名前2的内容
			List<PostResponse> hotEva = kffProjectPostRmiService.findHotEvaList(projectId,type,loginUser);
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
	* @Title: evaluationList 
	* @Description: TODO <项目主页中的评测列表接口>
	* @author linj <方法创建作者>
	* @create 下午4:58:51
	* @param @param request
	* @param @param projectId 项目id
	* @param @param pageIndex 第几页
	* @param @param pageSize 每页几条
	* @param @param sortField ：praise_num：按点赞量排序，为空时：按时间降序排序
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午4:58:51
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/evaluationList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity evaluationList(HttpServletRequest request,Integer projectId,Integer pageIndex,
			Integer pageSize,String sortField,String token,Integer kffUserId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if(projectId==null&&pageIndex==null&&pageSize==null&&StringUtils.isBlank(sortField)&&
					StringUtils.isBlank(token)) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				sortField = (String) requestContent.get("sortField");
				projectId = (Integer) requestContent.get("projectId");
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
				token = (String) requestContent.get("token");
				kffUserId = (Integer) requestContent.get("kffUserId");
			}
			if(projectId==null||pageIndex==null||pageSize==null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			PaginationQuery query = new PaginationQuery();
			KFFUser loginUser = null;
			if(StringUtils.isNotBlank(token)) {
				Integer userId = getUserIdByToken(token);
				loginUser = kffUserService.findById(userId);
				if(null!=kffUserId) {
					query.addQueryData("createUserId", kffUserId);
				}
			}
			if(null!=projectId) {
				query.addQueryData("projectId", projectId);
			}
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "1");
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			if (StringUtils.isNotBlank(sortField)) {
				query.addQueryData("sql_keyword_orderByc", sortField);
				query.addQueryData("sql_keyword_sortc", "desc");
			} else {
				query.addQueryData("sql_keyword_orderBy", "post_id");
				query.addQueryData("sql_keyword_sort", "desc");
			}
			Integer type = 2;// 取关注人
			PageResult<EvaluationDetailResponse> evaluations = kffRmiService.findPageEvaluationList(query,type,loginUser);
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
	* @Title: articleList 
	* @Description: TODO <项目主页获取文章列表接口>
	* @author linj <方法创建作者>
	* @create 下午5:46:45
	* @param @param request
	* @param @param projectId 项目id
	* @param @param pageIndex 第几页
	* @param @param pageSize 每页几条
	* @param @param sortField ：praise_num：按点赞量排序，为空时：按时间降序排序
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午5:46:45
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/articleList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity articleList(HttpServletRequest request,Integer projectId,Integer pageIndex,
			Integer pageSize,String sortField,String token,Integer kffUserId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if(projectId==null&&pageIndex==null&&pageSize==null&&StringUtils.isBlank(sortField)&&
					StringUtils.isBlank(token)&&kffUserId==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				sortField = (String) requestContent.get("sortField");
				projectId = (Integer) requestContent.get("projectId");
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
				token = (String) requestContent.get("token");
				kffUserId = (Integer) requestContent.get("kffUserId");
			}
			if(projectId==null||pageIndex==null||pageSize==null) {
				bre.setNoRequstData();
				return bre;
			}
			PaginationQuery query = new PaginationQuery();
			KFFUser loginUser = null;
			if(StringUtils.isNotBlank(token)) {
				Integer userId = getUserIdByToken(token);
				loginUser = kffUserService.findById(userId);
				if(null!=kffUserId) {
					query.addQueryData("createUserId", kffUserId);
				}
			}
			Integer type = 2;// 取关注人
			if(null!=projectId) {
				query.addQueryData("projectId", projectId);
			}
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-讨论；3-文章
			query.addQueryData("postType", "3");
			if (StringUtils.isNotBlank(sortField)) {
				query.addQueryData("sql_keyword_orderByc", sortField);
				query.addQueryData("sql_keyword_sortc", "desc");
			} else {
				query.addQueryData("sql_keyword_orderBy", "post_id");
				query.addQueryData("sql_keyword_sort", "desc");
			}
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<PostResponse> articles = kffRmiService.findPageArticleList(query,type,loginUser);
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
	* @Title: discussList 
	* @Description: TODO <项目主页获取讨论列表>
	* @author linj <方法创建作者>
	* @create 下午6:10:36
	* @param @param request
	* @param @param projectId 项目id
	* @param @param pageIndex 第几页
	* @param @param pageSize 每页几条
	* @param @param sortField ：praise_num：按点赞量排序，为空时：按时间降序排序
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午6:10:36
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/discussList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity discussList(HttpServletRequest request,Integer projectId,Integer pageIndex,
			Integer pageSize,String sortField,String token,Integer kffUserId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if(projectId==null&&pageIndex==null&&pageSize==null&&StringUtils.isBlank(sortField)&&
					StringUtils.isBlank(token)&&kffUserId==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				sortField = (String) requestContent.get("sortField");
				projectId = (Integer) requestContent.get("projectId");
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
				token = (String) requestContent.get("token");
				kffUserId = (Integer) requestContent.get("kffUserId");
			}
			if(pageIndex==null||pageSize==null) {
				bre.setNoRequstData();
				return bre;
			}
			PaginationQuery query = new PaginationQuery();
			KFFUser loginUser = null;
			if(StringUtils.isNotBlank(token)) {
				Integer userId = getUserIdByToken(token);
				loginUser = kffUserService.findById(userId);
				if(null!=kffUserId) {
					query.addQueryData("createUserId", kffUserId);
				}
			}
			if(null!=projectId) {
				query.addQueryData("projectId", projectId);
			}
			query.addQueryData("status", "1");
			// 帖子类型：1-评测；2-爆料；3-文章
			query.addQueryData("postType", "2");
			if (StringUtils.isNotBlank(sortField)) {
				query.addQueryData("sql_keyword_orderByc", sortField);
				query.addQueryData("sql_keyword_sortc", "desc");
			} else {
				query.addQueryData("sql_keyword_orderBy", "createTime");
				query.addQueryData("sql_keyword_sort", "desc");
			}
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			Integer type = 2;// 取关注人
			PageResult<PostResponse> discusses = kffRmiService.findPageDisscussList(query,type,loginUser);
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
			// String token = (String) request.getSession().getAttribute("token");
			// Integer userId = getUserIdByToken(token);
			Integer userId = 1;
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
//			String token = (String) request.getSession().getAttribute("token");
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
	@RequestMapping(value = "/getProjectPage", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getProjectPage(HttpServletRequest request, HttpServletResponse response, 
			String projectCode, Integer sortType,Integer pageIndex,Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
//			String token = (String) request.getSession().getAttribute("token");
//			Integer userId = getUserIdByToken(token);
//			if (null == sortType) {
//				sortType = 2;
//			}
//
//			if (StringUtils.isBlank(projectCode)) {
//				throw new RestServiceException("查询内容不能为空");
//			}
//			List<ProjectResponse> projects = kffRmiService.findProjectByCode(sortType, userId, projectCode);
			String token = (String) request.getSession().getAttribute("token");
			pageIndex=pageIndex==null||pageIndex<1?1:pageIndex;
			pageSize=pageSize==null||pageSize<1?20:pageSize;
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			// 1-按关注数量倒序；2-按名称排序
			if (null == sortType) {
				sortType = 2;
			}
			PageResult<ProjectResponse> projects = kffRmiService.findProjectByCodePage(sortType, userId, projectCode,pageIndex,pageSize);
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
				projectCodeAndCName.add(kffProject.getProjectCode() + "/" + kffProject.getProjectChineseName());
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
