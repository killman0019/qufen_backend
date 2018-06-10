package com.tzg.rest.controller.kff;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.rest.Base64Util;
import com.tzg.entitys.kff.article.ArticleDetailResponse;
import com.tzg.entitys.kff.commendation.CommendationRequest;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.discuss.DiscussDetailResponse;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.message.KFFMessage;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.suggest.SuggestRequest;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserHomeResponse;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.utils.DateUtil;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.SystemParamRmiService;

@Controller(value = "KFFHomeController")
@RequestMapping("/kff/home")
public class HomeController extends BaseController {
	private static Logger logger = Logger.getLogger(HomeController.class);

	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private SystemParamRmiService systemParamRmiService;

	/**
	 * 
	 * @Title: recommendList
	 * @Description: 推荐列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/recommendList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
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
			// 帖子类型：1-评测；2-讨论；3-文章
			// query.addQueryData("postType", "1");
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<PostResponse> recommends = kffRmiService.findPageRecommendList(userId, query);
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
	public BaseResponseEntity followList(HttpServletRequest request) {
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
			query.addQueryData("sortField", "collect_num");
			// 帖子类型：1-评测；2-讨论；3-文章
			// query.addQueryData("postType", "2");
			query.setPageIndex(baseRequest.getPageIndex());
			query.setRowsPerPage(baseRequest.getPageSize());
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
			newQuery.setPageIndex(params.getPageIndex());
			newQuery.setRowsPerPage(params.getPageSize());
			PageResult<Comments> newestComments = kffRmiService.findPageNewestComments(userId, postId, newQuery);
			map.put("newestComments", newestComments);

			if (params.getPageIndex() == null || params.getPageIndex() == 1) {
				PaginationQuery hotQuery = new PaginationQuery();
				hotQuery.addQueryData("status", "1");
				hotQuery.addQueryData("postId", postId + "");
				hotQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
				// 点赞数最多的2个评论
				hotQuery.addQueryData("sortField", "praise_num");
				hotQuery.setPageIndex(1);
				hotQuery.setRowsPerPage(2);
				List<Comments> hotComments = kffRmiService.findPageHotCommentsList(userId, postId, hotQuery);
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
	 * 
	 * @Title: discussDetail
	 * @Description: 讨论详情
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/discussDetail", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
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
			query.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS + "");
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
			EvaluationDetailResponse evaluationDetail = kffRmiService.findEvaluationDetail(userId, postId);
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
			newQuery.setPageIndex(params.getPageIndex());
			newQuery.setRowsPerPage(params.getPageSize());
			PageResult<Comments> newestComments = kffRmiService.findPageNewestComments(userId, postId, newQuery);
			map.put("newestComments", newestComments);

			if (params.getPageIndex() == null || params.getPageIndex() == 1) {
				PaginationQuery hotQuery = new PaginationQuery();
				hotQuery.addQueryData("status", "1");
				hotQuery.addQueryData("postId", postId + "");
				hotQuery.addQueryData("postType", KFFConstants.POST_TYPE_EVALUATION + "");
				// 点赞数最多的2个评论
				hotQuery.addQueryData("sortField", "praise_num");
				hotQuery.setPageIndex(1);
				hotQuery.setRowsPerPage(2);
				List<Comments> hotComments = kffRmiService.findPageHotCommentsList(userId, postId, hotQuery);
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
	public BaseResponseEntity uploadImgFile(HttpServletRequest request, String token, Integer imgtype, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			// JSONObject params = getParamMapFromRequestPolicy(request);
			// @RequestParam("uploadfile") MultipartFile file,
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
			String extention = FileUtils.getFileExtension(file.getOriginalFilename());
			if (!FileUtils.allowedExtensionSet().contains(extention)) {
				throw new RestServiceException("非法文件后缀" + extention);
			}

			SystemParam systemParam = systemParamRmiService.findByCode("upload_local_path");
			String localPath = systemParam.getVcParamValue();
			systemParam = systemParamRmiService.findByCode("upload_file_path");
			String urlPath = systemParam.getVcParamValue();
			logger.info("存放路径" + systemParam);
			if (imgtype == KFFConstants.IMGTYPE_AVATARS) {
				localPath = localPath + "avatars/" + DateUtil.getCurrentYearMonth() + "/";
				localPath = localPath + userId + "." + extention;
				try {
					FileUtils.createFileLocal(localPath, file.getBytes());
				} catch (Exception e) {
					logger.warn("error in uploadImgFile method createFileLocal IMGTYPE_AVATARS:{}", e);
					// throw new RestServiceException("生成文件失败");
				}
				// 更新用户头像url
				urlPath = urlPath + "/avatars/" + DateUtil.getCurrentYearMonth() + "/";
				urlPath = urlPath + userId + "." + extention;
				KFFUser account = new KFFUser();
				account.setUserId(userId);
				account.setUpdateTime(new Date());
				account.setIcon(urlPath);
				kffRmiService.updateUser(account);
				resMap.put("imgUrl", urlPath);
			} else if (imgtype == KFFConstants.IMGTYPE_POSTS) {
				localPath = localPath + "posts/" + DateUtil.getCurrentYearMonth() + "/";
				String randomStr = RandomUtil.produceString(10);
				localPath = localPath + randomStr + "." + extention;
				try {
					FileUtils.createFileLocal(localPath, file.getBytes());
				} catch (Exception e) {
					logger.warn("error in uploadImgFile method createFileLocal IMGTYPE_POSTS:{}", e);
					// throw new RestServiceException("生成文件失败");
				}
				// 返回imgurl
				urlPath = urlPath + "/posts/" + DateUtil.getCurrentYearMonth() + "/";
				urlPath = urlPath + randomStr + "." + extention;

				resMap.put("imgUrl", urlPath);
			} else if (imgtype == KFFConstants.IMGTYPE_PROJECTS) {
				localPath = localPath + "projects/" + DateUtil.getCurrentYearMonth() + "/";
				String randomStr = RandomUtil.produceString(10);
				localPath = localPath + randomStr + "." + extention;
				logger.info("存放路径localPath" + localPath);
				try {
					FileUtils.createFileLocal(localPath, file.getBytes());
				} catch (Exception e) {
					logger.warn("error in uploadImgFile method createFileLocal IMGTYPE_PROJECTS:{}", e);
					// throw new RestServiceException("生成文件失败");
				}
				// 返回imgurl
				urlPath = urlPath + "/projects/" + DateUtil.getCurrentYearMonth() + "/";
				urlPath = urlPath + randomStr + "." + extention;
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
}
