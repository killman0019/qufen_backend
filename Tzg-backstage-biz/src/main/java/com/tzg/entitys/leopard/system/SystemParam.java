package com.tzg.entitys.leopard.system;

import com.tzg.entitys.BaseModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cfour on 12/1/14.
 */
public class SystemParam extends BaseModel implements Serializable {
	
    /**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1L;
	private String vcParamName;
    private String vcParamCode;
    private String vcParamValue;
    private int iValid;
    private Date dtCreate;
    private Date dtModify;

    public String getVcParamName() {
        return vcParamName;
    }

    public void setVcParamName(String vcParamName) {
        this.vcParamName = vcParamName;
    }

    public String getVcParamCode() {
        return vcParamCode;
    }

    public void setVcParamCode(String vcParamCode) {
        this.vcParamCode = vcParamCode;
    }

    public String getVcParamValue() {
        return vcParamValue;
    }

    public void setVcParamValue(String vcParamValue) {
        this.vcParamValue = vcParamValue;
    }

    public int getiValid() {
        return iValid;
    }

    public void setiValid(int iValid) {
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
