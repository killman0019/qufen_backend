package com.tzg.entitys.kff.devaluationModel;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface DevaluationModelMapper extends BaseMapper<DevaluationModel, java.lang.Integer> {

	List<DevaluationModel> selectEvaluationModelByModelType(Integer modelType);

	List<DevaluationModel> findAll();

}
