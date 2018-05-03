package com.tzg.common.service.rocketmqlog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.StringUtil;
import com.tzg.entitys.rocketmqlog.Rocketmqlog;
import com.tzg.entitys.rocketmqlog.RocketmqlogMapper;

@Service
public class RocketmqlogService {

	@Autowired
	private RocketmqlogMapper rocketmqlogMapper;	
	   
    public Rocketmqlog findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return rocketmqlogMapper.findById(id);
    }
    
    /**
     * 根据消息key查询消息
     * @param key 消息key
     * @return
     * @throws Exception
     */
    public Rocketmqlog findByKey(java.lang.String key) throws Exception {
    	if(StringUtil.isBlank(key)){
			throw new Exception("key不能为空");
		}
        return rocketmqlogMapper.findByKey(key);
    } 
    
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        rocketmqlogMapper.deleteById(id);
    }
	
	public void save(Rocketmqlog rocketmqlog) throws Exception {	    
		rocketmqlogMapper.save(rocketmqlog);
	}
	
	public void update(Rocketmqlog rocketmqlog) throws Exception {	
		if(rocketmqlog.getId() == null){
			throw new Exception("id不能为空");
		}
		rocketmqlogMapper.update(rocketmqlog);
	}	
	
	public PageResult<Rocketmqlog> findPage(PaginationQuery query) throws Exception {
		PageResult<Rocketmqlog> result = null;
		try {
			Integer count = rocketmqlogMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", startRecord);
				query.addQueryData("endRecord", query.getRowsPerPage());
				List<Rocketmqlog> list = rocketmqlogMapper.findPage(query.getQueryData());
				result = new PageResult<Rocketmqlog>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
