package com.tzg.common.base;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;

@Service
public interface BaseServiceInterface<T,PK extends Serializable> {

    public T findById(PK id) throws Exception ;
	
	public void save(T entity) throws Exception ;
	
	public void update(T entity) throws Exception ;
	
	public PageResult<T> findPage(PaginationQuery query) throws Exception ;
	
	public Integer findCountByQueryData(PaginationQuery query) ;
	

	
}
