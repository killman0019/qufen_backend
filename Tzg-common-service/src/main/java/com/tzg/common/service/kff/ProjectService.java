package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.project.KFFProjectMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFProjectService")
@Transactional
public class ProjectService   {

	@Autowired
	private KFFProjectMapper projectMapper;	
	   
	@Transactional(readOnly=true)
    public KFFProject findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return projectMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        projectMapper.deleteById(id);
    }
	
	public void save(KFFProject project) throws RestServiceException {	    
		projectMapper.save(project);
	}
	
	public void update(KFFProject project) throws RestServiceException {	
		if(project.getProjectId() == null){
			throw new RestServiceException("id不能为空");
		}
		projectMapper.update(project);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<KFFProject> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<KFFProject> result = null;
		try {
			Integer count = projectMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<KFFProject> list = projectMapper.findPage(query.getQueryData());
				result = new PageResult<KFFProject>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<KFFProject> findProjectByCode(Map<String, Object> map) {
		
		return projectMapper.findProjectByCode(map);
	}

	public void increaseFollowerNum(Integer projectId)  throws RestServiceException {
		if(projectId == null || projectId ==0){
			throw new RestServiceException("项目ID错误");
		}
		projectMapper.increaseFollowerNum(projectId);
	}
	
	public void decreaseFollowerNum(Integer projectId) {
		
		projectMapper.decreaseFollowerNum(projectId);
	}
	@Transactional(readOnly = true)
	public List<KFFProject> findProjectName() {
		// TODO Auto-generated method stub
		return projectMapper.findProjectName();
	}

	@Transactional(readOnly = true)
	public KFFProject findProjectIdByCodeAndChineseName(KFFProject kffProject) {
		// TODO Auto-generated method stub
		return projectMapper.findProjectIdByCodeAndChineseName(kffProject);
	}

	public void increaseRaterNum(Integer projectId) {
		if(projectId == null || projectId ==0){
			//throw new RestServiceException("项目ID错误");
			return;
		}
		projectMapper.increaseRaterNum(projectId);
		
	}

	public void updateTotalScore(Integer projectId, BigDecimal totalScore) {
		if(projectId == null || projectId ==0){
			//throw new RestServiceException("项目ID错误");
			return;
		}
		if(totalScore == null || totalScore.compareTo(BigDecimal.ZERO)<=0){
			return;
		}
		KFFProject original = projectMapper.findById(projectId);
		if(original == null){
			return;
		}
		KFFProject project = new KFFProject();
		project.setProjectId(projectId);
		project.setUpdateTime(new Date());
		BigDecimal originalTotal = original.getTotalScore()==null?BigDecimal.ZERO:original.getTotalScore();
		int raterNum = original.getRaterNum()==null?0:original.getRaterNum();
		if(raterNum  == 0){
			return ;
		}
		originalTotal = originalTotal.multiply(new BigDecimal(raterNum));
		totalScore = originalTotal.add(totalScore);
		totalScore = totalScore.divide(new BigDecimal(raterNum+1)).setScale(1, RoundingMode.HALF_DOWN);
		project.setTotalScore(totalScore);
	
		projectMapper.updateTotalScore(project);
	}

	
	
}
