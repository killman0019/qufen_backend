package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.kff.ExplainActivityService;
import com.tzg.common.service.kff.FollowService;
import com.tzg.entitys.kff.activity.ExplainActivity;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.rmi.service.FollowRmiService;

public class FollowRmiServiceImpl implements FollowRmiService {

	private static final Log logger = LogFactory.getLog(FollowRmiServiceImpl.class);

	@Autowired
	private FollowService followService;

	
	public Follow findById(Integer id) {
		return  followService.findById(id);
	}
	
	public List<Follow> findListByAttr(Map<String, Object> map) {
		return followService.findListByAttr(map);
	}
}
