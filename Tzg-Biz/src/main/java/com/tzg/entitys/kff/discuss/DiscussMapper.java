package com.tzg.entitys.kff.discuss;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface DiscussMapper extends BaseMapper<Discuss, java.lang.Integer> {

	Discuss findByPostId(Integer postId);

	Discuss selectByPostId(Integer postId);

	Discuss findDisscussBypostId(Integer postId);

	List<Discuss> findByMap(Map<String, Object> disMap);
	
	Integer updateByMap(Map<String,Object> map);
	
	Integer findByCount(Map<String, Object> map);
}
