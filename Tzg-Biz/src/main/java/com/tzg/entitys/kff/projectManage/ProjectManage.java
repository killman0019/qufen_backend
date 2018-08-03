package com.tzg.entitys.kff.projectManage;

import java.io.Serializable;

public class ProjectManage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7793160353862654176L;

	private Integer tabId;

	private Integer tabOrderNumber;

	private Integer tabProjectNum;

	private Integer isShow;

	private Integer isDefaultOpen;

	private String memo;



	private String tabTitle;

	private Integer status;



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

	public Integer getTabProjectNum() {
		return tabProjectNum;
	}

	public void setTabProjectNum(Integer tabProjectNum) {
		this.tabProjectNum = tabProjectNum;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getIsDefaultOpen() {
		return isDefaultOpen;
	}

	public void setIsDefaultOpen(Integer isDefaultOpen) {
		this.isDefaultOpen = isDefaultOpen;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}



	public String getTabTitle() {
		return tabTitle;
	}

	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle == null ? null : tabTitle.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}