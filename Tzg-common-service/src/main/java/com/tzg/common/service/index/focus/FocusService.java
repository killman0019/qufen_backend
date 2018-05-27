package com.tzg.common.service.index.focus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.focus.Focus;
import com.tzg.entitys.focus.FocusMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
public class FocusService {

	@Autowired
	private FocusMapper focusMapper;	
	   
    public Focus findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return focusMapper.findById(id);
    }
	
	/**
	 * web首页焦点图List
	 * @return
	 */
	public List<Focus> findWebIndexList(){
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("startRecord", 0);
		query.addQueryData("endRecord", 6);
		query.addQueryData("istate", "1");
		query.addQueryData("itype", "1");
		List<Focus> list = focusMapper.findPage(query.getQueryData());
		return list;
	}
	
	/**
	 * wap首页焦点图List
	 * @return
	 */
	public List<Focus> findWapIndexList(){
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("startRecord", 0);
		query.addQueryData("endRecord", 6);
		query.addQueryData("istate", "1");
		query.addQueryData("itype", "2");
		List<Focus> list = focusMapper.findPage(query.getQueryData());
		return list;
	}
	
	public PageResult<Focus> findPage(PaginationQuery query) throws Exception {
		PageResult<Focus> result = null;
		try {
			Integer count = focusMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", startRecord);
				query.addQueryData("endRecord", query.getRowsPerPage());
				List<Focus> list = focusMapper.findPage(query.getQueryData());
				result = new PageResult<Focus>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
	public List<Focus> findFocusByQuery(PaginationQuery query) throws Exception {
		List<Focus> result = null;
		try {
			result = focusMapper.findPageForChannel(query.getQueryData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Focus> findFocus(Integer istate,Integer itype,Integer startRecord,Integer endRecord) throws Exception {
		List<Focus> result = null;
		PaginationQuery query = new PaginationQuery();
		try {
			query.addQueryData("istate", istate+"");
			query.addQueryData("itype", itype+"");
			query.addQueryData("startRecord", (startRecord-1)*endRecord);
			query.addQueryData("endRecord", endRecord);
			result = focusMapper.findPage(query.getQueryData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	   /**
	    * 发现页-》活动列表
	    *
	    * @param query
	    * @return
	    * @throws Exception
	    */
	   public PageResult<Focus> findActivityPage(PaginationQuery query) throws Exception {
	        PageResult<Focus> result = null;
	        try {
	            Integer count = focusMapper.findPageCountForChannel(query.getQueryData());
	            if (null != count && count.intValue() > 0) {
	                int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
	                query.addQueryData("startRecord", startRecord);
	                query.addQueryData("endRecord", query.getRowsPerPage());
	                List<Focus> list = focusMapper.findPageForChannel(query.getQueryData());
	                result = new PageResult<Focus>(list,count,query);
	            } 
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return result;
	    }
	    
}
