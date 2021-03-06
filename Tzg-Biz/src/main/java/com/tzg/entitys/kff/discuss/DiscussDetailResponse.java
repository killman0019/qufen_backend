package com.tzg.entitys.kff.discuss;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.commendation.Commendation;
import com.tzg.entitys.kff.comments.Comments;

public class DiscussDetailResponse implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 195656635634L;
	/**
	 * discussId
	 */
	private java.lang.Integer discussId;
	/**
	 * postId
	 */
	private java.lang.Integer postId;
	/**
	 * 讨论内容
	 */
	private java.lang.String disscussContents;

	private java.lang.String postUuid;
	/**
	 * 标签ID，名称的json串
	 */
	private java.lang.String tagInfos;

	// 0 未关注；1-已关注；2-不显示关注按钮
	private java.lang.Integer followStatus = 2;

	/**
	 * 项目ID
	 */
	private java.lang.Integer projectId;
	/**
	 * 项目icon
	 */
	private java.lang.String projectIcon;
	/**
	 * 项目代码
	 */
	private java.lang.String projectCode;
	/**
	 * 项目英文名称
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
	private java.lang.Integer postType = 3;
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
	 * 点赞状态：0-未点赞；1-已点赞
	 */
	private java.lang.Integer praiseStatus = 0;
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
	 * 收藏状态：0-未收藏；1-已收藏
	 */
	private java.lang.Integer collectStatus = 2;
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

	private Integer userType;

	private Double praiseIncome;
	private Double donateIncome;
	private Double postTotalIncome;

	/**
	 * 总捐赠金额
	 */
	private java.math.BigDecimal commendationNum = BigDecimal.ZERO;
	
	private Integer rewardActivityId;
	private BigDecimal rewardMoney;
	//每个用户回答悬赏能中奖的金额
	private BigDecimal rewardMoneyToOne;

	public BigDecimal getRewardMoneyToOne() {
		return rewardMoneyToOne;
	}

	public void setRewardMoneyToOne(BigDecimal rewardMoneyToOne) {
		this.rewardMoneyToOne = rewardMoneyToOne;
	}
	public Integer getRewardActivityId() {
		return rewardActivityId;
	}

	public void setRewardActivityId(Integer rewardActivityId) {
		this.rewardActivityId = rewardActivityId;
	}

	public BigDecimal getRewardMoney() {
		return rewardMoney;
	}

	public void setRewardMoney(BigDecimal rewardMoney) {
		this.rewardMoney = rewardMoney;
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

	public java.math.BigDecimal getCommendationNum() {
		return commendationNum;
	}

	public void setCommendationNum(java.math.BigDecimal commendationNum) {
		this.commendationNum = commendationNum;
	}

	public List<Commendation> getCommendationList() {
		return commendationList;
	}

	public void setCommendationList(List<Commendation> commendationList) {
		this.commendationList = commendationList;
	}

	// 捐赠用户列表，最多8个
	private List<Commendation> commendationList;

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

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

	// 热门评论
	private List<Comments> hotComments;

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

	public void setPostShortDesc(java.lang.String value) {
		this.postShortDesc = value;
	}

	public java.lang.String getPostShortDesc() {
		return this.postShortDesc;
	}

	public void setPostSmallImages(java.lang.String value) {
		this.postSmallImages = value;
	}

	public java.lang.String getPostSmallImages() {
		return this.postSmallImages;
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

	public java.lang.Integer getFollowStatus() {
		return followStatus;
	}

	public void setFollowStatus(java.lang.Integer followStatus) {
		this.followStatus = followStatus;
	}

	public java.lang.Integer getPostType() {
		return postType;
	}

	public void setPostType(java.lang.Integer postType) {
		this.postType = postType;
	}

	public void setDiscussId(java.lang.Integer value) {
		this.discussId = value;
	}

	public java.lang.Integer getDiscussId() {
		return this.discussId;
	}

	public void setPostId(java.lang.Integer value) {
		this.postId = value;
	}

	public java.lang.Integer getPostId() {
		return this.postId;
	}

	public void setDisscussContents(java.lang.String value) {
		this.disscussContents = value;
	}

	public java.lang.String getDisscussContents() {
		return this.disscussContents;
	}

	public java.lang.String getPostUuid() {
		return postUuid;
	}

	public void setPostUuid(java.lang.String postUuid) {
		this.postUuid = postUuid;
	}

	public void setTagInfos(java.lang.String value) {
		this.tagInfos = value;
	}

	public java.lang.String getTagInfos() {
		return this.tagInfos;
	}

	public java.lang.String getProjectChineseName() {
		return projectChineseName;
	}

	public void setProjectChineseName(java.lang.String projectChineseName) {
		this.projectChineseName = projectChineseName;
	}

	public List<Comments> getHotComments() {
		return hotComments;
	}

	public void setHotComments(List<Comments> hotComments) {
		this.hotComments = hotComments;
	}

}
