package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.kff.KFFNewsFlashService;
import com.tzg.common.service.kff.MiningActivityService;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.entitys.kff.activity.PostShare;
import com.tzg.entitys.kff.newFlash.KFFNewsFlash;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.MiningActivityRmiService;

public class MiningActivityRmiServiceImpl implements MiningActivityRmiService {

	private static final Log logger = LogFactory.getLog(MiningActivityRmiServiceImpl.class);

	@Autowired
	private MiningActivityService miningActivityService;

	public List<MiningActivity> findListByAttr(Map<String, Object> map) {
		return miningActivityService.findListByAttr(map);
	}
	
	public PageResult<MiningActivity> findMiningActivityPage(PaginationQuery query) throws RestServiceException {
		PageResult<MiningActivity> result = miningActivityService.findPage(query);
		return result;
	}
	
	public void updateByMap(Map<String,Object> map) throws RestServiceException {
		miningActivityService.updateByMap(map);
	}
	
	public MiningActivity findById(Integer id) {
		return  miningActivityService.findById(id);
	}
	
	public void manualActivity() {
		miningActivityService.manualActivity();
	}
}
