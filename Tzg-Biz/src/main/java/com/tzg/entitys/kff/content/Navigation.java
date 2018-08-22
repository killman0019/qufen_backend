package com.tzg.entitys.kff.content;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Navigation implements Serializable {
	
    /** 
	* @Fields serialVersionUID : TODO<用一句话描述这个变量表示什么> 
	*/ 
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Date createdAt;
    private String createDt;

    private Date updatedAt;

    private String navigationTitle;

    private String imgPath;

    private Integer sort;

    private Integer isShow;

    private String remark;
    
    private List<NavigationProject> navigationProjects;
    
    public List<NavigationProject> getNavigationProjects() {
		return navigationProjects;
	}

	public void setNavigationProjects(List<NavigationProject> navigationProjects) {
		this.navigationProjects = navigationProjects;
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

    public String getNavigationTitle() {
        return navigationTitle;
    }

    public void setNavigationTitle(String navigationTitle) {
        this.navigationTitle = navigationTitle == null ? null : navigationTitle.trim();
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}