package com.tzg.common.service.springtask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.service.kff.ProjectService;
import com.tzg.common.service.kff.QfIndexService;
import com.tzg.common.service.kff.TokenawardService;


@Service
@Transactional
public class taskJobService {
	private static final Log logger = LogFactory.getLog(taskJobService.class);

 @Autowired
 private TokenawardService tokenawardService;
 @Autowired
 private QfIndexService qfIndexService;
 
 @Autowired
 private ProjectService projectService;
 	
	  public void updateByGrantType() {  
 		Integer a = 2;
 		  tokenawardService.updateByGrantType(a);
 		  System.err.println("执行了第一个定时任务");
 		
 		 
	  }  
	  public void updateByGrantType2() {  
		   qfIndexService.updateYxPraiseValid();
		    System.err.println("执行了第二个定时任务");
		  
	  }  
	  
	  
	  //更新项目总分
	  public void caculateProjectTotalScore(){
		  logger.info("----start caculateProjectTotalScore job---"); 
		  projectService.caculateProjectTotalScore();
		  logger.info("----end caculateProjectTotalScore job---"); 

	  }
}
