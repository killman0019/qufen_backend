package com.tzg.entitys.kff.bgroupmustread;

import java.io.Serializable;
import java.util.Date;

public class BGroupMustRead implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3449510132538172722L;

	private Integer bGroupMustReadId;

	private Integer postId;

	private String title;

	private String createName;

	private Integer pageviewNum;

	private Date createTime;
	private Date updateTime;
	private String createTimeStr;
	private String updateTimeStr;
	private Integer isShow;

	private Integer postType;

	private Integer status;

	private String projectCode;

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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public Integer getbGroupMustReadId() {
		return bGroupMustReadId;
	}

	public void setbGroupMustReadId(Integer bGroupMustReadId) {
		this.bGroupMustReadId = bGroupMustReadId;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName == null ? null : createName.trim();
	}

	public Integer getPageviewNum() {
		return pageviewNum;
	}

	public void setPageviewNum(Integer pageviewNum) {
		this.pageviewNum = pageviewNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getPostType() {
		return postType;
	}

	public void setPostType(Integer postType) {
		this.postType = postType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}