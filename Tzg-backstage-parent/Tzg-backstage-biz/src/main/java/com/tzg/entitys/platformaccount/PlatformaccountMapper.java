package com.tzg.entitys.platformaccount;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface PlatformaccountMapper extends BaseMapper<Platformaccount, java.lang.Integer> {

    public Platformaccount findByLoginAccountId(Integer iloginAccountID);

    /**
     * 查询平台账户
     * 
     * @return
     */
    public Platformaccount findMaxOne();

    /**
     * 用户提现手续费
     * 
     * @param map
     */
    public void updateCashFeeAmt(Map<String, Object> map);

    /**
     * 投资人提现失败 退款
     * 
     * @param map
     */
    public void updateCashFailedAmt(Map<String, Object> map);

    /**
     * 用户提现
     * 
     * @param map
     */
    public void updateCash(Map<String, Object> map);

    /**
     * 用户充值
     * 
     * @param map
     */
    public void updateChargeAmt(Map<String, Object> map);

    /**
     * 投资- 红包支出
     * 
     * @param map
     */
    public void updateRedAmt(Map<String, Object> map);

    /**
     * 标的募集结束划账
     * 
     * @param map
     */
    public void updateTransfer(Map<String, Object> map);

    /**
     * 管理费添加
     * 
     * @param map
     */
    public void updateReserveAmt(Map<String, Object> map);

    /**
     * 借款人还款
     * 
     * @param map
     */
    public void updateRepayAmt(Map<String, Object> map);

    /**
     * 借款人补还
     * 
     * @param map
     */
    public void updateMakeupAmt(Map<String, Object> map);

    /**
     * 归还保证金
     * 
     * @param map
     */
    public void updateBondAmt(Map<String, Object> map);

    /**
     * 体验金奖励总支出
     * 
     * @param map
     */
    public void updateExperienceSubjectFee(Map<String, Object> map);

    /**
     * 向第三方支出提现手续费
     * 
     * @param map
     */
    public void updatePlatformCashFeeAmt(Map<String, Object> map);
    
    //后台操作充值
    public void updateBackTotalRecharge(Map<String, Object> map);
    //后台操作提现
    public void updateBackTotalCash(Map<String, Object> map);
   
    //后台备用金操作充值
    public void updateReserveRecharge(Map<String, Object> map);
    //后台备用金操作提现
    public void updateReserveCash(Map<String, Object> map);

	/**
	 * @Title: 
	 * @Description:平台管理费 收入增加
	 * @param map
	 * @Author Songwj
	 * @Date 2016年1月12日
	 */
	public void updateExpenseIncome(Map<String, Object> map);
    
    
}
