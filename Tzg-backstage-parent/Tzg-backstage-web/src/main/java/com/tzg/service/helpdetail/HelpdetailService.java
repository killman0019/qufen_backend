package com.tzg.service.helpdetail;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.helpdetail.Helpdetail;
import com.tzg.entitys.helpdetail.HelpdetailMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class HelpdetailService extends BaseService {

	@Autowired
	private HelpdetailMapper helpdetailMapper;	
	   
	@Transactional(readOnly=true)
    public Helpdetail findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return helpdetailMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        helpdetailMapper.deleteById(id);
    }
	
	public void save(Helpdetail helpdetail) throws Exception {	 
		if (helpdetail.getIhelpCategyId() == null) {
			throw new Exception("类别不能为空！");
		}
		if (StringUtils.isBlank(helpdetail.getVcQuestion())) {
			throw new Exception("问题不能为空！");
		}
		if (StringUtils.isBlank(helpdetail.getVcAnswer())) {
			throw new Exception("答复不能为空！");
		}
		if (helpdetail.getiSort() == null) {
			throw new Exception("顺序不能为空！");
		}
		helpdetail.setIstate(1);
		helpdetail.setDtCreate(new Date());
		helpdetailMapper.save(helpdetail);
	}
	
	public void update(Helpdetail helpdetail) throws Exception {	
		if(helpdetail.getId() == null){
			throw new Exception("id不能为空");
		}
		if (helpdetail.getIhelpCategyId() == null) {
			throw new Exception("类别不能为空！");
		}
		if (StringUtils.isBlank(helpdetail.getVcQuestion())) {
			throw new Exception("问题不能为空！");
		}
		if (StringUtils.isBlank(helpdetail.getVcAnswer())) {
			throw new Exception("答复不能为空！");
		}
		if (helpdetail.getiSort() == null) {
			throw new Exception("顺序不能为空！");
		}
		helpdetailMapper.update(helpdetail);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Helpdetail> findPage(PaginationQuery query) throws Exception {
		PageResult<Helpdetail> result = null;
		try {
			Integer count = helpdetailMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Helpdetail> list = helpdetailMapper.findPage(query.getQueryData());
				result = new PageResult<Helpdetail>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
