package com.tzg.entitys.kff.projectManage;

import java.io.Serializable;
import java.util.List;

import com.tzg.common.page.PageResult;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.ProjectResponse;

public class ProjectManageResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7529850304884903984L;
	/**
	 * tab栏下的项目列表
	 */
	private PageResult<ProjectResponse> ProjectPespones;
	/**
	 * tab栏的名称
	 */
	private String tabTitle;
	/**
	 * tab的排列序号
	 */
	private Integer tabOrderNumber;

	private List<ProjectResponse> ProjectPesponesList;

	public List<ProjectResponse> getProjectPesponesList() {
		return ProjectPesponesList;
	}

	public void setProjectPesponesList(List<ProjectResponse> projectPesponesList) {
		ProjectPesponesList = projectPesponesList;
	}

	public PageResult<ProjectResponse> getProjectPespones() {
		return ProjectPespones;
	}

	public void setProjectPespones(PageResult<ProjectResponse> projectPespones) {
		ProjectPespones = projectPespones;
	}

	public String getTabTitle() {
		return tabTitle;
	}

	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}

	public Integer getTabOrderNumber() {
		return tabOrderNumber;
	}

	public void setTabOrderNumber(Integer tabOrderNumber) {
		this.tabOrderNumber = tabOrderNumber;
	}

}
