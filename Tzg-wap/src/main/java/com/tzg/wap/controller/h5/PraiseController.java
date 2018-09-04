package com.tzg.wap.controller.h5;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.utils.HtmlUtils;
import com.tzg.common.utils.SyseUtil;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "KFFPraiseController")
@RequestMapping("/kff/praise")
public class PraiseController extends BaseController {
	private static Logger log = Logger.getLogger(PraiseController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	/**
	 * 点赞
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/savePostPraise", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity sendQiniuToken(HttpServletRequest request, String token, Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if (null == token && null == postId) {
				JSONObject params = HtmlUtils.getRequestContent(request);
				token = (String) params.get("token");
				if (StringUtils.isBlank(token)) {
					throw new RestServiceException(RestErrorCode.MISSING_ARGS);
				}
				postId = (Integer) params.get("postId");
			}
			Integer userId = getUserIdByToken(token);
			if (postId == null || postId <= 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}

			Map<String, Object> savePraisemap = kffRmiService.savePraise(userId, postId);
			bre.setData(savePraisemap);
			SyseUtil.systemErrOutJson(bre);
		} catch (RestServiceException e) {
			logger.error("PraiseController savePostPraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController savePostPraise:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: cancelPraise
	 * @Description: 取消点赞
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/cancelPostPraise", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity cancelPostPraise(HttpServletRequest request, String token, Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if (null == token && null == postId) {
				JSONObject params = HtmlUtils.getRequestContent(request);
				token = (String) params.get("token");
				postId = (Integer) params.get("postId");
			}
			Integer userId = getUserIdByToken(token);
			if (postId == null || postId <= 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}

			int praiseNum = kffRmiService.cancelPraise(userId, postId);
			map.put("praiseNum", praiseNum);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("PraiseController cancelPostPraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController cancelPostPraise:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
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
	@RequestMapping(value = "/saveCommentsPraise", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveCommentsPraise(HttpServletRequest request, String token, Integer commentsId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if (token == null && commentsId == null) {
				JSONObject params = HtmlUtils.getRequestContent(request);
				token = (String) params.get("token");
				commentsId = (Integer) params.get("commentsId");

			}
			Integer userId = getUserIdByToken(token);
			if (commentsId == null || commentsId <= 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_COMMENTSID);
			}

			int praiseNum = kffRmiService.saveCommentsPraise(userId, commentsId);
			map.put("praiseNum", praiseNum);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("PraiseController saveCommentsPraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController saveCommentsPraise:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
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
	@RequestMapping(value = "/cancelCommentsPraise", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity cancelCommentsPraise(HttpServletRequest request, String token, Integer commentsId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if (null == token && null == commentsId) {
				JSONObject params = HtmlUtils.getRequestContent(request);
				token = (String) params.get("token");
				commentsId = (Integer) params.get("commentsId");
			}
			Integer userId = getUserIdByToken(token);
			if (commentsId == null || commentsId <= 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_COMMENTSID);
			}

			int praiseNum = kffRmiService.cancelCommentsPraise(userId, commentsId);
			map.put("praiseNum", praiseNum);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("PraiseController cancelCommentsPraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController cancelCommentsPraise:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

}
