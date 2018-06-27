package com.tzg.rmi.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.TOC;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.common.base.Objects;
import com.tzg.common.constants.KFFConstants;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.service.kff.ArticleService;
import com.tzg.common.service.kff.AuthenticationService;
import com.tzg.common.service.kff.AwardPortService;
import com.tzg.common.service.kff.CoinPropertyService;
import com.tzg.common.service.kff.CollectService;
import com.tzg.common.service.kff.CommendationService;
import com.tzg.common.service.kff.CommentsService;
import com.tzg.common.service.kff.DareasService;
import com.tzg.common.service.kff.DevaluationModelDetailService;
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
import com.tzg.common.service.kff.ProjectevastatService;
import com.tzg.common.service.kff.QfIndexService;
import com.tzg.common.service.kff.SuggestService;
import com.tzg.common.service.kff.TokenawardService;
import com.tzg.common.service.kff.TokenrecordsService;
import com.tzg.common.service.kff.UserCardService;
import com.tzg.common.service.kff.UserInvationService;
import com.tzg.common.service.kff.UserService;
import com.tzg.common.service.kff.UserWalletService;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.Create2Code;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.DelHtmlAll;
import com.tzg.common.utils.DownImagesUtile;
import com.tzg.common.utils.DownImgGoodUtil;
import com.tzg.common.utils.FileUtils;
import com.tzg.common.utils.GetImgUrl;
import com.tzg.common.utils.H5AgainDeltagsUtil;
import com.tzg.common.utils.HexUtil;
import com.tzg.common.utils.DownImgGoodUtil;
import com.tzg.common.utils.QiniuUtil;
import com.tzg.common.utils.sendTelephone;
import com.tzg.common.utils.rest.AliyunConstant;
import com.tzg.common.utils.Numbers;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.ToRemoveHtml;
import com.tzg.common.utils.WorkHtmlRegexpUtil;
import com.tzg.common.utils.rest.AliyunConstant;
import com.tzg.common.utils.web.HttpUtil;
import com.tzg.common.zookeeper.ZKClient;
import com.tzg.entitys.kff.article.Article;
import com.tzg.entitys.kff.article.ArticleDetailResponse;
import com.tzg.entitys.kff.article.ArticleDetailShareResponse;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.authentication.Authentication;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.collect.Collect;
import com.tzg.entitys.kff.collect.CollectPostResponse;
import com.tzg.entitys.kff.commendation.Commendation;
import com.tzg.entitys.kff.commendation.CommendationRequest;
import com.tzg.entitys.kff.comments.CommentShareFloot;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.entitys.kff.comments.CommentsShareRequest;
import com.tzg.entitys.kff.dareas.Dareas;
import com.tzg.entitys.kff.devaluationModel.DevaluationModel;
import com.tzg.entitys.kff.devaluationModel.DevaluationModelRequest;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.discuss.Discuss;
import com.tzg.entitys.kff.discuss.DiscussDetailResponse;
import com.tzg.entitys.kff.discuss.DiscussRequest;
import com.tzg.entitys.kff.discuss.DiscussShare;
import com.tzg.entitys.kff.dprojectType.DprojectType;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.evaluation.EvaDtail;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.entitys.kff.evaluation.ProjectEvaluationDetailShareResponse;
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
import com.tzg.entitys.kff.projectevastat.Projectevastat;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.entitys.kff.suggest.Suggest;
import com.tzg.entitys.kff.suggest.SuggestRequest;
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.tokenaward.TokenawardReturn;
import com.tzg.entitys.kff.tokenaward.TokenawardReturn;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserHomeResponse;
import com.tzg.entitys.kff.userInvation.UserInvation;
import com.tzg.entitys.kff.usercard.UserCard;
import com.tzg.entitys.kff.userwallet.KFFUserWallet;
import com.tzg.entitys.kff.userwallet.KFFUserWalletMapper;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.entitys.photo.PhotoParams;
import com.tzg.rest.constant.KFFRestConstants;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.SmsSendRmiService;

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
	private DevaluationModelDetailService kffDevaluationModelDetailService;
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
	private ProjectevastatService kffProjectevastatService;
	@Autowired
	private UserCardService userCardService;
	@Autowired
	private AuthenticationService authenticationService;
	@Autowired
	private SmsSendRmiService smsSendRmiService;
	@Autowired
	private UserInvationService userInvationService;
	@Autowired
	private QfIndexService qfIndexService;
	@Autowired
	private CoinPropertyService coinPropertyService;
	@Autowired
	private TokenawardService tokenawardService;
	@Autowired
	private UserWalletService userWalletService;
	@Autowired
	private TokenawardService kffTokenawardService;
	@Autowired
	private AwardPortService awardPortService;
	@Autowired
	private KFFUserWalletMapper kFFUserWalletMapper;
	@Autowired
	private ZKClient zkClient;
	@Autowired
	private SystemParamService systemParamService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Value("#{paramConfig['registerUrl']}")
	private String contentself;

	@Value("#{paramConfig['picUrl']}")
	private String picServiceUrl;

	@Value("#{paramConfig['SMS_APP_KEY']}")
	private String smsAppkey;

	@Value("#{paramConfig['SMS_SECRET']}")
	private String smsSecret;

	@Value("#{paramConfig['SMS_TYPE']}")
	private String smsType;
	@Value("#{paramConfig['SMS_FREE_SIGN_NAME']}")
	private String smsFreeSignName;

	@Value("#{paramConfig['SMS_REGISTER_TEMPLATE_CODE']}")
	private String smsRegisterTemplateCode;

	@Value("#{paramConfig['SMS_LOGIN_TEMPLATE_CODE']}")
	private String smsLoginTemplateCode;

	@Value("#{paramConfig['SMS_FORGETPASDWORD_TEMPLATE_CODE']}")
	private String smsForgetpasdwoedTemplateCode;

	@Value("#{paramConfig['ipPicUrl']}")
	private String ipPicUrl;

	@Override
	public KFFUser registerRest(RegisterRequest registerRequest) {

		KFFUser user = kffUserService.registerRest(registerRequest);
		//

		//
		//
		this.setUserCardAuthentication(user.getUserId(), user.getMobile());

		//
		this.saveAuthenticationByUseId(user.getUserId());
		awardPortService.registerAward(user.getUserId());
		return user;
	}

	@Override
	public boolean verifyLoginaccount(String key, String value) {
		System.out.println("============================");
		return kffUserService.verifyLoginaccount(key, value);

	}

	@Override
	public KFFUser login(String loginName, String password) {

		return kffUserService.login(loginName, password);
	}

	@Override
	public Mobileversionupdate selectLastVersionByType(Integer platformType) throws RestServiceException {
		return kffMobileversionupdateService.findById(platformType);
	}

	@Override
	public void submitSuggest(SuggestRequest suggestRequest) throws RestServiceException {
		if (StringUtils.isBlank(suggestRequest.getContent())) {
			throw new RestServiceException("请填写建议内容");
		} else if (suggestRequest.getContent().length() > KFFConstants.MAX_NORMAL_CONTENT_LENGTH) {
			throw new RestServiceException("建议内容请勿超过" + KFFConstants.MAX_NORMAL_CONTENT_LENGTH + "字");
		}

		if (StringUtils.isBlank(suggestRequest.getContactInfo())) {
			throw new RestServiceException("请填写联系方式");
		} else if (suggestRequest.getContactInfo().length() > KFFConstants.MAX_NORMAL_TITLE_LENGTH) {
			throw new RestServiceException("联系方式请勿超过" + KFFConstants.MAX_NORMAL_TITLE_LENGTH + "字");
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
	public KFFUser findUserByPhoneNumber(String phoneNumber) throws RestServiceException {

		return kffUserService.findUserByPhoneNumber(phoneNumber);
	}

	@Override
	public boolean updateUser(KFFUser account) throws RestServiceException {

		return kffUserService.update(account);
	}

	@Override
	public KFFUser findUserById(Integer userId) throws RestServiceException {
		return kffUserService.findById(userId);
	}

	@Override
	public List<Dareas> getAreaListByCode(String areacode) throws RestServiceException {
		List<Dareas> areas = new ArrayList<>();
		if (StringUtils.isBlank(areacode)) {
			areas = kffDareasService.findProvinceAreas();
		} else {
			areas = kffDareasService.findSubAreasByCode(areacode);
		}
		return areas;
	}

	@Override
	public PageResult<Tokenrecords> findPageMyTokenRecords(PaginationQuery query) throws RestServiceException {

		return kffTokenrecordsService.findPage(query);
	}

	@Override
	public PageResult<CollectPostResponse> findPageMyCollectRecords(PaginationQuery query) throws RestServiceException {
		PageResult<CollectPostResponse> result = new PageResult<CollectPostResponse>();
		List<CollectPostResponse> postResponse = new ArrayList<>();
		PageResult<Collect> collects = kffCollectService.findPage(query);
		if (collects != null && CollectionUtils.isNotEmpty(collects.getRows())) {
			List<Integer> projectIds = new ArrayList<>();
			for (Collect collect : collects.getRows()) {
				projectIds.add(collect.getProjectId());
			}
			if (query.getQueryData().get("collectUserId") == null) {
				throw new RestServiceException("用户ID不能为空");
			}
			Integer userId = Integer.parseInt(query.getQueryData().get("collectUserId").toString());
			List<Follow> followedProjects = kffFollowService.findFollowedProjects(userId, projectIds);
			Set<Integer> followedProjectIds = new HashSet<>();
			if (CollectionUtils.isNotEmpty(followedProjects)) {
				for (Follow follow : followedProjects) {
					followedProjectIds.add(follow.getFollowedId());
				}
			}
			result.setCurPageNum(collects.getCurPageNum());
			result.setPageSize(collects.getPageSize());
			result.setQueryParameters(collects.getQueryParameters());
			result.setRowCount(collects.getRowCount());
			result.setRowsPerPage(collects.getRowsPerPage());
			for (Collect collect : collects.getRows()) {
				CollectPostResponse response = new CollectPostResponse();
				BeanUtils.copyProperties(collect, response);
				//
				Post post = kffPostService.findById(collect.getPostId());
				if (post != null) {
					response.setPostShortDesc(post.getPostShortDesc());
					if (StringUtils.isNotBlank(post.getPostSmallImages())) {
						try {
							List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
							response.setPostSmallImagesList(pfl);
						} catch (Exception e) {
							logger.error("我的收藏列表解析帖子缩略图json出错:{}", e);
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

					if (followedProjectIds != null && followedProjectIds.contains(post.getProjectId())) {
						response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
					}
					// response.setTotalScore(post.get);
					KFFProject project = kffProjectService.findById(post.getProjectId());
					if (project != null) {
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
	public PageResult<FollowResponse> findPageMyFollow(PaginationQuery query) throws RestServiceException {

		PageResult<FollowResponse> result = new PageResult<FollowResponse>();
		List<FollowResponse> followResponses = new ArrayList<>();
		PageResult<Follow> follows = kffFollowService.findPage(query);
		if (follows != null && CollectionUtils.isNotEmpty(follows.getRows())) {
			result.setCurPageNum(follows.getCurPageNum());
			result.setPageSize(follows.getPageSize());
			result.setQueryParameters(follows.getQueryParameters());
			result.setRowCount(follows.getRowCount());
			result.setRowsPerPage(follows.getRowsPerPage());
			for (Follow follow : follows.getRows()) {
				FollowResponse response = new FollowResponse();
				BeanUtils.copyProperties(follow, response);
				//
				// /*关注类型：1-关注项目;2-关注帖子；3-关注用户
				if (follow.getFollowType() != null && follow.getFollowType() == 2) {
					Post post = kffPostService.findById(follow.getFollowedId());
					if (post != null) {
						response.setPostShortDesc(post.getPostShortDesc());
						if (StringUtils.isNotBlank(post.getPostSmallImages())) {
							try {
								List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
								response.setPostSmallImages(pfl);
							} catch (Exception e) {
								logger.error("我的关注列表解析帖子缩略图json出错:{}", e);
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
						// response.setTotalScore(post.get);
						KFFProject project = kffProjectService.findById(post.getProjectId());
						if (project != null) {
							response.setProjectChineseName(project.getProjectChineseName());
							response.setProjectCode(project.getProjectCode());
							response.setProjectEnglishName(project.getProjectEnglishName());
							response.setProjectIcon(project.getProjectIcon());
							response.setProjectSignature(project.getProjectSignature());
							response.setTotalScore(project.getTotalScore());
						}
					}

				} else if (follow.getFollowType() != null && follow.getFollowType() == 3) {
					// 被关注用户信息在follow表中已包含
				} else if (follow.getFollowType() != null && follow.getFollowType() == 1) {
					// 项目 就是话题
					response.setFollowedProjectId(follow.getFollowedId());
					KFFProject project = kffProjectService.findById(follow.getFollowedId());
					if (project != null) {
						response.setProjectChineseName(project.getProjectChineseName());
						response.setProjectCode(project.getProjectCode());
						response.setProjectEnglishName(project.getProjectEnglishName());
						response.setProjectIcon(project.getProjectIcon());
						response.setProjectSignature(project.getProjectSignature());
						response.setTotalScore(project.getTotalScore());
					}
				}
				followResponses.add(response);
			}
			result.setRows(followResponses);
		}

		return result;
	}

	@Override
	public KFFMessage getMessageDetail(Integer userId, Integer messageId) throws RestServiceException {

		if (messageId == null) {
			throw new RestServiceException("messageId必填");
		}
		if (!messageId.equals(0)) {
			KFFMessage result = kffMessageService.findById(messageId);
			if (result == null) {
				throw new RestServiceException("消息不存在");
			}
			if (!result.getUserId().equals(userId)) {
				throw new RestServiceException("不能查看他人消息");
			}
			if (result.getSenderUserId() != null) {
				KFFUser user = kffUserService.findById(result.getSenderUserId());
				if (user != null) {
					result.setSenderUserIcon(user.getIcon());
				}
			}
			result.setState(2);
			result.setUpdateTime(new Date());
			kffMessageService.update(result);
			return result;
		} else {
			kffMessageService.updateAllMessageRead(userId);
			return null;
		}
	}

	@Override
	public PageResult<KFFMessage> findPageMyMessages(PaginationQuery query) {
		PageResult<KFFMessage> result = null;
		result = kffMessageService.findPage(query);
		if (result != null && CollectionUtils.isNotEmpty(result.getRows())) {
			for (KFFMessage message : result.getRows()) {
				if (message.getSenderUserId() != null) {
					KFFUser user = kffUserService.findById(message.getSenderUserId());
					if (user != null) {
						message.setSenderUserIcon(user.getIcon());
					}
				}
			}
		}
		return result;
	}

	@Override
	public void deleteMessage(Integer userId, Integer messageId) throws RestServiceException {
		if (userId == null) {
			throw new RestServiceException("用户ID不能为空");
		}
		if (messageId == null) {
			throw new RestServiceException("消息ID不能为空");
		}
		// 删除单个
		if (!messageId.equals(0)) {
			KFFMessage result = kffMessageService.findById(messageId);
			if (result == null) {
				throw new RestServiceException("消息不存在");
			}
			if (!result.getUserId().equals(userId)) {
				throw new RestServiceException("不能删除他人消息");
			}
			result.setState(2); // 已读
			result.setStatus(0); // 删除
			result.setUpdateTime(new Date());
			kffMessageService.update(result);
		} else {
			// 删除所有
			kffMessageService.deleteAllMessages(userId);
		}

	}

	@Override
	public KFFProject submitProject(SubmitKFFProjectRequest projectRequest) throws RestServiceException {
		Date now = new Date();
		KFFProject project = new KFFProject();
		BeanUtils.copyProperties(projectRequest, project);
		if (StringUtils.isBlank(project.getProjectCode())) {
			throw new RestServiceException("代币名称不能为空");
		}
		if (StringUtils.isBlank(project.getProjectEnglishName())) {
			throw new RestServiceException("项目英文名称不能为空");
		}
		if (StringUtils.isBlank(project.getWebsiteUrl())) {
			throw new RestServiceException("官网地址不能为空");
		}
		if (StringUtils.isBlank(project.getWhitepaperUrl())) {
			throw new RestServiceException("白皮书地址不能为空");
		}
		if (StringUtils.isBlank(project.getProjectTypeName())) {
			throw new RestServiceException("项目类型名称不能为空");
		}
		if (StringUtils.isBlank(project.getSubmitUserContactInfo())) {
			throw new RestServiceException("联系信息不能为空");
		}
		if (StringUtils.isBlank(project.getSubmitReason())) {
			throw new RestServiceException("推荐理由不能为空");
		}
		if (StringUtils.isBlank(project.getProjectDesc())) {
			throw new RestServiceException("项目描述不能为空");
		}
		if (project.getWhitepaperUrl().length() > 255) {
			throw new RestServiceException("白皮书地址长度超限");
		}
		if (project.getSubmitUserContactInfo().length() > 30) {
			throw new RestServiceException("联系信息长度超限");
		}
		if (project.getSubmitReason().length() > 3000) {
			throw new RestServiceException("提交理由信息长度超限");
		}
		if (project.getProjectDesc().length() > 3000) {
			throw new RestServiceException("项目描述信息长度超限");
		}
		if (Objects.equal(1, projectRequest.getListed()) && StringUtils.isBlank(projectRequest.getIssueDateStr())) {
			throw new RestServiceException("已上市项目请填写上市时间");
		}
		if (StringUtils.isBlank(project.getProjectSignature())) {
			throw new RestServiceException("项目标题不能为空");
		}
		if (project.getProjectSignature().length() > 30) {
			throw new RestServiceException("项目标题字数大于30字");
		}

		project.setCreateTime(now);
		project.setUpdateTime(now);
		project.setState(1);// 1；待审核；2-审核通过；3-拒绝
		project.setStatus(1);
		// 上市时间默认提交时间
		// project.setIssueDate(now);
		if (StringUtils.isNotBlank(projectRequest.getIssueDateStr())) {
			project.setIssueDate(DateUtil.getDate(projectRequest.getIssueDateStr(), "yyyy-MM-dd"));
		}
		kffProjectService.save(project);
		return null;
	}

	@Override
	public List<DprojectType> findAllProjectTypes() throws RestServiceException {

		return kffDprojectTypeService.findAllProjectTypes();
	}

	@Override
	public List<ProjectResponse> findProjectByCode(int sortType, Integer userId, String projectCode) throws RestServiceException {
		// if(StringUtils.isBlank(projectCode)){
		// throw new RestServiceException("查询条件:项目关键字不能为空");
		// }
		List<KFFProject> projects = new ArrayList<>();
		List<ProjectResponse> result = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("state", "2");
		map.put("status", "1");
		if (StringUtils.isNotBlank(projectCode)) {
			map.put("projectCode", projectCode);
		}
		if (sortType == 1) {
			map.put("sortField", "follower_num");
		}
		// else if(sortType == 2){
		// map.put("sortField", "project_code");
		// }
		projects = kffProjectService.findProjectByCode(map);
		if (CollectionUtils.isNotEmpty(projects)) {

			Set<Integer> followedProjectIds = new HashSet<Integer>();
			// 登录用户查关注项目列表
			if (userId != null && userId != 0) {
				PaginationQuery query = new PaginationQuery();
				query.addQueryData("followUserId", userId + "");
				query.addQueryData("followType", "1"); // 关注类型：1-关注项目;2-关注帖子；3-关注用户
				query.addQueryData("status", "1");
				query.setPageIndex(1);
				query.setRowsPerPage(1000);
				// query.addQueryData("followedProjectId",
				// project.getProjectId()+"");
				PageResult<Follow> follows = kffFollowService.findPage(query);

				if (follows != null && CollectionUtils.isNotEmpty(follows.getRows())) {
					for (Follow follow : follows.getRows()) {
						followedProjectIds.add(follow.getFollowedId());
					}
				}
			}
			for (KFFProject project : projects) {
				ProjectResponse response = new ProjectResponse();
				BeanUtils.copyProperties(project, response);
				// 登录用户
				if (userId != null && userId != 0) {
					if (followedProjectIds != null && followedProjectIds.contains(project.getProjectId())) {
						response.setFollowStatus(1);
					}
				}
				result.add(response);
			}

		}

		return result;
	}

	@Override
	public Map<String, Object> saveCommendation(CommendationRequest commendationRequest) throws RestServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (commendationRequest == null) {
			throw new RestServiceException("commendationRequest can't be null");
		}
		if (commendationRequest.getPostId() == null) {
			throw new RestServiceException("帖子ID不能为空");
		}
		if (commendationRequest.getProjectId() == null) {
			throw new RestServiceException("项目ID不能为空");
		}
		Post post = kffPostService.findById(commendationRequest.getPostId());
		if (post == null) {
			throw new RestServiceException("帖子不存在");
		}

		if (commendationRequest.getSendUserId() == null) {
			throw new RestServiceException("发送用户不能为空");
		}
		if (commendationRequest.getAmount() == null || commendationRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RestServiceException("打赏金额不合法");
		}
		if (commendationRequest.getReceiveUserId() == null) {
			throw new RestServiceException("接收用户不能为空");
		}
		KFFUser receiveUser = kffUserService.findById(commendationRequest.getReceiveUserId());
		if (receiveUser == null) {
			throw new RestServiceException("接收用户不存在");
		}
		KFFUser sendUser = kffUserService.findById(commendationRequest.getSendUserId());

		if (sendUser == null) {
			throw new RestServiceException("发送用户不存在");
		}
		if (sendUser.getKffCoinNum().compareTo(commendationRequest.getAmount()) < 0) {
			throw new RestServiceException("账户余额不足:捐赠数量" + commendationRequest.getAmount().intValue() + "余额数量" + sendUser.getKffCoinNum().intValue());
		}

		// 帖子捐赠人数加+1
		kffPostService.updateDonateNum(post.getPostId());

		commendationRequest.setSendUserIcon(sendUser.getIcon());
		commendationRequest.setPostType(post.getPostType());

		Commendation commendation = new Commendation();
		Date now = new Date();
		BeanUtils.copyProperties(commendationRequest, commendation);
		commendation.setStatus(1);
		commendation.setCreateTime(now);
		commendation.setUpdateTime(now);
		kffCommendationService.save(commendation);

		Integer randomBIZCode = Integer.valueOf(RandomUtil.produceNumber(3));
		// 发送方扣减，接收方增加
		kffUserService.updateUserKFFCoinNum(sendUser.getUserId(), commendationRequest.getAmount().multiply(new BigDecimal(-1)));
		kffUserService.updateUserKFFCoinNum(receiveUser.getUserId(), commendationRequest.getAmount());
		// 生成流水记录
		Tokenrecords sendTokenRecords = new Tokenrecords();
		sendTokenRecords.setTradeType(2); // 交易类型:1-收入；2-支出
		sendTokenRecords.setAmount(commendationRequest.getAmount());
		sendTokenRecords.setBalance(sendUser.getKffCoinNum());
		sendTokenRecords.setCreateTime(now);
		// 交易描述:10-充值11-评测奖励12-讨论奖励13-文章奖励14-榜单奖励15-用户赞赏16-注册奖励17-点赞奖励18-邀请好友奖励21-提现22-赞赏他人'
		sendTokenRecords.setFunctionDesc("赞赏他人");
		sendTokenRecords.setFunctionType(22);
		sendTokenRecords.setMemo("捐赠他人");
		sendTokenRecords.setStatus(1);
		// 交易流水号=交易类型（2位） 交易时间年月日（8位） 业务记录ID（10位）
		sendTokenRecords.setTradeCode(Numbers.getSerialNumber(sendTokenRecords.getTradeType(), randomBIZCode));
		sendTokenRecords.setTradeDate(now);
		sendTokenRecords.setUpdateTime(now);
		sendTokenRecords.setUserId(sendUser.getUserId());
		sendTokenRecords.setRewardGrantType(1);
		kffTokenrecordsService.save(sendTokenRecords);
		Tokenrecords receiveTokenRecords = new Tokenrecords();
		receiveTokenRecords.setTradeType(1); // 交易类型:1-收入；2-支出
		receiveTokenRecords.setAmount(commendationRequest.getAmount());
		receiveTokenRecords.setBalance(receiveUser.getKffCoinNum());
		receiveTokenRecords.setCreateTime(now);
		// 交易描述:10-充值11-评测奖励12-讨论奖励13-文章奖励14-榜单奖励15-用户赞赏16-注册奖励17-点赞奖励18-邀请好友奖励21-提现22-赞赏他人'
		receiveTokenRecords.setFunctionDesc("用户赞赏");
		receiveTokenRecords.setFunctionType(15);
		receiveTokenRecords.setMemo("他人捐赠");
		receiveTokenRecords.setStatus(1);
		// 交易流水号=交易类型（2位） 交易时间年月日（8位） 业务记录ID（10位）
		receiveTokenRecords.setTradeCode(Numbers.getSerialNumber(sendTokenRecords.getTradeType(), randomBIZCode));
		receiveTokenRecords.setTradeDate(now);
		receiveTokenRecords.setUpdateTime(now);
		receiveTokenRecords.setUserId(receiveUser.getUserId());
		receiveTokenRecords.setRewardGrantType(1);
		kffTokenrecordsService.save(receiveTokenRecords);
		/**
		 * 新加资产表同步
		 */
		CoinProperty coinSendUser = coinPropertyService.findByUserId(sendUser.getUserId()); // 发送打赏的人
		CoinProperty coinReceiveUser = coinPropertyService.findByUserId(receiveUser.getUserId()); // 接受打赏的人

		Double coinLockSend = coinSendUser.getCoinLock();
		coinLockSend = new BigDecimal(Double.toString(coinLockSend)).subtract(commendationRequest.getAmount()).doubleValue();
		coinSendUser.setCoinLock(coinLockSend);
		coinPropertyService.update(coinSendUser);

		Double coinLockReceive = coinReceiveUser.getCoinLock();
		coinLockReceive = new BigDecimal(Double.toString(coinLockReceive)).add(commendationRequest.getAmount()).doubleValue();
		coinReceiveUser.setCoinLock(coinLockReceive);
		coinPropertyService.update(coinReceiveUser);

		// 被赞赏用户消息
		KFFMessage message = new KFFMessage();
		message.setContent(sendUser.getUserName() + "赞赏了您" + commendationRequest.getAmount() + "个token");
		message.setCreateTime(now);
		message.setJumpInfo("");
		message.setState(KFFConstants.MESSAGE_STATE_UNREAD);
		message.setStatus(KFFConstants.STATUS_ACTIVE);
		message.setTitle("赞赏");
		message.setType(KFFConstants.MESSAGE_TYPE_COMMENDATION);
		message.setUpdateTime(now);
		message.setUserId(receiveUser.getUserId());
		message.setSenderUserId(sendUser.getUserId());
		kffMessageService.save(message);

		// 总捐赠金额
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("postId", commendationRequest.getPostId() + "");
		totalMap.put("status", "1");
		BigDecimal commendationNum = kffCommendationService.findCommendationNum(totalMap);
		resultMap.put("commendationNum", commendationNum);
		// 捐赠人数
		Post latestPost = kffPostService.findById(commendationRequest.getPostId());
		if (latestPost != null) {
			resultMap.put("donateNum", latestPost.getDonateNum());
		} else {
			resultMap.put("donateNum", 0);
		}
		return resultMap;
	}

	@Override
	public KFFUserHomeResponse findUserHomeByUserId(Integer loginUserId, Integer userId) throws RestServiceException {
		KFFUserHomeResponse result = new KFFUserHomeResponse();
		if (loginUserId == null || loginUserId <= 0) {
			throw new RestServiceException("登录用户ID不能为空");
		}
		KFFUser user = null;
		if (userId == null || userId <= 0) {
			// 查看用户本人
			user = kffUserService.findById(loginUserId);
			if (user == null) {
				throw new RestServiceException("用户不存在" + loginUserId);
			}
			result.setHomePageTitle("我的主页");
			result.setShowFollow(0);

		} else {
			// 查看他人
			user = kffUserService.findById(userId);
			if (user == null) {
				throw new RestServiceException("用户不存在" + userId);
			}
			result.setHomePageTitle(user.getUserName() + "的主页");
			// 默认设置显示 关注
			result.setShowFollow(1);
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("followUserId", loginUserId + "");
			query.addQueryData("followedUserId", userId + "");
			query.addQueryData("followType", "3"); // 关注类型：1-关注项目;2-关注帖子；3-关注用户
			query.addQueryData("status", "1");
			query.setPageIndex(1);
			query.setRowsPerPage(10);
			PageResult<Follow> follows = kffFollowService.findPage(query);
			if (follows != null && CollectionUtils.isNotEmpty(follows.getRows())) {
				// 默认设置显示 取消关注
				result.setShowFollow(2);
			}

		}
		BeanUtils.copyProperties(user, result);
		result.setTotalPostNum(result.getArticleNum() + result.getDiscussNum() + result.getEvaluationNum());

		return result;
	}

	@Override
	public PageResult<EvaluationDetailResponse> findPageEvaluationList(PaginationQuery query) throws RestServiceException {
		PageResult<EvaluationDetailResponse> result = new PageResult<EvaluationDetailResponse>();
		List<EvaluationDetailResponse> respones = new ArrayList<>();
		PageResult<Post> posts = kffPostService.findPage(query);
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for (Post post : posts.getRows()) {
				EvaluationDetailResponse response = new EvaluationDetailResponse();
				BeanUtils.copyProperties(post, response);
				if (StringUtils.isNotBlank(post.getPostSmallImages())) {
					try {
						List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
						response.setPostSmallImagesList(pfl);
					} catch (Exception e) {
						logger.error("评测列表解析帖子缩略图json出错:{}", e);
					}
				}

				Evaluation eva = kffEvaluationService.findByPostId(post.getPostId());
				if (eva == null) {
					logger.error("evaluation not find,postId:" + post.getPostId());
				} else {
					// 过滤掉简单评测
					// https://www.tapd.cn/21950911/bugtrace/bugs/view?bug_id=1121950911001000461
					/*if (Objects.equal(1, eva.getModelType())) {
						continue;
					}*/
					response.setProfessionalEvaDetail(eva.getProfessionalEvaDetail());
					response.setEvaluationTags(eva.getEvaluationTags());
					response.setEvaluationId(eva.getEvaluationId());
					response.setEvauationContent(eva.getEvauationContent());
					response.setTotalScore(eva.getTotalScore());
				}
				respones.add(response);
			}
			result.setRows(respones);
		}
		return result;
	}

	@Override
	public PageResult<PostResponse> findPageDisscussList(PaginationQuery query) throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> respones = new ArrayList<>();
		PageResult<Post> posts = kffPostService.findPage(query);
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for (Post post : posts.getRows()) {
				PostResponse response = new PostResponse();
				BeanUtils.copyProperties(post, response);
				if (StringUtils.isNotBlank(post.getPostSmallImages())) {
					try {
						List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
						response.setPostSmallImagesList(pfl);
					} catch (Exception e) {
						logger.error("讨论列表解析帖子缩略图json出错:{}", e);
					}
				}
				Discuss discuss = kffDiscussService.findByPostId(post.getPostId());
				if (discuss != null) {
					response.setDiscussId(discuss.getDiscussId());
					response.setDisscussContents(discuss.getDisscussContents());
					response.setTagInfos(discuss.getTagInfos());
				}
				respones.add(response);
			}
			result.setRows(respones);
		}
		return result;
	}

	@Override
	public PageResult<PostResponse> findPageArticleList(PaginationQuery query) throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> respones = new ArrayList<>();
		PageResult<Post> posts = kffPostService.findPage(query);
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());

			for (Post post : posts.getRows()) {
				PostResponse response = new PostResponse();
				BeanUtils.copyProperties(post, response);
				if (StringUtils.isNotBlank(post.getPostSmallImages())) {
					try {
						List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
						response.setPostSmallImagesList(pfl);
					} catch (Exception e) {
						logger.error("文章列表解析帖子缩略图json出错:{}", e);
					}
				}
				respones.add(response);
			}
			result.setRows(respones);
		}
		return result;
	}

	@Override
	public ProjectResponse findProjectById(Integer userId, Integer projectId) throws RestServiceException {
		ProjectResponse response = new ProjectResponse();
		if (userId == null) {
			throw new RestServiceException("用户ID不能为空");
		}
		if (projectId == null) {
			throw new RestServiceException("项目ID不能为空");
		}
		KFFProject project = kffProjectService.findById(projectId);
		if (project == null) {
			throw new RestServiceException("项目不存在" + projectId);
		}

		BeanUtils.copyProperties(project, response);

		PaginationQuery query = new PaginationQuery();
		query.addQueryData("followUserId", userId + "");
		query.addQueryData("followType", "1"); // 关注类型：1-关注项目;2-关注帖子；3-关注用户
		query.addQueryData("status", "1");
		query.addQueryData("followedId", projectId + "");
		query.setPageIndex(1);
		query.setRowsPerPage(1);
		PageResult<Follow> follows = kffFollowService.findPage(query);
		if (follows != null && CollectionUtils.isNotEmpty(follows.getRows())) {
			response.setFollowStatus(1);
		} else {
			response.setFollowStatus(0);
		}

		// 关注的用户
		PaginationQuery userquery = new PaginationQuery();
		userquery.addQueryData("followUserId", userId + "");
		userquery.addQueryData("followType", "3"); // 关注类型：1-关注项目;2-关注帖子；3-关注用户
		userquery.addQueryData("status", "1");
		userquery.setPageIndex(1);
		userquery.setRowsPerPage(1);
		List<KFFUser> activeUsers = findProjectActiveUsers(projectId);
		if (CollectionUtils.isNotEmpty(activeUsers)) {
			for (KFFUser user : activeUsers) {
				if (null != user) {
					userquery.addQueryData("followedId", user.getUserId() + "");
					PageResult<Follow> followsuser = kffFollowService.findPage(userquery);
					if (followsuser != null && CollectionUtils.isNotEmpty(followsuser.getRows())) {
						user.setFollowStatus(KFFConstants.STATUS_ACTIVE);
					}
				}
			}
		}
		response.setActiveUsers(activeUsers);

		KFFUser owner = kffUserService.findById(project.getSubmitUserId());
		if (owner != null) {
			userquery.addQueryData("followedId", owner.getUserId() + "");
			PageResult<Follow> followsuser = kffFollowService.findPage(userquery);
			if (followsuser != null && CollectionUtils.isNotEmpty(followsuser.getRows())) {
				owner.setFollowStatus(KFFConstants.STATUS_ACTIVE);
			}
		}
		response.setOwner(owner);

		return response;
	}

	@Override
	public List<KFFUser> findProjectActiveUsers(Integer projectId) throws RestServiceException {
		List<KFFUser> activeUsers = new ArrayList<>();
		KFFProject project = kffProjectService.findById(projectId);
		if (project != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("projectId", projectId + "");
			List<Post> posts = kffPostService.findProjectActiveUsers(map);
			if (CollectionUtils.isNotEmpty(posts)) {
				for (Post post : posts) {
					KFFUser user = new KFFUser();
					user = kffUserService.findById(post.getCreateUserId());
					if (null != user) {
						activeUsers.add(user);
					}
				}
			}
		}
		return activeUsers;
	}

	@Override
	public void saveComment(CommentsRequest comment) throws RestServiceException {
		if (comment == null) {
			throw new RestServiceException("参数缺失");
		}
		if (comment.getCommentUserId() == null) {
			throw new RestServiceException("评论用户ID不能为空");
		}
		if (StringUtils.isBlank(comment.getCommentContent())) {
			throw new RestServiceException("评论内容不能为空");
		}
		if (comment.getCommentContent().length() > 30000) {
			throw new RestServiceException("文章内容长度超过限制");
		}
		if (comment.getPostId() == null) {
			throw new RestServiceException("帖子ID不能为空");
		}
		KFFUser commentUser = kffUserService.findById(comment.getCommentUserId());
		if (commentUser == null) {
			throw new RestServiceException("用户不存在" + comment.getCommentUserId());
		}
		Post post = kffPostService.findById(comment.getPostId());
		if (post == null) {
			throw new RestServiceException("帖子不存在" + comment.getPostId());
		}
		Comments saveComment = new Comments();
		Date now = new Date();
		BeanUtils.copyProperties(comment, saveComment);
		saveComment.setCommentUserIcon(commentUser.getIcon());
		saveComment.setCommentUserName(commentUser.getUserName());

		saveComment.setBecommentedUserId(post.getCreateUserId());
		saveComment.setBecommentedUserIcon(post.getCreateUserIcon());
		saveComment.setBecommentedUserName(post.getCreateUserName());

		if (comment.getParentCommentsId() != null && comment.getParentCommentsId() != 0) {
			// 回复评论需要把评论的用户ID做为被评论人ID
			Comments parentComment = kffCommentsService.findById(comment.getParentCommentsId());
			if (parentComment == null) {
				throw new RestServiceException("错误的父帖ID" + comment.getParentCommentsId());
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

		// 帖子用户消息
		KFFMessage message = new KFFMessage();
		if (comment.getParentCommentsId() != null) {
			message.setContent(commentUser.getUserName() + "回复了您的评论!");
			message.setTitle("回复被评论");
			message.setType(KFFConstants.MESSAGE_TYPE_COMMENT_REPLY);

		} else {
			message.setContent(commentUser.getUserName() + "评论了您的帖子!");
			message.setTitle("评论");
			message.setType(KFFConstants.MESSAGE_TYPE_COMMENT);
		}
		message.setCreateTime(now);
		message.setJumpInfo("");
		message.setState(KFFConstants.MESSAGE_STATE_UNREAD);
		message.setStatus(KFFConstants.STATUS_ACTIVE);

		message.setUpdateTime(now);
		message.setUserId(post.getCreateUserId());
		message.setSenderUserId(commentUser.getUserId());
		kffMessageService.save(message);

	}

	@Override
	public Map<String, Object> saveArticle(ArticleRequest articleRequest, String toHtmlTags) throws RestServiceException {
		Map<String, Object> result = new HashMap<>();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		if (articleRequest == null) {
			throw new RestServiceException("参数缺失");
		}
		if (articleRequest.getCreateUserId() == null) {
			throw new RestServiceException("创建用户ID不能为空");
		}
		if (articleRequest.getProjectId() == null) {
			throw new RestServiceException("项目ID不能为空");
		}
		if (StringUtils.isBlank(articleRequest.getArticleContents())) {
			throw new RestServiceException("文章内容不能为空");
		}
		if (StringUtils.isBlank(articleRequest.getPostTitle())) {
			throw new RestServiceException("文章标题不能为空");
		}
		logger.info("长度" + articleRequest.getArticleContents().length());
		if (articleRequest.getArticleContents().length() > 16777215) {
			throw new RestServiceException("文章内容长度超过限制");
		}
		if (articleRequest.getPostTitle().length() > 120) {
			throw new RestServiceException("文章标题长度不能超过30字");
		}
		KFFUser createUser = kffUserService.findById(articleRequest.getCreateUserId());
		if (createUser == null) {
			throw new RestServiceException("用户不存在" + articleRequest.getCreateUserId());
		}
		KFFProject project = kffProjectService.findById(articleRequest.getProjectId());
		if (project == null) {
			throw new RestServiceException("项目不存在" + articleRequest.getProjectId());
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
		if (toHtmlTags != null) {
			//
			toHtmlTags = H5AgainDeltagsUtil.h5AgainDeltags(toHtmlTags);
			if (toHtmlTags.length() < 300) {
				post.setPostShortDesc(toHtmlTags);
			} else {
				//
				post.setPostShortDesc(toHtmlTags.substring(0, 300));
			}
			logger.info("去标签成功!");
		}
		if (toHtmlTags == null) {
			String text = WorkHtmlRegexpUtil.delHTMLTag(articleRequest.getArticleContents());
			toHtmlTags = H5AgainDeltagsUtil.h5AgainDeltags(text);
			if (toHtmlTags.length() < 300) {
				post.setPostShortDesc(toHtmlTags);
			} else {
				//
				post.setPostShortDesc(toHtmlTags.substring(0, 300));
			}
			logger.info("去标签成功!");
		}

		/************ begin *******************/
		Map<String, String> qiNiuMap = grabUrlAndReplaceQiniu(articleRequest.getArticleContents(), createUser.getUserId());
		String uploadIevisList = qiNiuMap.get("uploadIeviwList");
		String contentSrcReplace = qiNiuMap.get("contentSrcReplace");
		logger.info("缩略图的json串" + uploadIevisList);
		/************ end *******************/
		post.setPostSmallImages(uploadIevisList);
		post.setPostTitle(articleRequest.getPostTitle());
		post.setPostType(KFFConstants.POST_TYPE_ARTICLE);// 帖子类型：1-评测；2-讨论；3-文章
		post.setPraiseNum(0);
		post.setProjectCode(project.getProjectCode());
		post.setProjectEnglishName(project.getProjectEnglishName());
		post.setProjectChineseName(project.getProjectChineseName());
		post.setProjectIcon(project.getProjectIcon());
		post.setProjectId(project.getProjectId());
		post.setStatus(KFFConstants.STATUS_ACTIVE);
		post.setUuid(uuid);
		post.setStickTop(0);
		kffPostService.save(post);
		Post newPost = kffPostService.findByUUID(uuid);
		if (newPost == null) {
			throw new RestServiceException("帖子不存在" + uuid);
		}
		Article article = new Article();
		String contents = null;
		if (null == contentSrcReplace) {
			contents = WorkHtmlRegexpUtil.deleContentsHtmlTage(articleRequest.getArticleContents());
			article.setArticleContents(contents);
		} else {
			contents = WorkHtmlRegexpUtil.deleContentsHtmlTage(contentSrcReplace);
			article.setArticleContents(contents);
		}
		article.setPostId(newPost.getPostId());
		article.setPostUuid(uuid);
		kffArticleService.save(article);
		result.put("postId", newPost.getPostId());
		result.put("postType", newPost.getPostType());
		// 更新用户发帖数
		kffUserService.increasePostNum(createUser.getUserId(), KFFConstants.POST_TYPE_ARTICLE);
		return result;
	}

	private String uploadIeviwListObject(List<Object> photoIviewses) {
		List<PhotoParams> PhotoParamses = new ArrayList<PhotoParams>();
		for (Object photoIview : photoIviewses) {
			if (photoIview instanceof String) {
				if (null == photoIviewses) {
					throw new RestServiceException(RestErrorCode.PICTURE_UPLOAD_FAIL);
				}
				//
				//
				//
				String photoIviewStr = (String) photoIview;
				PhotoParams photoParams = new PhotoParams();

				photoParams.setFileUrl(photoIviewStr);
				// 取后缀名
				String[] str = photoIviewStr.split("\\.");
				logger.info(str[0]);
				logger.info(str[1]);
				photoParams.setExtension(str[1]);
				logger.info(str[0].lastIndexOf("/"));
				str[0].substring(str[0].lastIndexOf("/") + 1);
				logger.info(str[0].substring(str[0].lastIndexOf("/") + 1));
				photoParams.setFileName(str[0].substring(str[0].lastIndexOf("/") + 1));
				photoParams.setIsExist(true);
				PhotoParamses.add(photoParams);
			} else if (photoIview instanceof PhotoParams) {
				logger.info("全路径:" + photoIview);
				PhotoParams photoIviewStr = (PhotoParams) photoIview;
				PhotoParamses.add(photoIviewStr);
			}
		}
		String jsonString = JSON.toJSONString(PhotoParamses);
		logger.info("存储数据库的字符串:" + jsonString);
		return jsonString;
	}

	@Override
	public Map<String, Object> saveDiscuss(DiscussRequest discussRequest) throws RestServiceException {
		Map<String, Object> result = new HashMap<>();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		if (discussRequest == null) {
			throw new RestServiceException("参数缺失");
		}
		if (discussRequest.getCreateUserId() == null) {
			throw new RestServiceException("创建用户ID不能为空");
		}
		if (discussRequest.getProjectId() == null) {
			throw new RestServiceException("项目ID不能为空");
		}
		if (StringUtils.isBlank(discussRequest.getDisscussContents())) {
			throw new RestServiceException("讨论内容不能为空");
		}
		if (StringUtils.isBlank(discussRequest.getPostTitle())) {
			throw new RestServiceException("讨论标题不能为空");
		}
		if (discussRequest.getDisscussContents().length() > 3000) {
			throw new RestServiceException("讨论内容长度超过限制");
		}
		if (discussRequest.getPostTitle().length() > 120) {
			throw new RestServiceException("讨论标题长度不能超过60字");
		}
		KFFUser createUser = kffUserService.findById(discussRequest.getCreateUserId());
		if (createUser == null) {
			throw new RestServiceException("用户不存在" + discussRequest.getCreateUserId());
		}
		KFFProject project = kffProjectService.findById(discussRequest.getProjectId());
		if (project == null) {
			throw new RestServiceException("项目不存在" + discussRequest.getProjectId());
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
		// String text =
		// WorkHtmlRegexpUtil.delHTMLTag(discussRequest.getDisscussContents()).replace("&nbsp;",
		// " ");
		if (discussRequest.getDisscussContents().length() > 300) {
			post.setPostShortDesc(discussRequest.getDisscussContents().substring(0, 300));
		} else {
			post.setPostShortDesc(discussRequest.getDisscussContents());
		}
		/*// 抽取文章中的图片路径
		*//************ begin *******************/

		// ************ end *******************/
		//
		if (StringUtils.isNotBlank(discussRequest.getDiscussImages())) {
			List<String> picArray = JSON.parseArray(discussRequest.getDiscussImages(), String.class);
			List<PhotoParams> picNewArray = new ArrayList<PhotoParams>();
			/*String jsonString = null;*/
			for (String pic : picArray) {
				// upload/Idcard/2.jpg
				PhotoParams photoOUrl = JSON.parseObject(pic, PhotoParams.class);
				PhotoParams photoParams = new PhotoParams();
				photoParams.setFileUrl(photoOUrl.getFileUrl());
				photoParams.setExtension("ext");
				photoParams.setFileName("qufen");
				photoParams.setIsExist(true);
				picNewArray.add(photoParams);
			}
			String jsonString2 = JSON.toJSONString(picNewArray);
			discussRequest.setDiscussImages(jsonString2);
		}
		post.setPostSmallImages(discussRequest.getDiscussImages());

		post.setPostTitle(discussRequest.getPostTitle());
		post.setPostType(KFFConstants.POST_TYPE_DISCUSS);// 帖子类型：1-评测；2-讨论；3-文章
		post.setPraiseNum(0);
		post.setProjectCode(project.getProjectCode());
		post.setProjectEnglishName(project.getProjectEnglishName());
		post.setProjectChineseName(project.getProjectChineseName());
		post.setProjectIcon(project.getProjectIcon());
		post.setProjectId(project.getProjectId());
		post.setStatus(KFFConstants.STATUS_ACTIVE);
		post.setUuid(uuid);
		post.setStickTop(0);
		kffPostService.save(post);

		Post newPost = kffPostService.findByUUID(uuid);
		if (newPost == null) {
			throw new RestServiceException("帖子不存在" + uuid);
		}
		Discuss discuss = new Discuss();
		discuss.setDisscussContents(discussRequest.getDisscussContents());
		discuss.setPostId(newPost.getPostId());
		discuss.setPostUuid(uuid);
		discuss.setTagInfos(discussRequest.getTagInfos());
		kffDiscussService.save(discuss);

		// 更新用户发帖数
		kffUserService.increasePostNum(createUser.getUserId(), KFFConstants.POST_TYPE_DISCUSS);
		result.put("postId", newPost.getPostId());
		result.put("postType", newPost.getPostType());
		return result;
	}

	@Override
	public List<Dtags> findAllTags() throws RestServiceException {

		return kffDtagsService.findAllTags();

	}

	@Override
	// 根据用户的ID判断用户是否对项目进行评分,如果已经进行评分了,则抛出异常
	public Map<String, Object> saveEvaluation(EvaluationRequest evaluationRequest) throws RestServiceException {
		/**
		 * a.完整评测 EOS - 完整版专业评测(all)
		 * 
		 * b.自定义完整评测 EOS - 完整版自建模型评测(all）
		 * 
		 * c.部分评测 EOS - 项目进度评测（part） EOS - 技术框架评测（part） （部分评测要带上单项纬度名称）
		 * 
		 * d.简单评测 EOS - 简单评测
		 * 
		 * EOS指的是代币符号
		 */
		System.out.println("evaluationRequest.getTotalScore()" + evaluationRequest.getTotalScore());
		KFFProject project = kffProjectService.findById(evaluationRequest.getProjectId());
		if (null == project) {
			throw new RestServiceException("此项目信息有错");
		}
		if (2 == evaluationRequest.getModelType() || 4 == evaluationRequest.getModelType()) {
			// checkProjectEvaluation(evaluationRequest, project);
		}
		if (3 == evaluationRequest.getModelType()) {
			// checkProjectOnlyEvaluation(evaluationRequest);
		}
		Map<String, Object> result = new HashMap<>();
		String uuid = UUID.randomUUID().toString().replace("-", "");

		if (evaluationRequest.getTotalScore() == null) {
			throw new RestServiceException("综合评分丢失!");
		}
		if (evaluationRequest.getCreateUserId() == null) {
			throw new RestServiceException("创建用户ID不能为空");
		}
		if (evaluationRequest.getProjectId() == null) {
			throw new RestServiceException("项目ID不能为空");
		}
		if (evaluationRequest.getModelType() == null) {
			throw new RestServiceException("评测类型不能为空");
		}
		// 评测标题定义
		if (1 == evaluationRequest.getModelType()) {
			evaluationRequest.setPostTitle(project.getProjectCode() + "- 简单评测");// 完整评测 手机上的
		}
		if (2 == evaluationRequest.getModelType()) {
			evaluationRequest.setPostTitle(project.getProjectCode() + "- 完整版专业评测(all)");// 完整评测
		}
		String evaDetail = evaluationRequest.getProfessionalEvaDetail();
		List<EvaDtail> EvaDtails = JSON.parseArray(evaDetail, EvaDtail.class);
		String evaDtailModuleName = null;
		if (null != EvaDtails && EvaDtails.size() == 1) {
			evaDtailModuleName = EvaDtails.get(0).getModelName();
		}
		if (3 == evaluationRequest.getModelType()) {
			evaluationRequest.setPostTitle(project.getProjectCode() + " - " + evaDtailModuleName + "(part)");// 单项评测
		}
		if (4 == evaluationRequest.getModelType()) {
			evaluationRequest.setPostTitle(project.getProjectCode() + " - 完整版自建模型评测(all)");// 用户自定义评测
		}

		if (evaluationRequest.getModelType() != 1 && evaluationRequest.getModelType() != 2 && evaluationRequest.getModelType() != 3
				&& evaluationRequest.getModelType() != 4) {
			throw new RestServiceException("非法评测类型");
		}
		if (StringUtils.isBlank(evaluationRequest.getEvauationContent())) {
			throw new RestServiceException("评测内容不能为空");
		}
		if (StringUtils.isBlank(evaluationRequest.getPostTitle())) {
			throw new RestServiceException("评测标题不能为空");
		}
		if (evaluationRequest.getEvauationContent().length() > 16777215) {
			throw new RestServiceException("评测内容长度超过限制");
		}
		if (evaluationRequest.getPostTitle().length() > 120) {
			throw new RestServiceException("评测标题长度不能超过60字");
		}
		KFFUser createUser = kffUserService.findById(evaluationRequest.getCreateUserId());
		if (createUser == null) {
			throw new RestServiceException("用户不存在" + evaluationRequest.getCreateUserId());
		}
		if (project == null) {
			throw new RestServiceException("项目不存在" + evaluationRequest.getProjectId());
		}
		if (3 == evaluationRequest.getModelType()) {
			// 单项评测 totalScore取出来
			List<DevaluationModel> onlyevaDetail = JSON.parseArray(evaluationRequest.getProfessionalEvaDetail(), DevaluationModel.class);
			if (CollectionUtils.isNotEmpty(onlyevaDetail)) {
				System.out.println(onlyevaDetail.size());
				if (onlyevaDetail.size() == 1) {

					evaluationRequest.setTotalScore(onlyevaDetail.get(0).getScore());
				}
			}
		}
		BigDecimal totalScore = evaluationRequest.getTotalScore() == null ? BigDecimal.ZERO : evaluationRequest.getTotalScore();
		System.out.println(" evaluationRequest.getTotalScore()" + evaluationRequest.getTotalScore());
		System.out.println("totalScore" + totalScore);
		// 专业评测总分计算
		if (Objects.equal(2, evaluationRequest.getModelType())) {
			// try{
			if (StringUtils.isBlank(evaluationRequest.getProfessionalEvaDetail())) {
				throw new RestServiceException("专业评测打分内容不能为空");
			}
			JSONArray ja = JSON.parseArray(evaluationRequest.getProfessionalEvaDetail());
			if (ja == null || ja.size() == 0) {
				throw new RestServiceException("专业评测分项内容不能为空" + evaluationRequest.getProfessionalEvaDetail());
			}

			// 临时兼容score传入字符串
			List<DevaluationModel> models = new ArrayList<>();
			for (int i = 0; i < ja.size(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				DevaluationModel model = new DevaluationModel();
				model.setModelId((Integer) jo.get("modelId"));
				model.setModelName(jo.getString("modelName"));
				model.setModelWeight((Integer) jo.getInteger("modelWeight"));
				String score = String.valueOf(jo.get("score"));
				model.setScore(new BigDecimal(score));
				models.add(model);
			}

			/*
			List<DevaluationModel> models =JSON.parseArray(evaluationRequest.getProfessionalEvaDetail(), DevaluationModel.class);
			if(models == null || models.size() ==0){
				throw new RestServiceException("专业评测分项内容不能为空"+evaluationRequest.getProfessionalEvaDetail());
			}
			*/
			/*for (DevaluationModel model : models) {
				totalScore = totalScore.add(model.getScore().multiply(new BigDecimal(model.getModelWeight())));
			}
			totalScore = totalScore.divide(new BigDecimal(100), 1, RoundingMode.HALF_DOWN).setScale(1, RoundingMode.HALF_DOWN);*/

			// }catch(Exception e){
			// e.printStackTrace();
			// throw new
			// RestServiceException("专业评测分项内容格式不对"+evaluationRequest.getProfessionalEvaDetail());
			// }
			//
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
		logger.info("开始截取字符串");
		String text = WorkHtmlRegexpUtil.delHTMLTag(evaluationRequest.getEvauationContent()).replace("&nbsp;", " ");
		logger.info("无标签文本" + text);
		if (text.length() < 200) {
			post.setPostShortDesc(text);
		} else {
			post.setPostShortDesc(text.substring(0, 200));
		}
		logger.info(evaluationRequest.getEvauationContent());
		//
		/************ begin *******************/
		Map<String, String> qiNiuMap = grabUrlAndReplaceQiniu(evaluationRequest.getEvauationContent(), createUser.getUserId());
		String uploadIevisList = qiNiuMap.get("uploadIeviwList");
		String contentSrcReplace = qiNiuMap.get("contentSrcReplace");
		logger.info("缩略图的json串" + uploadIevisList);
		/************ end *******************/
		post.setPostSmallImages(uploadIevisList);
		post.setPostTitle(evaluationRequest.getPostTitle());
		post.setPostType(KFFConstants.POST_TYPE_EVALUATION);// 帖子类型：1-评测；2-讨论；3-文章
		post.setPraiseNum(0);
		post.setProjectCode(project.getProjectCode());
		post.setProjectEnglishName(project.getProjectEnglishName());
		post.setProjectChineseName(project.getProjectChineseName());
		post.setProjectIcon(project.getProjectIcon());
		post.setProjectId(project.getProjectId());
		post.setStatus(KFFConstants.STATUS_ACTIVE);
		post.setUuid(uuid);
		post.setStickTop(0);
		Integer postId = kffPostService.save(post);
		logger.warn("保存评测inserted postId is:" + postId + ",and UUID is:" + uuid);
		logger.warn("保存评测inserted post对象中的postId is:" + post.getPostId() + ",and UUID is:" + uuid);
		Post newPost = kffPostService.findByUUID(uuid);
		if (newPost == null) {
			throw new RestServiceException("帖子不存在" + uuid);
		}
		Evaluation evaluation = new Evaluation();
		String content = null;
		if (null == contentSrcReplace) {
			content = WorkHtmlRegexpUtil.deleContentsHtmlTage(evaluationRequest.getEvauationContent());
			evaluation.setEvauationContent(content);
		} else {
			content = WorkHtmlRegexpUtil.deleContentsHtmlTage(contentSrcReplace);
			evaluation.setEvauationContent(content);
		}
		evaluation.setPostId(newPost.getPostId());
		evaluation.setProjectId(project.getProjectId());
		evaluation.setPostUuid(uuid);
		evaluation.setEvaluationTags(evaluationRequest.getEvaluationTags());
		evaluation.setProfessionalEvaDetail(evaDetail);
		evaluation.setCreateTime(now);
		evaluation.setUpdateTime(now);
		evaluation.setCreateUserId(createUser.getUserId());
		evaluation.setModelType(evaluationRequest.getModelType());
		evaluation.setStatus(KFFConstants.STATUS_ACTIVE);
		evaluation.setTotalScore(totalScore);
		kffEvaluationService.save(evaluation);

		result.put("postId", newPost.getPostId());
		result.put("postType", newPost.getPostType());
		result.put("modelType", evaluationRequest.getModelType());
		// 更新用户发帖数
		kffUserService.increasePostNum(createUser.getUserId(), KFFConstants.POST_TYPE_EVALUATION);
		// 更新项目总分 改为定时任务每天晚上1点计算 统计超过 10份评测的项目
		// kffProjectService.updateTotalScore(project.getProjectId(), totalScore);
		// 更新评测人数
		kffProjectService.increaseRaterNum(project.getProjectId());

		return result;

	}

	@Override
	public void saveEvaluationModel(DevaluationModelRequest devaluationModelRequest) throws RestServiceException {
		if (devaluationModelRequest == null) {
			throw new RestServiceException("参数缺失");
		}
		if (devaluationModelRequest.getProjectId() == null) {
			throw new RestServiceException("项目ID不能为空");
		}
		if (StringUtils.isBlank(devaluationModelRequest.getProfessionalEvaDetail())) {
			throw new RestServiceException("模型内容不能为空");
		}

		try {

			List<DevaluationModel> dms = JSONArray.parseArray(devaluationModelRequest.getProfessionalEvaDetail(), DevaluationModel.class);
			if (CollectionUtils.isNotEmpty(dms)) {
				int totalWeight = 0;
				for (DevaluationModel dm : dms) {
					if (StringUtils.isBlank(dm.getModelName())) {
						throw new RestServiceException("模型名称不能为空");
					}
					if (dm.getModelName().length() > 30) {
						throw new RestServiceException("模型名称能超过30字符");
					}
					if (dm.getModelWeight() == null) {
						throw new RestServiceException("模型权重不能为空");
					}
					totalWeight = totalWeight + dm.getModelWeight();
				}
				if (totalWeight != 100) {
					throw new RestServiceException("模型权重之和不为100");
				}
				Integer batchId = Integer.parseInt(RandomUtil.produceNumber(8));
				Date now = new Date();
				for (DevaluationModel dm : dms) {
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
		} catch (Exception e) {
			logger.error("用户自定义模型解析json失败:{}", e);
			throw new RestServiceException("非法模型内容" + devaluationModelRequest.getProfessionalEvaDetail());
		}
	}

	@Override
	public int savePraise(Integer userId, Integer postId) throws RestServiceException {
		int result = 0;
		if (userId == null) {
			throw new RestServiceException("用户id不能为空");
		}
		if (postId == null) {
			throw new RestServiceException("帖子id不能为空");
		}
		KFFUser user = kffUserService.findById(userId);
		if (user == null) {
			throw new RestServiceException("用户不存在" + userId);
		}
		Post post = kffPostService.findById(postId);
		if (post == null) {
			throw new RestServiceException("帖子不存在" + postId);
		}
		Date now = new Date();
		boolean praiseNumIncrease = false;
		Praise praise = kffPraiseService.findByPostId(userId, postId);
		if (praise == null) {
			Praise save = new Praise();
			save.setBepraiseUserId(post.getCreateUserId());
			save.setCreateTime(now);
			save.setPostId(postId);
			save.setPostType(post.getPostType());
			save.setPraiseUserId(userId);
			save.setProjectId(post.getProjectId());
			save.setStatus(KFFConstants.STATUS_ACTIVE);
			save.setUpdateTime(now);
			save.setPraiseType(KFFConstants.PRAISE_TYPE_POST);
			kffPraiseService.save(save);
			praiseNumIncrease = true;
		} else {
			if (praise.getStatus() != null && praise.getStatus() == 0) {
				praise.setUpdateTime(now);
				praise.setStatus(KFFConstants.STATUS_ACTIVE);
				kffPraiseService.update(praise);
				praiseNumIncrease = true;
			} else {
				logger.warn("已点赞存在重复点赞");
				// throw new RestServiceException("已点赞，请勿重复点赞");
			}
		}
		// 帖子点赞数加1
		if (praiseNumIncrease) {
			kffPostService.increasePraiseNum(postId);
			kffUserService.increasePraiseNum(post.getCreateUserId());
			// 被点赞用户消息
			KFFMessage message = new KFFMessage();
			message.setContent(user.getUserName() + "点赞了您!");
			message.setCreateTime(now);
			message.setJumpInfo(user.getUserId().toString());
			message.setState(KFFConstants.MESSAGE_STATE_UNREAD);
			message.setStatus(KFFConstants.STATUS_ACTIVE);
			message.setTitle("点赞");
			message.setType(KFFConstants.MESSAGE_TYPE_PRAISE);
			message.setUpdateTime(now);
			message.setUserId(post.getCreateUserId());
			message.setSenderUserId(user.getUserId());
			kffMessageService.save(message);

			/**
			 * 添加帖子点赞送积分功能
			 * 
			 */

			// Integer yxPraise = qfIndexPraiseUser.getYxpraise();
			// Integer createPostUserQFIndex = qfIndex.getYxpraise();
			// if (evaluation.getModelType() == 1) {
			// 帖子点赞数 +1 的时候去执行以下 送积分操作

			// 根据 文章详情对象praise 获取帖子类型
			Post posts = kffPostService.findById(postId);
			Integer postType = posts.getPostType();
			System.err.println("我是帖子类型 :" + postType);
			// 根据点赞人的id 去查看他的有效点赞
			QfIndex qfIndexPraiseUser = qfIndexService.findByUserId(userId);
			Integer qfIndexId = qfIndexPraiseUser.getQfIndexId();
			Integer yxPraise = qfIndexPraiseUser.getYxpraise();
			// 根据帖子的id 去获取内容贡献者的id
			System.err.println("帖子的id: " + postId);
			Post creatUserPost = kffPostService.findById(postId);
			Integer createUserId = creatUserPost.getCreateUserId();
			System.err.println("内容贡献者的id: " + createUserId);
			// 根据内容贡献值的id去获取本身的区分指数
			QfIndex qfIndex = qfIndexService.findByUserId(createUserId);
			Integer createPostUserQFIndex = 0;
			Double createPUF = 0.0d;
			if (null != qfIndex) {
				createPostUserQFIndex = qfIndex.getStatusHierarchyType();
				// 发帖人赞的收益系数
				createPUF = createPostUserQFIndex * 0.01d;
			}

			// 满足点赞条件额外送币
			Double meet = 10000.00000000d;
			Double meet1 = 4000.00000000d;
			Double meet2 = 2000.00000000d;
			// 创建生成交易流水的交易日期
			Date date = new Date();
			String stringDate = DateUtil.getDate(date, "yyyy-MM-dd");
			String replaceAllDate = stringDate.replaceAll("-", "");
			// 创建业务记录id+
			Praise praiseId = kffPraiseService.findByPraiseId(createUserId, userId);
			String format = "";
			if (null != praiseId)
				format = String.format("%010d", praiseId.getPraiseId());
			// 判断点赞人是否实名认证
			UserCard findBycreateUserId = userCardService.findByUserid(createUserId);//
			// 看创建帖子的人是不是实名认证
			UserCard findByUserid = userCardService.findByUserid(userId);

			System.err.println("findBycreateUserId:" + findBycreateUserId);
			System.err.println("findByUserid : " + findByUserid);
			// 判断所点赞的文章是不是有效(1有效,0删除,无效)
			// QfIndex byUserId = qfIndexService.findByUserId(createUserId);

			if (post.getStatus() == 1 && findBycreateUserId != null && findByUserid != null) {
				/**
				 * 有效赞
				 * 
				 */
				System.err.println("我终于执行了");
				Integer validPraise = (int) Math.floor(yxPraise);
				// 判断 帖子类型是1-评测 , 2-讨论 , 3-文章
				Double pc = 5.00d; // 普通评测点赞奖励
				Double pc2 = 50.00d; // 专业评测点赞奖励
				Double pc3 = 20.00d; // 单项评测点赞奖励
				Double tl = 20.00d; // 讨论点赞奖励
				Double wz = 20.00d; // 文章点赞奖励
				KFFUser findByUserId = kffUserService.findById(createUserId);
				CoinProperty findByCreateUser = coinPropertyService.findByUserId(createUserId);
				if (postType == 1) {
					// 证明是评测的帖子
					// 判断评测的类型 (1-简单评测, 2-全面专业评测 , 3-部分系统单项评测)
					// 根据文章详情的id 去查询 查询评测类型 返回evaluation 对象
					System.err.println("执行我就相当于评测的帖子");
					Evaluation evaluation = kffEvaluationService.findByPostId(postId);
					// KFFUser findByUserId2 =
					// kffUserService.findByUserId(createUserId);
					if (evaluation.getModelType() == 1) {
						// 判断点赞类型 (简单评测的 只有点赞有奖励,评论赞没有)
						if (null != praiseId && praiseId.getPraiseType() == 1) {
							// 帖子点赞(普通1次1个代币)
							Tokenrecords tokenrecords = new Tokenrecords();
							Tokenaward tokenaward = new Tokenaward();

							if (praiseId.getStatus() == 1 && validPraise > 0 && validPraise != 0) {
								// 证明是有效赞
								tokenrecords.setFunctionDesc("点赞奖励(短评)");
								tokenrecords.setFunctionType(17);
								tokenrecords.setAmount(new BigDecimal(pc * createPUF)); // 点赞奖励生成流水
								tokenrecords.setUserId(createUserId);
								tokenrecords.setTradeType(1);// 交易类型:1-收入；2-支出
								tokenrecords.setRewardGrantType(1); // 发放类型
																	// 1-一次性发放
																	// 2-线性发放
								tokenrecords.setCreateTime(new Date()); // 创建的时间
								tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
								tokenrecords.setMemo("用户" + user.getUserName() + "点赞奖励(短评)"); // 流水备注

								Double coinLock = findByCreateUser.getCoinLock();
								coinLock = coinLock + (pc * createPUF);
								findByCreateUser.setCoinLock(coinLock);
								coinPropertyService.update(findByCreateUser);

								BigDecimal kffCoinNum = findByUserId.getKffCoinNum();
								findByUserId.setKffCoinNum(kffCoinNum.add(new BigDecimal(pc * createPUF)));
								findByUserId.setUpdateTime(new Date());

								kffUserService.update(findByUserId);
								kffTokenrecordsService.save(tokenrecords);

								// 根据userId去获取

								tokenaward.setUserId(createUserId);
								tokenaward.setTokenAwardFunctionDesc("点赞奖励(短评)");
								tokenaward.setTokenAwardFunctionType(17);
								tokenaward.setDistributionType(2);
								// Double rewardToken = tokenaward.getInviteRewards();
								// Double priaiseAward = tokenaward.getPriaiseAward();
								tokenaward.setPriaiseAward(pc * createPUF); // 点赞奖励.
								tokenaward.setInviteRewards(pc * createPUF);
								tokenaward.setCreateTime(new Date());
								tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
								KFFUser createUser = kffUserService.findById(createUserId);
								tokenaward.setUserName(createUser.getUserName());
								tokenaward.setMobile(createUser.getMobile());
								kffTokenawardService.save(tokenaward);

								qfIndexService.updateYxPraise(userId);
							}
						}
					}
					if (evaluation.getModelType() == 2 || evaluation.getModelType() == 4) {
						// 系统自定义专业完整版评测

						if (null != praiseId && praiseId.getPraiseType() == 1) {
							// 点赞类型1-帖子点赞；2-评论点赞
							// 帖子点赞(奖励50一个赞)
							Tokenrecords tokenrecords = new Tokenrecords();
							Tokenaward tokenaward = new Tokenaward();
							if (praiseId.getStatus() == 1 && validPraise > 0 && validPraise != 0) {
								// 证明是有效赞
								if (kffPostService.findById(postId).getPraiseNum() == 5) {
									tokenrecords.setFunctionDesc("点赞奖励(专评)");
									tokenrecords.setFunctionType(17);
									tokenrecords.setAmount(new BigDecimal(pc2 * createPUF + meet)); // 点赞奖励生成流水
									tokenrecords.setUserId(createUserId);
									tokenrecords.setTradeType(1);
									tokenrecords.setRewardGrantType(1);
									tokenrecords.setCreateTime(new Date());
									tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
									tokenrecords.setMemo("用户" + user.getUserName() + "点赞奖励(专评)"); // 流水备注
									kffTokenrecordsService.save(tokenrecords);

									BigDecimal kffCoinNum = findByUserId.getKffCoinNum();
									// KFFUser kffUser = new KFFUser();
									findByUserId.setKffCoinNum(kffCoinNum.add(new BigDecimal(pc2 * createPUF + meet)));
									findByUserId.setUpdateTime(new Date());
									kffUserService.update(findByUserId);

									Double coinLock = findByCreateUser.getCoinLock();
									coinLock = coinLock + (pc2 * createPUF + meet);
									findByCreateUser.setCoinLock(coinLock);
									coinPropertyService.update(findByCreateUser);

									tokenaward.setUserId(createUserId);
									tokenaward.setTokenAwardFunctionDesc("点赞奖励(专评)");
									tokenaward.setTokenAwardFunctionType(17);
									tokenaward.setDistributionType(2);
									// Double rewardToken = tokenaward.getRewardToken();
									// Double priaiseAward = tokenaward.getPriaiseAward();
									tokenaward.setPriaiseAward((pc2 * createPUF) + meet); // 点赞奖励.
									tokenaward.setInviteRewards((pc2 * createPUF) + meet);
									tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
									KFFUser createUser = kffUserService.findById(createUserId);
									tokenaward.setUserName(createUser.getUserName());
									tokenaward.setMobile(createUser.getMobile());
									tokenaward.setCreateTime(new Date());

									kffTokenawardService.save(tokenaward);

									qfIndexService.updateYxPraise(userId);
								} else {
									tokenrecords.setFunctionDesc("点赞奖励(专评)");
									tokenrecords.setFunctionType(17);
									tokenrecords.setAmount(new BigDecimal(pc2 * createPUF)); // 点赞奖励生成流水
									tokenrecords.setUserId(createUserId);
									tokenrecords.setTradeType(1);
									tokenrecords.setRewardGrantType(1);
									tokenrecords.setCreateTime(new Date());
									tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
									tokenrecords.setMemo("用户" + user.getUserName() + "点赞奖励(专评)"); // 流水备注
									kffTokenrecordsService.save(tokenrecords);

									BigDecimal kffCoinNum = findByUserId.getKffCoinNum();
									// KFFUser kffUser = new KFFUser();
									findByUserId.setKffCoinNum(kffCoinNum.add(new BigDecimal(pc2 * createPUF)));
									findByUserId.setUpdateTime(new Date());
									kffUserService.update(findByUserId);

									Double coinLock = findByCreateUser.getCoinLock();
									coinLock = coinLock + (pc2 * createPUF);
									findByCreateUser.setCoinLock(coinLock);
									coinPropertyService.update(findByCreateUser);

									tokenaward.setUserId(createUserId);
									tokenaward.setTokenAwardFunctionDesc("点赞奖励(专评)");
									tokenaward.setTokenAwardFunctionType(17);
									tokenaward.setDistributionType(2);
									// Double rewardToken = tokenaward.getRewardToken();
									// Double priaiseAward = tokenaward.getPriaiseAward();
									tokenaward.setPriaiseAward((pc2 * createPUF)); // 点赞奖励.
									tokenaward.setInviteRewards((pc2 * createPUF));
									tokenaward.setCreateTime(new Date());
									tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
									KFFUser createUser = kffUserService.findById(createUserId);
									tokenaward.setUserName(createUser.getUserName());
									tokenaward.setMobile(createUser.getMobile());
									kffTokenawardService.save(tokenaward);
									qfIndexService.updateYxPraise(userId);
								}
							}
						}

					}
					if (evaluation.getModelType() == 3) {
						// 用户自定义单项评测
						if (null != praiseId && praiseId.getStatus() == 1 && validPraise > 0 && validPraise != 0) {
							if (kffPostService.findById(postId).getPraiseNum() == 5) {
								Tokenrecords tokenrecords = new Tokenrecords();
								Tokenaward tokenaward = new Tokenaward();
								tokenrecords.setFunctionDesc("点赞奖励(单评)");
								tokenrecords.setFunctionType(17);
								tokenrecords.setAmount(new BigDecimal(pc3 * createPUF + meet2)); // 点赞奖励生成流水
								tokenrecords.setUserId(createUserId);
								tokenrecords.setTradeType(1);
								tokenrecords.setRewardGrantType(1);
								tokenrecords.setCreateTime(new Date());
								tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
								tokenrecords.setMemo("用户 :" + user.getUserName() + "点赞奖励(单评)"); // 流水备注
								kffTokenrecordsService.save(tokenrecords);

								BigDecimal kffCoinNum = findByUserId.getKffCoinNum();
								// KFFUser kffUser = new KFFUser();
								findByUserId.setKffCoinNum(kffCoinNum.add(new BigDecimal(pc3 * createPUF + meet2)));
								findByUserId.setUpdateTime(new Date());
								kffUserService.update(findByUserId);

								Double coinLock = findByCreateUser.getCoinLock();
								coinLock = coinLock + (pc3 * createPUF + meet2);
								findByCreateUser.setCoinLock(coinLock);
								coinPropertyService.update(findByCreateUser);

								tokenaward.setUserId(createUserId);
								tokenaward.setTokenAwardFunctionDesc("点赞奖励(单评)");
								tokenaward.setTokenAwardFunctionType(17);
								tokenaward.setDistributionType(2);

								// Double rewardToken = tokenaward.getRewardToken();
								// Double priaiseAward = tokenaward.getPriaiseAward();
								tokenaward.setPriaiseAward((pc3 * createPUF + meet2)); // 点赞奖励.
								tokenaward.setInviteRewards((pc3 * createPUF + meet2));
								tokenaward.setCreateTime(new Date());
								tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
								KFFUser createUser = kffUserService.findById(createUserId);
								tokenaward.setUserName(createUser.getUserName());
								tokenaward.setMobile(createUser.getMobile());
								kffTokenawardService.save(tokenaward);

								qfIndexService.updateYxPraise(userId);
							} else {
								Tokenrecords tokenrecords = new Tokenrecords();
								Tokenaward tokenaward = new Tokenaward();
								tokenrecords.setFunctionDesc("点赞奖励(单评)");
								tokenrecords.setFunctionType(17);
								tokenrecords.setAmount(new BigDecimal(pc3 * createPUF)); // 点赞奖励生成流水
								tokenrecords.setUserId(createUserId);
								tokenrecords.setTradeType(1);
								tokenrecords.setRewardGrantType(1);
								tokenrecords.setCreateTime(new Date());
								tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
								tokenrecords.setMemo("用户 :" + user.getUserName() + "点赞奖励(单评)"); // 流水备注
								kffTokenrecordsService.save(tokenrecords);

								BigDecimal kffCoinNum = findByUserId.getKffCoinNum();
								// KFFUser kffUser = new KFFUser();
								findByUserId.setKffCoinNum(kffCoinNum.add(new BigDecimal(pc3 * createPUF)));
								findByUserId.setUpdateTime(new Date());
								kffUserService.update(findByUserId);

								Double coinLock = findByCreateUser.getCoinLock();
								coinLock = coinLock + (pc3 * createPUF);
								findByCreateUser.setCoinLock(coinLock);
								coinPropertyService.update(findByCreateUser);

								tokenaward.setUserId(createUserId);
								tokenaward.setTokenAwardFunctionDesc("点赞奖励(单评)");
								tokenaward.setTokenAwardFunctionType(17);
								tokenaward.setDistributionType(2);

								// Double rewardToken = tokenaward.getRewardToken();
								// Double priaiseAward = tokenaward.getPriaiseAward();
								tokenaward.setPriaiseAward((pc3 * createPUF)); // 点赞奖励.
								tokenaward.setInviteRewards((pc3 * createPUF));
								tokenaward.setCreateTime(new Date());
								tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
								KFFUser createUser = kffUserService.findById(createUserId);
								tokenaward.setUserName(createUser.getUserName());
								tokenaward.setMobile(createUser.getMobile());
								kffTokenawardService.save(tokenaward);

								qfIndexService.updateYxPraise(userId);
							}

						}
					}
				}
				if (postType == 2) {
					// 证明是讨论的帖子
					System.err.println("执行我就相当于讨论的帖子");
					if (null != praiseId && praiseId.getPraiseType() == 1) {
						// 帖子点赞(普通1次五个)
						Tokenrecords tokenrecords = new Tokenrecords();
						Tokenaward tokenaward = new Tokenaward();
						if (praiseId.getStatus() == 1 && validPraise > 0 && validPraise != 0) {
							// 证明是有效赞
							tokenrecords.setFunctionDesc("点赞奖励(打假)");
							tokenrecords.setFunctionType(17);
							tokenrecords.setAmount(new BigDecimal(tl * createPUF)); // 点赞奖励生成流水
							tokenrecords.setUserId(createUserId);
							tokenrecords.setTradeType(1);
							tokenrecords.setRewardGrantType(1);
							tokenrecords.setCreateTime(new Date());
							tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
							tokenrecords.setMemo("用户" + user.getUserName() + "点赞奖励(打假)"); // 流水备注
							kffTokenrecordsService.save(tokenrecords);

							BigDecimal kffCoinNum = findByUserId.getKffCoinNum();
							// KFFUser kffUser = new KFFUser();
							findByUserId.setKffCoinNum(kffCoinNum.add(new BigDecimal(tl * createPUF)));
							findByUserId.setUpdateTime(new Date());
							kffUserService.update(findByUserId);

							Double coinLock = findByCreateUser.getCoinLock();
							coinLock = coinLock + (tl * createPUF);
							findByCreateUser.setCoinLock(coinLock);
							coinPropertyService.update(findByCreateUser);

							tokenaward.setUserId(createUserId);
							tokenaward.setTokenAwardFunctionDesc("点赞奖励(打假)");
							tokenaward.setTokenAwardFunctionType(17);
							tokenaward.setDistributionType(2);
							// Double rewardToken = tokenaward.getRewardToken();
							// Double priaiseAward = tokenaward.getPriaiseAward();
							tokenaward.setPriaiseAward((tl * createPUF)); // 点赞奖励.
							tokenaward.setInviteRewards((tl * createPUF)); // 奖励总数
							tokenaward.setCreateTime(new Date());
							tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
							KFFUser createUser = kffUserService.findById(createUserId);
							tokenaward.setUserName(createUser.getUserName());
							tokenaward.setMobile(createUser.getMobile());
							kffTokenawardService.save(tokenaward);
							qfIndexService.updateYxPraise(userId);
						}
					}
				}
				if (postType == 3) {
					// 证明是文章的帖子
					System.err.println("执行我就相当于文章的帖子");
					if (null != praiseId && praiseId.getPraiseType() == 1) {
						// 帖子点赞(普通1次二十个)
						Tokenrecords tokenrecords = new Tokenrecords();
						Tokenaward tokenaward = new Tokenaward();
						if (praiseId.getStatus() == 1 && validPraise > 0 && validPraise != 0) {
							// 证明是有效赞
							if (praiseId.getPraiseType() == 1) {
								if (kffPostService.findById(postId).getPraiseNum() == 5) {
									tokenrecords.setFunctionDesc("点赞奖励(文章)");
									tokenrecords.setFunctionType(17);
									tokenrecords.setAmount(new BigDecimal(wz * createPUF + meet1)); // 点赞奖励生成流水
									tokenrecords.setUserId(createUserId);
									tokenrecords.setTradeType(1);
									tokenrecords.setRewardGrantType(1);
									tokenrecords.setCreateTime(new Date());
									tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
									tokenrecords.setMemo("用户" + user.getUserName() + "点赞奖励(文章)"); // 流水备注
									kffTokenrecordsService.save(tokenrecords);

									BigDecimal kffCoinNum = findByUserId.getKffCoinNum();
									// KFFUser kffUser = new KFFUser();
									findByUserId.setKffCoinNum(kffCoinNum.add(new BigDecimal(wz * createPUF + meet1)));
									findByUserId.setUpdateTime(new Date());
									kffUserService.update(findByUserId);

									Double coinLock = findByCreateUser.getCoinLock();
									coinLock = coinLock + (wz * createPUF) + meet1;
									findByCreateUser.setCoinLock(coinLock);
									coinPropertyService.update(findByCreateUser);

									tokenaward.setUserId(createUserId);
									tokenaward.setTokenAwardFunctionDesc("点赞奖励(文章)");
									tokenaward.setTokenAwardFunctionType(17);
									tokenaward.setDistributionType(2);
									// Double rewardToken = tokenaward.getRewardToken();
									// Double priaiseAward = tokenaward.getPriaiseAward();
									tokenaward.setPriaiseAward((wz * createPUF + meet1)); // 点赞奖励.
									tokenaward.setInviteRewards((wz * createPUF + meet1));
									tokenaward.setCreateTime(new Date());
									tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
									KFFUser createUser = kffUserService.findById(createUserId);
									tokenaward.setUserName(createUser.getUserName());
									tokenaward.setMobile(createUser.getMobile());
									kffTokenawardService.save(tokenaward);
									qfIndexService.updateYxPraise(userId);
								} else {
									tokenrecords.setFunctionDesc("点赞奖励(文章)");
									tokenrecords.setFunctionType(17);
									tokenrecords.setAmount(new BigDecimal(wz * createPUF)); // 点赞奖励生成流水
									tokenrecords.setUserId(createUserId);
									tokenrecords.setTradeType(1);
									tokenrecords.setRewardGrantType(1);
									tokenrecords.setCreateTime(new Date());
									tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
									tokenrecords.setMemo("用户" + user.getUserName() + "点赞奖励(文章)"); // 流水备注
									kffTokenrecordsService.save(tokenrecords);

									BigDecimal kffCoinNum = findByUserId.getKffCoinNum();
									// KFFUser kffUser = new KFFUser();
									findByUserId.setKffCoinNum(kffCoinNum.add(new BigDecimal(wz * createPUF)));
									findByUserId.setUpdateTime(new Date());
									kffUserService.update(findByUserId);

									Double coinLock = findByCreateUser.getCoinLock();
									coinLock = coinLock + (wz * createPUF);
									findByCreateUser.setCoinLock(coinLock);
									coinPropertyService.update(findByCreateUser);

									tokenaward.setUserId(createUserId);
									tokenaward.setTokenAwardFunctionDesc("点赞奖励(文章)");
									tokenaward.setTokenAwardFunctionType(17);
									tokenaward.setDistributionType(2);
									// Double rewardToken = tokenaward.getRewardToken();
									// Double priaiseAward = tokenaward.getPriaiseAward();
									tokenaward.setPriaiseAward((wz * createPUF)); // 点赞奖励.
									tokenaward.setInviteRewards((wz * createPUF)); // 奖励总额
									tokenaward.setCreateTime(new Date());
									tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
									KFFUser createUser = kffUserService.findById(createUserId);
									tokenaward.setUserName(createUser.getUserName());
									tokenaward.setMobile(createUser.getMobile());
									kffTokenawardService.save(tokenaward);
									qfIndexService.updateYxPraise(userId);

								}
							}
						}
					}

				}
			}
		}

		Post latestPost = kffPostService.findById(postId);
		if (latestPost != null) {
			result = latestPost.getPraiseNum() == null ? 0 : (latestPost.getPraiseNum());
		}
		return result;
	}

	@Override
	public int cancelPraise(Integer userId, Integer postId) throws RestServiceException {
		int result = 0;
		if (userId == null) {
			throw new RestServiceException("用户id不能为空");
		}
		if (postId == null) {
			throw new RestServiceException("帖子id不能为空");
		}
		KFFUser user = kffUserService.findById(userId);
		if (user == null) {
			throw new RestServiceException("用户不存在" + userId);
		}
		Post post = kffPostService.findById(postId);
		if (post == null) {
			throw new RestServiceException("帖子不存在" + post);
		}
		Date now = new Date();
		boolean praiseNumDecrese = false;
		Praise praise = kffPraiseService.findByPostId(userId, postId);
		if (praise == null) {
			Praise save = new Praise();
			save.setBepraiseUserId(post.getCreateUserId());
			save.setCreateTime(now);
			save.setPostId(postId);
			save.setPostType(post.getPostType());
			save.setPraiseUserId(userId);
			save.setProjectId(post.getProjectId());
			save.setStatus(KFFConstants.STATUS_INACTIVE);
			save.setPraiseType(KFFConstants.PRAISE_TYPE_POST);
			save.setUpdateTime(now);
			;
			kffPraiseService.save(save);
			praiseNumDecrese = true;
		} else {
			if (praise.getStatus() != null && praise.getStatus() == 0) {
				logger.warn("已取消点赞存在重复取消点赞");
				// throw new RestServiceException("已取消点赞存在重复取消点赞");
			} else {
				praise.setUpdateTime(now);
				praise.setStatus(KFFConstants.STATUS_INACTIVE);
				kffPraiseService.update(praise);
				praiseNumDecrese = true;
			}
		}
		// 帖子点赞数减1
		if (praiseNumDecrese) {
			kffPostService.decreasePraiseNum(postId);
			kffUserService.decreasePraiseNum(post.getCreateUserId());
		}

		result = post.getPraiseNum() == null ? 0 : ((post.getPraiseNum() - 1) < 0 ? 0 : (post.getPraiseNum() - 1));
		return result;
	}

	@Override
	public void saveFollow(Integer userId, Integer followType, Integer followedId) throws RestServiceException {
		if (userId == null) {
			throw new RestServiceException("用户id不能为空");
		}
		if (followedId == null) {
			throw new RestServiceException("关注id不能为空");
		}
		if (followType == null) {
			throw new RestServiceException("关注类型不能为空");
		}
		// 1-关注项目;2-关注帖子；3-关注用户
		if (followType != 1 && followType != 2 && followType != 3) {
			throw new RestServiceException("无效的关注类型");
		}
		KFFUser user = kffUserService.findById(userId);
		if (user == null) {
			throw new RestServiceException("用户不存在" + userId);
		}

		if (Objects.equal(3, followType) && Objects.equal(userId, followedId)) {
			throw new RestServiceException("不用关注本人");
		}
		Integer followedUserId = 0;
		String followedUserIcon = "";
		String followedUserName = "";
		String followedUserSignature = "";
		// 关注项目
		if (followType == KFFConstants.FOLLOW_TYPE_PROJECT) {
			KFFProject followedProject = kffProjectService.findById(followedId);
			if (followedProject == null) {
				throw new RestServiceException("关注项目不存在" + followedId);
			}
			followedUserId = followedProject.getSubmitUserId();
			KFFUser projectCreator = kffUserService.findById(followedUserId);
			if (projectCreator != null) {
				followedUserId = projectCreator.getUserId();
				followedUserIcon = projectCreator.getIcon();
				followedUserName = projectCreator.getUserName();
				followedUserSignature = projectCreator.getUserSignature();
			}
		} else if (followType == KFFConstants.FOLLOW_TYPE_POST) {
			// 2-关注帖子
			Post followedPost = kffPostService.findById(followedId);
			if (followedPost == null) {
				throw new RestServiceException("关注帖子不存在" + followedId);
			}
			followedUserId = followedPost.getCreateUserId();
			followedUserIcon = followedPost.getCreateUserIcon();
			followedUserName = followedPost.getCreateUserName();
			followedUserSignature = followedPost.getCreateUserSignature();

		} else if (followType == KFFConstants.FOLLOW_TYPE_USER) {
			// 3-关注用户
			KFFUser followedUser = kffUserService.findById(followedId);
			if (followedUser == null) {
				throw new RestServiceException("关注的用户不存在" + followedId);
			}
			followedUserId = followedUser.getUserId();
			followedUserIcon = followedUser.getIcon();
			followedUserName = followedUser.getUserName();
			followedUserSignature = followedUser.getUserSignature();

		}
		Follow existFollow = kffFollowService.findByUserIdAndFollowType(userId, followType, followedId);
		Date now = new Date();
		boolean followNumIncrease = false;
		if (existFollow == null) {
			// 新增
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
		} else {
			// 更新状态
			if (existFollow.getStatus() != null && existFollow.getStatus() == 0) {
				existFollow.setStatus(1);
				existFollow.setUpdateTime(now);
				kffFollowService.update(existFollow);
				followNumIncrease = true;
			} else {
				logger.warn("重复关注");
			}
		}

		// 关注数量加1
		if (followNumIncrease) {
			if (followType == KFFConstants.FOLLOW_TYPE_PROJECT) {
				kffProjectService.increaseFollowerNum(followedId);
			} else if (followType == KFFConstants.FOLLOW_TYPE_USER) {
				// 增加粉丝数量
				kffUserService.increaseFansNum(followedUserId);
				// 被关注用户消息
				KFFMessage message = new KFFMessage();
				message.setContent(user.getUserName() + "关注了您!");
				message.setCreateTime(now);
				message.setJumpInfo("");
				message.setState(KFFConstants.MESSAGE_STATE_UNREAD);
				message.setStatus(KFFConstants.STATUS_ACTIVE);
				message.setTitle("关注");
				message.setType(KFFConstants.MESSAGE_TYPE_FOLLOW);
				message.setUpdateTime(now);
				message.setUserId(followedUserId);
				message.setSenderUserId(user.getUserId());
				kffMessageService.save(message);
			}
		}

	}

	@Override
	public void cancelFollow(Integer userId, Integer followType, Integer followedId) throws RestServiceException {

		if (userId == null) {
			throw new RestServiceException("用户id不能为空");
		}
		if (followedId == null) {
			throw new RestServiceException("取消关注id不能为空");
		}
		if (followType == null) {
			throw new RestServiceException("取消关注类型不能为空");
		}
		// 1-关注项目;2-关注帖子；3-关注用户
		if (followType != 1 && followType != 2 && followType != 3) {
			throw new RestServiceException("无效的取消关注类型");
		}
		KFFUser user = kffUserService.findById(userId);
		if (user == null) {
			throw new RestServiceException("用户不存在" + userId);
		}

		Integer followedUserId = 0;
		String followedUserIcon = "";
		String followedUserName = "";
		String followedUserSignature = "";
		// 1-取消关注项目
		if (followType == KFFConstants.FOLLOW_TYPE_PROJECT) {
			KFFProject followedProject = kffProjectService.findById(followedId);
			if (followedProject == null) {
				throw new RestServiceException("取消关注项目不存在" + followedId);
			}
			followedUserId = followedProject.getSubmitUserId();
			KFFUser projectCreator = kffUserService.findById(followedUserId);
			if (projectCreator != null) {
				followedUserId = projectCreator.getUserId();
				followedUserIcon = projectCreator.getIcon();
				followedUserName = projectCreator.getUserName();
				followedUserSignature = projectCreator.getUserSignature();
			}
		} else if (followType == KFFConstants.FOLLOW_TYPE_POST) {
			// 2-取消关注帖子
			Post followedPost = kffPostService.findById(followedId);
			if (followedPost == null) {
				throw new RestServiceException("取消关注帖子不存在" + followedId);
			}
			followedUserId = followedPost.getCreateUserId();
			followedUserIcon = followedPost.getCreateUserIcon();
			followedUserName = followedPost.getCreateUserName();
			followedUserSignature = followedPost.getCreateUserSignature();

		} else if (followType == KFFConstants.FOLLOW_TYPE_USER) {
			// 3-取消关注用户
			KFFUser followedUser = kffUserService.findById(followedId);
			if (followedUser == null) {
				throw new RestServiceException("取消关注的用户不存在" + followedId);
			}
			followedUserId = followedUser.getUserId();
			followedUserIcon = followedUser.getIcon();
			followedUserName = followedUser.getUserName();
			followedUserSignature = followedUser.getUserSignature();

		}
		Follow existFollow = kffFollowService.findByUserIdAndFollowType(userId, followType, followedId);
		Date now = new Date();
		boolean followNumDecrease = false;
		if (existFollow == null) {
			// 新增
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
		} else {
			// 更新状态
			if (existFollow.getStatus() != null && existFollow.getStatus() == KFFConstants.STATUS_ACTIVE) {
				existFollow.setStatus(KFFConstants.STATUS_INACTIVE);
				existFollow.setUpdateTime(now);
				kffFollowService.update(existFollow);
				followNumDecrease = true;
			} else {
				logger.warn("重复取消关注");
			}
		}

		// 关注数量减1
		if (followNumDecrease) {
			if (followType == 1) {
				kffProjectService.decreaseFollowerNum(followedId);
			} else if (followType == 3) {
				kffUserService.decreaseFansNum(followedUserId);
			}
		}
	}

	@Override
	public void saveCollect(Integer userId, Integer postId) throws RestServiceException {
		if (userId == null) {
			throw new RestServiceException("用户id不能为空");
		}
		if (postId == null) {
			throw new RestServiceException("帖子id不能为空");
		}
		KFFUser user = kffUserService.findById(userId);
		if (user == null) {
			throw new RestServiceException("用户不存在" + userId);
		}
		Post post = kffPostService.findById(postId);
		if (post == null) {
			throw new RestServiceException("帖子不存在" + postId);
		}
		Date now = new Date();
		boolean collectNumIncrease = false;
		Collect existCollect = kffCollectService.findByPostId(userId, postId);
		if (existCollect == null) {
			Collect save = new Collect();
			save.setCollectUserId(userId);
			save.setCreateTime(now);
			save.setPostId(postId);
			save.setPostType(post.getPostType());
			save.setProjectId(post.getProjectId());
			save.setStatus(KFFConstants.STATUS_ACTIVE);
			save.setUpdateTime(now);
			kffCollectService.save(save);
			collectNumIncrease = true;
		} else {
			if (existCollect.getStatus() != null && existCollect.getStatus() == KFFConstants.STATUS_INACTIVE) {
				existCollect.setUpdateTime(now);
				existCollect.setStatus(KFFConstants.STATUS_ACTIVE);
				kffCollectService.update(existCollect);
				collectNumIncrease = true;
			} else {
				logger.warn("已收藏存在重复收藏");
				// throw new RestServiceException("已收藏，请勿重复收藏");
			}
		}
		// 帖子收藏数加1
		if (collectNumIncrease) {
			kffPostService.increaseCollectNum(postId);
		}

	}

	@Override
	public void cancelCollect(Integer userId, Integer postId) throws RestServiceException {
		if (userId == null) {
			throw new RestServiceException(RestErrorCode.USER_ID_BLANK);
		}
		if (postId == null) {
			throw new RestServiceException(RestErrorCode.POST_ID_BLANK);
		}
		KFFUser user = kffUserService.findById(userId);
		if (user == null) {
			throw new RestServiceException("用户不存在" + userId);
		}
		Post post = kffPostService.findById(postId);
		if (post == null) {
			throw new RestServiceException("帖子不存在" + post);
		}
		Date now = new Date();
		boolean collectNumDecrese = false;
		Collect existCollect = kffCollectService.findByPostId(userId, postId);
		if (existCollect == null) {
			Collect save = new Collect();
			save.setCollectUserId(userId);
			save.setCreateTime(now);
			save.setPostId(postId);
			save.setPostType(post.getPostType());
			save.setProjectId(post.getProjectId());
			save.setStatus(KFFConstants.STATUS_INACTIVE);
			save.setUpdateTime(now);
			;
			kffCollectService.save(save);
			collectNumDecrese = true;
		} else {
			if (existCollect.getStatus() != null && existCollect.getStatus() == KFFConstants.STATUS_ACTIVE) {
				existCollect.setUpdateTime(now);
				existCollect.setStatus(KFFConstants.STATUS_INACTIVE);
				kffCollectService.update(existCollect);
				collectNumDecrese = true;

			} else {
				logger.warn("已取消收藏存在重复取消收藏");
				// throw new RestServiceException("已取消收藏存在重复取消收藏");
			}
		}
		// 帖子收藏数减1
		if (collectNumDecrese) {
			kffPostService.decreaseCollectNum(postId);
		}

	}

	@Override
	public PageResult<PostResponse> findPageRecommendList(Integer loginUserId, PaginationQuery query) throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> postResponse = new ArrayList<>();
		PageResult<Post> posts = kffPostService.findPage(query);
		KFFUser loginUser = null;
		if (loginUserId != null) {
			loginUser = kffUserService.findById(loginUserId);
		}
		/**
		 * 
		 * 解决登录送奖励问题
		 * 
		 * 
		 */
		if (loginUserId != null) {

			registerAward(loginUserId);

		}

		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for (Post post : posts.getRows()) {
				PostResponse response = new PostResponse();
				response.setcreateTime(post.getCreateTime());
				response.setCreateUserId(post.getCreateUserId());
				response.setPostId(post.getPostId());
				response.setPostType(post.getPostType());
				response.setProjectId(post.getProjectId());
				response.setStatus(post.getStatus());

				// 设置post和project信息
				response.setPostShortDesc(post.getPostShortDesc());
				response.setPostSmallImages(post.getPostSmallImages());
				if (StringUtils.isNotBlank(post.getPostSmallImages())) {
					try {
						List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
						response.setPostSmallImagesList(pfl);
					} catch (Exception e) {
						logger.error("首页推荐列表解析帖子缩略图json出错:{}", e);
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
				if (project != null) {
					Evaluation eva = kffEvaluationService.findByPostId(post.getPostId());
					response.setProjectChineseName(project.getProjectChineseName());
					response.setProjectCode(project.getProjectCode());
					response.setProjectEnglishName(project.getProjectEnglishName());
					response.setProjectIcon(project.getProjectIcon());
					response.setProjectSignature(project.getProjectSignature());
					if (null != eva) {
						response.setTotalScore(eva.getTotalScore());
					} else {
						response.setTotalScore(project.getTotalScore());
					}
				}

				// 设置项目关注状态
				if (loginUser == null) {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				} else {
					Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT, post.getProjectId());
					if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
						response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
					} else {
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
	public PageResult<PostResponse> findPageFollowList(Integer loginUserId, PaginationQuery query) throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> postResponse = new ArrayList<>();
		PageResult<Post> posts = kffPostService.findPage(query);
		KFFUser loginUser = null;
		if (loginUserId != null) {
			loginUser = kffUserService.findById(loginUserId);
		}
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for (Post post : posts.getRows()) {
				PostResponse response = new PostResponse();
				response.setcreateTime(post.getCreateTime());
				response.setCreateUserId(post.getCreateUserId());
				response.setPostId(post.getPostId());
				response.setPostType(post.getPostType());
				response.setProjectId(post.getProjectId());
				response.setStatus(post.getStatus());
				// 设置post和project信息
				response.setPostShortDesc(post.getPostShortDesc());
				response.setPostSmallImages(post.getPostSmallImages());
				logger.info("post.getPostSmallImages()" + post.getPostSmallImages());
				if (StringUtils.isNotBlank(post.getPostSmallImages())) {
					try {
						List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
						response.setPostSmallImagesList(pfl);
						for (PostFile postFile : pfl) {
							logger.info("postFile" + postFile.getFileUrl());
						}

					} catch (Exception e) {
						logger.error("首页关注列表解析帖子缩略图json出错:{}", e);
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
				if (project != null) {
					response.setProjectChineseName(project.getProjectChineseName());
					response.setProjectCode(project.getProjectCode());
					response.setProjectEnglishName(project.getProjectEnglishName());
					response.setProjectIcon(project.getProjectIcon());
					response.setProjectSignature(project.getProjectSignature());
					response.setTotalScore(project.getTotalScore());
				}

				// 设置项目关注状态
				if (loginUser == null) {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				} else {
					Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT, post.getProjectId());
					if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
						response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
					} else {
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
	public List<ProjectResponse> findPageProjectRankList(Integer loginUserId, PaginationQuery query) throws RestServiceException {
		// 登录和非登录用户区别只有关注状态按钮显示
		KFFUser loginUser = null;
		if (loginUserId != null) {
			loginUser = kffUserService.findById(loginUserId);
		}
		List<ProjectResponse> result = new ArrayList<>();
		PageResult<KFFProject> projects = kffProjectService.findPage(query);
		if (projects != null && CollectionUtils.isNotEmpty(projects.getRows())) {
			for (KFFProject project : projects.getRows()) {
				ProjectResponse response = new ProjectResponse();
				BeanUtils.copyProperties(project, response);
				if (loginUser == null) {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				} else {
					Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT, project.getProjectId());
					if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
						response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
					} else {
						response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
					}
				}

				result.add(response);
			}
		}
		return result;
	}

	@Override
	public ArticleDetailResponse findArticleDetail(Integer userId, Integer postId) throws RestServiceException {
		ArticleDetailResponse response = new ArticleDetailResponse();
		// 登录和非登录用户区别只有关注状态按钮显示
		KFFUser loginUser = null;
		if (userId != null) {
			loginUser = kffUserService.findById(userId);
		}
		if (postId == null || postId == 0) {
			throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
		}
		Post post = kffPostService.findById(postId);
		if (post == null) {
			throw new RestServiceException(RestErrorCode.POST_NOT_EXIST);
		}
		BeanUtils.copyProperties(post, response);

		Article article = kffArticleService.findByPostId(postId);
		response.setArticleContents(article == null ? "" : article.getArticleContents());
		// 关注状态
		if (loginUser == null) {
			response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		} else {
			// 返回对帖子用户的关注状态
			Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_USER, post.getCreateUserId());
			if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
				response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
			} else {
				response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
			}
		}

		// 点赞状态
		if (loginUser == null) {
			response.setPraiseStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		} else {
			Praise praise = kffPraiseService.findByPostId(userId, postId);
			if (praise != null && praise.getStatus() != null && praise.getStatus() == KFFConstants.STATUS_ACTIVE) {
				response.setPraiseStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
			} else {
				response.setPraiseStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
			}
		}
		// 收藏状态
		if (loginUser == null) {
			response.setCollectStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		} else {
			Collect collect = kffCollectService.findByPostId(userId, postId);
			if (collect != null && collect.getStatus() != null && collect.getStatus() == KFFConstants.STATUS_ACTIVE) {
				response.setCollectStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
			} else {
				response.setCollectStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
			}
		}
		if (loginUser != null) {
			response.setUserType(loginUser.getUserType());
		}
		// 赞赏用户列表最多8个
		List<Commendation> donateUsers = new ArrayList<>();
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("postId", postId + "");
		query.addQueryData("status", "1");
		query.setPageIndex(1);
		query.setRowsPerPage(8);
		PageResult<Commendation> pages = kffCommendationService.findPage(query);
		if (pages != null && CollectionUtils.isNotEmpty(pages.getRows())) {
			donateUsers = pages.getRows();
		}
		response.setCommendationList(donateUsers);
		// 总捐赠金额
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("postId", postId + "");
		totalMap.put("status", "1");
		BigDecimal commendationNum = kffCommendationService.findCommendationNum(totalMap);
		response.setCommendationNum(commendationNum);

		kffPostService.increasePageviewNum(postId);

		return response;
	}

	@Override
	public List<Comments> findPageHotCommentsList(Integer userId, Integer postId, PaginationQuery query) throws RestServiceException {
		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		List<Comments> result = new ArrayList<>();
		PageResult<Comments> comments = kffCommentsService.findPage(query);
		if (comments != null && CollectionUtils.isNotEmpty(comments.getRows())) {
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(10);
			// childQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
			for (Comments comment : comments.getRows()) {
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				query.addQueryData("parentCommentsId", comment.getCommentsId() + "");
				PageResult<Comments> childComments = kffCommentsService.findPage(query);
				if (childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())) {
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}

				// 登录用户判断点赞状态
				if (user != null) {
					Praise praise = kffPraiseService.findByCommentsId(userId, comment.getCommentsId());
					if (praise != null) {
						finalComment.setPraiseStatus(praise.getStatus());
					}
				} else {
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				result.add(finalComment);
			}
		}
		return result;
	}

	@Override
	public List<Comments> findPageHotCommentsListDis(Integer userId, Integer postId, PaginationQuery query) throws RestServiceException {
		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		List<Comments> result = new ArrayList<>();
		List<Comments> resultp = new ArrayList<>();

		PageResult<Comments> comments = kffCommentsService.findPage(query);
		int i = 0;
		for (Comments comments2 : comments.getRows()) {

			if (comments2.getParentCommentsId() == null) {
				i = i + 1;
				resultp.add(comments2);
				if (i == 2) {
					break;
				}

			}
		}

		if (resultp != null && CollectionUtils.isNotEmpty(resultp)) {
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			childQuery.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS + "");
			for (Comments comment : resultp) {
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				query.addQueryData("parentCommentsId", comment.getCommentsId() + "");
				PageResult<Comments> childComments = kffCommentsService.findPage(query);
				if (childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())) {
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}

				// 登录用户判断点赞状态
				if (user != null) {
					Praise praise = kffPraiseService.findByPostId(userId, comment.getCommentsId());
					if (praise != null) {
						finalComment.setPraiseStatus(praise.getStatus());
					}
				} else {
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				result.add(finalComment);
			}
		}
		return result;
	}

	@Override
	public PageResult<Comments> findPageNewestComments(Integer userId, Integer postId, PaginationQuery query) throws RestServiceException {
		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		PageResult<Comments> result = new PageResult<>();
		List<Comments> finalCommentList = new ArrayList<>();
		PageResult<Comments> comments = kffCommentsService.findPage(query);
		if (comments != null && CollectionUtils.isNotEmpty(comments.getRows())) {
			result.setCurPageNum(comments.getCurPageNum());
			result.setPageSize(comments.getPageSize());
			result.setRowCount(comments.getRowCount());
			result.setRowsPerPage(comments.getRowsPerPage());

			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			childQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
			for (Comments comment : comments.getRows()) {
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				childQuery.addQueryData("parentCommentsId", comment.getCommentsId() + "");
				PageResult<Comments> childComments = kffCommentsService.findPage(childQuery);
				if (childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())) {
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}

				// 登录用户判断点赞状态
				if (user != null) {
					Praise praise = kffPraiseService.findByCommentsId(userId, comment.getCommentsId());
					if (praise != null) {
						finalComment.setPraiseStatus(praise.getStatus());
					}
				} else {
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				finalCommentList.add(finalComment);
			}
			result.setRows(finalCommentList);
		}
		return result;
	}

	@Override
	public List<Comments> findAllChildCommentsList(Integer userId, Integer commentsId, PaginationQuery query) throws RestServiceException {
		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		List<Comments> result = new ArrayList<>();
		PageResult<Comments> comments = kffCommentsService.findPage(query);
		if (comments != null && CollectionUtils.isNotEmpty(comments.getRows())) {
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			for (Comments comment : comments.getRows()) {
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				childQuery.addQueryData("parentCommentsId", comment.getCommentsId() + "");
				PageResult<Comments> childComments = kffCommentsService.findPage(childQuery);
				if (childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())) {
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}
				// 登录用户判断点赞状态
				if (user != null) {
					Praise praise = kffPraiseService.findByCommentsId(userId, comment.getCommentsId());
					if (praise != null) {
						finalComment.setPraiseStatus(praise.getStatus());
					}
				} else {
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				result.add(finalComment);
			}
		}
		return result;
	}

	@Override
	public DiscussDetailResponse findDiscussDetail(Integer userId, Integer postId) throws RestServiceException {
		DiscussDetailResponse response = new DiscussDetailResponse();
		// 登录和非登录用户区别只有关注状态按钮显示
		KFFUser loginUser = null;
		if (userId != null) {
			loginUser = kffUserService.findById(userId);
		}
		if (postId == null || postId == 0) {
			throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
		}
		Post post = kffPostService.findById(postId);
		if (post == null) {
			throw new RestServiceException(RestErrorCode.POST_NOT_EXIST);
		}
		BeanUtils.copyProperties(post, response);

		Discuss discuss = kffDiscussService.findByPostId(postId);
		if (discuss != null) {
			response.setDisscussContents(discuss.getDisscussContents());
			response.setDiscussId(discuss.getDiscussId());
			response.setTagInfos(discuss.getTagInfos());
		}
		if (loginUser == null) {
			response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		} else {
			// 返回对帖子用户的关注状态
			Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_USER, post.getCreateUserId());
			if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
				response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
			} else {
				response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
			}
		}

		// 点赞状态
		if (loginUser == null) {
			response.setPraiseStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		} else {
			Praise follow = kffPraiseService.findByPostId(userId, postId);
			if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
				response.setPraiseStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
			} else {
				response.setPraiseStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
			}
		}

		// 收藏状态
		if (loginUser == null) {
			response.setCollectStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		} else {
			Collect collect = kffCollectService.findByPostId(userId, postId);
			if (collect != null && collect.getStatus() != null && collect.getStatus() == KFFConstants.STATUS_ACTIVE) {
				response.setCollectStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
			} else {
				response.setCollectStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
			}
		}
		if (null != loginUser) {
			response.setUserType(loginUser.getUserType());
		}
		// /2条超过20个点赞的，最高的 热门评论
		List<Comments> hotCommentsresult = new ArrayList<>();
		PaginationQuery hotQuery = new PaginationQuery();
		hotQuery.setPageIndex(1);
		hotQuery.setRowsPerPage(2);
		hotQuery.addQueryData("postId", postId + "");
		hotQuery.addQueryData("status", KFFConstants.STATUS_ACTIVE + "");
		hotQuery.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS + "");
		hotQuery.addQueryData("praiseNum", "20"); // 测试阶段可以临时注释掉
		hotQuery.addQueryData("sortField", "praise_num");
		PageResult<Comments> comments = kffCommentsService.findPage(hotQuery);
		if (comments != null && CollectionUtils.isNotEmpty(comments.getRows())) {
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			childQuery.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS + "");
			for (Comments comment : comments.getRows()) {
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				childQuery.addQueryData("parentCommentsId", comment.getCommentsId() + "");
				PageResult<Comments> childComments = kffCommentsService.findPage(childQuery);
				if (childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())) {
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}
				// 登录用户判断点赞状态
				if (loginUser != null) {
					Praise praise = kffPraiseService.findByCommentsId(userId, comment.getCommentsId());
					if (praise != null) {
						finalComment.setPraiseStatus(praise.getStatus());
					}
				} else {
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				hotCommentsresult.add(finalComment);
			}
		}

		response.setHotComments(hotCommentsresult);

		/*
		 * //赞赏用户列表最多8个 List<Commendation> donateUsers = new ArrayList<>();
		 * PaginationQuery query = new PaginationQuery();
		 * query.addQueryData("postId", postId+""); query.addQueryData("status",
		 * KFFConstants.STATUS_ACTIVE + ""); query.setPageIndex(1);
		 * query.setRowsPerPage(8); PageResult<Commendation> pages =
		 * kffCommendationService.findPage(query); if(pages != null &&
		 * CollectionUtils.isNotEmpty(pages.getRows())){ donateUsers =
		 * pages.getRows(); } response.setCommendationList(donateUsers); //总捐赠金额
		 * Map<String,Object> totalMap = new HashMap<String,Object>();
		 * totalMap.put("postId", postId+""); totalMap.put("status", "1");
		 * BigDecimal commendationNum =
		 * kffCommendationService.findCommendationNum(totalMap);
		 * response.setCommendationNum(commendationNum);
		 */
		kffPostService.increasePageviewNum(postId);
		return response;
	}

	@Override
	public PageResult<Comments> findPageDiscussCommentsList(Integer userId, PaginationQuery query) throws RestServiceException {
		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		List<Comments> result = new ArrayList<>();
		PageResult<Comments> comments = kffCommentsService.findPage(query);
		if (comments != null && CollectionUtils.isNotEmpty(comments.getRows())) {
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			childQuery.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS + "");
			int loopCounter = 0;
			for (Comments comment : comments.getRows()) {
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				finalComment.setFloor(comments.getRowCount() - (comments.getCurPageNum() - 1) * comments.getPageSize() - loopCounter);
				childQuery.addQueryData("parentCommentsId", comment.getCommentsId() + "");
				PageResult<Comments> childComments = kffCommentsService.findPage(childQuery);
				if (childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())) {
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}
				// 登录用户判断点赞状态
				if (user != null) {
					Praise praise = kffPraiseService.findByCommentsId(userId, comment.getCommentsId());
					if (praise != null) {
						finalComment.setPraiseStatus(praise.getStatus());
					}
				} else {
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				result.add(finalComment);

				loopCounter++;
			}
			comments.setRows(result);
		}
		return comments;
	}

	@Override
	public EvaluationDetailResponse findEvaluationDetail(Integer userId, Integer postId) throws RestServiceException {
		EvaluationDetailResponse response = new EvaluationDetailResponse();
		// 登录和非登录用户区别只有关注状态按钮显示
		KFFUser loginUser = null;
		if (userId != null) {
			loginUser = kffUserService.findById(userId);
		}
		if (postId == null || postId == 0) {
			throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
		}
		Post post = kffPostService.findById(postId);
		if (post == null) {
			throw new RestServiceException(RestErrorCode.POST_NOT_EXIST);
		}
		BeanUtils.copyProperties(post, response);

		Evaluation eva = kffEvaluationService.findByPostId(postId);
		if (eva == null) {
			throw new RestServiceException(RestErrorCode.EVA_NOT_EXIST);
		}
		response.setEvauationContent(eva.getEvauationContent());
		response.setProfessionalEvaDetail(eva.getProfessionalEvaDetail());
		response.setModelType(eva.getModelType());
		response.setEvaluationId(eva.getEvaluationId());
		response.setTotalScore(eva.getTotalScore());
		response.setEvaluationTags(eva.getEvaluationTags());
		if (loginUser == null) {
			response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		} else {
			// 返回对帖子用户的关注状态
			Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_USER, post.getCreateUserId());
			if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
				response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
			} else {
				response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
			}
		}

		// 点赞状态
		if (loginUser == null) {
			response.setPraiseStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		} else {
			Praise follow = kffPraiseService.findByPostId(userId, postId);
			if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
				response.setPraiseStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
			} else {
				response.setPraiseStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
			}
		}
		// 收藏状态
		if (loginUser == null) {
			response.setCollectStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		} else {
			Collect collect = kffCollectService.findByPostId(userId, postId);
			if (collect != null && collect.getStatus() != null && collect.getStatus() == KFFConstants.STATUS_ACTIVE) {
				response.setCollectStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
			} else {
				response.setCollectStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
			}
		}
		if (null != loginUser) {
			response.setUserType(loginUser.getUserType());
		}
		// 赞赏用户列表最多8个
		List<Commendation> donateUsers = new ArrayList<>();
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("postId", postId + "");
		query.addQueryData("status", KFFConstants.STATUS_ACTIVE + "");
		query.setPageIndex(1);
		query.setRowsPerPage(8);
		PageResult<Commendation> pages = kffCommendationService.findPage(query);
		if (pages != null && CollectionUtils.isNotEmpty(pages.getRows())) {
			donateUsers = pages.getRows();
		}
		response.setCommendationList(donateUsers);
		// 总捐赠金额
		Map<String, Object> totalMap = new HashMap<String, Object>();
		totalMap.put("postId", postId + "");
		totalMap.put("status", "1");
		BigDecimal commendationNum = kffCommendationService.findCommendationNum(totalMap);
		response.setCommendationNum(commendationNum);

		kffPostService.increasePageviewNum(postId);

		return response;

	}

	@Override
	public PostResponse findMyLatestDiscuss(Integer loginUserId) throws RestServiceException {
		if (loginUserId == null)
			return null;
		PostResponse result = new PostResponse();

		PaginationQuery query = new PaginationQuery();
		query.addQueryData("createUserId", loginUserId + "");
		query.addQueryData("status", KFFConstants.STATUS_ACTIVE + "");
		// 帖子类型：1-评测；2-讨论；3-文章
		query.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS + "");
		query.setPageIndex(1);
		query.setRowsPerPage(1);
		PageResult<Post> posts = kffPostService.findPage(query);
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			Post post = posts.getRows().get(0);
			BeanUtils.copyProperties(post, result);
			if (StringUtils.isNotBlank(post.getPostSmallImages())) {
				try {
					List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
					result.setPostSmallImagesList(pfl);
				} catch (Exception e) {
					logger.error("我的最新讨论帖子解析帖子缩略图json出错:{}", e);
				}
			}
		}
		return result;
	}

	@Override
	public int saveCommentsPraise(Integer userId, Integer commentsId) throws RestServiceException {
		int result = 0;
		if (userId == null) {
			throw new RestServiceException("用户id不能为空");
		}
		if (commentsId == null) {
			throw new RestServiceException("评论id不能为空");
		}
		KFFUser user = kffUserService.findById(userId);
		if (user == null) {
			throw new RestServiceException("用户不存在" + userId);
		}
		Comments comments = kffCommentsService.findById(commentsId);
		if (comments == null) {
			throw new RestServiceException("评论不存在" + commentsId);
		}
		Date now = new Date();
		boolean praiseNumIncrease = false;
		Praise praise = kffPraiseService.findByCommentsId(userId, commentsId);
		if (praise == null) {
			Praise save = new Praise();
			save.setBepraiseUserId(comments.getCommentUserId());
			save.setCreateTime(now);
			save.setPostId(commentsId);
			save.setPostType(comments.getPostType());
			save.setPraiseUserId(userId);
			save.setProjectId(comments.getProjectId());
			save.setStatus(KFFConstants.STATUS_ACTIVE);
			save.setPraiseType(KFFConstants.PRAISE_TYPE_COMMENTS);
			save.setUpdateTime(now);
			kffPraiseService.save(save);
			praiseNumIncrease = true;
		} else {
			if (praise.getStatus() != null && praise.getStatus() == 0) {
				praise.setUpdateTime(now);
				praise.setStatus(KFFConstants.STATUS_ACTIVE);
				kffPraiseService.update(praise);
				praiseNumIncrease = true;
			} else {
				logger.warn("已点赞存在重复点赞");
				// throw new RestServiceException("已点赞，请勿重复点赞");
			}
		}
		// 帖子点赞数加1
		if (praiseNumIncrease) {
			kffCommentsService.increasePraiseNum(commentsId);
			kffUserService.increasePraiseNum(comments.getCommentUserId());
			// 被点赞用户消息
			KFFMessage message = new KFFMessage();
			message.setContent(user.getUserName() + "点赞了您的评论!");
			message.setCreateTime(now);
			message.setJumpInfo("");
			message.setState(KFFConstants.MESSAGE_STATE_UNREAD);
			message.setStatus(KFFConstants.STATUS_ACTIVE);
			message.setTitle("评论点赞");
			message.setType(KFFConstants.MESSAGE_TYPE_PRAISE);
			message.setUpdateTime(now);
			message.setUserId(comments.getCommentUserId());
			message.setSenderUserId(user.getUserId());
			kffMessageService.save(message);
		}

		/**
		 * 添加评论点赞送积分功能 评论点赞数 +1 的时候去执行以下 送积分操作
		 */
		try {

			// 根据 文章详情对象praise 获取帖子类型
			// 根据commentsId去查询postid
			Comments findByPostId = kffCommentsService.findByPostId(commentsId);
			Integer postId = findByPostId.getPostId();
			// 根据postId去获取post详情对象
			Post post = kffPostService.findById(postId);

			Integer postType = comments.getPostType();

			// 根据点赞人的id 去查看他的有效点赞
			QfIndex qfIndexPraiseUser = qfIndexService.findByUserId(userId);
			Integer qfIndexId = qfIndexPraiseUser.getQfIndexId();
			Integer yxPraise = qfIndexPraiseUser.getYxpraise();

			Integer validPraise = (int) Math.floor(yxPraise);
			// 根据评论的id 去获取发表评论人的id
			Integer commentUserId = comments.getCommentUserId();

			// Post creatUserPost = kffPostService.findById(postId);
			// Integer createUserId = creatUserPost.getCreateUserId();
			// 根据发表评论人的id去获取本身的区分指数
			QfIndex qfIndex = qfIndexService.findByUserId(commentUserId);
			Integer createPostUserQFIndex = qfIndex.getStatusHierarchyType();
			// 发表评论人赞的收益系数
			Double createPUF = createPostUserQFIndex * 0.01d;

			// 创建生成交易流水的交易日期
			Date date = new Date();
			String stringDate = DateUtil.getDate(date, "yyyy-MM-dd");
			String replaceAllDate = stringDate.replaceAll("-", "");
			// 创建业务记录id
			// Praise praiseId = kffPraiseService.findByPostId(userId, postId);
			Praise praiseId = kffPraiseService.findByCommentsId(userId, commentsId);
			String format = String.format("%010d", praiseId.getPraiseId());

			// String format = String.format("%010d", praise.getPraiseId());
			// 判断点赞人是否实名认证
			// UserCard findBycreateUserId = userCardService.findByUserid(commentUserId);
			// UserCard findByUserid = userCardService.findByUserid(userId);
			Double profesEvaluat = 2.00d; // 评测的专业完整版评论赞奖励
			Double aloneEvaluat = 2.00d; // 单项评测评论赞奖励
			Double discuss = 2.00d; // 讨论的评论赞奖励
			Double article = 2.00d; // 文章的评论赞奖励
			// 判断所点赞的文章是不是有效(1有效,0删除,无效)
			CoinProperty coinCommenId = coinPropertyService.findByUserId(comments.getCommentUserId());
			if (post.getStatus() == 1) {
				/**
				 * 有效赞
				 * 
				 */
				// Integer validPraise = (int) Math.floor(yxPraise);
				// 判断 帖子类型是1-评测 , 2-讨论 , 3-文章

				if (postType == 1) {
					// 证明是评测的帖子
					// 判断评测的类型 (1-简单评测, 2-全面专业评测 , 3-部分系统单项评测)
					// 根据文章详情的id 去查询 查询评测类型 返回evaluation 对象
					Evaluation evaluation = kffEvaluationService.findByPostId(postId);

					if (evaluation.getModelType() == 2) {
						// 系统自定义专业完整版评测

						if (praiseId.getPraiseType() == 2) {
							// 证明是 专项完整版评测 评论点赞
							if (praiseId.getStatus() == 1 && validPraise > 0 && validPraise != 0) {
								Tokenrecords tokenrecords = new Tokenrecords();
								Tokenaward tokenaward = new Tokenaward();
								tokenrecords.setFunctionDesc("点赞奖励(评论)");
								tokenrecords.setFunctionType(17);
								tokenrecords.setAmount(new BigDecimal(profesEvaluat * createPUF)); // 点赞奖励生成流水
								tokenrecords.setUserId(commentUserId);
								tokenrecords.setTradeType(1);
								tokenrecords.setRewardGrantType(1);
								tokenrecords.setCreateTime(new Date());

								tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号

								kffTokenrecordsService.save(tokenrecords);

								Double coinLock = coinCommenId.getCoinLock();
								coinLock = coinLock + (profesEvaluat * createPUF);
								coinCommenId.setCoinLock(coinLock);
								coinPropertyService.update(coinCommenId);

								tokenaward.setUserId(commentUserId);
								tokenaward.setTokenAwardFunctionDesc("点赞奖励(评论)");
								tokenaward.setTokenAwardFunctionType(17);
								tokenaward.setDistributionType(2);
								// Double rewardToken = tokenaward.getRewardToken();
								// Double priaiseAward = tokenaward.getPriaiseAward();
								tokenaward.setPriaiseAward(profesEvaluat * createPUF); // 点赞奖励.
								tokenaward.setInviteRewards(profesEvaluat * createPUF);
								tokenaward.setCreateTime(new Date());
								tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
								KFFUser commentUser = kffUserService.findById(commentUserId);
								tokenaward.setUserName(commentUser.getUserName());
								tokenaward.setMobile(commentUser.getMobile());
								kffTokenawardService.save(tokenaward);

								qfIndexService.updateYxPraise(userId);
							}
						}
					}
					if (evaluation.getModelType() == 3) {
						// 用户自定义单项评测
						if (praiseId.getPraiseType() == 2) {
							if (praiseId.getStatus() == 1 && validPraise > 0 && validPraise != 0) {
								// 证明是用户自定义单项评测 评论点赞
								Tokenrecords tokenrecords = new Tokenrecords();
								Tokenaward tokenaward = new Tokenaward();
								tokenrecords.setFunctionDesc("点赞奖励(评论)");
								tokenrecords.setFunctionType(17);
								tokenrecords.setAmount(new BigDecimal(aloneEvaluat * createPUF)); // 点赞奖励生成流水
								tokenrecords.setUserId(commentUserId);
								tokenrecords.setTradeType(1);
								tokenrecords.setRewardGrantType(1);
								tokenrecords.setCreateTime(new Date());
								tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
								kffTokenrecordsService.save(tokenrecords);

								Double coinLock = coinCommenId.getCoinLock();
								coinLock = coinLock + (aloneEvaluat * createPUF);
								coinCommenId.setCoinLock(coinLock);
								coinPropertyService.update(coinCommenId);

								tokenaward.setUserId(commentUserId);
								tokenaward.setTokenAwardFunctionDesc("点赞奖励(评论)");
								tokenaward.setTokenAwardFunctionType(17);
								tokenaward.setDistributionType(2);
								// Double rewardToken = tokenaward.getRewardToken();
								// Double priaiseAward = tokenaward.getPriaiseAward();
								tokenaward.setPriaiseAward(aloneEvaluat * createPUF); // 点赞奖励.
								tokenaward.setInviteRewards(aloneEvaluat * createPUF);
								tokenaward.setCreateTime(new Date());
								tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
								KFFUser commentUser = kffUserService.findById(commentUserId);
								tokenaward.setUserName(commentUser.getUserName());
								tokenaward.setMobile(commentUser.getMobile());
								kffTokenawardService.save(tokenaward);

								qfIndexService.updateYxPraise(userId);
							}
						}
					}
				}
				if (postType == 2) {
					// 证明是讨论的帖子
					//

					if (praiseId.getPraiseType() == 2) {
						// 证明是评论点赞
						Tokenrecords tokenrecords = new Tokenrecords();
						Tokenaward tokenaward = new Tokenaward();
						if (praiseId.getStatus() == 1 && validPraise > 0 && validPraise != 0) {
							// 证明是有效赞
							tokenrecords.setFunctionDesc("点赞奖励(评论)");
							tokenrecords.setFunctionType(17);
							tokenrecords.setAmount(new BigDecimal(discuss * createPUF)); // 点赞奖励生成流水
							tokenrecords.setUserId(commentUserId);
							tokenrecords.setTradeType(1);
							tokenrecords.setRewardGrantType(1);
							tokenrecords.setCreateTime(new Date());
							tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
							tokenrecords.setMemo("用户" + user.getUserName() + "点赞奖励(评论)"); // 流水备注
							kffTokenrecordsService.save(tokenrecords);

							Double coinLock = coinCommenId.getCoinLock();
							coinLock = coinLock + (discuss * createPUF);
							coinCommenId.setCoinLock(coinLock);
							coinPropertyService.update(coinCommenId);

							tokenaward.setUserId(commentUserId);
							tokenaward.setTokenAwardFunctionDesc("点赞奖励(评论)");
							tokenaward.setTokenAwardFunctionType(17);
							tokenaward.setDistributionType(2);
							// Double rewardToken = tokenaward.getRewardToken();
							// Double priaiseAward = tokenaward.getPriaiseAward();
							tokenaward.setPriaiseAward(discuss * createPUF); // 点赞奖励.
							tokenaward.setInviteRewards(discuss * createPUF); // 奖励总数
							tokenaward.setCreateTime(new Date());
							tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
							KFFUser commentUser = kffUserService.findById(commentUserId);
							tokenaward.setUserName(commentUser.getUserName());
							tokenaward.setMobile(commentUser.getMobile());
							kffTokenawardService.save(tokenaward);
							qfIndexService.updateYxPraise(userId);

						}
					}

				}
				if (postType == 3) {
					// 证明是文章的帖子

					if (praiseId.getPraiseType() == 2) {
						// 证明是评论点赞
						Tokenrecords tokenrecords = new Tokenrecords();
						Tokenaward tokenaward = new Tokenaward();
						if (praiseId.getStatus() == 1 && validPraise > 0 && validPraise != 0) {
							// 证明是有效赞
							tokenrecords.setFunctionDesc("点赞奖励(评论)");
							tokenrecords.setFunctionType(17);
							tokenrecords.setAmount(new BigDecimal(article * createPUF)); // 点赞奖励生成流水
							tokenrecords.setUserId(commentUserId);
							tokenrecords.setTradeType(1);
							tokenrecords.setRewardGrantType(1);
							tokenrecords.setCreateTime(new Date());
							tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
							kffTokenrecordsService.save(tokenrecords);

							Double coinLock = coinCommenId.getCoinLock();
							coinLock = coinLock + (article * createPUF);
							coinCommenId.setCoinLock(coinLock);
							coinPropertyService.update(coinCommenId);

							tokenaward.setUserId(commentUserId);
							tokenaward.setTokenAwardFunctionDesc("点赞奖励(评论)");
							tokenaward.setTokenAwardFunctionType(17);
							tokenaward.setDistributionType(2);
							// Double rewardToken = tokenaward.getRewardToken();
							// Double priaiseAward = tokenaward.getPriaiseAward();
							tokenaward.setPriaiseAward(article * createPUF); // 点赞奖励.
							tokenaward.setInviteRewards(article * createPUF); // 点赞总数
							tokenaward.setCreateTime(new Date());
							tokenaward.setAwardBalance(0d); // 线性余额 跟一次性发放的奖励没有关系 默认为0
							KFFUser commentUser = kffUserService.findById(commentUserId);
							tokenaward.setUserName(commentUser.getUserName());
							tokenaward.setMobile(commentUser.getMobile());
							kffTokenawardService.save(tokenaward);
							qfIndexService.updateYxPraise(userId);
						}
					}

				}

				result = comments.getPraiseNum() == null ? 1 : (comments.getPraiseNum() + 1);

			}
		} catch (NullPointerException e) {
			logger.error("请求参数有错!:{}", e);
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int cancelCommentsPraise(Integer userId, Integer commentsId) throws RestServiceException {
		int result = 0;
		if (userId == null) {
			throw new RestServiceException("用户id不能为空");
		}
		if (commentsId == null) {
			throw new RestServiceException("评论id不能为空");
		}
		KFFUser user = kffUserService.findById(userId);
		if (user == null) {
			throw new RestServiceException("用户不存在" + userId);
		}
		Comments comments = kffCommentsService.findById(commentsId);
		if (comments == null) {
			throw new RestServiceException("评论不存在" + commentsId);
		}
		Date now = new Date();
		boolean praiseNumDecrese = false;
		Praise praise = kffPraiseService.findByCommentsId(userId, commentsId);
		if (praise == null) {
			Praise save = new Praise();
			save.setBepraiseUserId(comments.getCommentUserId());
			save.setCreateTime(now);
			save.setPostId(commentsId);
			save.setPostType(comments.getPostType());
			save.setPraiseUserId(userId);
			save.setProjectId(comments.getProjectId());
			save.setStatus(KFFConstants.STATUS_INACTIVE);
			save.setPraiseType(KFFConstants.PRAISE_TYPE_COMMENTS);
			save.setUpdateTime(now);
			;
			kffPraiseService.save(save);
			praiseNumDecrese = true;
		} else {
			if (praise.getStatus() != null && praise.getStatus() == 0) {
				logger.warn("已取消点赞存在重复取消点赞");
				// throw new RestServiceException("已取消点赞存在重复取消点赞");
			} else {
				praise.setUpdateTime(now);
				praise.setStatus(KFFConstants.STATUS_INACTIVE);
				kffPraiseService.update(praise);
				praiseNumDecrese = true;
			}
		}
		// 帖子点赞数减1
		if (praiseNumDecrese) {
			kffCommentsService.decreasePraiseNum(commentsId);
			kffUserService.decreasePraiseNum(comments.getCommentUserId());
		}

		result = comments.getPraiseNum() == null ? 0 : ((comments.getPraiseNum() - 1) < 0 ? 0 : (comments.getPraiseNum() - 1));
		return result;
	}

	@Override
	public List<PostResponse> findHotDiscussList(Integer projectId) throws RestServiceException {
		if (projectId == null) {
			throw new RestServiceException("项目id不能为空");
		}
		// Date day7 = DateUtil.getSpecifiedDateBeforeOrAfter(7).getTime();
		List<PostResponse> respones = new ArrayList<>();
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("projectId", projectId + "");
		// query.addQueryData("sortField", "comments_num");
		query.addQueryData("sortField", "praise_num");
		query.addQueryData("status", "1");
		query.addQueryData("postType", "2");
		// query.addQueryData("createTimeBegin", DateUtil.getDate(day7, "yyyy-MM-dd"));
		query.setPageIndex(1);
		query.setRowsPerPage(2);
		PageResult<Post> posts = kffPostService.findPage(query);
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			for (Post post : posts.getRows()) {
				if (post.getPraiseNum() < 10) {
					continue;
				}
				PostResponse response = new PostResponse();
				BeanUtils.copyProperties(post, response);
				if (StringUtils.isNotBlank(post.getPostSmallImages())) {
					try {
						List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
						response.setPostSmallImagesList(pfl);
					} catch (Exception e) {
						logger.error("讨论列表解析帖子缩略图json出错:{}", e);
					}
				}
				Discuss discuss = kffDiscussService.findByPostId(post.getPostId());
				if (discuss != null) {
					response.setDiscussId(discuss.getDiscussId());
					response.setDisscussContents(discuss.getDisscussContents());
					response.setTagInfos(discuss.getTagInfos());
				}
				respones.add(response);
			}
		}
		return respones;
	}

	@Override
	public List<DevaluationModelDetail> findProjectEvaStat(Integer projectId) throws RestServiceException {
		// TODO 考虑一下怎么实现 临时选一个返回
		if (projectId == null) {
			throw new RestServiceException("项目id不能为空");
		}

		List<DevaluationModelDetail> result = new ArrayList<>();

		// List<Evaluation> evaList =
		// kffEvaluationService.findByProjectId(projectId);

		PaginationQuery query = new PaginationQuery();
		query.addQueryData("modelId", "3");
		query.addQueryData("sortField", "comments_num");
		query.addQueryData("status", "1");
		query.setPageIndex(1);
		query.setRowsPerPage(100);
		List<DevaluationModelDetail> details = findSysEvaModelDetailList();

		if (details != null && CollectionUtils.isNotEmpty(details)) {

			PageResult<Projectevastat> proEvaStat = kffProjectevastatService.findPage(query);
			Map<String, Object> evaMap = new HashMap<String, Object>();
			if (proEvaStat != null && CollectionUtils.isNotEmpty(proEvaStat.getRows())) {
				for (Projectevastat eva : proEvaStat.getRows()) {

				}
			}
			for (DevaluationModelDetail detail : details) {
				float x = 8.1f;
				if (detail.getId() == 10)
					x = 9.1f;
				if (detail.getId() == 13)
					x = 6.1f;
				BigDecimal totalScore = BigDecimal.valueOf(x).setScale(1, BigDecimal.ROUND_HALF_DOWN);

				detail.setTotalScore(totalScore);
				detail.setRaterNum(Integer.valueOf(RandomUtil.produceNumber(3)));

				result.add(detail);
			}

		}
		return result;
	}

	@Override
	public List<DevaluationModelDetail> findSysEvaModelDetailList() throws RestServiceException {
		List<DevaluationModelDetail> result = null;
		PaginationQuery query = new PaginationQuery();
		query.setPageIndex(1);
		query.setRowsPerPage(10);
		query.addQueryData("modelType", KFFConstants.EVA_MODEL_TYPE_FULL_PRO + "");
		query.addQueryData("status", "1");
		PageResult<DevaluationModel> modelPage = kffDevaluationModelService.findPage(query);
		if (modelPage == null || CollectionUtils.isEmpty(modelPage.getRows())) {
			// throw new RestServiceException("当前不存在生效中的系统模型");
			return result;
		}
		DevaluationModel activeModel = modelPage.getRows().get(0);

		PaginationQuery detailQuery = new PaginationQuery();
		detailQuery.setPageIndex(1);
		detailQuery.setRowsPerPage(10);
		detailQuery.addQueryData("modelId", activeModel.getModelId() + "");
		detailQuery.addQueryData("status", "1");
		PageResult<DevaluationModelDetail> modelDetailList = kffDevaluationModelDetailService.findPage(detailQuery);

		if (modelDetailList != null) {
			result = modelDetailList.getRows();
		}
		return result;

	}

	@Override
	public KFFUser updateUserInfo(KFFUser account) throws RestServiceException {
		if (account == null) {
			throw new RestServiceException("account参数为空");
		}
		if (account.getUserId() == null || account.getUserId() == 0) {
			throw new RestServiceException("用户id参数为空");
		}

		KFFUser existUser = kffUserService.findById(account.getUserId());
		if (existUser == null) {
			throw new RestServiceException("用户不存在" + account.getUserId());
		}

		String userName = null;
		String userIcon = null;
		String userSignature = null;
		boolean syncUserInfo = false;
		if (StringUtils.isBlank(existUser.getUserName()) || (!existUser.getUserName().equals(account.getUserName()))) {
			userName = account.getUserName();
			syncUserInfo = true;
		}
		if (StringUtils.isBlank(existUser.getIcon()) || (!existUser.getIcon().equals(account.getIcon()))) {
			userIcon = account.getIcon();
			syncUserInfo = true;
		}
		if (StringUtils.isBlank(existUser.getUserSignature()) || (!existUser.getUserSignature().equals(account.getUserSignature()))) {
			userSignature = account.getUserSignature();
			syncUserInfo = true;
		}
		kffUserService.update(account);

		if (syncUserInfo) {
			taskExecutor.execute(new syncUserInfo(account.getUserId(), userName, userIcon, userSignature));
		}

		return account;
	}

	class syncUserInfo implements Runnable {
		private Integer userId;
		private String userName;
		private String userIcon;
		private String userSignature;

		public syncUserInfo(Integer userId, String userName, String userIcon, String userSignature) {
			this.userId = userId;
			this.userName = userName;
			this.userIcon = userIcon;
			this.userSignature = userSignature;
		}

		@Override
		public void run() {
			syncUserInfo(userId, userName, userIcon, userSignature);
		}
	}

	private void syncUserInfo(Integer userId, String userName, String userIcon, String userSignature) {
		if (userId == null) {
			return;
		}
		// 更新tbcommendation send_user_icon
		// tbcomments comment_user_id comment_user_icon comment_user_name
		// becommented_user_id becommented_user_name becommented_user_icon
		// tbfollow follow_user_id follower_user_name followed_user_id
		// followed_user_signature
		// followed_user_icon followed_user_name
		// tbpost create_user_id create_user_icon create_user_signature
		// create_user_name
		boolean update = false;
		boolean commendationUpdate = false;
		Map<String, Object> commentMap = new HashMap<>();
		Map<String, Object> beCommentMap = new HashMap<>();
		Map<String, Object> followMap = new HashMap<>();
		Map<String, Object> followedMap = new HashMap<>();
		Map<String, Object> postMap = new HashMap<>();
		Map<String, Object> commendationMap = new HashMap<>();

		commentMap.put("commentUserId", userId);
		beCommentMap.put("becommentedUserId", userId);
		followMap.put("followUserId", userId);
		followedMap.put("followedUserId", userId);
		postMap.put("createUserId", userId);
		commendationMap.put("sendUserId", userId);
		if (StringUtils.isNotBlank(userName)) {
			commentMap.put("commentUserName", userName);
			beCommentMap.put("becommentedUserName", userName);
			followMap.put("followerUserName", userName);
			followedMap.put("followedUserName", userName);
			postMap.put("createUserName", userName);
			update = true;
		}
		if (StringUtils.isNotBlank(userIcon)) {
			commentMap.put("commentUserIcon", userIcon);
			beCommentMap.put("becommentedUserIcon", userIcon);
			followedMap.put("followedUserIcon", userIcon);
			postMap.put("createUserIcon", userIcon);
			commendationMap.put("sendUserIcon", userIcon);
			update = true;
			commendationUpdate = true;
		}
		if (update) {
			kffCommentsService.updateUserInfo(commentMap);
			kffCommentsService.updateUserInfo(beCommentMap);
			kffFollowService.updateUserInfo(followMap);
		}
		if (commendationUpdate) {
			kffCommendationService.updateUserInfo(commendationMap);
		}
		if (StringUtils.isNotBlank(userSignature)) {
			followedMap.put("followedUserSignature", userSignature);
			postMap.put("createUserSignature", userSignature);
			update = true;
		}
		if (update) {
			kffFollowService.updateUserInfo(followedMap);
			kffPostService.updateUserInfo(postMap);
		}
	}

	@Override
	public List<Comments> findAllDiscussCommentsList(Integer userId, Integer postId) throws RestServiceException {
		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		List<Comments> result = new ArrayList<>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postId", postId + "");
		map.put("status", KFFConstants.STATUS_ACTIVE + "");
		List<Comments> comments = kffCommentsService.findAllCommentsByWhere(map);
		if (CollectionUtils.isNotEmpty(comments)) {
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			childQuery.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS + "");
			for (Comments comment : comments) {
				Comments finalComment = new Comments();
				BeanUtils.copyProperties(comment, finalComment);
				childQuery.addQueryData("parentCommentsId", comment.getCommentsId() + "");
				PageResult<Comments> childComments = kffCommentsService.findPage(childQuery);
				if (childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())) {
					finalComment.setChildCommentsList(childComments.getRows());
					finalComment.setChildCommentsNum(childComments.getRowCount());
				}
				// 登录用户判断点赞状态
				if (user != null) {
					Praise praise = kffPraiseService.findByPostId(userId, comment.getCommentsId());
					if (praise != null) {
						finalComment.setPraiseStatus(praise.getStatus());
					}
				} else {
					finalComment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
				result.add(finalComment);
			}
		}
		return result;
	}

	/**
	 * 根据用户ID查询用户的身份审核信息
	 * 
	 * 1:待审核 2: 审核通过 3: 审核不通过 4 未进行身份审核',
	 */
	@Override
	public Integer selectStatusByUserID(Integer userId) throws RestServiceException {
		if (null == userId) {
			throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
		}
		List<UserCard> userCards = userCardService.selectStatusByUserID(userId);
		if (null == userCards) {
			return 4;
		}
		return userCards.get(0).getStatus();
	}

	@Override
	public void saveUserIdCard(UserCard userCard) throws RestServiceException {
		userCardService.saveUserIdCard(userCard);

	}

	@Override
	public List<Authentication> selectAuthenticatiobByUserId(Integer userId) throws RestServiceException {
		if (null == userId) {
			throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
		}
		List<Authentication> authentications = authenticationService.selectAuthenticationByUserId(userId);

		return authentications;
	}

	/**
	 * 根据用户ID把authentication插入审核表中
	 */
	@Override
	public void saveAuthenticationByUseId(Integer userId) {
		if (null == userId) {
			throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		}
		Authentication authentication = new Authentication();
		authentication.setUserid(userId);
		authentication.setStatus(4);
		authentication.setValid(1);
		authentication.setCreatedata(new Date());
		authenticationService.saveAuthenByUserId(authentication);

	}

	/**
	 * 从后台获取所有项目名称
	 */
	@Override
	public List<KFFProject> findProjectName() {

		return kffProjectService.findProjectName();

	}

	@Override
	public void updataUserIdStstus(String userRealName, String userCardNum, String photoIviews, Integer userId) {
		if (StringUtils.isEmpty(userCardNum)) {
			throw new RestServiceException("请填写身份证号码");
		}
		String idcard = RegexUtil.IDCARD; // 判断手机是否符合标注
		if (!userCardNum.matches(idcard)) {
			throw new RestServiceException(RestErrorCode.USER_IDCARD_IS_FALSE);
		}
		// 将前台传来的数据进行转化保存在数据库
		if (StringUtils.isEmpty(userRealName)) {
			throw new RestServiceException(RestErrorCode.VCNAME_NULL);
		}
		// 根据用户的ID查询手机手机号
		String phone = kffUserService.findPhoneByUserId(userId);
		if (StringUtils.isEmpty(phone)) {
			throw new RestServiceException(RestErrorCode.PHONE_NULL);
		}
		if (StringUtils.isEmpty(photoIviews)) {

			throw new RestServiceException("请上传身份证");
		}

		// 将URL转化成字符串对象
		String uploadIeviw = this.uploadIeviw(photoIviews);
		UserCard userCard = new UserCard();
		userCard.setUserrealname(userRealName);
		userCard.setUsercardNum(userCardNum);
		userCard.setPositiveofcard(uploadIeviw);
		userCard.setStatus(1);// status 1待审核 2 审核通过 3 未通过审核 4 未提交审核
		userCard.setUpdatatime(new Date());
		userCard.setCreatetime(new Date());
		userCard.setValid(1);
		userCard.setPhone(phone);
		userCard.setUserid(userId);
		this.updataUserIdCard(userCard);

	}

	@Override
	public void updataUserIdCard(UserCard userCard) {
		userCardService.updataUserIdCard(userCard);

	}

	@Override
	public String uploadIeviw(String photoIviews) {
		if (null == photoIviews) {
			throw new RestServiceException(RestErrorCode.PICTURE_UPLOAD_FAIL);
		}

		//
		//
		//
		PhotoParams photoParams = new PhotoParams();
		photoParams.setFileUrl(photoIviews);
		// 取后缀名
		String[] str = photoIviews.split("\\.");
		logger.info(str[0]);
		logger.info(str[1]);
		photoParams.setExtension("ext");

		logger.info(str[0].lastIndexOf("/"));
		str[0].substring(str[0].lastIndexOf("/") + 1);
		logger.info(str[0].substring(str[0].lastIndexOf("/") + 1));
		// imgUrl.substring(imgUrl.lastIndexOf("/")+1);
		photoParams.setIsExist(true);
		photoParams.setFileName("qufen");
		//
		//
		//
		//
		return JSON.toJSONString(photoParams);
	}

	/**
	 * 将前台传的图片list 转化成指定格式,便于存放在数据库中
	 * 
	 * @param photoIviewses
	 * @return
	 */
	@Override
	public String uploadIeviwList(List<String> photoIviewses) {
		if (null == photoIviewses) {
			throw new RestServiceException(RestErrorCode.PICTURE_UPLOAD_FAIL);
		}

		//
		//
		//
		List<PhotoParams> PhotoParamses = new ArrayList<PhotoParams>();
		for (String photoIviews : photoIviewses) {
			PhotoParams photoParams = new PhotoParams();
			photoParams.setFileUrl(photoIviews);
			// 取后缀名
			// String[] str = photoIviews.split("\\.");
			// logger.info(str[0]);
			// logger.info(str[1]);
			photoParams.setExtension("ext");

			//
			//
			//
			//
			/** 88888888888888888888888888 ***/
			//
			photoParams.setFileName("qufen");
			photoParams.setIsExist(true);
			//
			//
			//
			//
			PhotoParamses.add(photoParams);
		}
		return JSON.toJSONString(PhotoParamses);
	}

	@Override
	public void setUserCardAuthentication(Integer userId, String phone) {
		if (null == selectAuthenticatiobByUserId(userId)) {
			throw new RestServiceException(RestErrorCode.SYS_ERROR);
		}
		UserCard userCard = new UserCard();
		userCard.setUserid(userId);
		userCard.setPhone(phone);
		userCard.setCreatetime(new Date());
		userCard.setUpdatatime(new Date());
		userCard.setValid(1);
		userCard.setStatus(4);

		this.saveUserCardOnRegister(userCard);
	}

	public void saveUserCardOnRegister(UserCard userCard) {
		this.saveUserIdCard(userCard);

	}

	@Override
	public Integer selectUserCardNum(String userCardNum) {

		return userCardService.selectUserCardNum(userCardNum);
	}

	@Override
	public Integer selectUserCardStatusByUserId(Integer userId) {

		List<UserCard> userCards = userCardService.selectStatusByUserID(userId);
		if (userCards.size() == 0) {
			return 5;
		}

		UserCard userCard = userCards.get(0);
		return userCard.getStatus();
	}

	@Override
	public Integer selectAuthenticationStatusByUserId(Integer userId) {
		List<Authentication> authentications = authenticationService.selectAuthenticationByUserId(userId);
		if (authentications.size() == 0) {
			return 5;
		}
		return authentications.get(0).getStatus();
	}

	@Override
	public void updataAuthentication(Authentication authentication) {
		if (null == authentication) {
			throw new RestServiceException(RestErrorCode.MISSING_ARGS);
		}
		if (null == authentication.getType()) {
			throw new RestServiceException(RestErrorCode.SYS_ERROR);
		}
		//
		// 项目方认证
		if (1 == authentication.getType()) {
			if (StringUtils.isBlank(authentication.getQufennickname())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getAuthinformation())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			//
			if (authentication.getAuthinformation().length() > 30) {
				throw new RestServiceException("认证信息不能超过30 字!");
			}
			if (StringUtils.isBlank(authentication.getCompany())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getRegistrationnum())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}

			if (null == authentication.getLicencepic()) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (null == authentication.getMissivepic()) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getOperatorname())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getNumber())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			// 添加手机号码合法性判断
			if (authentication.getNumber() != null) {
				String phonefmt = RegexUtil.PHONEREGEX;
				// 判断手机号码手机是否符合标注
				if (!authentication.getNumber().matches(phonefmt)) {
					throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
				}
			}
			if (StringUtils.isBlank(authentication.getMail())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			authentication.setLicencepic(uploadIeviw(authentication.getLicencepic()));
			authentication.setMissivepic(uploadIeviw(authentication.getMissivepic()));
		}
		// 评测媒体认证
		if (2 == authentication.getType()) {
			if (StringUtils.isBlank(authentication.getQufennickname())) {//
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getMedianame())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getMediachannel())) {//
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getLink())) {//
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (null == authentication.getAssistpic()) {//
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (null == authentication.getOperatorname()) {//
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getWechat())) {//
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getMail())) {//
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			authentication.setAssistpic(uploadIeviw(authentication.getAssistpic()));
		}
		// 机构号认证
		if (3 == authentication.getType()) {
			if (StringUtils.isBlank(authentication.getQufennickname())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getAuthinformation())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			// 添加认证信息的合法性判断
			if (authentication.getAuthinformation().length() > 30) {
				throw new RestServiceException("认证信息不能超过30 字!");
			}
			if (StringUtils.isBlank(authentication.getCompany())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getRegistrationnum())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}

			if (null == authentication.getLicencepic()) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (null == authentication.getMissivepic()) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getOperatorname())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			if (StringUtils.isBlank(authentication.getNumber())) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			// 添加手机号码合法性判断
			if (authentication.getNumber() != null) {
				String phonefmt = RegexUtil.PHONEREGEX;
				// 判断手机号码手机是否符合标注
				if (!authentication.getNumber().matches(phonefmt)) {
					throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
				}
			}
			if (null == authentication.getMail()) {
				throw new RestServiceException(RestErrorCode.PARAMS_IS_NULL);
			}
			authentication.setLicencepic(uploadIeviw(authentication.getLicencepic()));
			authentication.setMissivepic(uploadIeviw(authentication.getMissivepic()));
		}

		// 添加区分昵称的合法性判断
		if (authentication.getQufennickname().length() > 10) {
			throw new RestServiceException("区分昵称不能超过10 字!");
		}
		if (StringUtils.isNotBlank(authentication.getMail())) {
			// 正在判断邮箱
			String checkMailfmt = RegexUtil.EMAILREGEX;
			if (!authentication.getMail().matches(checkMailfmt)) {
				throw new RestServiceException(RestErrorCode.EMAIL_INCORRECT);
			}

		}

		if (StringUtils.isNotEmpty(authentication.getRegistrationnum())) {
			logger.info("营业执照的号码长度" + authentication.getRegistrationnum().length());
			if (authentication.getRegistrationnum().length() != 15 && authentication.getRegistrationnum().length() != 18) {
				throw new RestServiceException("营业执照注册号错误");
			}
			if (authentication.getRegistrationnum().length() == 18) {
				// 判断15位营业执照注册号
				String checkCode = RegexUtil.CHECKCODE;
				// 判断手机号码手机是否符合标注
				if (!authentication.getRegistrationnum().matches(checkCode)) {
					throw new RestServiceException("统一社会信用代码格式错误!");
				}
			}
			if (authentication.getRegistrationnum().length() == 15) {
				String checkCode = RegexUtil.CHECKCODE;
				if (!authentication.getRegistrationnum().matches(checkCode)) {
					throw new RestServiceException("营业执照号码格式错误!");
				}
			}
		}
		// 分类型进行参数判断 end
		authentication.setCreatedata(new Date());
		authentication.setStatus(1);
		authentication.setValid(1);

		authenticationService.updataAuthenByUserId(authentication);

	}

	@Override
	public List<Dtags> findAllTagsName() {

		return kffDtagsService.findAllTagsName();

	}

	@Override
	public KFFProject findProjectIdByCodeAndChineseName(KFFProject kffProject) {
		if (null == kffProject) {
			throw new RestServiceException(RestErrorCode.SYS_ERROR);
		}
		return kffProjectService.findProjectIdByCodeAndChineseName(kffProject);
	}

	@Override
	public KFFUser saveUserByphonePass(String phoneNumber, Integer invaUserId, String password) {

		KFFUser user = kffUserService.saveUserByphonePass(phoneNumber, invaUserId, password);
		//

		//
		//
		this.setUserCardAuthentication(user.getUserId(), user.getMobile());

		//
		this.saveAuthenticationByUseId(user.getUserId());

		// 将用户信息同步到区分指数表
		QfIndex qfIndex = new QfIndex();
		qfIndex.setUserId(user.getUserId());
		qfIndex.setContributeContent(0d);
		qfIndex.setLockedPosition(0d);
		qfIndex.setLiveness(0d);
		qfIndex.setInfluence(0d);
		qfIndex.setCommunityAssessment(0d);
		qfIndex.setHealthDegree(0d);
		qfIndex.setStatusHierarchyDesc("刁民");
		qfIndex.setStatusHierarchyType(0);
		qfIndex.setYxpraise(0);
		qfIndex.setCreateTime(new Date());
		qfIndexService.save(qfIndex);
		// 将用户信息同步到钱包表中
		KFFUserWallet kffUserWallet = new KFFUserWallet();
		kffUserWallet.setUserId(user.getUserId());
		kffUserWallet.setUserName(user.getUserName());
		kffUserWallet.setMobile(user.getMemo());
		kffUserWallet.setWalletType(0); // '钱包状态0-未绑定 1-已绑定'
		kFFUserWalletMapper.save(kffUserWallet);

		/******************* 关注邀请人 *********************/
		//

		//
		if (null != invaUserId) {

			KFFUser invaUser = kffUserService.findById(invaUserId);
			if (null == invaUser) {
				throw new RestServiceException("邀请码信息有误,请检查确认邀请码是否正确!");
			}
			// 将信息插入邀请表中
			Follow followUser = new Follow();
			followUser.setFollowUserId(user.getUserId());
			followUser.setFollowType(3);
			followUser.setFollowerUserName(user.getUserName());
			followUser.setFollowedUserId(invaUserId);
			followUser.setFollowedUserSignature(invaUser.getUserSignature());
			followUser.setFollowedUserIcon(invaUser.getIcon());
			followUser.setFollowedUserName(invaUser.getUserName());
			followUser.setFollowedId(invaUserId);
			followUser.setStatus(1);
			followUser.setCreateTime(new Date());
			followUser.setUpdateTime(new Date());
			kffFollowService.save(followUser);
		}
		return user;

	}

	/**
	 * 根据分享的postID 展示post相关信息
	 */
	@Override
	public ArticleDetailResponse findArticleDetailForShare(Integer postId) {
		ArticleDetailShareResponse articleDetailShareResponse = new ArticleDetailShareResponse();

		if (postId == null || postId == 0) {
			throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
		}

		Post post = kffPostService.findById(postId);

		if (post == null) {
			throw new RestServiceException(RestErrorCode.POST_NOT_EXIST);
		}
		BeanUtils.copyProperties(post, articleDetailShareResponse);

		// 放置创建poster的类型 加V
		if (StringUtils.isBlank(post.getCreateUserId() + "")) {
			throw new RestServiceException("创建人id为空");
		}
		// 根据createid 查询user表
		KFFUser kffUser = findUserById(post.getCreateUserId());
		if (null != kffUser) {
			articleDetailShareResponse.setcUsertype(kffUser.getUserType());
		}
		// 赞赏用户列表最多8个
		List<Commendation> donateUsers = new ArrayList<>();
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("postId", postId + "");
		query.addQueryData("status", "1");
		query.setPageIndex(1);
		query.setRowsPerPage(6);
		PageResult<Commendation> pages = kffCommendationService.findPage(query);
		if (pages != null && CollectionUtils.isNotEmpty(pages.getRows())) {
			donateUsers = pages.getRows();
		}
		articleDetailShareResponse.setCommendationList(donateUsers);

		// 根据postID 查询文章详情
		Article acticle = kffArticleService.selectArticleByPostId(postId);
		if (null != acticle) {
			articleDetailShareResponse.setArticleId(acticle.getArticleId());
		}
		articleDetailShareResponse.setArticle(acticle);
		// 根据postid查询project
		KFFProject kFFProject = kffProjectService.findById(post.getProjectId());
		if (null != kFFProject) {
			articleDetailShareResponse.setProjectIcon(kFFProject.getProjectIcon());
		}

		return articleDetailShareResponse;
	}

	@Override
	public ProjectEvaluationDetailShareResponse findEvaluationDetailForShare(Integer postId) {
		ProjectEvaluationDetailShareResponse projectEvaluationDetailShareResponse = new ProjectEvaluationDetailShareResponse();
		if (postId == null || postId == 0) {
			throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
		}
		Post post = kffPostService.findById(postId);

		if (post == null) {
			throw new RestServiceException(RestErrorCode.POST_NOT_EXIST);
		}

		// 放置创建poster的类型 加V
		if (StringUtils.isBlank(post.getCreateUserId() + "")) {
			throw new RestServiceException("创建人id为空");
		}
		// 根据createid 查询user表
		KFFUser kffUser = findUserById(post.getCreateUserId());
		if (null != kffUser) {
			projectEvaluationDetailShareResponse.setcUsertype(kffUser.getUserType());
		}
		post.setUuid(null);
		post.setCreateUserId(null);

		post.setPostType(null);
		post.setUpdateTime(null);
		post.setPostId(null);
		post.setProjectId(null);
		post.setStatus(null);
		projectEvaluationDetailShareResponse.setPost(post);
		// 根据post查询evaluation
		Evaluation evaluation = kffEvaluationService.selectEvaluationByPostId(postId);

		projectEvaluationDetailShareResponse.setEvaluation(evaluation);
		// 根据evaluation的evaluationTags 标签查询标签库 获得对应的tag标签
		List<Commendation> donateUsers = new ArrayList<>();
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("postId", postId + "");
		query.addQueryData("status", "1");
		query.setPageIndex(1);
		query.setRowsPerPage(6);
		PageResult<Commendation> pages = kffCommendationService.findPage(query);
		if (pages != null && CollectionUtils.isNotEmpty(pages.getRows())) {
			donateUsers = pages.getRows();
		}

		for (Commendation donateUser : donateUsers) {
			donateUser.setAmount(null);
			donateUser.setCommendationId(null);
			donateUser.setCreateTime(null);
			donateUser.setPostType(null);
			donateUser.setProjectId(null);
			donateUser.setReceiveUserId(null);
			donateUser.setSendUserId(null);
			donateUser.setUpdateTime(null);
			donateUser.setStatus(null);
			donateUser.setPostId(null);
		}
		// 取权重
		List<DevaluationModel> evaliationModel = findEvaliationModel();
		for (DevaluationModel devaluationModel1 : evaliationModel) {
			devaluationModel1.setModelDesc(null);
			devaluationModel1.setCreateUserId(null);
			devaluationModel1.setCreateTime(null);
			devaluationModel1.setModelType(null);
		}

		projectEvaluationDetailShareResponse.setCommendationList(donateUsers);

		return projectEvaluationDetailShareResponse;
	}

	@Override
	public ProjectEvaluationDetailShareResponse findEvaluationDetailPartForShare(Integer postId) {
		ProjectEvaluationDetailShareResponse projectEvaluationDetailShareResponse = new ProjectEvaluationDetailShareResponse();
		if (postId == null || postId == 0) {
			throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
		}
		Post post = kffPostService.findById(postId);

		if (post == null) {
			throw new RestServiceException(RestErrorCode.POST_NOT_EXIST);
		}
		projectEvaluationDetailShareResponse.setPost(post);

		// 放置创建poster的类型 加V
		if (StringUtils.isBlank(post.getCreateUserId() + "")) {
			throw new RestServiceException("创建人id为空");
		}
		// 根据createid 查询user表
		KFFUser kffUser = findUserById(post.getCreateUserId());
		if (null != kffUser) {
			projectEvaluationDetailShareResponse.setcUsertype(kffUser.getUserType());
		}

		// 根据post查询evaluation
		Evaluation evaluation = kffEvaluationService.selectEvaluationByPostId(postId);

		projectEvaluationDetailShareResponse.setEvaluation(evaluation);

		return projectEvaluationDetailShareResponse;
	}

	@Override
	public void saveUserInvation(Integer userId, String userIdTo2code) {
		if (null == userId) {
			throw new RestServiceException("系统错误,请重新注册");
		}
		if (null == userIdTo2code) {
			throw new RestServiceException("生成二维码错误,请重新注册");
		}
		// 将相关参数插入表中
		UserInvation userInvation = userInvationService.selectUserInvation(userId);
		if (null != userInvation) {
			return;
		}
		userInvationService.saveUserInvation(userId, userIdTo2code);

	}

	@Override
	public CommentsShareRequest findCommentMessage(Integer postId) {
		CommentsShareRequest commentsShareRequest = new CommentsShareRequest();
		if (null == postId) {
			throw new RestServiceException("参数异常");
		}
		Post post = kffPostService.findById(postId);
		if (null == post) {
			throw new RestServiceException("帖子为空");
		}
		// 放置创建poster的类型 加V
		if (StringUtils.isBlank(post.getCreateUserId() + "")) {
			throw new RestServiceException("创建人id为空");
		}
		// 根据createid 查询user表
		KFFUser kffUser = findUserById(post.getCreateUserId());
		if (null != kffUser) {
			commentsShareRequest.setcUsertype(kffUser.getUserType());
		}

		KFFProject project = kffProjectService.findById(post.getProjectId());
		if (null == project) {
			throw new RestServiceException("项目为空!");
		}
		commentsShareRequest.setProjectIcon(project.getProjectIcon());

		commentsShareRequest.setProjectCode(project.getProjectCode());

		commentsShareRequest.setProjectChineseName(project.getProjectChineseName());

		// 进行排序分页查询
		List<Comments> commentUsers = new ArrayList<>();
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("postId", postId + "");
		query.addQueryData("status", "1");
		query.setPageIndex(1);
		query.setRowsPerPage(5);// 展示三条最热门的评论
		PageResult<Comments> pages = kffCommentsService.findPageOrderBy(query);
		if (pages != null && CollectionUtils.isNotEmpty(pages.getRows())) {
			commentUsers = pages.getRows();
		}
		if (null == commentUsers) {
			throw new RestServiceException("当前没有评论!");
		}

		commentsShareRequest.setCommentses(commentUsers);
		// 根据postid 查询discuss
		Discuss discuss = kffDiscussService.findByPostId(postId);

		commentsShareRequest.setDiscuss(discuss);

		PaginationQuery hotQuery = new PaginationQuery();
		hotQuery.addQueryData("status", "1");
		hotQuery.addQueryData("postId", postId + "");
		hotQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
		hotQuery.addQueryData("parentCommentsIdNull", "YES");

		// 点赞数最多的2个评论
		hotQuery.addQueryData("sortField", "praiseNum");
		hotQuery.setPageIndex(1);
		hotQuery.setRowsPerPage(2);
		List<Comments> hotComments = this.findPageHotCommentsList(post.getCreateUserId(), postId, hotQuery);
		commentsShareRequest.setHotComments(hotComments);
		PaginationQuery newQuery = new PaginationQuery();
		newQuery.addQueryData("status", "1");
		newQuery.addQueryData("postId", postId + "");
		newQuery.addQueryData("postType", KFFConstants.POST_TYPE_ARTICLE + "");
		// 最新的4个评论
		newQuery.setPageIndex(1);
		newQuery.setRowsPerPage(4);
		PageResult<Comments> newestComments = this.findPageNewestComments(post.getCreateUserId(), postId, newQuery);
		commentsShareRequest.setNewestComments(newestComments == null ? null : newestComments.getRows());
		return commentsShareRequest;
	}

	@Override
	public List<Authentication> selectAuthenticationByUserId(Integer userId) {

		return authenticationService.selectAuthenticationByUserId(userId);
	}

	@Override
	public List<Evaluation> findEvaliation(Integer projectId) {

		return kffEvaluationService.findEvaliationByProjectId(projectId);
	}

	@Override
	public KFFProject selectProjectByprojectName(String projectName) {
		if (null == projectName) {
			throw new RestServiceException("请选择项目名称!");
		}
		String[] str = projectName.split("\\/");
		System.out.println(str[0]);
		System.out.println(str[1]);
		KFFProject kffProject = new KFFProject();
		kffProject.setProjectChineseName(str[1]);
		kffProject.setProjectCode(str[0]);
		return findProjectIdByCodeAndChineseName(kffProject);

	}

	@Override
	public List<DevaluationModel> findEvaliationModel() {
		List<DevaluationModel> devaluationModels = kffDevaluationModelService.findAll();
		if (devaluationModels == null) {
			throw new RestServiceException("评测系统出错,请联系客服!");
		}
		return devaluationModels;
	}

	@Override
	public List<Evaluation> selectEvaluationByUserId(Evaluation evaluation) {
		if (null == evaluation.getCreateUserId()) {
			throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		}
		return kffEvaluationService.selectEvaluationOrNotByUserId(evaluation);
	}

	@Override
	public DiscussShare findDiscussDetailWAP(Integer postId) {
		DiscussShare discussShare = new DiscussShare();

		if (postId == null || postId == 0) {
			throw new RestServiceException(RestErrorCode.MISSING_ARG_POSTID);
		}
		Post post = kffPostService.findById(postId);
		if (post == null) {
			throw new RestServiceException(RestErrorCode.POST_NOT_EXIST);
		}

		if (StringUtils.isBlank(post.getCreateUserId() + "")) {
			throw new RestServiceException("创建人id为空");
		}
		// 根据createid 查询user表
		KFFUser kffUser = findUserById(post.getCreateUserId());
		if (null != kffUser) {
			discussShare.setcUsertype(kffUser.getUserType());
		}

		Discuss discuss = kffDiscussService.findByPostId(postId);
		discussShare.setTagInfo(discuss.getTagInfos());
		// 防止post
		post.setPostId(null);
		post.setUpdateTime(null);
		post.setUuid(null);
		post.setUpdateTime(null);
		discussShare.setPost(post);

		// 赞赏用户列表最多8个
		List<Commendation> donateUsers = new ArrayList<>();
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("postId", postId + "");
		query.addQueryData("status", "1");
		query.setPageIndex(1);
		query.setRowsPerPage(4);
		PageResult<Commendation> pages = kffCommendationService.findPage(query);
		if (pages != null && CollectionUtils.isNotEmpty(pages.getRows())) {
			donateUsers = pages.getRows();
		}
		// 打赏
		discussShare.setDonateUsers(donateUsers);
		// 根据Postid 获取讨论的标签
		// 防止热门评论

		// 获得当前评论列表所在的楼层
		/*
		 * 
		 * 解决思路 : 根据postID 和 parentcommentsId(为空)
		 * 
		 * 
		 * 根据ID进行排序
		 */
		// 说明有评论
		List<Comments> commentsList = kffCommentsService.findFlootOrderById(postId);
		if (commentsList.size() != 0) {

			/**************** 热门评论产 *****************************/
			/*
			 * 热门评论 // 进行排序分页查询 List<Comments> commentUsers = new ArrayList<>();
			 * PaginationQuery queryhot = new PaginationQuery();
			 * queryhot.addQueryData("postId", postId + "");
			 * queryhot.addQueryData("status", "1"); queryhot.setPageIndex(1);
			 * queryhot.setRowsPerPage(2);// 展示三条最热门的评论 PageResult<Comments>
			 * pageshot = kffCommentsService.findPageOrderBy(queryhot); if (pageshot
			 * != null && CollectionUtils.isNotEmpty(pages.getRows())) {
			 * commentUsers = pageshot.getRows(); } if (null == commentUsers) {
			 * throw new RestServiceException("当前没有评论!"); } // 屏蔽部分参数
			 * List<CommentShareFloot> commentUsersfl = new ArrayList<>(); for
			 * (Comments commentUser : commentUsers) {
			 * commentUser.setCreateTime(null); commentUser.setUpdateTime(null);
			 * commentUser.setBecommentedUserIcon(null);
			 * commentUser.setPostId(null); commentUser.setProjectId(null);
			 * CommentShareFloot commentShareFloot = new CommentShareFloot();
			 * BeanUtils.copyProperties(commentUser, commentShareFloot); Integer
			 * indexOf = commentsList.indexOf(commentUser);
			 * commentShareFloot.setFloot(indexOf);
			 * commentUsersfl.add(commentShareFloot);
			 * 
			 * }
			 */
			// discussShare.setCommentsehot(commentUsers);
			// 根据postid 查询discuss
			/***************** 点赞最多的评论 ****************************/
			// 点赞最多的评论
			PaginationQuery hotQuery = new PaginationQuery();
			hotQuery.addQueryData("status", "1");
			hotQuery.addQueryData("postId", postId + "");
			hotQuery.addQueryData("postType", KFFConstants.POST_TYPE_DISCUSS + "");
			// 点赞数最多的2个评论
			hotQuery.addQueryData("sortField", "praise_num");

			List<Comments> hotComments = this.findPageHotCommentsListDis(post.getCreateUserId(), postId, hotQuery);
			List<CommentShareFloot> commenthotfl = new ArrayList<>();

			for (Comments commentUserHot : hotComments) {

				CommentShareFloot commentShareFloot = new CommentShareFloot();
				BeanUtils.copyProperties(commentUserHot, commentShareFloot);
				int indexOfhot = 0;

				for (Comments commentsli : commentsList) {
					if (commentsli.getCommentsId() == commentUserHot.getCommentsId()) {
						int indexOf = commentsList.indexOf(commentsli);
						indexOfhot = indexOf + 1;
					}
				}

				commentShareFloot.setFloot(indexOfhot);
				commenthotfl.add(commentShareFloot);
			}
			discussShare.setCommentsehot(commenthotfl);
		}
		/******************* 新的2个评论 目前不用 **************************/
		/*
		 * PaginationQuery newQuery = new PaginationQuery();
		 * newQuery.addQueryData("status", "1"); newQuery.addQueryData("postId",
		 * postId + ""); newQuery.addQueryData("postType",
		 * KFFConstants.POST_TYPE_ARTICLE + "");
		 * 
		 * // 最新的2个评论 newQuery.setPageIndex(1); newQuery.setRowsPerPage(2);
		 * List<Comments> newestComments =
		 * this.findPageNewestCommentsDis(post.getCreateUserId(), postId,
		 * newQuery);
		 * 
		 * List<CommentShareFloot> commentnewfl = new ArrayList<>(); for
		 * (Comments commentUsernew : newestComments) {
		 * 
		 * CommentShareFloot commentShareFloot = new CommentShareFloot();
		 * BeanUtils.copyProperties(commentUsernew, commentShareFloot);
		 * 
		 * int indexOfnew = 0;
		 * 
		 * for (Comments commentsli : commentsList) { if
		 * (commentsli.getCommentsId() == commentUsernew.getCommentsId()) {
		 * 
		 * int indexOf = commentsList.indexOf(commentsli); indexOfnew = indexOf
		 * + 1; } }
		 * 
		 * commentShareFloot.setFloot(indexOfnew);
		 * 
		 * Integer indexOf = commentsList.indexOf(commentUsernew);
		 * commentShareFloot.setFloot(indexOf);
		 * commentnewfl.add(commentShareFloot); }
		 * 
		 * // discussShare.setCommentseNew(commentnewfl);
		 *//*********************************************/
		// 获得最多赞的人评论 根据praisenum 排序
		List<Comments> commentZanDuoOnly = kffCommentsService.findBidPraiseNum(postId);
		// 说明有赞
		if (commentZanDuoOnly.size() != 0) {

			CommentShareFloot commentShareFlootZanDuoOnly = new CommentShareFloot();
			BeanUtils.copyProperties(commentZanDuoOnly.get(0), commentShareFlootZanDuoOnly);

			int indexOfzan = 0;

			for (Comments commentsli : commentsList) {
				if (commentsli.getCommentsId() == commentZanDuoOnly.get(0).getCommentsId()) {
					int indexOf = commentsList.indexOf(commentsli);
					indexOfzan = indexOf + 1;
				}
			}

			commentShareFlootZanDuoOnly.setFloot(indexOfzan);
		}
		// discussShare.setCommentseZanDuoOnly(commentShareFlootZanDuoOnly);
		// 设置评论总数
		Integer commentsSum = kffCommentsService.findCommentsSum();
		discussShare.setCommentseSum(commentsSum);
		return discussShare;
	}

	@Override
	public Discuss findDisscussBypostId(Integer postId) throws RestServiceException {

		return kffDiscussService.findDisscussBypostId(postId);
	}

	// 目前不用
	@Override
	public List<Comments> findPageNewestCommentsDis(Integer userId, Integer postId, PaginationQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 邀请奖励 (新增)
	 */
	@Override
	public void registerAward(Integer userId) {
		awardPortService.registerAward(userId);

	}

	/**
	 * token解锁
	 * 
	 * @param userId
	 * @param coinUnlock
	 * @return
	 * @throws RestServiceException
	 */
	@Override
	public List<CoinProperty> findCoinPropertyById(Integer userId, Double coinUnlock) throws RestServiceException {
		KFFUser user = null;
		if (userId != null && userId != 0) {

			user = kffUserService.findById(userId);
		}
		List<CoinProperty> result = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId + "");
		map.put("coinUnlock", coinUnlock + "");
		List<CoinProperty> coinProperty = coinPropertyService.findCoinPropertyByUserId(userId);
		if (CollectionUtils.isNotEmpty(coinProperty)) {
			CoinProperty childQuery = new CoinProperty();
			for (CoinProperty coinPropertys : coinProperty) {
				Double coinLock = coinPropertys.getCoinLock();
				Date date = new Date();
				childQuery.setCoinLock(coinLock -= coinUnlock);
				childQuery.setCoinUnlock(coinUnlock);
				childQuery.setCoinUnlockTime(date);
				childQuery.setCoinUnlockType(1);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date); // 将date数据转移到Calender对象中操作
				cal.add(Calendar.DATE, 30);
				Date time = cal.getTime();
				childQuery.setCoinUnlockUptime(time); // 设置解锁完成的时间
				coinPropertyService.save(childQuery);

				result.add(childQuery);
			}
		} else {
			new RestServiceException(RestErrorCode.SYS_ERROR);
		}

		return result;
	}

	@Override
	public List<CoinProperty> findCoinPropertyById(Integer userId, Date coinUnlockTime, Integer coinUnlockType) throws RestServiceException {
		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		List<CoinProperty> result = new ArrayList<>();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("userId", userId + "");
		map.put("coinUnlockTime", coinUnlockTime + "");
		map.put("coinUnlockType", coinUnlockType + "");
		List<CoinProperty> coinProperty = coinPropertyService.findAllCoinpropertyByWhere(map);
		if (CollectionUtils.isNotEmpty(coinProperty)) {

			CoinProperty childQuery = new CoinProperty();

			for (CoinProperty coinPropertys : coinProperty) {

				Double coinUnlock = coinPropertys.getCoinUnlock();
				Double coinLock = coinPropertys.getCoinLock();
				BigDecimal kffCoinNum = user.getKffCoinNum();
				user.setKffCoinNum(kffCoinNum.add(new BigDecimal(coinUnlock)));
				kffUserService.update(user);
				if (coinUnlockTime != null && userId != null && userId > 0 && coinUnlockType == 1) {

					childQuery.setCoinLock(coinLock += coinUnlock);
					childQuery.setCoinUnlockType(3);
					childQuery.setCoinUnlockUptime(null); // 清空解锁完成时间
					coinPropertyService.update(childQuery);

					result.add(childQuery);
				}
			}
		} else {
			new RestServiceException(RestErrorCode.SYS_ERROR);
		}

		return result;
	}

	@Override
	public List<CoinProperty> findCoinPropertyByUserId(Integer userId) {

		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		List<CoinProperty> result = new ArrayList<>();
		// Map<String,Object> map = new HashMap<String,Object>();

		// map.put("userId", userId + "");

		Double unlockSum = 0d; // 所有解锁中的 币值
		Double coinLockSum = 0d; // 所有锁定中的 币值
		Double coinDistributedSum = 0d; // 发放中的b值
		Double coinUsableSum = 0d; // 可用的币值 也就是说可以提现的
		Double totalAssets = 0d; // 账户总资产
		List<CoinProperty> coinProperty = coinPropertyService.findCoinPropertyByUserId(userId);
		// List<CoinProperty> coinProperty = coinPropertyService.findAllCoinpropertyByWhere(map);
		CoinProperty childQuery = new CoinProperty();

		if (CollectionUtils.isNotEmpty(coinProperty)) {

			for (CoinProperty coinPropertys : coinProperty) {
				if (coinPropertys.getCoinUnlock() != null && coinPropertys.getCoinUnlockType() == 1) {
					// 证明是解锁中的
					Double unlock = coinPropertys.getCoinUnlock(); // 解锁中的币值
					unlockSum += unlock;
				}
				if (coinPropertys.getCoinDistributed() != null) {
					// 证明是发放中的
					Double coinDistributed = coinPropertys.getCoinDistributed(); // 发放中的B
					coinDistributedSum += coinDistributed;
				}
				if (coinPropertys.getCoinLock() != null) {
					Double unlock = coinPropertys.getCoinLock(); // 锁定中的币值
					coinLockSum += unlock;
				}
				if (coinPropertys.getCoinUsable() != null) {
					// 可用的b 也就是说可以提现的
					Double coinUsable = coinPropertys.getCoinUsable();
					coinUsableSum += coinUsable;
				}
			}
			childQuery.setCoinLock(coinLockSum);
			childQuery.setCoinUnlock(unlockSum);
			childQuery.setCoinDistributed(coinDistributedSum);
			childQuery.setCoinUsable(coinUsableSum);
			totalAssets = unlockSum + coinLockSum + coinDistributedSum + coinUsableSum;
			childQuery.setTotalAssets(totalAssets);

			result.add(childQuery);
		} else {
			new Exception("集合没有数据");
		}

		return result;

	}

	/**
	 * 
	 * 发放中的逻辑
	 */
	@Override
	public List<Tokenaward> findAllTokenawardUser(Integer userId) {

		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		List<Tokenaward> result = new ArrayList<>();

		// Map<String, Object> map = new HashMap<String, Object>();

		// map.put("userId", userId + "");

		// List<Tokenaward> coinProperty = tokenawardService.findAllTokenawardByWhere(map);
		List<Tokenaward> coinProperty = tokenawardService.findByUserId(userId);
		// Tokenaward tokenaward = new Tokenaward();
		Double ytokensum = 0d; // 已发放
		Double ftokensum = 0d; // 发放中
		if (CollectionUtils.isNotEmpty(coinProperty)) {

			for (Tokenaward tokenawards : coinProperty) {

				if (tokenawards.getDistributionType() != null && tokenawards.getDistributionType() == 1) {

					/*Integer tokenType = tokenawards.getTokenAwardFunctionType(); // 奖励类型
					Double inviteRewards = tokenawards.getInviteRewards(); // 奖励数量
					Date createTime = tokenawards.getCreateTime(); // 奖励创建的时间
					Integer counter = tokenawards.getCounter(); // 发放的次数
					Double awardBalance = tokenawards.getAwardBalance(); // 线性发放的余额
					Double bDouble = inviteRewards - awardBalance;
					ytokensum += bDouble;// 已经发放的总数
					ftokensum += awardBalance; // 发放中的
					tokenawards.setTokenAwardFunctionType(tokenType);
					tokenawards.setInviteRewards(inviteRewards);
					tokenawards.setCounter(counter);
					tokenawards.setCreateTime(createTime);*/
					// tokenawards.setGiveNext(ftokensum);
					result.add(tokenawards);
					// result.add(tokenawards);
				}

			}
			// coinProperty.
			// Tokenaward tokenaward = new Tokenaward();
			// 发放中的总数
			// tokenaward.setRewardToken(ytokensum);
			// 已发放的总数
			// tokenaward.setGiveNext(ftokensum);
			// result.add(tokenaward);
			// result.addAll(coinProperty);
			// result.add(result);
		}
		return result;
	}

	@Override
	public KFFUserWallet save(Integer userId, String wallet) {

		KFFUser findById = kffUserService.findById(userId);
		String userName = findById.getUserName();
		String mobile = findById.getMobile();
		KFFUserWallet kffUserWallet = new KFFUserWallet();
		kffUserWallet.setUserId(userId);
		kffUserWallet.setUserName(userName);
		kffUserWallet.setMobile(mobile);
		kffUserWallet.setCreatetime(new Date());
		kffUserWallet.setWallet(wallet);
		kffUserWallet.setWalletType(1); // '钱包状态0-未绑定 1-已绑定'
		userWalletService.save(kffUserWallet);

		return kffUserWallet;
	}

	@Override
	public KFFUserWallet updateWallet(Integer userId, String wallet, String walletLater) {
		KFFUserWallet kffUserWallet = new KFFUserWallet();
		kffUserWallet.setUserId(userId);
		kffUserWallet.setWallet(wallet);
		KFFUserWallet findbyWallet = userWalletService.findbyWallet(kffUserWallet);
		findbyWallet.setWallet(walletLater);
		findbyWallet.setUpdatetime(new Date());
		userWalletService.updateWallet(findbyWallet);
		KFFUserWallet findbyWallet2 = userWalletService.findbyWallet(kffUserWallet);
		return findbyWallet2;
	}

	@Override
	public UserCard selectUserCardByUserId(Integer userId) throws RestServiceException {

		List<UserCard> userCards = userCardService.selectStatusByUserID(userId);
		if (userCards.size() == 0) {
			return null;
		}
		QfIndex qfUser = qfIndexService.findByUserId(userId);
		if (qfUser != null) {

			qfUser.setStatusHierarchyType(100);
			qfUser.setStatusHierarchyDesc("平民");
			qfIndexService.update(qfUser);
		}
		return userCards.get(0);
	}

	/**
	 * 目前先用double 根据token_award_function_type=18 userID 获取invite_rewards 的值
	 */
	@Override
	public Double selectInvationAward(Integer userId) throws RestServiceException {
		List<Tokenaward> tokenawards = kffTokenawardService.selectInvationAward(userId);
		if (tokenawards.size() == 0) {
			return 0.0;
		}
		Double inviteRewardSum = 0.0;
		for (Tokenaward tokenaward : tokenawards) {
			Double inviteRewards = tokenaward.getInviteRewards();
			inviteRewardSum = inviteRewardSum + inviteRewards;
		}
		return inviteRewardSum;
	}

	@Override
	public Integer selectInvationM1Num(Integer userId) throws RestServiceException {
		Integer m1Num = kffUserService.selectInvationM1Num(userId);
		return m1Num;
	}

	@Override
	public Integer selectInvationM2Num(Integer userId) throws RestServiceException {
		Integer m2Num = kffUserService.selectInvationM2Num(userId);
		return m2Num;
	}

	@Override
	public UserInvation selectUseInvation(Integer userId) throws RestServiceException {

		return userInvationService.selectUseInvation(userId);
	}

	@Override
	public void updataUserInvation(Integer userId, String posterUrl, String code2Url) throws RestServiceException {
		String str = HexUtil.userIdTo2code(userId);
		code2Url = "http://pic.qufen.top/" + "2code" + str + "qufen";
		userInvationService.updataUserInvation(userId, posterUrl, code2Url);

	}

	public List<KFFUserWallet> findBywalletAndType(Integer userId) {

		return userWalletService.findBywalletAndType(userId);
	}

	@Override
	public synchronized void aLiYunSmsApi(String phone, String module, String dynamicValidateCode, String cacheKey, String smsStormCheckKey) {
		// TaobaoClient client = new DefaultTaobaoClient(AliyunConstant.SMS_URL,
		// AliyunConstant.SMS_APP_KEY, AliyunConstant.SMS_SECRET);

		Map<String, String> map = new HashMap<String, String>();
		// 随机使用手机号发送信息

		// 设置超时时间-可自行调整
		try {
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			// 初始化ascClient需要的几个参数
			final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
			final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
			// 替换成你的AK
			final String accessKeyId = smsAppkey; // AliyunConstant.SMS_APP_KEY;//
													// 你的accessKeyId,参考本文档步骤2
			final String accessKeySecret = smsSecret;// AliyunConstant.SMS_SECRET;//
														// 你的accessKeySecret，参考本文档步骤2
			// 初始化ascClient,暂时不支持多region（请勿修改）
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			// 组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			// 使用post提交
			request.setMethod(MethodType.POST);
			// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
			// request.setPhoneNumbers(phone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName("区分");// AliyunConstant.SMS_FREE_SIGN_NAME
			// 必填:短信模板-可在短信控制台中找到
			// 在此添加模板类型
			logger.info(module);
			// 登陆
			if (module.equals("login")) {// AliyunConstant.SMS_LOGIN
				request.setTemplateCode(smsLoginTemplateCode);// AliyunConstant.SMS_LOGIN_TEMPLATE_CODE
				// 放置json串
				logger.info(smsLoginTemplateCode);
				map.put("code", dynamicValidateCode);// 放置code 验证码
				phone = sendTelephone.sendTele();
				request.setPhoneNumbers(phone);
			}
			// 注册
			if (module.equals("register")) {
				request.setTemplateCode(smsRegisterTemplateCode);// (AliyunConstant.SMS_REGISTER_TEMPLATE_CODE
				// 放置json串
				logger.info(smsRegisterTemplateCode);
				map.put("code", dynamicValidateCode);// 放置code 验证码
				phone = sendTelephone.sendTele();
				request.setPhoneNumbers(phone);
			}
			// 忘记密码
			if (module.equals("forgetPassword")) {
				request.setTemplateCode(smsForgetpasdwoedTemplateCode);
				// 放置json串
				map.put("code", dynamicValidateCode);// 放置code 验证码
				request.setPhoneNumbers(phone);
			}
			// request.setTemplateCode(AliyunConstant.SMS_REGISTER_TEMPLATE_CODE);
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			String jsonString = JSON.toJSONString(map);
			request.setTemplateParam(jsonString);
			// 可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
			// request.setSmsUpExtendCode("90997");
			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			// request.setOutId("yourOutId");
			// 请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			logger.info(sendSmsResponse.getCode());
			System.out.println(sendSmsResponse.getCode());
			redisService.put(cacheKey, dynamicValidateCode, 60 * 10);// 设置10分钟
			if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				System.out.println("短信发送成功!");
				System.out.println(sendSmsResponse.getCode());
				redisService.put(cacheKey, dynamicValidateCode, 60 * 10);// 设置10分钟
			}
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public PageResult<Tokenaward> findPageMyTokenAwards(PaginationQuery query) {

		return tokenawardService.findPage(query);
	}

	@Override
	public void updateUserWallet(Integer userId, String wallet) {

		userWalletService.updateUserWallet(userId, wallet);
	}

	@Override
	public KFFUserWallet findUserId(Integer userId) {

		return userWalletService.findUserId(userId);
	}

	@Override
	public List<Tokenaward> findByTokenAward(Integer userId) {

		return tokenawardService.findByUserId(userId);
	}

	@Override
	public Tokenaward findToken(Integer userId) {
		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		List<Tokenaward> result = new ArrayList<>();
		Tokenaward tokenaward = new Tokenaward();
		// Map<String, Object> map = new HashMap<String, Object>();

		// map.put("userId", userId + "");

		// List<Tokenaward> coinProperty = tokenawardService.findAllTokenawardByWhere(map);
		List<Tokenaward> coinProperty = tokenawardService.findByUserId(userId);
		if (CollectionUtils.isNotEmpty(coinProperty)) {
			Double ytokensum = 0d; // 已发放
			Double ftokensum = 0d; // 发放中
			for (Tokenaward tokenawards : coinProperty) {
				Integer tokenType = tokenawards.getTokenAwardFunctionType(); // 奖励类型
				Double inviteRewards = tokenawards.getInviteRewards(); // 奖励数量
				Date createTime = tokenawards.getCreateTime(); // 奖励创建的时间
				Integer counter = tokenawards.getCounter(); // 发放的次数
				Double awardBalance = tokenawards.getAwardBalance(); // 线性发放的余额
				if (inviteRewards != null && awardBalance != null && tokenawards.getDistributionType() == 1) {

					BigDecimal bd1 = new BigDecimal(Double.toString(inviteRewards));
					BigDecimal bd2 = new BigDecimal(Double.toString(awardBalance));
					Double bDouble = bd1.subtract(bd2).doubleValue();
					BigDecimal bd3 = new BigDecimal(Double.toString(ytokensum));
					BigDecimal bd4 = new BigDecimal(Double.toString(bDouble));
					ytokensum = bd3.add(bd4).doubleValue();
					BigDecimal bd5 = new BigDecimal(Double.toString(ftokensum));
					BigDecimal bd6 = new BigDecimal(Double.toString(awardBalance));
					ftokensum = bd5.add(bd6).doubleValue();

				}

			}
			// 发放中的总数

			tokenaward.setRewardToken(ytokensum);
			// 已发放的总数
			tokenaward.setGiveNext(ftokensum);
			// result.add(tokenaward);
			// result.addAll(coinProperty);
			tokenaward.setUserId(userId);
			result.add(tokenaward);
		}
		return tokenaward;
	}

	@Override
	public List<Tokenrecords> findAllTokenrecordsUserId(Integer userId) {
		// TODO Auto-generated method stub
		return kffTokenrecordsService.findAllTokenrecordsUserId(userId);
	}

	public PageResult<TokenawardReturn> findPageMyTokenawardReturn(PaginationQuery query) {

		return kffTokenrecordsService.findPageMyTokenawardReturn(query);

	}

	@Override
	public List<Tokenaward> findAllTokenawardUserId(Integer userId) {
		// TODO Auto-generated method stub
		return kffTokenawardService.findAllTokenawardUserId(userId);
	}

	@Override
	public String creat2Code(Integer userId) throws RestServiceException {
		/**
		 * 邀请链接的url
		 */

		// String contentself = "http://192.168.10.196:5000/user/registerSmp?invaUIH=";
		/**
		 * 最终海报的存放路径
		 */
		String posterSysPath = "/upload/poster/";
		/**
		 * 原始海报的存放路径 "D:\\opt\\file\\upload\\poster\\init\\initpic.png
		 */
		String initPosterSysPath = picServiceUrl + "/upload/poster/init/initpic";// 原始海报存放路径
		String text = "";

		String str = HexUtil.userIdTo2code(userId);

		// 调用create2CodeImg 方法生成二维码
		// 调用overlapImage 方法将两个图片合成一个
		// 调用createCharAtImg 方法 向图片上添加文字
		// 返回图片的相对于服务器的绝对地址
		String contentselfid = contentself + str;// 注册链接+邀请码
		String posterSysPathNoUrllast = posterSysPath + DateUtil.getCurrentYearMonth() + "/" + str + ".png";// 不含服务器路径的地址

		String posterSysPathlast = picServiceUrl + posterSysPathNoUrllast;// 含有服务器路径的地址
		try {
			FileUtils.createFileLocal(posterSysPathlast);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RestServiceException("后台创建文件出错!");
		}
		String initPosterSysPathLast = initPosterSysPath + Create2Code.rand1To11() + ".png";// 选出随机海报图片
		String code2Path = create2CodeNameAndPath(str);// 二维码路径
		Create2Code.create2CodeImg(code2Path, contentselfid);// 在制定位置生成二维码

		Create2Code.overlapImage(initPosterSysPathLast, code2Path, posterSysPathlast);
		logger.info("posterSysPathlast" + posterSysPathlast);
		String posterQiniuName = str + "qufen" + DateUtil.getCurrentTimeSS();
		String qiNiuUrl = QiniuUtil.uploadLocalPic(posterSysPathlast, posterQiniuName);
		logger.info("qiNiuUrl" + qiNiuUrl);
		if (StringUtils.isNotEmpty(qiNiuUrl)) {
			// 直接删除本地服务器上的图片
			File file = new File(posterSysPathlast);
			if (file.exists()) {// 说明文件存在
				if (file.isFile()) {
					boolean delete = file.delete();
					if (delete) {
						logger.info("删除成功!");
					} else {
						logger.info("删除失败!");
					}
				}
			}
		}
		// 将二维码生成后的照片上传到七牛云上
		String code2QiniuName = "2code" + str + "qufen";
		String qiNiuUrl2code = QiniuUtil.uploadLocalPic(code2Path, code2QiniuName);
		if (StringUtils.isNotEmpty(qiNiuUrl2code)) {
			// 直接删除本地服务器上的图片
			File file = new File(code2Path);
			if (file.exists()) {// 说明文件存在
				if (file.isFile()) {
					boolean delete = file.delete();
					if (delete) {
						logger.info("删除成功!");
					} else {
						logger.info("删除失败!");
					}
				}
			}
		}
		return qiNiuUrl;

	}

	@Override
	public String create2CodeNameAndPath(String code2) throws RestServiceException {
		String code2sysPath = picServiceUrl + "/upload/2code/";
		String path = code2sysPath + code2 + ".png";
		return path;
	}

	@Override
	public void checkProjectOnlyEvaluation(EvaluationRequest evaluationRequest) {
		// 同一个项目 同一个单项评测 不能多次提交
		String postSmallImages = evaluationRequest.getProfessionalEvaDetail();
		List<EvaDtail> evaDtails = JSON.parseArray(postSmallImages, EvaDtail.class);
		EvaDtail evaDtail = null;
		if (CollectionUtils.isNotEmpty(evaDtails)) {
			evaDtail = evaDtails.get(0);
		}
		List<Evaluation> evaluations = selectOnlyEvaluationByProjectId(evaluationRequest.getProjectId(), evaluationRequest.getCreateUserId(),
				evaluationRequest.getModelType());
		if (CollectionUtils.isNotEmpty(evaluations)) {
			for (Evaluation evaluation : evaluations) {
				String evaDetailDBStr = evaluation.getProfessionalEvaDetail();
				if (StringUtils.isNotEmpty(evaDetailDBStr)) {
					List<EvaDtail> evaDetailDBs = JSON.parseArray(evaDetailDBStr, EvaDtail.class);
					if (CollectionUtils.isNotEmpty(evaDetailDBs)) {
						for (EvaDtail evaDetailDB : evaDetailDBs) {
							if (null != evaDetailDB && null != evaDtail) {
								if (evaDetailDB.getModelName().equals(evaDtail.getModelName())) {
									throw new RestServiceException("不能对同一项目同一指标进行多次评测!");
								}
							}
						}
					}
				}
			}
		}

	}

	@Override
	public List<Evaluation> selectOnlyEvaluationByProjectId(Integer projectId, Integer createUserId, Integer modelType) {

		return kffEvaluationService.selectEvaluationByParam(projectId, createUserId, modelType);

	}

	@Override
	public void checkProjectEvaluation(EvaluationRequest evaluationRequest, KFFProject project) {
		Evaluation evaluationDB = new Evaluation();
		evaluationDB.setModelType(evaluationRequest.getModelType());
		evaluationDB.setCreateUserId(evaluationRequest.getCreateUserId());
		evaluationDB.setProjectId(project.getProjectId());
		List<Evaluation> evaluations = selectEvaluationByUserId(evaluationDB);
		if (CollectionUtils.isNotEmpty(evaluations)) {
			if (evaluations.size() >= 1) {
				throw new RestServiceException("完整评测和自定义评测不能对同一个项目进行重复评测!");
			}

		}

	}

	@Override
	public String createPostShare2Code() {
		// TODO 生成post分享的二维码 待使用

		return null;
	}

	@Override
	public QfIndex findQfIndexUser(Integer loginUserId) {
		// TODO Auto-generated method stub
		return qfIndexService.findByUserId(loginUserId);
	}

	@Override
	public Map<String, String> grabUrlAndReplaceSelf(String content, Integer createId) throws RestServiceException {
		Map<String, String> map = new HashMap<String, String>();

		logger.info("开始进行抽离图片");

		List<String> imgSrc = GetImgUrl.getImgStr(content);
		List<Object> imgDB = new ArrayList<Object>();
		String replaceStr = null;
		String evaluationSrcReplace = null;
		int i = 0;
		for (String img : imgSrc) {
			logger.info("抽离的图片路径");
			logger.info(img);
			if (img.contains(ipPicUrl)) {
				// 说明是h5富文本传来的,服务器中存有图片,直接截取存放数据库
				// http://192.168.10.151:8080/upload/postPic/201806/20180610160559928.png
				logger.info("开始处理H5富文本编译器图片");
				if (ipPicUrl.contains("app.qufen.top")) {
					replaceStr = img.replaceAll("http://" + ipPicUrl + "/", "");
				} else {
					replaceStr = img.replaceAll(ipPicUrl + "/", "");
				}
				logger.info("h5富文本传来的图片绝对路径:" + replaceStr);
				if (imgDB.size() <= 3) {
					imgDB.add(replaceStr);
				}
			} else {
				// 生成url名称
				String picUrlGen = picServiceUrl;
				String picName = "/upload/postPic/" + DateUtil.getCurrentYearMonth() + "/" + DateUtil.getCurrentTimeStamp() + i + createId + ".jpg";
				String picUrlName = picUrlGen + picName;
				try {
					FileUtils.createFileLocal(picUrlName);
				} catch (Exception e) {
					throw new RestServiceException("后台创建文件出错!");
				}
				String picurlIpName = ipPicUrl + picName;
				logger.info("img :原图片路径: " + img);
				Boolean isExistSerive = DownImgGoodUtil.downloadPicture(img, picUrlName);

				if (!isExistSerive) {// isExistSerive 是false
					logger.info("启动处理图片失败预案");
					PhotoParams photoParams = new PhotoParams();
					photoParams.setFileUrl(img);
					photoParams.setIsExist(false);
					if (imgDB.size() <= 3) {
						imgDB.add(photoParams);
					}
					picurlIpName = img;
				}
				logger.info("坑逼百度!!!!");

				if (imgDB.size() <= 3) {
					imgDB.add(picName);
					// i = i + 1;
				}

				logger.info("picurlIpName : 替换后的图片路径: " + picurlIpName);
				if (i == 0) {
					evaluationSrcReplace = content.replaceAll(img, picurlIpName);
				}
				evaluationSrcReplace = evaluationSrcReplace.replaceAll(img, picurlIpName);
				logger.info(evaluationSrcReplace);
				i = i + 1;
			}
		}
		logger.info("图片抽离成功!");
		// 将图片集合转化成json
		// String uploadIeviwList = uploadIeviwList(imgDB);
		String uploadIeviwList = uploadIeviwListObject(imgDB);
		logger.info("缩略图的json串" + uploadIeviwList);
		map.put("uploadIeviwList", uploadIeviwList);
		map.put("evaluationSrcReplace", evaluationSrcReplace);
		return map;
	}

	@Override
	public Map<String, String> grabUrlAndReplaceQiniu(String content, Integer createid) throws RestServiceException {
		Map<String, String> map = new HashMap<String, String>();
		logger.info("开始进行抽离图片");
		List<String> imgSrc = GetImgUrl.getImgStr(content);
		List<String> imgDB = new ArrayList<String>();
		String contentSrcReplace = null;
		int i = 0;
		for (String img : imgSrc) {
			logger.info("抽离的图片路径");
			logger.info(img);
			if (img.contains("http://pic.qufen.top")) {
				// 说明是h5富文本传来的,服务器中存有图片,直接截取存放数据库
				logger.info("h5富文本传来的图片绝对路径:" + img);
				if (imgDB.size() <= 3) {
					imgDB.add(img);
				}
			} else {
				// 生成url名称
				logger.info("img :原图片路径: " + img);
				String fileName = DateUtil.getCurrentTimeSS() + createid + i;
				String UrlQiniu = QiniuUtil.changeToLocalUrl(img, fileName);
				if ("false".equals(UrlQiniu)) {
					logger.info(img + "上传七牛失败!");
					logger.info("坑逼百度!!!!");
				} else {
					if (imgDB.size() <= 3) {
						imgDB.add(UrlQiniu);
					}
					logger.info("picurlIpName : 替换后的图片路径: " + UrlQiniu);
					if (i == 0) {
						logger.info("原img将被代替" + img);
						logger.info("七牛的url" + UrlQiniu);
						contentSrcReplace = content.replace(img, UrlQiniu);
					}
					contentSrcReplace = contentSrcReplace.replace(img, UrlQiniu);
					logger.info(contentSrcReplace);
					i = i + 1;
				}
			}
		}
		logger.info("图片抽离成功!");
		String uploadIeviwList = uploadIeviwListQiniu(imgDB);
		logger.info("缩略图的json串" + uploadIeviwList);
		map.put("uploadIeviwList", uploadIeviwList);
		map.put("contentSrcReplace", contentSrcReplace);
		// logger.info(contentSrcReplace);
		return map;
	}

	@Override
	public String uploadIeviwListQiniu(List<String> photoIviewses) throws RestServiceException {
		if (null == photoIviewses) {
			throw new RestServiceException(RestErrorCode.PICTURE_UPLOAD_FAIL);
		}
		List<PhotoParams> PhotoParamses = new ArrayList<PhotoParams>();
		for (String photoIviews : photoIviewses) {
			PhotoParams photoParams = new PhotoParams();
			photoParams.setFileUrl(photoIviews);
			photoParams.setExtension("ext");
			photoParams.setFileName("qufen");
			photoParams.setSize("1");
			photoParams.setIsExist(true);
			PhotoParamses.add(photoParams);
		}
		return JSON.toJSONString(PhotoParamses);
	}

	@Override
	public PageResult<PostResponse> findPageCounterfeitListList(Integer loginUserId, PaginationQuery query) throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> postResponse = new ArrayList<>();
		PageResult<Post> posts = kffPostService.findPage(query);
		KFFUser loginUser = null;
		if (loginUserId != null) {
			loginUser = kffUserService.findById(loginUserId);
		}

		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for (Post post : posts.getRows()) {
				PostResponse response = new PostResponse();
				response.setcreateTime(post.getCreateTime());
				response.setCreateUserId(post.getCreateUserId());
				response.setPostId(post.getPostId());
				response.setPostType(post.getPostType());
				response.setProjectId(post.getProjectId());
				response.setStatus(post.getStatus());

				// 设置post和project信息
				response.setPostShortDesc(post.getPostShortDesc());
				response.setPostSmallImages(post.getPostSmallImages());
				if (StringUtils.isNotBlank(post.getPostSmallImages())) {
					try {
						List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
						response.setPostSmallImagesList(pfl);
					} catch (Exception e) {
						logger.error("首页推荐列表解析帖子缩略图json出错:{}", e);
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
				if (project != null) {
					response.setProjectChineseName(project.getProjectChineseName());
					response.setProjectCode(project.getProjectCode());
					response.setProjectEnglishName(project.getProjectEnglishName());
					response.setProjectIcon(project.getProjectIcon());
					response.setProjectSignature(project.getProjectSignature());
					response.setTotalScore(project.getTotalScore());
				}

				// 设置项目关注状态
				if (loginUser == null) {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				} else {
					Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT, post.getProjectId());
					if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
						response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
					} else {
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
	public Double findTodayToken(Integer loginUserId) {

		return kffTokenrecordsService.findTodayToken(loginUserId);
	}

	@Override
	public Integer findPopByToken(Integer loginUserId) {
		return kffUserService.findPopByToken(loginUserId);
	}

	@Override
	public void updateUserKFFPop(Integer loginUserId) {
		kffUserService.updateUserKFFPop(loginUserId);
	}

	@Override
	public String getWeiXinTicket() {
		String appId = "wxa034b7003154ee6c";
		String appSecret = "7fce4f1ee0f63b62f20fe8321a31dea8";
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		String access_token = "";
		String ticket = "";
		try {
			Object act = redisService.get("access_token");

			Object apiticket = redisService.get("ticket");
			logger.info("[act] = " + act + " [apiticket] = " + apiticket);
			if (null == act) {

				String url = "https://api.weixin.qq.com/cgi-bin/token";
				String jsonStrToken = HttpUtil.sendGet(url, "grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);

				logger.debug("[jsonStrToken] = " + jsonStrToken);

				JSONObject json = JSONObject.parseObject(jsonStrToken);

				access_token = (String) json.getString("access_token");
				if (access_token == null) {
					return null;
				}
				redisService.put("access_token", access_token, 7200);
				redisService.put("ticket", access_token, 7200);
			} else {
				access_token = (String) act;
			}

			if (null == apiticket) {
				String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
				String jsonStrTicket = HttpUtil.sendGet(url, "access_token=" + access_token + "&type=jsapi");

				logger.debug("[jsonStrTicket] = " + jsonStrTicket);

				JSONObject json = JSONObject.parseObject(jsonStrTicket);
				ticket = (String) json.get("ticket");

			} else {
				ticket = (String) apiticket;
			}
			map.put("ticket", ticket);
			bre.setData(ticket);
		} catch (Exception e) {
			logger.info("生成失败");
			e.printStackTrace();
		}
		return ticket;
	}
}
