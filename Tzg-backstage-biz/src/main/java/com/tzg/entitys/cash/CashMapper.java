package com.tzg.entitys.cash;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface CashMapper extends BaseMapper<Cash, java.lang.Integer> {
	
	/**
	 * 今日 提现次数
	 * @param loginAccountId
	 * @return
	 */
	public Integer todayCashCount(Integer iloginAccountId);
	
	public Integer findPageCountForFailure(Map<String,String> map);
	public List<Cash> findPageForFailure(Map<String,String> map);
	
	
	public Integer updateFinancialCashFailureIdByIds(Map<String,Object> map);
	public Integer deleteFinancialCashFailureIdByIds(Map<String,Object> map);
	public Cash findByIFinancialCashFailureID(java.lang.Integer ifinancialCashFailureId) ;
	
	public List<Cash> downCashForInvestor(Map<String,String> map);
	public void updateDownCashForInvestor(Map<String,String> map);
	
	
	public List<Cash> findList(Map<String,String> map);
	
	/**
	 * 获取需要提现的列表
	 * @return
	 */
	public List<Cash> findCashScheduleList();

	/**
	 * 获取需要查询提现结果的列表
	 * @return
	 */
	public List<Cash> findWithdrawList();

	public Cash findSumCash(Map<String, String> queryData);

	public List<Cash> findSearch(Map<String, String> queryData);
	
	public String findAllBankName();
	
	public Integer updateAllSelected(Map<String, String> queryData);
	
	public Map<String,String> findCityCodeByCardNO(String vcCardNO);
	
	public Integer findSearchCount(Map<String, String> queryData);
}
