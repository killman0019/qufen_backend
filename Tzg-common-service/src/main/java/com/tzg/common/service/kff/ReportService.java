package com.tzg.common.service.kff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.report.ReportMapper;

@Service(value = "ReportService")
@Transactional
public class ReportService {
	@Autowired
	private ReportMapper reportMapper;

}
