package com.tzg.rest.controller.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.enums.MiningActivityStatus;
import com.tzg.common.enums.UserOperation;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.StringUtil;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.entitys.kff.activity.PostShare;
import com.tzg.entitys.kff.activity.RewardDetail;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.utils.activity.ActivityUtils;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.EvaluationRmiService;
import com.tzg.rmi.service.FollowRmiService;
import com.tzg.rmi.service.MiningActivityRmiService;
import com.tzg.rmi.service.PostShareRmiService;
import com.tzg.rmi.service.RewardDetailRmiService;

/** 
* @ClassName: RewardDetailController 
* @Description: TODO<活动奖励接口> 
* @author linj<作者>
* @date 2018年7月5日 下午1:23:02 
* @version v1.0.0 
*/
@Controller
@RequestMapping("/kff/rewardDetail")
public class RewardDetailController extends BaseController {
	private static Logger logger = Logger.getLogger(RewardDetailController.class);
	
	@Autowired
	private MiningActivityRmiService miningActivityRmiService;
	@Autowired
	private RewardDetailRmiService rewardDetailRmiService;
	
	@Autowired
	private FollowRmiService followRmiService;
	
	@Autowired
	private PostShareRmiService postShareRmiService;
	@Autowired
	private EvaluationRmiService evaluationRmiService;
	
	
	
	
	/** 
	* @Title: addRewardDetail 
	* @Description: TODO <领取活动奖励接口>
	* @author linj <方法创建作者>
	* @create 下午3:12:48
	* @param @param request
	* @param @param token 用户登录的token
	* @param @param activityId 活动id
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午3:12:48
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/addRewardDetail")
	public BaseResponseEntity addRewardDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			miningActivityRmiService.manualActivity();
			
			JSONObject policyJson = getParamJsonFromRequestPolicy(request);
			Integer id = (Integer)policyJson.get("activityId");
			if(null==id) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS); 
			}
			String token = (String) policyJson.get("token");
			if(StringUtil.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS); 
			}
			Integer userId = getUserIdByToken(token);
			Map<String,Object> seMap = new HashMap<String,Object>();
			seMap.put("activityId", id);
			seMap.put("userId", userId);
			List<RewardDetail> rewDet = rewardDetailRmiService.findListByAttr(seMap);
			if(!rewDet.isEmpty()) {
				bre.setCode(500);
				bre.setMsg("您已领取了该活动的奖励！");
				return bre;
			}
			//判断活动份数是否已为0
			MiningActivity mnAct = miningActivityRmiService.findById(id);
			if(!mnAct.getStatus().equals(MiningActivityStatus.STARTING.getValue())) {
				bre.setCode(500);
				bre.setMsg("该活动状态不再进行中！");
				return bre;
			}
			MiningActivity mnActtc = arrangeMiningActivity(mnAct, userId);
			Integer followType = mnActtc.getFollowType();
			Integer commentType = mnActtc.getCommentType();
			Integer receiveType = mnActtc.getReceiveType();
			Integer shareType = mnActtc.getShareType();
			if(null!=followType&&0==followType) {
				bre.setCode(500);
				bre.setMsg("请先关注再领取奖励！");
				return bre;
			}
			if(null!=commentType&&0==commentType) {
				bre.setCode(500);
				bre.setMsg("请先点评再领取奖励！");
				return bre;
			}
			if(null!=shareType&&0==shareType) {
				bre.setCode(500);
				bre.setMsg("请先分享再领取奖励！");
				return bre;
			}
			if(null!=receiveType&&1==receiveType) {
				bre.setCode(500);
				bre.setMsg("您已经领取过奖励了！");
				return bre;
			}
			Map<String, Object> reMap = ActivityUtils.checkActivityStatus(mnAct);
			Integer codec = (Integer) reMap.get("code");
			if(codec!=200) {
				bre.setCode(codec);
				bre.setMsg(reMap.get("msg").toString());
				return bre;
			}
			rewardDetailRmiService.add(mnAct,userId);
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
	
	/** 
	* @Title: getUserRewardList 
	* @Description: TODO <挖矿收益列表接口>
	* @author linj <方法创建作者>
	* @create 下午6:51:44
	* @param @param request
	* @param @param token 用户登录的token
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午6:51:44
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/getUserRewardList")
	public BaseResponseEntity getUserRewardList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			miningActivityRmiService.manualActivity();
			
			JSONObject policyJson = getParamJsonFromRequestPolicy(request);
			String token = (String) policyJson.get("token");
			if(StringUtil.isBlank(token)) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS); 
			}
			Integer userId = getUserIdByToken(token);
			PaginationQuery query = new PaginationQuery();
			Integer curPage = (Integer)policyJson.get("pageIndex");
			Integer pageSize = (Integer)policyJson.get("pageSize");
			curPage=curPage==null||curPage<1?1:curPage;
			pageSize=pageSize==null||pageSize<1?15:pageSize;
	        query.setPageIndex(curPage);
	        query.setRowsPerPage(pageSize);
	        query.addQueryData("userId", userId);
	        PageResult<RewardDetail> data = rewardDetailRmiService.findRewardDetailPage(query);
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
	
}


