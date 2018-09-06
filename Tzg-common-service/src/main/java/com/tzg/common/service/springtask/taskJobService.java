package com.tzg.common.service.springtask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jpush.api.report.UsersResult.User;

import com.tzg.common.service.kff.PostService;
import com.tzg.common.service.kff.ProjectService;
import com.tzg.common.service.kff.ProjectTradeService;
import com.tzg.common.service.kff.QfIndexService;
import com.tzg.common.service.kff.RobotService;
import com.tzg.common.service.kff.TokenawardService;
import com.tzg.common.service.kff.TokenrecordsService;
import com.tzg.common.service.kff.TransactionPairService;
import com.tzg.common.service.kff.UserService;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.sysGlobals;

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

	@Autowired
	private PostService postService;

	@Autowired
	private ProjectTradeService projectTradeService;

	@Autowired
	private TransactionPairService transactionPairService;
	@Autowired
	private TokenrecordsService tokenrecordsService;

	@Autowired
	private UserService userService;

	@Autowired
	private SystemParamService systemParamService;

	@Autowired
	private RobotService robotService;

	/**
	 * 
	* @Title: putRedis 
	* @Description: TODO <初始参数>
	* @author zhangdd <方法创建作者>
	* @create 上午10:57:16
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 上午10:57:16
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void putRedis() {
		logger.info("----start putRedis job---");
		robotService.putRedis();
		logger.info("----end putRedis job---");
	}

	/**
	 * 
	* @Title: robotComment 
	* @Description: TODO <一级评论>
	* @author zhangdd <方法创建作者>
	* @create 下午8:03:56
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 下午8:03:56
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void robotComment() {
		logger.info("----start robotComment job---");
		robotService.robotTask(5);
		logger.info("----end robotComment job---");
	}

	/**
	 * 
	* @Title: robotFollow 
	* @Description: TODO <用一句话描述这个方法的作用>
	* @author zhangdd <方法创建作者>
	* @create 下午8:05:08
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 下午8:05:08
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void robotFollow() {
		logger.info("----start robotFollow job---");
		robotService.robotTask(4);
		logger.info("----end robotFollow job---");
	}

	/**
	 * 
	* @Title: robotCommendation 
	* @Description: TODO <用一句话描述这个方法的作用>
	* @author zhangdd <方法创建作者>
	* @create 下午8:05:13
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 下午8:05:13
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void robotCommendation() {
		logger.info("----start robotCommendation job---");
		robotService.robotTask(3);
		logger.info("----end robotCommendation job---");
	}

	/**
	 * 
	* @Title: robotSecondComment 
	* @Description: TODO <用一句话描述这个方法的作用>
	* @author zhangdd <方法创建作者>
	* @create 下午8:05:17
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 下午8:05:17
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void robotSecondComment() {
		logger.info("----start robotSecondComment job---");
		robotService.robotTask(2);
		logger.info("----end robotSecondComment job---");
	}

	/**
	 * 
	* @Title: robotPraise 
	* @Description: TODO <用一句话描述这个方法的作用>
	* @author zhangdd <方法创建作者>
	* @create 下午8:05:20
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 下午8:05:20
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void robotPraise() {
		logger.info("----start robotPraise job---");
		robotService.robotTask(1);
		logger.info("----end robotPraise job---");
	}

	/**
	 * 
	* @Title: updateWeeHours 
	* @Description: TODO <凌晨定时任务>
	* @author zhangdd <方法创建作者>
	* @create 下午7:43:31
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 下午7:43:31
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void updateWeeHours() {
		logger.info("----start caculateProjectTotalScore job---");
		Integer a = 2;
		tokenawardService.updateByGrantType(a);// 更新分期发放奖励
		logger.info("-------------- end caculatePostTatolIncome job---");

		logger.info("----start updateYxPraiseAndYxCommentionYxShare job---");
		qfIndexService.updateYxPraiseValid();// 更新有效赞有效分享 有效评论
		logger.info("--------- end updateYxPraiseAndYxCommentionYxShare job---");

		logger.info("--------------start updatePop job---");
		userService.setPop();// 更新弹框
		logger.info("-------------- end updatePop job---");
		systemParamService.updateTime(sysGlobals.TASK_TOKEN_AWARD);
	}

	/*public void updateByGrantType() {
		Integer a = 2;
		tokenawardService.updateByGrantType(a);
		System.err.println("执行了第一个定时任务");

	}

	public void updateByGrantType2() {
		qfIndexService.updateYxPraiseValid();
		System.err.println("执行了第二个定时任务");

	}
	*/
	// 更新项目总分
	public void caculateProjectTotalScore() {
		logger.info("----start caculateProjectTotalScore job---");
		projectService.caculateProjectTotalScore();
		logger.info("----end caculateProjectTotalScore job---");

	}

	/**
	 * 计算post的收益
	 */
	public void caculatePostTatolIncome() {
		logger.info("--------------start caculatePostTatolIncome job---");
		postService.caculatePostTatolIncome();
		logger.info("-------------- end caculatePostTatolIncome job---");
	}

	/**
	 * 
	 * TODO 获得项目详情
	 * @author zhangdd
	 * @data 2018年8月6日
	 *
	 */
	public void getProjectDateFromUrlTask() {
		logger.info("--------------start getProjectDateFromUrlTask job---");
		projectTradeService.getProjectDateFromUrlTask();
		logger.info("-------------- end getProjectDateFromUrlTask job---");
	}

	/**
	 * 
	 * TODO 获得交易对信息
	 * @author zhangdd
	 * @data 2018年8月6日
	 *
	 */
	public void getdatafromUrlByexchangeTask() {
		logger.info("--------------start getdatafromUrlByexchangeTask job---");
		transactionPairService.getdatafromUrlByexchangeTask();
		logger.info("-------------- end getdatafromUrlByexchangeTask job---");
	}

	public void caluCoinUserTask() {
		logger.info("--------------start getdatafromUrlByexchangeTask job---");
		tokenrecordsService.caluCoinUserTask();
		logger.info("-------------- end getdatafromUrlByexchangeTask job---");

	}

}
