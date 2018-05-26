package com.tzg.entitys.kff.evaluation;

import java.util.List;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationMapper extends BaseMapper<Evaluation, java.lang.Integer> {
	/**
	 * 根据postID 查询评测帖子
	 * 
	 * @param postId
	 * @return
	 */
	Evaluation selectEvaluationByPostId(Integer postId);

	List<Evaluation> findEvaliationByProjectId(Integer projectId);

	Evaluation selectEvaluationOrNotByUserId(Evaluation evaluation);

}
