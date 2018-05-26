package com.tzg.entitys.projectcategy;

import java.io.Serializable;

public class Projectcategy implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4203249675305796043L;

	private Integer id;

	private String vcName;

	private Integer isort;

	private Integer istate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVcName() {
		return vcName;
	}

	public void setVcName(String vcName) {
		this.vcName = vcName;
	}

	public Integer getIsort() {
		return isort;
	}

	public void setIsort(Integer isort) {
		this.isort = isort;
	}

	public Integer getIstate() {
		return istate;
	}

	public void setIstate(Integer istate) {
		this.istate = istate;
	}

}
