package com.tzg.entitys.subject;

import java.io.Serializable;

public class SubjectCountVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1157534541183895185L;

	private Integer count;

	private Integer waitingCount; // 待开标

	private Integer investingCount; // 投标中

	private Integer repayCount; // 还款中

	private Integer overCount; // 已结束

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getWaitingCount() {
		return waitingCount;
	}

	public void setWaitingCount(Integer waitingCount) {
		this.waitingCount = waitingCount;
	}

	public Integer getInvestingCount() {
		return investingCount;
	}

	public void setInvestingCount(Integer investingCount) {
		this.investingCount = investingCount;
	}

	public Integer getRepayCount() {
		return repayCount;
	}

	public void setRepayCount(Integer repayCount) {
		this.repayCount = repayCount;
	}

	public Integer getOverCount() {
		return overCount;
	}

	public void setOverCount(Integer overCount) {
		this.overCount = overCount;
	}

}
