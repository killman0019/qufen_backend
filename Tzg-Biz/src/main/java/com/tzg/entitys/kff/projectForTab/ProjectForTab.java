package com.tzg.entitys.kff.projectForTab;

import java.io.Serializable;

public class ProjectForTab implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7748157680411415026L;

	private Integer projectTabId;

	private String projectCode;

	private String projectChineseName;

	private Integer tabId;

	private Integer status;

	public Integer getProjectTabId() {
		return projectTabId;
	}

	public void setProjectTabId(Integer projectTabId) {
		this.projectTabId = projectTabId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode == null ? null : projectCode.trim();
	}

	public String getProjectChineseName() {
		return projectChineseName;
	}

	public void setProjectChineseName(String projectChineseName) {
		this.projectChineseName = projectChineseName == null ? null : projectChineseName.trim();
	}

	public Integer getTabId() {
		return tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}