package com.tzg.entitys.kff.article;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Article implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2900612070269248941L;
	/**
     * articleId
     */ 	
	private java.lang.Integer articleId;
    /**
     * postId
     */ 	
	private java.lang.Integer postId;
	
	private String postUuid;
    /**
     * 文章内容，目前限定30000字，图片信息用富文本保存在里边
     */ 	
	private java.lang.String articleContents;

	
	public void setArticleId(java.lang.Integer value) {
		this.articleId = value;
	}
	
	public java.lang.Integer getArticleId() {
		return this.articleId;
	}
	
	public String getPostUuid() {
		return postUuid;
	}

	public void setPostUuid(String postUuid) {
		this.postUuid = postUuid;
	}

	public void setPostId(java.lang.Integer value) {
		this.postId = value;
	}
	
	public java.lang.Integer getPostId() {
		return this.postId;
	}
	
	public void setArticleContents(java.lang.String value) {
		this.articleContents = value;
	}
	
	public java.lang.String getArticleContents() {
		return this.articleContents;
	}

	
}

