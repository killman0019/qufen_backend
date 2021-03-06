package com.tzg.entitys.kff.qfindex;

import java.io.Serializable;
import java.math.BigDecimal;

public class QfindexResponse implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/
	private static final long serialVersionUID = -4513549458456299476L;
	/**
	 * 点赞次数
	 */
	private Integer praiseDegr;
	/**
	 * 获得点赞奖励数
	 */
	private Double praiseAward;
	/**
	 * 点赞的总次数
	 */
	private Integer praiseSumDegr;
	/**
	 * 点赞的领取状态
	 */
	private Integer praiseReceStatus = 0;
	/**
	 * 获得点赞总奖励
	 */
	private Double praiseAwardSum;
	/**
	 * 
	 * 分享次数
	 */
	private Integer sharePostDegr;
	/**
	 * 获得分享奖励
	 */
	private Double sharePostAward;
	/**
	 * 分享的总次数
	 */
	private Integer sharePostSumDegr;
	/**
	 * 分享的领取状态
	 */
	private Integer sharePostReceStatus = 0;
	/**
	 * 获得分享总奖励
	 */
	private Double sharePostAwardSum;
	/**
	 * 评论次数
	 */
	private Integer commentDegr;
	/**
	 * 获得评论的奖励
	 */
	private Double commentAward;
	/**
	 * 首次评论获得奖励
	 */
	private Double commentFirstAward;

	/**
	 * 评论总次数
	 */
	private Integer commentSumDegr;
	/**
	 * 评论的领取状态
	 */
	private Integer commentReceStatus = 0;
	/**
	 * 获得的评论总奖励
	 */
	private Double commentAwardSum;
	/**
	 * 评测次数
	 */
	private Integer evaDegr;
	/**
	 * 获得发表评测的奖励
	 */
	private Double evaAward;
	/**
	 * 评测的总次数
	 */
	private Integer evaAwardSumDegr;
	/**
	 * 获得发布评测的总奖励
	 */
	private Double evaAwardSum;

	/**
	 * 评测的领取奖励
	 */
	private Integer evaReceStatus = 0;

	/**
	 * 阅读次数
	 */
	private Integer readingDegr;
	/**
	 * 获得阅读评测奖励
	 */
	private Double readingAward;
	/**
	 * 阅读总次数
	 */
	private Integer readingSumDegr;
	/**
	 * 阅读奖励领取状态
	 */
	private Integer readingReceStatus = 0;
	/**
	 * 获得阅读总奖励
	 */
	private Double readingAwardSum;
	/**
	 * 区分指数
	 */
	private Integer statusHierarchyType;
	/**
	 * 登录奖励
	 */
	private BigDecimal loginAward;
	/**
	 * 邀请奖励
	 */
	private BigDecimal invaAward;
	/**
	 * 每邀请一个好友的奖励 后台配置
	 */
	private BigDecimal invaEachAward;
	/**
	 * 今天获得奖励
	 */
	private Double todayAward;

	public Double getCommentFirstAward() {
		return commentFirstAward;
	}

	public void setCommentFirstAward(Double commentFirstAward) {
		this.commentFirstAward = commentFirstAward;
	}

	public Double getPraiseAwardSum() {
		return praiseAwardSum;
	}

	public void setPraiseAwardSum(Double praiseAwardSum) {
		this.praiseAwardSum = praiseAwardSum;
	}

	public Double getSharePostAwardSum() {
		return sharePostAwardSum;
	}

	public void setSharePostAwardSum(Double sharePostAwardSum) {
		this.sharePostAwardSum = sharePostAwardSum;
	}

	public Double getCommentAwardSum() {
		return commentAwardSum;
	}

	public void setCommentAwardSum(Double commentAwardSum) {
		this.commentAwardSum = commentAwardSum;
	}

	public Double getEvaAwardSum() {
		return evaAwardSum;
	}

	public void setEvaAwardSum(Double evaAwardSum) {
		this.evaAwardSum = evaAwardSum;
	}

	public Double getReadingAwardSum() {
		return readingAwardSum;
	}

	public void setReadingAwardSum(Double readingAwardSum) {
		this.readingAwardSum = readingAwardSum;
	}

	public Double getTodayAward() {
		return todayAward;
	}

	public void setTodayAward(Double todayAward) {
		this.todayAward = todayAward;
	}

	public BigDecimal getInvaEachAward() {
		return invaEachAward;
	}

	public void setInvaEachAward(BigDecimal invaEachAward) {
		this.invaEachAward = invaEachAward;
	}

	public Integer getPraiseReceStatus() {
		return praiseReceStatus;
	}

	public void setPraiseReceStatus(Integer praiseReceStatus) {
		this.praiseReceStatus = praiseReceStatus;
	}

	public Integer getSharePostReceStatus() {
		return sharePostReceStatus;
	}

	public void setSharePostReceStatus(Integer sharePostReceStatus) {
		this.sharePostReceStatus = sharePostReceStatus;
	}

	public Integer getCommentReceStatus() {
		return commentReceStatus;
	}

	public void setCommentReceStatus(Integer commentReceStatus) {
		this.commentReceStatus = commentReceStatus;
	}

	public Integer getEvaReceStatus() {
		return evaReceStatus;
	}

	public void setEvaReceStatus(Integer evaReceStatus) {
		this.evaReceStatus = evaReceStatus;
	}

	public Integer getReadingReceStatus() {
		return readingReceStatus;
	}

	public void setReadingReceStatus(Integer readingReceStatus) {
		this.readingReceStatus = readingReceStatus;
	}

	public Integer getPraiseSumDegr() {
		return praiseSumDegr;
	}

	public void setPraiseSumDegr(Integer praiseSumDegr) {
		this.praiseSumDegr = praiseSumDegr;
	}

	public Integer getSharePostSumDegr() {
		return sharePostSumDegr;
	}

	public void setSharePostSumDegr(Integer sharePostSumDegr) {
		this.sharePostSumDegr = sharePostSumDegr;
	}

	public Integer getCommentSumDegr() {
		return commentSumDegr;
	}

	public void setCommentSumDegr(Integer commentSumDegr) {
		this.commentSumDegr = commentSumDegr;
	}

	public Integer getEvaAwardSumDegr() {
		return evaAwardSumDegr;
	}

	public void setEvaAwardSumDegr(Integer evaAwardSumDegr) {
		this.evaAwardSumDegr = evaAwardSumDegr;
	}

	public Integer getReadingSumDegr() {
		return readingSumDegr;
	}

	public void setReadingSumDegr(Integer readingSumDegr) {
		this.readingSumDegr = readingSumDegr;
	}

	public Double getPraiseAward() {
		return praiseAward;
	}

	public void setPraiseAward(Double praiseAward) {
		this.praiseAward = praiseAward;
	}

	public Double getSharePostAward() {
		return sharePostAward;
	}

	public void setSharePostAward(Double sharePostAward) {
		this.sharePostAward = sharePostAward;
	}

	public Double getCommentAward() {
		return commentAward;
	}

	public void setCommentAward(Double commentAward) {
		this.commentAward = commentAward;
	}

	public Double getEvaAward() {
		return evaAward;
	}

	public void setEvaAward(Double evaAward) {
		this.evaAward = evaAward;
	}

	public Double getReadingAward() {
		return readingAward;
	}

	public void setReadingAward(Double readingAward) {
		this.readingAward = readingAward;
	}

	public BigDecimal getInvaAward() {
		return invaAward;
	}

	public void setInvaAward(BigDecimal invaAward) {
		this.invaAward = invaAward;
	}

	public BigDecimal getLoginAward() {
		return loginAward;
	}

	public void setLoginAward(BigDecimal loginAward) {
		this.loginAward = loginAward;
	}

	public Integer getStatusHierarchyType() {
		return statusHierarchyType;
	}

	public void setStatusHierarchyType(Integer statusHierarchyType) {
		this.statusHierarchyType = statusHierarchyType;
	}

	public Integer getPraiseDegr() {
		return praiseDegr;
	}

	public void setPraiseDegr(Integer praiseDegr) {
		this.praiseDegr = praiseDegr;
	}

	public Integer getSharePostDegr() {
		return sharePostDegr;
	}

	public void setSharePostDegr(Integer sharePostDegr) {
		this.sharePostDegr = sharePostDegr;
	}

	public Integer getCommentDegr() {
		return commentDegr;
	}

	public void setCommentDegr(Integer commentDegr) {
		this.commentDegr = commentDegr;
	}

	public Integer getEvaDegr() {
		return evaDegr;
	}

	public void setEvaDegr(Integer evaDegr) {
		this.evaDegr = evaDegr;
	}

	public Integer getReadingDegr() {
		return readingDegr;
	}

	public void setReadingDegr(Integer readingDegr) {
		this.readingDegr = readingDegr;
	}

}
