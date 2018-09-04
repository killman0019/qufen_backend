package com.tzg.entitys.kff.dtags;

import java.io.Serializable;
import java.util.Date;

public class DtagsReponse implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/
	private static final long serialVersionUID = 4151664901941777564L;

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

	private Integer isShow;

	private Integer tagTypeOrderNumder;

	private String typeName;
	
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

	public java.lang.Integer getTagId() {
		return tagId;
	}

	public void setTagId(java.lang.Integer tagId) {
		this.tagId = tagId;
	}

	public java.lang.String getTagName() {
		return tagName;
	}

	public void setTagName(java.lang.String tagName) {
		this.tagName = tagName;
	}

	public java.lang.Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(java.lang.Integer createUserId) {
		this.createUserId = createUserId;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.lang.Boolean getStatus() {
		return status;
	}

	public void setStatus(java.lang.Boolean status) {
		this.status = status;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getTagTypeOrderNumder() {
		return tagTypeOrderNumder;
	}

	public void setTagTypeOrderNumder(Integer tagTypeOrderNumder) {
		this.tagTypeOrderNumder = tagTypeOrderNumder;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
