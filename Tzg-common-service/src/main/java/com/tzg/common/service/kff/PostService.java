package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzg.common.constants.KFFConstants;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.activity.RewardActivity;
import com.tzg.entitys.kff.article.Article;
import com.tzg.entitys.kff.commendation.Commendation;
import com.tzg.entitys.kff.commendation.CommendationMapper;
import com.tzg.entitys.kff.discuss.Discuss;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostDiscussVo;
import com.tzg.entitys.kff.post.PostMapper;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.tokenrecords.TokenrecordsMapper;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFPostService")
@Transactional
public class PostService {

	private static final Log logger = LogFactory.getLog(PostService.class);

	@Autowired
	private UserService kffUserService;
	@Autowired
	private PraiseService kffPraiseService;
	@Autowired
	private FollowService kffFollowService;
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private CommendationMapper commendationMapper;
	@Autowired
	private TokenrecordsMapper tokenrecordsMapper;
	@Autowired
	private ArticleService kffArticleService;
	@Autowired
	private EvaluationService kffEvaluationService;
	@Autowired
	private DiscussService kffDiscussService;
	@Autowired
	private RewardActivityService rewardActivityService;
	
	@Transactional(readOnly = true)
	public List<PostDiscussVo> findSetTopDiscuss(Map<String,Object> map) throws RestServiceException {
		return postMapper.findSetTopDiscuss(map);
	}
	
	@Transactional(readOnly = true)
	public Integer findSetTopDiscussCount(Map<String,Object> map) throws RestServiceException {
		return postMapper.findSetTopDiscussCount(map);
	}
	
	@Transactional(readOnly = true)
	public List<PostDiscussVo> findSetTopPost(Map<String,Object> map) throws RestServiceException {
		return postMapper.findSetTopPost(map);
	}
	
	@Transactional(readOnly = true)
	public Integer findSetTopPostCount(Map<String,Object> map) throws RestServiceException {
		return postMapper.findSetTopPostCount(map);
	}
	
