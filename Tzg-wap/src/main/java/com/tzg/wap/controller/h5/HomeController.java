package com.tzg.wap.controller.h5;

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
import com.tzg.common.base.BaseRequest;
import com.tzg.common.constants.KFFConstants;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.rest.Base64Util;
import com.tzg.entitys.kff.article.ArticleDetailResponse;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.comments.CommentsShareRequest;
import com.tzg.entitys.kff.discuss.DiscussDetailResponse;
import com.tzg.entitys.kff.discuss.DiscussShare;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.entitys.kff.evaluation.ProjectEvaluationDetailResponse;
import com.tzg.entitys.kff.evaluation.ProjectEvaluationDetailShareResponse;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
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

	/**
	 * 首页推荐列表
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value = "/recommendList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity evaluationList(HttpServletRequest request, HttpServletResponse response, Integer pageIndex, Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (null == pageIndex) {
			pageIndex = 1;
		}
		if (null == pageSize) {
			pageSize = 10;
		}
		try {
//			String token = (String) request.getSession().getAttribute("token");
			Integer userId = null;
//			if (StringUtils.isNotBlank(token)) {
//				userId = getUserIdByToken(token);
//			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("status", "1");
			query.addQueryData("stickTop", "1");
			query.addQueryData("sortField", "stick_updateTime");
			query.addQueryData("praiseNum", "10");
			// 帖子类型：1-评测；2-讨论；3-文章
			// query.addQueryData("postType", "1");
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			Integer type = 1;//取关项目
			PageResult<PostResponse> recommends = kffRmiService.findPageRecommendList(userId, query,type);
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
	 * 
	 * @Title: followList
	 * @Description: 关注列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/followList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity followList(HttpServletRequest request, HttpServletResponse response, Integer pageIndex, Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (null == pageIndex) {
			pageIndex = 1;
		}
		if (null == pageSize) {
			pageSize = 10;
		}
		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("status", "1");
			query.addQueryData("sortField", "collectNum");
			// 帖子类型：1-评测；2-讨论；3-文章
			// query.addQueryData("postType", "2");
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<PostResponse> follows = kffRmiService.findPageFollowList(userId, query);
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
	 * 文章详情
	 * 
	 * @param response
	 * @param request
	 * @param token
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/articleDetail", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity articleDetail(HttpServletResponse response, HttpServletRequest request, Integer postId) {
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
			ArticleDetailResponse article = kffRmiService.findArticleDetail(userId, postId);
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
	@RequestMapping(value = "/discussDetail", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity discussDetail(HttpServletRequest request, HttpServletResponse response, Integer postId) {
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
			DiscussDetailResponse discuss = kffRmiService.findDiscussDetail(userId, postId);
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
	 * 讨论详情页,单个评论详情页
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/discussCommentList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity discussCommentList(HttpServletRequest request, HttpServletResponse response, Integer postId) {
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
			List<Comments> comments = kffRmiService.findAllDiscussCommentsList(userId, postId);
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

			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}

			DiscussShare discussShare = kffRmiService.findDiscussDetailWAP(postId);
			map.put("discussShare", discussShare);

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
	public BaseResponseEntity evaluationDetail(HttpServletRequest request, HttpServletResponse response, String token, Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {

			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			DiscussDetailResponse evaluationDetail = kffRmiService.findDiscussDetail(userId, postId);
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

			if (postId == null || postId == 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
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
