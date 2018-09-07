package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.activity.RewardActivity;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.post.PostResponse;

/** 
* @ClassName: RewardActivityRmiService 
* @Description: TODO<悬赏远程服务调用> 
* @author linj<作者>
* @date 2018年7月5日 下午1:32:17 
* @version v1.0.0 
*/
public interface RewardActivityRmiService {
	
	public PageResult<RewardActivity> findPage(PaginationQuery query);

	public RewardActivity findById(Integer id);
	
	public List<RewardActivity> findListByAttr(Map<String, Object> map);
	
	public void saveRewardActivity(ArticleRequest articleRequest,Integer rewardDate,String rewardMoney);
	
	public PageResult<PostResponse> findPageRewardList(Integer loginUserId, PaginationQuery query, Integer type);
	
	public Map<String, Object> findOneByAttr(Map<String, Object> map);
	
	public void updateRewardActivityAndPost(Integer postId,Integer id);
	
	public PostResponse findRewardDetail(Integer userId, Integer type, Integer postId);
	
	public PageResult<PostResponse> findRewardAnswerList(Integer loginUserId, PaginationQuery query, Integer type);
	
}
