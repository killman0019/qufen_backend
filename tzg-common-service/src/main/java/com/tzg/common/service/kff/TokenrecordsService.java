package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.tokenrecords.TokenrecordsMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFTokenrecordsService")
@Transactional
public class TokenrecordsService {

	@Autowired
	private TokenrecordsMapper tokenrecordsMapper;	
	   
	@Transactional(readOnly=true)
    public Tokenrecords findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return tokenrecordsMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        tokenrecordsMapper.deleteById(id);
    }
	
	public void save(Tokenrecords tokenrecords) throws RestServiceException {	    
		tokenrecordsMapper.save(tokenrecords);
	}
	
	public void update(Tokenrecords tokenrecords) throws RestServiceException {	
		if(tokenrecords.getTokenRecordsId() == null){
			throw new RestServiceException("id不能为空");
		}
		tokenrecordsMapper.update(tokenrecords);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Tokenrecords> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Tokenrecords> result = null;
		try {
			Integer count = tokenrecordsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Tokenrecords> list = tokenrecordsMapper.findPage(query.getQueryData());
				result = new PageResult<Tokenrecords>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional(readOnly=true)
	public Float findUserSumRewardToken(java.lang.Integer userId) throws RestServiceException{
		
		return tokenrecordsMapper.findUserSumRewardToken();
	}
	
}
