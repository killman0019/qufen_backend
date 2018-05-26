package com.tzg.common.service.help;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.base.BaseService;
import com.tzg.entitys.helpdetail.Helpdetail;
import com.tzg.entitys.helpdetail.HelpdetailMapper;

@Service
public class HelpdetailService extends BaseService<Helpdetail, Integer> {

	@Autowired
	private HelpdetailMapper helpdetailMapper;	
	   
	@Autowired
	public void setDao(HelpdetailMapper helpdetailMapper){
		super.setMapper(helpdetailMapper);
	}
	
	public HelpdetailMapper getDao(){
		return (HelpdetailMapper)super.getMapper();
	}

}
