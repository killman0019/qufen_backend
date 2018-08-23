package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.entitys.kff.dtags.DtagsReponse;
import com.tzg.entitys.kff.tagstype.TagsTypeResponse;
import com.tzg.rest.exception.rest.RestServiceException;

/**
 * 
* @ClassName: KFFTagsRmiService 
* @Description: TODO<标签和标签类型服务层> 
* @author zhangdd<作者>
* @date 2018年8月23日 下午1:38:02 
* @version v1.0.0
 */
public interface KFFTagsRmiService {
	/**
	 * 
	 * 
	* @Title: getEvaTagsAndTagType 
	* @Description: TODO <获得评测的标签和标签类型>
	* @author zhangdd <方法创建作者>
	 * @param typeId 
	* @create 下午1:51:03
	* @param @return
	* @param @throws RestServiceException <参数说明>
	* @return Map<String,List<Dtags>> 
	* @throws 
	* @update 下午1:51:03
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public List<Map<String, List<DtagsReponse>>> getEvaTagsAndTagType(Integer typeId) throws RestServiceException;

	/**
	 * 
	* @Title: getTagsAndTagType 
	* @Description: TODO <获得标签和标签类型>
	* @author zhangdd <方法创建作者>
	* @create 下午3:42:58
	* @param @param typeId
	* @param @return
	* @param @throws RestServiceException <参数说明>
	* @return List<TagsTypeResponse> 
	* @throws 
	* @update 下午3:42:58
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public List<TagsTypeResponse> getTagsAndTagType(Integer typeId) throws RestServiceException;

}
