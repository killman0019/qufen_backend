package com.tzg.entitys.dtags;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface DtagsMapper extends BaseMapper<Dtags, java.lang.Integer> {

	void activeById(Integer id);

	Dtags findByTagName(String tagName);	

}
