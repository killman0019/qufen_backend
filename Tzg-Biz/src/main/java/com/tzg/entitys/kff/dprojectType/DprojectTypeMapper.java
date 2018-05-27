package com.tzg.entitys.kff.dprojectType;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface DprojectTypeMapper extends BaseMapper<DprojectType, java.lang.Integer> {

	List<DprojectType> findAllProjectTypes();	

}
