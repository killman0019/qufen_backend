package com.tzg.rmi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.databinding.types.soapencoding.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.service.kff.ArticleService;
import com.tzg.common.service.kff.AuthenticationService;
import com.tzg.common.service.kff.AwardPortService;
import com.tzg.common.service.kff.BGroupMustReadService;
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
import com.tzg.common.utils.DozerMapperUtils;
import com.tzg.common.zookeeper.ZKClient;
import com.tzg.entitys.kff.bgroupmustread.BGroupMustRead;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.userwallet.KFFUserWalletMapper;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFPostRmiService;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.SmsSendRmiService;

/**
 * 每篇帖子收益的实现类
 * 
 * @author Administrator
 *
 */
public class KFFPostRmiServiceImpl implements KFFPostRmiService {

	@Autowired
	private KFFRmiService kffRmiService;
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

	@Autowired
	private BGroupMustReadService bgroupMustReadService;

	/**
	 * 暂时未使用
	 */
	@Override
	public Map<String, Object> countEveryPostIncome(Integer userId) throws RestServiceException {
		// 判断用户是否为空
		Map<String, Object> map = new HashMap<String, Object>();
		Double praiseIncome = null;
		Double donateIncome = null;
		Double postTotalIncome = null;

		if (userId == null) {
			throw new RestServiceException("用户没有登录,请重新登录!");
			// 查询 出当前帖子下的所有帖子 (是否在规定时间后进行查询)
		}
		List<Post> posts = kffPostService.selectAllPostByUserId(userId);
		if (!CollectionUtils.isEmpty(posts)) {// 判断当前用户名下的帖子是否存在
			for (Post post : posts) {
				if (null != post) {

				}
			}
		}
		map.put("praiseIncome", praiseIncome);
		map.put("donateIncome", donateIncome);
		map.put("postTotalIncome", postTotalIncome);
		return map;
	}

	/**
	 * 展示B圈必读相关信息
	 */
	@Override
	public PageResult<PostResponse> showBGmustRead(PaginationQuery query) throws RestServiceException {
		// TODO 展示B圈必读的信息 分页展示
		PageResult<PostResponse> postResponsePage = null;
		List<PostResponse> postResponseList = new ArrayList<PostResponse>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sql_keyword_orderBy", "create_time");
		map.put("sql_keyword_sort", "DESC");
		query.setQueryData(map);
		PageResult<BGroupMustRead> bgroupMustReadPage = bgroupMustReadService.findPage(query);
		if (null != bgroupMustReadPage && !CollectionUtils.isEmpty(bgroupMustReadPage.getRows())) {
			List<BGroupMustRead> bgroupMustreadList = bgroupMustReadPage.getRows();
			for (BGroupMustRead bGroupMustRead : bgroupMustreadList) {
				// 根据查询的对象查询相关帖子
				Integer postId = bGroupMustRead.getPostId();
				Post post = kffPostService.findById(postId);
				if (null != post) {
					PostResponse postResponse = new PostResponse();
					DozerMapperUtils.map(post, postResponse);
					postResponseList.add(postResponse);
				}

			}
			postResponsePage = new PageResult<PostResponse>(postResponseList, bgroupMustReadPage.getRowCount(), query);
		}
		return postResponsePage;
	}
}
