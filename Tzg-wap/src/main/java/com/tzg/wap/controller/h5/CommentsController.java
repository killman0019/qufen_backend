package com.tzg.wap.controller.h5;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.utils.HtmlUtils;
import com.tzg.common.utils.SyseUtil;
import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.entitys.kff.comments.CommentsShareRequest;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.KFFRobotRmiService;
import com.tzg.rmi.service.KFFUserRmiService;

@Controller(value = "KFFCommentsController")
@RequestMapping("/kff/comments")
public class CommentsController extends BaseController {
	private static Logger log = Logger.getLogger(CommentsController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	@Autowired
	private KFFUserRmiService kffUserRmiService;

	@Autowired
	private KFFRobotRmiService kffRobotRmiService;

	/**
	 * 评论帖子
	 * 
	 * @param request
	 * @param response
	 * @param comment
	 * @return
	 */
	@RequestMapping(value = "/saveComment", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveComment(HttpServletRequest request, HttpServletResponse response, CommentsRequest comment, String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			if (null == token) {
				JSONObject params = HtmlUtils.getRequestContent(request);
				token = (String) params.get("token");
				comment = new CommentsRequest();
				String commentContent = (String) params.get("commentContent");
				Integer postId = params.getInteger("postId");
				Integer becommentedId = null;
				if (StringUtils.isNotEmpty(params.getInteger("becommentedId") + "")) {
					becommentedId = params.getInteger("becommentedId");
				}
				Integer parentCommentsId = null;
				if (StringUtils.isNotEmpty(params.getInteger("parentCommentsId") + "")) {
					parentCommentsId = params.getInteger("parentCommentsId");
				}

				comment.setPostId(postId);
				comment.setCommentContent(commentContent);
				comment.setParentCommentsId(parentCommentsId);
				comment.setBecommentedId(becommentedId);

			}
			Integer userId = getUserIdByToken(token);
			comment.setCommentUserId(userId);
			CommentsRequest resultComment = kffRobotRmiService.createComment(comment, userId);

			Map<String, Object> saveComment = kffRmiService.saveComment(resultComment);
			bre.setData(saveComment);
			SyseUtil.systemErrOutJson(bre);
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
