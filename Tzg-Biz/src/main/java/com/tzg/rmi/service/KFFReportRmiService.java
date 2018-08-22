package com.tzg.rmi.service;

import java.util.List;

import com.tzg.entitys.kff.reportmodel.ReportModel;
import com.tzg.rest.exception.rest.RestServiceException;

/**
 * 
 * 
 * ClassName: KFFReportService  
 * Function: TODO 投诉的相关接口
 * date: 2018年8月13日 下午2:09:46  
 * 
 * @author zhangdd 
 * @version  
 * @since JDK 1.7 
 *
 */
public interface KFFReportRmiService {
	/**
	 * 
	 * TODO 获得投诉选择明细
	 * @param userId
	 * @return
	 * @throws RestServiceException
	 * @author zhangdd
	 * @data 2018年8月13日
	 *
	 */
	public List<ReportModel> getReportModelList() throws RestServiceException;

	/**
	 * 
	 * TODO 保存举报
	 * @param userId
	 * @param reportModelId
	 * @param contentId
	 * @return
	 * @throws RestServiceException
	 * @author zhangdd
	 * @param type 
	 * @data 2018年8月13日
	 *
	 */
	public void saveReport(Integer userId, Integer reportModelId, Integer contentId, Integer type) throws RestServiceException;

}
