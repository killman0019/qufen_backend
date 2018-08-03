package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.entitys.kff.activity.RewardDetail;
import com.tzg.rest.exception.rest.RestServiceException;

/** 
* @ClassName: RewardDetailRmiService 
* @Description: TODO<奖励远程服务调用> 
* @author linj<作者>
* @date 2018年7月5日 下午1:32:17 
* @version v1.0.0 
*/
public interface RewardDetailRmiService {

	public RewardDetail findById(Integer id);
	
	public List<RewardDetail> findListByAttr(Map<String, Object> map);
	
	public void save(RewardDetail rewardDetail);
	
	public MiningActivity add(MiningActivity mnAct,Integer userId);
	
	public PageResult<RewardDetail> findRewardDetailPage(PaginationQuery query) throws RestServiceException;

}
