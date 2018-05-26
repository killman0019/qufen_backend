package com.tzg.entitys.llpaycard;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface LlpaycardMapper extends BaseMapper<Llpaycard, java.lang.Integer> {	
	
	/**
	 * 连连回调后 更新绑卡状态
	 * @param llpaycard
	 */
	public void updateStateByCardNo(Llpaycard llpaycard);
	
	/**
	 * 提现失败 更新累计提现金额
	 * @param llpaycard
	 */
	public void cashFailedUpdateAmtByCardNo(Llpaycard llpaycard);
	
	public List<Llpaycard> findBankList(Map<String, String> map);
	
	/**
	 * 需保留的提现金额
	 * @param map
	 * @return
	 */
	public BigDecimal getCanCashAmt(Map<String, Object> map);
	
	/**
	 * 更新绑卡状态
	 * @param llpaycard
	 */
	public void updateStateByRequestCode(Llpaycard llpaycard);

	/**
	 * 更新绑卡状态(根据支付渠道类型)
	 * @param llpaycard
	 */
	public void updateStateByCardNoAndChannelType(Llpaycard llpaycard);

	/**
	 * 根据银行卡查询绑卡信息
	 * @param llpaycard
	 */
	public Llpaycard findByCardNo(String cardNo);

	public List<Llpaycard> findLlpaycardByChannel(Map<String, Object> map);

	/**
	 * 根据绑卡信息查询提现银行卡
	 * @param llpaycard
	 */
	public Llpaycard findByBankCardId(Map<String, String> map);

	/**
	 * 账户管理获取绑定卡
	 * @param id
	 * @return
	 */
	public List<Llpaycard> findBindCardByLoginAccountId(Map<String, String> map);

}