	@Transactional(readOnly = true)
	public Post findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return postMapper.findById(id);
	}

	public Post findByUUID(String uuid) {
		if (uuid == null) {
			throw new RestServiceException("uuid不能为空");
		}
		return postMapper.findByUUID(uuid);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		postMapper.deleteById(id);
	}

	public Integer save(Post post) throws RestServiceException {
		return postMapper.save(post);
	}
	
	public Integer updateByMap(Map<String,Object> map) throws RestServiceException {
		return postMapper.updateByMap(map);
	}

	public void update(Post post) throws RestServiceException {
		if (post.getPostId() == null) {
			throw new RestServiceException("id不能为空");
		}
		postMapper.update(post);
	}
	
	@Transactional(readOnly = true)
	public PageResult<Post> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPage(query.getQueryData());
				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	private static Integer getListArr(List<Post> list) {
		if (list.isEmpty()) {
			return null;
		}
		Random ran = new Random();
		int con = ran.nextInt(list.size());
		return con;
	}
	public PageResult<Post> findPageNewestList(PaginationQuery query,Integer typec,
			Integer userId,Integer pageSize){
		List<Post> postDisscussList = new ArrayList<Post>();
		List<Post> postResponsec = new ArrayList<>();
		PageResult<Post> result = new PageResult<Post>();
		query.addQueryData("status", 1);
		query.addQueryData("postTypec", 4);
		query.addQueryData("sql_keyword_orderBy", "createTime");
		query.addQueryData("sql_keyword_sort", "desc");
		query.setRowsPerPage(pageSize*2);
		PageResult<Post> resultc = findPage(query);
		KFFUser loginUser = null;
		List<Integer> praisedPostId = null;
		// 获得点赞postidlist集合
		if (null != userId) {
			loginUser = kffUserService.findById(userId);
			praisedPostId = kffPraiseService.findPraisedPostIdByUserId(loginUser.getUserId());
		}
		if(resultc!=null&&!resultc.getRows().isEmpty()) {
			resultc.setRowsPerPage(pageSize);
			List<Post> postList = resultc.getRows();
			// 从中随机取出rowsPerPage条，等于小于rowsPerPage条就全部取出
			if (postList.size() <= pageSize) {
				for (Post postResponse : postList) {
					postDisscussList.add(postResponse);
				
				}
			} else {
				List<Integer> usList = new ArrayList<Integer>();
				for (int i = 0; i < pageSize; i++) {
					boolean flag = false;
					while (!flag) {
						Integer usArr = getListArr(postList);
						if (!usList.contains(usArr)) {
							usList.add(usArr);
							flag = true;
						}
					}
				}
				Collections.sort(usList);
				for (int i = 0; i < usList.size(); i++) {
					Post postDiscussVo = postList.get(usList.get(i));
					postDisscussList.add(postDiscussVo);
				}
			}
			postList = postDisscussList;
			result.setCurPageNum(resultc.getCurPageNum());
			result.setPageSize(resultc.getPageSize());
			result.setQueryParameters(resultc.getQueryParameters());
			result.setRowsPerPage(resultc.getRowsPerPage());
			result.setRowCount(resultc.getRowCount());
			for (Post postResponse : postList) {
				// 设置人的关注状态
				if (loginUser == null) {
					postResponse.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
				} else {
					if (null != praisedPostId && !CollectionUtils.isEmpty(praisedPostId)) {
						if (praisedPostId.contains(postResponse.getPostId())) {
							postResponse.setPraiseStatus(1);
						}
					}
					if (typec == 2) {
						Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_USER,
								postResponse.getCreateUserId());
						if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
							postResponse.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
						} else {
							postResponse.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
						}
					} else if (typec == 1) {
						Follow follow = kffFollowService.findByUserIdAndFollowType(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_PROJECT,
								postResponse.getProjectId());
						if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
							postResponse.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
						} else {
							postResponse.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
						}
					}
				}
				
				if (null != postResponse.getPostTotalIncome()) {
					postResponse.setPostTotalIncome(postResponse.getPostTotalIncome());
				} else {
					postResponse.setPostTotalIncome(0.0);
				}
				Integer postType = postResponse.getPostType();
				if (1 == postType) {
					// 查询评测和文章的标签
					Evaluation evation = kffEvaluationService.findByPostId(postResponse.getPostId());
					if (null != evation) {
						if(evation.getModelType()==1) {
							continue;
						}
						postResponse.setTagInfos(evation.getEvaluationTags());
						postResponse.setTotalScore(evation.getTotalScore());
					} else {
						postResponse.setTagInfos(null);
						postResponse.setTotalScore(new BigDecimal("0"));
					}
				}
				if (2 == postType) {
					// 查询爆料的标签
					Discuss discuss = kffDiscussService.findByPostId(postResponse.getPostId());
					postResponse.setTagInfos(discuss.getTagInfos());
					if(null!=discuss.getRewardActivityId()) {
						RewardActivity ac = rewardActivityService.findById(discuss.getRewardActivityId());
						if (ac != null) {
							// 取悬赏总奖励
							postResponse.setPostType(4);
							postResponse.setRewardMoney(ac.getRewardMoney());
							postResponse.setRewardMoneyToOne(discuss.getRewardMoney());
							postResponse.setPostIdToReward(ac.getPostId());
						}
					}
					if(13446==postResponse.getPostId()) {
						System.out.println(postResponse.getPostType());
					}
				}
				if (3 == postType) {
					// 查询文章的标签
					Article ac = kffArticleService.findByPostId(postResponse.getPostId());
					postResponse.setTagInfos(ac.getTagInfos());
				}
				//查询发布人的类型
				KFFUser user = kffUserService.findById(postResponse.getCreateUserId());
				if(user!=null) {
					postResponse.setUserType(user.getUserType());
				}else {
					postResponse.setUserType(1);
				}
				postResponsec.add(postResponse);
			}
		}
		result.setRows(postResponsec);
		return result;
	}
	
	
	@Transactional(readOnly = true)
	public PageResult<Post> findPageWithFollower(PaginationQuery query,Integer typec,Integer userId) {
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPage(query.getQueryData());
				if(!list.isEmpty()) {
					KFFUser loginUser = null;
					if (userId != null) {
						loginUser = kffUserService.findById(userId);
					}
					for (Post post : list) {
						// 设置人的关注状态
						if (loginUser == null) {
							post.setFollowStatus(KFFConstants.COLLECT_STATUS_NOT_SHOW);
						} else {
							if (typec == 2) {
								Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(loginUser.getUserId(), KFFConstants.FOLLOW_TYPE_USER,
										post.getCreateUserId());
								if (follow != null && follow.getStatus() != null && follow.getStatus() == KFFConstants.STATUS_ACTIVE) {
									post.setFollowStatus(KFFConstants.COLLECT_STATUS_COLLECTED);
								} else {
									post.setFollowStatus(KFFConstants.COLLECT_STATUS_NOCOLLECT);
								}
							} 
						}
						Discuss dis = kffDiscussService.findByPostId(post.getPostId());
						if(null!=dis) {
							if(dis.getRewardActivityId()!=null) {
								post.setPostType(4);
							}
						}
					}
				}
				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	@Transactional(readOnly = true)
	public PageResult<Post> findPageWithEvaluation(PaginationQuery query) throws RestServiceException {
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findPageWithEvaluationCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPageWithEvaluation(query.getQueryData());
				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public PageResult<Post> findPageWithDiscuss(PaginationQuery query) throws RestServiceException {
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findPageWithDiscussCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPageWithDiscuss(query.getQueryData());
				if(!list.isEmpty()) {
					for (Post post : list) {
						if(post.getRewardActivityId()!=null) {
							post.setPostType(4);
							RewardActivity reAct = rewardActivityService.findById(post.getRewardActivityId());
							post.setRewardMoney(reAct.getRewardMoney());
							post.setPostIdToReward(reAct.getPostId());
						}
					}
				}
				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public PageResult<Post> findPageWithArticle(PaginationQuery query) throws RestServiceException {
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findPageWithArticleCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPageWithArticle(query.getQueryData());
				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public PageResult<PostDiscussVo> findPostVoPage(PaginationQuery query) throws RestServiceException {
		PageResult<PostDiscussVo> result = null;
		try {
			Integer count = postMapper.findSetTopPostCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<PostDiscussVo> list = postMapper.findSetTopPost(query.getQueryData());
				result = new PageResult<PostDiscussVo>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public PageResult<PostDiscussVo> findPostDiscussVoPage(PaginationQuery query) throws RestServiceException {
		PageResult<PostDiscussVo> result = null;
		try {
			Integer count = postMapper.findSetTopDiscussCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<PostDiscussVo> list = postMapper.findSetTopDiscuss(query.getQueryData());
				result = new PageResult<PostDiscussVo>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	public List<Post> findProjectActiveUsers(Map<String, Object> map) {

		return postMapper.findProjectActiveUsers(map);
	}

	public void updateDonateNum(Integer postId) {
		postMapper.updateDonateNum(postId);

	}

	public void increasePraiseNum(Integer postId) {
		postMapper.increasePraiseNum(postId);

	}

	public void decreasePraiseNum(Integer postId) {
		postMapper.decreasePraiseNum(postId);
	}

	public void increaseCollectNum(Integer postId) {

		postMapper.increaseCollectNum(postId);
	}

	public void increasePageviewNum(Integer postId) {

		postMapper.increasePageviewNum(postId);
	}

	public void decreaseCollectNum(Integer postId) {
		postMapper.decreaseCollectNum(postId);

	}

	public void updateUserInfo(Map<String, Object> postMap) {

		postMapper.updateUserInfo(postMap);
	}

	@Transactional(readOnly = true)
	public PageResult<Post> findMyPageFollowList(PaginationQuery query) {
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findMyPageFollowListCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findMyPageFollowList(query.getQueryData());
				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public PageResult<PostResponse> findPageForFollowList(PaginationQuery query) {
		PageResult<PostResponse> result = null;
		try {
			Integer count = postMapper.findPageForFollowCount(query.getQueryData());
			System.out.println("count------------------------------->"+count);
//			if (null != count && count.intValue() > 0) {
//				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
//				query.addQueryData("startRecord", Integer.toString(startRecord));
//				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
//				List<PostResponse> list = postMapper.findPageForFollowList(query.getQueryData());
//				result = new PageResult<PostResponse>(list, count, query);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public PageResult<Post> findPageRecommendList(PaginationQuery query) {
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findPageCountRecommendList(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPageRecommendList(query.getQueryData());
				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional(readOnly = true)
	public PageResult<Post> findPageRemoveSingleEva(PaginationQuery query) {
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findPageCountRemoveSingleEva(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPageRemoveSingleEva(query.getQueryData());
				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据用户的id查询当前用户下的所有帖子
	 * 
	 * @param userId
	 * @return
	 */
	public List<Post> selectAllPostByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Post> selectAllPost(int i) {
		List<Post> posts = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "1");
			String dataStr = "2018-06-21 00:00:00";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parse = format.parse(dataStr);
			map.put("createTimeBegin", parse);

			PaginationQuery query = new PaginationQuery();
			query.setRowsPerPage(10);
			query.setPageIndex(i);
			query.setQueryData(map);
			posts = selectAllPostQuery(query);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posts;
	}

	private List<Post> selectAllPostQuery(PaginationQuery query) {
		// TODO 进行分页查询整个数据库
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPage(query.getQueryData());
				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.getRows();
	}

	public void caculatePostTatolIncome() {
		// 查找所有的post的并且进行统计
		System.err.println("定时任务开始!");
		// List<Post> posts =null;
		ExecutorService newFixedThreadPoolCaluaPostIncome = null;
		try {
			newFixedThreadPoolCaluaPostIncome = Executors.newFixedThreadPool(10);
			int i = 0;
			while (true) {
				i = i + 1;

				List<Post> posts = selectAllPost(i);
				if (!CollectionUtils.isEmpty(posts)) {
					// 进行线程的跑线程

					try {
						for (Post p : posts) {

							final Integer postId = p.getPostId();
							final Post pf = p;
							newFixedThreadPoolCaluaPostIncome.execute(new Runnable() {
								@Override
								public void run() {
									try {
										caculateEveryPostIncome(postId, pf);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						//newFixedThreadPoolCaluaPostIncome.shutdown();

					}

				} else if (CollectionUtils.isEmpty(posts)) {
					if (!newFixedThreadPoolCaluaPostIncome.isShutdown()) {
						newFixedThreadPoolCaluaPostIncome.shutdown();
						return;
					}
					System.err.println("结束");
					return;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (!newFixedThreadPoolCaluaPostIncome.isShutdown()) {
				newFixedThreadPoolCaluaPostIncome.shutdown();
			}
			System.err.println("结束");
		}
		if (!newFixedThreadPoolCaluaPostIncome.isShutdown()) {
			// newFixedThreadPoolCaluaPostIncome.shutdown();

			System.err.println("已经关闭");
		}

	}

	/**
	 * 
	 * TODO 计算每篇文章的收益
	 * @param postId
	 * @author zhangdd
	 * @data 2018年8月8日
	 *
	 */
	public void caculateEveryPostIncome(Integer postId, Post post) {
		int countDays = DateUtil.countDays(new Date(), post.getCreateTime());
		if (countDays < 30) {
			// if (DateUtil.countDays(post.getCreateTime(), new Date()) > 30) {
			try {

				Double praiseIncome = 0.0d;
				Double donateIncome = 0.0d;
				Double postTotalIncome = 0.0d;

				// TODO Auto-generated method stub

				// 查询打赏奖励表,查询对这个帖子进行的打赏
				List<Commendation> commendations = commendationMapper.selectAllCommendationByPostId(postId);
				if (!CollectionUtils.isEmpty(commendations)) {
					// 打赏表不能为空
					for (Commendation commendation : commendations) {
						if (commendation != null) {
							BigDecimal amount = commendation.getAmount();
							double doubleAmount = amount.doubleValue();
							donateIncome = donateIncome + doubleAmount;
						}
					}
				}
				// 进行点赞统计
				Map<String, Object> tcmap = new HashMap<String, Object>();
				tcmap.put("postId", postId + "");
				tcmap.put("status", "1");
				List<Tokenrecords> tokenrecords = tokenrecordsMapper.findAllPage(tcmap);
				if (!CollectionUtils.isEmpty(tokenrecords)) {
					for (Tokenrecords tokenrecord : tokenrecords) {
						if (null != tokenrecord) {
							BigDecimal amount = tokenrecord.getAmount();
							double doubleAmout = amount.doubleValue();
							praiseIncome = praiseIncome + doubleAmout;

						}
					}
				}
				// 进行post总收益的计算

				postTotalIncome = praiseIncome + donateIncome;
				// 将三个数据更新到数据库中

				Post postDB = new Post();
				postDB.setPostId(postId);
				postDB.setPraiseIncome(praiseIncome);
				postDB.setDonateIncome(donateIncome);
				postDB.setPostTotalIncome(postTotalIncome);
				postDB.setUpdateTime(new Date());
				postMapper.update(postDB);
				logger.warn("------update post income for post:" + postId + "praiseIncome:" + praiseIncome + "donateIncome:" + donateIncome
						+ "postTotalIncome:" + postTotalIncome);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * TODO 获得置顶页面
	 * @param query
	 * @param postId
	 * @return
	 * @author zhangdd
	 * @data 2018年8月9日
	 *
	 */
	@Transactional(readOnly = true)
	public PageResult<Post> findPageIncludeSkick(PaginationQuery query, Integer postId) {
		// TODO 获得置顶的页面
		PageResult<Post> result = null;
		try {
			Post skickPost = postMapper.findById(postId);
			if (null == skickPost) {
				throw new RestServiceException("置顶内容为空!");
			}
			query.addQueryData("delPost", postId);
			Integer count = postMapper.findPageCountRecommendList(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPageRecommendList(query.getQueryData());

				list.add(0, skickPost);

				result = new PageResult<Post>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * 
	 * TODO 统计点赞的帖子收益
	 * @param postId
	 * @param amount
	 * @author zhangdd
	 * @data 2018年8月21日
	 *
	 */
	public void updatePraiseIncome(Integer postId, Double amount) {
		// TODO 统计点赞的帖子收益
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postId", postId);
		map.put("amount", amount);
		map.put("praiseIncome", amount);

		postMapper.updateIncome(map);
	}

	/**
	 * 
	 * TODO 统计打赏的帖子收益
	 * @param postId
	 * @param amount
	 * @author zhangdd
	 * @data 2018年8月21日
	 *
	 */
	public void updateCommendationIncome(Integer postId, Double amount) {
		// TODO 统计打赏的帖子收益
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("donateIncome", amount);
		map.put("postId", postId);
		map.put("amount", amount);
		postMapper.updateIncome(map);
	}
}
