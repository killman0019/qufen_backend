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
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.StringUtil;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.entitys.kff.activity.RewardDetail;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.utils.activity.ActivityUtils;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.MiningActivityRmiService;
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


