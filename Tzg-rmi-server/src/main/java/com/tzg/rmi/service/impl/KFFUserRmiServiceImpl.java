package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.kff.UserService;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rmi.service.KFFUserRmiService;

public class KFFUserRmiServiceImpl implements KFFUserRmiService {

	private static final Log logger = LogFactory.getLog(KFFUserRmiServiceImpl.class);

	@Autowired
	private UserService userService;

	
	public KFFUser findById(Integer id){
		return  userService.findById(id);
	}
	
	public List<KFFUser> findListByAttr(Map<String, Object> map) {
		return userService.findListByAttr(map);
	}
	
	public List<KFFUser> findListByMap(Map<String, Object> map) {
		return userService.findListByMap(map);
	}
}
