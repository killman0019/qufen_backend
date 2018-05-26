package com.tzg.service.lowlevelinvest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.lowlevelinvest.Lowlevelinvest;
import com.tzg.entitys.lowlevelinvest.LowlevelinvestMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class LowlevelinvestService extends BaseService {

	@Autowired
	private LowlevelinvestMapper lowlevelinvestMapper;	
	   
	@Transactional(readOnly=true)
    public Lowlevelinvest findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return lowlevelinvestMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        lowlevelinvestMapper.deleteById(id);
    }
	
	public void save(Lowlevelinvest lowlevelinvest) throws Exception {	    
		lowlevelinvestMapper.save(lowlevelinvest);
	}
	
	public void update(Lowlevelinvest lowlevelinvest) throws Exception {	
		if(lowlevelinvest.getId() == null){
			throw new Exception("id不能为空");
		}
		lowlevelinvestMapper.update(lowlevelinvest);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Lowlevelinvest> findPage(PaginationQuery query) throws Exception {
		PageResult<Lowlevelinvest> result = null;
		try {
			Integer count = lowlevelinvestMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Lowlevelinvest> list = lowlevelinvestMapper.findPage(query.getQueryData());
				result = new PageResult<Lowlevelinvest>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
