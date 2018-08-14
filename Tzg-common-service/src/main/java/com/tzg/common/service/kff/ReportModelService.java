package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.reportmodel.ReportModel;
import com.tzg.entitys.kff.reportmodel.ReportModelMapper;

@Service(value = "ReportModelService")
@Transactional
public class ReportModelService {
	@Autowired
	private ReportModelMapper reportModelMapper;

	@Transactional(readOnly = true)
	public List<ReportModel> findByMap(Map<String, Object> reportMap) {
		// TODO 根据map 进行查询

		return reportModelMapper.findByMap(reportMap);
	}

}
