package com.tzg.service.activerewardrecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.activerewardrecord.Activerewardrecord;
import com.tzg.entitys.activerewardrecord.ActiverewardrecordMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class ActiverewardrecordService extends BaseService {

	@Autowired
	private ActiverewardrecordMapper activerewardrecordMapper;	
	   
	@Transactional(readOnly=true)
    public Activerewardrecord findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return activerewardrecordMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        activerewardrecordMapper.deleteById(id);
    }
	
	public void save(Activerewardrecord activerewardrecord) throws Exception {	    
		activerewardrecordMapper.save(activerewardrecord);
	}
	
	public void update(Activerewardrecord activerewardrecord) throws Exception {	
		if(activerewardrecord.getId() == null){
			throw new Exception("id不能为空");
		}
		activerewardrecordMapper.update(activerewardrecord);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Activerewardrecord> findPage(PaginationQuery query) throws Exception {
		PageResult<Activerewardrecord> result = null;
		try {
			Integer count = activerewardrecordMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Activerewardrecord> list = activerewardrecordMapper.findPage(query.getQueryData());
				result = new PageResult<Activerewardrecord>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
