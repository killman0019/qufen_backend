package com.tzg.entitys.kff.commentlibrary;

import java.io.Serializable;
import java.util.Date;

public class CommentLibrary implements Serializable {
	/** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/
	private static final long serialVersionUID = -7177962293845368984L;

	private Integer id;

	private String content;

	private Integer type;

	private Date createTime;

	private Date updateTime;

	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
}