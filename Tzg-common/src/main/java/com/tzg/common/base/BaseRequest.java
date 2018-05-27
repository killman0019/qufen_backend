/**
 * 
 */
package com.tzg.common.base;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1442162145629041617L;

	//登录用户的token
	private String token;
     //分页当前页码 默认1
	private Integer pageIndex = 1;
	//分页每页记录条数 默认10
	private Integer pageSize = 10;
	//根据token解析出来的userID
	private Integer userId;
	
	private Integer projectId;
	private Integer postId;
	private String sortField;
	
	
	
	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
