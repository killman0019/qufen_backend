package com.tzg.service.focus;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.focus.Focus;
import com.tzg.entitys.focus.FocusMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class FocusService extends BaseService {

	@Autowired
	private FocusMapper focusMapper;	
	   
	@Transactional(readOnly=true)
    public Focus findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return focusMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        focusMapper.deleteById(id);
    }
	
	public void save(Focus focus) throws Exception {
		if (StringUtils.isBlank(focus.getVcName())) {
			throw new Exception("别名不能为空！");
		}
		if (focus.getItype() == null) {
			throw new Exception("适用类型不能为空！");
		}
		if (StringUtils.isBlank(focus.getVcUrl())) {
			throw new Exception("链接地址不能为空！");
		}
		if (focus.getIsort() == null) {
			throw new Exception("顺序不能为空！");
		}
//		if (StringUtils.isBlank(focus.getVcJumpUrl())) {
//			throw new Exception("跳转地址不能为空！");
//		}
		focus.setIstate(1);
		focus.setDtCreate(new Date());
		
		focusMapper.save(focus);
	}
	
	public void update(Focus focus) throws Exception {	
		if(focus.getId() == null){
			throw new Exception("id不能为空");
		}
		if (StringUtils.isBlank(focus.getVcName())) {
			throw new Exception("别名不能为空！");
		}
		if (StringUtils.isBlank(focus.getVcUrl())) {
			throw new Exception("链接地址不能为空！");
		}
		if (focus.getIsort() == null) {
			throw new Exception("顺序不能为空！");
		}
		if (StringUtils.isBlank(focus.getVcChannel())) {
			focus.setVcChannel("");
		}
//		if (StringUtils.isBlank(focus.getVcJumpUrl())) {
//			throw new Exception("跳转地址不能为空！");
//		}
		focusMapper.update(focus);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Focus> findPage(PaginationQuery query) throws Exception {
		PageResult<Focus> result = null;
		try {
			Integer count = focusMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Focus> list = focusMapper.findPage(query.getQueryData());
				result = new PageResult<Focus>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
