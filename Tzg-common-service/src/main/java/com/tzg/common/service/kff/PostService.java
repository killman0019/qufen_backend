package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.commendation.Commendation;
import com.tzg.entitys.kff.commendation.CommendationMapper;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostMapper;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.tokenrecords.TokenrecordsMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFPostService")
@Transactional
public class PostService {

	private static final Log logger = LogFactory.getLog(PostService.class);

	@Autowired
	private PostMapper postMapper;

	@Autowired
	private CommendationMapper commendationMapper;

	@Autowired
	private TokenrecordsMapper tokenrecordsMapper;

	private static final ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);

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

	public List<Post> selectAllPost() {
		List<Post> posts = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "1");
			String dataStr = "2018-07-11 00:00:00";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parse = format.parse(dataStr);
			map.put("createTimeBegin", parse);
			posts = postMapper.selectAllPost(map);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return posts;
	}

	public void caculatePostTatolIncome() {
		// 查找所有的post的并且进行统计
		System.err.println("定时任务开始!");
		List<Post> posts = selectAllPost();
		if (!CollectionUtils.isEmpty(posts)) {
			// 进行线程的跑线程
			final CountDownLatch countDownLatch = new CountDownLatch(posts.size());

			try {
				for (Post p : posts) {
					final Integer postId = p.getPostId();
					newFixedThreadPool.execute(new Runnable() {
						@Override
						public void run() {
							try {
								caculateEveryPostIncome(postId, countDownLatch);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});

				}
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				newFixedThreadPool.shutdown();
			}
		}
	}

	/**
	 * 进行post统计 分为 点赞token统计 捐赠token
	 * 
	 * 
	 * @param postId
	 * @param countDownLatch
	 */
	private void caculateEveryPostIncome(Integer postId, CountDownLatch countDownLatch) {
		try {
			Double praiseIncome = 0.0d;
			Double donateIncome = 0.0d;
			Double postTotalIncome = 0.0d;

			// TODO Auto-generated method stub
			if (postId == null) {
				countDownLatch.countDown();// 直接线程关闭
			}
			Post post = postMapper.findById(postId);
			if (null != post) {
				countDownLatch.countDown();// 直接线程关闭
			}
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
			postMapper.update(postDB);
			logger.warn("------update post income for post:" + postId + "praiseIncome:" + praiseIncome + "donateIncome:" + donateIncome + "postTotalIncome:"
					+ postTotalIncome);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// System.err.println("定时任务结束!");
			countDownLatch.countDown();
		}
	}
}
