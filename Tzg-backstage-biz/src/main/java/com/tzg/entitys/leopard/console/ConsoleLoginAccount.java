package com.tzg.entitys.leopard.console;

import com.tzg.entitys.BaseModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by cfour on 12/1/14.
 */
public class ConsoleLoginAccount extends BaseModel implements Serializable{

	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1L;
	private String vcLoginName;
    private String vcLoginPassword;
    private String vcRealName;
    /**
     * 手机号码
     */ 	
	private String vcPhone;
    private Integer iValid;
    private Date dtCreate;
    private Date dtModify;
    private List<String> rolesId;

    public List<String> getRolesId() {
        return rolesId;
    }

    public void setRolesId(List<String> rolesId) {
        this.rolesId = rolesId;
    }

    public String getVcLoginName() {

        return vcLoginName;
    }

    public void setVcLoginName(String vcLoginName) {
        this.vcLoginName = vcLoginName;
    }

    public String getVcLoginPassword() {
        return vcLoginPassword;
    }

    public void setVcLoginPassword(String vcLoginPassword) {
        this.vcLoginPassword = vcLoginPassword;
    }

	public String getVcRealName() {
        return vcRealName;
    }

    public void setVcRealName(String vcRealName) {
        this.vcRealName = vcRealName;
    }

    public java.lang.String getVcPhone() {
		return vcPhone;
	}

	public void setVcPhone(java.lang.String vcPhone) {
		this.vcPhone = vcPhone;
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
