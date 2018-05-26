package com.tzg.common.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;

@Service
public class BaseService<T,PK extends Serializable> {

	private BaseMapper<T,PK> mapper;	
	
	public BaseMapper<T, PK> getMapper() {
		return mapper;
	}

	public void setMapper(BaseMapper<T, PK> mapper) {
		this.mapper = mapper;
	}

    public T findById(PK id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return mapper.findById(id);
    }
	
	public void save(T entity) throws Exception {	    
		mapper.save(entity);
	}
	
	public void update(T entity) throws Exception {	
		mapper.update(entity);
	}	
	
	public PageResult<T> findPage(PaginationQuery query) throws Exception {
		PageResult<T> result = null;
		try {
			Integer count = findCountByQueryData(query);
			List<T> list = null ;
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", startRecord);
				query.addQueryData("endRecord", query.getRowsPerPage());
				list = mapper.findPage(query.getQueryData());
			} 
			result = new PageResult<T>(list,count,query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
	public Integer findCountByQueryData(PaginationQuery query) {
		Integer count = mapper.findPageCount(query.getQueryData());
		return count;
	}
	

	
}
