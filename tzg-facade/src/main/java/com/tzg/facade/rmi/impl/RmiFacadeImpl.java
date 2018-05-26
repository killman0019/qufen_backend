/**  
 * Filename:    RmiFacadeImpl.java  
 * Description:   
 * Copyright:   Copyright (c) 2015-2018 All Rights Reserved.
 * Company:     tzg.cn Inc.
 * @author:     wangk 
 * @version:    1.0  
 * Create at:   2015年12月17日 上午9:02:22  
 *  
 * Modification History:  
 *	Date				Author      Version     Description  
 * ------------------------------------------------------------------  
 *	2015年12月17日	    wangk      1.0         1.0 Version  
 *
*/

package com.tzg.facade.rmi.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.utils.DigestUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.facade.rmi.RmiFacade;
import com.tzg.facade.rocketMq.RocketMqFacade;


@Service("rmiFacade")
public class RmiFacadeImpl implements RmiFacade {

    
    @Autowired
    private RocketMqFacade         rocketMqFacade;

    @Override
    public BigDecimal useCDkey(Integer loginid, String vcRedCode) throws Exception {
        BigDecimal redAmt = BigDecimal.ZERO;
        //发送红包发消息
        if (redAmt.compareTo(BigDecimal.ZERO) > 0) {
            rocketMqFacade.redSendSuccessNtf(loginid, redAmt, "兑换获得");
        }
        return redAmt;
    }

    @Override
    public void emailAuthorize(String ac) throws Exception {
        BigDecimal redAmt = BigDecimal.ZERO;
        //发送红包发消息
        if (redAmt.compareTo(BigDecimal.ZERO) > 0) {
            String token = new String(DigestUtil.decryptAES(DigestUtil.hex2byte(ac,TzgConstant.DEFAULT_CHARSET), TzgConstant.EMAIL_TOKEN_PWD),TzgConstant.DEFAULT_CHARSET);
            Integer accountId = Integer.valueOf(token.split("\\|")[0]);
            rocketMqFacade.redSendSuccessNtf(accountId, redAmt, "邮箱认证获得");
        }
    }
    
    @Override
    public void realNameAuthorize(Integer id, String name, String cardCode) throws Exception {
        BigDecimal redAmt = BigDecimal.ZERO;
      //发送红包发消息
        if (redAmt.compareTo(BigDecimal.ZERO) > 0) {
       //     rocketMqFacade.redSendSuccessNtf(id, redAmt, "实名认证获得");
        }
    }
    
    @Override
    public Loginaccount register(RegisterRequest registerRequest) throws Exception{
        Loginaccount account= null;
        //注册完成发消息给人人赚，同步人人赚的账号
        //pyramidFacade.registerSendMsg(account);
        rocketMqFacade.registerSuccessNtf(account.getId());
        return account;
    }
    
    @Override
    public Loginaccount registerRest(RegisterRequest registerRequest) throws Exception {
        Loginaccount account=  null;
        //注册完成发消息给人人赚，同步人人赚的账号
        //pyramidFacade.registerSendMsg(account);
       // rocketMqFacade.registerSuccessNtf(account.getId());
        return account;
    }
    
    @Override
    public Loginaccount apiRegister(RegisterRequest registerRequest) throws Exception {
        Loginaccount account = null;
        //注册完成发消息给人人赚，同步人人赚的账号
       // pyramidFacade.registerSendMsg(account);
       // rocketMqFacade.registerSuccessNtf(account.getId());
        return account;
    }
    
    @Override
    public Map<String, String> yeepayCallBackRecharge(String data, String encryptkey) throws Exception {
        Map<String, String> map= new HashMap<String,String>();
//        Recharge recharge =new Recharge(); 
//        if(recharge.getIstate()==2){//成功
//          rocketMqFacade.rechargeSuccessNtf(recharge.getIloginAccountId(),recharge.getId(),recharge.getNumRecharge());
//        }
        return map;
    }
    
    /**
     * 充值后-解析异步回调报文
     * @param content 回调报文
     * @throws Exception
     */
    public void callBackRecharge(String content) throws Exception{
        String billCode="";
//        Recharge recharge =new Recharge(); 
//        if(recharge.getIstate()==2){//成功
//          rocketMqFacade.rechargeSuccessNtf(recharge.getIloginAccountId(),recharge.getId(),recharge.getNumRecharge());
//        }
    }
}
