package com.tzg.entitys.kff.praise;

import com.tzg.common.base.BaseMapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface PraiseMapper extends BaseMapper<Praise, java.lang.Integer> {

	Praise findByPostId(Map<String, Object> map);	
	
	Praise findByPraiseId(Map<String, Object> map);

	List<Praise> findAllActivePraisesByPostId(Map<String, Object> map);

	Praise findevaByPostId(Map<String, Object> map);

	List<Praise> findByMap(Map<String, Object> praiseMap);	
	

}
