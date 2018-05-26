package com.tzg.service.notice;

import java.util.Date;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.notice.Notice;
import com.tzg.entitys.notice.NoticeMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class NoticeService extends BaseService {

	@Autowired
	private NoticeMapper noticeMapper;	
	   
	@Transactional(readOnly=true)
    public Notice findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return noticeMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        noticeMapper.deleteById(id);
    }
	
	public void save(Notice notice) throws Exception {	 
		if(notice.getItype() == null){
			throw new Exception("类型不能为空！");
		}		
		if (StringUtils.isBlank(notice.getVcTitle())) {
			throw new Exception("标题不能为空！");
		}
		if (StringUtils.isBlank(notice.getVcContent())) {
			throw new Exception("内容不能为空！");
		}		
		notice.setDtCreate(new Date());
		notice.setDtNotice(new Date());
		notice.setIstate(1);
		noticeMapper.save(notice);
	}
	
	public void update(Notice notice) throws Exception {	
		if(notice.getId() == null){
			throw new Exception("id不能为空");
		}
		if(notice.getItype() == null){
			throw new Exception("类型不能为空！");
		}
		if (StringUtils.isBlank(notice.getVcTitle())) {
			throw new Exception("标题不能为空！");
		}
		if (StringUtils.isBlank(notice.getVcContent())) {
			throw new Exception("内容不能为空！");
		}			
		noticeMapper.update(notice);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Notice> findPage(PaginationQuery query) throws Exception {
		PageResult<Notice> result = null;
		try {
			Integer count = noticeMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Notice> list = noticeMapper.findPage(query.getQueryData());
				result = new PageResult<Notice>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
