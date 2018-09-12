package com.tzg.entitys.kff.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RewardActivityVo implements Serializable {
    /** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/ 
	private static final long serialVersionUID = 1L;

	private Integer postId;
	private Integer projectId;
	private String projectCode;
	private String projectIcon;
	private String postTitle;
	private BigDecimal rewardMoney;
	private Date beginTime;
	private Date endTime;
	//悬赏的状态：0-进行中，1-已结束，2-已撤销
	private Integer state;
	
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getProjectIcon() {
		return projectIcon;
	}
	public void setProjectIcon(String projectIcon) {
		this.projectIcon = projectIcon;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public BigDecimal getRewardMoney() {
		return rewardMoney;
	}
	public void setRewardMoney(BigDecimal rewardMoney) {
		this.rewardMoney = rewardMoney;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}