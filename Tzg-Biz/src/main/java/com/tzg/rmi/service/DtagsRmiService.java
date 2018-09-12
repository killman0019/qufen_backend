package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.rest.exception.rest.RestServiceException;

/** 
* @ClassName: DtagsRmiService 
* @Description: TODO<话题远程服务调用> 
* @author linj<作者>
* @date 2018年7月5日 下午1:32:17 
* @version v1.0.0 
*/
public interface DtagsRmiService {

	public PageResult<Dtags> findPage(PaginationQuery query) throws RestServiceException;
	
	public List<Dtags> findListByAttr(Map<String, Object> map);
	
	public void updateByMap(Map<String,Object> map) throws RestServiceException;
	
	public Dtags findById(Integer id);

}
