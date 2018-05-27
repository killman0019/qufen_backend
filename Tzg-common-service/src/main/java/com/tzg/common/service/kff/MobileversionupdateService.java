package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.mobileversionupdate.Mobileversionupdate;
import com.tzg.entitys.kff.mobileversionupdate.MobileversionupdateMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFMobileversionupdateService")
@Transactional
public class MobileversionupdateService   {

	@Autowired
	private MobileversionupdateMapper mobileversionupdateMapper;	
	   
	@Transactional(readOnly=true)
    public Mobileversionupdate findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return mobileversionupdateMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        mobileversionupdateMapper.deleteById(id);
    }
	
	public void save(Mobileversionupdate mobileversionupdate) throws Exception {	    
		mobileversionupdateMapper.save(mobileversionupdate);
	}
	
	public void update(Mobileversionupdate mobileversionupdate) throws Exception {	
		if(mobileversionupdate.getId() == null){
			throw new Exception("id不能为空");
		}
		mobileversionupdateMapper.update(mobileversionupdate);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Mobileversionupdate> findPage(PaginationQuery query) throws Exception {
		PageResult<Mobileversionupdate> result = null;
		try {
			Integer count = mobileversionupdateMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Mobileversionupdate> list = mobileversionupdateMapper.findPage(query.getQueryData());
				result = new PageResult<Mobileversionupdate>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
