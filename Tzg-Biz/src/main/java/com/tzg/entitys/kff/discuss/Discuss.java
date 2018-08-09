package com.tzg.entitys.kff.discuss;

import java.io.Serializable;
import java.util.Date;

import com.tzg.common.utils.DateUtil;

public class Discuss implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2419310933659029173L;
	/**
	 * discussId
	 */
	private java.lang.Integer discussId;
	/**
	 * postId
	 */
	private java.lang.Integer postId;
	/**
	 * 讨论内容
	 */
	private java.lang.String disscussContents;

	private java.lang.String postUuid;
	/**
	 * 标签ID，名称的json串
	 */
	private java.lang.String tagInfos;

	private Integer disStickTop;
	
	private Date disStickUpdateTime;

	public Integer getDisStickTop() {
		return disStickTop;
	}

	public void setDisStickTop(Integer disStickTop) {
		this.disStickTop = disStickTop;
	}

	public Date getDisStickUpdateTime() {
		return disStickUpdateTime;
	}

	public void setDisStickUpdateTime(Date disStickUpdateTime) {
		this.disStickUpdateTime = disStickUpdateTime;
	}

	public void setDiscussId(java.lang.Integer value) {
		this.discussId = value;
	}

	public java.lang.Integer getDiscussId() {
		return this.discussId;
	}

	public void setPostId(java.lang.Integer value) {
		this.postId = value;
	}

	public java.lang.Integer getPostId() {
		return this.postId;
	}

	public void setDisscussContents(java.lang.String value) {
		this.disscussContents = value;
	}

	public java.lang.String getDisscussContents() {
		return this.disscussContents;
	}

	public java.lang.String getPostUuid() {
		return postUuid;
	}

	public void setPostUuid(java.lang.String postUuid) {
		this.postUuid = postUuid;
	}

	public void setTagInfos(java.lang.String value) {
		this.tagInfos = value;
	}

	public java.lang.String getTagInfos() {
		return this.tagInfos;
	}

}
