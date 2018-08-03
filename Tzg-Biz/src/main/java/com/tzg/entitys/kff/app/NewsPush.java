package com.tzg.entitys.kff.app;

import java.io.Serializable;
import java.util.Date;

public class NewsPush implements Serializable {
    /** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/ 
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Date createdAt;
    private String createDt;

    private Date updatedAt;

    private String title;

    private String content;

    private Short pushType;

    private String peopleRange;

    private Short isRelativePage;

    private Short linkedType;
    private String linkedMsg;

    private Integer articleId;

    private Short launchType;

    private String launchTime;
    
    private String author;
    
    private Integer executeStatus;
    
	public Integer getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(Integer executeStatus) {
		this.executeStatus = executeStatus;
	}

	public String getLinkedMsg() {
		return linkedMsg;
	}

	public void setLinkedMsg(String linkedMsg) {
		this.linkedMsg = linkedMsg;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Short getPushType() {
        return pushType;
    }

    public void setPushType(Short pushType) {
        this.pushType = pushType;
    }

    public String getPeopleRange() {
        return peopleRange;
    }

    public void setPeopleRange(String peopleRange) {
        this.peopleRange = peopleRange == null ? null : peopleRange.trim();
    }

    public Short getIsRelativePage() {
        return isRelativePage;
    }

    public void setIsRelativePage(Short isRelativePage) {
        this.isRelativePage = isRelativePage;
    }

    public Short getLinkedType() {
        return linkedType;
    }

    public void setLinkedType(Short linkedType) {
        this.linkedType = linkedType;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Short getLaunchType() {
        return launchType;
    }

    public void setLaunchType(Short launchType) {
        this.launchType = launchType;
    }

    public String getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(String launchTime) {
        this.launchTime = launchTime == null ? null : launchTime.trim();
    }
}