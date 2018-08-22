package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.kff.NavigationService;
import com.tzg.entitys.kff.content.Navigation;
import com.tzg.rmi.service.NavigationRmiService;

public class NavigationRmiServiceImpl implements NavigationRmiService {

	private static final Log logger = LogFactory.getLog(NavigationRmiServiceImpl.class);

	@Autowired
	private NavigationService navigationService;

	
	public Navigation findById(Integer id) {
		return  navigationService.findById(id);
	}
	
	public List<Navigation> findListByAttr(Map<String, Object> map) {
		return navigationService.findListByAttr(map);
	}
}
