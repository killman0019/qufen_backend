package com.tzg.entitys.platformaccount;

import java.io.Serializable;
import java.math.BigDecimal;

public class Platformaccount implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private java.lang.Integer id;

	private Integer iloginAccountID;
	private String iloginAccountName;
	/**
	 * numAvailable
	 */
	private BigDecimal numAvailable;
	/**
	 * numTotalCash
	 */
	private BigDecimal numTotalCash;
	/**
	 * numTotalRecharge
	 */
	private BigDecimal numTotalRecharge;
	/**
	 * 后台提现总额
	 */
	private BigDecimal numBackTotalCash;
	/**
	 * 后台充值总额
	 */
	private BigDecimal numBackTotalRecharge;
	/**
	 * 后台账户余额
	 */
	private BigDecimal numBackAvailable;
	/**
	 *  充值手续费总支出 平台用户充值时，第三方收取平台的手续费。
	 */
	private BigDecimal numTotalFeeCast;
	/**
	 * numTotalRedCast
	 */
	private BigDecimal numTotalRedCast;
	/**
	 * numExperienceSubjectFee
	 */
	private BigDecimal numExperienceSubjectFee;
	/**
	 * numTotalCouponCast
	 */
	private BigDecimal numTotalCouponCast;
	/**
	 * numTotalServiceFee
	 */
	private BigDecimal numTotalServiceFee;
	/**
	 * numTotalExpenseIncome
	 */
	private BigDecimal numTotalExpenseIncome;
	/**
	 * 平台用户提现时，平台收取的提现手续费
	 */
	private BigDecimal numTotalFeeIncome;
	/**
	 * numTotalBondIncome
	 */
	private BigDecimal numTotalBondIncome;
	/**
	 * numTotalBondRepaid
	 */
	private BigDecimal numTotalBondRepaid;
	/**
	 * numTotalBondCast
	 */
	private BigDecimal numTotalBondCast;
	/**
	 * 1 -- 有效； 2 -- 冻结中； 3 -- 无效。
	 */
	private java.lang.Integer istatus;
	
	/**
	 *  第三方提现手续费总支出  平台用户提现时，第三方收取平台的手续费
	 */
	private BigDecimal numTotalFeeCash;
	/**
	 * 累计贴现总额
	 */
	private BigDecimal numTotalDiscount;
	
	private BigDecimal numInterestIntervalAmtEarn;
	
	private BigDecimal numInterestIntervalAmtLose;
	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public Integer getIloginAccountID() {
		return iloginAccountID;
	}

	public void setIloginAccountID(Integer iloginAccountID) {
		this.iloginAccountID = iloginAccountID;
	}

	public void setNumAvailable(BigDecimal value) {
		this.numAvailable = value;
	}

	public BigDecimal getNumAvailable() {
		return this.numAvailable;
	}

	public void setNumTotalCash(BigDecimal value) {
		this.numTotalCash = value;
	}

	public BigDecimal getNumTotalCash() {
		return this.numTotalCash;
	}

	public void setNumTotalRecharge(BigDecimal value) {
		this.numTotalRecharge = value;
	}

	public BigDecimal getNumTotalRecharge() {
		return this.numTotalRecharge;
	}

	public void setNumTotalFeeCast(BigDecimal value) {
		this.numTotalFeeCast = value;
	}

	public BigDecimal getNumBackTotalCash() {
		return numBackTotalCash;
	}

	public void setNumBackTotalCash(BigDecimal numBackTotalCash) {
		this.numBackTotalCash = numBackTotalCash;
	}

	public BigDecimal getNumBackTotalRecharge() {
		return numBackTotalRecharge;
	}

	public void setNumBackTotalRecharge(BigDecimal numBackTotalRecharge) {
		this.numBackTotalRecharge = numBackTotalRecharge;
	}

	public BigDecimal getNumBackAvailable() {
		return numBackAvailable;
	}

	public void setNumBackAvailable(BigDecimal numBackAvailable) {
		this.numBackAvailable = numBackAvailable;
	}

	public BigDecimal getNumTotalFeeCast() {
		return this.numTotalFeeCast;
	}

	public void setNumTotalRedCast(BigDecimal value) {
		this.numTotalRedCast = value;
	}

	public BigDecimal getNumTotalRedCast() {
		return this.numTotalRedCast;
	}

	public BigDecimal getNumExperienceSubjectFee() {
		return numExperienceSubjectFee;
	}

	public void setNumExperienceSubjectFee(BigDecimal numExperienceSubjectFee) {
		this.numExperienceSubjectFee = numExperienceSubjectFee;
	}

	public void setNumTotalCouponCast(BigDecimal value) {
		this.numTotalCouponCast = value;
	}

	public BigDecimal getNumTotalCouponCast() {
		return this.numTotalCouponCast;
	}

	public void setNumTotalServiceFee(BigDecimal value) {
		this.numTotalServiceFee = value;
	}

	public BigDecimal getNumTotalServiceFee() {
		return this.numTotalServiceFee;
	}

	public void setNumTotalExpenseIncome(BigDecimal value) {
		this.numTotalExpenseIncome = value;
	}

	public BigDecimal getNumTotalExpenseIncome() {
		return this.numTotalExpenseIncome;
	}

	public void setNumTotalFeeIncome(BigDecimal value) {
		this.numTotalFeeIncome = value;
	}

	public BigDecimal getNumTotalFeeIncome() {
		return this.numTotalFeeIncome;
	}

	public void setNumTotalBondIncome(BigDecimal value) {
		this.numTotalBondIncome = value;
	}

	public BigDecimal getNumTotalBondIncome() {
		return this.numTotalBondIncome;
	}

	public void setNumTotalBondRepaid(BigDecimal value) {
		this.numTotalBondRepaid = value;
	}

	public BigDecimal getNumTotalBondRepaid() {
		return this.numTotalBondRepaid;
	}

	public void setNumTotalBondCast(BigDecimal value) {
		this.numTotalBondCast = value;
	}

	public BigDecimal getNumTotalBondCast() {
		return this.numTotalBondCast;
	}

	public void setIstatus(java.lang.Integer value) {
		this.istatus = value;
	}

	public java.lang.Integer getIstatus() {
		return this.istatus;
	}

	public String getIloginAccountName() {
		return iloginAccountName;
	}

	public void setIloginAccountName(String iloginAccountName) {
		this.iloginAccountName = iloginAccountName;
	}

	public BigDecimal getNumTotalFeeCash() {
		return numTotalFeeCash;
	}

	public void setNumTotalFeeCash(BigDecimal numTotalFeeCash) {
		this.numTotalFeeCash = numTotalFeeCash;
	}

	public BigDecimal getNumTotalDiscount() {
		return numTotalDiscount;
	}

	public void setNumTotalDiscount(BigDecimal numTotalDiscount) {
		this.numTotalDiscount = numTotalDiscount;
	}

	public BigDecimal getNumInterestIntervalAmtEarn() {
		return numInterestIntervalAmtEarn;
	}

	public void setNumInterestIntervalAmtEarn(BigDecimal numInterestIntervalAmtEarn) {
		this.numInterestIntervalAmtEarn = numInterestIntervalAmtEarn;
	}

	public BigDecimal getNumInterestIntervalAmtLose() {
		return numInterestIntervalAmtLose;
	}

	public void setNumInterestIntervalAmtLose(BigDecimal numInterestIntervalAmtLose) {
		this.numInterestIntervalAmtLose = numInterestIntervalAmtLose;
	}
	
	

}
