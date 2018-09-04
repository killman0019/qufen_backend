package com.tzg.wap.controller.h5;

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
import com.tzg.common.utils.HtmlUtils;
import com.tzg.common.utils.SyseUtil;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "KFFFollowController")
@RequestMapping("/kff/follow")
public class FollowController extends BaseController {
	private static Logger log = Logger.getLogger(FollowController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	/** 
	* @Title: saveFollow 
	* @Description: TODO <关注接口>
	* @author linj <方法创建作者>
	* @create 上午11:24:15
	* @param @param request
	* @param @param token 用户登录唯一标识
	* @param @param followType 关注类型：1-关注项目;2-关注帖子；3-关注用户
	* @param @param followedId 关注项目即为projectId,关注帖子即为postId,关注用户即为userId
	* @param @return <参数说明> 
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午11:24:15
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/saveFollow", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity saveFollow(HttpServletRequest request, String token, Integer followType, Integer followedId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (followedId == null && followType == null && StringUtils.isBlank(token)) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				token = (String) requestContent.get("token");
				followType = (Integer) requestContent.get("followType");
				followedId = (Integer) requestContent.get("followedId");
			}
			if (followedId == null || followType == null || StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer userId = getUserIdByToken(token);
			kffRmiService.saveFollow(userId, followType, followedId);
			map.put("followStatus", 1);
			bre.setData(map);
			SyseUtil.systemErrOutJson(bre);
		} catch (RestServiceException e) {
			log.error("FollowController saveFollow:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("FollowController saveFollow:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/** 
	* @Title: cancelFollow 
	* @Description: TODO <取消关注接口>
	* @author linj <方法创建作者>
	* @create 上午11:27:02
	* @param @param request
	* @param @param token 用户登录唯一标识
	* @param @param followType 关注类型：1-关注项目;2-关注帖子；3-关注用户
	* @param @param followedId 关注的id
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午11:27:02
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/cancelFollow", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity cancelFollow(HttpServletRequest request, String token, Integer followType, Integer followedId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (followedId == null && followType == null && StringUtils.isBlank(token)) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				token = (String) requestContent.get("token");
				followType = (Integer) requestContent.get("followType");
				followedId = (Integer) requestContent.get("followedId");
			}
			if (followedId == null || followType == null || StringUtils.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer userId = getUserIdByToken(token);
			kffRmiService.cancelFollow(userId, followType, followedId);
			map.put("followStatus", 0);
			bre.setData(map);
		} catch (RestServiceException e) {
			log.error("FollowController cancelFollow:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			log.error("FollowController cancelFollow:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
