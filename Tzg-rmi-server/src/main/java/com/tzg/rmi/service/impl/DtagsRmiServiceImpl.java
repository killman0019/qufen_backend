package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.kff.DtagsService;
import com.tzg.common.service.kff.KFFNewsFlashService;
import com.tzg.common.service.kff.MiningActivityService;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.entitys.kff.activity.PostShare;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.newFlash.KFFNewsFlash;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.DtagsRmiService;
import com.tzg.rmi.service.MiningActivityRmiService;

public class DtagsRmiServiceImpl implements DtagsRmiService {

	private static final Log logger = LogFactory.getLog(DtagsRmiServiceImpl.class);

	@Autowired
	private DtagsService dTagsService;

	public List<Dtags> findListByAttr(Map<String, Object> map) {
		return dTagsService.findListByAttr(map);
	}
	
	public PageResult<Dtags> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Dtags> result = dTagsService.findPage(query);
		return result;
	}
	
	public void updateByMap(Map<String,Object> map) throws RestServiceException {
		dTagsService.updateByMap(map);
	}
	
	public Dtags findById(Integer id) {
		return  dTagsService.findById(id);
	}
	
}
