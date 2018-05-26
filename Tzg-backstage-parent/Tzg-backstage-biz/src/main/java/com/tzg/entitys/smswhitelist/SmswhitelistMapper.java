package com.tzg.entitys.smswhitelist;

import java.util.Map;

import com.tzg.entitys.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface SmswhitelistMapper extends BaseMapper<Smswhitelist, java.lang.Integer> {	
	public Integer verifySmswhitelist(Map<String,String> map);
}
