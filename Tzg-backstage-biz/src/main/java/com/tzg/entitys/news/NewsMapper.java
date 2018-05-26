package com.tzg.entitys.news;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface NewsMapper extends BaseMapper<News, java.lang.Integer> {

	public void updateIHomeSort(News news) throws Exception;

	

}
