package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.notice.KFFNotice;
import com.tzg.entitys.kff.notice.KFFNoticeMapper;


@Service(value="KFFNoticeService")
@Transactional
public class NoticeService   {

	@Autowired
	private KFFNoticeMapper noticeMapper;	
	   
	@Transactional(readOnly=true)
    public KFFNotice findById(java.lang.Integer id) throws Exception {
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
	
	public void save(KFFNotice notice) throws Exception {	    
		noticeMapper.save(notice);
	}
	
	public void update(KFFNotice notice) throws Exception {	
		if(notice.getNoticeId() == null){
			throw new Exception("id不能为空");
		}
		noticeMapper.update(notice);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<KFFNotice> findPage(PaginationQuery query) throws Exception {
		PageResult<KFFNotice> result = null;
		try {
			Integer count = noticeMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<KFFNotice> list = noticeMapper.findPage(query.getQueryData());
				result = new PageResult<KFFNotice>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public KFFNotice selectLatestNotice() {
		
		return noticeMapper.selectLatestNotice();
	}
	

	
}
