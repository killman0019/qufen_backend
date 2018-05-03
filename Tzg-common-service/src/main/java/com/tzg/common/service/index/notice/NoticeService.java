package com.tzg.common.service.index.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.notice.Notice;
import com.tzg.entitys.notice.NoticeMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
public class NoticeService{

	@Autowired
	private NoticeMapper noticeMapper;	
	   
    public Notice findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return noticeMapper.findById(id);
    }
	
	/**
	 * web首页List
	 * @return
	 */
	public List<Notice> findIndexList(String type){
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("startRecord", 0);
		query.addQueryData("endRecord", 10);
		query.addQueryData("istate", "1");
		query.addQueryData("itype", type);
		List<Notice> list = noticeMapper.findPage(query.getQueryData());
		return list;
	}
	
	public PageResult<Notice> findPage(PaginationQuery query) throws Exception {
		PageResult<Notice> result = null;
		try {
			Integer count = noticeMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", startRecord);
				query.addQueryData("endRecord", query.getRowsPerPage());
				List<Notice> list = noticeMapper.findPage(query.getQueryData());
				result = new PageResult<Notice>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
