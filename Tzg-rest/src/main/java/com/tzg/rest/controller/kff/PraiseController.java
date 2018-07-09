package com.tzg.rest.controller.kff;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value="KFFPraiseController")
@RequestMapping("/kff/praise")
public class PraiseController extends BaseController {
	private static Logger log = Logger.getLogger(PraiseController.class);
	
	@Autowired
	private KFFRmiService kffRmiService;
	
	/**
	 * 
	* @Title: savePostPraise
	* @Description: 对帖子进行点赞
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/savePostPraise",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity sendQiniuToken(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
            String token = (String) params.get("token");
            Integer postId = (Integer) params.get("postId");            
			Integer userId = getUserIdByToken(token);
			if(postId == null || postId <= 0){
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
	
			int praiseNum = kffRmiService.savePraise(userId,postId);
            map.put("praiseNum", praiseNum);
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("PraiseController savePostPraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController savePostPraise:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 
	* @Title: cancelPostPraise
	* @Description: 取消对帖子的点赞
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/cancelPostPraise",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity cancelPostPraise(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
            String token = (String) params.get("token");
            Integer postId = (Integer) params.get("postId");            
			Integer userId = getUserIdByToken(token);
			if(postId == null || postId <= 0){
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}
	
			int praiseNum = kffRmiService.cancelPraise(userId,postId);
			map.put("praiseNum", praiseNum);
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("PraiseController cancelPostPraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController cancelPostPraise:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 
	* @Title: saveCommentsPraise
	* @Description: 对评论进行点赞
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/saveCommentsPraise",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity saveCommentsPraise(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
            String token = (String) params.get("token");
            Integer commentsId = (Integer) params.get("commentsId");            
			Integer userId = getUserIdByToken(token);
			if(commentsId == null || commentsId <= 0){
				throw new RestServiceException(RestErrorCode.MISSING_ARG_COMMENTSID);
			}
	
			int praiseNum = kffRmiService.saveCommentsPraise(userId,commentsId);
			map.put("praiseNum", praiseNum);
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("PraiseController saveCommentsPraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController saveCommentsPraise:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 
	* @Title: cancelCommentsPraise
	* @Description: 取消 对评论的点赞
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/cancelCommentsPraise",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity cancelCommentsPraise(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
            String token = (String) params.get("token");
            Integer commentsId = (Integer) params.get("commentsId");            
			Integer userId = getUserIdByToken(token);
			if(commentsId == null || commentsId <= 0){
				throw new RestServiceException(RestErrorCode.MISSING_ARG_COMMENTSID);
			}
	
			int praiseNum = kffRmiService.cancelCommentsPraise(userId,commentsId);
			map.put("praiseNum", praiseNum);
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("PraiseController cancelCommentsPraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController cancelCommentsPraise:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	
}


