package com.tzg.common.service.kff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONArray;
import com.tzg.common.constants.KFFConstants;
import com.tzg.common.enums.PostType;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.H5AgainDeltagsUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.entitys.kff.activity.RewardActivity;
import com.tzg.entitys.kff.activity.RewardActivityMapper;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.post.PostDiscussVo;
import com.tzg.entitys.kff.post.PostFile;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.user.KFFUser;
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
	
	@Transactional(readOnly = true)
	public RewardActivity findById(java.lang.Integer id) {
		return rewardActivityMapper.findById(id);
	}
	
	@Transactional(readOnly = true)
	public List<RewardActivity> findListByAttr(Map<String, Object> map) {
		return rewardActivityMapper.findListByAttr(map);
	}

	public void saveRewardActivity(RewardActivity reAct) throws RestServiceException {
		rewardActivityMapper.save(reAct);
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
	public Integer findSetTopPostCount(Map<String,Object> map) throws RestServiceException {
		return rewardActivityMapper.findSetTopPostCount(map);
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

}
