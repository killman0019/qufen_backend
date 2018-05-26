package com.tzg.service.institution;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.institution.Institution;
import com.tzg.entitys.institution.InstitutionMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class InstitutionService extends BaseService {

	@Autowired
	private InstitutionMapper institutionMapper;	
	   
	@Transactional(readOnly=true)
    public Institution findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return institutionMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        institutionMapper.deleteById(id);
    }
	
	public void save(Institution institution) throws Exception {	
		if (StringUtils.isBlank(institution.getVcLogoUrl())) {
			throw new Exception("机构logo地址不能为空！");
		}
		if (StringUtils.isBlank(institution.getVcName())) {
			throw new Exception("机构名称不能为空！");
		}
		if (StringUtils.isBlank(institution.getVcUrl())) {
			throw new Exception("机构介绍链接地址不能为空！");
		}
		if (institution.getIsort() == null) {
			throw new Exception("顺序不能为空！");
		}
		institution.setIstate(1);
		institutionMapper.save(institution);
	}
	
	public void update(Institution institution) throws Exception {	
		if(institution.getId() == null){
			throw new Exception("id不能为空");
		}
		if (StringUtils.isBlank(institution.getVcLogoUrl())) {
			throw new Exception("机构logo地址不能为空！");
		}
		if (StringUtils.isBlank(institution.getVcName())) {
			throw new Exception("机构名称不能为空！");
		}
		if (StringUtils.isBlank(institution.getVcUrl())) {
			throw new Exception("机构介绍链接地址不能为空！");
		}
		if (institution.getIsort() == null) {
			throw new Exception("顺序不能为空！");
		}
		institutionMapper.update(institution);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Institution> findPage(PaginationQuery query) throws Exception {
		PageResult<Institution> result = null;
		try {
			Integer count = institutionMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Institution> list = institutionMapper.findPage(query.getQueryData());
				result = new PageResult<Institution>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
