package com.tzg.rmi.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.kff.KFFNewsFlashImgService;
import com.tzg.common.service.kff.KFFNewsFlashService;
import com.tzg.entitys.kff.newFlash.KFFNewsFlash;
import com.tzg.entitys.kff.newsFlashImg.KFFNewsFlashImg;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.NewsFlashImgRmiService;

public class NewsFlashImgRmiServiceImpl implements NewsFlashImgRmiService {

	private static final Log logger = LogFactory.getLog(NewsFlashImgRmiServiceImpl.class);

	@Autowired
	private KFFNewsFlashImgService kffNewsFlashImgService;


	public PageResult<KFFNewsFlashImg> findNewsFlashImgPage(PaginationQuery query) throws RestServiceException {
		PageResult<KFFNewsFlashImg> result = kffNewsFlashImgService.findPage(query);
		return result;
	}
	
	
}
