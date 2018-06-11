package com.tzg.entitys.kff.evaluation;

import java.io.Serializable;

public class EvaDtail implements Serializable {

	/**
	 * {"modelId":4,"modelName":"维度3","modelWeight":47,"score":4.1},{"modelId":4,"modelName":"维度2",
	 * "modelWeight":31,"score":4.3}
	 */
	private static final long serialVersionUID = -8447624940909181823L;
	private String modelId;
	private String modelName;
	private String modelWeight;
	private String score;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelWeight() {
		return modelWeight;
	}

	public void setModelWeight(String modelWeight) {
		this.modelWeight = modelWeight;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
}
