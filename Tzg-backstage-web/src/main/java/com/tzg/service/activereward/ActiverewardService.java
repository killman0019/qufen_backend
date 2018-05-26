package com.tzg.service.activereward;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.activereward.Activereward;
import com.tzg.entitys.activereward.ActiverewardMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class ActiverewardService extends BaseService {

	@Autowired
	private ActiverewardMapper activerewardMapper;	
	   
	@Transactional(readOnly=true)
    public Activereward findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return activerewardMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        activerewardMapper.deleteById(id);
    }
	
	public void save(Activereward activereward) throws Exception {	    
		activerewardMapper.save(activereward);
	}
	
	public void update(Activereward activereward) throws Exception {	
		if(activereward.getId() == null){
			throw new Exception("id不能为空");
		}
		activerewardMapper.update(activereward);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Activereward> findPage(PaginationQuery query) throws Exception {
		PageResult<Activereward> result = null;
		try {
			Integer count = activerewardMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Activereward> list = activerewardMapper.findPage(query.getQueryData());
				result = new PageResult<Activereward>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
