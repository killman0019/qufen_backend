package com.tzg.common.service.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.dareas.Dareas;
import com.tzg.entitys.kff.dareas.DareasMapper;
import com.tzg.rest.exception.rest.RestServiceException;


@Service(value="KFFDareasService")
@Transactional
public class DareasService {

	@Autowired
	private DareasMapper dareasMapper;	
	   
	@Transactional(readOnly=true)
    public Dareas findById(java.lang.String id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return dareasMapper.findById(id);
    }
	
    public void delete(java.lang.String id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        dareasMapper.deleteById(id);
    }
	
	public void save(Dareas dareas) throws RestServiceException {	    
		dareasMapper.save(dareas);
	}
	
	public void update(Dareas dareas) throws RestServiceException {	
		if(dareas.getCode() == null){
			throw new RestServiceException("id不能为空");
		}
		dareasMapper.update(dareas);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Dareas> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Dareas> result = null;
		try {
			Integer count = dareasMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Dareas> list = dareasMapper.findPage(query.getQueryData());
				result = new PageResult<Dareas>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Dareas> findProvinceAreas() {
		
		return dareasMapper.findProvinceAreas();
	}

	public List<Dareas> findSubAreasByCode(String areacode) {
		if(StringUtils.isBlank(areacode)){
			throw new RestServiceException("区域不能为空");
		}else if(areacode.length() != 2){
			throw new RestServiceException("非法的区域码");
		}		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code",areacode);
		return dareasMapper.findSubAreasByCode(map);
	}
	

	
}
