package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.projectevastat.Projectevastat;
import com.tzg.entitys.kff.projectevastat.ProjectevastatMapper;
import com.tzg.rest.exception.rest.RestServiceException;


@Service(value="KFFProjectevastatService")
@Transactional
public class ProjectevastatService {

	@Autowired
	private ProjectevastatMapper projectevastatMapper;	
	   
	@Transactional(readOnly=true)
    public Projectevastat findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new  RestServiceException("id不能为空");
		}
        return projectevastatMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        projectevastatMapper.deleteById(id);
    }
	
	public void save(Projectevastat projectevastat) throws RestServiceException {	    
		projectevastatMapper.save(projectevastat);
	}
	
	public void update(Projectevastat projectevastat) throws RestServiceException {	
		if(projectevastat.getId() == null){
			throw new RestServiceException("id不能为空");
		}
		projectevastatMapper.update(projectevastat);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Projectevastat> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Projectevastat> result = null;
		try {
			Integer count = projectevastatMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Projectevastat> list = projectevastatMapper.findPage(query.getQueryData());
				result = new PageResult<Projectevastat>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
