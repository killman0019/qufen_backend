package com.tzg.entitys.kff.dtags;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface DtagsMapper extends BaseMapper<Dtags, java.lang.Integer> {

	List<Dtags> findAllTags();

	List<Dtags> findAllTagsName();

	List<DtagsReponse> getEvaTagsAndTagType(Map<String, Object> map);

	List<Dtags> findByMap(Map<String, Object> map);
}
