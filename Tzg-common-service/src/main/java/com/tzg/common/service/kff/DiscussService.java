package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.discuss.Discuss;
import com.tzg.entitys.kff.discuss.DiscussMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFDiscussService")
@Transactional
public class DiscussService   {

	@Autowired
	private DiscussMapper discussMapper;	
	   
	@Transactional(readOnly=true)
    public Discuss findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return discussMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        discussMapper.deleteById(id);
    }
	
	public void save(Discuss discuss) throws RestServiceException {	    
		discussMapper.save(discuss);
	}
	
	public void update(Discuss discuss) throws RestServiceException {	
		if(discuss.getDiscussId() == null){
			throw new RestServiceException("id不能为空");
		}
		discussMapper.update(discuss);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Discuss> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Discuss> result = null;
		try {
			Integer count = discussMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Discuss> list = discussMapper.findPage(query.getQueryData());
				result = new PageResult<Discuss>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Discuss findByPostId(Integer postId)  throws RestServiceException {
		
		return discussMapper.findByPostId(postId);
	}
	

	
}
