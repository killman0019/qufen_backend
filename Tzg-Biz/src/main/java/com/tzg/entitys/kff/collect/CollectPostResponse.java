package com.tzg.entitys.kff.collect;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.post.PostFile;

public class CollectPostResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8824054290447132368L;
	// 0 未关注；1-已关注；2-不显示关注按钮
	private java.lang.Integer followStatus = 0;

	/**
	 * collectId
	 */
	private java.lang.Integer collectId;
	/**
	 * collectUserId
	 */
	private java.lang.Integer collectUserId;
	/**
	 * projectId
	 */
	private java.lang.Integer projectId;
	/**
	 * postId
	 */
	private java.lang.Integer postId;
	/**
	 * 帖子类型：1-评测；2-讨论；3-文章
	 */
	private java.lang.Integer postType;
	/**
	 * 状态:0-取消收藏；1-收藏
	 */
	private java.lang.Integer status;
	/**
	 * 用来标记帖子发表时间
	 */
	private java.util.Date createTime;
	private java.lang.String createTimeStr;
	/**
	 * updateTime
	 */
	private java.util.Date updateTime;
	private java.lang.String updateTimeStr;

	// post表来源
	// 帖子标题
	java.lang.String postTitle;
	/**
	 * 帖子标题剪短描述
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
	private Integer userType;
	// 评测总分
	private java.math.BigDecimal totalScore = BigDecimal.ZERO;
	// project表来源
	/**
	 * 项目图标
	 */
	private java.lang.String projectIcon;

	/**
	 * 代币名称
	 */
	private java.lang.String projectCode;
	/**
	 * 代币英文名称
	 */
	private java.lang.String projectEnglishName;
	/**
	 * projectChineseName
	 */
	private java.lang.String projectChineseName;
	/**
	 * 项目标题
	 */
	private java.lang.String projectSignature;

	private Integer createUserId;
	//每个用户回答悬赏能中奖的金额
	private BigDecimal rewardMoneyToOne;
	//悬赏的PostId
	private Integer postIdToReward;
	//悬赏金额
    private BigDecimal rewardMoney;

	public BigDecimal getRewardMoneyToOne() {
		return rewardMoneyToOne;
	}

	public void setRewardMoneyToOne(BigDecimal rewardMoneyToOne) {
		this.rewardMoneyToOne = rewardMoneyToOne;
	}

	public Integer getPostIdToReward() {
		return postIdToReward;
	}

	public void setPostIdToReward(Integer postIdToReward) {
		this.postIdToReward = postIdToReward;
	}

	public BigDecimal getRewardMoney() {
		return rewardMoney;
	}

	public void setRewardMoney(BigDecimal rewardMoney) {
		this.rewardMoney = rewardMoney;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	private java.math.BigDecimal evaTotalScore = BigDecimal.ZERO;

	public java.math.BigDecimal getEvaTotalScore() {
		return evaTotalScore;
	}

	public void setEvaTotalScore(java.math.BigDecimal evaTotalScore) {
		this.evaTotalScore = evaTotalScore;
	}

	public java.lang.String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(java.lang.String postTitle) {
		this.postTitle = postTitle;
	}

	public java.lang.String getPostShortDesc() {
		return postShortDesc;
	}

	public void setPostShortDesc(java.lang.String postShortDesc) {
		this.postShortDesc = postShortDesc;
	}

	public java.lang.Integer getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(java.lang.Integer followStatus) {
		this.followStatus = followStatus;
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

	public java.math.BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(java.math.BigDecimal totalScore) {
		this.totalScore = totalScore;
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

	public void setCollectId(java.lang.Integer value) {
		this.collectId = value;
	}

	public java.lang.Integer getCollectId() {
		return this.collectId;
	}

	public void setCollectUserId(java.lang.Integer value) {
		this.collectUserId = value;
	}

	public java.lang.Integer getCollectUserId() {
		return this.collectUserId;
	}

	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}

	public java.lang.Integer getProjectId() {
		return this.projectId;
	}

	public void setPostId(java.lang.Integer value) {
		this.postId = value;
	}

	public java.lang.Integer getPostId() {
		return this.postId;
	}

	public java.lang.Integer getPostType() {
		return postType;
	}

	public void setPostType(java.lang.Integer postType) {
		this.postType = postType;
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

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
