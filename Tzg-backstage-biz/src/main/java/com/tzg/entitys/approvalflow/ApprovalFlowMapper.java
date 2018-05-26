package com.tzg.entitys.approvalflow;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

/**
 * Created by cfour on 12/1/14.
 */
@Repository
public interface ApprovalFlowMapper extends
		BaseMapper<ApprovalFlow, Serializable> {

	public ApprovalFlow findById(int id);

	public ApprovalFlow findByType(int type);

	/**
	 * 
	 * @param vcApprovalType
	 * @return
	 */
	public Integer queryInfoByVcApprovalType(Integer vcApprovalType);
}
