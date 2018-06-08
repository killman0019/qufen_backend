package com.tzg.rest.controller.kff;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.entitys.kff.message.KFFMessage;
import com.tzg.entitys.kff.suggest.SuggestRequest;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value="KFFMessageController")
@RequestMapping("/kff/message")
public class MessageController extends BaseController {
	private static Logger log = Logger.getLogger(MessageController.class);
	
	@Autowired
	private KFFRmiService kffRmiService;
	
	
	/**
	 * 
	* @Title: messageList
	* @Description: 获取消息列表
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/messageList",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity messageList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			String token = baseRequest.getToken();
			Integer userId = getUserIdByToken(token);
			baseRequest.setUserId(userId);
			PaginationQuery query = new PaginationQuery();
	        query.addQueryData("userId", userId +"");
	        query.addQueryData("status", "1");
	        query.setPageIndex(baseRequest.getPageIndex());
	        query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<KFFMessage> messages = kffRmiService.findPageMyMessages(query);
            map.put("messages", messages);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("MessageController messageList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("MessageController messageList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 
	* @Title: messageDetail
	* @Description: 查看单个消息详情
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/messageDetail",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity messageDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
            String token = (String) params.get("token");
            Integer messageId = (Integer) params.get("messageId");            
			Integer userId = getUserIdByToken(token);
			if(messageId == null){
				throw new RestServiceException(RestErrorCode.MISSING_ARG_MESSAGEID);
			}
			KFFMessage message = kffRmiService.getMessageDetail(userId,messageId);
            
			map.put("message", message);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("MessageController messageDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("MessageController messageDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 
	* @Title: deleteMessage
	* @Description: 删除消息
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/deleteMessage",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity deleteMessage(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
            String token = (String) params.get("token");
            Integer messageId = (Integer) params.get("messageId");            
			Integer userId = getUserIdByToken(token);
			if(messageId == null){
				throw new RestServiceException(RestErrorCode.MISSING_ARG_MESSAGEID);
			}
	
			kffRmiService.deleteMessage(userId,messageId);
            
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("MessageController deleteMessage:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("MessageController deleteMessage:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
}


