package com.tzg.entitys.kff.evaluation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.commendation.Commendation;
import com.tzg.entitys.kff.devaluationModel.DevaluationModel;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.post.PostFile;

public class EvaluationDetailResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1505059238636317323L;

	// "0 未关注；1-已关注；2-不显示关注按钮"
	private java.lang.Integer followStatus = 2;
	// 0-未点赞；1-已点赞，2-未登录用户不显示 数字
	private java.lang.Integer praiseStatus = 2;

	private java.lang.Integer collectStatus = 2;

	// 捐赠用户列表
	private List<Commendation> commendationList;

	// 捐赠总金额
	private BigDecimal commendationNum = BigDecimal.ZERO;

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
	 * 评测表标签 包含 tagId,tagName的json字符串
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

	private java.lang.String projectChineseName;
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
	 * 缩略图对象
	 */
	private List<PostFile> postSmallImagesList;
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
	private Integer stickTop;
	private java.lang.String createUserName;
	private Integer userType;

	public Integer getStickTop() {
		return stickTop;
	}

	public void setStickTop(Integer stickTop) {
		this.stickTop = stickTop;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	// 综合测评分项列表
	private List<DevaluationModel> fullProEvaList;
	// 精选评测列表
	private List<Evaluation> hotEvaList;

	// private List<DevaluationModelDetail> activeProModelDetail;

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
		this.createTimeStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.createTime = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public java.lang.String getCreateTimeStr() {
		return this.createTimeStr;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTimeStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
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

	public java.lang.Integer getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(java.lang.Integer followStatus) {
		this.followStatus = followStatus;
	}

	public java.lang.String getProjectIcon() {
		return projectIcon;
	}

	public void setProjectIcon(java.lang.String projectIcon) {
		this.projectIcon = projectIcon;
	}

	public java.lang.String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(java.lang.String projectCode) {
		this.projectCode = projectCode;
	}

	public java.lang.String getProjectEnglishName() {
		return projectEnglishName;
	}

	public void setProjectEnglishName(java.lang.String projectEnglishName) {
		this.projectEnglishName = projectEnglishName;
	}

	public java.lang.String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(java.lang.String postTitle) {
		this.postTitle = postTitle;
	}

	public java.lang.Integer getPostType() {
		return postType;
	}

	public void setPostType(java.lang.Integer postType) {
		this.postType = postType;
	}

	public java.lang.String getPostShortDesc() {
		return postShortDesc;
	}

	public void setPostShortDesc(java.lang.String postShortDesc) {
		this.postShortDesc = postShortDesc;
	}

	public java.lang.String getPostSmallImages() {
		return postSmallImages;
	}

	public void setPostSmallImages(java.lang.String postSmallImages) {
		this.postSmallImages = postSmallImages;
	}

	public java.lang.Integer getCommentsNum() {
		return commentsNum;
	}

	public void setCommentsNum(java.lang.Integer commentsNum) {
		this.commentsNum = commentsNum;
	}

	public java.lang.Integer getPraiseNum() {
		return praiseNum;
	}

	public void setPraiseNum(java.lang.Integer praiseNum) {
		this.praiseNum = praiseNum;
	}

	public java.lang.Integer getPageviewNum() {
		return pageviewNum;
	}

	public void setPageviewNum(java.lang.Integer pageviewNum) {
		this.pageviewNum = pageviewNum;
	}

	public java.lang.Integer getDonateNum() {
		return donateNum;
	}

	public void setDonateNum(java.lang.Integer donateNum) {
		this.donateNum = donateNum;
	}

	public java.lang.Integer getCollectNum() {
		return collectNum;
	}

	public void setCollectNum(java.lang.Integer collectNum) {
		this.collectNum = collectNum;
	}

	public java.lang.String getCreateUserIcon() {
		return createUserIcon;
	}

	public void setCreateUserIcon(java.lang.String createUserIcon) {
		this.createUserIcon = createUserIcon;
	}

	public java.lang.String getCreateUserSignature() {
		return createUserSignature;
	}

	public void setCreateUserSignature(java.lang.String createUserSignature) {
		this.createUserSignature = createUserSignature;
	}

	public java.lang.String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(java.lang.String createUserName) {
		this.createUserName = createUserName;
	}

	public List<DevaluationModel> getFullProEvaList() {
		return fullProEvaList;
	}

	public void setFullProEvaList(List<DevaluationModel> fullProEvaList) {
		this.fullProEvaList = fullProEvaList;
	}

	public List<Evaluation> getHotEvaList() {
		return hotEvaList;
	}

	public void setHotEvaList(List<Evaluation> hotEvaList) {
		this.hotEvaList = hotEvaList;
	}

	public List<Commendation> getCommendationList() {
		return commendationList;
	}

	public void setCommendationList(List<Commendation> commendationList) {
		this.commendationList = commendationList;
	}

	public List<PostFile> getPostSmallImagesList() {
		return postSmallImagesList;
	}

	public void setPostSmallImagesList(List<PostFile> postSmallImagesList) {
		this.postSmallImagesList = postSmallImagesList;
	}

	public java.lang.Integer getPraiseStatus() {
		return praiseStatus;
	}

	public void setPraiseStatus(java.lang.Integer praiseStatus) {
		this.praiseStatus = praiseStatus;
	}

	public java.lang.Integer getCollectStatus() {
		return collectStatus;
	}

	public void setCollectStatus(java.lang.Integer collectStatus) {
		this.collectStatus = collectStatus;
	}

	public BigDecimal getCommendationNum() {
		return commendationNum;
	}

	public void setCommendationNum(BigDecimal commendationNum) {
		this.commendationNum = commendationNum;
	}

	public java.lang.String getProjectChineseName() {
		return projectChineseName;
	}

	public void setProjectChineseName(java.lang.String projectChineseName) {
		this.projectChineseName = projectChineseName;
	}

}
