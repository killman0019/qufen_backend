package com.tzg.rmi.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.constants.KFFConstants;
import com.tzg.common.enums.DiscussType;
import com.tzg.common.enums.RewardActivityState;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.kff.CoinPropertyService;
import com.tzg.common.service.kff.PostService;
import com.tzg.common.service.kff.ProjectService;
import com.tzg.common.service.kff.RewardActivityService;
import com.tzg.common.service.kff.UserService;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.H5AgainDeltagsUtil;
import com.tzg.common.utils.WorkHtmlRegexpUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.activity.RewardActivity;
import com.tzg.entitys.kff.activity.RewardActivityVo;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.RewardActivityRmiService;
import com.vdurmont.emoji.EmojiParser;

public class RewardActivityRmiServiceImpl implements RewardActivityRmiService {

	private static final Log logger = LogFactory.getLog(RewardActivityRmiServiceImpl.class);

	@Autowired
	private RewardActivityService rewardActivityService;
	@Autowired
	private UserService kffUserService;
	@Autowired
	private ProjectService kffProjectService;
	@Autowired
	private KFFRmiServiceImpl kffRmiServiceImpl;
	@Autowired
	private PostService kffPostService;
	@Autowired
	private CoinPropertyService coinPropertyService;
	
	public PageResult<RewardActivity> findPage(PaginationQuery query) throws RestServiceException {
		return rewardActivityService.findPage(query);
	}

	public RewardActivity findById(Integer id) {
		return rewardActivityService.findById(id);
	}

	public List<RewardActivity> findListByAttr(Map<String, Object> map) {
		return rewardActivityService.findListByAttr(map);
	}
	public PageResult<PostResponse> findPageRewardList(Integer loginUserId, PaginationQuery query,
			Integer type){
		return rewardActivityService.findPageRewardList(loginUserId, query,type);
	}
	
	public PageResult<PostResponse> findPageForNewAndHighList(Integer loginUserId, PaginationQuery query,
			Integer type){
		return rewardActivityService.findPageForNewAndHighList(loginUserId, query,type);
	}
	
	public PageResult<PostResponse> findPageForBurstList(Integer loginUserId, PaginationQuery query, 
			Integer type){
		return rewardActivityService.findPageForBurstList(loginUserId, query,type);
	}
	
	public PageResult<PostResponse> findRewardAnswerList(Integer loginUserId, PaginationQuery query, 
			Integer type){
		return rewardActivityService.findRewardAnswerList(loginUserId, query,type);
	}
	
	public PageResult<RewardActivityVo> getRewardActivityList( PaginationQuery query){
		return rewardActivityService.getRewardActivityList(query);
	}
	
	public Integer findLinkedCount(Map<String, Object> map) {
		return rewardActivityService.findLinkedCount(map);
	}
	
	public Map<String, Object> findOneByAttr(Map<String, Object> map){
		return rewardActivityService.findOneByAttr(map);
	}
	
	public void updateRewardActivityAndPost(Integer postId,Integer id) {
		rewardActivityService.updateRewardActivityAndPost(postId,id);
	}
	
	public PostResponse findRewardDetail(Integer userId, Integer type, Integer postId) {
		return rewardActivityService.findRewardDetail(userId,type,postId);
	}
	
