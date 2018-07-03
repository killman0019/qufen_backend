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
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.entitys.kff.project.SubmitKFFProjectRequest;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "KFFCommentsController")
@RequestMapping("/kff/comments")
public class CommentsController extends BaseController {
	private static Logger log = Logger.getLogger(CommentsController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	/**
	 * @Title: saveComment
	 * @Description: 发表评论
	 * @param @param request
	 * @param @param response
	 * @param @return
	 * @return BaseResponseEntity
	 * @see
	 * @throws
	 */
	@RequestMapping(value = "/saveComment", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveComment(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			CommentsRequest comment = getParamMapFromRequestPolicy(request, CommentsRequest.class);
			System.err.println("comment" + JSON.toJSONString(comment));
			String token = comment.getToken();
			Integer userId = getUserIdByToken(token);
			comment.setCommentUserId(userId);
			Map<String, Object> saveComment = kffRmiService.saveComment(comment);
			System.err.println(JSON.toJSON(saveComment));
			bre.setData(saveComment);
		} catch (RestServiceException e) {
			logger.error("CommentsController saveComment:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("CommentsController saveComment:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

}
