package com.tzg.wap.controller.h5;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.entitys.kff.commendation.CommendationRequest;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

public class TokenController extends BaseController {
	private static Logger log = Logger.getLogger(TokenController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	/**
	 * 
	 * @Title: commendation
	 * @Description: 捐赠打赏
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/commendation", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity commendation(HttpServletRequest request,
			HttpServletResponse response,
			CommendationRequest commendationRequest) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			String token = (String) request.getSession().getAttribute("token");
			Integer userId = getUserIdByToken(token);
			commendationRequest.setSendUserId(userId);
			kffRmiService.saveCommendation(commendationRequest);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("TokenController commendation:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("TokenController commendation:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}