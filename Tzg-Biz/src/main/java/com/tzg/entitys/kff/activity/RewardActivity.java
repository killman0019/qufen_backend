package com.tzg.entitys.kff.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RewardActivity implements Serializable {
    /** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/ 
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Date createdAt;

    private Date updatedAt;

    private Integer postId;

    private Integer rewardDate;

    private Date beginTime;

    private Date endTime;

    private BigDecimal rewardMoney;

    private String tagInfos;

    private Integer answerCount;

    private Integer state;

    private Integer isActivity;

    private Integer isNiceChoice;

    private Date niceChoiceAt;

    private Integer type;

    private Integer disStickTop;

    private Date disStickUpdatetime;
    
    private String rewardContents;
    
    public String getRewardContents() {
		return rewardContents;
	}

	public void setRewardContents(String rewardContents) {
		this.rewardContents = rewardContents;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getRewardDate() {
        return rewardDate;
    }

    public void setRewardDate(Integer rewardDate) {
        this.rewardDate = rewardDate;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getRewardMoney() {
        return rewardMoney;
    }

    public void setRewardMoney(BigDecimal rewardMoney) {
        this.rewardMoney = rewardMoney;
    }

    public String getTagInfos() {
        return tagInfos;
    }

    public void setTagInfos(String tagInfos) {
        this.tagInfos = tagInfos == null ? null : tagInfos.trim();
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsActivity() {
        return isActivity;
    }

    public void setIsActivity(Integer isActivity) {
        this.isActivity = isActivity;
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

    public Date getDisStickUpdatetime() {
        return disStickUpdatetime;
    }

    public void setDisStickUpdatetime(Date disStickUpdatetime) {
        this.disStickUpdatetime = disStickUpdatetime;
    }
}