package com.tzg.common.service.help;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.base.BaseService;
import com.tzg.entitys.helpcategy.Helpcategy;
import com.tzg.entitys.helpcategy.HelpcategyMapper;
@Service
public class HelpcategyService extends BaseService<Helpcategy, Integer> {

	@Autowired
	private HelpcategyMapper helpcategyMapper;	
	   
	@Autowired
	public void setDao(HelpcategyMapper helpcategyMapper){
		super.setMapper(helpcategyMapper);
	}
	
	public HelpcategyMapper getDao(){
		return (HelpcategyMapper)super.getMapper();
	}

	
}
