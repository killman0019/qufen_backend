package com.tzg.service.smswhitelist;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.RegexUtil;
import com.tzg.entitys.smswhitelist.Smswhitelist;
import com.tzg.entitys.smswhitelist.SmswhitelistMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class SmswhitelistService extends BaseService {
	@Autowired
	private RedisService redisService;
	@Autowired
	private SmswhitelistMapper smswhitelistMapper;	
	   
	@Transactional(readOnly=true)
    public Smswhitelist findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return smswhitelistMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
    	this.redisService.del("smsWhites");
        smswhitelistMapper.deleteById(id);
    }
	
	public void save(Smswhitelist smswhitelist) throws Exception {
		if(!RegexUtil.match(smswhitelist.getVcPhone(), RegexUtil.PHONEREGEX)){
			throw new Exception("手机号码格式错误");
		}
		
		if (verifySmswhitelist("vcPhone", smswhitelist.getVcPhone())) {
			throw new Exception("手机号码已存在");
		}
		this.redisService.del("smsWhites");
		smswhitelist.setDtCreate(new Date());
		smswhitelistMapper.save(smswhitelist);
	}
	
	public void update(Smswhitelist smswhitelist) throws Exception {	
		if(smswhitelist.getId() == null){
			throw new Exception("id不能为空");
		}
		this.redisService.del("smsWhites");
		smswhitelistMapper.update(smswhitelist);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Smswhitelist> findPage(PaginationQuery query) throws Exception {
		PageResult<Smswhitelist> result = null;
		try {
			Integer count = smswhitelistMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Smswhitelist> list = smswhitelistMapper.findPage(query.getQueryData());
				result = new PageResult<Smswhitelist>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 验证 手机号 是否存在
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	private boolean verifySmswhitelist(String key, String value) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
			return false;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		Integer count = smswhitelistMapper.verifySmswhitelist(map);
		if (count > 0) {
			return true;
		}
		return false;
	}

	
}
