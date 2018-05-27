package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.dtags.DtagsMapper;
import com.tzg.rest.exception.rest.RestServiceException;


@Service(value="KFFDtagsService")
@Transactional
public class DtagsService  {

	@Autowired
	private DtagsMapper dtagsMapper;	
	   
	@Transactional(readOnly=true)
    public Dtags findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return dtagsMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        dtagsMapper.deleteById(id);
    }
	
	public void save(Dtags dtags) throws RestServiceException {	    
		dtagsMapper.save(dtags);
	}
	
	public void update(Dtags dtags) throws RestServiceException {	
		if(dtags.getTagId() == null){
			throw new RestServiceException("id不能为空");
		}
		dtagsMapper.update(dtags);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Dtags> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Dtags> result = null;
		try {
			Integer count = dtagsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Dtags> list = dtagsMapper.findPage(query.getQueryData());
				result = new PageResult<Dtags>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Dtags> findAllTags() {
		
		return dtagsMapper.findAllTags();
	}
	

	
}
