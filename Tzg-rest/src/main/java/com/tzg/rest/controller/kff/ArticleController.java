package com.tzg.rest.controller.kff;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzg.common.base.BaseRequest;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.utils.PolicyUtil;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.KFFUserRmiService;

@Controller(value = "KFFArticleController")
@RequestMapping("/kff/article")
public class ArticleController extends BaseController {
	private static Logger log = Logger.getLogger(ArticleController.class);

	@Autowired
	private KFFRmiService kffRmiService;
	@Autowired
	private KFFUserRmiService userRmiService;
	/**
	 * @Title: saveArticle
	 * @Description: 发表文章
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/saveArticle", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveArticle(BaseRequest baseRequest,HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String policy;
			ArticleRequest articleRequest;
			try{
				policy = PolicyUtil.decryptPolicy(baseRequest.getPolicy());
			}catch(Exception e){
				e.printStackTrace();
				throw new RestServiceException(RestErrorCode.DECRYPT_POLICY_ERROR);
			}
			try{
				articleRequest = JSON.parseObject(policy, ArticleRequest.class);
			}catch(Exception e){
				e.printStackTrace();
				throw new RestServiceException(RestErrorCode.PARSE_JSON_ERROR);
				
			}
			if(null == articleRequest) {
				bre.setNoRequstData();
				return bre;
			}
			String token = articleRequest.getToken();
			Integer userId = getUserIdByToken(token);
			//判断用户被禁用时，不能发文章
			boolean flag = userRmiService.findUserByStatus(userId);
			if(!flag) {
				bre.setCode(RestErrorCode.ACCOUNT_LOCKED.getValue());
				bre.setMsg(sysGlobals.DISABLE_ACCOUNT_MSG);
				return bre;
			}
			articleRequest.setCreateUserId(userId);
			map = kffRmiService.saveArticle(articleRequest, null);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ArticleController saveArticle:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ArticleController saveArticle:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

}
