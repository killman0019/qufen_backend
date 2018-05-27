package com.tzg.rmi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rmi.service.SystemParamRmiService;

public class SystemParamRmiServiceImpl implements SystemParamRmiService {
	
	@Autowired
	private SystemParamService systemParamService;
	
	@Override
	public String getstaticUploadUrl() {
		return systemParamService.getstaticUploadUrl();
	}

	@Override
	public String getWapPath() {
		return systemParamService.getWapPath();
	}

	@Override
	public String getWebPath() {
		return systemParamService.getWebPath();
	}

	public SystemParam findByCode(String code) throws Exception{
        return systemParamService.findByCode(code);
    }

	@Override
	public String getBankCardPaymentProtocol() {
		return systemParamService.getBankCardPaymentProtocol();
	}
}