	public Map<String, Object> saveRewardActivity(ArticleRequest articleRequest,Integer rewardDate,BigDecimal rewardMoney,
			CoinProperty coinProty) throws RestServiceException {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		if (articleRequest.getArticleContents().length() > 16777215) {
			throw new RestServiceException("悬赏内容长度超过限制");
		}
		if (articleRequest.getPostTitle().length() > 120) {
			throw new RestServiceException("悬赏标题长度不能超过30字");
		}
		KFFUser createUser = kffUserService.findById(articleRequest.getCreateUserId());
		if (createUser == null) {
			throw new RestServiceException("用户不存在" + articleRequest.getCreateUserId());
		}
		KFFProject project = kffProjectService.findById(articleRequest.getProjectId());
		if (project == null) {
			throw new RestServiceException("项目不存在" + articleRequest.getProjectId());
		}
		// 禁止发纯图片的文章
		articleRequest.setArticleContents(EmojiParser.removeAllEmojis(articleRequest.getArticleContents()));
		String delHTMLTag = WorkHtmlRegexpUtil.delHTMLTag(articleRequest.getArticleContents());
		delHTMLTag = H5AgainDeltagsUtil.h5AgainDeltags(delHTMLTag);
		if (null == delHTMLTag || delHTMLTag.length() == 0) {
			throw new RestServiceException("请对所发表的内容进行文字描述");
		}
		if (StringUtils.isBlank(articleRequest.getArticleContents())) {
			throw new RestServiceException("文章内容不合法");
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
		String text = delHTMLTag;
		String toHtmlTags = H5AgainDeltagsUtil.h5AgainDeltags(text);
		if (toHtmlTags.length() < 300) {
			post.setPostShortDesc(toHtmlTags);
		} else {
			//
			post.setPostShortDesc(toHtmlTags.substring(0, 300));
		}

		/************ begin *******************/
		Map<String, String> qiNiuMap = kffRmiServiceImpl.grabUrlAndReplaceQiniu(articleRequest.getArticleContents(), createUser.getUserId());
		String uploadIevisList = qiNiuMap.get("uploadIeviwList");
		String contentSrcReplace = qiNiuMap.get("contentSrcReplace");
		// logger.info("缩略图的json串" + uploadIevisList);
		/************ end *******************/
		post.setPostSmallImages(articleRequest.getPostSmallImages());
		post.setPostTitle(articleRequest.getPostTitle());
		post.setPostType(KFFConstants.POST_TYPE_REWARD);// 帖子类型：1-评测；2-讨论；3-文章,4-悬赏
		post.setPraiseNum(0);
		post.setProjectCode(project.getProjectCode());
		post.setProjectEnglishName(project.getProjectEnglishName());
		post.setProjectChineseName(project.getProjectChineseName());
		post.setProjectIcon(project.getProjectIcon());
		post.setProjectId(project.getProjectId());
		post.setStatus(KFFConstants.STATUS_ACTIVE);
		post.setUuid(uuid);
		kffPostService.save(post);
		//计算用户的token
		coinPropertyService.countReduceForReward(coinProty,rewardMoney,post.getPostId());
		//保存悬赏子表
		RewardActivity reAct = new RewardActivity();
		reAct.setCreatedAt(now);
		reAct.setUpdatedAt(now);
		reAct.setPostId(post.getPostId());
		reAct.setRewardDate(rewardDate);
		reAct.setRewardMoney(rewardMoney);
		reAct.setBeginTime(now);
		try {
			reAct.setEndTime(DateUtil.countEndTime(rewardDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reAct.setState(RewardActivityState.STARTING.getValue());
		reAct.setIsActivity(sysGlobals.DISABLE);
		reAct.setIsNiceChoice(sysGlobals.DISABLE);
		if (createUser.getUserType() != null && createUser.getUserType() == 1) {
			reAct.setIsNiceChoice(1);// '是否精选：0-是，1-否
			reAct.setType(DiscussType.ORDINARYBURST.getValue());
		}
		if (createUser.getUserType() != null && createUser.getUserType() != 1) {
			reAct.setIsNiceChoice(0);// 是否精选：0-是，1-否
			reAct.setNiceChoiceAt(now);
			reAct.setType(DiscussType.AUTHACCOUNTPUBLISH.getValue());
		}
		String contents = null;
		if (null == contentSrcReplace) {
			contents = WorkHtmlRegexpUtil.deleContentsHtmlTage(articleRequest.getArticleContents());
			reAct.setRewardContents(contents);
		} else {
			contents = WorkHtmlRegexpUtil.deleContentsHtmlTage(contentSrcReplace);
			reAct.setRewardContents(contents);
		}
		if (StringUtils.isNotBlank(articleRequest.getTagInfos())) {
			reAct.setTagInfos(articleRequest.getTagInfos());
		}
		rewardActivityService.saveRewardActivity(reAct);
		Map<String, Object> result = new HashMap<>();
		result.put("postId", post.getPostId());
		result.put("postType", post.getPostType());
		return result;
	}
}
