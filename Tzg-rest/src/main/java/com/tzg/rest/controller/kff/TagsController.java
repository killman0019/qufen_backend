package com.tzg.rest.controller.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.utils.SyseUtil;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.tagstype.TagsTypeResponse;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.DtagsRmiService;
import com.tzg.rmi.service.KFFTagsRmiService;

@Controller
@RequestMapping("/kff/tags")
public class TagsController extends BaseController {
	private static Logger logger = Logger.getLogger(TagsController.class);
	@Autowired
	private KFFTagsRmiService kffTagsRmiService;
	@Autowired
	private DtagsRmiService dTagsRmiService;

	/**
	 * 
	* @Title: getTagsAndTagType 
	* @Description: TODO <获得评测的标签和标签类型>
	* @author zhangdd <方法创建作者>
	* @create 下午1:45:53
	* @param @param baseRequest
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午1:45:53
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	@RequestMapping(value = "/getTagsAndTagType", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getEvaTagsAndTagType(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject policyJson = getParamMapFromRequestPolicy(request);
			Integer typeId = policyJson.getInteger("typeId");
			List<TagsTypeResponse> result = kffTagsRmiService.getTagsAndTagType(typeId);
			map.put("result", result);
			bre.setData(map);
			SyseUtil.systemErrOutJson(bre);
		} catch (RestServiceException e) {
			logger.error("TagsController getTagsAndTagType:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("TagsController getTagsAndTagType:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: getDTagsInfo 
	* @Description: TODO <爆料页面获取话题接口>
	* @author linj <方法创建作者>
	* @create 下午7:29:07
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午7:29:07
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/getDTagsInfo")
	public BaseResponseEntity getDTagsInfo(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			Map<String,Object> seMap = new HashMap<>();
			seMap.put("status", 1);
			seMap.put("stickTop", 1);//是否推荐：0-否，1-是
			seMap.put("sortField", "stick_update_time");
			seMap.put("sortSequence", "desc");
			seMap.put("startRecord", 0);
			seMap.put("endRecord", 20);
			List<Dtags> data = dTagsRmiService.findListByAttr(seMap);
			if(data.isEmpty()) {
				bre.setNoDataMsg();
				return bre;
			}
			bre.setData(data);
		} catch (RestServiceException e) {
			logger.error("TagsController getDTagsInfo:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("TagsController getDTagsInfo:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: getDTagDetail 
	* @Description: TODO <获取单个话题的信息接口>
	* @author linj <方法创建作者>
	* @create 上午10:12:15
	* @param @param request
	* @param @param id 话题id
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午10:12:15
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/getDTagDetail")
	public BaseResponseEntity getDTagDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			JSONObject params = getParamJsonFromRequestPolicy(request);
			Integer id = params.getInteger("id");
			if(null==id) {
				bre.setNoDataMsg();
				return bre;
			}
			Dtags dTags = dTagsRmiService.findById(id);
			if(null==dTags) {
				bre.setNoDataMsg();
				return bre;
			}
			bre.setData(dTags);
		} catch (RestServiceException e) {
			logger.error("TagsController getDTagDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("TagsController getDTagDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
