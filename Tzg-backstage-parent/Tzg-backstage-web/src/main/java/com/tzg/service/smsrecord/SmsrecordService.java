package com.tzg.service.smsrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.smsrecord.Smsrecord;
import com.tzg.entitys.smsrecord.SmsrecordMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class SmsrecordService extends BaseService {

	@Autowired
	private SmsrecordMapper smsrecordMapper;	
	   
	@Transactional(readOnly=true)
    public Smsrecord findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return smsrecordMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        smsrecordMapper.deleteById(id);
    }
	
	public void save(Smsrecord smsrecord) throws Exception {	    
		smsrecordMapper.save(smsrecord);
	}
	
	public void update(Smsrecord smsrecord) throws Exception {	
		if(smsrecord.getId() == null){
			throw new Exception("id不能为空");
		}
		smsrecordMapper.update(smsrecord);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Smsrecord> findPage(PaginationQuery query) throws Exception {
		PageResult<Smsrecord> result = null;
		try {
			Integer count = smsrecordMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Smsrecord> list = smsrecordMapper.findPage(query.getQueryData());
				result = new PageResult<Smsrecord>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
