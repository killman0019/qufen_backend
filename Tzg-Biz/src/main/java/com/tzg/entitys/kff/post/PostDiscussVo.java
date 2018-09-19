package com.tzg.entitys.kff.post;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.tzg.common.utils.DateUtil;

public class PostDiscussVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5214212436521123046L;
	/**
	 * postId
	 */
	private java.lang.Integer postId;
	/**
	 * 项目ID
	 */
	private java.lang.Integer stickTop;

	/**
	 * 推荐操作时间
	 */
	private java.util.Date stickUpdateTime;
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

	private java.lang.String uuid;

	private Double praiseIncome;
	private Double donateIncome;
	private Double postTotalIncome;
	
	//爆料的id
	private Integer discussId;
	//爆料的内容
	private String disscussContents;
	//图片url等json信息；目前保存9张
	private String postUuid;
	//标签ID，名称的json串
	private String tagInfos;
	//是否精选：0-是，1-否
	private Integer isNiceChoice;
	//被评为精选的时间
	private Date niceChoiceAt;
	//精评类型：0-点赞，1-认证账号发布，2-人工精选,3-普通爆料(或普通帖子)
	private Integer type;
	//1置顶 0 不置顶
	private Integer disStickTop;
	//置顶时间
	private Date disStickUpdateTime;
	//悬赏的状态：0-进行中，1-已结束，2-已撤销
	private Integer state;
	/**
	 * 项目总评分：1-10精确到1位
	 */
	private BigDecimal totalScore = BigDecimal.ZERO;
	
	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getDiscussId() {
		return discussId;
	}

	public void setDiscussId(Integer discussId) {
		this.discussId = discussId;
	}

	public String getDisscussContents() {
		return disscussContents;
	}

	public void setDisscussContents(String disscussContents) {
		this.disscussContents = disscussContents;
	}

	public String getPostUuid() {
		return postUuid;
	}

	public void setPostUuid(String postUuid) {
		this.postUuid = postUuid;
	}

	public String getTagInfos() {
		return tagInfos;
	}

	public void setTagInfos(String tagInfos) {
		this.tagInfos = tagInfos;
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

	public java.lang.String getUuid() {
		return uuid;
	}

	public void setUuid(java.lang.String uuid) {
		this.uuid = uuid;
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

	public java.lang.Integer getStickTop() {
		return stickTop;
	}

	public void setStickTop(java.lang.Integer stickTop) {
		this.stickTop = stickTop;
	}

	public java.util.Date getStickUpdateTime() {
		return stickUpdateTime;
	}

	public void setStickUpdateTime(java.util.Date stickUpdateTime) {
		this.stickUpdateTime = stickUpdateTime;
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
