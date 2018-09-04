package com.tzg.entitys.kff.comments;

import com.tzg.common.base.BaseMapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CommentsMapper extends BaseMapper<Comments, java.lang.Integer> {

	List<Comments> findAllCommentsByWhere(Map<String, Object> map);

	/**
	 */
	List<Comments> findPageCountOrderBy(Map<String, Object> queryData);

	void increasePraiseNum(Integer commentsId);

	List<Comments> findBidPraiseNum(Integer postId);

	void decreasePraiseNum(Integer commentsId);

	Integer findCommentsSum();

	void updateUserInfo(Map<String, Object> map);

	List<Comments> findFlootOrderById(Integer postId);

	Comments findByPostId(Integer commentsId);

	Comments selectIdByCommentUUID(String commnetUUID);

	Integer findParentCommentsSum(Integer postId);

	List<Comments> findByMap(Map<String, Object> commMap);

	
}
