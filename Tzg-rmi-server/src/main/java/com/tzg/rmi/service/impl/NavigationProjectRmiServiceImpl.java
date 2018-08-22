package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.kff.NavigationProjectService;
import com.tzg.entitys.kff.content.NavigationProject;
import com.tzg.rmi.service.NavigationProjectRmiService;

public class NavigationProjectRmiServiceImpl implements NavigationProjectRmiService {

	private static final Log logger = LogFactory.getLog(NavigationProjectRmiServiceImpl.class);

	@Autowired
	private NavigationProjectService navigationProjectService;

	
	public NavigationProject findById(Integer id) {
		return  navigationProjectService.findById(id);
	}
	
	public List<NavigationProject> findListByAttr(Map<String, Object> map) {
		return navigationProjectService.findListByAttr(map);
	}
}
