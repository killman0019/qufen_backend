package com.tzg.entitys.kff.tagstype;

import java.io.Serializable;
import java.util.Date;

public class TagsType implements Serializable {
	/** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/
	private static final long serialVersionUID = -780391952374885524L;

	private Integer typeId;

	private String typeName;

	private Date createTime;

	private Date updateTime;

	private Integer status;

	private Integer isShow;

	private Integer tagTypeOrderNumder;

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName == null ? null : typeName.trim();
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

	public Integer getTagTypeOrderNumder() {
		return tagTypeOrderNumder;
	}

	public void setTagTypeOrderNumder(Integer tagTypeOrderNumder) {
		this.tagTypeOrderNumder = tagTypeOrderNumder;
	}
}