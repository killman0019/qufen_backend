package com.tzg.entitys.kff.reportedcontent;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface ReportedContentMapper extends BaseMapper<ReportedContent, Integer> {

	List<ReportedContent> findByMap(Map<String, Object> reportedContentMap);

	void increaseReportDegree(Integer reportedContentKeyId);

	Integer findIsReportToPContentFromUserId(Map<String, Object> map);

}