package com.tzg.entitys.kff.interactionroot;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface InteractionRootMapper extends BaseMapper<InteractionRoot, java.lang.Integer> {

	List<InteractionRoot> findByMap(Map<String, Object> map);

}