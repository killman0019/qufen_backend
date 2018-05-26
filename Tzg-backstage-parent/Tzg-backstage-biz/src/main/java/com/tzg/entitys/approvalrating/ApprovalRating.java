package com.tzg.entitys.approvalrating;

import java.io.Serializable;

import com.tzg.entitys.BaseModel;

/**
 * Created by cfour on 12/1/14.
 */
public class ApprovalRating extends BaseModel implements Serializable {

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1L;
	private String vcApprovalName;

    public String getVcApprovalName() {
        return vcApprovalName;
    }

    public void setVcApprovalName(String vcApprovalName) {
        this.vcApprovalName = vcApprovalName;
    }
}
