package com.tzg.entitys.kff.discuss;

import java.io.Serializable;

import com.tzg.common.utils.DateUtil;

public class DiscussRequest implements Serializable {

    private String token;
    
    /**
     * projectId
     */ 	
	private java.lang.Integer projectId;
	
	private String postTitle;
	
	private java.lang.Integer createUserId;
    /**
     * 讨论内容
     */ 	
	private java.lang.String disscussContents;
    /**
     * 图片url等json信息；目前保存9张
     */ 	
	private java.lang.String discussImages;
    /**
     * 标签ID，名称的json串
     */ 	
	private java.lang.String tagInfos;


	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public java.lang.Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(java.lang.Integer createUserId) {
		this.createUserId = createUserId;
	}

	public java.lang.Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(java.lang.Integer projectId) {
		this.projectId = projectId;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public void setDisscussContents(java.lang.String value) {
		this.disscussContents = value;
	}
	
	public java.lang.String getDisscussContents() {
		return this.disscussContents;
	}
	
	public void setDiscussImages(java.lang.String value) {
		this.discussImages = value;
	}
	
	public java.lang.String getDiscussImages() {
		return this.discussImages;
	}
	
	public void setTagInfos(java.lang.String value) {
		this.tagInfos = value;
	}
	
	public java.lang.String getTagInfos() {
		return this.tagInfos;
	}

	
}

