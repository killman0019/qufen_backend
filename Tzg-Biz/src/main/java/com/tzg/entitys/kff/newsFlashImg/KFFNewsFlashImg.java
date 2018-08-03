package com.tzg.entitys.kff.newsFlashImg;

import java.io.Serializable;
import java.util.Date;

public class KFFNewsFlashImg implements Serializable {
	
	private static final long serialVersionUID = 2291506901418589407L;
    private Integer id;

    private Date createdAt;
    private String createDt;

    private Date updatedAt;

    private String title;

    private String imgPath;

    private String author;

    private Integer state;

    private Integer isCheckDetails;

    private Integer type;

    private Integer articleId;

    private Integer sort;
    
    private String outUrl;
    
    private Integer newsFlashWay;
    
    public Integer getNewsFlashWay() {
		return newsFlashWay;
	}

	public void setNewsFlashWay(Integer newsFlashWay) {
		this.newsFlashWay = newsFlashWay;
	}
    
    public String getOutUrl() {
		return outUrl;
	}

	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsCheckDetails() {
        return isCheckDetails;
    }

    public void setIsCheckDetails(Integer isCheckDetails) {
        this.isCheckDetails = isCheckDetails;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}