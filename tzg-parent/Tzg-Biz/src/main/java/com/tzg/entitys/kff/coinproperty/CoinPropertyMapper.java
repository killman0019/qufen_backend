package com.tzg.entitys.kff.coinproperty;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface CoinPropertyMapper extends BaseMapper<CoinProperty, java.lang.Integer> {

	CoinProperty findByUserId(Integer userid);

	List<CoinProperty> findAllCoinpropertyByWhere(Map<String, Object> map);

}
