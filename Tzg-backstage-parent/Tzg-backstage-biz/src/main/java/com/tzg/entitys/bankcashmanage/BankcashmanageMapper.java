package com.tzg.entitys.bankcashmanage;

import java.util.List;
import java.util.Map;

import com.tzg.entitys.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface BankcashmanageMapper extends BaseMapper<Bankcashmanage, java.lang.Integer> {

	public List<Bankcashmanage> findCashBankCard(Map<String, Object> map);

	public List<Bankcashmanage> findByCardId(Integer id);	

}
