package com.tzg.service.friendlylink;

import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.friendlylink.Friendlylink;
import com.tzg.entitys.friendlylink.FriendlylinkMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class FriendlylinkService extends BaseService {

	@Autowired
	private FriendlylinkMapper friendlylinkMapper;	
	   
	@Transactional(readOnly=true)
    public Friendlylink findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return friendlylinkMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        friendlylinkMapper.deleteById(id);
    }
	
	public void save(Friendlylink friendlylink) throws Exception {	  
		if (StringUtils.isBlank(friendlylink.getVcName())) {
			throw new Exception("名称不能为空！");
		}
		if (StringUtils.isBlank(friendlylink.getVcUrl())) {
			throw new Exception("链接地址不能为空！");
		}
		friendlylink.setIstate(1);
		friendlylinkMapper.save(friendlylink);
	}
	
	public void update(Friendlylink friendlylink) throws Exception {	
		if(friendlylink.getId() == null){
			throw new Exception("id不能为空");
		}
		if (StringUtils.isBlank(friendlylink.getVcName())) {
			throw new Exception("名称不能为空！");
		}
		if (StringUtils.isBlank(friendlylink.getVcUrl())) {
			throw new Exception("链接地址不能为空！");
		}		
		friendlylinkMapper.update(friendlylink);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Friendlylink> findPage(PaginationQuery query) throws Exception {
		PageResult<Friendlylink> result = null;
		try {
			Integer count = friendlylinkMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Friendlylink> list = friendlylinkMapper.findPage(query.getQueryData());
				result = new PageResult<Friendlylink>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
