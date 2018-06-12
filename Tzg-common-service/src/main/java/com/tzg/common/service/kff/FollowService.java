package com.tzg.common.service.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.follow.FollowMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFFollowService")
@Transactional
public class FollowService   {

	@Autowired
	private FollowMapper followMapper;	
	   
	@Transactional(readOnly=true)
    public Follow findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return followMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        followMapper.deleteById(id);
    }
	
	public void save(Follow follow) throws RestServiceException {	    
		followMapper.save(follow);
	}
	
	public void update(Follow follow) throws RestServiceException {	
		if(follow.getFollowId() == null){
			throw new RestServiceException("id不能为空");
		}
		followMapper.update(follow);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Follow> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Follow> result = null;
		try {
			Integer count = followMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Follow> list = followMapper.findPage(query.getQueryData());
				result = new PageResult<Follow>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Follow findByUserIdAndFollowType(Integer userId, Integer followType, Integer followedId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("followUserId", userId+"");
		map.put("followType", followType+"");
		map.put("followedId", followedId+"");
		return followMapper.findByWhere(map);
	}

	public void updateUserInfo(Map<String, Object> followMap) {
		
		followMapper.updateUserInfo(followMap);
		
	}

	public List<Follow> findFollowedProjects(Integer userId, List<Integer> projectIds) {
		if(userId == null){ 
			return null;
		}
		if(CollectionUtils.isEmpty(projectIds)){
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("followUserId", userId+"");
		map.put("followType", "1");
		map.put("followedIds", projectIds);
		return followMapper.findFollowedProjects(map);
	}

	
}
