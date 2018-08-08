package com.tzg.rest.controller.kff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.enums.UserOperation;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.activity.ExplainActivity;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.entitys.kff.activity.PostShare;
import com.tzg.entitys.kff.activity.RewardDetail;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.EvaluationRmiService;
import com.tzg.rmi.service.ExplainActivityRmiService;
import com.tzg.rmi.service.FollowRmiService;
import com.tzg.rmi.service.KFFProjectPostRmiService;
import com.tzg.rmi.service.KFFUserRmiService;
import com.tzg.rmi.service.MiningActivityRmiService;
import com.tzg.rmi.service.PostShareRmiService;
import com.tzg.rmi.service.RewardDetailRmiService;
import com.tzg.rmi.service.SystemParamRmiService;

/** 
* @ClassName: MiningActivityController 
* @Description: TODO<挖矿活动controller> 
* @author linj<作者>
* @date 2018年7月5日 下午1:23:02 
* @version v1.0.0 
*/
@Controller
@RequestMapping("/kff/miningActivity")
public class MiningActivityController extends BaseController {
	private static Logger logger = Logger.getLogger(MiningActivityController.class);
	
	@Autowired
	private MiningActivityRmiService miningActivityRmiService;
	@Autowired
	private ExplainActivityRmiService explainActivityRmiService;
	@Autowired
	private EvaluationRmiService evaluationRmiService;
	@Autowired
	private FollowRmiService followRmiService;
	@Autowired
	private PostShareRmiService postShareRmiService;
	@Autowired
	private RewardDetailRmiService rewardDetailRmiService;
	@Autowired
	private KFFProjectPostRmiService projectPostRmiService;
	@Autowired
	private KFFUserRmiService userRmiService;
	@Autowired
	private SystemParamRmiService systemParamRmiService;
	
	/**
	 * @Description： 随机抽取集合中值
	 * @author: linj
	 * @date: 2017/04/29 17:12:00
	 * @param list
	 */
	private static  Integer getProjectListArr(List<KFFProject> list){
		if(list.isEmpty()){
			return null;
		}
		Random ran = new Random();
		int con=ran.nextInt(list.size());
		return con;
	}
	
	 /**
		 * @Description： 随机抽取集合中值
		 * @author: linj
		 * @date: 2017/04/29 17:12:00
		 * @param list
		 */
	private static Integer getUserListArr(List<KFFUser> list){
		if(list.isEmpty()){
			return null;
		}
		Random ran = new Random();
		int con=ran.nextInt(list.size());
		return con;
	}

	
	
