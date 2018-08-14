package com.tzg.common.service.kff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.reportinfor.ReportInfor;
import com.tzg.entitys.kff.reportinfor.ReportInforMapper;

@Service(value = "reportInforService")
@Transactional
public class ReportInforService {
	@Autowired
	private ReportInforMapper reportInforMapper;

	public void save(ReportInfor reportInfor) {
		// TODO 保存
		reportInforMapper.save(reportInfor);
	}
}
