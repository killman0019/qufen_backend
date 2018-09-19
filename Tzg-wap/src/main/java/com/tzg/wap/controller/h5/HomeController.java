package com.tzg.wap.controller.h5;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.constants.KFFConstants;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.HtmlUtils;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.rest.Base64Util;
import com.tzg.entitys.kff.article.ArticleDetailResponse;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.comments.CommentsShareRequest;
import com.tzg.entitys.kff.discuss.DiscussDetailResponse;
import com.tzg.entitys.kff.discuss.DiscussShare;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.evaluation.ProjectEvaluationDetailShareResponse;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFPostRmiService;
import com.tzg.rmi.service.KFFProjectPostRmiService;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.KFFUserRmiService;
import com.tzg.rmi.service.SystemParamRmiService;
import com.tzg.wap.utils.DateUtil;

@Controller(value = "KFFHomeController")
@RequestMapping("/kff/home")
public class HomeController extends BaseController {

	private static Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private SystemParamRmiService systemParamRmiService;
	@Autowired
	private KFFProjectPostRmiService kffProjectPostRmiService;
	@Autowired
	private KFFUserRmiService kffUserService;
	@Autowired
	private KFFPostRmiService postRmiService;
	
	/** 
	* @Title: newestList 
	* @Description: TODO <最新列表接口>
	* @author linj <方法创建作者>
	* @create 下午3:12:03
	* @param @param request
	* @param @param pageIndex 第几页
	* @param @param pageSize 每页几条
	* @param @param token 用户登录唯一标识
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午3:12:03
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/newestList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity newestList(HttpServletRequest request,Integer pageIndex,Integer pageSize,
			String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(pageIndex==null&&pageSize==null&&StringUtils.isBlank(token)) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
				token = (String) requestContent.get("token");
			}
			if(null==pageIndex||pageSize==null) {
				bre.setNoRequstData();
				return bre;
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			Integer type = 2;// 取关注人
			PageResult<Post> rewards = postRmiService.findPageNewestList(query,userId,type,pageSize);
			if(null!=rewards&&!rewards.getRows().isEmpty()) {
				bre.setData(rewards);
				return bre;
			}
			bre.setNoDataMsg();
		} catch (RestServiceException e) {
			logger.error("HomeController newestList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController newestList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: recommendList 
	* @Description: TODO <首页评测接口>
	* @author linj <方法创建作者>
	* @create 上午9:46:48
	* @param @param request
	* @param @param token
	* @param @param pageIndex
	* @param @param pageSize
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午9:46:48
	* @updator <推荐列表======>修改为评测接口>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/recommendList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity recommendList(HttpServletRequest request,String token,Integer pageIndex,
			Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if(pageIndex==null&&pageSize==null&&StringUtils.isBlank(token)) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
				token = (String) requestContent.get("token");
			}
			if (pageIndex==null||pageSize==null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("status", "1");
			query.addQueryData("stickTop", "1");
			query.addQueryData("sortField", "stick_updateTime");
			query.addQueryData("notDiscuss", "true");
			// query.addQueryData("praiseNum", "10");
			// 帖子类型：1-评测；2-讨论；3-文章
			// query.addQueryData("postType", "1");
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			Integer type = 2;// 取关注人
			PageResult<PostResponse> recommends = kffRmiService.findPageEvaluatingList(userId, query, type);
			map.put("recommends", recommends);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController recommendList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController recommendList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: newRecommendList 
	* @Description: TODO <新版首页推荐接口>
	* @author linj <方法创建作者>
	* @create 上午11:01:27
	* @param @param request
	* @param @param token
	* @param @param pageIndex
	* @param @param pageSize
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午11:01:27
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/newRecommendList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity newRecommendList(HttpServletRequest request, HttpServletResponse response,
			String token,Integer pageIndex,Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(pageIndex==null&&pageSize==null&&StringUtils.isBlank(token)) {
			JSONObject requestContent = HtmlUtils.getRequestContent(request);
			pageIndex = (Integer) requestContent.get("pageIndex");
			pageSize = (Integer) requestContent.get("pageSize");
			token = (String) requestContent.get("token");
		}
		if (pageIndex==null||pageSize==null) {
			throw new RestServiceException(RestErrorCode.MISSING_ARGS);
		}
		try {
			Integer userId =  null;
			if(StringUtils.isNotBlank(token)) {
				userId =  getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			Integer type = 2;// 取关注人
			Integer nowCount = pageSize;
			PageResult<PostResponse> recommends = kffRmiService.findPageRecommendList(userId, query, type, nowCount);
			map.put("recommends", recommends);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController recommendList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController recommendList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	
	/** 
	* @Title: followList 
	* @Description: TODO <主页-关注列表>
	* @author linj <方法创建作者>
	* @create 下午5:51:25
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午5:51:25
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/followList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity followList(HttpServletRequest request,Integer pageIndex,Integer pageSize,
			String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if(pageIndex==null&&pageSize==null&&StringUtils.isBlank(token)) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
				token = (String) requestContent.get("token");
			}
			if(pageIndex==null||pageSize==null||StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("statusc", "1");
			query.addQueryData("sort", "tbf.createTime");
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			Integer type = 2;// 取关注人
			Integer	userId = getUserIdByToken(token);
			KFFUser	loginUser = kffUserService.findById(userId);
			PageResult<PostResponse> follows = kffProjectPostRmiService.findPageForFollowList(userId, query,type,loginUser);
			map.put("follows", follows);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController followList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController followList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 
	 * @Title: projectRankList
	 * @Description: 项目排行列表--青天榜
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/projectRankList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity projectRankList(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("status", "1");
			query.addQueryData("sortField", "totalScore");
			query.setPageIndex(1);
			query.setRowsPerPage(20);
			List<ProjectResponse> rankList = kffRmiService.findPageProjectRankList(userId, query);
			map.put("rankList", rankList);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController projectRankList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController projectRankList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/** 
	* @Title: articleDetail 
	* @Description: TODO <文章详情>
	* @author linj <方法创建作者>
	* @create 下午2:12:58
	* @param @param response
	* @param @param request
	* @param @param postId 帖子-文章id
	* @param @param token 用户登录的唯一标识
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午2:12:58
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/articleDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity articleDetail(HttpServletResponse response, HttpServletRequest request, 
			Integer postId,String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if(StringUtils.isBlank(token)&&postId==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				postId = (Integer) requestContent.get("postId");
				token = (String) requestContent.get("token");
			}
			if (postId==null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer userId = null;
			if(StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			Integer type = 2;// 取关注人
			ArticleDetailResponse article = kffRmiService.findArticleDetail(userId,type, postId);
			map.put("articleDetail", article);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 分享文章详情
	 * 
	 * @param response
	 * @param request
	 * @param token
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/shareArticleDetail", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity shareArticleDetail(HttpServletResponse response, HttpServletRequest request, Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if(null==postId) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				if (null != requestContent) {
					postId = Integer.valueOf((String) requestContent.get("postId"));
				}
			}
			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}

			ArticleDetailResponse article = kffRmiService.findArticleDetailForShare(postId);
			map.put("articleDetail", article);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 文章评论列表
	 * 
	 * @param response
	 * @param request
	 * @param token
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/articleCommentList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity articleCommentList(HttpServletResponse response, HttpServletRequest request, Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery hotQuery = new PaginationQuery();
			hotQuery.addQueryData("status", "1");
			hotQuery.addQueryData("postId", postId + "");
			hotQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
			hotQuery.addQueryData("parentCommentsIdNull", "YES");

			// 点赞数最多的2个评论
			hotQuery.addQueryData("sortField", "praiseNum");
			hotQuery.setPageIndex(1);
			hotQuery.setRowsPerPage(2);
			List<Comments> hotComments = kffRmiService.findPageHotCommentsList(userId, postId, hotQuery);
			map.put("hotComments", hotComments);
			PaginationQuery newQuery = new PaginationQuery();
			newQuery.addQueryData("status", "1");
			newQuery.addQueryData("postId", postId + "");
			newQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
			// 最新的20个评论
			newQuery.setPageIndex(1);
			newQuery.setRowsPerPage(20);
			PageResult<Comments> newestComments = kffRmiService.findPageNewestComments(userId, postId, newQuery);
			map.put("newestComments", newestComments == null ? null : newestComments.getRows());

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController articleCommentList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController articleCommentList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: articleCommentReplyList
	 * @Description: 针对一个评论的所有评论列表 （评论的回复列表页）
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/articleCommentReplyList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity articleCommentReplyList(HttpServletRequest request, HttpServletResponse response, Integer commentsId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			if (commentsId == null || commentsId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_COMMENTSID);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("status", "1");
			query.addQueryData("parentCommentsId", commentsId + "");
			query.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
			// 前30个针对评论的评论
			// query.addQueryData("sortField", "praiseNum");
			query.setPageIndex(1);
			query.setRowsPerPage(30);
			List<Comments> childComments = kffRmiService.findAllChildCommentsList(userId, commentsId, query);
			map.put("childComments", childComments);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController articleCommentReplyList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController articleCommentReplyList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 讨论详情页
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param postId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/discussDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity discussDetail(HttpServletRequest request, HttpServletResponse response, 
			String token,Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if(StringUtils.isBlank(token)&&postId==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				postId = (Integer) requestContent.get("postId");
				token = (String) requestContent.get("token");
			}
			if (postId==null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer userId = null;
			if(StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			Integer type = 2;// 取关注人
			DiscussDetailResponse discuss = kffRmiService.findDiscussDetail(userId, type,postId);
			map.put("discussDetail", discuss);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController discussDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController discussDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}


	/**
	 * 讨论分享页
	 * 
	 * @param request
	 * @param response
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/discussCommentListShare", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity discussCommentListShare(HttpServletRequest request, HttpServletResponse response, Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if(null==postId) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				if (null != requestContent) {
					postId = Integer.valueOf((String) requestContent.get("postId"));
				}
			}
			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}

			DiscussShare discussShare = kffRmiService.findDiscussDetailWAP(postId);
			map.put("discussShare", discussShare);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController discussCommentListShare:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController discussCommentListShare:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: evaluationDetail
	 * @Description: 评测详情
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/evaluationDetail", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity evaluationDetail(HttpServletRequest request, HttpServletResponse response, 
			String token, Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if(StringUtils.isBlank(token)&&postId==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				postId = (Integer) requestContent.get("postId");
				token = (String) requestContent.get("token");
			}
			if (postId==null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer userId = null;
			if(StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			Integer type = 2;// 取关注人
			EvaluationDetailResponse evaluationDetail = kffRmiService.findEvaluationDetail(userId,type,postId);
			map.put("evaluationDetail", evaluationDetail);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController evaluationDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController evaluationDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: discussCommentList 
	* @Description: TODO <爆料详情页,列表接口>
	* @author linj <方法创建作者>
	* @create 下午5:26:17
	* @param @param request
	* @param @param token
	* @param @param postId
	* @param @param pageIndex
	* @param @param pageSize
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午5:26:17
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/discussCommentList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity discussCommentList(HttpServletRequest request,String token,Integer postId,Integer pageIndex,
			Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if(StringUtils.isBlank(token)&&null==postId&&null==pageIndex
					&&null==pageSize) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				token = (String) requestContent.get("token");
				postId = (Integer) requestContent.get("postId");
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
			}
			if(null==postId||null==pageIndex||null==pageSize) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer userId =  null;
			if(StringUtils.isNotBlank(token)) {
				userId =  getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			query.addQueryData("postId", postId + "");
			query.addQueryData("status", KFFConstants.STATUS_ACTIVE + "");
			query.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS + "");
			query.addQueryData("parentCommentsIdNull", "YES");

			PageResult<Comments> comments = kffRmiService.findPageDiscussCommentsList(userId, query);
			if(null==comments) {
				bre.setNoDataMsg();
				return bre;
			}
			if(comments.getRows().isEmpty()) {
				bre.setNoDataMsg();
				return bre;
			}
			map.put("comments", comments);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController discussCommentList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController discussCommentList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	
	/** 
	* @Title: postCommentList 
	* @Description: TODO <评测,文章详情页,评论列表接口>
	* @author linj <方法创建作者>
	* @create 下午2:10:14
	* @param @param request
	* @param @param token 用户登录唯一标识
	* @param @param postId 评测，文章的id
	* @param @param pageIndex 第几页
	* @param @param pageSize 每页多少条
	* @param @param postType 帖子类型：1-评测；3-文章
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午2:10:14
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/postCommentList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity postCommentList(HttpServletRequest request,String token,Integer postId,
			Integer pageIndex,Integer pageSize,Integer postType) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if(StringUtils.isBlank(token)&&null==postId&&null==pageIndex
					&&null==pageSize&&null==postType) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				token = (String) requestContent.get("token");
				postId = (Integer) requestContent.get("postId");
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
				postType = (Integer) requestContent.get("postType");
			}
			if(null==postId||null==pageIndex
					||null==pageSize||null==postType) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer userId = null;
			if(StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery newQuery = new PaginationQuery();
			newQuery.addQueryData("status", "1");
			newQuery.addQueryData("postId", postId);
			newQuery.addQueryData("postType", postType);
			newQuery.addQueryData("parentCommentsIdNull", "YES");
			newQuery.setPageIndex(pageIndex);
			newQuery.setRowsPerPage(pageSize);
			newQuery.addQueryData("sortField", "praise_num");
			PageResult<Comments> newestComments = kffRmiService.findPageNewestCommentsSelf(userId, postId, newQuery);
			List<Comments> hotComments = new ArrayList<>();
			if (pageIndex == 1) {
				PaginationQuery hotQuery = new PaginationQuery();
				hotQuery.addQueryData("status", "1");
				hotQuery.addQueryData("postId", postId);
				hotQuery.addQueryData("postType", postType);
				newQuery.addQueryData("parentCommentsIdNull", "YES");
				// 点赞数最多的2个评论
				hotQuery.addQueryData("sortField", "praise_num");
				hotQuery.addQueryData("praiseNum", "10");
				hotQuery.setPageIndex(1);
				hotQuery.setRowsPerPage(2);
				hotComments = kffRmiService.findPageHotCommentsListSelf(userId, postId, hotQuery);
			}
			if(null==newestComments&&newestComments==null) {
				bre.setNoDataMsg();
				return bre;
			}
			map.put("hotComments", hotComments);
			map.put("newestComments", newestComments);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController postCommentList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController postCommentList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 分享评测详情
	 * 
	 * @param request
	 * @param response
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/shareEvaluationDetail", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity shareEvaluationDetail(HttpServletRequest request, HttpServletResponse response, Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if(null==postId) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				postId = Integer.valueOf((String) requestContent.get("postId"));
				if (postId == null || postId == 0) {
					throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
				}
			}
			ProjectEvaluationDetailShareResponse findEvaluationDetailForShare = kffRmiService.findEvaluationDetailForShare(postId);
			System.out.println(findEvaluationDetailForShare + "++++++++++++++++++findEvaluationDetailForShare");
			map.put("projectEvaluationDetailResponse", findEvaluationDetailForShare);
			bre.setData(map);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController evaluationDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController evaluationDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 分享评测部分详情
	 * 
	 * @param request
	 * @param response
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/shareEvaluationPartDetail", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity shareEvaluationPartDetail(HttpServletRequest request, HttpServletResponse response, Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
			ProjectEvaluationDetailShareResponse findEvaluationDetailForShare = kffRmiService.findEvaluationDetailForShare(postId);
			System.out.println(findEvaluationDetailForShare + "++++++++++++++++++findEvaluationDetailForShare");
			map.put("projectEvaluationDetailResponse", findEvaluationDetailForShare);
			bre.setData(map);

		} catch (RestServiceException e) {
			logger.error("HomeController evaluationDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController evaluationDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 讨论分享
	 * 
	 * @param request
	 * @param response
	 * @param comment
	 * @return
	 */
	@RequestMapping(value = "/shareComment", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity shareComment(HttpServletRequest request, HttpServletResponse response, Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		System.out.println(postId);
		try {
			// 根据分享的postId 查询评论表
			CommentsShareRequest commentsShareRequest = kffRmiService.findCommentMessage(postId);
			map.put("commentsShareRequest", commentsShareRequest);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("CommentsController saveComment:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("CommentsController saveComment:{}", e);
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
	 * @param imgtype
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/uploadImg", method = { RequestMethod.POST, RequestMethod.GET }, produces = "text/html; charset=utf-8", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public BaseResponseEntity uploadImg(HttpServletRequest request, HttpServletResponse response, String imgdata, Integer imgtype) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			String token = (String) request.getSession().getAttribute("token");
			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			if (StringUtils.isBlank(imgdata)) {
				throw new RestServiceException(RestErrorCode.USER_ICON_DATA_EMPTY);
			}
			if (imgtype == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_IMGTYPE);
			}
			if (imgtype != KFFConstants.IMGTYPE_AVATARS || imgtype != KFFConstants.IMGTYPE_POSTS) {
				throw new RestServiceException(RestErrorCode.WRONG_IMGTYPE);
			}
			Integer userId = AccountTokenUtil.decodeAccountToken(token);
			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}

			SystemParam systemParam = systemParamRmiService.findByCode("upload_local_path");
			String localPath = systemParam.getVcParamValue();
			systemParam = systemParamRmiService.findByCode("upload_file_path");
			String urlPath = systemParam.getVcParamValue();

			if (imgtype == KFFConstants.IMGTYPE_AVATARS) {
				localPath = localPath + "avatars/" + DateUtil.getCurrentYearMonth() + "/";
				String file = localPath + userId + ".jpg";
				Base64Util.decoderBase64File(imgdata, file);
				// 更新用户头像url
				urlPath = urlPath + "/avatars/" + DateUtil.getCurrentYearMonth() + "/";
				file = urlPath + userId + ".jpg";
				KFFUser account = new KFFUser();
				account.setUserId(userId);
				account.setUpdateTime(new Date());
				account.setIcon(file);
				kffRmiService.updateUser(account);
				resMap.put("imgUrl", file);
			} else if (imgtype == KFFConstants.IMGTYPE_POSTS) {
				localPath = localPath + "posts/" + DateUtil.getCurrentYearMonth() + "/";
				String file = localPath + userId + ".jpg";
				Base64Util.decoderBase64File(imgdata, file);
				// 返回imgurl
				urlPath = urlPath + "/posts/" + DateUtil.getCurrentYearMonth() + "/";
				file = urlPath + RandomUtil.produceString(10) + ".jpg";
				KFFUser account = new KFFUser();
				account.setUserId(userId);
				account.setUpdateTime(new Date());
				account.setIcon(file);
				kffRmiService.updateUser(account);
				resMap.put("imgUrl", file);
			}

		} catch (RestServiceException e) {
			logger.error("error in uploadImg method:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("uncatched error in uploadImg method:{}", e);
			e.printStackTrace();
			return this.resResult(RestErrorCode.SYS_ERROR);
		}
		return bre;
	}
}
