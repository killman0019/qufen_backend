package com.tzg.entitys.kff.article;

import java.io.Serializable;
import java.util.List;

import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.comments.Comments;
/**
 * 文章评论 列表页 返回对象
 * 包括 热门评论列表（2条） 和 最新评论列表（20条）
 * @author Administrator
 * @param <T>
 *
 */
public class ArticleCommentListResponse implements Serializable {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	private List<Comments> hotComments;
	
	private List<Comments> newestComments;

	

}

