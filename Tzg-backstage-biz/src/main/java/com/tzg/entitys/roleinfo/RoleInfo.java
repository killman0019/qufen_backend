package com.tzg.entitys.roleinfo;

import com.tzg.entitys.BaseModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by cfour on 12/1/14.
 */
public class RoleInfo extends BaseModel implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -228546060029271617L;
	private String vcRoleName;
    private Integer iApprovalRatingID;
    private Integer iValid;
    private Date dtCreate;
    private Date dtModify;
    private List<String> resourcesId;

    public List<String> getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(List<String> resourcesId) {
        this.resourcesId = resourcesId;
    }

    public String getVcRoleName() {
        return vcRoleName;
    }

    public void setVcRoleName(String vcRoleName) {
        this.vcRoleName = vcRoleName;
    }

    public Integer getiApprovalRatingID() {
        return iApprovalRatingID;
    }

    public void setiApprovalRatingID(Integer iApprovalRatingID) {
        this.iApprovalRatingID = iApprovalRatingID;
    }

    public Integer getiValid() {
        return iValid;
    }

    public void setiValid(Integer iValid) {
        this.iValid = iValid;
    }

    public Date getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Date dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Date getDtModify() {
        return dtModify;
    }

    public void setDtModify(Date dtModify) {
        this.dtModify = dtModify;
    }
}
