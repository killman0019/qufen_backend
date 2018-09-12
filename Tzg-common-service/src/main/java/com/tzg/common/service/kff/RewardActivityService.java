package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.tzg.common.constants.KFFConstants;
import com.tzg.common.enums.DiscussType;
import com.tzg.common.enums.PostType;
import com.tzg.common.enums.RewardActivityState;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.H5AgainDeltagsUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.activity.RewardActivity;
import com.tzg.entitys.kff.activity.RewardActivityMapper;
import com.tzg.entitys.kff.activity.RewardActivityVo;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.discuss.Discuss;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostDiscussVo;
import com.tzg.entitys.kff.post.PostFile;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.praise.Praise;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
@Transactional(rollbackFor=Exception.class)
public class RewardActivityService {
	private static final Log logger = LogFactory.getLog(RewardActivityService.class);
	@Autowired
	private RewardActivityMapper rewardActivityMapper;
	@Autowired
	private UserService kffUserService;
	@Autowired
	private PraiseService kffPraiseService;
	@Autowired
	private FollowService kffFollowService;
	@Autowired
	private ProjectService kffProjectService;
	@Autowired
	private PostService kffPostService;
	@Autowired
	private CoinPropertyService coinPropertyService;
	@Autowired
	private DiscussService discussService;
	
