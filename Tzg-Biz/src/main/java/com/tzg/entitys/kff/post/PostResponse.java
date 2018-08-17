package com.tzg.entitys.kff.post;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.tzg.common.utils.DateUtil;

public class PostResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2265875960205049361L;

	// 0 未关注；1-已关注；2-不显示关注按钮
	private java.lang.Integer followStatus = 2;

	private java.lang.String actionDesc;

	private java.lang.Integer actionType;

	/**
	 * postId
	 */
	private java.lang.Integer postId;
	/**
	 * 项目ID
	 */
	private java.lang.Integer projectId;
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
	 * projectChineseName
	 */
	private java.lang.String projectChineseName;

	/**
	 * 一句话简介
	 */
	private java.lang.String projectSignature;

	/**
	 * 项目总评分：1-10精确到1位
	 */
	private BigDecimal totalScore = BigDecimal.ZERO;

	/**
	 * 帖子标题
	 */
	private java.lang.String postTitle;
	/**
	 * 帖子类型：1-评测；2-讨论；3-文章
	 */
	private java.lang.Integer postType;
	/**
	 * 剪短描述
	 */
	private java.lang.String postShortDesc;

	/**
	 * 缩略图json，目前最多三张图的url等信息
	 */
	private List<PostFile> postSmallImagesList;

	/**
	 * 缩略图json，目前最多三张图的url等信息
	 */
	private java.lang.String postSmallImages;
	/**
	 * 评论数量
	 */
	private java.lang.Integer commentsNum;
	/**
	 * 点赞数
	 */
	private java.lang.Integer praiseNum;
	/**
	 * 浏览量
	 */
	private java.lang.Integer pageviewNum;
	/**
	 * 捐赠人数
	 */
	private java.lang.Integer donateNum;
	/**
	 * 收藏人数
	 */
	private java.lang.Integer collectNum;
	/**
	 * 用户ID
	 */
	private java.lang.Integer createUserId;
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
	/**
	 * 用户类型
	 */
	private Integer userType;
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
	 * 状态：0-删除；1-有效
	 */
	private java.lang.Integer status;

	// discuss内容
	/**
	 * discussId
	 */
	private java.lang.Integer discussId;
	/**
	 * 讨论内容
	 */
	private java.lang.String disscussContents;
	/**
	 * 标签ID，名称的json串
	 */
	private java.lang.String tagInfos;

	// eva内容
	/**
	 * 1-10，小数点1位
	 */
	private BigDecimal evaTotalScore = BigDecimal.ZERO;
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

	private Double praiseIncome;
	private Double donateIncome;
	private Double postTotalIncome;
	
	private String postUuid;
	private Integer isNiceChoice;
	private Date niceChoiceAt;
	private Integer type;
	private Integer disStickTop;
	private Date disStickUpdateTime;
	
	public String getPostUuid() {
		return postUuid;
	}

	public void setPostUuid(String postUuid) {
		this.postUuid = postUuid;
	}

	public Integer getIsNiceChoice() {
		return isNiceChoice;
	}

	public void setIsNiceChoice(Integer isNiceChoice) {
		this.isNiceChoice = isNiceChoice;
	}

	public Date getNiceChoiceAt() {
		return niceChoiceAt;
	}

	public void setNiceChoiceAt(Date niceChoiceAt) {
		this.niceChoiceAt = niceChoiceAt;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getDisStickTop() {
		return disStickTop;
	}

	public void setDisStickTop(Integer disStickTop) {
		this.disStickTop = disStickTop;
	}

	public Date getDisStickUpdateTime() {
		return disStickUpdateTime;
	}

	public void setDisStickUpdateTime(Date disStickUpdateTime) {
		this.disStickUpdateTime = disStickUpdateTime;
	}

	public BigDecimal getEvaTotalScore() {
		return evaTotalScore;
	}

	public void setEvaTotalScore(BigDecimal evaTotalScore) {
		this.evaTotalScore = evaTotalScore;
	}

	public java.lang.String getProfessionalEvaDetail() {
		return professionalEvaDetail;
	}

	public void setProfessionalEvaDetail(java.lang.String professionalEvaDetail) {
		this.professionalEvaDetail = professionalEvaDetail;
	}

	public java.lang.String getEvauationContent() {
		return evauationContent;
	}

	public void setEvauationContent(java.lang.String evauationContent) {
		this.evauationContent = evauationContent;
	}

	public java.lang.String getEvaluationTags() {
		return evaluationTags;
	}

	public void setEvaluationTags(java.lang.String evaluationTags) {
		this.evaluationTags = evaluationTags;
	}

	public java.lang.Integer getDiscussId() {
		return discussId;
	}

	public void setDiscussId(java.lang.Integer discussId) {
		this.discussId = discussId;
	}

	public java.lang.String getDisscussContents() {
		return disscussContents;
	}

	public void setDisscussContents(java.lang.String disscussContents) {
		this.disscussContents = disscussContents;
	}

	public java.lang.String getTagInfos() {
		return tagInfos;
	}

	public void setTagInfos(java.lang.String tagInfos) {
		this.tagInfos = tagInfos;
	}

	public java.lang.Integer getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(java.lang.Integer followStatus) {
		this.followStatus = followStatus;
	}

	public void setPostId(java.lang.Integer value) {
		this.postId = value;
	}

	public java.lang.Integer getPostId() {
		return this.postId;
	}

	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}

	public java.lang.Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectIcon(java.lang.String value) {
		this.projectIcon = value;
	}

	public java.lang.String getProjectIcon() {
		return this.projectIcon;
	}

	public void setProjectCode(java.lang.String value) {
		this.projectCode = value;
	}

	public java.lang.String getProjectCode() {
		return this.projectCode;
	}

	public void setProjectEnglishName(java.lang.String value) {
		this.projectEnglishName = value;
	}

	public java.lang.String getProjectEnglishName() {
		return this.projectEnglishName;
	}

	public void setPostTitle(java.lang.String value) {
		this.postTitle = value;
	}

	public java.lang.String getPostTitle() {
		return this.postTitle;
	}

	public java.lang.Integer getPostType() {
		return postType;
	}

	public void setPostType(java.lang.Integer postType) {
		this.postType = postType;
	}

	public void setPostShortDesc(java.lang.String value) {
		this.postShortDesc = value;
	}

	public java.lang.String getPostShortDesc() {
		return this.postShortDesc;
	}

	public List<PostFile> getPostSmallImagesList() {
		return postSmallImagesList;
	}

	public void setPostSmallImagesList(List<PostFile> postSmallImagesList) {
		this.postSmallImagesList = postSmallImagesList;
	}

	public java.lang.String getPostSmallImages() {
		return postSmallImages;
	}

	public void setPostSmallImages(java.lang.String postSmallImages) {
		this.postSmallImages = postSmallImages;
	}

	public void setCommentsNum(java.lang.Integer value) {
		this.commentsNum = value;
	}

	public java.lang.Integer getCommentsNum() {
		return this.commentsNum;
	}

	public void setPraiseNum(java.lang.Integer value) {
		this.praiseNum = value;
	}

	public java.lang.Integer getPraiseNum() {
		return this.praiseNum;
	}

	public void setPageviewNum(java.lang.Integer value) {
		this.pageviewNum = value;
	}

	public java.lang.Integer getPageviewNum() {
		return this.pageviewNum;
	}

	public void setDonateNum(java.lang.Integer value) {
		this.donateNum = value;
	}

	public java.lang.Integer getDonateNum() {
		return this.donateNum;
	}

	public void setCollectNum(java.lang.Integer value) {
		this.collectNum = value;
	}

	public java.lang.Integer getCollectNum() {
		return this.collectNum;
	}

	public void setCreateUserId(java.lang.Integer value) {
		this.createUserId = value;
	}

	public java.lang.Integer getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserIcon(java.lang.String value) {
		this.createUserIcon = value;
	}

	public java.lang.String getCreateUserIcon() {
		return this.createUserIcon;
	}

	public void setCreateUserSignature(java.lang.String value) {
		this.createUserSignature = value;
	}

	public java.lang.String getCreateUserSignature() {
		return this.createUserSignature;
	}

	public void setCreateUserName(java.lang.String value) {
		this.createUserName = value;
	}

	public java.lang.String getCreateUserName() {
		return this.createUserName;
	}

	public void setcreateTime(java.util.Date value) {
		this.createTimeStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.createTime = value;
	}

	public java.util.Date getcreateTime() {
		return this.createTime;
	}

	public java.lang.String getcreateTimeStr() {
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

	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}

	public java.lang.Integer getStatus() {
		return this.status;
	}

	public java.lang.String getProjectChineseName() {
		return projectChineseName;
	}

	public void setProjectChineseName(java.lang.String projectChineseName) {
		this.projectChineseName = projectChineseName;
	}

	public java.lang.String getProjectSignature() {
		return projectSignature;
	}

	public void setProjectSignature(java.lang.String projectSignature) {
		this.projectSignature = projectSignature;
	}

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

	public java.lang.String getActionDesc() {
		return actionDesc;
	}

	public void setActionDesc(java.lang.String actionDesc) {
		this.actionDesc = actionDesc;
	}

	public java.lang.Integer getActionType() {
		return actionType;
	}

	public void setActionType(java.lang.Integer actionType) {
		this.actionType = actionType;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Double getPraiseIncome() {
		return praiseIncome;
	}

	public void setPraiseIncome(Double praiseIncome) {
		this.praiseIncome = praiseIncome;
	}

	public Double getDonateIncome() {
		return donateIncome;
	}

	public void setDonateIncome(Double donateIncome) {
		this.donateIncome = donateIncome;
	}

	public Double getPostTotalIncome() {
		return postTotalIncome;
	}

	public void setPostTotalIncome(Double postTotalIncome) {
		this.postTotalIncome = postTotalIncome;
	}

}
