package com.tzg.entitys.kff.follow;

import com.tzg.common.base.BaseMapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface FollowMapper extends BaseMapper<Follow, java.lang.Integer> {

	Follow findByWhere(Map<String, Object> map);	

}
