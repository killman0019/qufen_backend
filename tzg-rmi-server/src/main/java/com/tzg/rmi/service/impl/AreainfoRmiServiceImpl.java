package com.tzg.rmi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.areainfo.AreainfoService;
import com.tzg.entitys.areainfo.Areainfo;
import com.tzg.rmi.service.AreainfoRmiService;

public class AreainfoRmiServiceImpl implements AreainfoRmiService {
	
	@Autowired
	private AreainfoService areainfoService;

	@Override
	public List<Areainfo> findFirstLevelList() throws Exception {
		return areainfoService.queryFirstLevelAll();
	}
	
	@Override
	public List<Areainfo> findByParentCode(String parentCode) throws Exception {
		return areainfoService.queryInfoByiFatherID(parentCode);
	}

	@Override
	public Areainfo findByCode(String code) throws Exception {
		return areainfoService.queryInfoByCode(code);
	}


}
