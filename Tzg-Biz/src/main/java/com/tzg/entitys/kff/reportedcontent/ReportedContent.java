package com.tzg.entitys.kff.reportedcontent;

import java.io.Serializable;
import java.util.Date;

public class ReportedContent implements Serializable {
	/**
	 * TODO
	 */
	private static final long serialVersionUID = -1290551210280774261L;

	private Integer reportedContentKeyId;

	private Integer reportedContentId;

	private Integer contentType;

	private Date contentReportedTime;

	private Integer status;

	private Integer vaild;

	private Integer reportedContentCreateId;

	private String reportedCreaterMobile;

	private Date reportedContentCreateTime;

	private String reportedContentCreateTimeStr;

	private String contentTitle;

	private String reportedContentUrl;

	private Integer consoleReportModelId;

	private String ortherReason;

	private Integer reportedDegree;

	private Date updateTime;

	private String updateTimeStr;

	public String getReportedContentCreateTimeStr() {
		return reportedContentCreateTimeStr;
	}

	public void setReportedContentCreateTimeStr(String reportedContentCreateTimeStr) {
		this.reportedContentCreateTimeStr = reportedContentCreateTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public Integer getReportedContentKeyId() {
		return reportedContentKeyId;
	}

	public void setReportedContentKeyId(Integer reportedContentKeyId) {
		this.reportedContentKeyId = reportedContentKeyId;
	}

	public Integer getReportedContentId() {
		return reportedContentId;
	}

	public void setReportedContentId(Integer reportedContentId) {
		this.reportedContentId = reportedContentId;
	}

	public Integer getContentType() {
		return contentType;
	}

	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}

	public Date getContentReportedTime() {
		return contentReportedTime;
	}

	public void setContentReportedTime(Date contentReportedTime) {
		this.contentReportedTime = contentReportedTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVaild() {
		return vaild;
	}

	public void setVaild(Integer vaild) {
		this.vaild = vaild;
	}

	public Integer getReportedContentCreateId() {
		return reportedContentCreateId;
	}

	public void setReportedContentCreateId(Integer reportedContentCreateId) {
		this.reportedContentCreateId = reportedContentCreateId;
	}

	public String getReportedCreaterMobile() {
		return reportedCreaterMobile;
	}

	public void setReportedCreaterMobile(String reportedCreaterMobile) {
		this.reportedCreaterMobile = reportedCreaterMobile == null ? null : reportedCreaterMobile.trim();
	}

	public Date getReportedContentCreateTime() {
		return reportedContentCreateTime;
	}

	public void setReportedContentCreateTime(Date reportedContentCreateTime) {
		this.reportedContentCreateTime = reportedContentCreateTime;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle == null ? null : contentTitle.trim();
	}

	public String getReportedContentUrl() {
		return reportedContentUrl;
	}

	public void setReportedContentUrl(String reportedContentUrl) {
		this.reportedContentUrl = reportedContentUrl == null ? null : reportedContentUrl.trim();
	}

	public Integer getConsoleReportModelId() {
		return consoleReportModelId;
	}

	public void setConsoleReportModelId(Integer consoleReportModelId) {
		this.consoleReportModelId = consoleReportModelId;
	}

	public String getOrtherReason() {
		return ortherReason;
	}

	public void setOrtherReason(String ortherReason) {
		this.ortherReason = ortherReason == null ? null : ortherReason.trim();
	}

	public Integer getReportedDegree() {
		return reportedDegree;
	}

	public void setReportedDegree(Integer reportedDegree) {
		this.reportedDegree = reportedDegree;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}