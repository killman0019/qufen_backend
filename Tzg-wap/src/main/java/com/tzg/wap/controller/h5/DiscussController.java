package com.tzg.wap.controller.h5;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.tzg.common.base.BaseRequest;
import com.tzg.entitys.discussImages.DiscussImages;
import com.tzg.entitys.kff.discuss.DiscussRequest;
import com.tzg.entitys.kff.discuss.DiscussRequestData;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.evaluation.EvaluationData;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "KFFDiscussController")
@RequestMapping("/kff/discuss")
public class DiscussController extends BaseController {
	private static Logger log = Logger.getLogger(DiscussController.class);
	@Autowired
	private KFFRmiService kffRmiService;

	/**
	 * 发表讨论
	 * 
	 * @param discussRequest
	 * @return String projectName, String disscussContents, String tagInfos, String token, String
	 *         discussImages, String postTitle
	 */
	@RequestMapping(value = "/saveDiscuss", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveDiscuss(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			DiscussRequestData discussRequestData = JSON.parseObject(data, DiscussRequestData.class);
			// 进行json转换
			String token = discussRequestData.getToken();
			String projectName = discussRequestData.getProjectName();
			String disscussContents = discussRequestData.getDisscussContents();
			String postTitle = discussRequestData.getPostTitle();
			String discussImagesl = StringEscapeUtils.unescapeHtml(discussRequestData.getDiscussImages());
			String tagInfosl = StringEscapeUtils.unescapeHtml(discussRequestData.getTagInfos());
			if (null == token) {
				throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
			}

			// List<String> name = JSON.parseObject(pic, ArrayList.class);
			DiscussRequest discussRequest = new DiscussRequest();
			Integer userId = getUserIdByToken(token);
			discussRequest.setCreateUserId(userId);
			KFFProject subProject = kffRmiService.selectProjectByprojectName(projectName);
			if (null == subProject) {
				throw new RestServiceException(RestErrorCode.SYS_ERROR);
			}
			discussRequest.setToken(token);
			discussRequest.setProjectId(subProject.getProjectId());
			discussRequest.setDisscussContents(disscussContents);
			discussRequest.setTagInfos(tagInfosl);
			discussRequest.setDiscussImages(discussImagesl);
			discussRequest.setPostTitle(postTitle);
			// name是个图片数组
			/*if (null == name) {
				discussRequest.setDiscussImages(null);
			}
			if (name.size() <= 3) {
				String uploadIeviwList = kffRmiService.uploadIeviwList(name);
				discussRequest.setDiscussImages(uploadIeviwList);
			} else {
				int i = 0;
				List<String> strl = new ArrayList<String>();
				for (String nameStr : name) {
					i = i + 1;
					if (i == 4) {
						break;
					}
					strl.add(nameStr);

				}
				String uploadIeviwList = kffRmiService.uploadIeviwList(strl);
				discussRequest.setDiscussImages(uploadIeviwList);
			}
			*/
			kffRmiService.saveDiscuss(discussRequest);
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
	 * 标签列表
	 * 
	 * @param baseRequest
	 * @return
	 */
	@RequestMapping(value = "/tagList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity tagList(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			List<Dtags> tagList = kffRmiService.findAllTags();
			// List<Dtags> tagList = kffRmiService.findAllTagsName();
			List<Dtags> tagListnew = new ArrayList<Dtags>();

			for (Dtags tag : tagList) {
				Dtags dtags = new Dtags();
				dtags.setTagId(tag.getTagId());
				dtags.setTagName(tag.getTagName());
				tagListnew.add(dtags);
			}
			map.put("tagList", tagListnew);
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
