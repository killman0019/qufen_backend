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
import com.tzg.entitys.kff.commendation.CommendationRequest;
import com.tzg.entitys.kff.comments.Comments;
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

@Controller(value="KFFHomeController")
@RequestMapping("/kff/home")
public class HomeController extends BaseController {
	private static Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private KFFRmiService kffRmiService;
    @Autowired
    private SystemParamRmiService  systemParamRmiService;
	
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
	@RequestMapping(value="/recommendList",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity evaluationList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer userId = null;
			if(StringUtils.isNotBlank(token)){
				userId = getUserIdByToken(token);
			}			
			PaginationQuery query = new PaginationQuery();
	        query.addQueryData("status", "1");
	        query.addQueryData("sortField", "pageviewNum");
	        //帖子类型：1-评测；2-讨论；3-文章
	        //query.addQueryData("postType", "1");
	        query.setPageIndex(baseRequest.getPageIndex());
	        query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<PostResponse> recommends = kffRmiService.findPageRecommendList(userId,query);
            map.put("recommends", recommends);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController recommendList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController recommendList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		System.out.println(bre);
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
	@RequestMapping(value="/followList",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity followList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer userId = null;
			if(StringUtils.isNotBlank(token)){
				userId = getUserIdByToken(token);
			}	
			PaginationQuery query = new PaginationQuery();
	        query.addQueryData("status", "1");
	        query.addQueryData("sortField", "collectNum");
	        //帖子类型：1-评测；2-讨论；3-文章
	        //query.addQueryData("postType", "2");
	        query.setPageIndex(baseRequest.getPageIndex());
	        query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<PostResponse> follows = kffRmiService.findPageFollowList(userId,query);
            map.put("follows", follows);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController followList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController followList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
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
	@RequestMapping(value="/projectRankList",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity projectRankList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer userId = null;
			if(StringUtils.isNotBlank(token)){
				userId = getUserIdByToken(token);
			}				
			PaginationQuery query = new PaginationQuery();
	        query.addQueryData("status", "1");
	        query.addQueryData("sortField", "totalScore");
	        query.setPageIndex(1);
	        query.setRowsPerPage(20);
			List<ProjectResponse> rankList = kffRmiService.findPageProjectRankList(userId,query);
            map.put("rankList", rankList);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController projectRankList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController projectRankList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
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
	@RequestMapping(value="/articleDetail",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity articleDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String)params.get("token");
			Integer postId = (Integer) params.get("postId");
			if(postId == null || postId == 0){
			   throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);	
			}
			Integer userId = null;
			if(StringUtils.isNotBlank(token)){
				userId = getUserIdByToken(token);
			}				
			ArticleDetailResponse article = kffRmiService.findArticleDetail(userId,postId);
            map.put("articleDetail", article);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	
	/**
	 * 
	* @Title: articleCommentList
	* @Description: 文章评论列表 页面（包括2条热门评论，20条最新评论）
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/articleCommentList",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity articleCommentList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String)params.get("token");
			Integer postId = (Integer) params.get("postId");
			if(postId == null || postId == 0){
			   throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);	
			}
			Integer userId = null;
			if(StringUtils.isNotBlank(token)){
				userId = getUserIdByToken(token);
			}						
			PaginationQuery hotQuery = new PaginationQuery();
			hotQuery.addQueryData("status", "1");
			hotQuery.addQueryData("postId", postId + "");
			hotQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
	        //点赞数最多的2个评论
			hotQuery.addQueryData("sortField", "praiseNum");
			hotQuery.setPageIndex(1);
			hotQuery.setRowsPerPage(2);
			List<Comments> hotComments = kffRmiService.findPageHotCommentsList(userId,postId,hotQuery);
            map.put("hotComments", hotComments);
        	PaginationQuery newQuery = new PaginationQuery();
        	newQuery.addQueryData("status", "1");
        	newQuery.addQueryData("postId", postId + "");
        	newQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE +"");
	        //最新的20个评论
        	newQuery.setPageIndex(1);
        	newQuery.setRowsPerPage(20);
            List<Comments> newestComments = kffRmiService.findPageNewestComments(userId,postId,newQuery);            
            map.put("newestComments", newestComments);
            
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController articleCommentList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController articleCommentList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	
	/**
	 * 
	* @Title: articleCommentReplyList
	* @Description: 针对一个评论的所有评论列表  （评论的回复列表页）
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/articleCommentReplyList",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity articleCommentReplyList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String)params.get("token");
			Integer commentsId = (Integer) params.get("commentsId");
			if(commentsId == null || commentsId == 0){
			   throw new RestServiceException(RestErrorCode.MISSING_ARG_COMMENTSID);	
			}
			Integer userId = null;
			if(StringUtils.isNotBlank(token)){
				userId = getUserIdByToken(token);
			}						
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("status", "1");
			query.addQueryData("parentCommentsId", commentsId + "");
			query.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
	        //前30个针对评论的评论
			//query.addQueryData("sortField", "praiseNum");
			query.setPageIndex(1);
			query.setRowsPerPage(30);
			List<Comments> childComments = kffRmiService.findAllChildCommentsList(userId,commentsId,query);
            map.put("childComments", childComments);  
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController articleCommentReplyList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController articleCommentReplyList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
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
	@RequestMapping(value="/discussDetail",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity discussDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String)params.get("token");
			Integer postId = (Integer) params.get("postId");
			if(postId == null || postId == 0){
			   throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);	
			}
			Integer userId = null;
			if(StringUtils.isNotBlank(token)){
				userId = getUserIdByToken(token);
			}				
			ArticleDetailResponse discuss = kffRmiService.findDiscussDetail(userId,postId);
            map.put("discussDetail", discuss);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController discussDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController discussDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 
	* @Title: discussCommentList
	* @Description: 显示所有针对某个评论的所有评论
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/discussCommentList",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity discussCommentList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String)params.get("token");
			Integer postId = (Integer) params.get("postId");
			if(postId == null || postId == 0){
			   throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);	
			}
			Integer userId = null;
			if(StringUtils.isNotBlank(token)){
				userId = getUserIdByToken(token);
			}						
			List<Comments> comments = kffRmiService.findAllDiscussCommentsList(userId,postId);
            map.put("comments", comments);
     
            
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController discussCommentList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController discussCommentList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
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
	@RequestMapping(value="/evaluationDetail",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity evaluationDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String)params.get("token");
			Integer projectId = (Integer) params.get("projectId");
			if(projectId == null || projectId == 0){
			   throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);	
			}
			Integer userId = null;
			if(StringUtils.isNotBlank(token)){
				userId = getUserIdByToken(token);
			}				
		//	ArticleDetailResponse evaluationDetail = kffRmiService.findDiscussDetail(userId,postId);
     //       map.put("evaluationDetail", evaluationDetail);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("HomeController evaluationDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController evaluationDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	
	
	/**
	 * 上传用户头像 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadImg",method = {RequestMethod.POST,RequestMethod.GET},
			produces = "text/html; charset=utf-8",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public BaseResponseEntity uploadImg(
			HttpServletRequest request, HttpServletResponse response
			) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
        	String token = (String)params.get("token");
        	String imgdata = (String)params.get("imgdata");
        	Integer imgtype = (Integer)params.get("imgtype");
        	if(StringUtils.isBlank(token)){
        		throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
        	}
        	if(StringUtils.isBlank(imgdata)){
        		throw new RestServiceException(RestErrorCode.USER_ICON_DATA_EMPTY);
        	}
        	if(imgtype == null){
        		throw new RestServiceException(RestErrorCode.MISSING_ARG_IMGTYPE);
        	} 
        	if(imgtype != KFFConstants.IMGTYPE_AVATARS || imgtype != KFFConstants.IMGTYPE_POSTS){
        		throw new RestServiceException(RestErrorCode.WRONG_IMGTYPE);
        	}   
        	Integer userId = AccountTokenUtil.decodeAccountToken(token);
        	if(userId == null){
        		throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
        	}
        	
        	SystemParam systemParam = systemParamRmiService.findByCode("upload_local_path");        	
        	String localPath = systemParam.getVcParamValue();        	
        	systemParam = systemParamRmiService.findByCode("upload_file_path");
        	String urlPath = systemParam.getVcParamValue();
        	
        	if(imgtype == KFFConstants.IMGTYPE_AVATARS){
        		localPath = localPath + "avatars/" + DateUtil.getCurrentYearMonth()+"/";
	        	String file = localPath + userId+".jpg";
	        	Base64Util.decoderBase64File(imgdata,file);	        	
	        	//更新用户头像url	        	
	        	urlPath = urlPath + "/avatars/" + DateUtil.getCurrentYearMonth()+"/";
	        	file = urlPath + userId+".jpg";
	        	KFFUser account = new KFFUser();
	        	account.setUserId(userId);
	        	account.setUpdateTime(new Date());
	        	account.setIcon(file);
	        	kffRmiService.updateUser(account);
	        	resMap.put("imgUrl", file);
        	}else if(imgtype == KFFConstants.IMGTYPE_POSTS){
        		localPath = localPath + "posts/" + DateUtil.getCurrentYearMonth()+"/";
	        	String file = localPath + userId+".jpg";
	        	Base64Util.decoderBase64File(imgdata,file);	        	
	        	//返回imgurl	        	
	        	urlPath = urlPath + "/posts/" + DateUtil.getCurrentYearMonth()+"/";
	        	file = urlPath + RandomUtil.produceString(10)+".jpg";
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


