package com.tzg.service.active;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.active.Active;
import com.tzg.entitys.active.ActiveMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class ActiveService extends BaseService {

	@Autowired
	private ActiveMapper activeMapper;	
	   
	@Transactional(readOnly=true)
    public Active findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return activeMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        activeMapper.deleteById(id);
    }
	
	public void save(Active active) throws Exception {	    
		activeMapper.save(active);
	}
	
	public void update(Active active) throws Exception {	
		if(active.getId() == null){
			throw new Exception("id不能为空");
		}
		activeMapper.update(active);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Active> findPage(PaginationQuery query) throws Exception {
		PageResult<Active> result = null;
		try {
			Integer count = activeMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Active> list = activeMapper.findPage(query.getQueryData());
				result = new PageResult<Active>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
