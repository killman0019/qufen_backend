package com.tzg.entitys.approvalrating;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

/**
 * Created by cfour on 12/1/14.
 */
@Repository
public interface ApprovalRatingMapper extends
		BaseMapper<ApprovalRating, Integer> {

	public List<ApprovalRating> queryApprovalratingAll();
	
	ApprovalRating queryByApprovalName(String vcApprovalName);
}
