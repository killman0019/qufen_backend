package com.tzg.rest.controller.kff;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.base.BaseRequest;
import com.tzg.common.constants.KFFConstants;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.FileUtils;
import com.tzg.common.utils.QiniuUtil;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.SyseUtil;
import com.tzg.common.utils.rest.Base64Util;
import com.tzg.entitys.kff.article.ArticleDetailResponse;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.discuss.DiscussDetailResponse;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.utils.DateUtil;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFPostRmiService;
import com.tzg.rmi.service.KFFProjectPostRmiService;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.KFFUserRmiService;
import com.tzg.rmi.service.SystemParamRmiService;

@Controller(value = "KFFHomeController")
@RequestMapping("/kff/home")
public class HomeController extends BaseController {
	private static Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private KFFProjectPostRmiService kffProjectPostRmiService;
	@Autowired
	private SystemParamRmiService systemParamRmiService;
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
	public BaseResponseEntity newestList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			JSONObject params = getParamJsonFromRequestPolicy(request);
			Integer pageIndex = params.getInteger("pageIndex");
			Integer pageSize = params.getInteger("pageSize");
			String token = params.getString("token");
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
	public BaseResponseEntity recommendList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
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
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
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
	public BaseResponseEntity newRecommendList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
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
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			Integer nowCount = baseRequest.getPageSize();
			Integer type = 2;// 取关注人
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
	 * @Title: getBurstList
	 * @Description: TODO <获取爆料列表>
	 * @author linj <方法创建作者>
	 * @create 上午10:23:11
	 * @param @param request
	 * @param @return <参数说明>
	 * @return BaseResponseEntity
	 * @throws
	 * @update 上午10:23:11
	 * @updator <修改人 修改后更新修改时间，不同人修改再添加>
	 * @updateContext <修改内容>
	 */
	@ResponseBody
	@RequestMapping(value = "/getBurstList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity getBurstList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();

			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			Integer nowCount = baseRequest.getPageSize();
			Integer type = 2;// 取关注人
			PageResult<PostResponse> recommends = kffRmiService.findBurstList(userId, query, type, nowCount);
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
	public BaseResponseEntity followList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer pageIndex = baseRequest.getPageIndex();
			Integer pageSize = baseRequest.getPageSize();
			if(StringUtils.isBlank(token)||null==pageIndex||null==pageSize) {
				bre.setNoRequstData();
				return bre;
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("statusc", "1");
			query.addQueryData("sort", "tbf.createTime");
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			Integer type = 2;// 取关注人
			Integer	userId = getUserIdByToken(token);
			KFFUser	loginUser = kffUserService.findById(userId);
			PageResult<PostResponse> follows = kffProjectPostRmiService.findPageForFollowList(userId, query, type, loginUser);
			map.put("follows", follows);
			bre.setData(map);
			SyseUtil.systemErrOutJson(map);
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
	public BaseResponseEntity projectRankList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("status", "1");
			query.addQueryData("state", "2");
			query.addQueryData("sortField", "total_score");
			query.addQueryData("sortSequence", "desc");
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
	 * 
	 * @Title: articleDetail
	 * @Description: 文章详情
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/articleDetail", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity articleDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer postId = (Integer) params.get("postId");
			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			Integer type = 2;// 取关注人
			ArticleDetailResponse article = kffRmiService.findArticleDetail(userId, type, postId);
			map.put("articleDetail", article);

			bre.setData(map);
			SyseUtil.systemErrOutJson(bre);
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
	 * 
	 * @Title: articleCommentList
	 * @Description: 文章评论列表 页面（分页获取）
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/articleCommentList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity articleCommentList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest params = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = params.getToken();
			Integer postId = params.getPostId();
			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}

			PaginationQuery newQuery = new PaginationQuery();
			newQuery.addQueryData("status", "1");
			newQuery.addQueryData("postId", postId + "");
			newQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
			newQuery.addQueryData("parentCommentsIdNull", "YES");
			newQuery.setPageIndex(params.getPageIndex());
			newQuery.setRowsPerPage(params.getPageSize());

			if (StringUtils.isNotBlank(params.getSortField())) {
				newQuery.addQueryData("sortField", params.getSortField());
				// newQuery.addQueryData("sortSequence", "desc");
			} else {
				newQuery.addQueryData("sortField", "comments_id");
				// newQuery.addQueryData("sortSequence", "desc");
			}

			PageResult<Comments> newestComments = kffRmiService.findPageNewestCommentsSelf(userId, postId, newQuery);
			// System.err.println("这个是最新评论newestComments" + JSON.toJSONString(newestComments));
			map.put("newestComments", newestComments);

			if (params.getPageIndex() == null || params.getPageIndex() == 1) {
				PaginationQuery hotQuery = new PaginationQuery();
				hotQuery.addQueryData("status", "1");
				hotQuery.addQueryData("postId", postId + "");
				hotQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
				hotQuery.addQueryData("parentCommentsIdNull", "YES");
				// 点赞数最多的2个评论 点赞数量超过10个
				hotQuery.addQueryData("sortField", "praise_num");
				hotQuery.addQueryData("praiseNum", "10");
				hotQuery.setPageIndex(1);
				hotQuery.setRowsPerPage(2);
				List<Comments> hotComments = kffRmiService.findPageHotCommentsListSelf(userId, postId, hotQuery);
				// System.err.println("这个是热门评论hotComments" + JSON.toJSONString(hotComments));
				map.put("hotComments", hotComments);
			}
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
	public BaseResponseEntity articleCommentReplyList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer commentsId = (Integer) params.get("commentsId");
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
	* @Title: discussDetail 
	* @Description: TODO <爆料详情>
	* @author linj <方法创建作者>
	* @create 下午2:56:10
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午2:56:10
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/discussDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity discussDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer postId = (Integer) params.get("postId");
			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			Integer type = 2;// 取关注人
			DiscussDetailResponse discuss = kffRmiService.findDiscussDetail(userId, type, postId);
			map.put("discussDetail", discuss);
			bre.setData(map);
			SyseUtil.systemErrOutJson(bre);
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
	 * 
	 * @Title: discussCommentList
	 * @Description: 显示所有针对某个帖子的所有评论
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/discussCommentList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity discussCommentList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = (String) baseRequest.getToken();
			Integer postId = (Integer) baseRequest.getPostId();
			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			query.addQueryData("postId", postId + "");
			query.addQueryData("status", KFFConstants.STATUS_ACTIVE + "");
//			query.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS + "");
			query.addQueryData("parentCommentsIdNull", "YES");

			if (StringUtils.isNotBlank(baseRequest.getSortField())) {
				query.addQueryData("sortField", baseRequest.getSortField());
				query.addQueryData("sortSequence", "desc");
			} else {
				query.addQueryData("sortField", "comments_id");
				query.addQueryData("sortSequence", "desc");
			}

			PageResult<Comments> comments = kffRmiService.findPageDiscussCommentsList(userId, query);
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
	public BaseResponseEntity evaluationDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer postId = (Integer) params.get("postId");
			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			Integer type = 2;// 取关注人
			EvaluationDetailResponse evaluationDetail = kffRmiService.findEvaluationDetail(userId, type, postId);
			map.put("evaluationDetail", evaluationDetail);
			bre.setData(map);
			SyseUtil.systemErrOutJson(bre);
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
	 * 
	 * @Title: evaluationCommentList
	 * @Description: 评测评论列表 页面（分页获取）
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/evaluationCommentList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity evaluationCommentList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest params = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = params.getToken();
			Integer postId = params.getPostId();
			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}

