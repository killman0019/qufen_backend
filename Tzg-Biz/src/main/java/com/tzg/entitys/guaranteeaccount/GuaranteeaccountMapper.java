package com.tzg.entitys.guaranteeaccount;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface GuaranteeaccountMapper extends BaseMapper<Guaranteeaccount, java.lang.Integer> {	
	Guaranteeaccount findByLoginAccountId(Integer iLoginAccountId);
	/**
	 * 充值完成增加账户余额 
	 * @param map
	 */
	void updateChargeAmt(Map<String,Object> map);
	
	/**
	 * 提现完成增加账户余额 
	 * @param map
	 */
	void updateCashAmt(Map<String,Object> map);
}
