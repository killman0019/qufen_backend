package com.tzg.entitys.approvalflow;

import java.io.Serializable;

import com.tzg.entitys.BaseModel;

/**
 * Created by cfour on 12/1/14.
 */
public class ApprovalFlow extends BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3216970655946677188L;
	private int vcApprovalType;
	private String vcApprovalFlow;

	public int getVcApprovalType() {
		return vcApprovalType;
	}

	public void setVcApprovalType(int vcApprovalType) {
		this.vcApprovalType = vcApprovalType;
	}

	public String getVcApprovalFlow() {
		return vcApprovalFlow;
	}

	public void setVcApprovalFlow(String vcApprovalFlow) {
		this.vcApprovalFlow = vcApprovalFlow;
	}
}
