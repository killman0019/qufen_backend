package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.dprojectType.DprojectType;
import com.tzg.entitys.kff.dprojectType.DprojectTypeMapper;
import com.tzg.rest.exception.rest.RestServiceException;


@Service(value="KFFDprojectTypeService")
@Transactional
public class DprojectTypeService  {

	@Autowired
	private DprojectTypeMapper dprojectTypeMapper;	
	   
	@Transactional(readOnly=true)
    public DprojectType findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return dprojectTypeMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        dprojectTypeMapper.deleteById(id);
    }
	
	public void save(DprojectType dprojectType) throws RestServiceException {	    
		dprojectTypeMapper.save(dprojectType);
	}
	
	public void update(DprojectType dprojectType) throws RestServiceException {	
		if(dprojectType.getProjectTypeId() == null){
			throw new RestServiceException("id不能为空");
		}
		dprojectTypeMapper.update(dprojectType);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<DprojectType> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<DprojectType> result = null;
		try {
			Integer count = dprojectTypeMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<DprojectType> list = dprojectTypeMapper.findPage(query.getQueryData());
				result = new PageResult<DprojectType>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<DprojectType> findAllProjectTypes() {
		
		return dprojectTypeMapper.findAllProjectTypes();
	}
	

	
}
