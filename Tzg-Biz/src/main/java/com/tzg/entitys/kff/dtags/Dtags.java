package com.tzg.entitys.kff.dtags;

import java.io.Serializable;
import java.util.Date;

import com.tzg.common.utils.DateUtil;

public class Dtags implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6930345390801424910L;
	/**
	 * tagId
	 */
	private java.lang.Integer tagId;
	/**
	 * tagName
	 */
	private java.lang.String tagName;
	/**
	 * createUserId
	 */
	private java.lang.Integer createUserId;
	/**
	 * createTime
	 */
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
	/**
	 * updateTime
	 */
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;
	/**
	 * 状态:0-删除;1-有效
	 */
	private java.lang.Boolean status;

	private Integer typeId;
	//图标地址
	private String imgPath;
	//是否推荐：0-否，1-是
	private Integer stickTop;
	//操作推荐时间
	private Date stickUpdateTime;

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getStickTop() {
		return stickTop;
	}

	public void setStickTop(Integer stickTop) {
		this.stickTop = stickTop;
	}

	public Date getStickUpdateTime() {
		return stickUpdateTime;
	}

	public void setStickUpdateTime(Date stickUpdateTime) {
		this.stickUpdateTime = stickUpdateTime;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public void setTagId(java.lang.Integer value) {
		this.tagId = value;
	}

	public java.lang.Integer getTagId() {
		return this.tagId;
	}

	public void setTagName(java.lang.String value) {
		this.tagName = value;
	}

	public java.lang.String getTagName() {
		return this.tagName;
	}

	public void setCreateUserId(java.lang.Integer value) {
		this.createUserId = value;
	}

	public java.lang.Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTimeStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.createTime = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public java.lang.String getCreateTimeStr() {
		return this.createTimeStr;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTimeStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.updateTime = value;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public java.lang.String getUpdateTimeStr() {
		return this.updateTimeStr;
	}

	public void setStatus(java.lang.Boolean value) {
		this.status = value;
	}

	public java.lang.Boolean getStatus() {
		return this.status;
	}

}
