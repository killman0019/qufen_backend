package com.tzg.entitys.kff.reportmodel;

import java.io.Serializable;
import java.util.Date;

public class ReportModel implements Serializable {
	/**
	 * 投诉列表
	 */
	private static final long serialVersionUID = 2660173205777797768L;

	private Integer reportId;

	private Date createTime;

	private Date updateTime;
	/**
	 * 状态 1有效 0 无效
	 */
	private Integer status;
	/**
	 * 是否展示 1 展示 0 不展示
	 */
	private Integer isShow;

	private String mome;
	/**
	 * 投诉列表名称
	 */
	private String reportName;

	private String createTimeStr;

	private String updateTimeStr;

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getMome() {
		return mome;
	}

	public void setMome(String mome) {
		this.mome = mome == null ? null : mome.trim();
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName == null ? null : reportName.trim();
	}
}