package com.tzg.entitys.kff.project;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;


public interface UserProjectMapper extends BaseMapper<UserProject, java.lang.Integer>{
	UserProject findOneByAttr(Map<String,Object> map);
	
	void insertBatchs(List<UserProject> list);
	List<UserProject> findListByAttr(Map<String,Object> map);
	
	List<String> findListByString(Map<String,Object> map);
	
	List<String> findAll();
	
	List<UserProject> findProjectByCode(Map<String, Object> map);

	List<UserProject> findByMap(Map<String, Object> map);	
}