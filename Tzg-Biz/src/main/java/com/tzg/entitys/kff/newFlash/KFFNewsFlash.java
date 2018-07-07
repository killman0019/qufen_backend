package com.tzg.entitys.kff.newFlash;

import java.io.Serializable;
import java.util.Date;

public class KFFNewsFlash implements Serializable {
	
	private static final long serialVersionUID = 2291506901418589407L;
    private Integer id;

    private Date createdAt;
    private String createDt;//用于前段展示

    private Date updatedAt;

    private String title;

    private String content;

    private Integer rise;

    private Integer fall;
    
    private Integer state;
    
    private String author;
    
    private Integer isCheckDetails;
    
    private Integer type;
    
    private Integer articleId;
    
    private Integer isProminent;
    
    public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
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

	public Integer getIsProminent() {
		return isProminent;
	}

	public void setIsProminent(Integer isProminent) {
		this.isProminent = isProminent;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
}