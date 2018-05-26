package com.tzg.common.service.rocketmqmsgreceive;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.StringUtil;
import com.tzg.entitys.rocketmqmsgreceive.Rocketmqmsgreceive;
import com.tzg.entitys.rocketmqmsgreceive.RocketmqmsgreceiveMapper;

@Service
@Transactional
public class RocketmqmsgreceiveService{

	@Autowired
	private RocketmqmsgreceiveMapper rocketmqmsgreceiveMapper;	
	   
	@Transactional(readOnly=true)
    public Rocketmqmsgreceive findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return rocketmqmsgreceiveMapper.findById(id);
    }
	
	/**
	 * 验证消息是否被消费成功
	 * @param key
	 * @return 已经被消费成功返回true 没消费返回false
	 * @throws Exception 
	 */
	public Boolean checkReceive(String key) throws Exception {
		// 查询改消息是否消费成功
		Rocketmqmsgreceive rocketmqmsgreceive = this.findByKey(key);
		// 如过该信息被消费成功，那么直接返回
		if (rocketmqmsgreceive != null
				&& StringUtil.equals(rocketmqmsgreceive.getVcresult(), "success")) {
			return true;
		}
		return false;
	}
	/**
	 * 保存消费成功的消息
	 * @param key 消息id
	 * @throws Exception
	 */
	public void saveSuccess(String key) throws Exception {
		// 查询改消息是否消费成功
		Rocketmqmsgreceive rocketmqmsgreceive = new Rocketmqmsgreceive();
		rocketmqmsgreceive.setVckey(key);
		rocketmqmsgreceive.setVcresult("success");
		this.save(rocketmqmsgreceive);
	}
	
	
	 /**
     * 根据消息key查询消息
     * @param key 消息key
     * @return
     * @throws Exception
     */
    public Rocketmqmsgreceive findByKey(java.lang.String key) throws Exception {
    	if(StringUtil.isBlank(key)){
			throw new Exception("key不能为空");
		}
        return rocketmqmsgreceiveMapper.findByKey(key);
    } 
    
    /**
     * 根据消息key查询消息
     * @param key 消息key
     * @return
     * @throws Exception
     */
    public Rocketmqmsgreceive findByKeyMqTag(java.lang.String key, String mqtag) throws Exception {
    	if(StringUtil.isBlank(key)){
			throw new Exception("key不能为空");
		}
    	if(StringUtil.isBlank(mqtag)){
			throw new Exception("mqtag不能为空");
		}
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("key", key);
    	map.put(mqtag, mqtag);
        return rocketmqmsgreceiveMapper.findByKeyMqTag(map);
    } 
    
    
    
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        rocketmqmsgreceiveMapper.deleteById(id);
    }
	
	public void save(Rocketmqmsgreceive rocketmqmsgreceive) throws Exception {	    
		rocketmqmsgreceiveMapper.save(rocketmqmsgreceive);
	}
	
	public void update(Rocketmqmsgreceive rocketmqmsgreceive) throws Exception {	
		if(rocketmqmsgreceive.getId() == null){
			throw new Exception("id不能为空");
		}
		rocketmqmsgreceiveMapper.update(rocketmqmsgreceive);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Rocketmqmsgreceive> findPage(PaginationQuery query) throws Exception {
		PageResult<Rocketmqmsgreceive> result = null;
		try {
			Integer count = rocketmqmsgreceiveMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", startRecord);
				query.addQueryData("endRecord", query.getRowsPerPage());
				List<Rocketmqmsgreceive> list = rocketmqmsgreceiveMapper.findPage(query.getQueryData());
				result = new PageResult<Rocketmqmsgreceive>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
