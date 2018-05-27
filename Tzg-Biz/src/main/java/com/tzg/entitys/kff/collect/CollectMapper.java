package com.tzg.entitys.kff.collect;

import com.tzg.common.base.BaseMapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CollectMapper extends BaseMapper<Collect, java.lang.Integer> {

	Collect findByWhere(Map<String, Object> map);	

}
