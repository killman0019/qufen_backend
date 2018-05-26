package com.tzg.common.service.index.institution;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.institution.Institution;
import com.tzg.entitys.institution.InstitutionMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
public class InstitutionService {

	@Autowired
	private InstitutionMapper institutionMapper;	
	   
    public Institution findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return institutionMapper.findById(id);
    }
	
	/**
	 * web首页List
	 * @return
	 */
	public List<Institution> findIndexList(){
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("startRecord", 0);
		query.addQueryData("endRecord", 100);
		query.addQueryData("istate", "1");
		List<Institution> list = institutionMapper.findPage(query.getQueryData());
		return list;
	}
	
	public PageResult<Institution> findPage(PaginationQuery query) throws Exception {
		PageResult<Institution> result = null;
		try {
			Integer count = institutionMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", startRecord);
				query.addQueryData("endRecord", query.getRowsPerPage());
				List<Institution> list = institutionMapper.findPage(query.getQueryData());
				result = new PageResult<Institution>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
