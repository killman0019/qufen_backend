package com.tzg.rest.controller.kff;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.SyseUtil;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.entitys.kff.activity.PostShare;
import com.tzg.entitys.kff.post.Post;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.MiningActivityRmiService;
import com.tzg.rmi.service.PostShareRmiService;

/** 
* @ClassName: PostShareController 
* @Description: TODO<活动分享接口> 
* @author linj<作者>
* @date 2018年7月5日 下午1:23:02 
* @version v1.0.0 
*/
@Controller
@RequestMapping("/kff/postShare")
public class PostShareController extends BaseController {
	private static Logger logger = Logger.getLogger(PostShareController.class);

	@Autowired
	private MiningActivityRmiService miningActivityRmiService;
	@Autowired
	private PostShareRmiService postShareRmiService;

	@Autowired
	private KFFRmiService kffRmiService;

	/** 
	* @Title: addPostShare 
	* @Description: TODO <活动分享成功回调页面>
	* @author linj <方法创建作者>
	* @create 下午3:12:48
	* @param @param request
	* @param @param token 用户登录的token
	* @param @param activityId 活动id
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午3:12:48
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/addPostShare")
	public BaseResponseEntity addPostShare(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			JSONObject policyJson = getParamJsonFromRequestPolicy(request);
			Integer id = (Integer) policyJson.get("activityId");
			Integer postId = (Integer) policyJson.get("postId");

			String token = (String) policyJson.get("token");
			if (StringUtil.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer userId = getUserIdByToken(token);

			PostShare postShare = new PostShare();
			Date date = new Date();
			postShare.setCreatedAt(date);
			postShare.setUpdatedAt(date);
			postShare.setActivityId(id);
			postShare.setUserId(userId);
			Post post = null;
			if (null != postId && 0 != postId) {
				post = kffRmiService.findPostByPostId(postId);
				if (null == post) {
					throw new RestServiceException(RestErrorCode.MISSING_ARGS);
				}
				postShare.setArticleId(postId);
				postShare.setType(post.getPostType());
			} else {
				MiningActivity mnAct = miningActivityRmiService.findById(id);
				if (null == mnAct) {
					throw new RestServiceException(RestErrorCode.NO_DATA_MSG);
				}
				postShare.setType(mnAct.getType());
				postShare.setArticleId(mnAct.getArticleId());
			}

			Map<String, Object> shareMap = postShareRmiService.save(postShare, post);
			bre.setData(shareMap);
			SyseUtil.systemErrOutJson(bre);
		} catch (RestServiceException e) {
			logger.error("NewsFlashController getNewsFlashPageList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("NewsFlashController getNewsFlashPageList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
