package com.tzg.wap.controller.h5;

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
import com.tzg.common.utils.HtmlUtils;
import com.tzg.common.utils.SyseUtil;
import com.tzg.entitys.kff.tagstype.TagsTypeResponse;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFTagsRmiService;

@Controller(value = "TagsController")
@RequestMapping("/kff/tags")
public class TagsController extends BaseController {
	private static Logger logger = Logger.getLogger(TagsController.class);
	@Autowired
	private KFFTagsRmiService kffTagsRmiService;

	/**
	 * 
	* @Title: getTagsAndTagType 
	* @Description: TODO <获得评测的标签和标签类型>
	* @author zhangdd <方法创建作者>
	* @create 下午1:45:53
	* @param @param typeId 评测标签时typeId传1 取全部标签及标签类型时取0
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午1:45:53
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	@ResponseBody
	@RequestMapping(value = "/getTagsAndTagType", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity getEvaTagsAndTagType(HttpServletRequest request,Integer typeId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(typeId==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				typeId = (Integer) requestContent.get("typeId");
			}
			if(typeId==null) {
				bre.setNoRequstData();
				return bre;
			}
			List<TagsTypeResponse> result = kffTagsRmiService.getTagsAndTagType(typeId);
			if(result.isEmpty()) {
				bre.setNoDataMsg();
				return bre;
			}
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
}