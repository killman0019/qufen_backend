package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.reportedcontent.ReportedContent;
import com.tzg.entitys.kff.reportedcontent.ReportedContentMapper;

@Service(value = "reportedContentService")
@Transactional
public class ReportedContentService {

	@Autowired
	private ReportedContentMapper reportedContentMapper;

	public void save(ReportedContent reportedContent) {
		// TODO 保存
		reportedContentMapper.save(reportedContent);
	}

	public List<ReportedContent> findByMap(Map<String, Object> reportedContentMap) {
		// TODO map 查询
		return reportedContentMapper.findByMap(reportedContentMap);
	}

	public void increaseReportedDegree(Integer reportedContentKeyId) {
		// TODO 投诉册数多一条
		reportedContentMapper.increaseReportDegree(reportedContentKeyId);
	}
}
