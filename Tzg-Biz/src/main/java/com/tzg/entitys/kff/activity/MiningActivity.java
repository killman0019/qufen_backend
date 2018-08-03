package com.tzg.entitys.kff.activity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MiningActivity implements Serializable{
    /** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/ 
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Date createdAt;
    private String createDt;

    private Date updatedAt;

    private String title;

    private Integer projectId;
    
    private String projectCode;

    private String tokenName;

    private Integer tokenCount;
    
    private BigDecimal tokenEveryCount;

    private BigDecimal tokenCash;

    private Integer tokenNum;
    
    private Integer tokenSurplusNum;

    private String activityRemark;

    private String beginDt;

    private String endDt;

    private String activityStep;

    private Integer type;

    private Integer articleId;

    private Integer status;
    private String statusMsg;
    
    private BigDecimal tokenUnclaimed;
    
    //判断用户是否关注了该项目:0-未关注，1-已关注
    private Integer followType;
    //判断用户是否在活动时间内点评过该项目：0-未点评，1-已点评
    private Integer commentType;
    //判断在活动时间内用户是否分享过该项目：0-未分享，1-已分享
    private Integer shareType;
    //判断在活动时间内用户是否领取过该项目奖励：0-未领取，1-已领取
    private Integer receiveType;
    
    //项目图标
    private String projectIcon;
    //项目中文名称
    private String projectChineseName;
    
    private Integer version;
    
    public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getProjectIcon() {
		return projectIcon;
	}

	public void setProjectIcon(String projectIcon) {
		this.projectIcon = projectIcon;
	}

	public String getProjectChineseName() {
		return projectChineseName;
	}

	public void setProjectChineseName(String projectChineseName) {
		this.projectChineseName = projectChineseName;
	}

	public Integer getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}

	public Integer getShareType() {
		return shareType;
	}

	public void setShareType(Integer shareType) {
		this.shareType = shareType;
	}

	public Integer getCommentType() {
		return commentType;
	}

	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}

	public Integer getFollowType() {
		return followType;
	}

	public void setFollowType(Integer followType) {
		this.followType = followType;
	}

	public BigDecimal getTokenEveryCount() {
		return tokenEveryCount;
	}

	public void setTokenEveryCount(BigDecimal tokenEveryCount) {
		this.tokenEveryCount = tokenEveryCount;
	}

	public Integer getTokenSurplusNum() {
		return tokenSurplusNum;
	}

	public void setTokenSurplusNum(Integer tokenSurplusNum) {
		this.tokenSurplusNum = tokenSurplusNum;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public BigDecimal getTokenUnclaimed() {
		return tokenUnclaimed;
	}

	public void setTokenUnclaimed(BigDecimal tokenUnclaimed) {
		this.tokenUnclaimed = tokenUnclaimed;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName == null ? null : tokenName.trim();
    }

    public Integer getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(Integer tokenCount) {
        this.tokenCount = tokenCount;
    }

    public BigDecimal getTokenCash() {
        return tokenCash;
    }

    public void setTokenCash(BigDecimal tokenCash) {
        this.tokenCash = tokenCash;
    }

    public Integer getTokenNum() {
        return tokenNum;
    }

    public void setTokenNum(Integer tokenNum) {
        this.tokenNum = tokenNum;
    }

    public String getActivityRemark() {
        return activityRemark;
    }

    public void setActivityRemark(String activityRemark) {
        this.activityRemark = activityRemark == null ? null : activityRemark.trim();
    }

    public String getBeginDt() {
        return beginDt;
    }

    public void setBeginDt(String beginDt) {
        this.beginDt = beginDt == null ? null : beginDt.trim();
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt == null ? null : endDt.trim();
    }

    public String getActivityStep() {
        return activityStep;
    }

    public void setActivityStep(String activityStep) {
        this.activityStep = activityStep;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}