package com.tzg.common.service.kff;

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

	public void increaseFollowerNum(Integer projectId) {
		
		projectMapper.increaseFollowerNum(projectId);
	}
	
	public void decreaseFollowerNum(Integer projectId) {
		
		projectMapper.decreaseFollowerNum(projectId);
	}
	
}
