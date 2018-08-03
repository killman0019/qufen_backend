package com.tzg.rmi.service.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.kff.KFFNewsFlashService;
import com.tzg.entitys.kff.app.NewsFlash;
import com.tzg.entitys.kff.newFlash.KFFNewsFlash;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.NewsFlashRmiService;

public class NewsFlashRmiServiceImpl implements NewsFlashRmiService {

	private static final Log logger = LogFactory.getLog(NewsFlashRmiServiceImpl.class);

	@Autowired
	private KFFNewsFlashService kffNewsFlashService;


	public PageResult<KFFNewsFlash> findNewsFlashPage(PaginationQuery query) throws RestServiceException {
		PageResult<KFFNewsFlash> result = kffNewsFlashService.findPage(query);
		return result;
	}
	
	public PageResult<NewsFlash> findAppNewsFlashPage(PaginationQuery query) throws RestServiceException {
		PageResult<NewsFlash> result = kffNewsFlashService.findAppNewsFlashPage(query);
		return result;
	}
	
	
	public void updateByMap(Map<String,Object> map) throws RestServiceException {
		kffNewsFlashService.updateByMap(map);
	}
	
	public KFFNewsFlash findById(Integer id) {
		return  kffNewsFlashService.findById(id);
	}
	
	public NewsFlash findAppNewsFlashById(Integer id) {
		return  kffNewsFlashService.findAppNewsFlashById(id);
	}
	
}
