package com.tzg.entitys.kff.article;

public class ArticleRequestData extends ArticleRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3281625326011486121L;

	private String projectName;
	private String postTitle;
	private String token;
	private String toHtmlTags;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToHtmlTags() {
		return toHtmlTags;
	}

	public void setToHtmlTags(String toHtmlTags) {
		this.toHtmlTags = toHtmlTags;
	}
}
