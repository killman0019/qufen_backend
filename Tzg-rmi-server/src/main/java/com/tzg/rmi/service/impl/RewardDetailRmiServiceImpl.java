package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.kff.RewardDetailService;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.entitys.kff.activity.RewardDetail;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.RewardDetailRmiService;

public class RewardDetailRmiServiceImpl implements RewardDetailRmiService {

	private static final Log logger = LogFactory.getLog(RewardDetailRmiServiceImpl.class);

	@Autowired
	private RewardDetailService rewardDetailService;
	
	public RewardDetail findById(Integer id) {
		return  rewardDetailService.findById(id);
	}
	
	public List<RewardDetail> findListByAttr(Map<String, Object> map) {
		return rewardDetailService.findListByAttr(map);
	}
	
	public void save(RewardDetail rewardDetail) {
		rewardDetailService.save(rewardDetail);
	}
	
	public MiningActivity add(MiningActivity mnAct,Integer userId) {
		return rewardDetailService.add(mnAct,userId);
	}
	
	public PageResult<RewardDetail> findRewardDetailPage(PaginationQuery query) throws RestServiceException {
		PageResult<RewardDetail> result = rewardDetailService.findPage(query);
		return result;
	}
}
