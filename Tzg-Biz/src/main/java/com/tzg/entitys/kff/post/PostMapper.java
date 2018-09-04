package com.tzg.entitys.kff.post;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface PostMapper extends BaseMapper<Post, java.lang.Integer> {

	List<Post> findProjectActiveUsers(Map<String, Object> map);

	Post findByUUID(String uuid);

	void updateDonateNum(Integer postId);

	void increasePraiseNum(Integer postId);

	void decreasePraiseNum(Integer postId);

	void increaseCollectNum(Integer postId);

	void increasePageviewNum(Integer postId);

	void decreaseCollectNum(Integer postId);

	void updateUserInfo(Map<String, Object> postMap);

	Integer findMyPageFollowListCount(Map<String, Object> map);

	List<Post> findMyPageFollowList(Map<String, Object> map);

	// 查询评测数量大于0的所有项目id列表
	List<Post> getProjectIdsGreateThanTenEvas();

	List<Post> findPageRecommendList(Map<String, Object> queryData);

	Integer findPageCountRecommendList(Map<String, Object> queryData);

	List<Post> selectAllPost(Map<String, Object> map);

	List<PostDiscussVo> findSetTopDiscuss(Map<String, Object> map);

	Integer findSetTopDiscussCount(Map<String, Object> map);

	List<PostDiscussVo> findSetTopPost(Map<String, Object> map);

	Integer findSetTopPostCount(Map<String, Object> map);

	Integer updateByMap(Map<String, Object> map);

	List<PostResponse> findPageForFollowList(Map<String, Object> map);

	Integer findPageForFollowCount(Map<String, Object> map);

	Integer findPageCountRemoveSingleEva(Map<String, Object> queryData);

	List<Post> findPageRemoveSingleEva(Map<String, Object> queryData);

	/**
	 * 
	 * TODO 更新post有的点赞收益
	 * @param map
	 * @author zhangdd
	 * @data 2018年8月21日
	 *
	 */
	void updatePraiseIncome(Map<String, Object> map);

	/**
	 * 
	 * TODO 更新post的打赏收益
	 * @param map
	 * @author zhangdd
	 * @data 2018年8月21日
	 *
	 */
	void updateCommendationIncome(Map<String, Object> map);

	/**
	 * 
	 * TODO 更新帖子的收益
	 * @param map
	 * @author zhangdd
	 * @data 2018年8月21日
	 *
	 */
	void updateIncome(Map<String, Object> map);
	
	
	List<Post> findPageWithEvaluation(Map<String, Object> map);
	Integer findPageWithEvaluationCount(Map<String, Object> map);
	
	List<Post> findPageWithDiscuss(Map<String, Object> map);
	Integer findPageWithDiscussCount(Map<String, Object> map);
	
	List<Post> findPageWithArticle(Map<String, Object> map);
	Integer findPageWithArticleCount(Map<String, Object> map);
	

}
