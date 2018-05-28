package com.tzg.common.service.springtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.service.kff.QfIndexService;
import com.tzg.common.service.kff.TokenawardService;


@Service
@Transactional
public class taskJobService {
	
 @Autowired
 private TokenawardService tokenawardService;
 @Autowired
 private QfIndexService qfIndexService;
 	
	  public void updateByGrantType() {  
 		Integer a = 2;
 		  tokenawardService.updateByGrantType(a);
 		  qfIndexService.updateYxPraiseValid();
 		  
	  }  
}
