package com.tzg.entitys.kff.evaluation;

import java.io.Serializable;

public class EvaluationData extends EvaluationRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6273566010976853954L;

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

	private String token;
	private String projectName;

}
