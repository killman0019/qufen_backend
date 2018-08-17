package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.user.KFFUser;
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
	 * 
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public Map<String, Object> findProjectEvaStatScore(Integer projectId) throws RestServiceException;

	/**
	 * 显示针对某个评论的所有评论 分页加载
	 * 
	 * @param userId
	 * @param query
	 * @return
	 */
	public PageResult<Comments> findPagecommentCommentsList(Integer userId, PaginationQuery query) throws RestServiceException;

	/**
	 * 关注的用户 项目 动态
	 * 
	 * @param userId
	 * @param query
	 * @return
	 */
	public PageResult<PostResponse> findMyPageFollowList(Integer userId, PaginationQuery query) throws RestServiceException;
	
	public PageResult<PostResponse> findPageForFollowList(Integer userId, PaginationQuery query,Integer type,KFFUser loginUser);

	/**
	 * 项目维度 专业评测 分项统计
	 * 
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public Map<String, Object> findProjectEvaStat(Integer projectId) throws RestServiceException;

	/**
	 * 热门评测
	 * 
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public List<PostResponse> findHotEvaList(Integer projectId) throws RestServiceException;

	/**
	 * 分页获取简单评测列表
	 * 
	 * @param query
	 * @param loginUserId 
	 * @return
	 */
	public PageResult<EvaluationDetailResponse> findPageSimpleEvaluationList(PaginationQuery query, Integer loginUserId);

	/**
	 * 计算项目的总分
	 * 
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public Map<String, Object> selectProjectAllTotalScore(Integer projectId) throws RestServiceException;

	/**
	 * 根据项目id查询project
	 * 
	 * @param projectId
	 * @return
	 */
	public KFFProject selectProjectByProjectId(Integer projectId);

	/**
	 * 项目维度 单项评测 分项统计
	 * 
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public Map<String, Object> selectProjectEvaStatSelf(Integer projectId) throws RestServiceException;
	
	public List<KFFProject> findListByMap(Map<String, Object> map);
}
