package com.tzg.common.service.smsrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.smsrecord.Smsrecord;
import com.tzg.entitys.smsrecord.SmsrecordMapper;

@Service
public class SmsrecordService {

	@Autowired
	private 	SmsrecordMapper smsrecordMapper;
	
	public void save(Smsrecord smsrecord) throws Exception{
		smsrecordMapper.save(smsrecord);
	}
	
	public Integer findPageCount(PaginationQuery query){
		return smsrecordMapper.findPageCount(query.getQueryData());
	}
}
