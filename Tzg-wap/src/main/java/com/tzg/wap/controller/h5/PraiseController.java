package com.tzg.wap.controller.h5;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
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
	@RequestMapping(value = "/savePraise", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity savePraise(HttpServletRequest request,
			HttpServletResponse response,  Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer userId = getUserIdByToken(token);
			if (postId == null || postId <= 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}

			kffRmiService.savePraise(userId, postId);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("PraiseController savePraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController savePraise:{}", e);
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
	@RequestMapping(value = "/cancelPraise", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity cancelPraise(HttpServletRequest request,
			HttpServletResponse response,  Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer userId = getUserIdByToken(token);
			if (postId == null || postId <= 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}

			kffRmiService.cancelPraise(userId, postId);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("PraiseController cancelPraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController cancelPraise:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
