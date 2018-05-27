package com.tzg.rmi.service;

import com.tzg.entitys.leopard.system.SystemParam;

public interface SystemParamRmiService {
	
	/**
	 * 获取静态资源服务器地址
	 * @return
	 */
	public String getstaticUploadUrl();
	
	/**
	 * 获取wap地址
	 * @return
	 */
	public String getWapPath();
	
	/**
	 * 获取web地址
	 * @return
	 */
	public String getWebPath();
	
	/**
	 * 
	* @Title: findByCode 
	* @Description: TODO  
	* @param @param code
	* @param @return    
	* @return SystemParam
	* @see    
	* @throws
	 */
	public SystemParam findByCode(String code) throws Exception;
	
	/**
	 * 银行卡支付服务协议地址
	 * @return
	 */
	public String getBankCardPaymentProtocol();
	
}
