package com.tzg.entitys.bankpaymanage;

import java.util.List;
import java.util.Map;

import com.tzg.entitys.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface BankpaymanageMapper extends BaseMapper<Bankpaymanage, java.lang.Integer> {

	public String getBankNameById(int id);

	public List<Bankpaymanage> findPayBankCard(Map<String, Object> map);

	public Bankpaymanage findPayBankCardByBank(Map<String, Object> map);
	
	public String getAllBankNames();
}
