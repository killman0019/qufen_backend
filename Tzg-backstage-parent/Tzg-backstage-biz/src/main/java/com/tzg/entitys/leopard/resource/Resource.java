package com.tzg.entitys.leopard.resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tzg.entitys.BaseModel;

/**
 * Created by cfour on 12/1/14.
 */
public class Resource extends BaseModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3739377862793114176L;
	private Integer iParentId;
	/**
	 * 资源名称
	 */
	private java.lang.String vcName;
	/**
	 * 访问URL
	 */
	private java.lang.String vcUrl;
	/**
	 * 资源类型，1：菜单资源，2：按钮资源
	 */
	private java.lang.Integer iType;
	/**
	 * 按钮标示
	 */
	private java.lang.String vcButtonName;
	/**
	 * 图标
	 */
	private java.lang.String vcIcon;
	/**
	 * 排列顺序
	 */
	private java.lang.Integer isort;
	
	/**
	 * 是否选中
	 */
	private boolean checked ;
	
	private Integer iValid;
	private Date dtCreate;
	private Date dtModify;

	private List<Resource> children = new ArrayList<Resource>();

	public Integer getiParentId() {
		return iParentId;
	}

	public void setiParentId(Integer iParentId) {
		this.iParentId = iParentId;
	}

	public java.lang.String getVcName() {
		return vcName;
	}

	public void setVcName(java.lang.String vcName) {
		this.vcName = vcName;
	}

	public java.lang.String getVcUrl() {
		return vcUrl;
	}

	public void setVcUrl(java.lang.String vcUrl) {
		this.vcUrl = vcUrl;
	}

	public java.lang.Integer getiType() {
		return iType;
	}

	public void setiType(java.lang.Integer iType) {
		this.iType = iType;
	}

	public java.lang.String getVcButtonName() {
		return vcButtonName;
	}

	public void setVcButtonName(java.lang.String vcButtonName) {
		this.vcButtonName = vcButtonName;
	}

	public java.lang.String getVcIcon() {
		return vcIcon;
	}

	public void setVcIcon(java.lang.String vcIcon) {
		this.vcIcon = vcIcon;
	}

	public java.lang.Integer getIsort() {
		return isort;
	}

	public void setIsort(java.lang.Integer isort) {
		this.isort = isort;
	}


	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
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

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

}
