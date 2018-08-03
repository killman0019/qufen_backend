package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.rest.exception.rest.RestServiceException;

/** 
* @ClassName: MiningActivityRmiService 
* @Description: TODO<快讯远程服务调用> 
* @author linj<作者>
* @date 2018年7月5日 下午1:32:17 
* @version v1.0.0 
*/
public interface MiningActivityRmiService {

	public PageResult<MiningActivity> findMiningActivityPage(PaginationQuery query) throws RestServiceException;
	
	public List<MiningActivity> findListByAttr(Map<String, Object> map);
	
	public void updateByMap(Map<String,Object> map) throws RestServiceException;
	
	public MiningActivity findById(Integer id);
	
	public void manualActivity();

}
