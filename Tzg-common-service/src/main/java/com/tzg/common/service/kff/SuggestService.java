package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.suggest.Suggest;
import com.tzg.entitys.kff.suggest.SuggestMapper;
import com.tzg.rest.exception.rest.RestServiceException;


@Service(value="KFFSuggestService")
@Transactional
public class SuggestService   {

	@Autowired
	private SuggestMapper suggestMapper;	
	   
	@Transactional(readOnly=true)
    public Suggest findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return suggestMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        suggestMapper.deleteById(id);
    }
	
	public void save(Suggest suggest) throws RestServiceException {	    
		suggestMapper.save(suggest);
	}
	
	public void update(Suggest suggest) throws RestServiceException {	
		if(suggest.getSuggestId() == null){
			throw new RestServiceException("id不能为空");
		}
		suggestMapper.update(suggest);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Suggest> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Suggest> result = null;
		try {
			Integer count = suggestMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Suggest> list = suggestMapper.findPage(query.getQueryData());
				result = new PageResult<Suggest>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
