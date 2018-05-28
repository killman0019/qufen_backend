package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFQfIndexService")
@Transactional
public class QfIndexService {

	@Autowired
	private com.tzg.entitys.kff.qfindex.QfIndexMapper QfIndexMapper;	
	   
	@Transactional(readOnly=true)
    public QfIndex findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return QfIndexMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
    	QfIndexMapper.deleteById(id);
    }
	
	public void save(QfIndex qfIndex) throws RestServiceException {	    
		QfIndexMapper.save(qfIndex);
	}
	
	public void update(QfIndex qfIndex) throws RestServiceException {	
		if(qfIndex.getQfIndexId() == null){
			throw new RestServiceException("id不能为空");
		}
		QfIndexMapper.update(qfIndex);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<QfIndex> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<QfIndex> result = null;
		try {
			Integer count = QfIndexMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<QfIndex> list = QfIndexMapper.findPage(query.getQueryData());
				result = new PageResult<QfIndex>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public QfIndex findByUserId(Integer userid)  throws RestServiceException {
		if(userid == null){
			throw new RestServiceException("id不能为空");
		}
		return QfIndexMapper.findByUserId(userid);
	}
	public void updateYxPraise(Integer qfIndexId,Integer userid)  throws RestServiceException {
		if(userid == null){
			throw new RestServiceException("用户id不能为空");
		}
		if(qfIndexId == null){
			throw new RestServiceException("id不能为空");
		}
		 QfIndexMapper.updateYxPraise(qfIndexId, userid);
	}

	public void updateYxPraiseValid() {
		QfIndexMapper.updateYxPraiseValid();
	}

	

	
}
