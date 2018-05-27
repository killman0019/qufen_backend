package com.tzg.rest.controller.kff;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value="KFFFollowController")
@RequestMapping("/kff/follow")
public class FollowController extends BaseController {
	private static Logger log = Logger.getLogger(FollowController.class);
	
	@Autowired
	private KFFRmiService kffRmiService;
	
	/**
	 * 
	* @Title: saveFollow
	* @Description: 关注
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/saveFollow",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity saveFollow(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
            String token = (String) params.get("token");
            Integer followType = (Integer) params.get("followType");   
            Integer followedId = (Integer) params.get("followedId");
			Integer userId = getUserIdByToken(token);
			if(followType == null || followType <= 0){
				throw new RestServiceException(RestErrorCode.MISSING_ARG_FOLLOWTYPE);
			}
			if(followedId == null || followedId <= 0){
				throw new RestServiceException(RestErrorCode.MISSING_ARG_FOLLOWID);
			}
	
			kffRmiService.saveFollow(userId,followType,followedId);
            
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("FollowController saveFollow:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("FollowController saveFollow:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 
	* @Title: cancelFollow
	* @Description: 取消关注
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/cancelFollow",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity cancelFollow(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
            String token = (String) params.get("token");
            Integer followType = (Integer) params.get("followType");   
            Integer followedId = (Integer) params.get("followedId");        
    		Integer userId = getUserIdByToken(token);
			if(followType == null || followType <= 0){
				throw new RestServiceException(RestErrorCode.MISSING_ARG_FOLLOWTYPE);
			}
			if(followedId == null || followedId <= 0){
				throw new RestServiceException(RestErrorCode.MISSING_ARG_FOLLOWID);
			}
	
			kffRmiService.cancelFollow(userId,followType,followedId);
            
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("FollowController cancelFollow:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("FollowController cancelFollow:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
}