			PaginationQuery newQuery = new PaginationQuery();
			newQuery.addQueryData("status", "1");
			newQuery.addQueryData("postId", postId + "");
			newQuery.addQueryData("postType", KFFConstants.POST_TYPE_EVALUATION + "");
			newQuery.addQueryData("parentCommentsIdNull", "YES");
			newQuery.setPageIndex(params.getPageIndex());
			newQuery.setRowsPerPage(params.getPageSize());

			if (StringUtils.isNotBlank(params.getSortField())) {
				newQuery.addQueryData("sortField", params.getSortField());
				newQuery.addQueryData("sortSequence", "desc");
			} else {
				newQuery.addQueryData("sortField", "comments_id");
				newQuery.addQueryData("sortSequence", "desc");
			}

			PageResult<Comments> newestComments = kffRmiService.findPageNewestCommentsSelf(userId, postId, newQuery);
			// System.err.println("最新评论newestComments" + JSONObject.toJSONString(newestComments));
			map.put("newestComments", newestComments);

			if (params.getPageIndex() == null || params.getPageIndex() == 1) {
				PaginationQuery hotQuery = new PaginationQuery();
				hotQuery.addQueryData("status", "1");
				hotQuery.addQueryData("postId", postId + "");
				hotQuery.addQueryData("postType", KFFConstants.POST_TYPE_EVALUATION + "");
				newQuery.addQueryData("parentCommentsIdNull", "YES");
				// 点赞数最多的2个评论
				hotQuery.addQueryData("sortField", "praise_num");
				hotQuery.addQueryData("praiseNum", "10");
				hotQuery.setPageIndex(1);
				hotQuery.setRowsPerPage(2);
				List<Comments> hotComments = kffRmiService.findPageHotCommentsListSelf(userId, postId, hotQuery);
				// System.err.println("最新评论hotComments" + JSONObject.toJSONString(hotComments));
				map.put("hotComments", hotComments);
			}
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
	 * 通用上传接口--base64形式
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadImg", method = { RequestMethod.POST, RequestMethod.GET }, produces = "text/html; charset=utf-8", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public BaseResponseEntity uploadImg(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");
			String imgdata = (String) params.get("imgdata");
			Integer imgtype = (Integer) params.get("imgtype");
			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			if (StringUtils.isBlank(imgdata)) {
				throw new RestServiceException(RestErrorCode.USER_ICON_DATA_EMPTY);
			}
			if (imgtype == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_IMGTYPE);
			}
			if (imgtype != KFFConstants.IMGTYPE_AVATARS && imgtype != KFFConstants.IMGTYPE_POSTS && imgtype != KFFConstants.IMGTYPE_PROJECTS) {
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
				String randomStr = RandomUtil.produceString(10);
				String file = localPath + randomStr + ".jpg";
				Base64Util.decoderBase64File(imgdata, file);
				// 返回imgurl
				urlPath = urlPath + "/posts/" + DateUtil.getCurrentYearMonth() + "/";
				file = urlPath + randomStr + ".jpg";

				resMap.put("imgUrl", file);
			} else if (imgtype == KFFConstants.IMGTYPE_PROJECTS) {
				localPath = localPath + "projects/" + DateUtil.getCurrentYearMonth() + "/";
				String randomStr = RandomUtil.produceString(10);
				String file = localPath + randomStr + ".jpg";
				Base64Util.decoderBase64File(imgdata, file);
				// 返回imgurl
				urlPath = urlPath + "/projects/" + DateUtil.getCurrentYearMonth() + "/";
				file = urlPath + randomStr + ".jpg";

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

	/**
	 * 通用上传接口--流媒体形式
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadImgFile", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity uploadImgFile(HttpServletRequest request, String token, Integer imgtype, String userIcon, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			// JSONObject params = getParamMapFromRequestPolicy(request);
			// String userIcon = (String) params.get("userIcon");
			MultipartFile file = null;
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
			if (multipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				if (multiRequest != null && multiRequest.getFileMap() != null) {
					file = multiRequest.getFileMap().get("uploadfile");
				}
			}

			if (StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}
			if (file == null) {
				throw new RestServiceException("uploadfile不能为空");
			}
			if (file.isEmpty()) {
				throw new RestServiceException("uploadfile内容为空");
			}
			if (file.getBytes() == null) {
				throw new Exception("文件内容不能为空");
			}
			if (imgtype == null) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_IMGTYPE);
			}
			if (imgtype != KFFConstants.IMGTYPE_AVATARS && imgtype != KFFConstants.IMGTYPE_POSTS && imgtype != KFFConstants.IMGTYPE_PROJECTS) {
				throw new RestServiceException(RestErrorCode.WRONG_IMGTYPE);
			}
			Integer userId = AccountTokenUtil.decodeAccountToken(token);
			if (userId == null) {
				throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
			}
			if (file.getSize() > 5 * 1024 * 1024) {
				throw new RestServiceException("图片大于5M");
			}
			// 文件后缀
			/*String extention = FileUtils.getFileExtension(file.getOriginalFilename());
			if (!FileUtils.allowedExtensionSet().contains(extention)) {
				throw new RestServiceException("非法文件后缀" + extention);
			}*/

			String name = UUID.randomUUID().toString().replaceAll("-", "");
			name = DateUtil.getCurrentTimeSS();
			// jpg
			String ext = FilenameUtils.getExtension(file.getOriginalFilename());
			if (!FileUtils.allowedExtensionSet().contains(ext)) {
				throw new RestServiceException("此文件后缀为" + ext + ",请更换图片后缀或者联系客服!");
			}

			// SystemParam systemParam = systemParamRmiService.findByCode("upload_local_path");
			// String localPath = systemParam.getVcParamValue();
			// systemParam = systemParamRmiService.findByCode("upload_file_path");

			name = DateUtil.getCurrentTimeSS();
			// jpg

			if (imgtype == KFFConstants.IMGTYPE_AVATARS) {
				/*String currentTimeSS = DateUtil.getCurrentTimeSS();
				localPath = localPath + "avatars/" + DateUtil.getCurrentYearMonth() + "/";
				localPath = localPath + currentTimeSS + "." + extention;
				try {
					FileUtils.createFileLocal(localPath, file.getBytes());
				} catch (Exception e) {
					logger.warn("error in uploadImgFile method createFileLocal IMGTYPE_AVATARS:{}", e);
					// throw new RestServiceException("生成文件失败");
				}
				// 更新用户头像url

				urlPath = urlPath + "/avatars/" + DateUtil.getCurrentYearMonth() + "/";
				urlPath = urlPath + currentTimeSS + "." + extention;*/

				String urlPath = "";
				KFFUser account = new KFFUser();
				KFFUser OldUser = kffRmiService.findUserById(userId);
				// account.setUserId(userId);
				OldUser.setUpdateTime(new Date());
				if (StringUtils.isBlank(userIcon)) {
					String fName = "avatars" + name + "." + ext;
					urlPath = QiniuUtil.uploadStream(file.getInputStream(), fName);
					OldUser.setIcon(urlPath);
				} else {
					urlPath = userIcon;
					OldUser.setIcon(urlPath);
				}

				kffRmiService.updateUserInfo(OldUser);
				resMap.put("imgUrl", urlPath);
			} else if (imgtype == KFFConstants.IMGTYPE_POSTS) {
				/*localPath = localPath + "postPic/" + DateUtil.getCurrentYearMonth() + "/";
				String randomStr = RandomUtil.produceString(10);
				String currentTimeSS = DateUtil.getCurrentTimeSS();
				localPath = localPath + currentTimeSS + "." + extention;
				try {
					FileUtils.createFileLocal(localPath, file.getBytes());
				} catch (Exception e) {
					logger.warn("error in uploadImgFile method createFileLocal IMGTYPE_POSTS:{}", e);
					// throw new RestServiceException("生成文件失败");
				}*/
				// 返回imgurl
				// urlPath = urlPath + "/postPic/" + DateUtil.getCurrentYearMonth() + "/";
				// urlPath = urlPath + currentTimeSS + "." + extention;

				String fName = "posts" + name + "." + ext;
				String urlPath = QiniuUtil.uploadStream(file.getInputStream(), fName);

				resMap.put("imgUrl", urlPath);
			} else if (imgtype == KFFConstants.IMGTYPE_PROJECTS) {
				/*localPath = localPath + "projects/" + DateUtil.getCurrentYearMonth() + "/";
				String randomStr = RandomUtil.produceString(10);
				String currentTimeSS = DateUtil.getCurrentTimeSS();
				localPath = localPath + currentTimeSS + "." + extention;
				logger.info("存放路径localPath" + localPath);
				try {
					FileUtils.createFileLocal(localPath, file.getBytes());
				} catch (Exception e) {
					logger.warn("error in uploadImgFile method createFileLocal IMGTYPE_PROJECTS:{}", e);
					// throw new RestServiceException("生成文件失败");
				}
				// 返回imgurl
				urlPath = urlPath + "/projects/" + DateUtil.getCurrentYearMonth() + "/";
				urlPath = urlPath + currentTimeSS + "." + extention;*/
				String fName = "projects" + name + "." + ext;
				String urlPath = QiniuUtil.uploadStream(file.getInputStream(), fName);

				logger.info("存放路径urlPath" + urlPath);
				resMap.put("imgUrl", urlPath);
			}

			bre.setData(resMap);
		} catch (RestServiceException e) {
			logger.warn("error in uploadImgFile method:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.warn("uncatched error in uploadImgFile method:{}", e);
			e.printStackTrace();
			return this.resResult(RestErrorCode.SYS_ERROR);
		}

		return bre;
	}

	/**
	 * 
	 * @Title: commentCommentsList
	 * @Description:显示针对某个评论的所有评论 分页加载
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/commentCommentsList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity commentCommentsList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = (String) baseRequest.getToken();
			Integer postId = (Integer) baseRequest.getPostId();
			Integer commentsId = (Integer) baseRequest.getCommentsId();
			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
			if (commentsId == null || commentsId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_COMMENTSID);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			query.addQueryData("postId", postId + "");
			query.addQueryData("parentCommentsId", commentsId + "");// parentCommentsId
			query.addQueryData("status", KFFConstants.STATUS_ACTIVE + "");
			PageResult<Comments> comments = kffProjectPostRmiService.findPagecommentCommentsList(userId, query);
			map.put("comments", comments);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController commentCommentsList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController commentCommentsList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: counterfeitList
	 * @Description:打假列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/counterfeitList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity counterfeitList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("status", "1");
			query.addQueryData("stickTop", "1");

			query.addQueryData("sortField", "createTime");
			// 帖子类型：1-评测；2-讨论；3-文章
			// query.addQueryData("postType", "1");
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<PostResponse> counterfeits = kffRmiService.findPageCounterfeitListList(userId, query);
			map.put("counterfeits", counterfeits);
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
}
