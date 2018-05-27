package com.tzg.entitys.kff.evaluation;

import com.tzg.common.base.BaseMapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationMapper extends BaseMapper<Evaluation, java.lang.Integer> {
	/**
	 * @param postId
	 * @return
	 */
	Evaluation selectEvaluationByPostId(Integer postId);

	List<Evaluation> findEvaliationByProjectId(Integer projectId);
	Evaluation findByPostId(Integer postId);

	Evaluation selectEvaluationOrNotByUserId(Evaluation evaluation);
	List<Evaluation> findByWhere(Map<String, Object> map);	

}
