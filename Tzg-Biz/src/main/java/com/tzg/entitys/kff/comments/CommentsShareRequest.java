package com.tzg.entitys.kff.comments;

import java.io.Serializable;
import java.util.List;

import com.tzg.entitys.kff.discuss.Discuss;
import com.tzg.entitys.kff.dtags.Dtags;

public class CommentsShareRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6655951470841938511L;

	private String projectCode;

	private String projectChineseName;

	private String projectIcon;

	private List<Comments> commentses;

	private Discuss discuss;

	private Dtags dtags;

	private List<Comments> newestComments;

	private List<Comments> hotComments;

	public List<Comments> getHotComments() {
		return hotComments;
	}

	public void setHotComments(List<Comments> hotComments) {
		this.hotComments = hotComments;
	}

	public List<Comments> getNewestComments() {
		return newestComments;
	}

	public void setNewestComments(List<Comments> newestComments) {
		this.newestComments = newestComments;
	}

	public Dtags getDtags() {
		return dtags;
	}

	public void setDtags(Dtags dtags) {
		this.dtags = dtags;
	}

	public Discuss getDiscuss() {
		return discuss;
	}

	public void setDiscuss(Discuss discuss) {
		this.discuss = discuss;
	}

	public List<Comments> getCommentses() {
		return commentses;
	}

	public void setCommentses(List<Comments> commentses) {
		this.commentses = commentses;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectChineseName() {
		return projectChineseName;
	}

	public void setProjectChineseName(String projectChineseName) {
		this.projectChineseName = projectChineseName;
	}

	public String getProjectIcon() {
		return projectIcon;
	}

	public void setProjectIcon(String projectIcon) {
		this.projectIcon = projectIcon;
	}

}
