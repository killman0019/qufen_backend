package com.tzg.rmi.service;

import java.util.List;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.projectManage.ProjectManageTabResponse;
import com.tzg.entitys.kff.transactionpair.TransactionPairResponse;
import com.tzg.rest.exception.rest.RestServiceException;

/**
 * 项目的rmi相关的接口
 * 
 * @author Administrator
 *
 */
public interface KFFProjectRmiService {
	
	public PageResult<KFFProject> findPage(PaginationQuery query);
	public PageResult<KFFProject> findPageWithFollower(PaginationQuery query,Integer typec,Integer userId);
	
	/**
	 * 向前台展示tab键
	 * 
	 * @return
	 * @throws RestServiceException
	 */
	public List<ProjectManageTabResponse> showTab() throws RestServiceException;

	/**
	 * 获取最热项目榜 (关注人数超过1000人的前20个项目；)
	 * 
	 * @param userId
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<ProjectResponse> selectHotProjectPage(Integer userId, PaginationQuery query) throws RestServiceException;

	/**
	 * 获得项目评分榜 (评分人数超过10个，按项目评分来进行排名，最多20；)
	 * 
	 * @param userId
	 * @param query
	 * @return
	 */
	public PageResult<ProjectResponse> selectEvaProjectPage(Integer userId, PaginationQuery query) throws RestServiceException;

	/**
	 * 根据 tabId,和userId 查询project分页列表
	 * 
	 * @param tabId
	 * @param userId
	 * @param query
	 * @return
	 */
	public PageResult<ProjectResponse> showProjectList(Integer tabId, Integer userId, PaginationQuery query) throws RestServiceException;

	/**
	 * 根据projectid查交易所或者交易对
	 * 
	 * @param projectId
	 * @param query 
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<TransactionPairResponse> selectExchangeAndTranPair(Integer projectId, PaginationQuery query) throws RestServiceException;

	/**
	 * 
	 * TODO  测试接口
	 * @throws RestServiceException
	 * @author zhangdd
	 * @data 2018年8月6日
	 *
	 */
	public void text() throws RestServiceException;

	/**
	 * 
	 * TODO 获得projectList 和行情数据
	 * @param tabId
	 * @param userId
	 * @param query
	 * @return
	 * @author zhangdd
	 * @data 2018年8月8日
	 *
	 */
	public PageResult<ProjectResponse> showProjectListNew(Integer tabId, Integer userId, PaginationQuery query);

}
