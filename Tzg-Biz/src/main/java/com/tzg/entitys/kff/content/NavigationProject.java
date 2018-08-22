package com.tzg.entitys.kff.content;

import java.io.Serializable;
import java.util.Date;

public class NavigationProject implements Serializable {
    /** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/ 
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Date createdAt;
    private String createDt;

    private Date updatedAt;

    private String navigationProjectTitle;

    private String projectImgPath;

    private Integer navigationId;

    private String navigationTitle;

    private Integer sort;

    private String webUrl;

    private String remark;
    
    private Integer isShow;

    public String getCreateDt() {
		return createDt;
	}

	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
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

    public String getNavigationProjectTitle() {
        return navigationProjectTitle;
    }

    public void setNavigationProjectTitle(String navigationProjectTitle) {
        this.navigationProjectTitle = navigationProjectTitle == null ? null : navigationProjectTitle.trim();
    }

    public String getProjectImgPath() {
        return projectImgPath;
    }

    public void setProjectImgPath(String projectImgPath) {
        this.projectImgPath = projectImgPath == null ? null : projectImgPath.trim();
    }

    public Integer getNavigationId() {
        return navigationId;
    }

    public void setNavigationId(Integer navigationId) {
        this.navigationId = navigationId;
    }

    public String getNavigationTitle() {
        return navigationTitle;
    }

    public void setNavigationTitle(String navigationTitle) {
        this.navigationTitle = navigationTitle == null ? null : navigationTitle.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl == null ? null : webUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}