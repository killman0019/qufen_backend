package com.tzg.entitys.kff.projectManage;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;

public interface ProjectManageMapper extends BaseMapper<ProjectManageMapper, java.lang.Integer> {

	List<ProjectManage> findByMap(Map<String, Object> map);

}