package com.tzg.rmi.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.tzg.common.constants.KFFConstants;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.service.kff.ArticleService;
import com.tzg.common.service.kff.CollectService;
import com.tzg.common.service.kff.CommendationService;
import com.tzg.common.service.kff.CommentsService;
import com.tzg.common.service.kff.DareasService;
import com.tzg.common.service.kff.DevaluationModelService;
import com.tzg.common.service.kff.DiscussService;
import com.tzg.common.service.kff.DprojectTypeService;
import com.tzg.common.service.kff.DtagsService;
import com.tzg.common.service.kff.EvaluationService;
import com.tzg.common.service.kff.FollowService;
import com.tzg.common.service.kff.MessageService;
import com.tzg.common.service.kff.MobileversionupdateService;
import com.tzg.common.service.kff.NoticeService;
import com.tzg.common.service.kff.PostService;
import com.tzg.common.service.kff.PraiseService;
import com.tzg.common.service.kff.ProjectService;
import com.tzg.common.service.kff.SuggestService;
import com.tzg.common.service.kff.TokenrecordsService;
import com.tzg.common.service.kff.UserService;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.Numbers;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.zookeeper.ZKClient;
import com.tzg.entitys.kff.article.Article;
import com.tzg.entitys.kff.article.ArticleDetailResponse;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.collect.Collect;
import com.tzg.entitys.kff.collect.CollectPostResponse;
import com.tzg.entitys.kff.commendation.Commendation;
import com.tzg.entitys.kff.commendation.CommendationRequest;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.entitys.kff.dareas.Dareas;
import com.tzg.entitys.kff.devaluationModel.DevaluationModel;
import com.tzg.entitys.kff.devaluationModel.DevaluationModelRequest;
import com.tzg.entitys.kff.discuss.Discuss;
import com.tzg.entitys.kff.discuss.DiscussRequest;
import com.tzg.entitys.kff.dprojectType.DprojectType;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.follow.FollowResponse;
import com.tzg.entitys.kff.message.KFFMessage;
import com.tzg.entitys.kff.mobileversionupdate.Mobileversionupdate;
import com.tzg.entitys.kff.notice.KFFNotice;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostFile;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.praise.Praise;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.project.SubmitKFFProjectRequest;
import com.tzg.entitys.kff.suggest.Suggest;
import com.tzg.entitys.kff.suggest.SuggestRequest;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserHomeResponse;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFRmiService;
public class KFFRmiServiceImpl implements KFFRmiService {
	
	private static final Log logger = LogFactory.getLog(KFFRmiServiceImpl.class);

	@Autowired
	private ArticleService kffArticleService;
	@Autowired
	private CollectService kffCollectService;
	@Autowired
	private CommentsService kffCommentsService;
	@Autowired
	private DiscussService kffDiscussService;
	@Autowired
	private EvaluationService kffEvaluationService;
	@Autowired
	private DevaluationModelService kffDevaluationModelService;
	@Autowired
	private FollowService kffFollowService;
	@Autowired
	private PostService kffPostService;
	@Autowired
	private MessageService kffMessageService;
	@Autowired
	private MobileversionupdateService kffMobileversionupdateService;
	@Autowired
	private PraiseService kffPraiseService;
	@Autowired
	private ProjectService kffProjectService;
	@Autowired
	private TokenrecordsService kffTokenrecordsService;
	@Autowired
	private UserService kffUserService;
	@Autowired
	private NoticeService kffNoticeService;
	@Autowired
	private SuggestService kffSuggestService;
	@Autowired
	private DareasService kffDareasService;
	@Autowired
	private DtagsService kffDtagsService;
	@Autowired
	private DprojectTypeService kffDprojectTypeService;
	@Autowired
	private CommendationService kffCommendationService;


    @Autowired
    private ZKClient zkClient;
    @Autowired
    private SystemParamService systemParamService;
    @Autowired
    private RedisService redisService;

	@Override
	public KFFUser registerRest(RegisterRequest registerRequest) {
		 return kffUserService.registerRest(registerRequest);
	}

	@Override
	public boolean verifyLoginaccount(String key, String value) {
		return kffUserService.verifyLoginaccount(key,value);
		
	}

	@Override
	public KFFUser login(String loginName, String password) {
		
		return kffUserService.login(loginName,password);
	}

	@Override
	public Mobileversionupdate selectLastVersionByType(Integer platformType)
			throws RestServiceException {		
		return kffMobileversionupdateService.findById(platformType);
	}

	@Override
	public void submitSuggest(SuggestRequest suggestRequest)
			throws RestServiceException {
		if(StringUtils.isBlank(suggestRequest.getContent())){
			throw new RestServiceException("请填写建议内容");
		}else if(suggestRequest.getContent().length() > KFFConstants.MAX_NORMAL_CONTENT_LENGTH){
			throw new RestServiceException("建议内容请勿超过"+KFFConstants.MAX_NORMAL_CONTENT_LENGTH+"字");
		}
		
		if(StringUtils.isBlank(suggestRequest.getContactInfo())){
			throw new RestServiceException("请填写联系方式");
		}else if(suggestRequest.getContactInfo().length() > KFFConstants.MAX_NORMAL_TITLE_LENGTH){
			throw new RestServiceException("联系方式请勿超过"+KFFConstants.MAX_NORMAL_TITLE_LENGTH +"字");
		}
		
		Suggest suggest = new Suggest();
		Date now = new Date();
		suggest.setContactInfo(suggestRequest.getContactInfo());
		suggest.setContent(suggestRequest.getContent());
		suggest.setCreateUserId(suggestRequest.getCreateUserId());
		suggest.setCreateTime(now);
		suggest.setUpdateTime(now);
		suggest.setStatus(1);
		kffSuggestService.save(suggest);
		
	}

	@Override
	public KFFNotice selectLatestNotice() throws RestServiceException {

		return kffNoticeService.selectLatestNotice();
	}
	
	@Override
	public KFFUser findUserByPhoneNumber(String phoneNumber) throws RestServiceException{
		
		return kffUserService.findUserByPhoneNumber(phoneNumber);
	}
	
	@Override
	public boolean updateUser(KFFUser account) throws RestServiceException{
		
		return kffUserService.update(account);
	}
	
	@Override
	public KFFUser findUserById(Integer userId) throws RestServiceException{		
		return kffUserService.findById(userId);
	}

	@Override
	public List<Dareas> getAreaListByCode(String areacode)
			throws RestServiceException {
		List<Dareas> areas = new ArrayList<>();
        if(StringUtils.isBlank(areacode)) {
        	areas = kffDareasService.findProvinceAreas();
        }else {
        	areas = kffDareasService.findSubAreasByCode(areacode);
        }
		return areas;
	}

	@Override
	public PageResult<Tokenrecords> findPageMyTokenRecords(PaginationQuery query)
			throws RestServiceException {
		
		return kffTokenrecordsService.findPage(query);
	}

	@Override
	public PageResult<CollectPostResponse> findPageMyCollectRecords(PaginationQuery query) throws RestServiceException {
		PageResult<CollectPostResponse> result = new PageResult<CollectPostResponse>();
		List<CollectPostResponse> postResponse = new ArrayList<>();		
		PageResult<Collect> collects = kffCollectService.findPage(query);
		if(collects != null && CollectionUtils.isNotEmpty(collects.getRows())){
			result.setCurPageNum(collects.getCurPageNum());
			result.setPageSize(collects.getPageSize());
			result.setQueryParameters(collects.getQueryParameters());
			result.setRowCount(collects.getRowCount());
			result.setRowsPerPage(collects.getRowsPerPage());
			for(Collect collect:collects.getRows()){
				CollectPostResponse response = new CollectPostResponse();
				BeanUtils.copyProperties(collect, response);
				//查询post和project信息
				Post post = kffPostService.findById(collect.getPostId());
				if(post != null){
							response.setPostShortDesc(post.getPostShortDesc());
							if(StringUtils.isNotBlank(post.getPostSmallImages())){
								try{
								List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
								response.setPostSmallImages(pfl);
								}catch(Exception e){
									logger.error("我的收藏列表解析帖子缩略图json出错:{}",e); 
								}
							}
							response.setPostTitle(post.getPostTitle());
							response.setCommentsNum(post.getCommentsNum());
							response.setCollectNum(post.getCollectNum());
							response.setPraiseNum(post.getPraiseNum());
							response.setPageviewNum(post.getPageviewNum());
							response.setDonateNum(post.getDonateNum());
							response.setCreateUserIcon(post.getCreateUserIcon());
							response.setCreateUserName(post.getCreateUserName());
							response.setCreateUserSignature(post.getCreateUserSignature());
							//response.setTotalScore(post.get);
							KFFProject project = kffProjectService.findById(post.getProjectId());
							if(project != null){
								response.setProjectChineseName(project.getProjectChineseName());
								response.setProjectCode(project.getProjectCode());
								response.setProjectEnglishName(project.getProjectEnglishName());
								response.setProjectIcon(project.getProjectIcon());
								response.setProjectSignature(project.getProjectSignature());
								response.setTotalScore(project.getTotalScore());
							}
							postResponse.add(response);
			}
		}
			result.setRows(postResponse);
	}
		return result;
	}

