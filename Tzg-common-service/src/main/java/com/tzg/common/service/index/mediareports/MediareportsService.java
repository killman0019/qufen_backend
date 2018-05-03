package com.tzg.common.service.index.mediareports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.mediareports.Mediareports;
import com.tzg.entitys.mediareports.MediareportsMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
public class MediareportsService {

	@Autowired
	private MediareportsMapper mediareportsMapper;	
	   
    public Mediareports findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return mediareportsMapper.findById(id);
    }
	
	/**
	 * web首页List
	 * @return
	 */
	public List<Mediareports> findIndexList(){
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("startRecord", 0);
		query.addQueryData("endRecord", 100);
		query.addQueryData("istate", "1");
		List<Mediareports> list = mediareportsMapper.findPage(query.getQueryData());
		return list;
	}
	
	public PageResult<Mediareports> findPage(PaginationQuery query) throws Exception {
		PageResult<Mediareports> result = null;
		try {
			Integer count = mediareportsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", startRecord);
				query.addQueryData("endRecord", query.getRowsPerPage());
				List<Mediareports> list = mediareportsMapper.findPage(query.getQueryData());
				result = new PageResult<Mediareports>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
