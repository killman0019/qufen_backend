package com.tzg.entitys.loginaccount;

import java.util.List;
import java.util.Map;

import com.tzg.entitys.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface LoginaccountMapper extends BaseMapper<Loginaccount, java.lang.Integer> {
	
	public Loginaccount findAllValidById(Integer id);
	public Loginaccount findByUsername(Map<String,String> map);
	public List<Loginaccount> findByUsernameFuzzy(Map<String,String> map);
	
	public Integer verifyLoginaccount(Map<String,String> map);
	
	public Loginaccount login(Map<String,String> map);
	
	public Integer findIdByRecommendedCode(String vcRecommendedCode);
	
	public Loginaccount findByPhoneNumber(String vcPhone);
	public Loginaccount findByEmail(String vcEmail);
	public Loginaccount findByLoginName(String vcLoginName);
	
	public List<Loginaccount> queryLoginaccountAll(Loginaccount account);
	
	public Integer findBorrowerCashPageCount(Map<String,String> map);
	public List<Loginaccount> findBorrowerCashPage(Map<String,String> map);
	
	public Integer findPageCountForOm(Map<String,String> map);
	public List<Loginaccount> findPageForOm(Map<String,String> map);
	
}
