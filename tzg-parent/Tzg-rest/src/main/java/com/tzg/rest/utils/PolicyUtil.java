package com.tzg.rest.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by wj.song on 2016/5/4 0004.
 */
public class PolicyUtil {

    private static Logger logger = LoggerFactory.getLogger(PolicyUtil.class);

    public static final String AES_KEY = "0987654321qazxcv";

    /***
     * 解密
     * @param policy
     * @return
     */
    public static String decryptPolicy(String policy) {
        try {
            if (StringUtils.isNotBlank(policy)) {
                logger.info("decryptPolicy decode before : " + policy);
                policy = URLDecoder.decode(policy, "UTF-8");
                logger.info("decryptPolicy decode after : " + policy);
                String result = AESUtil.decrypt(policy, AES_KEY);
                logger.info("decrypt policy : " + result);
                return result;
            }
        } catch (Exception e) {
            logger.error("decryptPolicy error : ", e);
        }
        return null;
    }

    /**
     * AES解密
     * @param policy
     * @return
     */
    public static String decryptAES(String policy){
        try {
            if (StringUtils.isNotBlank(policy)) {
                logger.info("decryptAES decode before : " + policy);
                String result = AESUtil.decrypt(policy, AES_KEY);
                logger.info("decrypt policy : " + result);
                return result;
            }
        } catch (Exception e) {
            logger.error("decryptPolicy error : ", e);
        }
        return null;
    }

    /***
     * 加密
     * @param policy
     * @return
     */
    public static String encryptPolicy(String policy) {
        try {
            if (StringUtils.isNotBlank(policy)) {
                return URLEncoder.encode(AESUtil.encrypt(policy, AES_KEY), "UTF-8");
            }
        } catch (Exception e) {
            logger.error("encryptPolicy error : ", e);
        }
        return null;
    }

    public static void main(String[] args) {

        String encryptStr = decryptPolicy("g%2FLBEh%2B3vndEGJK0PQ6W8lhhAgy%2BCV2cAFBwQaD87bmwrhsbzFKKBGeD7kK4SfIRrLJb5K3Sj9j94HI56ZbseCg%2F3o%2BBFi4Jl4%2BjnjacGD1V%2Fa171DRw51XQlnBQAQZsBu5JSSDZEMyOFlzAl6ivJKTRc6FrI9jCmDgL3uW4OasmA1dtMEGqa1OXnntdUcxCbo3uW1pN90%2BQED8Enl9Q7k%2B6YjPWj%2FEIw39XJRxSCn4TaobRoKw1xYEyciKUQChX");
       System.out.println(encryptStr);
//        Date date = new Date();
//        date.setTime(1453507200000L);
//        System.out.println(date);
//
        String str = "{\"userId\":\"1000110002\",\"schoolId\":\"10001\"}";
        String a = encryptPolicy(str);
        System.out.println(encryptPolicy(str));

        System.out.println(decryptAES(a));
    }
}
