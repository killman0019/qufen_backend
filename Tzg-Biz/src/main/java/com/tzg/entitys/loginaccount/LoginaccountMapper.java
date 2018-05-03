package com.tzg.entitys.loginaccount;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface LoginaccountMapper extends BaseMapper<Loginaccount, java.lang.Integer> {
	
	public Loginaccount findAllValidById(Integer id);
	public Loginaccount findByUsername(Map<String,Object> map);
	public List<Loginaccount> findByUsernameFuzzy(Map<String,Object> map);
	
	public Integer verifyLoginaccount(Map<String,Object> map);
	
	public Loginaccount login(Map<String,Object> map);
	
	public Integer findIdByRecommendedCode(String vcRecommendedCode);
	
	public Loginaccount findByPhoneNumber(String vcPhone);
	public Loginaccount findByEmail(String vcEmail);
	public Loginaccount findByLoginName(String vcLoginName);
	
	public List<Loginaccount> queryLoginaccountAll(Loginaccount account);
	
	public Integer findBorrowerCashPageCount(Map<String,Object> map);
	public List<Loginaccount> findBorrowerCashPage(Map<String,Object> map);
	
	public Integer findPageCountForOm(Map<String,Object> map);
	public List<Loginaccount> findPageForOm(Map<String,Object> map);
	
	/**查询生日的用户*/
	public List<Loginaccount> findPageForBirthday(Map<String,Object> map);
	
	/**根据推荐码查询用户*/
	public Loginaccount findByRecommendCode(Map<String,Object> map);
	
	public Integer findAllUserAmount();
}
