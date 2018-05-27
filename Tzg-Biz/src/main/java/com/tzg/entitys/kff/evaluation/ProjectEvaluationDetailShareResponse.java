package com.tzg.entitys.kff.evaluation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.tzg.entitys.kff.article.Article;
import com.tzg.entitys.kff.commendation.Commendation;
import com.tzg.entitys.kff.devaluationModel.DevaluationModel;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.post.Post;

public class ProjectEvaluationDetailShareResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1710396913595208186L;

	private Evaluation evaluation;

	private List<Evaluation> evaluations;

	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	private List<DevaluationModel> devaluationModelList;

	private DevaluationModel devaluationModel;
	/**
	 * 文章详情
	 */
	private String articleText;

	public String getArticleText() {
		return articleText;
	}

	public void setArticleText(String articleText) {
		this.articleText = articleText;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public List<Commendation> getCommendationList() {
		return commendationList;
	}

	public void setCommendationList(List<Commendation> commendationList) {
		this.commendationList = commendationList;
	}

	private String modelName;

	private List<Commendation> commendationList;

	public DevaluationModel getDevaluationModel() {
		return devaluationModel;
	}

	public void setDevaluationModel(DevaluationModel devaluationModel) {
		this.devaluationModel = devaluationModel;
	}

	private Post post;
	private String dtags;

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}

	public String getDtags() {
		return dtags;
	}

	public void setDtags(String evaluationTags) {
		this.dtags = evaluationTags;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<DevaluationModel> getDevaluationModelList() {
		return devaluationModelList;
	}

	public void setDevaluationModelList(List<DevaluationModel> devaluationModelList) {
		this.devaluationModelList = devaluationModelList;
	}

}
