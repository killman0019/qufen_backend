package com.tzg.entitys.kff.evaluation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.devaluationModel.DevaluationModel;

public class ProjectEvaluationDetailResponse implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = -1505059238636317323L;
/**
     * evaluationId
     */ 	
	private java.lang.Integer evaluationId;
    /**
     * projectId
     */ 	
	private java.lang.Integer projectId;
	private java.lang.Integer postId;
	private java.lang.String postUuid;
    /**
     * 1-简单测评；2-全面系统专业测评;3-部分系统专业评测；4-用户自定义专业测评
     */ 	
	private java.lang.Integer modelType;
    /**
     * 1-10，小数点1位
     */ 	
	private BigDecimal totalScore = BigDecimal.ZERO;
    /**
     * 专业评测各子项评测内容详情json字符串
     */ 	
	private java.lang.String professionalEvaDetail;
    /**
     * 评测描述信息
     */ 	
	private java.lang.String evauationContent;
    /**
     * 评测表标签
     */ 	
	private java.lang.String evaluationTags;
    /**
     * 0删除；1有效
     */ 	
	private java.lang.Integer status = 1;
    /**
     * createTime
     */ 	
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
    /**
     * updateTime
     */ 	
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;
    /**
     * createUserId
     */ 	
	private java.lang.Integer createUserId;

	   /**
     * projectIcon
     */ 	
	private java.lang.String projectIcon;
    /**
     * projectCode
     */ 	
	private java.lang.String projectCode;
    /**
     * projectEnglishName
     */ 	
	private java.lang.String projectEnglishName;
    /**
     * 帖子标题
     */ 	
	private java.lang.String postTitle;
    /**
     * 帖子类型：1-评测；2-讨论；3-文章
     */ 	
	private java.lang.Integer postType = 1;
    /**
     * 剪短描述
     */ 	
	private java.lang.String postShortDesc;
    /**
     * 缩略图json，目前最多三张图的url等信息
     */ 	
	private java.lang.String postSmallImages;
    /**
     * 评论数量
     */ 	
	private java.lang.Integer commentsNum = 0;
    /**
     * 点赞数
     */ 	
	private java.lang.Integer praiseNum = 0;
    /**
     * 浏览量
     */ 	
	private java.lang.Integer pageviewNum = 0;
    /**
     * 捐赠人数
     */ 	
	private java.lang.Integer donateNum = 0;
    /**
     * 收藏人数
     */ 	
	private java.lang.Integer collectNum = 0;
    /**
     * 用户头像
     */ 	
	private java.lang.String createUserIcon;
    /**
     * 用户签名
     */ 	
	private java.lang.String createUserSignature;
    /**
     * 用户昵称
     */ 	
	private java.lang.String createUserName;
	
	//综合测评分项列表
	private List<DevaluationModel> fullProEvaList ;
	//精选评测列表
	private List<Evaluation> hotEvaList;
	
	
	public java.lang.Integer getPostId() {
		return postId;
	}

	public void setPostId(java.lang.Integer postId) {
		this.postId = postId;
	}

	public java.lang.String getPostUuid() {
		return postUuid;
	}

	public void setPostUuid(java.lang.String postUuid) {
		this.postUuid = postUuid;
	}

	public void setEvaluationId(java.lang.Integer value) {
		this.evaluationId = value;
	}
	
	public java.lang.Integer getEvaluationId() {
		return this.evaluationId;
	}
	
	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}
	
	public java.lang.Integer getProjectId() {
		return this.projectId;
	}
	

	
	public java.lang.Integer getModelType() {
		return modelType;
	}

	public void setModelType(java.lang.Integer modelType) {
		this.modelType = modelType;
	}

	public void setTotalScore(BigDecimal value) {
		this.totalScore = value;
	}
	
	public BigDecimal getTotalScore() {
		return this.totalScore;
	}
	
	public void setProfessionalEvaDetail(java.lang.String value) {
		this.professionalEvaDetail = value;
	}
	
	public java.lang.String getProfessionalEvaDetail() {
		return this.professionalEvaDetail;
	}
	
	public void setEvauationContent(java.lang.String value) {
		this.evauationContent = value;
	}
	
	public java.lang.String getEvauationContent() {
		return this.evauationContent;
	}
	
	public void setEvaluationTags(java.lang.String value) {
		this.evaluationTags = value;
	}
	
	public java.lang.String getEvaluationTags() {
		return this.evaluationTags;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public java.lang.String getCreateTimeStr() {
		return this.createTimeStr;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTimeStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public java.lang.String getUpdateTimeStr() {
		return this.updateTimeStr;
	}
	
	public void setCreateUserId(java.lang.Integer value) {
		this.createUserId = value;
	}
	
	public java.lang.Integer getCreateUserId() {
		return this.createUserId;
	}

	
}

