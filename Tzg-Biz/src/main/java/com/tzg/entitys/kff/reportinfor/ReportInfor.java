package com.tzg.entitys.kff.reportinfor;

import java.io.Serializable;
import java.util.Date;

public class ReportInfor implements Serializable {
	/**
	 * TODO
	 */
	private static final long serialVersionUID = 5985068551487759116L;

	private Integer id;

	private Integer reportModelId;

	private String reportUserName;

	private String reportUserMobile;

	private Integer reportUserId;

	private Date createTime;
	private String createTimeStr;

	private Date updateTime;

	private String updateTimeStr;

	private Integer status;

	private Integer reportedContentKeyId;

	public String getReportUserMobile() {
		return reportUserMobile;
	}

	public void setReportUserMobile(String reportUserMobile) {
		this.reportUserMobile = reportUserMobile;
	}

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReportModelId() {
		return reportModelId;
	}

	public void setReportModelId(Integer reportModelId) {
		this.reportModelId = reportModelId;
	}

	public String getReportUserName() {
		return reportUserName;
	}

	public void setReportUserName(String reportUserName) {
		this.reportUserName = reportUserName == null ? null : reportUserName.trim();
	}

	public Integer getReportUserId() {
		return reportUserId;
	}

	public void setReportUserId(Integer reportUserId) {
		this.reportUserId = reportUserId;
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

	public Integer getReportedContentKeyId() {
		return reportedContentKeyId;
	}

	public void setReportedContentKeyId(Integer reportedContentKeyId) {
		this.reportedContentKeyId = reportedContentKeyId;
	}
}