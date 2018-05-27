package com.tzg.entitys.kff.discuss;

import java.io.Serializable;
import java.util.List;

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
	private List<CommentShareFloot> commentsehot;
	private List<CommentShareFloot> commentseNew;
	private List<CommentShareFloot> commentseZanDuo;
	private CommentShareFloot commentseZanDuoOnly;
	private Integer commentseSum;

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

	public List<CommentShareFloot> getCommentsehot() {
		return commentsehot;
	}

	public void setCommentsehot(List<CommentShareFloot> commentsehot) {
		this.commentsehot = commentsehot;
	}

	public List<CommentShareFloot> getCommentseNew() {
		return commentseNew;
	}

	public void setCommentseNew(List<CommentShareFloot> commentseNew) {
		this.commentseNew = commentseNew;
	}

	public List<CommentShareFloot> getCommentseZanDuo() {
		return commentseZanDuo;
	}

	public void setCommentseZanDuo(List<CommentShareFloot> commentseZanDuo) {
		this.commentseZanDuo = commentseZanDuo;
	}

	public void setPost(Post post) {
		this.post = post;
	}

}
