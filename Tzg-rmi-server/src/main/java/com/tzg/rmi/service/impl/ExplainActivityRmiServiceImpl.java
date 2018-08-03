package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.kff.ExplainActivityService;
import com.tzg.entitys.kff.activity.ExplainActivity;
import com.tzg.rmi.service.ExplainActivityRmiService;

public class ExplainActivityRmiServiceImpl implements ExplainActivityRmiService {

	private static final Log logger = LogFactory.getLog(ExplainActivityRmiServiceImpl.class);

	@Autowired
	private ExplainActivityService explainActivityService;

	
	public ExplainActivity findById(Integer id) {
		return  explainActivityService.findById(id);
	}
	
	public List<ExplainActivity> findListByAttr(Map<String, Object> map) {
		return explainActivityService.findListByAttr(map);
	}
}
