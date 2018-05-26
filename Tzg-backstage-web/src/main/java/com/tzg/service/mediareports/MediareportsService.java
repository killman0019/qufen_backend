package com.tzg.service.mediareports;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.mediareports.Mediareports;
import com.tzg.entitys.mediareports.MediareportsMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class MediareportsService extends BaseService {

	@Autowired
	private MediareportsMapper mediareportsMapper;	
	   
	@Transactional(readOnly=true)
    public Mediareports findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return mediareportsMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        mediareportsMapper.deleteById(id);
    }
	
	public void save(Mediareports mediareports) throws Exception {	
		if (StringUtils.isBlank(mediareports.getVcLogoUrl())) {
			throw new Exception("媒体logo地址不能为空！");
		}
		if (StringUtils.isBlank(mediareports.getVcUrl())) {
			throw new Exception("媒体链接地址不能为空！");
		}
		if (mediareports.getIsort() == null) {
			throw new Exception("顺序不能为空！");
		}
		mediareports.setIstate(1);
		mediareportsMapper.save(mediareports);
	}
	
	public void update(Mediareports mediareports) throws Exception {	
		if(mediareports.getId() == null){
			throw new Exception("id不能为空");
		}
		if (StringUtils.isBlank(mediareports.getVcLogoUrl())) {
			throw new Exception("媒体logo地址不能为空！");
		}
		if (StringUtils.isBlank(mediareports.getVcUrl())) {
			throw new Exception("媒体链接地址不能为空！");
		}
		if (mediareports.getIsort() == null) {
			throw new Exception("顺序不能为空！");
		}
		mediareportsMapper.update(mediareports);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Mediareports> findPage(PaginationQuery query) throws Exception {
		PageResult<Mediareports> result = null;
		try {
			Integer count = mediareportsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Mediareports> list = mediareportsMapper.findPage(query.getQueryData());
				result = new PageResult<Mediareports>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
