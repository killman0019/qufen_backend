package com.tzg.entitys.leopard.role.resource;

import java.io.Serializable;
import java.util.Date;

import com.tzg.entitys.BaseModel;

/**
 * Created by cfour on 12/1/14.
 */
public class RoleResource extends BaseModel implements Serializable{
	/**
	* @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 1L;
	private int iRoleinfoID;
	private int iResourceID;
	
	private String iResourceIDStr;

	/********** 资源表相关属性 **************/
	private int iParentId;
	private String vcName;
	private String vcUrl;
	private int iType;
	private String vcButtonName;
	private int iValid;
	private Date dtCreate;
	private Date dtModify;

	public int getiParentId() {
		return iParentId;
	}

	public void setiParentId(int iParentId) {
		this.iParentId = iParentId;
	}

	public String getVcName() {
		return vcName;
	}

	public void setVcName(String vcName) {
		this.vcName = vcName;
	}

	public String getVcUrl() {
		return vcUrl;
	}

	public void setVcUrl(String vcUrl) {
		this.vcUrl = vcUrl;
	}

	public int getiType() {
		return iType;
	}

	public void setiType(int iType) {
		this.iType = iType;
	}

	public String getVcButtonName() {
		return vcButtonName;
	}

	public void setVcButtonName(String vcButtonName) {
		this.vcButtonName = vcButtonName;
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

	public int getiResourceID() {
		return iResourceID;
	}

	public void setiResourceID(int iResourceID) {
		this.iResourceID = iResourceID;
	}

	public int getiRoleinfoID() {
		return iRoleinfoID;
	}

	public void setiRoleinfoID(int iRoleinfoID) {
		this.iRoleinfoID = iRoleinfoID;
	}
	
	public String getiResourceIDStr() {
		return iResourceIDStr;
	}

	public void setiResourceIDStr(String iResourceIDStr) {
		this.iResourceIDStr = iResourceIDStr;
	}
}
