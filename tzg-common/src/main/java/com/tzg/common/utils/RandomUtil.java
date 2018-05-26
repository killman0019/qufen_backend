package com.tzg.common.utils;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

/**
 *  @function:  产生随机数字、随机字母、随机数字+字母。
 */
public class RandomUtil {
     
    /**
     * 随机产生几位数字
     */
    public static final String produceNumber(int maxLength){
        return RandomStringUtils.randomNumeric(maxLength);
    }
     

    /**
     * 随机产生几位字符串：例：maxLength=3,则结果可能是 aAz
     * @param maxLength 传入数必须是正数。
     */
    public static String produceString(int maxLength){
        return RandomStringUtils.randomAlphabetic(maxLength);
    }
     
    /**
     * 随机产生随机数字+字母：例：maxLength=3,则结果可能是 1Az
     * @param maxLength 传入数必须是正数。
     */
    public static String produceStringAndNumber(int maxLength){
        return RandomStringUtils.randomAlphanumeric(maxLength);
    }
    
    /**
     * 随机int 在 min max 之间
     * @param min
     * @param max
     * @return
     */
    public static Integer randomNumber(int min,int max){
    	return new Random().nextInt(max+1-min)+min;
    }
    		
    		
    public static void main(String[] args) {
    	 //       System.out.println(RandomUtil.produceNumber(3));
    	 //       System.out.println(RandomUtil.produceString(3));
//    	for(int i =0; i < 800; i ++){
//    		System.out.print(RandomUtil.produceStringAndNumber(12) +"  \t");
//    		if(i % 20 ==0)
//    			System.out.println();
//    	}
//    	System.out.println(RandomStringUtils.randomAlphanumeric(12));
    }
}