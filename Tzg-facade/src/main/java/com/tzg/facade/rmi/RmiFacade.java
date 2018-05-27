package com.tzg.facade.rmi;

import java.math.BigDecimal;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.entitys.loginaccount.RegisterRequest;


@Service
public interface RmiFacade {

    /**
     *兑换码使用
     * @param id
     * @param vcRedCode
     * @return
     */
    public BigDecimal useCDkey(Integer id, String vcRedCode) throws Exception;
    
    /**
     * 邮箱验证
     *
     * @param id
     * @param ac
     * @throws Exception
     */
    public void emailAuthorize(String ac) throws Exception;
    
    /**
     * 实名认证
     * @param id
     * @param ac
     * @throws Exception
     */
    public void realNameAuthorize(Integer id, String name, String cardCode) throws Exception;
   
    /**
     * wap,web 注册
     * @param registerRequest
     * @return
     * @throws Exception
     */
    public Loginaccount register(RegisterRequest registerRequest) throws Exception;
    
    /**
     * api注册
     */
    public Loginaccount apiRegister(RegisterRequest registerRequest) throws Exception;
    /**
     * 
    * @Title: registerRest 
    * @Description: 手机客户端注册用户  
    * @param @param bre
    * @param @param registerRequest
    * @param @throws Exception    
    * @return void
    * @see    
    * @throws
     */
    public Loginaccount registerRest(RegisterRequest registerRequest) throws Exception;

    /**
     * 易宝充值后-解析异步回调报文
     *
     * @param data
     * @param encryptkey
     * @return
     * @throws Exception
     */
    public Map<String, String> yeepayCallBackRecharge(String data, String encryptkey) throws Exception;
    
    /**
     * 连连充值后-解析异步回调报文
     * @param content 回调报文
     * @throws Exception
     */
    public void callBackRecharge(String content) throws Exception;
}
