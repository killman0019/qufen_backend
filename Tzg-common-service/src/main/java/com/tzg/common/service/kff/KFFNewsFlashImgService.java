package com.tzg.common.service.kff;

import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.base.BaseService;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.newsFlashImg.KFFNewsFlashImg;
import com.tzg.entitys.kff.newsFlashImg.KFFNewsFlashImgMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
@Transactional
public class KFFNewsFlashImgService extends BaseService {

	@Autowired
	private KFFNewsFlashImgMapper kffNewsFlashImgMapper;	
	   
	@Transactional(readOnly=true)
    public KFFNewsFlashImg findById(Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return kffNewsFlashImgMapper.findById(id);
    }
	@Transactional(readOnly=true)
    public KFFNewsFlashImg selectByPrimaryKey(Integer id) {
        return kffNewsFlashImgMapper.selectByPrimaryKey(id);
    }
	
    public void delete(Integer id) throws Exception {
    	kffNewsFlashImgMapper.deleteById(id);
    }
	
	public void update(KFFNewsFlashImg systemNewsFlashImg) throws Exception {	
		if(systemNewsFlashImg.getId() == null){
			throw new Exception("id不能为空");
		}
		if (StringUtils.isBlank(systemNewsFlashImg.getTitle())) {
			throw new Exception("标题不能为空！");
		}
		kffNewsFlashImgMapper.update(systemNewsFlashImg);
	}	
	
	
	public void stateUpdate(KFFNewsFlashImg systemNewsFlashImg) {	 
		kffNewsFlashImgMapper.update(systemNewsFlashImg);
	}
	
	@Transactional(readOnly=true)
	public PageResult<KFFNewsFlashImg> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<KFFNewsFlashImg> result = null;
		try {
			Integer count = kffNewsFlashImgMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<KFFNewsFlashImg> list = kffNewsFlashImgMapper.findPage(query.getQueryData());
				result = new PageResult<KFFNewsFlashImg>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
