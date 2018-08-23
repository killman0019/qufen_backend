package com.tzg.rmi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.kff.DtagsService;
import com.tzg.common.service.kff.TagsTypeService;
import com.tzg.common.utils.DozerMapperUtils;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.dtags.DtagsReponse;
import com.tzg.entitys.kff.tagstype.TagsType;
import com.tzg.entitys.kff.tagstype.TagsTypeResponse;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFTagsRmiService;

/**
 * 
* @ClassName: KFFTagsRmiServiceImpl 
* @Description: TODO<标签和标签类型服务实现类> 
* @author zhangdd<作者>
* @date 2018年8月23日 下午1:43:46 
* @version v1.0.0
 */
public class KFFTagsRmiServiceImpl implements KFFTagsRmiService {
	@Autowired
	private DtagsService dtagsService;
	@Autowired
	private TagsTypeService tagsTypeService;

	@Override
	public List<Map<String, List<DtagsReponse>>> getEvaTagsAndTagType(Integer typeId) throws RestServiceException {
		// TODO 获得评测的标签和标签类型

		Map<String, List<DtagsReponse>> result = new HashMap<String, List<DtagsReponse>>();
		// 评测的类型是固定id 为1
		List<Map<String, List<DtagsReponse>>> resultList = new ArrayList<Map<String, List<DtagsReponse>>>();

		Map<String, Object> tagsTypeMap = new HashMap<String, Object>();
		if (typeId == 0) {
			tagsTypeMap.put("isShow", 1);
			tagsTypeMap.put("status", 1);
			tagsTypeMap.put("orderByKey", "tag_type_order_numder");
			tagsTypeMap.put("orderBySort", "ASC");
		} else if (typeId == 1) {
			tagsTypeMap.put("isShow", 1);
			tagsTypeMap.put("status", 1);
			tagsTypeMap.put("typeId", 1);
		}
		List<TagsType> tagsTypeList = tagsTypeService.findByMap(tagsTypeMap);
		if (CollectionUtils.isEmpty(tagsTypeList)) {
			throw new RestServiceException("标签类型错误,请联系客服");
		}

		for (TagsType tagsType : tagsTypeList) {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("typeId", tagsType.getTypeId());

			List<DtagsReponse> dtagsReponseList = dtagsService.getEvaTagsAndTagType(map);
			if (CollectionUtils.isNotEmpty(dtagsReponseList)) {
				String typeName = dtagsReponseList.get(0).getTypeName();
				result.put(typeName, dtagsReponseList);
			}
			resultList.add(result);
		}
		return resultList;
	}

	@Override
	public List<TagsTypeResponse> getTagsAndTagType(Integer typeId) throws RestServiceException {
		// TODO 获得标签类型和标签

		List<TagsTypeResponse> tagsTypeResponseList = new ArrayList<TagsTypeResponse>();
		Map<String, Object> tagsTypeMap = new HashMap<String, Object>();
		if (typeId == 0) {
			tagsTypeMap.put("isShow", 1);
			tagsTypeMap.put("status", 1);
			tagsTypeMap.put("orderByKey", "tag_type_order_numder");
			tagsTypeMap.put("orderBySort", "ASC");
		} else if (typeId == 1) {
			tagsTypeMap.put("isShow", 1);
			tagsTypeMap.put("status", 1);
			tagsTypeMap.put("typeId", 1);
		}
		List<TagsType> tagsTypeList = tagsTypeService.findByMap(tagsTypeMap);
		if (CollectionUtils.isNotEmpty(tagsTypeList)) {
			for (TagsType tagsType : tagsTypeList) {
				TagsTypeResponse tagsTypeResponse = new TagsTypeResponse();
				DozerMapperUtils.map(tagsType, tagsTypeResponse);
				Map<String, Object> dtagsMap = new HashMap<String, Object>();
				dtagsMap.put("typeId", tagsType.getTypeId());
				List<Dtags> dtagsList = dtagsService.findByMap(dtagsMap);
				tagsTypeResponse.setDtagsList(dtagsList);
				tagsTypeResponseList.add(tagsTypeResponse);
			}
		}
		return tagsTypeResponseList;
	}
}
