package com.tzg.service.approvalflow;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.approvalflow.ApprovalFlow;
import com.tzg.entitys.approvalflow.ApprovalFlowMapper;
import com.tzg.service.base.BaseService;

/**
 * Created by cfour on 12/3/14.
 */
@Service
@Transactional
public class ApprovalFlowService extends BaseService {

	@Autowired
	private ApprovalFlowMapper approvalFlowMapper;

	/**
	 * 
	 * @param approvalFlow
	 * @throws Exception
	 */
	public void save(ApprovalFlow approvalFlow) throws Exception {
		if (StringUtils.isBlank(approvalFlow.getVcApprovalFlow())) {
			throw new Exception("审批流程不能为空！");
		}
		Integer primaryId = null;
		if (approvalFlow != null) {
			primaryId = approvalFlowMapper
					.queryInfoByVcApprovalType(approvalFlow.getVcApprovalType());
		}
		if (primaryId != null) {
			throw new Exception("该流程类型记录已存在");
		} 
			approvalFlowMapper.save(approvalFlow);
		
	}

	/**
	 * 
	 * @param approvalFlow
	 * @return
	 */
	public void update(ApprovalFlow approvalFlow) throws Exception {
		if (StringUtils.isBlank(approvalFlow.getVcApprovalFlow())) {
			throw new Exception("审批流程不能为空！");
		}
		Integer primaryId = null;		
		if (approvalFlow != null) {
			primaryId = approvalFlowMapper
					.queryInfoByVcApprovalType(approvalFlow.getVcApprovalType());
		}
		if (primaryId != approvalFlow.getId()) {
			throw new Exception("该流程类型记录已存在");
		} 		
		approvalFlowMapper.update(approvalFlow);

	}

	public ApprovalFlow findById(int id) {
		return approvalFlowMapper.findById(id);
	}

	public ApprovalFlow findByType(int type) {
		return approvalFlowMapper.findByType(type);
	}

	@Transactional(readOnly = true)
	public PageResult<ApprovalFlow> findPage(PaginationQuery query)
			throws Exception {
		PageResult<ApprovalFlow> result = null;
		try {
			Integer count = approvalFlowMapper.findPageCount(query
					.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<ApprovalFlow> list = approvalFlowMapper.findPage(query
						.getQueryData());
				result = new PageResult<ApprovalFlow>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
