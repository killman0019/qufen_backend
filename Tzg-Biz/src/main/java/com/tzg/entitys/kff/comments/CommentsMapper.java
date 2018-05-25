package com.tzg.entitys.kff.comments;

import com.tzg.common.base.BaseMapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CommentsMapper extends BaseMapper<Comments, java.lang.Integer> {

	List<Comments> findAllCommentsByWhere(Map<String, Object> map);

	/**
	 * 根据点赞人数进行排序 获赞人数最后的放在首位
	 */
	List<Comments> findPageCountOrderBy(Map<String, Object> queryData);

	List<Comments> findBidPraiseNum(Integer postId);

	Integer findCommentsSum();

	List<Comments> findFlootOrderById(Integer postId);

}
