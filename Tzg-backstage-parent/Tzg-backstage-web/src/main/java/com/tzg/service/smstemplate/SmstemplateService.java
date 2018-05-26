package com.tzg.service.smstemplate;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.smstemplate.Smstemplate;
import com.tzg.entitys.smstemplate.SmstemplateMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class SmstemplateService extends BaseService {

	@Autowired
	private SmstemplateMapper smstemplateMapper;	
	   
	@Transactional(readOnly=true)
    public Smstemplate findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return smstemplateMapper.findById(id);
    }
	
    public Smstemplate findByType(java.lang.Integer itype) throws Exception {
    	if(itype == null){
			throw new Exception("itype不能为空");
		}
        return smstemplateMapper.findByType(itype);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        smstemplateMapper.deleteById(id);
    }
	
	public void save(Smstemplate smstemplate) throws Exception {	 
		if(StringUtils.isBlank(smstemplate.getVcTemplate())){
			throw new Exception("短信模板不能为空！");
		}		
		smstemplateMapper.save(smstemplate);
	}
	
	public void update(Smstemplate smstemplate) throws Exception {	
		if(smstemplate.getId() == null){
			throw new Exception("id不能为空！");
		}
		if(StringUtils.isBlank(smstemplate.getVcTemplate())){
			throw new Exception("短信模板不能为空！");
		}
		smstemplate.setDtModify(new Date());
		smstemplateMapper.update(smstemplate);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Smstemplate> findPage(PaginationQuery query) throws Exception {
		PageResult<Smstemplate> result = null;
		try {
			Integer count = smstemplateMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Smstemplate> list = smstemplateMapper.findPage(query.getQueryData());
				result = new PageResult<Smstemplate>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
