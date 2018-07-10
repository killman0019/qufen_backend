package com.tzg.entitys.kff.article;

import java.io.Serializable;
import java.util.List;

import com.tzg.common.utils.DateUtil;

public class ArticleRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6845949064192834427L;
	
	private String token;
	
	private java.lang.Integer createUserId;

	/**
     * projectId
     */ 	
	private java.lang.Integer projectId;
	
	private String postTitle;
    /**
     * 文章内容，目前限定30000字，图片信息用富文本保存在里边
     */ 	
	private java.lang.String articleContents;

	private String postSmallImages;
	
	/**
	 * 标签ID，名称的json串
     */ 	
	private String tagInfos;
	
	public String getTagInfos() {
		return tagInfos;
	}

	public void setTagInfos(String tagInfos) {
		this.tagInfos = tagInfos;
	}

	public void setArticleContents(java.lang.String value) {
		this.articleContents = value;
	}
	
	public java.lang.String getArticleContents() {
		return this.articleContents;
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

	public String getPostSmallImages() {
		return postSmallImages;
	}

	public void setPostSmallImages(String postSmallImages) {
		this.postSmallImages = postSmallImages;
	}

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

	
}

