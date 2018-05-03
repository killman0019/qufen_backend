package com.tzg.rest.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class IncomeObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private String date;
	private String  month;
	private String day;
	private int index;
	private BigDecimal income;
	
	
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
}