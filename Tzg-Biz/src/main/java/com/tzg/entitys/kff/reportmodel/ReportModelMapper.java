package com.tzg.entitys.kff.reportmodel;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface ReportModelMapper extends BaseMapper<ReportModel, Integer> {

	List<ReportModel> findByMap(Map<String, Object> reportMap);

}