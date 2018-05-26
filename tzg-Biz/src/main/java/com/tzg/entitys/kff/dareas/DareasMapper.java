package com.tzg.entitys.kff.dareas;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface DareasMapper extends BaseMapper<Dareas, java.lang.String> {

	public List<Dareas> findProvinceAreas();

	public List<Dareas> findSubAreasByCode(Map<String,Object> map);	

}
