package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.kff.CoinPropertyService;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.CoinPropertyRmiService;

public class CoinPropertyRmiServiceImpl implements CoinPropertyRmiService {

	private static final Log logger = LogFactory.getLog(CoinPropertyRmiServiceImpl.class);

	@Autowired
	private CoinPropertyService coinPropertyService;

	public List<CoinProperty> findListByAttr(Map<String, Object> map) {
		return coinPropertyService.findListByAttr(map);
	}
	
	public PageResult<CoinProperty> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<CoinProperty> result = coinPropertyService.findPage(query);
		return result;
	}
	
	public void updateByMap(Map<String,Object> map) throws RestServiceException {
		coinPropertyService.updateByMap(map);
	}
	
	public CoinProperty findById(Integer id) {
		return  coinPropertyService.findById(id);
	}
	
}