	@Override
	public PageResult<FollowResponse> findPageMyFollow(PaginationQuery query)
			throws RestServiceException {
		
		PageResult<FollowResponse> result = new PageResult<FollowResponse>();
        List<FollowResponse> followResponses = new ArrayList<>();
		PageResult<Follow> follows = kffFollowService.findPage(query);				
		if(follows != null && CollectionUtils.isNotEmpty(follows.getRows())){
			result.setCurPageNum(follows.getCurPageNum());
			result.setPageSize(follows.getPageSize());
			result.setQueryParameters(follows.getQueryParameters());
			result.setRowCount(follows.getRowCount());
			result.setRowsPerPage(follows.getRowsPerPage());			
			for(Follow follow:follows.getRows()){
				FollowResponse response = new FollowResponse();
				BeanUtils.copyProperties(follow, response);
				//查询post和project信息
				///*关注类型：1-关注项目;2-关注帖子；3-关注用户
				if(follow.getFollowType() != null && follow.getFollowType() == 2){
					Post post = kffPostService.findById(follow.getFollowedId());
					if(post != null){
						response.setPostShortDesc(post.getPostShortDesc());
						if(StringUtils.isNotBlank(post.getPostSmallImages())){
							try{
							List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
							response.setPostSmallImages(pfl);
							}catch(Exception e){
								logger.error("我的关注列表解析帖子缩略图json出错:{}",e); 
							}
						}
						response.setPostTitle(post.getPostTitle());
						response.setCommentsNum(post.getCommentsNum());
						response.setCollectNum(post.getCollectNum());
						response.setPraiseNum(post.getPraiseNum());
						response.setPageviewNum(post.getPageviewNum());
						response.setDonateNum(post.getDonateNum());
						response.setCreateUserIcon(post.getCreateUserIcon());
						response.setCreateUserName(post.getCreateUserName());
						response.setCreateUserSignature(post.getCreateUserSignature());
						//response.setTotalScore(post.get);
						KFFProject project = kffProjectService.findById(post.getProjectId());
						if(project != null){
							response.setProjectChineseName(project.getProjectChineseName());
							response.setProjectCode(project.getProjectCode());
							response.setProjectEnglishName(project.getProjectEnglishName());
							response.setProjectIcon(project.getProjectIcon());
							response.setProjectSignature(project.getProjectSignature());
							response.setTotalScore(project.getTotalScore());
						}
					}
					
				}else if (follow.getFollowType() != null && follow.getFollowType() == 3){
					//被关注用户信息在follow表中已包含
				}
				followResponses.add(response);
			}			
			result.setRows(followResponses);
		}
		
		return result;
	}

	@Override
	public KFFMessage getMessageDetail(Integer userId,Integer messageId)
			throws RestServiceException {
		KFFMessage result = kffMessageService.findById(messageId);
		if(result == null){
			throw new RestServiceException("消息不存在");
		}
		if(result.getUserId() != userId){
			throw new RestServiceException("不能查看他人消息");
		}
		return result;
	}

	@Override
	public PageResult<KFFMessage> findPageMyMessages(PaginationQuery query) {

		return kffMessageService.findPage(query);
	}

	@Override
	public void deleteMessage(Integer userId, Integer messageId)
			throws RestServiceException {
		KFFMessage result = kffMessageService.findById(messageId);
		if(result == null){
			throw new RestServiceException("消息不存在");
		}
		if(result.getUserId() != userId){
			throw new RestServiceException("不能删除他人消息");
		}	
	     result.setState(2);  //已读
	     result.setStatus(0); //删除
	     result.setUpdateTime(new Date());
	     kffMessageService.update(result);
	}

	@Override
	public KFFProject submitProject(SubmitKFFProjectRequest projectRequest)
			throws RestServiceException {
		Date now = new Date();
		KFFProject project = new KFFProject();
		BeanUtils.copyProperties(projectRequest, project);
		if(StringUtils.isBlank(project.getProjectCode())){
			throw new RestServiceException("代币名称不能为空");
		}
		if(StringUtils.isBlank(project.getProjectEnglishName())){
			throw new RestServiceException("项目英文名称不能为空");
		}
		if(StringUtils.isBlank(project.getWebsiteUrl())){
			throw new RestServiceException("官网地址不能为空");
		}
		if(StringUtils.isBlank(project.getWhitepaperUrl())){
			throw new RestServiceException("白皮书地址不能为空");
		}
		if(StringUtils.isBlank(project.getProjectTypeName())){
			throw new RestServiceException("项目类型名称不能为空");
		}
		if(StringUtils.isBlank(project.getSubmitUserContactInfo())){
			throw new RestServiceException("联系信息不能为空");
		}
		if(StringUtils.isBlank(project.getSubmitReason())){
			throw new RestServiceException("推荐理由不能为空");
		}
		if(StringUtils.isBlank(project.getProjectDesc())){
			throw new RestServiceException("项目描述不能为空");
		}
		if(project.getWhitepaperUrl().length()>255){
			throw new RestServiceException("白皮书地址长度超限");
		}
		if(project.getSubmitUserContactInfo().length()>30){
			throw new RestServiceException("联系信息长度超限");
		}
		if(project.getSubmitReason().length()>3000){
			throw new RestServiceException("提交理由信息长度超限");
		}
		if(project.getProjectDesc().length()>3000){
			throw new RestServiceException("项目描述信息长度超限");
		}
		
		project.setCreateTime(now);
		project.setUpdateTime(now);
		project.setState(1);//1；待审核；2-审核通过；3-拒绝
		project.setStatus(1);
		project.setIssueDate(DateUtil.getDate(project.getIssueDateStr(), "YYYY-MM-dd"));
		kffProjectService.save(project);
		return null;
	}

	@Override
	public List<DprojectType> findAllProjectTypes() throws RestServiceException {

		return kffDprojectTypeService.findAllProjectTypes();
	}

	@Override
	public List<ProjectResponse> findProjectByCode(int sortType,Integer userId,String projectCode)
			throws RestServiceException {
	    if(StringUtils.isBlank(projectCode)){
	    	throw new RestServiceException("查询条件:项目关键字不能为空");
	    }
		List<KFFProject> projects = new ArrayList<>();
		List<ProjectResponse> result = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		map.put("state", "2");
		map.put("projectCode", projectCode);
		if(sortType == 1 ){
			map.put("sortField", "follower_num");
		}
		projects = kffProjectService.findProjectByCode(map);
		if(CollectionUtils.isNotEmpty(projects)){
			PaginationQuery query = new PaginationQuery();
	        query.addQueryData("followUserId", userId +"");
	        query.addQueryData("followType", "1"); //关注类型：1-关注项目;2-关注帖子；3-关注用户       
	        query.addQueryData("status", "1");
	        query.setPageIndex(1);
	        query.setRowsPerPage(10);
	        
			for(KFFProject project:projects){
				ProjectResponse response = new ProjectResponse();
				BeanUtils.copyProperties(project, response);
				query.addQueryData("followedProjectId", project.getProjectId()+"");
                PageResult<Follow> follows = kffFollowService.findPage(query);  
				if(follows != null && CollectionUtils.isNotEmpty(follows.getRows())){
					response.setFollowStatus(1);
				}
				result.add(response);
			}
		}
		
		return result;
	}

