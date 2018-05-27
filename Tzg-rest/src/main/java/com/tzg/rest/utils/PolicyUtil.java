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

     //   String encryptStr = decryptPolicy("EBhoQyk6p6p1aPSXxzwzi%252B3om%252FdERMY2NZZzjZTGtEGqsgWB%252FDyGz0asOw0LG%252Bk2ZXSCfnIsOxXF%250Al%252B0HSNdEwRgyaIKa8O0wxXzz%252FNOwNlViMRL7IwMlQwITcq2%252FvSq3yzeCAIX%252F8SVg2gcHbU%252B83Q%253D%253D%250A");
      // System.out.println(encryptStr);
//        Date date = new Date();
//        date.setTime(1453507200000L);
//        System.out.println(date);
//
        String str = "{\"phoneNumber\":\"15967158998\",\"password\":\"11111111\",\"dynamicVerifyCode\":\"488258\"}";
       // String str = "{\"phone\":\"15967158998\",\"module\":\"register\"}";

        String a = encryptPolicy(str);
        System.out.println(encryptPolicy(str));
//
//        System.out.println(decryptAES(a));
    }
}
