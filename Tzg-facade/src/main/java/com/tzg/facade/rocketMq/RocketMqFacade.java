package com.tzg.facade.rocketMq;

import java.math.BigDecimal;

import com.alibaba.dubbo.config.annotation.Service;


/**
 * 消息Facade
 */
@Service
public interface RocketMqFacade {
	
	/**
	 * 投资完成后发消息
	 * @param investrecordId 投资记录id
	 * @param loginAccountId 投资人账户信息id
	 * @param type 投资类型，暂时包含标的投资（初体验标），定期宝投资
	 */
	public void investSuccess(Integer investrecordId, Integer loginAccountId, String type);

	/**
	 * 红包发送发消息
	 *
	 * @param iloginAccountId
	 * @param awardsaccountId
	 * @param numAmt
	 * @param vcDesp
	 */
	public void redSendSuccessNtf(Integer iloginAccountId,BigDecimal numAmt, String vcDesp);

	/**
     * 注册成功发消息
     * @param iloginAccountId
     */
    public void registerSuccessNtf(Integer iloginAccountId);
    
    /**
     * 提现成功发消息
     * @param iloginAccountId
     */
    public void cashSuccessNtf(Integer iloginAccountId,Integer cashId);
    
    /**
     * 充值成功发消息
     * @param iloginAccountId
     */
    public void rechargeSuccessNtf(Integer iloginAccountId,Integer rechargeId,BigDecimal numAmt);
    
}
