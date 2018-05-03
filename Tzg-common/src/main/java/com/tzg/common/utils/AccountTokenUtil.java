package com.tzg.common.utils;

import javax.servlet.http.HttpServletRequest;
import org.jfree.util.Log;
import com.tzg.common.bbs.util.Base64;

public class AccountTokenUtil {
	
    /**
     * request app解码
     */
    
    public static Integer getRequestToken(HttpServletRequest request)  throws Exception {
        String accountToken = request.getHeader("token");
        Assert.isNull(accountToken, "解码失败 request.token为空");
        Integer iloginId = null;
        if (StringUtil.isNotEmpty(accountToken)) {
            String[] keys = accountToken.split("@");
            if (accountToken.split("@").length >= 1) {
                iloginId = decodeAccountToken(keys[0]);
            }
        }
        return iloginId;
        
    }
	/**
	 * @Description: 根据用户id生成token
	 * @param loginaccountId
	 * @return String
	 */
	public static String getAccountToken(Integer loginaccountId) {
		if (null == loginaccountId) {
			return "";
		}
		String time = String.valueOf(System.currentTimeMillis());
		String newkey = MD5Util.md5Hex(String.valueOf(loginaccountId) + time + TzgConstant.LOGIN_SIGN_KEY);
		String token = String.valueOf(loginaccountId) + ":" + time + ":" + newkey;
		token = com.tzg.common.bbs.util.Base64.base64_encode(token);
		return token;
	}
	
	/**
	 * 
	* @Description: 解码token获得用户id
	* @param token
	* @return    
	* String
	 */
	public static Integer decodeAccountToken(String token) {
		Integer loginAccountId=null;
		try {
			Assert.isBlank(token, "登录帐号为空");
			String[] str=Base64.base64_decode(token).split(":");
			Assert.isBlank(str[0], "登录帐号为空");
			Assert.isBlank(str[1], "登录时间为空");
			Assert.isBlank(str[2], "key为空");
			String newkey=MD5Util.md5Hex(str[0]+str[1]+TzgConstant.LOGIN_SIGN_KEY);
			if(str[2].equals(newkey)){
				loginAccountId = Integer.parseInt(str[0]);
			}
		} catch (Exception e) {
		    Log.error(e.getMessage());
		}
		return loginAccountId;
	}
	
	   /**
     * @Description: 根据用户vcPhone生成token
     * @param vcphone
     * @return String
     */
    public static String getAccountPhoneToken(String vcPhone) {
        if (StringUtil.isBlank(vcPhone)) {
            return "";
        }
        String time = String.valueOf(System.currentTimeMillis());
        String newkey = MD5Util.md5Hex(vcPhone + time + TzgConstant.LOGIN_SIGN_KEY);
        String token = vcPhone + ":" + time + ":" + newkey;
        token = com.tzg.common.bbs.util.Base64.base64_encode(token);
        return token;
    }
    
    /**
     * 
    * @Description: 解码token获得用户vcPhone
    * @param token
    * @return    
    * String
     */
    public static String decodeAccountPhoneToken(String token) {
        String vcPhone=null;
        try {
            Assert.isBlank(token, "手机号为空");
            String[] str=Base64.base64_decode(token).split(":");
            Assert.isBlank(str[0], "手机号为空");
            Assert.isBlank(str[1], "登录时间为空");
            Assert.isBlank(str[2], "key为空");
            String newkey=MD5Util.md5Hex(str[0]+str[1]+TzgConstant.LOGIN_SIGN_KEY);
            if(str[2].equals(newkey)){
                vcPhone = str[0];
            }
        } catch (Exception e) {
            Log.error(e.getMessage());
        }
        return vcPhone;
    }
	
	public static void main(String[] args) {
        System.out.println(decodeAccountPhoneToken(getAccountPhoneToken("15868802663")));
    }
}