	@Transactional(readOnly = true)
	public RewardActivity findById(java.lang.Integer id) {
		return rewardActivityMapper.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<RewardActivity> findListByAttr(Map<String, Object> map) {
		return rewardActivityMapper.findListByAttr(map);
	}

	@Transactional(readOnly = true)
	public RewardActivity findFirstByAttr(Map<String, Object> map) {
		 List<RewardActivity> reActs = rewardActivityMapper.findListByAttr(map);
		 if(reActs.isEmpty()) {
			 return null;
		 }
		 return reActs.get(0);
	}
	
	public void saveRewardActivity(RewardActivity reAct) throws RestServiceException {
		rewardActivityMapper.save(reAct);
	}
	
	public void update(RewardActivity reAct) {
		rewardActivityMapper.update(reAct);
	}
	
	@Transactional(readOnly = true)
	public PageResult<PostDiscussVo> findPostVoPage(PaginationQuery query) throws RestServiceException {
		PageResult<PostDiscussVo> result = null;
		try {
			Integer count = rewardActivityMapper.findSetTopPostCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<PostDiscussVo> list = rewardActivityMapper.findSetTopPost(query.getQueryData());
				result = new PageResult<PostDiscussVo>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public PageResult<PostResponse> findPostVoPageToReward(PaginationQuery query) throws RestServiceException {
		PageResult<PostResponse> result = null;
		try {
			Integer count = rewardActivityMapper.findLinkedPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<PostResponse> list = rewardActivityMapper.findLinkedPage(query.getQueryData());
				result = new PageResult<PostResponse>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public Integer findSetTopPostCount(Map<String,Object> map) throws RestServiceException {
		return rewardActivityMapper.findSetTopPostCount(map);
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> findOneByAttr(Map<String, Object> map){
		return rewardActivityMapper.findOneByAttr(map);
	}
	
	@Transactional(readOnly = true)
	public PageResult<PostResponse> findPageForBurstList(Integer loginUserId, PaginationQuery query, 
			Integer type){
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> postResponse = new ArrayList<>();
		PageResult<PostResponse> posts = findPostVoPageToReward(query);
		KFFUser loginUser = null;
		if (loginUserId != null) {
			loginUser = kffUserService.findById(loginUserId);
		}
		List<Integer> praisedPostId = null;
		// 获得点赞postidlist集合
		if (null != loginUserId) {
			praisedPostId = kffPraiseService.findPraisedPostIdByUserId(loginUserId);

		}
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for (PostResponse post : posts.getRows()) {
				if (StringUtils.isNotBlank(post.getPostShortDesc())) {
					post.setPostShortDesc(H5AgainDeltagsUtil.h5AgainDeltags(post.getPostShortDesc()));
				}
				PostResponse response = new PostResponse();
				response.setcreateTime(post.getcreateTime());
				response.setCreateUserId(post.getCreateUserId());
				response.setPostId(post.getPostId());
				response.setPostType(post.getPostType());
				response.setProjectId(post.getProjectId());
				response.setStatus(post.getStatus());
				response.setPraiseIncome(post.getPraiseIncome());
				response.setDonateIncome(post.getDonateIncome());
				response.setPostTotalIncome(post.getPostTotalIncome());
				// 设置post和project信息
				response.setPostShortDesc(post.getPostShortDesc());
				response.setPostSmallImages(post.getPostSmallImages());
				response.setPraiseIncome(post.getPraiseIncome());
				response.setDonateIncome(post.getDonateIncome());
				response.setPostTotalIncome(post.getPostTotalIncome());
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
				response.setCommentsNum(post.getCommentsNum());
				if (post != null) {
					KFFUser createUser = kffUserService.findByUserId(post.getCreateUserId());
					response.setUserType(createUser.getUserType());
				}
				KFFProject project = kffProjectService.findById(post.getProjectId());
				if (project != null) {
					response.setProjectChineseName(project.getProjectChineseName());
					response.setProjectCode(project.getProjectCode());
					response.setProjectEnglishName(project.getProjectEnglishName());
					response.setProjectIcon(project.getProjectIcon());
					response.setProjectSignature(project.getProjectSignature());
				}
				Integer postType = post.getPostType();
				if (null == postType) {
					throw new RuntimeException("post没有类型");
				}
				if (2 == postType) {
					// 查询爆料的标签
					Discuss discuss = discussService.findByPostId(post.getPostId());
					if(null!=discuss) {
						response.setTagInfos(discuss.getTagInfos());
					}
				}
				if (4 == postType) {
					// 查询悬赏的标签
					Discuss discuss = discussService.findByPostId(post.getPostId());
					if(null!=discuss) {
						response.setTagInfos(discuss.getTagInfos());
						RewardActivity ac = rewardActivityMapper.findById(discuss.getRewardActivityId());
						if(ac!=null) {
							//取悬赏总奖励
							response.setRewardMoney(ac.getRewardMoney());
							response.setRewardMoneyToOne(discuss.getRewardMoney());
							response.setPostIdToReward(ac.getPostId());
						}
					}
				}
				// 设置人的关注状态
				if (loginUser == null) {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				} else {
					if (type == 2) {
						Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_USER,
								post.getCreateUserId());
						if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
						} else {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
						}
					} else if (type == 1) {
						Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT,
								post.getProjectId());
						if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
						} else {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
						}
					}

					if (null != praisedPostId && !CollectionUtils.isEmpty(praisedPostId)) {
						if (praisedPostId.contains(post.getPostId())) {
							response.setPraiseStatus(1);
						}
					}
				}
				// 爆料字段
				response.setDiscussId(post.getDiscussId());
				response.setDisscussContents(post.getDisscussContents());
				response.setPostUuid(post.getPostUuid());
//				if (null == post.getIsNiceChoice()) {
//					response.setIsNiceChoice(sysGlobals.DISABLE);
//				} else {
//					response.setIsNiceChoice(post.getIsNiceChoice());
//				}
//				response.setNiceChoiceAt(post.getNiceChoiceAt());
				if (null == post.getType()) {
					response.setType(DiscussType.ORDINARYBURST.getValue());
				} else {
					response.setType(post.getType());
				}
//				response.setDisStickTop(post.getDisStickTop());
//				response.setDisStickUpdateTime(post.getDisStickUpdateTime());
				postResponse.add(response);
			}
		}
		result.setRows(postResponse);
		return result;
		
	}
	
	@Transactional(readOnly = true)
	public PageResult<PostResponse> findPageForNewAndHighList(Integer loginUserId, PaginationQuery query,
			Integer type){
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> postResponse = new ArrayList<>();
		PageResult<PostDiscussVo> posts = findPostVoPage(query);
		KFFUser loginUser = null;
		if (loginUserId != null) {
			loginUser = kffUserService.findById(loginUserId);
		}
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			List<Integer> praisedPostId = null;
			// 获得点赞postidlist集合
			if (null != loginUserId) {
				praisedPostId = kffPraiseService.findPraisedPostIdByUserId(loginUserId);
			}
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for (PostDiscussVo post : posts.getRows()) {
				post.setPostShortDesc(H5AgainDeltagsUtil.h5AgainDeltags(post.getPostShortDesc()));
				PostResponse response = new PostResponse();
				response.setcreateTime(post.getCreateTime());
				response.setCreateUserId(post.getCreateUserId());
				response.setPostId(post.getPostId());
				response.setPostType(post.getPostType());
				response.setProjectId(post.getProjectId());
				response.setStatus(post.getStatus());
				response.setPraiseIncome(post.getPraiseIncome());
				response.setDonateIncome(post.getDonateIncome());
				response.setPostTotalIncome(post.getPostTotalIncome());
				// 设置post和project信息
				response.setPostShortDesc(post.getPostShortDesc());
				response.setPostSmallImages(post.getPostSmallImages());
				response.setPraiseIncome(post.getPraiseIncome());
				response.setDonateIncome(post.getDonateIncome());
				response.setPostTotalIncome(post.getPostTotalIncome());
				if (null != praisedPostId && !CollectionUtils.isEmpty(praisedPostId)) {
					if (praisedPostId.contains(post.getPostId())) {
						response.setPraiseStatus(1);
					}
				}
				if (StringUtil.isNotBlank(post.getPostSmallImages())) {
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
				response.setCommentsNum(post.getCommentsNum());
				if (post != null) {
					KFFUser createUser = kffUserService.findByUserId(post.getCreateUserId());
					response.setUserType(createUser.getUserType());
				}
				KFFProject project = kffProjectService.findById(post.getProjectId());
				if (project != null) {
					response.setProjectChineseName(project.getProjectChineseName());
					response.setProjectCode(project.getProjectCode());
					response.setProjectEnglishName(project.getProjectEnglishName());
					response.setProjectIcon(project.getProjectIcon());
					response.setProjectSignature(project.getProjectSignature());
					response.setTotalScore(project.getTotalScore());
				}
				// 查询悬赏的标签
				Map<String,Object> secMap = new HashMap<>();
				secMap.put("postId", post.getPostId());
				List<RewardActivity> ac = rewardActivityMapper.findListByAttr(secMap);
				if(ac.isEmpty()) {
					response.setTagInfos(null);
					response.setRewardMoney(null);
					response.setAnswerCount(null);
					response.setEndTime(null);
					response.setType(null);
				}else {
					RewardActivity reAct = ac.get(0);
					response.setRewardMoney(reAct.getRewardMoney());
					response.setAnswerCount(reAct.getAnswerCount());
					response.setEndTime(reAct.getEndTime());
					response.setTagInfos(reAct.getTagInfos());
					response.setType(reAct.getType());
				}
				// 设置人的关注状态
				if (loginUser == null) {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				} else {
					if (type == 2) {
						Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_USER,
								post.getCreateUserId());
						if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
						} else {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
						}
					} else if (type == 1) {
						Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT,
								post.getProjectId());
						if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
						} else {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
						}
					}
				}
//				if (post.getDisStickTop() == null) {
//					response.setDisStickTop(0);
//				} else {
//					response.setDisStickTop(post.getDisStickTop());
//				}
//				response.setDisStickUpdateTime(post.getDisStickUpdateTime());
//				response.setIsNiceChoice(post.getIsNiceChoice());
//				response.setNiceChoiceAt(post.getNiceChoiceAt());
				postResponse.add(response);
			}
		}
		result.setRows(postResponse);
		return result;
	}
	
	
	@Transactional(readOnly=true)
	public PageResult<PostResponse> findPageRewardList(Integer loginUserId, PaginationQuery query, Integer type)
			throws RestServiceException {
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> postResponse = new ArrayList<>();
		PageResult<PostDiscussVo> posts = null;
		int pageIndex = query.getPageIndex();
		// 第一页先取10条后台人工置顶的悬赏，然后再从推荐池中取出10条
		List<PostDiscussVo> postDisscussList = new ArrayList<PostDiscussVo>();
		Map<String, Object> seMap = new HashMap<String, Object>();
		if (pageIndex == 1) {
			seMap.clear();
			seMap.put("status", "1");
			seMap.put("postTypec", PostType.REWARD.getValue());
			seMap.put("disStickTopc", 1);// 置顶的
			seMap.put("sort", "rac.dis_stick_updateTime");
			seMap.put("startRecord", 0);
			seMap.put("endRecord", 10);
			List<PostDiscussVo> postDiscuss = rewardActivityMapper.findSetTopPost(seMap);
			if (!postDiscuss.isEmpty()) {
				for (PostDiscussVo postDiscussVo : postDiscuss) {
					postDisscussList.add(postDiscussVo);
				}
			}
		}
		query.addQueryData("status", "1");
		query.addQueryData("postTypec", PostType.REWARD.getValue());
		query.addQueryData("disStickTopc1", 1);// 不置顶的
		query.addQueryData("stickTopc", 0);// 是否精选：0-是，1-否
		query.addQueryData("sort", "rac.nice_choice_at");
		PageResult<PostDiscussVo> findPostVoPage = findPostVoPage(query);
		if (null != findPostVoPage) {
			List<PostDiscussVo> postDiscusWithDt = findPostVoPage.getRows();
			if (!postDiscusWithDt.isEmpty()) {
				for (PostDiscussVo postDiscussVo : postDiscusWithDt) {
					postDisscussList.add(postDiscussVo);
				}
			}
		}
		Integer count = findSetTopPostCount(query.getQueryData());
		posts = new PageResult<PostDiscussVo>(postDisscussList, count, query);
		KFFUser loginUser = null;
		if (loginUserId != null) {
			loginUser = kffUserService.findById(loginUserId);
		}
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			List<Integer> praisedPostId = null;
			// 获得点赞postidlist集合
			if (null != loginUserId) {
				praisedPostId = kffPraiseService.findPraisedPostIdByUserId(loginUserId);
			}
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for (PostDiscussVo post : posts.getRows()) {
				post.setPostShortDesc(H5AgainDeltagsUtil.h5AgainDeltags(post.getPostShortDesc()));
				PostResponse response = new PostResponse();
				response.setcreateTime(post.getCreateTime());
				response.setCreateUserId(post.getCreateUserId());
				response.setPostId(post.getPostId());
				response.setPostType(post.getPostType());
				response.setProjectId(post.getProjectId());
				response.setStatus(post.getStatus());
				response.setPraiseIncome(post.getPraiseIncome());
				response.setDonateIncome(post.getDonateIncome());
				response.setPostTotalIncome(post.getPostTotalIncome());
				// 设置post和project信息
				response.setPostShortDesc(post.getPostShortDesc());
				response.setPostSmallImages(post.getPostSmallImages());
				response.setPraiseIncome(post.getPraiseIncome());
				response.setDonateIncome(post.getDonateIncome());
				response.setPostTotalIncome(post.getPostTotalIncome());
				if (null != praisedPostId && !CollectionUtils.isEmpty(praisedPostId)) {
					if (praisedPostId.contains(post.getPostId())) {
						response.setPraiseStatus(1);
					}
				}
				if (StringUtil.isNotBlank(post.getPostSmallImages())) {
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
				response.setCommentsNum(post.getCommentsNum());
				if (post != null) {
					KFFUser createUser = kffUserService.findByUserId(post.getCreateUserId());
					response.setUserType(createUser.getUserType());
				}
				KFFProject project = kffProjectService.findById(post.getProjectId());
				if (project != null) {
					response.setProjectChineseName(project.getProjectChineseName());
					response.setProjectCode(project.getProjectCode());
					response.setProjectEnglishName(project.getProjectEnglishName());
					response.setProjectIcon(project.getProjectIcon());
					response.setProjectSignature(project.getProjectSignature());
					response.setTotalScore(project.getTotalScore());
				}
				// 查询悬赏的标签
				Map<String,Object> secMap = new HashMap<>();
				secMap.put("postId", post.getPostId());
				List<RewardActivity> ac = rewardActivityMapper.findListByAttr(secMap);
				if(ac.isEmpty()) {
					response.setTagInfos(null);
					response.setRewardMoney(null);
					response.setAnswerCount(null);
					response.setEndTime(null);
					response.setType(null);
				}else {
					RewardActivity reAct = ac.get(0);
					response.setRewardMoney(reAct.getRewardMoney());
					response.setAnswerCount(reAct.getAnswerCount());
					response.setEndTime(reAct.getEndTime());
					response.setTagInfos(reAct.getTagInfos());
					response.setType(reAct.getType());
				}
				// 设置人的关注状态
				if (loginUser == null) {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				} else {
					if (type == 2) {
						Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_USER,
								post.getCreateUserId());
						if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
						} else {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
						}
					} else if (type == 1) {
						Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT,
								post.getProjectId());
						if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
						} else {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
						}
					}
				}
				if (post.getDisStickTop() == null) {
					response.setDisStickTop(0);
				} else {
					response.setDisStickTop(post.getDisStickTop());
				}
				response.setDisStickUpdateTime(post.getDisStickUpdateTime());
				response.setIsNiceChoice(post.getIsNiceChoice());
				response.setNiceChoiceAt(post.getNiceChoiceAt());
				postResponse.add(response);
			}
		}
		result.setRows(postResponse);
		return result;
	}
	
	@Transactional(readOnly = true)
	public PageResult<PostResponse> findLinkedPage(PaginationQuery query) throws RestServiceException {
		PageResult<PostResponse> result = null;
		try {
			Integer count = rewardActivityMapper.findLinkedPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<PostResponse> list = rewardActivityMapper.findLinkedPage(query.getQueryData());
				result = new PageResult<PostResponse>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public Integer findLinkedCount(Map<String, Object> map) {
		return rewardActivityMapper.findLinkedCount(map);
	}
	
	@Transactional(readOnly = true)
	public PageResult<PostResponse> findRewardAnswerList(Integer loginUserId, PaginationQuery query, Integer type){
		PageResult<PostResponse> result = new PageResult<PostResponse>();
		List<PostResponse> postResponse = new ArrayList<>();
		Integer postId = (Integer) query.getQueryData().get("rewardActivityId");
		Map<String,Object> seMap = new HashMap<>();
		seMap.put("postId", postId);
		List<RewardActivity> reActs = rewardActivityMapper.findListByAttr(seMap);
		if(reActs.isEmpty()) {
			throw new RuntimeException("暂无数据！");
		}
		query.addQueryData("rewardActivityId", reActs.get(0).getId());
		PageResult<PostResponse> posts = findLinkedPage(query);
		KFFUser loginUser = null;
		if (loginUserId != null) {
			loginUser = kffUserService.findById(loginUserId);
		}
		List<Integer> praisedPostId = null;
		// 获得点赞postidlist集合
		if (null != loginUserId) {
			praisedPostId = kffPraiseService.findPraisedPostIdByUserId(loginUserId);
		}
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setQueryParameters(posts.getQueryParameters());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			for (PostResponse post : posts.getRows()) {
				if (StringUtils.isNotBlank(post.getPostShortDesc())) {
					post.setPostShortDesc(H5AgainDeltagsUtil.h5AgainDeltags(post.getPostShortDesc()));
				}
				PostResponse response = new PostResponse();
				response.setcreateTime(post.getcreateTime());
				response.setCreateUserId(post.getCreateUserId());
				response.setPostId(post.getPostId());
				response.setPostType(post.getPostType());
				response.setProjectId(post.getProjectId());
				response.setStatus(post.getStatus());
				response.setPraiseIncome(post.getPraiseIncome());
				response.setDonateIncome(post.getDonateIncome());
				response.setPostTotalIncome(post.getPostTotalIncome());
				// 设置post和project信息
				response.setPostShortDesc(post.getPostShortDesc());
				response.setPostSmallImages(post.getPostSmallImages());
				response.setPraiseIncome(post.getPraiseIncome());
				response.setDonateIncome(post.getDonateIncome());
				response.setPostTotalIncome(post.getPostTotalIncome());
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
				response.setCommentsNum(post.getCommentsNum());
				if (post != null) {
					KFFUser createUser = kffUserService.findByUserId(post.getCreateUserId());
					response.setUserType(createUser.getUserType());
				}
				KFFProject project = kffProjectService.findById(post.getProjectId());
				if (project != null) {
					response.setProjectChineseName(project.getProjectChineseName());
					response.setProjectCode(project.getProjectCode());
					response.setProjectEnglishName(project.getProjectEnglishName());
					response.setProjectIcon(project.getProjectIcon());
					response.setProjectSignature(project.getProjectSignature());
					response.setTotalScore(project.getTotalScore());
				}
				response.setTagInfos(post.getTagInfos());
				// 设置人的关注状态
				if (loginUser == null) {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				} else {
					if (type == 2) {
						Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_USER,
								post.getCreateUserId());
						if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
						} else {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
						}
					} else if (type == 1) {
						Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT,
								post.getProjectId());
						if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
						} else {
							response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
						}
					}
					if (null != praisedPostId && !CollectionUtils.isEmpty(praisedPostId)) {
						if (praisedPostId.contains(post.getPostId())) {
							response.setPraiseStatus(1);
						}
					}
				}
				response.setDiscussId(post.getDiscussId());
				response.setDisscussContents(post.getDisscussContents());
				response.setPostUuid(post.getPostUuid());
				postResponse.add(response);
			}
		}
		result.setRows(postResponse);
		return result;
	}
	
	@Transactional(readOnly = true)
	public PageResult<RewardActivityVo> getRewardActivityList(PaginationQuery query) throws RestServiceException {
		PageResult<RewardActivityVo> result = null;
		try {
			Integer count = rewardActivityMapper.findRewardActivityListCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<RewardActivityVo> list = rewardActivityMapper.findRewardActivityListPage(query.getQueryData());
				result = new PageResult<RewardActivityVo>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional(readOnly = true)
	public PageResult<RewardActivity> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<RewardActivity> result = null;
		try {
			Integer count = rewardActivityMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<RewardActivity> list = rewardActivityMapper.findPage(query.getQueryData());
				result = new PageResult<RewardActivity>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly=true)
	public PostResponse findRewardDetail(Integer userId, Integer type, Integer postId) throws RestServiceException {
		PostResponse response = new PostResponse();
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
		Map<String,Object> seMap = new HashMap<>();
		seMap.put("postId", postId);
		List<RewardActivity> reActs = rewardActivityMapper.findListByAttr(seMap);
		if(reActs.isEmpty()) {
			throw new RestServiceException(RestErrorCode.NO_DATA_MSG);
		}
		RewardActivity reAct = reActs.get(0);
		response.setRewardContents(reAct.getRewardContents());
		response.setTagInfos(reAct.getTagInfos());
		response.setPostSmallImages(post.getPostSmallImages());
		response.setPraiseIncome(post.getPraiseIncome());
		response.setDonateIncome(post.getDonateIncome());
		response.setPostTotalIncome(post.getPostTotalIncome());
		// 设置人的关注状态
		if (loginUser == null) {
			response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
		} else {
			if (type == 2) {
				Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_USER, post.getCreateUserId());
				if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
				} else {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
				}
			} else if (type == 1) {
				Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT, post.getProjectId());
				if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
				} else {
					response.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
				}
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
		if (null != loginUser) {
			KFFUser ceateUser = kffUserService.findById(post.getCreateUserId());
			response.setUserType(ceateUser.getUserType());
		}
		response.setIsNiceChoice(reAct.getIsNiceChoice());
		response.setNiceChoiceAt(reAct.getNiceChoiceAt());
		response.setType(reAct.getType());
		response.setRewardMoney(reAct.getRewardMoney());
		response.setAnswerCount(reAct.getAnswerCount());
		response.setEndTime(reAct.getEndTime());
		response.setCommentsNum(post.getCommentsNum());
		response.setTagInfos(reAct.getTagInfos());
		return response;
	}
	
	public void updateRewardActivityAndPost(Integer postId,Integer id) {
		Post post = new Post();
		post.setPostId(postId);
		post.setStatus(0);
		kffPostService.update(post);
		RewardActivity reAct = new RewardActivity();
		reAct.setId(id);
		reAct.setState(RewardActivityState.REVOKEING.getValue());//悬赏的状态：0-进行中，1-已结束，2-已撤销
		reAct.setIsActivity(sysGlobals.DISABLE);
		reAct.setIsNiceChoice(sysGlobals.DISABLE);
		reAct.setType(DiscussType.ORDINARYBURST.getValue());
		reAct.setDisStickTop(0);//置顶状态：1置顶 0 不置顶
		rewardActivityMapper.update(reAct);
		//将悬赏的所有回答（即爆料表）全部置为不可操作状态
		Map<String,Object> seMap = new HashMap<>();
		seMap.put("rewardActivityId", id);
		seMap.put("isNiceChoice", sysGlobals.DISABLE);
		seMap.put("type", DiscussType.ORDINARYBURST.getValue());
		seMap.put("disStickTop", 0);//置顶状态：1置顶 0 不置顶
		seMap.put("rewardMoney", new BigDecimal("0"));
		discussService.updateByMap(seMap);
		
		//需要将悬赏的奖励token返回给用户
		Post postc = kffPostService.findById(postId);
		RewardActivity reActc = rewardActivityMapper.findById(id);
		//计算用户的token
		seMap.clear();
		seMap.put("userId", postc.getCreateUserId());
		List<CoinProperty> coinPr = coinPropertyService.findListByAttr(seMap);
		if(coinPr.isEmpty()) {
			throw new RuntimeException("该用户没有对应的token记录");
		}
		CoinProperty coinProty = coinPr.get(0);
	    coinPropertyService.countAddForReward(coinProty,reActc.getRewardMoney(),post.getPostId());
	}

}
