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
import com.tzg.common.utils.HtmlUtils;
import com.tzg.entitys.kff.commendation.CommendationRequest;
import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "tokenController")
@RequestMapping("/kff/token")
public class TokenController extends BaseController {
	private static Logger log = Logger.getLogger(TokenController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	/**
	 * 
	* @Title: commendation 
	* @Description: TODO <打赏接口>
	* @author zhangdd <方法创建作者>
	* @create 上午9:42:12
	* @param @param request
	* @param @param response
	* @param @param commendationRequest
	* @param @param token
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午9:42:12
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	@RequestMapping(value = "/commendation", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity commendation(HttpServletRequest request, HttpServletResponse response, CommendationRequest commendationRequest, String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if (token == null) {
				JSONObject params = HtmlUtils.getRequestContent(request);
				token = (String) params.get("token");

				commendationRequest = params.getObject("commendationRequest", CommendationRequest.class);
			}
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
