package com.tzg.rmi.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.smsrecord.SmsrecordService;
import com.tzg.common.service.smssend.SmssendService;
import com.tzg.common.utils.DateUtil;
import com.tzg.rmi.service.SmsSendRmiService;

public class SmsSendRmiServiceImpl implements SmsSendRmiService {
	
	@Autowired
	private SmssendService smssendService;
	@Autowired
	private SmsrecordService smsrecordService;

	
	@Override
	public void sendMSG(String phoneNumber, String dynamicValidateCode,
			String bus) throws Exception {
		smssendService.sendMSG(phoneNumber, dynamicValidateCode, bus);
	}

	@Override
	public Integer findSendMSGCount(String phoneNumber,int timespan) throws Exception {
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("vcPhone", phoneNumber);
		query.addQueryData("istate", "1");
		query.addQueryData("itype", "2");
		Date date1 = new Date();
		long Time = ( date1.getTime ( ) / 1000 ) -timespan * 60;
		date1.setTime ( Time * 1000 );
		query.addQueryData("dtSendBegin", DateUtil.getDateTime(date1));
		query.addQueryData("dtSendEnd", DateUtil.getDateTime(new Date()));
		Integer count = smsrecordService.findPageCount(query);
		return count;
	}

	@Override
	public void sendMSGByLoginAccountId(Integer loginAccountId,
			Integer smstemplateType, String vcName, String investAmt,
			String interestAmt) throws Exception {
		smssendService.sendMSGByLoginAccountId(loginAccountId, smstemplateType,
				vcName, investAmt, interestAmt);

	}

}
