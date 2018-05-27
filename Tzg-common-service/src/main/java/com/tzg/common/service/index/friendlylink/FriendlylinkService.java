package com.tzg.common.service.index.friendlylink;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.friendlylink.Friendlylink;
import com.tzg.entitys.friendlylink.FriendlylinkMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
public class FriendlylinkService {

	@Autowired
	private FriendlylinkMapper friendlylinkMapper;	
	   
    public Friendlylink findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return friendlylinkMapper.findById(id);
    }
	
	/**
	 * web首页List
	 * @return
	 */
	public List<Friendlylink> findIndexList(){
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("startRecord", 0);
		query.addQueryData("endRecord", 100);
		query.addQueryData("istate", "1");
		List<Friendlylink> list = friendlylinkMapper.findPage(query.getQueryData());
		return list;
	}
	
	public PageResult<Friendlylink> findPage(PaginationQuery query) throws Exception {
		PageResult<Friendlylink> result = null;
		try {
			Integer count = friendlylinkMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", startRecord);
				query.addQueryData("endRecord", query.getRowsPerPage());
				List<Friendlylink> list = friendlylinkMapper.findPage(query.getQueryData());
				result = new PageResult<Friendlylink>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
