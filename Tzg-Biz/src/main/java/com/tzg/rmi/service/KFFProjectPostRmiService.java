package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.projectevastat.ProjectevastatByGrade;
import com.tzg.rest.exception.rest.RestServiceException;

/**
 * kff 项目，帖子相关远程服务调用
 * 
 * @author Administrator
 *
 */
public interface KFFProjectPostRmiService {
	

	/**
	 * 项目维度 普通评测按分值统计
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public Map<String,Object> findProjectEvaStatScore(Integer projectId) throws RestServiceException;

	/**
	 * 显示针对某个评论的所有评论  分页加载
	 * @param userId
	 * @param query
	 * @return
	 */
	public PageResult<Comments> findPagecommentCommentsList(Integer userId,
			PaginationQuery query) throws RestServiceException;

	/**
	 *关注的用户 项目 动态 
	 * @param userId
	 * @param query
	 * @return
	 */
	public PageResult<PostResponse> findMyPageFollowList(Integer userId, PaginationQuery query) throws RestServiceException;
	
}
