package com.tzg.rmi.service;

import java.util.Map;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.rest.exception.rest.RestServiceException;

/**
 * post 相关的操作
 * 
 * @author Administrator
 *
 */
public interface KFFPostRmiService {
	/**
	 * 根据用户的id统计用户下每篇帖子的收益情况
	 * 
	 * @param userId
	 * @return
	 * @throws RestServiceException
	 */
	public Map<String, Object> countEveryPostIncome(Integer userId) throws RestServiceException;

	/**
	 * 展示B圈必读
	 * 
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<PostResponse> showBGmustRead(PaginationQuery query) throws RestServiceException;
	
	public PageResult<Post> findPage(PaginationQuery query) throws RestServiceException;
	
	public PageResult<Post> findPageWithEvaluation(PaginationQuery query);
	public PageResult<Post> findPageWithDiscuss(PaginationQuery query);
	public PageResult<Post> findPageWithArticle(PaginationQuery query);

}
