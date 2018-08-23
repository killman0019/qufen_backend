package com.tzg.entitys.kff.tagstype;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.tzg.entitys.kff.dtags.Dtags;

public class TagsTypeResponse implements Serializable {
	/** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/
	private static final long serialVersionUID = -1119294046932589605L;

	private Integer typeId;

	private String typeName;

	private Date createTime;

	private Date updateTime;

	private Integer status;

	private Integer isShow;

	private Integer tagTypeOrderNumder;

	private List<Dtags> dtagsList;

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
		this.typeName = typeName;
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

	public List<Dtags> getDtagsList() {
		return dtagsList;
	}

	public void setDtagsList(List<Dtags> dtagsList) {
		this.dtagsList = dtagsList;
	}

}