	@Override
	public void saveCommendation(CommendationRequest commendationRequest)
			throws RestServiceException {
		if(commendationRequest == null){
			throw new RestServiceException("commendationRequest can't be null");
		}
		if(commendationRequest.getPostId() == null){
			throw new RestServiceException("帖子ID不能为空");
		}
		if(commendationRequest.getProjectId() == null){
			throw new RestServiceException("项目ID不能为空");
		}
		Post post = kffPostService.findById(commendationRequest.getPostId());
		if(post == null){
			throw new RestServiceException("帖子不存在");
		}
		//帖子捐赠人数加+1
		kffPostService.updateDonateNum(post.getPostId());
		
		if(commendationRequest.getSendUserId() == null){
			throw new RestServiceException("发送用户不能为空");
		}
		if(commendationRequest.getAmount() == null || commendationRequest.getAmount() <= 0){
			throw new RestServiceException("打赏金额不合法");
		}
		if(commendationRequest.getReceiveUserId() == null){
			throw new RestServiceException("接收用户不能为空");
		}
		KFFUser receiveUser = kffUserService.findById(commendationRequest.getReceiveUserId());
		if(receiveUser == null){
			throw new RestServiceException("接收用户不存在");
		}
		KFFUser sendUser = kffUserService.findById(commendationRequest.getSendUserId());
		
		if(sendUser == null){
			throw new RestServiceException("发送用户不存在");
		}
		if(sendUser.getKffCoinNum() < commendationRequest.getAmount()){
			throw new RestServiceException("账户余额不足:捐赠数量"+commendationRequest.getAmount()+"余额数量"+sendUser.getKffCoinNum());
		}
		commendationRequest.setSendUserIcon(sendUser.getIcon());
		commendationRequest.setPostType(post.getPostType());
		
		Commendation commendation = new Commendation();
		BeanUtils.copyProperties(commendationRequest, commendation);
		kffCommendationService.save(commendation);
		
		Date now = new Date();
		Integer randomBIZCode=Integer.valueOf(RandomUtil.produceNumber(3));
		//发送方扣减，接收方增加
		kffUserService.updateUserKFFCoinNum(sendUser.getUserId(),-commendationRequest.getAmount());
		kffUserService.updateUserKFFCoinNum(receiveUser.getUserId(), commendationRequest.getAmount());
		//生成流水记录
		Tokenrecords sendTokenRecords = new Tokenrecords();
		sendTokenRecords.setTradeType(2); //交易类型:1-收入；2-支出
		sendTokenRecords.setAmount(commendationRequest.getAmount());
		sendTokenRecords.setBalance(sendUser.getKffCoinNum());
		sendTokenRecords.setCreateTime(now);
		//交易描述:10-充值11-评测奖励12-讨论奖励13-文章奖励14-榜单奖励15-用户赞赏16-注册奖励17-点赞奖励18-邀请好友奖励21-提现22-赞赏他人'
		sendTokenRecords.setFunctionDesc("赞赏他人");
		sendTokenRecords.setFunctionType(22);
		sendTokenRecords.setMemo("捐赠他人");
		sendTokenRecords.setStatus(1);
		//交易流水号=交易类型（2位） 交易时间年月日（8位） 业务记录ID（10位）
		sendTokenRecords.setTradeCode(Numbers.getSerialNumber(sendTokenRecords.getTradeType(), randomBIZCode));
		sendTokenRecords.setTradeDate(now);		
		sendTokenRecords.setUpdateTime(now);
		sendTokenRecords.setUserId(sendUser.getUserId());		
		kffTokenrecordsService.save(sendTokenRecords);
		
		Tokenrecords receiveTokenRecords  = new Tokenrecords();	
		receiveTokenRecords.setTradeType(1); //交易类型:1-收入；2-支出
		receiveTokenRecords.setAmount(commendationRequest.getAmount());
		receiveTokenRecords.setBalance(receiveUser.getKffCoinNum());
		receiveTokenRecords.setCreateTime(now);
		//交易描述:10-充值11-评测奖励12-讨论奖励13-文章奖励14-榜单奖励15-用户赞赏16-注册奖励17-点赞奖励18-邀请好友奖励21-提现22-赞赏他人'
		receiveTokenRecords.setFunctionDesc("用户赞赏");
		sendTokenRecords.setFunctionType(15);
		receiveTokenRecords.setMemo("他人捐赠");
		receiveTokenRecords.setStatus(1);
		//交易流水号=交易类型（2位） 交易时间年月日（8位） 业务记录ID（10位）
		receiveTokenRecords.setTradeCode(Numbers.getSerialNumber(sendTokenRecords.getTradeType(), randomBIZCode));
		receiveTokenRecords.setTradeDate(now);		
		receiveTokenRecords.setUpdateTime(now);
		receiveTokenRecords.setUserId(receiveUser.getUserId());	
		kffTokenrecordsService.save(receiveTokenRecords);
	}

	@Override
	public KFFUserHomeResponse findUserHomeByUserId(Integer loginUserId,
			Integer userId) throws RestServiceException {
		KFFUserHomeResponse result = null;
		if(loginUserId == null || loginUserId <=0){
			throw new RestServiceException("登录用户ID不能为空");
		}
		KFFUser user = null;
		if(userId == null || userId <= 0){
		//查看用户本人	
			user = kffUserService.findById(loginUserId);
			if(user == null){
				throw new RestServiceException("用户不存在"+loginUserId);
			}
			result.setHomePageTitle("我的主页");
			result.setShowFollow(0);
			
		}else{
		//查看他人	
			user = kffUserService.findById(userId);	
			if(user == null){
				throw new RestServiceException("用户不存在"+userId);
			}
			result.setHomePageTitle(user.getUserName()+"的主页");
			//默认设置显示 关注
			result.setShowFollow(1);
			PaginationQuery query = new PaginationQuery();
	        query.addQueryData("followUserId", loginUserId +"");
	        query.addQueryData("followedUserId", userId +"");
	        query.addQueryData("followType", "3"); //关注类型：1-关注项目;2-关注帖子；3-关注用户       	        
	        query.addQueryData("status", "1");
	        query.setPageIndex(1);
	        query.setRowsPerPage(10);
	        PageResult<Follow> follows = kffFollowService.findPage(query);
		    if(follows !=null && CollectionUtils.isNotEmpty(follows.getRows())){
		    	//默认设置显示 取消关注
				result.setShowFollow(2);
		    }
		
		}
		BeanUtils.copyProperties(user, result);
		result.setTotalPostNum(result.getArticleNum()+result.getDiscussNum()+result.getEvaluationNum());
		
		return result;
	}

