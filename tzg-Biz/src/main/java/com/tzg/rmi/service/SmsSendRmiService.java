package com.tzg.rmi.service;


/**
 * 短信 
 */
public interface SmsSendRmiService {
	
	/**
	 * 发送验证码类短信
	 * @param phoneNumber
	 * @param dynamicValidateCode
	 * @param bus
	 * @throws Exception
	 */
	public void sendMSG(String phoneNumber, String dynamicValidateCode, String bus) throws Exception;
	
	/**
	 * 指定时间内验证码发送次数
	 * @param phoneNumber 手机号码
	 * @param timespan  时间间隔 单位分钟
	 * @return
	 * @throws Exception
	 */
	public Integer findSendMSGCount(String phoneNumber, int timespan) throws Exception;
	/**
	 * 发送短信(指定用户ID)
	 * @param loginAccountId
	 * @param smstemplateType
	 * @param objects
	 * @throws Exception
	 */
	public void sendMSGByLoginAccountId(Integer loginAccountId,
			Integer smstemplateType, String vcName, String investAmt,
			String interestAmt) throws Exception;
}
