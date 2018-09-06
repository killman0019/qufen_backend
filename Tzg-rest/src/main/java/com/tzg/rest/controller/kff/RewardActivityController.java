package com.tzg.rest.controller.kff;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.base.BaseRequest;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.SyseUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFUserRmiService;
import com.tzg.rmi.service.RewardActivityRmiService;

@Controller
@RequestMapping("/kff/rewardActivity")
public class RewardActivityController extends BaseController {
	private static Logger logger = Logger.getLogger(RewardActivityController.class);

	@Autowired
	private KFFUserRmiService userRmiService;
	@Autowired
	private RewardActivityRmiService rewardActivityRmiService;
	
	/** 
	* @Title: saveRewardActivity 
	* @Description: TODO <发布悬赏接口>
	* @author linj <方法创建作者>
	* @create 下午5:16:00
	* @param @param baseRequest
	* @param @param rewardDate //悬赏的天数
	* @param @param rewardMoney //悬赏金额
	* @param @param token //用户登陆唯一标识
	* @param @param projectId //项目ID
	* @param @param rewardContents //悬赏内容
	* @param @param postTitle //帖子标题
	* @param @param tagInfos //悬赏标签
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午5:16:00
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/saveRewardActivity", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity saveRewardActivity(BaseRequest baseRequest,HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			JSONObject params = getParamJsonFromRequestPolicy(request);
			Integer rewardDate = params.getInteger("rewardDate");
			String rewardMoney = params.getString("rewardMoney");
			String token = params.getString("token");
			Integer projectId = params.getInteger("projectId");
			String rewardContents = params.getString("rewardContents");
			String postTitle = params.getString("postTitle");
			String tagInfos = params.getString("tagInfos");
			if(null==rewardDate||StringUtil.isBlank(rewardMoney)||StringUtil.isBlank(token)||null==projectId
					||StringUtil.isBlank(rewardContents)||StringUtil.isBlank(postTitle)) {
				bre.setNoRequstData();
				return bre;
			}
			Integer userId = getUserIdByToken(token);
			//判断用户被禁用时，不能发文章
			boolean flag = userRmiService.findUserByStatus(userId);
			if(!flag) {
				bre.setCode(RestErrorCode.ACCOUNT_LOCKED.getValue());
				bre.setMsg(sysGlobals.DISABLE_ACCOUNT_MSG);
				return bre;
			}
			if(rewardDate>15) {
				bre.setCode(RestErrorCode.SYS_ERROR.getValue());
				bre.setMsg("有效期最长是15天");
				return bre;
			}
			ArticleRequest articleRequest = new ArticleRequest();
			articleRequest.setCreateUserId(userId);
			articleRequest.setProjectId(projectId);
			articleRequest.setPostTitle(postTitle);
			articleRequest.setArticleContents(rewardContents);
			articleRequest.setTagInfos(tagInfos);
			rewardActivityRmiService.saveRewardActivity(articleRequest, rewardDate,rewardMoney);
			bre.setSuccessMsg();
		} catch (RestServiceException e) {
			logger.error("RewardActivityController saveRewardActivity:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RewardActivityController saveRewardActivity:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: rewardList 
	* @Description: TODO <悬赏列表接口>
	* @author linj <方法创建作者>
	* @create 下午1:58:55
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午1:58:55
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/rewardList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity rewardList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			JSONObject params = getParamJsonFromRequestPolicy(request);
			Integer pageIndex = params.getInteger("pageIndex");
			Integer pageSize = params.getInteger("pageSize");
			String token = params.getString("token");
			if(null==pageIndex||pageSize==null) {
				bre.setNoRequstData();
				return bre;
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			// 帖子类型：1-评测；2-讨论；3-文章,4-悬赏
			 query.addQueryData("postType", "4");
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			Integer type = 2;// 取关注人
			PageResult<PostResponse> rewards = rewardActivityRmiService.findPageRewardList(userId, query, type);
			if(null!=rewards&&!rewards.getRows().isEmpty()) {
				bre.setData(rewards);
				return bre;
			}
			bre.setNoDataMsg();
		} catch (RestServiceException e) {
			logger.error("RewardActivityController rewardList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RewardActivityController rewardList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: rewardDetail 
	* @Description: TODO <悬赏详情接口>
	* @author linj <方法创建作者>
	* @create 下午7:36:24
	* @param @param request
	* @param @param token 用户登录唯一标识
	* @param @param postId 帖子的id
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午7:36:24
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/rewardDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity rewardDetail(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			JSONObject params = getParamJsonFromRequestPolicy(request);
			String token = (String) params.get("token");
			Integer postId = (Integer) params.get("postId");
			if(null==postId) {
				bre.setNoRequstData();
				return bre;
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			Integer type = 2;// 取关注人
			PostResponse evaluationDetail = rewardActivityRmiService.findRewardDetail(userId, type, postId);
			bre.setData(evaluationDetail);
			SyseUtil.systemErrOutJson(bre);
		} catch (RestServiceException e) {
			logger.error("HomeController evaluationDetail:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("HomeController evaluationDetail:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: stopReward 
	* @Description: TODO <终止悬赏接口>
	* @author linj <方法创建作者>
	* @create 下午4:32:42
	* @param @param request
	* @param @param postId 悬赏的帖子id
	* @param @param token 用户登录唯一标识
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午4:32:42
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/stopReward", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity stopReward(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			JSONObject params = getParamJsonFromRequestPolicy(request);
			Integer postId = params.getInteger("postId");
			String token = params.getString("token");
			if(null==postId||StringUtil.isBlank(token)) {
				bre.setNoRequstData();
				return bre;
			}
			Integer userId = getUserIdByToken(token);
			Map<String,Object> seMap = new HashMap<>();
			seMap.put("postTypec", 4);
			seMap.put("postId", postId);
			seMap.put("createUserId", userId);
			Map<String, Object> reMap = rewardActivityRmiService.findOneByAttr(seMap);
			if(null==reMap||reMap.isEmpty()) {
				bre.setNoDataMsg();
				return bre;
			}
			Integer id = Integer.valueOf(reMap.get("id").toString());
			String beginTime = reMap.get("beginTime").toString();
			boolean flag = DateUtil.biTimeCount(beginTime);
			if(flag) {
				rewardActivityRmiService.updateRewardActivityAndPost(postId,id);
				bre.setSuccessMsg();
			}else {
				bre.setNoDataMsg();
			}
		} catch (RestServiceException e) {
			logger.error("RewardActivityController rewardList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RewardActivityController rewardList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
}
