package com.tzg.common.service.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFEvaluationService")
@Transactional
public class EvaluationService   {

	@Autowired
	private EvaluationMapper evaluationMapper;	
	   
	@Transactional(readOnly=true)
    public Evaluation findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return evaluationMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        evaluationMapper.deleteById(id);
    }
	
	public void save(Evaluation evaluation) throws RestServiceException {	    
		evaluationMapper.save(evaluation);
	}
	
	public void update(Evaluation evaluation) throws RestServiceException {	
		if(evaluation.getEvaluationId() == null){
			throw new RestServiceException("id不能为空");
		}
		evaluationMapper.update(evaluation);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Evaluation> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Evaluation> result = null;
		try {
			Integer count = evaluationMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Evaluation> list = evaluationMapper.findPage(query.getQueryData());
				result = new PageResult<Evaluation>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Evaluation findByPostId(Integer postId) throws RestServiceException{
		if(postId == null){
			throw new RestServiceException("postId不能为空");
		}
        return evaluationMapper.findByPostId(postId);
	}

	public List<Evaluation> findByProjectId(Integer projectId) throws RestServiceException{
		if(projectId == null){
			throw new RestServiceException("postId不能为空");
		}
		Map<String,Object> map = new HashMap<>();
		map.put("projectId", projectId+"");
		map.put("status", "1");
		map.put("modelType", "2");
		List<Evaluation> list = evaluationMapper.findByWhere(map);
		
		return list;
	}
	

	
}
