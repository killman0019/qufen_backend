package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.base.BaseService;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.newFlash.KFFNewsFlash;
import com.tzg.entitys.kff.newFlash.KFFNewsFlashMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
@Transactional
public class KFFNewsFlashService extends BaseService {

	@Autowired
	private KFFNewsFlashMapper kffNewsFlashMapper;	
	
	public void updateByMap(Map<String,Object> map) throws RestServiceException {	
		kffNewsFlashMapper.updateByMap(map);
	}	
	
	public KFFNewsFlash findById(Integer id) {
		return kffNewsFlashMapper.selectByPrimaryKey(id);
	}
	
	@Transactional(readOnly=true)
	public PageResult<KFFNewsFlash> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<KFFNewsFlash> result = null;
		try {
			Integer count = kffNewsFlashMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<KFFNewsFlash> list = kffNewsFlashMapper.findPage(query.getQueryData());
				result = new PageResult<KFFNewsFlash>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
