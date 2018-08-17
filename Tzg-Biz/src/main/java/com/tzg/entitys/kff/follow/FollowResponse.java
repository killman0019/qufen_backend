package com.tzg.entitys.kff.follow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.post.PostFile;

public class FollowResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3779121295009805713L;
	/**
	 * followId
	 */
	private java.lang.Integer followId;
	/**
	 * followUserId
	 */
	private java.lang.Integer followUserId;
	/**
	 * 关注类型：1-关注项目;2-关注帖子；3-关注用户
	 */
	private java.lang.Integer followType;
	/**
	 * followerUserName
	 */
	private java.lang.String followerUserName;
	/**
	 * followedUserId
	 */
	private java.lang.Integer followedUserId;
	/**
	 * followedUserSignature
	 */
	private java.lang.String followedUserSignature;
	/**
	 * followedUserIcon
	 */
	private java.lang.String followedUserIcon;
	/**
	 * followedUserName
	 */
	private java.lang.String followedUserName;
	/**
	 * followedProjectId
	 */
	private java.lang.Integer followedProjectId;
	/**
	 * followedPostId
	 */
	private java.lang.Integer followedPostId;
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

	private Integer userType;

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
	private List<PostFile> postSmallImages;
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

	private Integer followerNum;

	private String userName;
	private Integer userId;

	private String userSignature;

	private String userIcon;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserSignature() {
		return userSignature;
	}

	public void setUserSignature(String userSignature) {
		this.userSignature = userSignature;
	}

	public String getUserIcon() {
		return userIcon;
	}

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public Integer getFollowerNum() {
		return followerNum;
	}

	public void setFollowerNum(Integer followerNum) {
		this.followerNum = followerNum;
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

	public List<PostFile> getPostSmallImages() {
		return postSmallImages;
	}

	public void setPostSmallImages(List<PostFile> postSmallImages) {
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

	public java.lang.Integer getFollowId() {
		return followId;
	}

	public void setFollowId(java.lang.Integer followId) {
		this.followId = followId;
	}

	public java.lang.Integer getFollowUserId() {
		return followUserId;
	}

	public void setFollowUserId(java.lang.Integer followUserId) {
		this.followUserId = followUserId;
	}

	public java.lang.Integer getFollowType() {
		return followType;
	}

	public void setFollowType(java.lang.Integer followType) {
		this.followType = followType;
	}

	public java.lang.String getFollowerUserName() {
		return followerUserName;
	}

	public void setFollowerUserName(java.lang.String followerUserName) {
		this.followerUserName = followerUserName;
	}

	public java.lang.Integer getFollowedUserId() {
		return followedUserId;
	}

	public void setFollowedUserId(java.lang.Integer followedUserId) {
		this.followedUserId = followedUserId;
	}

	public java.lang.String getFollowedUserSignature() {
		return followedUserSignature;
	}

	public void setFollowedUserSignature(java.lang.String followedUserSignature) {
		this.followedUserSignature = followedUserSignature;
	}

	public java.lang.String getFollowedUserIcon() {
		return followedUserIcon;
	}

	public void setFollowedUserIcon(java.lang.String followedUserIcon) {
		this.followedUserIcon = followedUserIcon;
	}

	public java.lang.String getFollowedUserName() {
		return followedUserName;
	}

	public void setFollowedUserName(java.lang.String followedUserName) {
		this.followedUserName = followedUserName;
	}

	public java.lang.Integer getFollowedProjectId() {
		return followedProjectId;
	}

	public void setFollowedProjectId(java.lang.Integer followedProjectId) {
		this.followedProjectId = followedProjectId;
	}

	public java.lang.Integer getFollowedPostId() {
		return followedPostId;
	}

	public void setFollowedPostId(java.lang.Integer followedPostId) {
		this.followedPostId = followedPostId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
