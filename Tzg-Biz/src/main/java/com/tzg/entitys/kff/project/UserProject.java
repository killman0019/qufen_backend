package com.tzg.entitys.kff.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.dprojectType.DprojectType;

public class UserProject implements Serializable{
    /** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/ 
	private static final long serialVersionUID = 1L;

	private Integer projectId;

    private String projectIcon;

    private Integer state;

    private String projectCode;

    private String projectEnglishName;

    private String projectChineseName;

    private String projectSignature;

    private String websiteUrl;

    private Integer listed;

    private Date issueDate;
    private String issueDateStr;

    private Long issueNum;
    private String issueNumStr;

    private String whitepaperUrl;

    private String projectTypeName;

    private Integer projectTypeId;
    private List<DprojectType> projectTypeList;

    private Integer submitUserId;

    private String submitUserContactInfo;

    private Integer submitUserType;

    private Integer status;

    private Date createTime;
    private String createTimeStr;

    private Date publishTime;
    private String publishTimeStr;

    private Date updateTime;
    private String updateTimeStr;

    private BigDecimal totalScore;

    private Integer raterNum;

    private Integer followerNum;

    private Integer commentsNum;

    private Integer collectNum;

    private Integer totalraternum;

    private Integer cmcId;

    private String github;

    private String bsjCirculateData;

    private Integer projectUserId;

    private String projectMobile;

    private Integer projectUserType;
    
    private String projectDesc;

    private String submitReason;
    
    public java.lang.String getIssueDateStr() {
		return this.issueDateStr;
	}

	public void setIssueDateStr(String issueDateStr) {
		this.issueDateStr = issueDateStr;
	}
    
    public List<DprojectType> getProjectTypeList() {
		return projectTypeList;
	}

	public void setProjectTypeList(List<DprojectType> projectTypeList) {
		this.projectTypeList = projectTypeList;
	}

	public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc == null ? null : projectDesc.trim();
    }

    public String getSubmitReason() {
        return submitReason;
    }

    public void setSubmitReason(String submitReason) {
        this.submitReason = submitReason == null ? null : submitReason.trim();
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectIcon() {
        return projectIcon;
    }

    public void setProjectIcon(String projectIcon) {
        this.projectIcon = projectIcon == null ? null : projectIcon.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    public String getProjectEnglishName() {
        return projectEnglishName;
    }

    public void setProjectEnglishName(String projectEnglishName) {
        this.projectEnglishName = projectEnglishName == null ? null : projectEnglishName.trim();
    }

    public String getProjectChineseName() {
        return projectChineseName;
    }

    public void setProjectChineseName(String projectChineseName) {
        this.projectChineseName = projectChineseName == null ? null : projectChineseName.trim();
    }

    public String getProjectSignature() {
        return projectSignature;
    }

    public void setProjectSignature(String projectSignature) {
        this.projectSignature = projectSignature == null ? null : projectSignature.trim();
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl == null ? null : websiteUrl.trim();
    }

    public Integer getListed() {
        return listed;
    }

    public void setListed(Integer listed) {
        this.listed = listed;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
        this.issueDateStr = DateUtil.getDate(issueDate, "yyyy-MM-dd");
		this.issueDate = issueDate;
    }

    public Long getIssueNum() {
        return issueNum;
    }

    public void setIssueNum(Long issueNum) {
        this.issueNum = issueNum;
    }

    public String getWhitepaperUrl() {
        return whitepaperUrl;
    }

    public void setWhitepaperUrl(String whitepaperUrl) {
        this.whitepaperUrl = whitepaperUrl == null ? null : whitepaperUrl.trim();
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName == null ? null : projectTypeName.trim();
    }

    public Integer getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(Integer projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public Integer getSubmitUserId() {
        return submitUserId;
    }

    public void setSubmitUserId(Integer submitUserId) {
        this.submitUserId = submitUserId;
    }

    public String getSubmitUserContactInfo() {
        return submitUserContactInfo;
    }

    public void setSubmitUserContactInfo(String submitUserContactInfo) {
        this.submitUserContactInfo = submitUserContactInfo == null ? null : submitUserContactInfo.trim();
    }

    public Integer getSubmitUserType() {
        return submitUserType;
    }

    public void setSubmitUserType(Integer submitUserType) {
        this.submitUserType = submitUserType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTimeStr = DateUtil.getDate(createTime, "yyyy-MM-dd HH:mm:ss");
        this.createTime = createTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
    	this.publishTimeStr = DateUtil.getDate(publishTime, "yyyy-MM-dd HH:mm:ss");
        this.publishTime = publishTime;
    }

    public String getIssueNumStr() {
		return issueNumStr;
	}

	public void setIssueNumStr(String issueNumStr) {
		this.issueNumStr = issueNumStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getPublishTimeStr() {
		return publishTimeStr;
	}

	public void setPublishTimeStr(String publishTimeStr) {
		this.publishTimeStr = publishTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
    	this.updateTimeStr = DateUtil.getDate(updateTime, "yyyy-MM-dd HH:mm:ss");
        this.updateTime = updateTime;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getRaterNum() {
        return raterNum;
    }

    public void setRaterNum(Integer raterNum) {
        this.raterNum = raterNum;
    }

    public Integer getFollowerNum() {
        return followerNum;
    }

    public void setFollowerNum(Integer followerNum) {
        this.followerNum = followerNum;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public Integer getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Integer collectNum) {
        this.collectNum = collectNum;
    }

    public Integer getTotalraternum() {
        return totalraternum;
    }

    public void setTotalraternum(Integer totalraternum) {
        this.totalraternum = totalraternum;
    }

    public Integer getCmcId() {
        return cmcId;
    }

    public void setCmcId(Integer cmcId) {
        this.cmcId = cmcId;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github == null ? null : github.trim();
    }

    public String getBsjCirculateData() {
        return bsjCirculateData;
    }

    public void setBsjCirculateData(String bsjCirculateData) {
        this.bsjCirculateData = bsjCirculateData == null ? null : bsjCirculateData.trim();
    }

    public Integer getProjectUserId() {
        return projectUserId;
    }

    public void setProjectUserId(Integer projectUserId) {
        this.projectUserId = projectUserId;
    }

    public String getProjectMobile() {
        return projectMobile;
    }

    public void setProjectMobile(String projectMobile) {
        this.projectMobile = projectMobile == null ? null : projectMobile.trim();
    }

    public Integer getProjectUserType() {
        return projectUserType;
    }

    public void setProjectUserType(Integer projectUserType) {
        this.projectUserType = projectUserType;
    }
}