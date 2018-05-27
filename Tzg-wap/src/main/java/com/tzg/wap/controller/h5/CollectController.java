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

@Controller(value = "KFFCollectController")
@RequestMapping("/kff/collect")
public class CollectController extends BaseController {
	private static Logger log = Logger.getLogger(CollectController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	/**
	 * 收藏操作
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param postId
	 * @return
	 */
	@RequestMapping(value = "/saveCollect", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveCollect(HttpServletRequest request,
			HttpServletResponse response,  Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer userId = getUserIdByToken(token);
			if (postId == null || postId <= 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}

			kffRmiService.saveCollect(userId, postId);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("CollectController saveCollect:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("CollectController saveCollect:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: cancelCollect
	 * @Description: 取消收藏
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/cancelCollect", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity cancelCollect(HttpServletRequest request,
			HttpServletResponse response,  Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer userId = getUserIdByToken(token);
			if (postId == null || postId <= 0) {
				throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
			}

			kffRmiService.cancelCollect(userId, postId);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("CollectController cancelCollect:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("CollectController cancelCollect:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
