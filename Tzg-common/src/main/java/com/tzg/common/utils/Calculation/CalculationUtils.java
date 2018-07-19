package com.tzg.common.utils.Calculation;

import java.text.DecimalFormat;

public class CalculationUtils {
	//2数相除取余后取整
	public static Integer twoNumberDiv(int a,int b) {
		Integer resultc = 0;
		float num =(float)a/b;
        DecimalFormat df = new DecimalFormat("0.000");
        String result = df.format(num);
        String[] split = result.split("\\.");
        String str1 = split[1];
        if(!"000".equals(str1)) {
        	resultc = Integer.valueOf(split[0])+1;
        }else {
        	resultc = Integer.valueOf(split[0]);
        }
		return resultc;
	}
	
	public static void main(String[] args) {
		System.out.println(twoNumberDiv(699, 100));
	}
	
}
