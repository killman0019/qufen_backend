package com.tzg.entitys.kff.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NewsFlash implements Serializable {
    /** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/ 
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Date createdAt;

    private Date updatedAt;

    private String bzjId;

    private String title;

    private String abstractc;

    private String host;

    private String publishTime;

    private BigDecimal rank;

    private String relevance;

    private String sentiment;

    private String site;

    private String tags;

    private String url;
    
    private String projectCode;
    
    private String articleEvents;

    private String content;

    private String html;
    
    private Integer rise;

    private Integer fall;
    
    
    public Integer getRise() {
		return rise;
	}

	public void setRise(Integer rise) {
		this.rise = rise;
	}

	public Integer getFall() {
		return fall;
	}

	public void setFall(Integer fall) {
		this.fall = fall;
	}

    public String getArticleEvents() {
        return articleEvents;
    }

    public void setArticleEvents(String articleEvents) {
        this.articleEvents = articleEvents == null ? null : articleEvents.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html == null ? null : html.trim();
    }
    
    public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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

    public String getBzjId() {
        return bzjId;
    }

    public void setBzjId(String bzjId) {
        this.bzjId = bzjId == null ? null : bzjId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAbstractc() {
        return abstractc;
    }

    public void setAbstractc(String abstractc) {
        this.abstractc = abstractc == null ? null : abstractc.trim();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime == null ? null : publishTime.trim();
    }

    public BigDecimal getRank() {
        return rank;
    }

    public void setRank(BigDecimal rank) {
        this.rank = rank;
    }

    public String getRelevance() {
        return relevance;
    }

    public void setRelevance(String relevance) {
        this.relevance = relevance == null ? null : relevance.trim();
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment == null ? null : sentiment.trim();
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site == null ? null : site.trim();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}