package com.tzg.entitys.kff.commentlibrary;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface CommentLibraryMapper extends BaseMapper<CommentLibrary, java.lang.Integer> {

	List<CommentLibrary> findByMap(Map<String, Object> map);

}