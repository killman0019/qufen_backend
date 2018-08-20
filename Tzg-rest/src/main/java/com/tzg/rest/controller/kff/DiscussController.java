package com.tzg.rest.controller.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.base.BaseRequest;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.discuss.DiscussRequest;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.KFFUserRmiService;

@Controller(value = "KFFDiscussController")
@RequestMapping("/kff/discuss")
public class DiscussController extends BaseController {
	private static Logger log = Logger.getLogger(DiscussController.class);
	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private KFFUserRmiService userRmiService;

	/**
	 * @Title: saveDiscuss
	 * @Description: 发表讨论
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/saveDiscuss", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveDiscuss(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			DiscussRequest discussRequest = getParamMapFromRequestPolicy(request, DiscussRequest.class);
			String token = discussRequest.getToken();
			if(StringUtils.isBlank(token)) {
				bre.setCode(RestErrorCode.MISSING_ARGS.getValue());
				bre.setMsg(RestErrorCode.MISSING_ARGS.getErrorReason());
				return bre;
			}
			Integer userId = getUserIdByToken(token);
			//判断用户被禁用时，不能发爆料
			boolean flag = userRmiService.findUserByStatus(userId);
			if(!flag) {
				bre.setCode(RestErrorCode.ACCOUNT_LOCKED.getValue());
				bre.setMsg(sysGlobals.DISABLE_ACCOUNT_MSG);
				return bre;
			}
			discussRequest.setCreateUserId(userId);
			map = kffRmiService.saveDiscuss(discussRequest);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("DiscussController saveDiscuss:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("DiscussController saveDiscuss:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * @Title: tagList
	 * @Description: 获取标签列表
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/tagList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity tagList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer userId = getUserIdByToken(token);
			List<Dtags> tagList = kffRmiService.findAllTags();
			map.put("tagList", tagList);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("DiscussController tagList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("DiscussController tagList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
