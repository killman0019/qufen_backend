package com.tzg.entitys.kff.article;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.tzg.entitys.kff.post.PostFile;

public class ArticleDetailShareResponse extends ArticleDetailResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6223753189608444037L;
	/**
	 * 评论数
	 */
	private java.lang.Integer discussNum = 0;

	private List<PostFile> postSmallImageslp;

	private JSON conCommendationlp;

	public JSON getConCommendationlp() {
		return conCommendationlp;
	}

	private String projectIcon;

	public String getProjectIcon() {
		return projectIcon;
	}

	public void setProjectIcon(String projectIcon) {
		this.projectIcon = projectIcon;
	}

	public void setConCommendationlp(JSON conCommendationlp) {
		this.conCommendationlp = conCommendationlp;
	}

	private Article article;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public java.lang.Integer getDiscussNum() {
		return discussNum;
	}

	public void setDiscussNum(java.lang.Integer discussNum) {
		this.discussNum = discussNum;
	}

	public List<PostFile> getPostSmallImageslp() {
		return postSmallImageslp;
	}

	public void setPostSmallImageslp(List<PostFile> postSmallImageslp) {
		this.postSmallImageslp = postSmallImageslp;
	}

}
