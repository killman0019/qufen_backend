package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.kff.PostShareService;
import com.tzg.entitys.kff.activity.PostShare;
import com.tzg.rmi.service.PostShareRmiService;

public class PostShareRmiServiceImpl implements PostShareRmiService {

	private static final Log logger = LogFactory.getLog(PostShareRmiServiceImpl.class);

	@Autowired
	private PostShareService postShareService;

	
	public PostShare findById(Integer id) {
		return  postShareService.findById(id);
	}
	
	public List<PostShare> findListByAttr(Map<String, Object> map) {
		return postShareService.findListByAttr(map);
	}
	
	public void save(PostShare postShare) {
		postShareService.save(postShare);
	}
}