	/** 
	* @Title: getHotProjectAndHotUser 
	* @Description: TODO <发现页面取热门项目，活跃用户>
	* @author linj <方法创建作者>
	* @create 下午7:59:09
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午7:59:09
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/getHotProjectAndHotUser", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity getHotProjectAndHotUser(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String,Object> reMap = new HashMap<String,Object>();
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			SystemParam sysParm = systemParamRmiService.findByCode(sysGlobals.HOT_USER);
			map.clear();
			map.put("hotUser", Integer.valueOf(sysParm.getVcParamValue()));
			List<KFFUser> users = userRmiService.findListByMap(map);
			List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
			if(!users.isEmpty()) {
				if(users.size()<11) {
					for (KFFUser kffUser : users) {
						Map<String,Object> seMap = new HashMap<String,Object>();
						seMap.put("userName", kffUser.getUserName());
						seMap.put("icon", kffUser.getIcon());
						seMap.put("userId", kffUser.getUserId());
						userList.add(seMap);	
					}
				}else {
					List<Integer> usList = new ArrayList<Integer>();
					for (int i = 0; i < 10; i++) {
						boolean flag = false;
						while (!flag) {
							Integer usArr = getUserListArr(users);
							if(!usList.contains(usArr)) {
								usList.add(usArr);
								flag = true;
							}
						}
					}
					for (int i = 0; i < usList.size(); i++) {
						KFFUser kffUser = users.get(usList.get(i));
						Map<String,Object> seMap = new HashMap<String,Object>();
						seMap.put("userName", kffUser.getUserName());
						seMap.put("icon", kffUser.getIcon());
						seMap.put("userId", kffUser.getUserId());
						userList.add(seMap);
					}
				}
			}
			SystemParam sysParmc = systemParamRmiService.findByCode(sysGlobals.HOT_PROJECT);
			map.clear();
			map.put("hotProject", Integer.valueOf(sysParmc.getVcParamValue()));
			List<KFFProject> projects = projectPostRmiService.findListByMap(map);
			List<Map<String,Object>> projectList = new ArrayList<Map<String,Object>>();
			if(!projects.isEmpty()) {
				if(projects.size()<11) {
					for (KFFProject kffProject : projects) {
						Map<String,Object> seMap = new HashMap<String,Object>();
						seMap.put("projectChineseName", kffProject.getProjectChineseName());
						seMap.put("projectIcon", kffProject.getProjectIcon());
						seMap.put("projectId", kffProject.getProjectId());
						projectList.add(seMap);	
					}
				}else {
					List<Integer> usList = new ArrayList<Integer>();
					for (int i = 0; i < 10; i++) {
						boolean flag = false;
						while (!flag) {
							Integer usArr = getProjectListArr(projects);
							if(!usList.contains(usArr)) {
								usList.add(usArr);
								flag = true;
							}
						}
					}
					for (int i = 0; i < usList.size(); i++) {
						KFFProject kffProject = projects.get(usList.get(i));
						Map<String,Object> seMap = new HashMap<String,Object>();
						seMap.put("projectChineseName", kffProject.getProjectChineseName());
						seMap.put("projectIcon", kffProject.getProjectIcon());
						seMap.put("projectId", kffProject.getProjectId());
						projectList.add(seMap);	
					}
				}
			}
			reMap.put("users", userList);
			reMap.put("projects", projectList);
			bre.setData(reMap);
		} catch (RestServiceException e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: getActivityStatus 
	* @Description: TODO <前端页面判断活动是否开始>
	* @author linj <方法创建作者>
	* @create 下午2:39:47
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午2:39:47
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/getActivityStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity getActivityStatus(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			miningActivityRmiService.manualActivity();
		} catch (RestServiceException e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	
	
	/** 
	* @Title: getExplainActivity 
	* @Description: TODO <点评挖矿活动说明接口>
	* @author linj <方法创建作者>
	* @create 下午5:42:57
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午5:42:57
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/getExplainActivity", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity getExplainActivity(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			miningActivityRmiService.manualActivity();
			
			Map<String, Object> seMap = new HashMap<String, Object>();
			seMap.put("status", sysGlobals.ENABLE);
			List<ExplainActivity> explainActivity = explainActivityRmiService.findListByAttr(seMap);
			bre.setData(explainActivity);
		} catch (RestServiceException e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController articleDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	
	/** 
	* @Title: getMiningActivityPageList 
	* @Description: TODO <点评挖矿列表接口>
	* @author linj <方法创建作者>
	* @create 下午7:39:39
	* @param @param request
	* @param @param state 0-进行中（查询进行中，未开始，已挖完），1-已结束（查询终止，已结束）
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午7:39:39
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/getMiningActivityPageList")
	public BaseResponseEntity getMiningActivityPageList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			miningActivityRmiService.manualActivity();
			
			JSONObject policyJson = getParamJsonFromRequestPolicy(request);
			PaginationQuery query = new PaginationQuery();
			Integer state = (Integer)policyJson.get("state");
			if(null==state) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS); 
			}
			if(state==0) {
				query.addQueryData("status1", 0);
				query.addQueryData("status2", 1);
				query.addQueryData("status3", 4);
			}
			if(state==1) {
				query.addQueryData("status4", 2);
				query.addQueryData("status5", 3);
			}
			Integer curPage = (Integer)policyJson.get("pageIndex");
			Integer pageSize = (Integer)policyJson.get("pageSize");
			curPage=curPage==null||curPage<1?1:curPage;
			pageSize=pageSize==null||pageSize<1?15:pageSize;
	        query.setPageIndex(curPage);
	        query.setRowsPerPage(pageSize);
			PageResult<MiningActivity> data = miningActivityRmiService.findMiningActivityPage(query);
            map.put("data", data);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("NewsFlashController getNewsFlashPageList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("NewsFlashController getNewsFlashPageList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: getMiningActivityDetail 
	* @Description: TODO <点评挖矿活动详情页面>
	* @author linj <方法创建作者>
	* @create 下午1:55:20
	* @param @param request
	* @param @param id 活动id
	* @param @param token 用户登录的token
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午1:55:20
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/getMiningActivityDetail")
	public BaseResponseEntity getMiningActivityDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			miningActivityRmiService.manualActivity();
			
			JSONObject policyJson = getParamJsonFromRequestPolicy(request);
			PaginationQuery query = new PaginationQuery();
			Integer id = (Integer)policyJson.get("id");
			if(null==id) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS); 
			}
			String token = (String) policyJson.get("token");
			if(StringUtil.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS); 
			}
			Integer userId = getUserIdByToken(token);
			query.addQueryData("id", id);
			MiningActivity mnAct = miningActivityRmiService.findById(id);
			if(null==mnAct) {
				throw new RestServiceException(RestErrorCode.NO_DATA_MSG); 
			}
			MiningActivity mnActt = arrangeMiningActivity(mnAct, userId);
            map.put("data", mnActt);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("NewsFlashController getNewsFlashPageList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("NewsFlashController getNewsFlashPageList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	
	/** 
	* @Title: arrangeMiningActivity 
	* @Description: TODO <查看在规定时间内用户是否关注了，点评了，分享了项目>
	* @author linj <方法创建作者>
	* @create 下午1:52:54
	* @param @param mnAct
	* @param @param userId
	* @param @return <参数说明>
	* @return MiningActivity 
	* @throws 
	* @update 下午1:52:54
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	public MiningActivity arrangeMiningActivity(MiningActivity mnAct,Integer userId) {
		String startTime = mnAct.getBeginDt();
		String endTime = mnAct.getEndDt();
		String activityStep = mnAct.getActivityStep();
		String[] actp = activityStep.split(",");
		boolean stepOne = false;
		boolean stepTwo = false;
		boolean stepThree = false;
		if(actp.length==3) {
			if(StringUtil.isNotBlank(actp[0])) {
				stepOne = true;
			}
			if(StringUtil.isNotBlank(actp[1])) {
				stepTwo = true;
			}
			if(StringUtil.isNotBlank(actp[2])) {
				stepThree = true;
			}
		}
		//活动步骤：0-关注项目，1-点评项目，2-分享活动
		if(actp.length==2) {
			String actp1 = actp[0];
			String actp2 = actp[1];
			if(actp1.equals("0")) {
				stepOne = true;
			}
			if(actp1.equals("1")) {
				stepTwo = true;
			}
			if(actp2.equals("1")) {
				stepTwo = true;
			}
			if(actp2.equals("2")) {
				stepThree = true;
			}
		}
		if(actp.length==1) {
			String actp1 = actp[0];
			if(actp1.equals("0")) {
				stepOne = true;
			}
			if(actp1.equals("1")) {
				stepTwo = true;
			}
			if(actp1.equals("2")) {
				stepThree = true;
			}
		}
		
		//判断用户是否已经关注了该项目
		Map<String, Object> seMap = new HashMap<String, Object>();
		if(stepOne) {
		    seMap.put("followType", 1);
		    seMap.put("followUserId", userId);
		    seMap.put("followedId", mnAct.getProjectId());
		    seMap.put("status", 1);
			List<Follow> flw = followRmiService.findListByAttr(seMap);
			if(flw.isEmpty()) {
				mnAct.setFollowType(UserOperation.UNOPERATION.getValue());
			}else {
				mnAct.setFollowType(UserOperation.OPERATIONING.getValue());
			}
		}
		//判断用户是否在活动时间内点评过该项目
		if(stepTwo) {
			seMap.clear();
			seMap.put("status", 1);
			seMap.put("projectId", mnAct.getProjectId());
			seMap.put("createUserId", userId);
			seMap.put("startTime", startTime);
			seMap.put("endTime", endTime);
			List<Evaluation> eval = evaluationRmiService.findListByAttr(seMap);
			if(eval.isEmpty()) {
				mnAct.setCommentType(UserOperation.UNOPERATION.getValue());
			}else {
				mnAct.setCommentType(UserOperation.OPERATIONING.getValue());
			}
		}
		//判断在活动时间内用户是否分享过该项目
		if(stepThree) {
			seMap.clear();
			seMap.put("startTime", startTime);
			seMap.put("endTime", endTime);
			seMap.put("userId", userId);
			seMap.put("activityId", mnAct.getId());
			List<PostShare> share = postShareRmiService.findListByAttr(seMap);
			if(share.isEmpty()) {
				mnAct.setShareType(UserOperation.UNOPERATION.getValue());
			}else {
				mnAct.setShareType(UserOperation.OPERATIONING.getValue());
			}
		}
		//判断在活动时间内用户是否领取过该项目的奖励
		seMap.clear();
		seMap.put("activityId", mnAct.getId());
		seMap.put("userId", userId);
		List<RewardDetail> reDet = rewardDetailRmiService.findListByAttr(seMap);
		if(reDet.isEmpty()) {
			mnAct.setReceiveType(UserOperation.UNOPERATION.getValue());
		}else {
			mnAct.setReceiveType(UserOperation.OPERATIONING.getValue());
		}
		return mnAct;
	}
}


