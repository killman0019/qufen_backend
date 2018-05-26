package com.tzg.common.service.index.news;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.news.News;
import com.tzg.entitys.news.NewsMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
public class NewsService {

	@Autowired
	private NewsMapper newsMapper;

	public News findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return newsMapper.findById(id);
	}

	/**
	 * web首页List
	 * @return
	 */
	public List<News> findIndexList(){
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("startRecord", 0);
		query.addQueryData("endRecord", 6);
		query.addQueryData("istate", "2");
		query.addQueryData("itype", "1");
		query.addQueryData("ihomedisplay", "2");
		List<News> list = newsMapper.findPage(query.getQueryData());
		return list;
	}
	
	public PageResult<News> findPage(PaginationQuery query) throws Exception {
		PageResult<News> result = null;
		try {
			Integer count = newsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", startRecord);
				query.addQueryData("endRecord",
						query.getRowsPerPage());
				List<News> list = newsMapper.findPage(query.getQueryData());
				result = new PageResult<News>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


}
