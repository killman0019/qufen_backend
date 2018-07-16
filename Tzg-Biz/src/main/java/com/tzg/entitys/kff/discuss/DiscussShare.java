package com.tzg.entitys.kff.discuss;

import java.io.Serializable;
import java.util.List;

import com.tzg.common.page.PageResult;
import com.tzg.entitys.kff.commendation.Commendation;
import com.tzg.entitys.kff.comments.CommentShareFloot;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.post.Post;

public class DiscussShare implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2948067706016260207L;
	private String tagInfo;
	private List<Comments> commentsehot;
	private List<Comments> commentseNew;
	private List<Comments> commentseZanDuo;
	private CommentShareFloot commentseZanDuoOnly;
	private Integer commentseSum;
	private Integer cUsertype;
	private Discuss discuss;

	public Discuss getDiscuss() {
		return discuss;
	}

	public void setDiscuss(Discuss discuss) {
		this.discuss = discuss;
	}

	public Integer getcUsertype() {
		return cUsertype;
	}

	public void setcUsertype(Integer cUsertype) {
		this.cUsertype = cUsertype;
	}

	public Integer getCommentseSum() {
		return commentseSum;
	}

	public void setCommentseSum(Integer commentseSum) {
		this.commentseSum = commentseSum;
	}

	public CommentShareFloot getCommentseZanDuoOnly() {
		return commentseZanDuoOnly;
	}

	public void setCommentseZanDuoOnly(CommentShareFloot commentseZanDuoOnly) {
		this.commentseZanDuoOnly = commentseZanDuoOnly;
	}

	private Post post;
	private List<Commendation> donateUsers;

	public List<Commendation> getDonateUsers() {
		return donateUsers;
	}

	public void setDonateUsers(List<Commendation> donateUsers) {
		this.donateUsers = donateUsers;
	}

	public String getTagInfo() {
		return tagInfo;
	}

	public void setTagInfo(String tagInfo) {
		this.tagInfo = tagInfo;
	}

	public Post getPost() {
		return post;
	}

	public List<Comments> getCommentsehot() {
		return commentsehot;
	}

	public void setCommentsehot(List<Comments> commentsehot) {
		this.commentsehot = commentsehot;
	}

	public List<Comments> getCommentseNew() {
		return commentseNew;
	}

	public void setCommentseNew(List<Comments> commentseNew) {
		this.commentseNew = commentseNew;
	}

	public List<Comments> getCommentseZanDuo() {
		return commentseZanDuo;
	}

	public void setCommentseZanDuo(List<Comments> commentseZanDuo) {
		this.commentseZanDuo = commentseZanDuo;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
