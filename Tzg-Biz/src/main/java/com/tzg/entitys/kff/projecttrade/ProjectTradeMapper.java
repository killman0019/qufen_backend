package com.tzg.entitys.kff.projecttrade;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface ProjectTradeMapper extends BaseMapper<ProjectTrade, Integer> {

	List<ProjectTrade> findByMap(Map<String, String> map);

	void updateBatch(List<ProjectTrade> projectTradeList);

}