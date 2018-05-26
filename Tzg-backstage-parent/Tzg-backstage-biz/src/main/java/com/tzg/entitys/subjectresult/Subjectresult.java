package com.tzg.entitys.subjectresult;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class Subjectresult implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * [主键]
     */ 	
	private java.lang.Integer id;
    /**
     * [标的id]
     */ 	
	private java.lang.Integer isubjectId;
    /**
     * [募集结束时间]
     */ 	
	private java.util.Date dtCollectEnd;
	private java.lang.String dtCollectEndStr;
    /**
     * [实际募集金额]
     */ 	
	private BigDecimal numActualFinancing;
    /**
     * [募集期利息（包含多财）]
     */ 	
	private BigDecimal numCollectInterest;
    /**
     * [利息（包含多财）]
     */ 	
	private BigDecimal numInterest;
    /**
     * [协议募集期利息]
     */ 	
	private BigDecimal numAgreementCollectInterest;
    /**
     * [协议利息]
     */ 	
	private BigDecimal numAgreementInterest;
    /**
     * [额外募集期利息(利息券）]
     */ 	
	private BigDecimal numExtraCollectInterest;
    /**
     * [额外利息(利息券）
     */ 	
	private BigDecimal numExtraInterest;
    /**
     * [累计红包]
     */ 	
	private BigDecimal numTotalAwards;
    /**
     * [多财贴息-赚]
     */ 	
	private BigDecimal numMminterestEarn;
    /**
     * [多财贴息-亏]
     */ 	
	private BigDecimal numMminterestLose;
    /**
     * [创建时间]
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
	
	/**
     * 标的名称
     */ 	
	private java.lang.String vcName;
	
	private java.lang.String projectName;
	
	 /**
     * 预计年利率
     */ 	
	private BigDecimal numInterestRate = BigDecimal.ZERO;
    /**
     * 奖励年利率
     */ 	
	private BigDecimal numRewardRate = BigDecimal.ZERO;
	
	/**
	 * 基准利率
	 */
	private BigDecimal totalRate = BigDecimal.ZERO;
	
	 /**
     * 融资金额
     */ 	
	private BigDecimal numTotalFinancing;
	 /**
     * 1-- 新录入             2-- 待审核             3-- 退回             4--作废             5-- 审核通过             6-- 待预告             -----------与第三方交互             7--处理中(与第三方交互中)；             8--处理失败（第三方处理失败）；             9。处理成功             -----------             9-- 待开标             10-- 投标中             11-- 还款中             12-- 结束             
     */ 	
	private java.lang.Integer istate;
	
	private java.lang.Integer iInterestConfigId;
	  

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIsubjectId(java.lang.Integer value) {
		this.isubjectId = value;
	}
	
	public java.lang.Integer getIsubjectId() {
		return this.isubjectId;
	}
	
	public void setDtCollectEnd(java.util.Date value) {
		this.dtCollectEndStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtCollectEnd = value;
	}
	
	public java.util.Date getDtCollectEnd() {
		return this.dtCollectEnd;
	}
	
	public java.lang.String getDtCollectEndStr() {
		return this.dtCollectEndStr;
	}
	
	public void setNumActualFinancing(BigDecimal value) {
		this.numActualFinancing = value;
	}
	
	public BigDecimal getNumActualFinancing() {
		return this.numActualFinancing;
	}
	
	public void setNumCollectInterest(BigDecimal value) {
		this.numCollectInterest = value;
	}
	
	public BigDecimal getNumCollectInterest() {
		return this.numCollectInterest;
	}
	
	public void setNumInterest(BigDecimal value) {
		this.numInterest = value;
	}
	
	public BigDecimal getNumInterest() {
		return this.numInterest;
	}
	
	public void setNumAgreementCollectInterest(BigDecimal value) {
		this.numAgreementCollectInterest = value;
	}
	
	public BigDecimal getNumAgreementCollectInterest() {
		return this.numAgreementCollectInterest;
	}
	
	public void setNumAgreementInterest(BigDecimal value) {
		this.numAgreementInterest = value;
	}
	
	public BigDecimal getNumAgreementInterest() {
		return this.numAgreementInterest;
	}
	
	public void setNumExtraCollectInterest(BigDecimal value) {
		this.numExtraCollectInterest = value;
	}
	
	public BigDecimal getNumExtraCollectInterest() {
		return this.numExtraCollectInterest;
	}
	
	public void setNumExtraInterest(BigDecimal value) {
		this.numExtraInterest = value;
	}
	
	public BigDecimal getNumExtraInterest() {
		return this.numExtraInterest;
	}
	
	public void setNumTotalAwards(BigDecimal value) {
		this.numTotalAwards = value;
	}
	
	public BigDecimal getNumTotalAwards() {
		return this.numTotalAwards;
	}
	
	public void setNumMminterestEarn(BigDecimal value) {
		this.numMminterestEarn = value;
	}
	
	public BigDecimal getNumMminterestEarn() {
		return this.numMminterestEarn;
	}
	
	public void setNumMminterestLose(BigDecimal value) {
		this.numMminterestLose = value;
	}
	
	public BigDecimal getNumMminterestLose() {
		return this.numMminterestLose;
	}
	
	public void setDtCreate(java.util.Date value) {
		this.dtCreateStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtCreate = value;
	}
	
	public java.util.Date getDtCreate() {
		return this.dtCreate;
	}
	
	public java.lang.String getDtCreateStr() {
		return this.dtCreateStr;
	}

	public java.lang.String getVcName() {
		return vcName;
	}

	public void setVcName(java.lang.String vcName) {
		this.vcName = vcName;
	}

	public BigDecimal getNumInterestRate() {
		return numInterestRate;
	}

	public void setNumInterestRate(BigDecimal numInterestRate) {
		this.numInterestRate = numInterestRate;
	}

	public BigDecimal getNumRewardRate() {
		return numRewardRate;
	}

	public void setNumRewardRate(BigDecimal numRewardRate) {
		this.numRewardRate = numRewardRate;
	}

	public BigDecimal getTotalRate() {
		this.totalRate = this.numInterestRate.add(this.numRewardRate);
		return totalRate;
	}

	public void setTotalRate(BigDecimal totalRate) {
		this.totalRate = totalRate;
	}

	public BigDecimal getNumTotalFinancing() {
		return numTotalFinancing;
	}

	public void setNumTotalFinancing(BigDecimal numTotalFinancing) {
		this.numTotalFinancing = numTotalFinancing;
	}

	public java.lang.Integer getIstate() {
		return istate;
	}

	public void setIstate(java.lang.Integer istate) {
		this.istate = istate;
	}

	public java.lang.Integer getiInterestConfigId() {
		return iInterestConfigId;
	}

	public void setiInterestConfigId(java.lang.Integer iInterestConfigId) {
		this.iInterestConfigId = iInterestConfigId;
	}

	public java.lang.String getProjectName() {
		return projectName;
	}

	public void setProjectName(java.lang.String projectName) {
		this.projectName = projectName;
	}
	
	
	

	
}

