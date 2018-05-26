package com.tzg.common.utils;

import java.math.BigDecimal;
import java.math.MathContext;

public class CalculatorUtil {
	
	/**
	 * 计算等额本息总利息
	 * 公式:
	 * 	还款总利息=还款总额-本金
	 */
	public static BigDecimal calcuMatchPrincipalTotalInterest(BigDecimal totalAmount, BigDecimal yearInterest, int months) {
		BigDecimal totalRepayment = calcuMatchPrincipalInterestTotal(totalAmount, yearInterest, months);
		BigDecimal totalInterest = totalRepayment.subtract(totalAmount); 
		return totalInterest;
	}
	
	/**
	 * 计算等额本息还款总额
	 * 公式:
	 * 	还款总额=每月应还本息*还款月数
	 */
	public static BigDecimal calcuMatchPrincipalInterestTotal(BigDecimal totalAmount, BigDecimal yearInterest, int months){
		BigDecimal monthlyPay = calcuMatchPrincipalInterest(totalAmount, yearInterest, months);
		BigDecimal totalRepayment = monthlyPay.multiply(new BigDecimal(months));
		return totalRepayment;
	}
	
	/**
	 * 计算等额本息每月还本付息金额
	 */
	public static BigDecimal calcuMatchPrincipalInterest(BigDecimal corpus, BigDecimal yearInterest, int months) {
		BigDecimal monthInterest = calcuMonthInterest(yearInterest);
		BigDecimal one = new BigDecimal(1);
		BigDecimal partOfCalcu = ((monthInterest.add(one)).pow(months));
		BigDecimal divisor = monthInterest.multiply(partOfCalcu);
		BigDecimal dividend = partOfCalcu.subtract(one);
		MathContext mc = new MathContext(12);
		return corpus.multiply((divisor.divide(dividend, mc)), mc);
	}
	
	/**
	 * 计算月利率 公式: 年利率/12
	 */
	public static BigDecimal calcuMonthInterest(BigDecimal yearInterest) {
		BigDecimal hundred = new BigDecimal(100);
		BigDecimal monthsOfYear = new BigDecimal(12);
		MathContext mContext = new MathContext(12);
		return yearInterest.divide(hundred, mContext).divide(monthsOfYear, mContext);
	}
	
}
