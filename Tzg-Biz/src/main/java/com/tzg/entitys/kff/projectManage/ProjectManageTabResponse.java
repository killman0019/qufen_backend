package com.tzg.entitys.kff.projectManage;

import java.io.Serializable;

public class ProjectManageTabResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8164221352407310713L;

	private Integer tabId;

	private Integer tabOrderNumber;

	private String tabTitle;
	private Integer isDefaultOpen;

	public Integer getIsDefaultOpen() {
		return isDefaultOpen;
	}

	public void setIsDefaultOpen(Integer isDefaultOpen) {
		this.isDefaultOpen = isDefaultOpen;
	}

	public Integer getTabId() {
		return tabId;
	}

	public void setTabId(Integer tabId) {
		this.tabId = tabId;
	}

	public Integer getTabOrderNumber() {
		return tabOrderNumber;
	}

	public void setTabOrderNumber(Integer tabOrderNumber) {
		this.tabOrderNumber = tabOrderNumber;
	}

	public String getTabTitle() {
		return tabTitle;
	}

	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}

}
