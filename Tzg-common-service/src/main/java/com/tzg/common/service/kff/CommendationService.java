package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.commendation.Commendation;
import com.tzg.entitys.kff.commendation.CommendationMapper;
import com.tzg.rest.exception.rest.RestServiceException;


@Service(value="KFFCommendationService")
@Transactional
public class CommendationService {

	@Autowired
	private CommendationMapper commendationMapper;	
	   
	@Transactional(readOnly=true)
    public Commendation findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return commendationMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        commendationMapper.deleteById(id);
    }
	
	public void save(Commendation commendation) throws RestServiceException {	    
		commendationMapper.save(commendation);
	}
	
	public void update(Commendation commendation) throws RestServiceException {	
		if(commendation.getCommendationId() == null){
			throw new RestServiceException("id不能为空");
		}
		commendationMapper.update(commendation);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Commendation> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Commendation> result = null;
		try {
			Integer count = commendationMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Commendation> list = commendationMapper.findPage(query.getQueryData());
				result = new PageResult<Commendation>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public BigDecimal findCommendationNum(Map<String, Object> totalMap) throws RestServiceException {
		
		return commendationMapper.findCommendationNum(totalMap);
	}

	public void updateUserInfo(Map<String, Object> commendationMap) {
		
		commendationMapper.updateUserInfo(commendationMap);
		
	}
	

	
}
