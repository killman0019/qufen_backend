package com.tzg.entitys.kff.exchange;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;



public interface ExchangeMapper extends BaseMapper<Exchange, Integer> {

	List<Exchange> findByMap(Map<String, String> exchangeMap);
    
}