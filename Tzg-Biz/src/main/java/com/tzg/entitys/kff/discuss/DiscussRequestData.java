package com.tzg.entitys.kff.discuss;

public class DiscussRequestData extends DiscussRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3902763587418426979L;

	private String token;

	private String projectName;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