	@Override
	public PageResult<PostResponse> findPageEvaluationList(PaginationQuery query)
			throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> respones = new ArrayList<>();
		PageResult<Post> posts = kffPostService.findPage(query);
		if(posts != null && CollectionUtils.isNotEmpty(posts.getRows())){
			for(Post post:posts.getRows()){
				PostResponse response = new PostResponse();
				BeanUtils.copyProperties(post, response);
				if(StringUtils.isNotBlank(post.getPostSmallImages())){
					try{
					List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
					response.setPostSmallImages(pfl);
					}catch(Exception e){
						logger.error("评测列表解析帖子缩略图json出错:{}",e); 
					}
				}
				respones.add(response);
			}
			result.setRows(respones);
		}
		return result;
	}

	@Override
	public PageResult<PostResponse> findPageDisscussList(PaginationQuery query)
			throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> respones = new ArrayList<>();
		PageResult<Post> posts = kffPostService.findPage(query);
		if(posts != null && CollectionUtils.isNotEmpty(posts.getRows())){
			for(Post post:posts.getRows()){
				PostResponse response = new PostResponse();
				BeanUtils.copyProperties(post, response);
				if(StringUtils.isNotBlank(post.getPostSmallImages())){
					try{
					List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
					response.setPostSmallImages(pfl);
					}catch(Exception e){
						logger.error("讨论列表解析帖子缩略图json出错:{}",e); 
					}
				}
				respones.add(response);
			}
			result.setRows(respones);
		}
		return result;
	}

	@Override
	public PageResult<PostResponse> findPageArticleList(PaginationQuery query)
			throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> respones = new ArrayList<>();
		PageResult<Post> posts = kffPostService.findPage(query);
		if(posts != null && CollectionUtils.isNotEmpty(posts.getRows())){
			for(Post post:posts.getRows()){
				PostResponse response = new PostResponse();
				BeanUtils.copyProperties(post, response);
				if(StringUtils.isNotBlank(post.getPostSmallImages())){
					try{
					List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
					response.setPostSmallImages(pfl);
					}catch(Exception e){
						logger.error("文章列表解析帖子缩略图json出错:{}",e); 
					}
				}
				respones.add(response);
			}
			result.setRows(respones);
		}
		return result;
	}

	@Override
	public ProjectResponse findProjectById(Integer userId, Integer projectId)
			throws RestServiceException {
		ProjectResponse response = new ProjectResponse();
		if(userId == null){
			throw new RestServiceException("用户ID不能为空");
		}
		if(projectId == null){
			throw new RestServiceException("项目ID不能为空");
		}
		KFFProject project = kffProjectService.findById(projectId);
		if(project == null){
			throw new RestServiceException("项目不存在"+projectId);
		}
		
		BeanUtils.copyProperties(project, response);		
		
		PaginationQuery query = new PaginationQuery();
        query.addQueryData("followUserId", userId +"");
        query.addQueryData("followType", "1"); //关注类型：1-关注项目;2-关注帖子；3-关注用户       
        query.addQueryData("status", "1");
        query.addQueryData("followedProjectId", projectId+"");
        query.setPageIndex(1);
        query.setRowsPerPage(10);
        PageResult<Follow> follows = kffFollowService.findPage(query);  
		if(follows != null && CollectionUtils.isNotEmpty(follows.getRows())){
		  response.setFollowStatus(1);
		}else{
		  response.setFollowStatus(0);
		}
		
		List<KFFUser> activeUsers = findProjectActiveUsers(projectId);
		
		response.setActiveUsers(activeUsers);
		
		KFFUser owner = kffUserService.findById(project.getSubmitUserId());
		response.setOwner(owner);
				
		return response;
	}

	@Override
	public List<KFFUser> findProjectActiveUsers(Integer projectId)
			throws RestServiceException {
		List<KFFUser> activeUsers = new ArrayList<>();
		KFFProject project = kffProjectService.findById(projectId);
		if(project != null){
			Map<String,Object> map = new HashMap<>();
			map.put("projectId", projectId+"");
			List<Post> posts = kffPostService.findProjectActiveUsers(map);
			if(CollectionUtils.isNotEmpty(posts)){
				for(Post post:posts){
					KFFUser user = new KFFUser();
					user = kffUserService.findById(post.getCreateUserId());
					activeUsers.add(user);
				}
			}
		}
		return activeUsers;
	}

	@Override
	public void saveComment(CommentsRequest comment)
			throws RestServiceException {
		if(comment == null){
			throw new RestServiceException("参数缺失");
		}		
		if(comment.getCommentUserId() == null){
			throw new RestServiceException("评论用户ID不能为空");
		}		
		if(StringUtils.isBlank(comment.getCommentContent())){
			throw new RestServiceException("评论内容不能为空");
		}
		if(comment.getCommentContent().length() > 30000){
			throw new RestServiceException("文章内容长度超过限制");
		}
		if(comment.getPostId() == null){
			throw new RestServiceException("帖子ID不能为空");
		}	
		KFFUser commentUser = kffUserService.findById(comment.getCommentUserId());
		if(commentUser == null){
			throw new RestServiceException("用户不存在"+comment.getCommentUserId());
		}
		Post post = kffPostService.findById(comment.getPostId());
		if(post == null){
			throw new RestServiceException("帖子不存在"+comment.getPostId());
		}
		Comments saveComment = new Comments();
		Date now = new Date();
		BeanUtils.copyProperties(comment, saveComment);
		saveComment.setCommentUserIcon(commentUser.getIcon());
		saveComment.setCommentUserName(commentUser.getUserName());
		
		saveComment.setBecommentedUserId(post.getCreateUserId());
		saveComment.setBecommentedUserIcon(post.getCreateUserIcon());
		saveComment.setBecommentedUserName(post.getCreateUserName());
		
		if(comment.getParentCommentsId() != null){
		//回复评论需要把评论的用户ID做为被评论人ID
			Comments parentComment = kffCommentsService.findById(comment.getParentCommentsId());
			if(parentComment == null){
				throw new RestServiceException("错误的父帖ID"+comment.getParentCommentsId());
			}
			saveComment.setBecommentedUserId(parentComment.getCommentUserId());
			saveComment.setBecommentedUserIcon(parentComment.getCommentUserIcon());
			saveComment.setBecommentedUserName(parentComment.getCommentUserName());
		}

		saveComment.setPostId(post.getPostId());
		saveComment.setProjectId(post.getProjectId());
		saveComment.setPostType(post.getPostType());
		saveComment.setCreateTime(now);
		saveComment.setUpdateTime(now);
		saveComment.setStatus(1);
		kffCommentsService.save(saveComment);
	}

	@Override
	public void saveArticle(ArticleRequest articleRequest)
			throws RestServiceException {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		if(articleRequest == null){
			throw new RestServiceException("参数缺失");
		}		
		if(articleRequest.getCreateUserId() == null){
			throw new RestServiceException("创建用户ID不能为空");
		}	
		if(articleRequest.getProjectId() == null){
			throw new RestServiceException("项目ID不能为空");
		}	
		if(StringUtils.isBlank(articleRequest.getArticleContents())){
			throw new RestServiceException("文章内容不能为空");
		}
		if(StringUtils.isBlank(articleRequest.getPostTitle())){
			throw new RestServiceException("文章标题不能为空");
		}
		if(articleRequest.getArticleContents().length() > 30000){
			throw new RestServiceException("文章内容长度超过限制");
		}	
		if(articleRequest.getPostTitle().length() > 30){
			throw new RestServiceException("文章标题长度不能超过30字");
		}
		KFFUser createUser = kffUserService.findById(articleRequest.getCreateUserId());
		if(createUser == null){
			throw new RestServiceException("用户不存在"+articleRequest.getCreateUserId());
		}
		KFFProject project = kffProjectService.findById(articleRequest.getProjectId());
		if(project == null){
			throw new RestServiceException("项目不存在"+articleRequest.getProjectId());
		}
		Post post = new Post();
		Date now = new Date();
		post.setCollectNum(0);
		post.setCommentsNum(0);
		post.setCreateTime(now);
		post.setUpdateTime(now);
		post.setCreateUserIcon(createUser.getIcon());
		post.setCreateUserId(createUser.getUserId());
		post.setCreateUserName(createUser.getUserName());
		post.setCreateUserSignature(createUser.getUserSignature());
		post.setDonateNum(0);
		post.setPageviewNum(0);
		post.setPostShortDesc(articleRequest.getArticleContents().substring(300));
		post.setPostSmallImages(articleRequest.getPostSmallImages());
		post.setPostTitle(articleRequest.getPostTitle()); 
		post.setPostType(3);//帖子类型：1-评测；2-讨论；3-文章
		post.setPraiseNum(0);
		post.setProjectCode(project.getProjectCode());
		post.setProjectEnglishName(project.getProjectEnglishName());
		post.setProjectIcon(project.getProjectIcon());
		post.setProjectId(project.getProjectId());
		post.setStatus(1);
		post.setUuid(uuid);
		kffPostService.save(post);
		
		Post newPost = kffPostService.findByUUID(uuid);
		if(newPost == null){
			throw new RestServiceException("帖子不存在"+uuid);
		}
		Article article = new Article();
		article.setArticleContents(articleRequest.getArticleContents());
		article.setPostId(newPost.getPostId());
		article.setPostUuid(uuid);
		kffArticleService.save(article);
		
	}

	@Override
	public void saveDiscuss(DiscussRequest discussRequest)
			throws RestServiceException {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		if(discussRequest == null){
			throw new RestServiceException("参数缺失");
		}		
		if(discussRequest.getCreateUserId() == null){
			throw new RestServiceException("创建用户ID不能为空");
		}	
		if(discussRequest.getProjectId() == null){
			throw new RestServiceException("项目ID不能为空");
		}	
		if(StringUtils.isBlank(discussRequest.getDisscussContents())){
			throw new RestServiceException("讨论内容不能为空");
		}
		if(StringUtils.isBlank(discussRequest.getPostTitle())){
			throw new RestServiceException("讨论标题不能为空");
		}
		if(discussRequest.getDisscussContents().length() > 30000){
			throw new RestServiceException("讨论内容长度超过限制");
		}	
		if(discussRequest.getPostTitle().length() > 30){
			throw new RestServiceException("讨论标题长度不能超过30字");
		}
		KFFUser createUser = kffUserService.findById(discussRequest.getCreateUserId());
		if(createUser == null){
			throw new RestServiceException("用户不存在"+discussRequest.getCreateUserId());
		}
		KFFProject project = kffProjectService.findById(discussRequest.getProjectId());
		if(project == null){
			throw new RestServiceException("项目不存在"+discussRequest.getProjectId());
		}
		Post post = new Post();
		Date now = new Date();
		post.setCollectNum(0);
		post.setCommentsNum(0);
		post.setCreateTime(now);
		post.setUpdateTime(now);
		post.setCreateUserIcon(createUser.getIcon());
		post.setCreateUserId(createUser.getUserId());
		post.setCreateUserName(createUser.getUserName());
		post.setCreateUserSignature(createUser.getUserSignature());
		post.setDonateNum(0);
		post.setPageviewNum(0);
		post.setPostShortDesc(discussRequest.getDisscussContents().substring(300));
		post.setPostSmallImages(discussRequest.getDiscussImages());
		post.setPostTitle(discussRequest.getPostTitle()); 
		post.setPostType(2);//帖子类型：1-评测；2-讨论；3-文章
		post.setPraiseNum(0);
		post.setProjectCode(project.getProjectCode());
		post.setProjectEnglishName(project.getProjectEnglishName());
		post.setProjectIcon(project.getProjectIcon());
		post.setProjectId(project.getProjectId());
		post.setStatus(1);
		post.setUuid(uuid);
		kffPostService.save(post);
		
		Post newPost = kffPostService.findByUUID(uuid);
		if(newPost == null){
			throw new RestServiceException("帖子不存在"+uuid);
		}
		Discuss discuss = new Discuss();
		discuss.setDisscussContents(discussRequest.getDisscussContents());
		discuss.setPostId(newPost.getPostId());
		discuss.setPostUuid(uuid);
		discuss.setTagInfos(discussRequest.getTagInfos());
		kffDiscussService.save(discuss);
		
	}

	@Override
	public List<Dtags> findAllTags() throws RestServiceException {
		
		return kffDtagsService.findAllTags();
		
	}

	@Override
	public void saveEvaluation(EvaluationRequest evaluationRequest)
			throws RestServiceException {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		if(evaluationRequest == null){
			throw new RestServiceException("参数缺失");
		}		
		if(evaluationRequest.getCreateUserId() == null){
			throw new RestServiceException("创建用户ID不能为空");
		}	
		if(evaluationRequest.getProjectId() == null){
			throw new RestServiceException("项目ID不能为空");
		}
		if(evaluationRequest.getModelType() == null){
			throw new RestServiceException("评测类型不能为空");
		}
		if(evaluationRequest.getModelType() != 1 &&
		   evaluationRequest.getModelType() != 2 &&
		   evaluationRequest.getModelType() != 3 &&
		   evaluationRequest.getModelType() != 4){
			throw new RestServiceException("非法评测类型");
		}
		if(StringUtils.isBlank(evaluationRequest.getEvauationContent())){
			throw new RestServiceException("评测内容不能为空");
		}
		if(StringUtils.isBlank(evaluationRequest.getPostTitle())){
			throw new RestServiceException("评测标题不能为空");
		}
		if(evaluationRequest.getEvauationContent().length() > 30000){
			throw new RestServiceException("评测内容长度超过限制");
		}	
		if(evaluationRequest.getPostTitle().length() > 30){
			throw new RestServiceException("评测标题长度不能超过30字");
		}
		KFFUser createUser = kffUserService.findById(evaluationRequest.getCreateUserId());
		if(createUser == null){
			throw new RestServiceException("用户不存在"+evaluationRequest.getCreateUserId());
		}
		KFFProject project = kffProjectService.findById(evaluationRequest.getProjectId());
		if(project == null){
			throw new RestServiceException("项目不存在"+evaluationRequest.getProjectId());
		}
		BigDecimal totalScore = evaluationRequest.getTotalScore();
		
		Post post = new Post();
		Date now = new Date();
		post.setCollectNum(0);
		post.setCommentsNum(0);
		post.setCreateTime(now);
		post.setUpdateTime(now);
		post.setCreateUserIcon(createUser.getIcon());
		post.setCreateUserId(createUser.getUserId());
		post.setCreateUserName(createUser.getUserName());
		post.setCreateUserSignature(createUser.getUserSignature());
		post.setDonateNum(0);
		post.setPageviewNum(0);
		post.setPostShortDesc(evaluationRequest.getEvauationContent().substring(300));
		post.setPostSmallImages(evaluationRequest.getPostSmallImages());
		post.setPostTitle(evaluationRequest.getPostTitle()); 
		post.setPostType(1);//帖子类型：1-评测；2-讨论；3-文章
		post.setPraiseNum(0);
		post.setProjectCode(project.getProjectCode());
		post.setProjectEnglishName(project.getProjectEnglishName());
		post.setProjectIcon(project.getProjectIcon());
		post.setProjectId(project.getProjectId());
		post.setStatus(1);
		post.setUuid(uuid);
		kffPostService.save(post);
		
		Post newPost = kffPostService.findByUUID(uuid);
		if(newPost == null){
			throw new RestServiceException("帖子不存在"+uuid);
		}
		Evaluation evaluation = new Evaluation();
		evaluation.setEvauationContent(evaluationRequest.getEvauationContent());
		evaluation.setPostId(newPost.getPostId());
		evaluation.setProjectId(project.getProjectId());
		evaluation.setPostUuid(uuid);
		evaluation.setEvaluationTags(evaluationRequest.getEvaluationTags());
		evaluation.setProfessionalEvaDetail(evaluationRequest.getProfessionalEvaDetail());
		evaluation.setCreateTime(now);
		evaluation.setCreateUserId(createUser.getUserId());
		evaluation.setModelType(evaluationRequest.getModelType());
		evaluation.setStatus(1);
		evaluation.setTotalScore(totalScore);
		kffEvaluationService.save(evaluation);
		
	}

	@Override
	public void saveEvaluationModel(
			DevaluationModelRequest devaluationModelRequest)
			throws RestServiceException {
		if(devaluationModelRequest == null){
			throw new RestServiceException("参数缺失");
		}	
		if(devaluationModelRequest.getProjectId() == null){
			throw new RestServiceException("项目ID不能为空");
		}
		if(StringUtils.isBlank(devaluationModelRequest.getProfessionalEvaDetail())){
			throw new RestServiceException("模型内容不能为空");
		}
		
		try{
			
			List<DevaluationModel> dms = JSONArray.parseArray(devaluationModelRequest.getProfessionalEvaDetail(), DevaluationModel.class);
			if(CollectionUtils.isNotEmpty(dms)){
				int totalWeight = 0;
				for(DevaluationModel dm:dms){
					if(StringUtils.isBlank(dm.getModelName())){
						throw new RestServiceException("模型名称不能为空");
					}
					if(dm.getModelName().length()>30){
						throw new RestServiceException("模型名称能超过30字符");
					}
					if(dm.getModelWeight()==null){
						throw new RestServiceException("模型权重不能为空");
					}
					totalWeight = totalWeight + dm.getModelWeight();
				}
				if(totalWeight != 100){
					throw new RestServiceException("模型权重之和不为100");
				}
				Integer batchId = Integer.parseInt(RandomUtil.produceNumber(8));
				Date now = new Date();
				for(DevaluationModel dm:dms){
					DevaluationModel save = new DevaluationModel();
					save.setBatchId(batchId);
					save.setCreateTime(now);
					save.setCreateUserId(devaluationModelRequest.getCreateUserId());
					save.setModelDesc("用户自建模型");
					save.setModelName(dm.getModelName());
					save.setModelType(3);
					save.setModelWeight(dm.getModelWeight());
					save.setStatus(1);
					save.setUpdateTime(now);
					kffDevaluationModelService.save(save);
				}
				
			}
		}catch(Exception e){
			logger.error("用户自定义模型解析json失败:{}",e);
			throw new RestServiceException("非法模型内容"+devaluationModelRequest.getProfessionalEvaDetail());
		}
	}

	@Override
	public void savePraise(Integer userId, Integer postId) throws RestServiceException {
		if(userId == null){
			throw new RestServiceException("用户id不能为空");
		}
		if(postId == null){
			throw new RestServiceException("帖子id不能为空");
		}
		KFFUser user = kffUserService.findById(userId);
		if(user == null){
			throw new RestServiceException("用户不存在"+userId);
		}
		Post post = kffPostService.findById(postId);
		if(post == null){
			throw new RestServiceException("帖子不存在"+postId);
		}
	    Date now = new Date();
	    boolean praiseNumIncrease = false;
		Praise praise = kffPraiseService.findByPostId(userId,postId);
		if(praise == null){
		    Praise save = new Praise();
		    save.setBepraiseUserId(post.getCreateUserId());
		    save.setCreateTime(now);
		    save.setPostId(postId);
		    save.setPostType(post.getPostType());
		    save.setPraiseUserId(userId);
		    save.setProjectId(post.getProjectId());
		    save.setStatus(1);
		    save.setUpdateTime(now);;
			kffPraiseService.save(save);
			praiseNumIncrease = true;
		}else{
			if(praise.getStatus() !=null && praise.getStatus()==0){
				praise.setUpdateTime(now);
				praise.setStatus(1);
				kffPraiseService.update(praise);
				praiseNumIncrease = true;
			}else{
				logger.warn("已点赞存在重复点赞");
				//throw new RestServiceException("已点赞，请勿重复点赞");
			}
		}
		//帖子点赞数加1
		if(praiseNumIncrease){
			kffPostService.increasePraiseNum(postId);
		}
	}

	@Override
	public void cancelPraise(Integer userId, Integer postId) throws RestServiceException {
		if(userId == null){
			throw new RestServiceException("用户id不能为空");
		}
		if(postId == null){
			throw new RestServiceException("帖子id不能为空");
		}
		KFFUser user = kffUserService.findById(userId);
		if(user == null){
			throw new RestServiceException("用户不存在"+userId);
		}
		Post post = kffPostService.findById(postId);
		if(post == null){
			throw new RestServiceException("帖子不存在"+post);
		}
	    Date now = new Date();
	    boolean praiseNumDecrese = false;
		Praise praise = kffPraiseService.findByPostId(userId,postId);
		if(praise == null){
		    Praise save = new Praise();
		    save.setBepraiseUserId(post.getCreateUserId());
		    save.setCreateTime(now);
		    save.setPostId(postId);
		    save.setPostType(post.getPostType());
		    save.setPraiseUserId(userId);
		    save.setProjectId(post.getProjectId());
		    save.setStatus(0);
		    save.setUpdateTime(now);;
			kffPraiseService.save(save);
			praiseNumDecrese = true;
		}else{
			if(praise.getStatus() !=null && praise.getStatus()==0){
				logger.warn("已取消点赞存在重复取消点赞");
				//throw new RestServiceException("已取消点赞存在重复取消点赞");
			}else{
				praise.setUpdateTime(now);
				praise.setStatus(0);
				kffPraiseService.update(praise);
				praiseNumDecrese = true;								
			}
		}
		//帖子点赞数减1
		if(praiseNumDecrese){
			kffPostService.decreasePraiseNum(postId);
		}
		
	}

	@Override
	public void saveFollow(Integer userId, Integer followType, Integer followedId) throws RestServiceException {
		if(userId == null){
			throw new RestServiceException("用户id不能为空");
		}
		if(followedId == null){
			throw new RestServiceException("关注id不能为空");
		}
		if(followType == null){
			throw new RestServiceException("关注类型不能为空");
		}
		//1-关注项目;2-关注帖子；3-关注用户
		if(followType != 1 && followType != 2 && followType != 3){
			throw new RestServiceException("无效的关注类型");
		}
		KFFUser user = kffUserService.findById(userId);
		if(user == null){
			throw new RestServiceException("用户不存在"+userId);
		}
		
		Integer followedUserId = 0;
		String followedUserIcon = "";
		String followedUserName = "";
        String followedUserSignature = "";				
		//关注项目
		if(followType == KFFConstants.FOLLOW_TYPE_PROJECT){
		    KFFProject followedProject = kffProjectService.findById(followedId);
		    if(followedProject == null){
		    	throw new RestServiceException("关注项目不存在"+followedId);
		    }
		    followedUserId = followedProject.getSubmitUserId();
		    KFFUser projectCreator = kffUserService.findById(followedUserId);
		    if(projectCreator != null){
		    	followedUserId = projectCreator.getUserId();
		    	followedUserIcon = projectCreator.getIcon();
			    followedUserName = projectCreator.getUserName();
			    followedUserSignature = projectCreator.getUserSignature();			    
		    }
		}else if(followType == KFFConstants.FOLLOW_TYPE_POST){
			//2-关注帖子
			Post followedPost = kffPostService.findById(followedId);
			if(followedPost == null){
				throw new RestServiceException("关注帖子不存在"+followedId);
			}
	    	followedUserId = followedPost.getCreateUserId();
			followedUserIcon = followedPost.getCreateUserIcon();
		    followedUserName = followedPost.getCreateUserName();
		    followedUserSignature = followedPost.getCreateUserSignature();
		    
		}else if(followType == KFFConstants.FOLLOW_TYPE_USER){
		 //3-关注用户
			KFFUser followedUser = kffUserService.findById(followedId);
            if(followedUser == null){
            	throw new RestServiceException("关注的用户不存在"+followedId);
            }
            followedUserId = followedUser.getUserId();
			followedUserIcon = followedUser.getIcon();
		    followedUserName = followedUser.getUserName();
		    followedUserSignature = followedUser.getUserSignature();
            
		}		
		Follow existFollow = kffFollowService.findByUserIdAndFollowType(userId,followType,followedId);
		Date now = new Date();
	    boolean followNumIncrease = false;
		if(existFollow == null){
		//新增	
				Follow save = new Follow();
				save.setCreateTime(now);
				save.setFollowedId(followedId);
				save.setFollowedUserIcon(followedUserIcon);
				save.setFollowedUserId(followedUserId);
				save.setFollowedUserName(followedUserName);
				save.setFollowedUserSignature(followedUserSignature);
				save.setFollowerUserName(user.getUserName());
				save.setFollowType(followType);
				save.setFollowUserId(userId);
				save.setStatus(1);
				save.setUpdateTime(now);			    
				kffFollowService.save(save);
				followNumIncrease = true;
		}else{
		//更新状态	
			if(existFollow.getStatus() != null && existFollow.getStatus() == 0){
				existFollow.setStatus(1);
				existFollow.setUpdateTime(now);
				kffFollowService.update(existFollow);
				followNumIncrease = true;
			}else{
				logger.warn("重复关注");
			}
		}
		
		//关注数量加1
		if(followNumIncrease){
			if(followType == 1){
			   kffProjectService.increaseFollowerNum(followedId);
			}else if(followType == 3){
			   kffUserService.increaseFansNum(followedUserId);
			}
		}
		
	}

	@Override
	public void cancelFollow(Integer userId, Integer followType, Integer followedId) throws RestServiceException {
		
		if(userId == null){
			throw new RestServiceException("用户id不能为空");
		}
		if(followedId == null){
			throw new RestServiceException("取消关注id不能为空");
		}
		if(followType == null){
			throw new RestServiceException("取消关注类型不能为空");
		}
		//1-关注项目;2-关注帖子；3-关注用户
		if(followType != 1 && followType != 2 && followType != 3){
			throw new RestServiceException("无效的取消关注类型");
		}
		KFFUser user = kffUserService.findById(userId);
		if(user == null){
			throw new RestServiceException("用户不存在"+userId);
		}
		
		Integer followedUserId = 0;
		String followedUserIcon = "";
		String followedUserName = "";
        String followedUserSignature = "";				
		//1-取消关注项目
		if(followType == KFFConstants.FOLLOW_TYPE_PROJECT){
		    KFFProject followedProject = kffProjectService.findById(followedId);
		    if(followedProject == null){
		    	throw new RestServiceException("取消关注项目不存在"+followedId);
		    }
		    followedUserId = followedProject.getSubmitUserId();
		    KFFUser projectCreator = kffUserService.findById(followedUserId);
		    if(projectCreator != null){
		    	followedUserId = projectCreator.getUserId();
		    	followedUserIcon = projectCreator.getIcon();
			    followedUserName = projectCreator.getUserName();
			    followedUserSignature = projectCreator.getUserSignature();			    
		    }
		}else if(followType == KFFConstants.FOLLOW_TYPE_POST){
			//2-取消关注帖子
			Post followedPost = kffPostService.findById(followedId);
			if(followedPost == null){
				throw new RestServiceException("取消关注帖子不存在"+followedId);
			}
	    	followedUserId = followedPost.getCreateUserId();
			followedUserIcon = followedPost.getCreateUserIcon();
		    followedUserName = followedPost.getCreateUserName();
		    followedUserSignature = followedPost.getCreateUserSignature();
		    
		}else if(followType == KFFConstants.FOLLOW_TYPE_USER){
		 //3-取消关注用户
			KFFUser followedUser = kffUserService.findById(followedId);
            if(followedUser == null){
            	throw new RestServiceException("取消关注的用户不存在"+followedId);
            }
            followedUserId = followedUser.getUserId();
			followedUserIcon = followedUser.getIcon();
		    followedUserName = followedUser.getUserName();
		    followedUserSignature = followedUser.getUserSignature();
            
		}		
		Follow existFollow = kffFollowService.findByUserIdAndFollowType(userId,followType,followedId);
		Date now = new Date();
	    boolean followNumDecrease = false;
		if(existFollow == null){
		//新增	
				Follow save = new Follow();
				save.setCreateTime(now);
				save.setFollowedId(followedId);
				save.setFollowedUserIcon(followedUserIcon);
				save.setFollowedUserId(followedUserId);
				save.setFollowedUserName(followedUserName);
				save.setFollowedUserSignature(followedUserSignature);
				save.setFollowerUserName(user.getUserName());
				save.setFollowType(followType);
				save.setFollowUserId(userId);
				save.setStatus(KFFConstants.STATUS_INACTIVE);
				save.setUpdateTime(now);			    
				kffFollowService.save(save);
				followNumDecrease = true;
		}else{
		//更新状态	
			if(existFollow.getStatus() != null && existFollow.getStatus() == KFFConstants.STATUS_ACTIVE){
				existFollow.setStatus(KFFConstants.STATUS_INACTIVE);
				existFollow.setUpdateTime(now);
				kffFollowService.update(existFollow);
				followNumDecrease = true;
			}else{
				logger.warn("重复取消关注");
			}
		}
		
		//关注数量减1
		if(followNumDecrease){
			if(followType == 1){
			   kffProjectService.decreaseFollowerNum(followedId);
			}else if(followType == 3){
			   kffUserService.decreaseFansNum(followedUserId);
			}
		}
	}

	@Override
	public void saveCollect(Integer userId, Integer postId) throws RestServiceException {
		if(userId == null){
			throw new RestServiceException("用户id不能为空");
		}
		if(postId == null){
			throw new RestServiceException("帖子id不能为空");
		}
		KFFUser user = kffUserService.findById(userId);
		if(user == null){
			throw new RestServiceException("用户不存在"+userId);
		}
		Post post = kffPostService.findById(postId);
		if(post == null){
			throw new RestServiceException("帖子不存在"+postId);
		}
	    Date now = new Date();
	    boolean collectNumIncrease = false;
		Collect existCollect = kffCollectService.findByPostId(userId,postId);
		if(existCollect == null){
		    Collect save = new Collect();
		    save.setCollectUserId(userId);
		    save.setCreateTime(now);
		    save.setPostId(postId);
		    save.setPostType(post.getPostType());
		    save.setProjectId(post.getProjectId());
		    save.setStatus(KFFConstants.STATUS_ACTIVE);
		    save.setUpdateTime(now);;
			kffCollectService.save(save);
			collectNumIncrease = true;
		}else{
			if(existCollect.getStatus() !=null && existCollect.getStatus()==KFFConstants.STATUS_INACTIVE){
				existCollect.setUpdateTime(now);
				existCollect.setStatus(KFFConstants.STATUS_ACTIVE);
				kffCollectService.update(existCollect);
				collectNumIncrease = true;
			}else{
				logger.warn("已收藏存在重复收藏");
				//throw new RestServiceException("已收藏，请勿重复收藏");
			}
		}
		//帖子收藏数加1
		if(collectNumIncrease){
			kffPostService.increaseCollectNum(postId);
		}
		
	}

	@Override
	public void cancelCollect(Integer userId, Integer postId) throws RestServiceException {
		if(userId == null){
			throw new RestServiceException(RestErrorCode.USER_ID_BLANK);
		}
		if(postId == null){
			throw new RestServiceException(RestErrorCode.POST_ID_BLANK);
		}
		KFFUser user = kffUserService.findById(userId);
		if(user == null){
			throw new RestServiceException("用户不存在"+userId);
		}
		Post post = kffPostService.findById(postId);
		if(post == null){
			throw new RestServiceException("帖子不存在"+post);
		}
	    Date now = new Date();
	    boolean collectNumDecrese = false;
		Collect existCollect = kffCollectService.findByPostId(userId,postId);
		if(existCollect == null){
		    Collect save = new Collect();
		    save.setCollectUserId(userId);
		    save.setCreateTime(now);
		    save.setPostId(postId);
		    save.setPostType(post.getPostType());
		    save.setProjectId(post.getProjectId());
		    save.setStatus(KFFConstants.STATUS_INACTIVE);
		    save.setUpdateTime(now);;
		    kffCollectService.save(save);
			collectNumDecrese = true;
		}else{
			if(existCollect.getStatus() !=null && existCollect.getStatus()==KFFConstants.STATUS_ACTIVE){
				existCollect.setUpdateTime(now);
				existCollect.setStatus(KFFConstants.STATUS_INACTIVE);
				kffCollectService.update(existCollect);
				collectNumDecrese = true;	
				
			}else{
				logger.warn("已取消收藏存在重复取消收藏");
				//throw new RestServiceException("已取消收藏存在重复取消收藏");						
			}
		}
		//帖子收藏数减1
		if(collectNumDecrese){
			kffPostService.decreaseCollectNum(postId);
		}
		
	}

	@Override
	public PageResult<PostResponse> findPageRecommendList(Integer loginUserId,PaginationQuery query) throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> postResponse = new ArrayList<>();		
		PageResult<Post> posts = kffPostService.findPage(query);
		KFFUser loginUser = null;
		if(loginUserId != null){
			loginUser = kffUserService.findById(loginUserId);
		}
		if(posts != null && CollectionUtils.isNotEmpty(posts.getRows())){
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for(Post post:posts.getRows()){
				PostResponse response = new PostResponse();
				BeanUtils.copyProperties(post, response);
				//设置post和project信息
				response.setPostShortDesc(post.getPostShortDesc());
				if(StringUtils.isNotBlank(post.getPostSmallImages())){
					try{
							List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
							response.setPostSmallImages(pfl);
					}catch(Exception e){
							logger.error("首页推荐列表解析帖子缩略图json出错:{}",e); 
					}
				}
				response.setPostTitle(post.getPostTitle());
				response.setCommentsNum(post.getCommentsNum());
				response.setCollectNum(post.getCollectNum());
				response.setPraiseNum(post.getPraiseNum());
				response.setPageviewNum(post.getPageviewNum());
				response.setDonateNum(post.getDonateNum());
				response.setCreateUserIcon(post.getCreateUserIcon());
				response.setCreateUserName(post.getCreateUserName());
				response.setCreateUserSignature(post.getCreateUserSignature());
				KFFProject project = kffProjectService.findById(post.getProjectId());
				if(project != null){
						response.setProjectChineseName(project.getProjectChineseName());
						response.setProjectCode(project.getProjectCode());
						response.setProjectEnglishName(project.getProjectEnglishName());
						response.setProjectIcon(project.getProjectIcon());
						response.setProjectSignature(project.getProjectSignature());
						response.setTotalScore(project.getTotalScore());
				}
				
				//设置项目关注状态
				if(loginUser == null){
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				}else{					
					Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT, post.getProjectId());
				    if(follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE){
				    	response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
				    }else{
				    	response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
				    }
				}
				
				postResponse.add(response);
			}
		}		
		result.setRows(postResponse);

	return result;
}
	
	@Override
	public PageResult<PostResponse> findPageFollowList(Integer loginUserId,PaginationQuery query) throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> postResponse = new ArrayList<>();		
		PageResult<Post> posts = kffPostService.findPage(query);
		KFFUser loginUser = null;
		if(loginUserId != null){
			loginUser = kffUserService.findById(loginUserId);
		}
		if(posts != null && CollectionUtils.isNotEmpty(posts.getRows())){
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for(Post post:posts.getRows()){
				PostResponse response = new PostResponse();
				BeanUtils.copyProperties(post, response);
				//设置post和project信息
				response.setPostShortDesc(post.getPostShortDesc());
				if(StringUtils.isNotBlank(post.getPostSmallImages())){
					try{
							List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
							response.setPostSmallImages(pfl);
					}catch(Exception e){
							logger.error("首页关注列表解析帖子缩略图json出错:{}",e); 
					}
				}
				response.setPostTitle(post.getPostTitle());
				response.setCommentsNum(post.getCommentsNum());
				response.setCollectNum(post.getCollectNum());
				response.setPraiseNum(post.getPraiseNum());
				response.setPageviewNum(post.getPageviewNum());
				response.setDonateNum(post.getDonateNum());
				response.setCreateUserIcon(post.getCreateUserIcon());
				response.setCreateUserName(post.getCreateUserName());
				response.setCreateUserSignature(post.getCreateUserSignature());
				KFFProject project = kffProjectService.findById(post.getProjectId());
				if(project != null){
						response.setProjectChineseName(project.getProjectChineseName());
						response.setProjectCode(project.getProjectCode());
						response.setProjectEnglishName(project.getProjectEnglishName());
						response.setProjectIcon(project.getProjectIcon());
						response.setProjectSignature(project.getProjectSignature());
						response.setTotalScore(project.getTotalScore());
				}
				
				//设置项目关注状态
				if(loginUser == null){
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				}else{					
					Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT, post.getProjectId());
				    if(follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE){
				    	response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
				    }else{
				    	response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
				    }
				}
				
				postResponse.add(response);
			}
		}		
		result.setRows(postResponse);

	return result;
	}

	@Override
	public List<ProjectResponse> findPageProjectRankList(Integer loginUserId,PaginationQuery query) throws RestServiceException {
		//登录和非登录用户区别只有关注状态按钮显示
		KFFUser loginUser = null;
		if(loginUserId != null){
			loginUser = kffUserService.findById(loginUserId);
		}
		List<ProjectResponse> result = new ArrayList<>();
		PageResult<KFFProject> projects =  kffProjectService.findPage(query);
		if(projects != null && CollectionUtils.isNotEmpty(projects.getRows())){
			for(KFFProject project:projects.getRows()){
				ProjectResponse response = new ProjectResponse();
				BeanUtils.copyProperties(project, response);
				if(loginUser == null){
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				}else{					
					Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT, project.getProjectId());
				    if(follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE){
				    	response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
				    }else{
				    	response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
				    }
				}
				
				result.add(response);
			}
		}
		return result;
	}

	@Override
	public ArticleDetailResponse findArticleDetail(Integer userId,Integer postId)
			throws RestServiceException {
		ArticleDetailResponse response = new ArticleDetailResponse();
		//登录和非登录用户区别只有关注状态按钮显示
		KFFUser loginUser = null;
		if(userId != null){
			loginUser = kffUserService.findById(userId);
		}
		if(postId == null || postId == 0){
			throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
		}
		Post post = kffPostService.findById(postId);
		if(post == null){
			throw new RestServiceException(RestErrorCode.POST_NOT_EXIST);
		}
		BeanUtils.copyProperties(post, response);
		
		Article article = kffArticleService.findByPostId(postId);
		response.setArticleContents(article == null ? "":article.getArticleContents());
		
		if(loginUser == null){
			response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		}else{					
			Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_POST, post.getPostId());
		    if(follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE){
		    	response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
		    }else{
		    	response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
		    }
		}
		//赞赏用户列表最多8个
		List<Commendation> donateUsers = new ArrayList<>();
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("postId", postId+"");
		query.addQueryData("status", "1");
		query.setPageIndex(1);
		query.setRowsPerPage(8);
		PageResult<Commendation> pages = kffCommendationService.findPage(query);
		if(pages != null && CollectionUtils.isNotEmpty(pages.getRows())){
			donateUsers = pages.getRows();
		}
		response.setCommendationList(donateUsers);
		return response;
	}

	@Override
	public List<Comments> findPageHotCommentsList(Integer userId,Integer postId, PaginationQuery query) throws RestServiceException {
		KFFUser user = null;
		if(userId != null && userId != 0){
			user = kffUserService.findById(userId);
		}	
		List<Comments> result = new ArrayList<>();
		PageResult<Comments> comments = kffCommentsService.findPage(query);
		if(comments != null && CollectionUtils.isNotEmpty(comments.getRows())){
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			childQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE+"");
			for(Comments comment:comments.getRows()){
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				query.addQueryData("parentCommentsId", comment.getCommentsId()+"");
				PageResult<Comments> childComments = kffCommentsService.findPage(query);
				if(childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())){
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}
				
				//登录用户判断点赞状态
				if(user != null){
					Praise praise = kffPraiseService.findByPostId(userId, comment.getCommentsId());
					if(praise != null){
						finalComment.setPraiseStatus(praise.getStatus());
					}
				}else{
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				result.add(finalComment);
			}
		}
		return result;
	}

	@Override
	public List<Comments> findPageNewestComments(Integer userId,Integer postId, PaginationQuery query) throws RestServiceException {
		KFFUser user = null;
		if(userId != null && userId != 0){
			user = kffUserService.findById(userId);
		}	
		List<Comments> result = new ArrayList<>();
		PageResult<Comments> comments = kffCommentsService.findPage(query);
		if(comments != null && CollectionUtils.isNotEmpty(comments.getRows())){
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			childQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE+"");
			for(Comments comment:comments.getRows()){
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				childQuery.addQueryData("parentCommentsId", comment.getCommentsId()+"");
				PageResult<Comments> childComments = kffCommentsService.findPage(childQuery);
				if(childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())){
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}
				
				//登录用户判断点赞状态
				if(user != null){
					Praise praise = kffPraiseService.findByPostId(userId, comment.getCommentsId());
					if(praise != null){
						finalComment.setPraiseStatus(praise.getStatus());
					}
				}else{
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				result.add(finalComment);
			}
		}
		return result;
	}

	@Override
	public List<Comments> findAllChildCommentsList(Integer userId, Integer commentsId, PaginationQuery query)
			throws RestServiceException {
		KFFUser user = null;
		if(userId != null && userId != 0){
			user = kffUserService.findById(userId);
		}	
		List<Comments> result = new ArrayList<>();
		PageResult<Comments> comments = kffCommentsService.findPage(query);
		if(comments != null && CollectionUtils.isNotEmpty(comments.getRows())){
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			for(Comments comment:comments.getRows()){
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				childQuery.addQueryData("parentCommentsId", comment.getCommentsId()+"");
				PageResult<Comments> childComments = kffCommentsService.findPage(childQuery);
				if(childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())){
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}				
				//登录用户判断点赞状态
				if(user != null){
					Praise praise = kffPraiseService.findByPostId(userId, comment.getCommentsId());
					if(praise != null){
						finalComment.setPraiseStatus(praise.getStatus());
					}
				}else{
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				result.add(finalComment);
			}
		}
		return result;
	}

	@Override
	public ArticleDetailResponse findDiscussDetail(Integer userId, Integer postId) throws RestServiceException {
		ArticleDetailResponse response = new ArticleDetailResponse();
		//登录和非登录用户区别只有关注状态按钮显示
		KFFUser loginUser = null;
		if(userId != null){
			loginUser = kffUserService.findById(userId);
		}
		if(postId == null || postId == 0){
			throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
		}
		Post post = kffPostService.findById(postId);
		if(post == null){
			throw new RestServiceException(RestErrorCode.POST_NOT_EXIST);
		}
		BeanUtils.copyProperties(post, response);
		
		Discuss discuss = kffDiscussService.findByPostId(postId);
		response.setArticleContents(discuss == null ? "":discuss.getDisscussContents());
		
		
		if(loginUser == null){
			response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		}else{					
			Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_POST, post.getPostId());
		    if(follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE){
		    	response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
		    }else{
		    	response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
		    }
		}
		//赞赏用户列表最多8个
		List<Commendation> donateUsers = new ArrayList<>();
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("postId", postId+"");
		query.addQueryData("status", "1");
		query.setPageIndex(1);
		query.setRowsPerPage(8);
		PageResult<Commendation> pages = kffCommendationService.findPage(query);
		if(pages != null && CollectionUtils.isNotEmpty(pages.getRows())){
			donateUsers = pages.getRows();
		}
		response.setCommendationList(donateUsers);
		return response;
	}

	@Override
	public List<Comments> findAllDiscussCommentsList(Integer userId, Integer postId) throws RestServiceException {
		KFFUser user = null;
		if(userId != null && userId != 0){
			user = kffUserService.findById(userId);
		}	
		List<Comments> result = new ArrayList<>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("postId", postId);
		map.put("status", KFFConstants.STATUS_ACTIVE);
		List<Comments> comments = kffCommentsService.findAllCommentsByWhere(map);
		if( CollectionUtils.isNotEmpty(comments)){
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			childQuery.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS+"");
			for(Comments comment:comments){
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				childQuery.addQueryData("parentCommentsId", comment.getCommentsId()+"");
				PageResult<Comments> childComments = kffCommentsService.findPage(childQuery);
				if(childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())){
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}				
				//登录用户判断点赞状态
				if(user != null){
					Praise praise = kffPraiseService.findByPostId(userId, comment.getCommentsId());
					if(praise != null){
						finalComment.setPraiseStatus(praise.getStatus());
					}
				}else{
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				result.add(finalComment);
			}
		}
		return result;
	}


	
    
}
