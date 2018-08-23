package com.tzg.entitys.kff.tagstype;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface TagsTypeMapper extends BaseMapper<TagsType, java.lang.Integer> {

	List<TagsType> findByMap(Map<String, Object> map);

}