package com.tzg.entitys.kff.devaluationModelDetail;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;


@Repository
public interface DevaluationModelDetailMapper extends BaseMapper<DevaluationModelDetail, java.lang.Integer> {

	List<DevaluationModelDetail> findByWhere(Map<String, String> map);	

}
