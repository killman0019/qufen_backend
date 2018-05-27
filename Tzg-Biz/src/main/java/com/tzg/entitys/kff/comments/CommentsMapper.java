package com.tzg.entitys.kff.comments;

import com.tzg.common.base.BaseMapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CommentsMapper extends BaseMapper<Comments, java.lang.Integer> {

	List<Comments> findAllCommentsByWhere(Map<String, Object> map);

	void increasePraiseNum(Integer commentsId);

	void decreasePraiseNum(Integer commentsId);

	void updateUserInfo(Map<String, Object> map);	

}
