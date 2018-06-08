package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.entitys.kff.projectevastat.ProjectevastatByGrade;
import com.tzg.rest.exception.rest.RestServiceException;

/**
 * kff 项目，帖子相关远程服务调用
 * 
 * @author Administrator
 *
 */
public interface KFFProjectPostRmiService {
	

	public List<ProjectevastatByGrade> findProjectEvaStatScore(Integer projectId) throws RestServiceException;
	
}
