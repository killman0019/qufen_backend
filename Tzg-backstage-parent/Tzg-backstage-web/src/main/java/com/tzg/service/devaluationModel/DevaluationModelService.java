package com.tzg.service.devaluationModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.devaluationModel.DevaluationModel;
import com.tzg.entitys.devaluationModel.DevaluationModelMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class DevaluationModelService extends BaseService {

	@Autowired
	private DevaluationModelMapper devaluationModelMapper;	
	   
	@Transactional(readOnly=true)
    public DevaluationModel findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return devaluationModelMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        devaluationModelMapper.deleteById(id);
    }
	
	public void save(DevaluationModel devaluationModel) throws Exception {	    
		devaluationModelMapper.save(devaluationModel);
	}
	
	public void update(DevaluationModel devaluationModel) throws Exception {	
		if(devaluationModel.getModelId() == null){
			throw new Exception("id不能为空");
		}
		devaluationModelMapper.update(devaluationModel);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<DevaluationModel> findPage(PaginationQuery query) throws Exception {
		PageResult<DevaluationModel> result = null;
		try {
			Integer count = devaluationModelMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<DevaluationModel> list = devaluationModelMapper.findPage(query.getQueryData());
				result = new PageResult<DevaluationModel>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
