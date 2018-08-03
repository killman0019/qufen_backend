package com.tzg.entitys.kff.projectForTab;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;

public interface ProjectForTabMapper extends BaseMapper<ProjectForTab, java.lang.Integer>{
	int deleteByPrimaryKey(Integer projectTabId);

	int insert(ProjectForTab record);

	int insertSelective(ProjectForTab record);

	ProjectForTab selectByPrimaryKey(Integer projectTabId);

	int updateByPrimaryKeySelective(ProjectForTab record);

	int updateByPrimaryKey(ProjectForTab record);

	List<ProjectForTab> findByMap(Map<String, Object> map);
}