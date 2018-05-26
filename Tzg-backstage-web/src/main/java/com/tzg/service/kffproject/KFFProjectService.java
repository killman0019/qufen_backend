package com.tzg.service.kffproject;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kffproject.Project;
import com.tzg.entitys.kffproject.ProjectMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class KFFProjectService extends BaseService {

	@Autowired
	private ProjectMapper projectMapper;	
	   
	@Transactional(readOnly=true)
    public Project findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return projectMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        projectMapper.deleteById(id);
    }
	
	public void save(Project project) throws Exception {
		Date now = new Date();
		project.setCollectNum(0);
		project.setCommentsNum(0);
		project.setCreateTime(now);
		project.setFollowerNum(0);
		if(project.getIssueDateStr() == null){
			throw new Exception("发行时间不能为空");
		}
		project.setIssueDate(DateUtil.getDate(project.getIssueDateStr(), "yyyy-MM-dd"));
		if(project.getIssueDate().compareTo(now)>0){
			throw new Exception("发行时间不能大于当前时间");
		}
		if(project.getIssueNum() == null || project.getIssueNum()<0 ){
		  project.setIssueNum(0);
		}
		if(project.getListed() == null || project.getListed() ==0){
		  project.setListed(0);
		}else{
		   project.setListed(1);
		}
		if(StringUtils.isBlank(project.getProjectChineseName())){
			throw new Exception("中文名称不能为空");
		}
		if(project.getProjectChineseName().length() > 30){
			throw new Exception("中文名称不能超过30个字符");
		}
		if(StringUtils.isBlank(project.getProjectEnglishName())){
			throw new Exception("英文名称不能为空");
		}
		if(project.getProjectEnglishName().length() > 30){
			throw new Exception("英文名称不能超过30个字符");
		}
		if(StringUtils.isBlank(project.getProjectCode())){
			throw new Exception("代币名称不能为空");
		}
		if(project.getProjectCode().length() > 10){
			throw new Exception("代币名称不能超过10个字符");
		}
		if(StringUtils.isBlank(project.getProjectDesc())){
			throw new Exception("项目描述不能为空");
		}
		if(project.getProjectDesc().length() > 30000){
			throw new Exception("项目描述不能超过30000个字符");
		}
		if(StringUtils.isBlank(project.getProjectSignature())){
			throw new Exception("项目简单描述不能为空");
		}
		if(project.getProjectSignature().length() > 30){
			throw new Exception("项目简单描述不能超过30个字符");
		}
		//project.setProjectChineseName(value);
		//project.setProjectCode(value);
		//project.setProjectDesc(value);
		//project.setProjectEnglishName(value);
		//project.setProjectIcon(value);
		//project.setProjectSignature(value);
		
		//TODO
		//project.setProjectTypeId(value);
		//project.setProjectTypeName(value);
		project.setPublishTime(now);
		project.setRaterNum(0);
		project.setState(1);
		project.setStatus(1);
		project.setSubmitReason("系统后台提交");
		project.setSubmitUserContactInfo("系统后台提交");
		project.setSubmitUserId(0);
		project.setSubmitUserType(1);
		project.setTotalScore(BigDecimal.ZERO);
		project.setUpdateTime(now);
		if(StringUtils.isBlank(project.getWhitepaperUrl())){
			throw new Exception("白皮书网址不能为空");
		}
		if(project.getWhitepaperUrl().length() > 255){
			throw new Exception("白皮书网址长度不能超过255个字符");
		}
		if(StringUtils.isBlank(project.getWebsiteUrl())){
			throw new Exception("官网不能为空");
		}
		if(project.getWebsiteUrl().length() > 255){
			throw new Exception("官网长度不能超过255个字符");
		}
		//project.setWebsiteUrl(value);
		//project.setWhitepaperUrl(value);
		projectMapper.save(project);
	}
	
	public void update(Project project) throws Exception {	
		if(project.getProjectId() == null){
			throw new Exception("id不能为空");
		}
		projectMapper.update(project);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Project> findPage(PaginationQuery query) throws Exception {
		PageResult<Project> result = null;
		try {
			Integer count = projectMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Project> list = projectMapper.findPage(query.getQueryData());
				result = new PageResult<Project>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
