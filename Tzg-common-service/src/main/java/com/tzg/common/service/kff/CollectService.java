package com.tzg.common.service.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.collect.Collect;
import com.tzg.entitys.kff.collect.CollectMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFCollectService")
@Transactional
public class CollectService {

	@Autowired
	private CollectMapper collectMapper;	
	   
	@Transactional(readOnly=true)
    public Collect findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return collectMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        collectMapper.deleteById(id);
    }
	
	public void save(Collect collect) throws RestServiceException {	    
		collectMapper.save(collect);
	}
	
	public void update(Collect collect) throws RestServiceException {	
		if(collect.getCollectId() == null){
			throw new RestServiceException("id不能为空");
		}
		collectMapper.update(collect);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Collect> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Collect> result = null;
		try {
			Integer count = collectMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Collect> list = collectMapper.findPage(query.getQueryData());
				result = new PageResult<Collect>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Collect findByPostId(Integer userId, Integer postId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("collectUserId",userId+"");
		map.put("postId",postId+"");
		return collectMapper.findByWhere(map);
	}
	

	
}
