package com.tzg.entitys.kff.devaluationModel;

import java.io.Serializable;

public class DevaluationModelRequest implements Serializable {

	private Integer projectId;
	private String token;
	// 包含 modelName, score,modelWeight 3项的json数组
	// modelWeight是占比,小数点2位 ( 例：0.32代表32%)，一组中所有modelWeight相加和位1
	private String professionalEvaDetail;

	private Integer createUserId;
	
	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getProfessionalEvaDetail() {
		return professionalEvaDetail;
	}

	public void setProfessionalEvaDetail(String professionalEvaDetail) {
		this.professionalEvaDetail = professionalEvaDetail;
	}

}
