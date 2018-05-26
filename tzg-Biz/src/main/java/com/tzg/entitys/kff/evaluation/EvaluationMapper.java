package com.tzg.entitys.kff.evaluation;

import com.tzg.common.base.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationMapper extends BaseMapper<Evaluation, java.lang.Integer> {

	Evaluation findByPostId(Integer postId);	

}
