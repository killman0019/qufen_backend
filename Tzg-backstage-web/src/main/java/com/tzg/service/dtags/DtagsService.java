package com.tzg.service.dtags;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.dtags.Dtags;
import com.tzg.entitys.dtags.DtagsMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class DtagsService extends BaseService {

	@Autowired
	private DtagsMapper dtagsMapper;	
	   
	@Transactional(readOnly=true)
    public Dtags findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return dtagsMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        dtagsMapper.deleteById(id);
    }
	
	public void save(Dtags dtags) throws Exception {	
		if(StringUtils.isBlank(dtags.getTagName())){
			throw new Exception("标签名称不能为空");
		}
		if(dtags.getTagName().length() > 8
				){
			throw new Exception("标签名称不能超过8个字符");
		}
		if(StringUtils.isBlank(dtags.getMemo())){
			throw new Exception("备注不能为空");
		}
		if(dtags.getMemo().length() > 20
				){
			throw new Exception("备注不能超过20个字符");
		}
		
		Dtags exist = dtagsMapper.findByTagName(dtags.getTagName());
		if(exist != null){
			throw new Exception("标签:" +dtags.getTagName() +"已经存在");
		}
		Date now = new Date();
		dtags.setCreateTime(now);
		dtags.setCreateUserId(1);
		dtags.setStatus(1);
		dtags.setUpdateTime(now);
		dtagsMapper.save(dtags);
	}
	
	public void update(Dtags dtags) throws Exception {	
		if(dtags.getTagId() == null){
			throw new Exception("id不能为空");
		}
		Date now = new Date();
		
        if(StringUtils.isBlank(dtags.getTagName())){
        	throw new Exception("标签名称不能为空");
		}
        if(dtags.getTagName().length() > 8
				){
			throw new Exception("标签名称不能超过8个字符");
		}
		if(StringUtils.isBlank(dtags.getMemo())){
			throw new Exception("备注不能为空");
		}
		if(dtags.getMemo().length() > 20
				){
			throw new Exception("备注不能超过20个字符");
		}
		dtags.setStatus(1);
		dtags.setUpdateTime(now);
		dtagsMapper.update(dtags);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Dtags> findPage(PaginationQuery query) throws Exception {
		PageResult<Dtags> result = null;
		try {
			Integer count = dtagsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Dtags> list = dtagsMapper.findPage(query.getQueryData());
				result = new PageResult<Dtags>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void active(Integer id)  throws Exception {
		if(id == null){
			throw new Exception("id不能为空");
		}
        dtagsMapper.activeById(id);
	}
	

	
}
