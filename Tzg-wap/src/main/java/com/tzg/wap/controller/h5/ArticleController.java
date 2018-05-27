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

import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.entitys.kff.article.Article;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "KFFArticleController")
@RequestMapping("/kff/article")
public class ArticleController extends BaseController {
	private static Logger log = Logger.getLogger(ArticleController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	/**
	 * 发表文章
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param projectId
	 * @param postTitle
	 * @return
	 */
	@RequestMapping(value = "/saveArticle", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveArticle(HttpServletRequest request, HttpServletResponse response, String projectName, String postTitle,
			String postSmallImages, String articleContents, String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (null == projectName) {
			throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
		}
		if (null == token) {
			throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		}
		// 将项目名进行拆分 进行查询
		String[] str = projectName.split("\\/");
		System.out.println(str[0]);
		System.out.println(str[1]);
		KFFProject kffProject = new KFFProject();
		kffProject.setProjectChineseName(str[0]);
		kffProject.setProjectCode(str[1]);
		KFFProject SubProject = kffRmiService.findProjectIdByCodeAndChineseName(kffProject);
		if (null == SubProject) {
			throw new RestServiceException(RestErrorCode.SYS_ERROR);
		}
		try {
			Integer userId = AccountTokenUtil.decodeAccountToken(token);
			ArticleRequest articleRequest = new ArticleRequest();
			articleRequest.setCreateUserId(userId);
			// articleRequest.setPostSmallImages(kffRmiService.uploadIeviw(postSmallImages));
			articleRequest.setArticleContents(articleContents);
			articleRequest.setPostTitle(postTitle);
			articleRequest.setProjectId(SubProject.getProjectId());
			kffRmiService.saveArticle(articleRequest);
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
