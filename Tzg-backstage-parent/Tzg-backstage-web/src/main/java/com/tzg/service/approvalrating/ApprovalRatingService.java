package com.tzg.service.approvalrating;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.approvalrating.ApprovalRating;
import com.tzg.entitys.approvalrating.ApprovalRatingMapper;
import com.tzg.service.base.BaseService;

/**
 * Created by cfour on 12/3/14.
 */
@Service
@Transactional
public class ApprovalRatingService extends BaseService {
	@Autowired
	private ApprovalRatingMapper approvalRatingMapper;

	public void save(ApprovalRating approvalRating) throws Exception {
		if (StringUtils.isBlank(approvalRating.getVcApprovalName())) {
			throw new Exception("审批等级不能为空！");
		}
		if (null != approvalRatingMapper.queryByApprovalName(approvalRating
				.getVcApprovalName())) {
			throw new Exception("审批等级已经存在！");
		}
		approvalRatingMapper.save(approvalRating);
	}

	public void update(ApprovalRating approvalRating) throws Exception {
		if (StringUtils.isBlank(approvalRating.getVcApprovalName())) {
			throw new Exception("审批等级不能为空！");
		}
		ApprovalRating rating = approvalRatingMapper
				.queryByApprovalName(approvalRating.getVcApprovalName());
		if (null != rating && rating.getId() != approvalRating.getId()) {
			throw new Exception("审批等级已经存在！");
		}
		approvalRatingMapper.update(approvalRating);
	}

	public void delete(int id) {
		approvalRatingMapper.deleteById(id);
	}

	public ApprovalRating findById(int id) {
		return approvalRatingMapper.findById(id);
	}

	public List<ApprovalRating> queryApprovalratingAll() {
		List<ApprovalRating> list = null;
		list = approvalRatingMapper.queryApprovalratingAll();
		return list;
	}

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public PageResult<ApprovalRating> findPage(PaginationQuery query)
			throws Exception {
		PageResult<ApprovalRating> result = null;
		try {
			Integer count = approvalRatingMapper.findPageCount(query
					.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<ApprovalRating> list = approvalRatingMapper.findPage(query
						.getQueryData());
				result = new PageResult<ApprovalRating>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
