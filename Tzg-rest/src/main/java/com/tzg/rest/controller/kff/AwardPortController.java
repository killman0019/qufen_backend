package com.tzg.rest.controller.kff;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value="KFFAwardPortController")
@RequestMapping("/kff/awardPort")
public class AwardPortController extends BaseController {
	private static Logger log = Logger.getLogger(AwardPortController.class);
	
	@Autowired
	private KFFRmiService kffRmiService;
	
	/**
	* @Title: saveArticle
	* @Description: 收支明细
	* @param request
	* @param  response   
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/details",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity details(HttpServletRequest request) {
        BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            JSONObject params = getParamMapFromRequestPolicy(request);
            String token = (String) params.get("token");
            Integer pageIndex = (Integer) params.get("pageIndex") == null ? 1:(Integer) params.get("pageIndex");
            Integer pageSize = (Integer) params.get("pageSize") == null ? 10:(Integer) params.get("pageSize");
            
            if (StringUtils.isBlank(token)) {
            	throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
            }		         
            Integer userId = null;
			try{
				userId = AccountTokenUtil.decodeAccountToken(token);
			}catch(Exception e){
				logger.error("updateUserInfo decodeAccountToken error:{}",e);
				return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR,e.getMessage());
			}
			if(userId == null){
        		throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
        	}

			KFFUser loginaccount = kffRmiService.findUserById(userId);
			
			if(loginaccount == null){
				return this.resResult(RestErrorCode.USER_NOT_EXIST);
			}
			
            PaginationQuery query = new PaginationQuery();
            query.addQueryData("userId", userId +"");
            query.setPageIndex(pageIndex);
            query.setRowsPerPage(pageSize);
            PageResult<Tokenrecords> result = kffRmiService.findPageMyTokenRecords(query);
            
            map.put("myTokenRecords", result);

            bre.setData(map);
        } catch (RestServiceException e) {
            logger.warn("myTokenRecordsList warn:{}", e);
            return this.resResult(e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("myTokenRecordsList error:{}", e);
            return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
        }
        return bre;
    }
	
}


