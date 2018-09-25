package com.tzg.wap.controller.h5;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.tzg.common.enums.RewardActivityState;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.HtmlUtils;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.SyseUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.activity.RewardActivityVo;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.CoinPropertyRmiService;
import com.tzg.rmi.service.KFFUserRmiService;
import com.tzg.rmi.service.RewardActivityRmiService;

@Controller
@RequestMapping("/H5/rewardActivity")
public class RewardActivityController extends BaseController {
	private static Logger logger = Logger.getLogger(RewardActivityController.class);

	@Autowired
	private KFFUserRmiService userRmiService;
	@Autowired
	private RewardActivityRmiService rewardActivityRmiService;
	@Autowired
	private CoinPropertyRmiService coinPropertyRmiService;
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
	public BaseResponseEntity saveRewardActivity(BaseRequest baseRequest,HttpServletRequest request,Integer rewardDate,
			String rewardMoney,String token,Integer projectId,String rewardContents,String postTitle,String tagInfos,
			String discussImages) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(rewardDate==null&&StringUtil.isBlank(rewardMoney)&&StringUtil.isBlank(token)&&projectId==null
					&&StringUtil.isBlank(rewardContents)&&StringUtil.isBlank(postTitle)&&StringUtil.isBlank(tagInfos)) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				rewardDate = (Integer) requestContent.get("rewardDate");
				rewardMoney = (String) requestContent.get("rewardMoney");
				token = (String) requestContent.get("token");
				projectId = (Integer) requestContent.get("projectId");
				rewardContents = (String) requestContent.get("rewardContents");
				postTitle = (String) requestContent.get("postTitle");
				tagInfos = (String) requestContent.get("tagInfos");
				discussImages = (String) requestContent.get("discussImages");
			}
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
			//校验用户可用的token是否够
			Map<String,Object> seMap = new HashMap<>();
			seMap.put("userId", userId);
			List<CoinProperty> coinPr = coinPropertyRmiService.findListByAttr(seMap);
			if(coinPr.isEmpty()) {
				bre.setCode(RestErrorCode.SYS_ERROR.getValue());
				bre.setMsg("该用户没有对应的token记录");
				return bre;
			}
			CoinProperty coinProty = coinPr.get(0);
			BigDecimal nowRewardMoney = StringUtil.toBeBigDecimal(rewardMoney);
			BigDecimal coinLock = StringUtil.toBeBigDecimal(coinProty.getCoinLock().toString());
			BigDecimal senCount = StringUtil.subBigDecimal(coinLock, nowRewardMoney);
			BigDecimal num = new BigDecimal("0");
			if(senCount.compareTo(num)<0) {
				bre.setCode(RestErrorCode.SYS_ERROR.getValue());
				bre.setMsg("该用户没有足够的token币");
				return bre;
			}
			//判断法的token币是否大于1000
			BigDecimal tk = new BigDecimal("1000");
			if(nowRewardMoney.compareTo(tk)<0) {
				bre.setCode(RestErrorCode.SYS_ERROR.getValue());
				bre.setMsg("发布悬赏必须要1000个token币以上");
				return bre;
			}
			ArticleRequest articleRequest = new ArticleRequest();
			articleRequest.setCreateUserId(userId);
			articleRequest.setProjectId(projectId);
			articleRequest.setPostTitle(postTitle);
			articleRequest.setArticleContents(rewardContents);
			articleRequest.setTagInfos(tagInfos);
			articleRequest.setPostSmallImages(discussImages);
			rewardActivityRmiService.saveRewardActivity(articleRequest, rewardDate,nowRewardMoney,coinProty);
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
	public BaseResponseEntity rewardList(HttpServletRequest request,Integer pageIndex,Integer pageSize,
			String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(pageIndex==null&&StringUtil.isBlank(token)&&pageSize==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				pageIndex = (Integer) requestContent.get("pageIndex");
				token = (String) requestContent.get("token");
				pageSize = (Integer) requestContent.get("pageSize");
			}
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
	public BaseResponseEntity rewardDetail(HttpServletRequest request,String token,Integer postId) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(postId==null&&StringUtil.isBlank(token)) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				postId = (Integer) requestContent.get("postId");
				token = (String) requestContent.get("token");
			}
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
	public BaseResponseEntity stopReward(HttpServletRequest request,Integer postId,String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(postId==null&&StringUtil.isBlank(token)) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				postId = (Integer) requestContent.get("postId");
				token = (String) requestContent.get("token");
			}
			if(null==postId||StringUtil.isBlank(token)) {
				bre.setNoRequstData();
				return bre;
			}
			Integer userId = getUserIdByToken(token);
			Map<String,Object> seMap = new HashMap<>();
			//判断这个悬赏有没有被终止
			seMap.put("postId", postId);
			seMap.put("state", RewardActivityState.STARTING.getValue());
			Map<String, Object> reMapc = rewardActivityRmiService.findOneByAttr(seMap);
			if(reMapc.isEmpty()) {
				bre.setCode(500);
				bre.setMsg("该悬赏未在进行中，无法终止");
				return bre;
			}
			//终止的条件是1小时内没有回答的记录
			seMap.clear();
			seMap.put("postId", postId);
			seMap.put("state", RewardActivityState.STARTING.getValue());
			Integer reActCount = rewardActivityRmiService.findLinkedCount(seMap);
			if(reActCount>0) {
				bre.setCode(500);
				bre.setMsg("该悬赏已有用户回答，无法终止");
				return bre;
			}
			seMap.clear();
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
				bre.setCode(500);
				bre.setMsg("终止失败");
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
	
	/** 
	* @Title: getRewardAnswer 
	* @Description: TODO <悬赏精彩，全部回答接口>
	* @author linj <方法创建作者>
	* @create 上午11:35:18
	* @param @param request
	* @param @param rewarId //悬赏id
	* @param @param types //回答类型：1-精彩回答，2-全部回答
	* @param @param token //用户登录唯一标识
	* @param @param pageIndex //第几页
	* @param @param pageSize //当前页有几条
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午11:35:18
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/getRewardAnswerList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity getRewardAnswerList(HttpServletRequest request,Integer rewarId,Integer types,
			String token,Integer pageIndex,Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(rewarId==null&&StringUtil.isBlank(token)&&types==null&&pageIndex==null&&pageSize==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				rewarId = (Integer) requestContent.get("rewarId");
				token = (String) requestContent.get("token");
				types = (Integer) requestContent.get("types");
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
			}
			if(null==rewarId||null==types||null==pageSize||null==pageIndex) {
				bre.setNoRequstData();
				return bre;
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("rewardActivityId", rewarId);
			if(types==1) {
				query.addQueryData("sort", "tpt.praise_num");
			}
			if(types==2) {
				query.addQueryData("sort", "tpt.createTime");
			}
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			Integer type = 2;// 取关注人
			PageResult<PostResponse> answers = rewardActivityRmiService.findRewardAnswerList(userId, query, type);
			if(null!=answers&&!answers.getRows().isEmpty()) {
				bre.setData(answers);
				return bre;
			}
			bre.setNoDataMsg();
		} catch (RestServiceException e) {
			logger.error("RewardActivityController getRewardAnswer:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RewardActivityController getRewardAnswer:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: getRewardActivityList 
	* @Description: TODO <尽调挖矿接口>
	* @author linj <方法创建作者>
	* @create 下午4:59:48
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午4:59:48
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/getRewardActivityList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity getRewardActivityList(HttpServletRequest request,Integer pageIndex,Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(pageIndex==null&&pageSize==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
			}
			if(null==pageSize||null==pageIndex) {
				bre.setNoRequstData();
				return bre;
			}
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("sort", "rac.updated_at");
			query.addQueryData("state", RewardActivityState.STARTING.getValue());
			query.addQueryData("isActivity", sysGlobals.ENABLE);
			query.addQueryData("endTime", new Date());
			query.addQueryData("status", "1");
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<RewardActivityVo> data = rewardActivityRmiService.getRewardActivityList(query);
			if(null!=data&&!data.getRows().isEmpty()) {
				bre.setData(data);
				return bre;
			}
			bre.setNoDataMsg();
		} catch (RestServiceException e) {
			logger.error("RewardActivityController getRewardAnswer:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RewardActivityController getRewardAnswer:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: rewardSquareList 
	* @Description: TODO <悬赏广场接口>
	* @author linj <方法创建作者>
	* @create 下午1:56:04
	* @param @param request
	* @param @param pageIndex 第几页
	* @param @param pageSize 每页几条
	* @param @param typec 1-最新悬赏，2-高额悬赏，3-精彩回复
	* @param @param token 当前用户登录唯一标识
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午1:56:04
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/rewardSquareList", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity rewardSquareList(HttpServletRequest request,Integer pageIndex,Integer pageSize,
			Integer typec,String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(pageIndex==null&&pageSize==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
				typec = (Integer) requestContent.get("typec");
				token = (String) requestContent.get("token");
			}
			if(null==pageIndex||pageSize==null||typec==null) {
				bre.setNoRequstData();
				return bre;
			}
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			PaginationQuery query = new PaginationQuery();
			// 帖子类型：1-评测；2-讨论；3-文章,4-悬赏
			query.addQueryData("statec", 2);//不是撤销的悬赏都需要显示出来
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			Integer type = 2;// 取关注人
			if(typec==1 || typec==2) {
				query.addQueryData("postTypec", "4");
				if(typec==1) {
					query.addQueryData("sort", "rac.created_at");
				}else if(typec==2) {
					query.addQueryData("status", 1);
					query.addQueryData("sort", "rac.reward_money");
				}
				PageResult<PostResponse> rewards = rewardActivityRmiService.findPageForNewAndHighList(userId, query, type);
				if(null!=rewards&&!rewards.getRows().isEmpty()) {
					bre.setData(rewards);
					return bre;
				}
			}
			if(typec==3) {
				query.addQueryData("postTypec", "2");
				query.addQueryData("status", 1);
				query.addQueryData("sort", "tpt.stick_updateTime");
				query.addQueryData("stickTopc", 1);
				query.addQueryData("linkedOne", "1");
				PageResult<PostResponse> rewards = rewardActivityRmiService.findPageForBurstList(userId, query, type);
				if(null!=rewards&&!rewards.getRows().isEmpty()) {
					bre.setData(rewards);
					return bre;
				}
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
}
