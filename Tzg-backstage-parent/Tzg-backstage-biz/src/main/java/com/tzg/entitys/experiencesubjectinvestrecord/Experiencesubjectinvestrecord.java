package com.tzg.entitys.experiencesubjectinvestrecord;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;

import com.tzg.common.utils.DateUtil;

public class Experiencesubjectinvestrecord implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private java.lang.Integer id;
    /**
     * iloginAccountId
     */
    private java.lang.Integer iloginAccountId;
    /**
     * isubjectId
     */
    private java.lang.Integer isubjectId;
    /**
     * vcName
     */
    private java.lang.String  vcName;
    /**
     * numInterestRate
     */
    private BigDecimal        numInterestRate;
    /**
     * numRewardRate
     */
    private BigDecimal        numRewardRate;

    private BigDecimal        totalRate;
    /**
     * dtInvest
     */
    private java.util.Date    dtInvest;
    private java.lang.String  dtInvestStr;
    /**
     * numInvest
     */
    private BigDecimal        numInvest;
    /**
     * numActualExtraInterest
     */
    private BigDecimal        numActualExtraInterest;
    /**
     * numActualInterest
     */
    private BigDecimal        numActualInterest;
    /**
     * dtRepay
     */
    private java.util.Date    dtRepay;
    private java.lang.String  dtRepayStr;

    /**
     * numPeriod
     */
    private Integer           numPeriod;

    /**
     * 1--处理中(与第三方交互中)； 2--投资中（第三方处理成功）； 3--投资失败（第三方处理失败）； 4、回款中； 5、回款结束。
     */
    private java.lang.Integer istate;
    /**
     * 该条记录第一次创建的时间。
     */
    private java.util.Date    dtCreate;
    private java.lang.String  dtCreateStr;
    /**
     * 可选值： pc wap ios android auto ------------- ios和android是指手机app auto -- 自动投标
     */
    private java.lang.String  vcInvestDevice;
    /**
     * 1 -- 到期还本付息； 2 -- 每日付息到期还本； 3 -- 每月付息到期还本； 4 -- 等额本息。
     */
    private java.lang.Integer irepayMode;
    /**
     * 计划的募集结束时间。非实际募集结束时间
     */
    private java.util.Date    dtCollectEnd;
    private java.lang.String  dtCollectEndStr;
    /**
     * 标的名称+主键（格式化成11位）
     */
    private java.lang.String  vcContractNo;

    public Integer getNumPeriod() {
        if (dtRepay != null && dtCollectEnd != null) {
            try {
                this.numPeriod = DateUtil.getMonthSpace(dtRepay, dtCollectEnd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return numPeriod;
    }

    public void setNumPeriod(Integer numPeriod) {
        this.numPeriod = numPeriod;
    }

    public BigDecimal getTotalRate() {
        this.totalRate = numInterestRate.add(numRewardRate);
        return totalRate;
    }

    public void setTotalRate(BigDecimal totalRate) {
        this.totalRate = totalRate;
    }

    public void setId(java.lang.Integer value) {
        this.id = value;
    }

    public java.lang.Integer getId() {
        return this.id;
    }

    public void setIloginAccountId(java.lang.Integer value) {
        this.iloginAccountId = value;
    }

    public java.lang.Integer getIloginAccountId() {
        return this.iloginAccountId;
    }

    public void setIsubjectId(java.lang.Integer value) {
        this.isubjectId = value;
    }

    public java.lang.Integer getIsubjectId() {
        return this.isubjectId;
    }

    public void setVcName(java.lang.String value) {
        this.vcName = value;
    }

    public java.lang.String getVcName() {
        return this.vcName;
    }

    public void setNumInterestRate(BigDecimal value) {
        this.numInterestRate = value;
    }

    public BigDecimal getNumInterestRate() {
        return this.numInterestRate;
    }

    public void setNumRewardRate(BigDecimal value) {
        this.numRewardRate = value;
    }

    public BigDecimal getNumRewardRate() {
        return this.numRewardRate;
    }

    public void setDtInvest(java.util.Date value) {
        this.dtInvestStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
        this.dtInvest = value;
    }

    public java.util.Date getDtInvest() {
        return this.dtInvest;
    }

    public java.lang.String getDtInvestStr() {
        return this.dtInvestStr;
    }

    public void setNumInvest(BigDecimal value) {
        this.numInvest = value;
    }

    public BigDecimal getNumInvest() {
        return this.numInvest;
    }

    public void setNumActualExtraInterest(BigDecimal value) {
        this.numActualExtraInterest = value;
    }

    public BigDecimal getNumActualExtraInterest() {
        return this.numActualExtraInterest;
    }

    public void setNumActualInterest(BigDecimal value) {
        this.numActualInterest = value;
    }

    public BigDecimal getNumActualInterest() {
        return this.numActualInterest;
    }

    public void setDtRepay(java.util.Date value) {
        this.dtRepayStr = DateUtil.getDate(value, "yyyy-MM-dd");
        this.dtRepay = value;
    }

    public java.util.Date getDtRepay() {
        return this.dtRepay;
    }

    public java.lang.String getDtRepayStr() {
        return this.dtRepayStr;
    }

    public void setIstate(java.lang.Integer value) {
        this.istate = value;
    }

    public java.lang.Integer getIstate() {
        return this.istate;
    }

    public void setDtCreate(java.util.Date value) {
        this.dtCreateStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
        this.dtCreate = value;
    }

    public java.util.Date getDtCreate() {
        return this.dtCreate;
    }

    public java.lang.String getDtCreateStr() {
        return this.dtCreateStr;
    }

    public void setVcInvestDevice(java.lang.String value) {
        this.vcInvestDevice = value;
    }

    public java.lang.String getVcInvestDevice() {
        return this.vcInvestDevice;
    }

    public void setIrepayMode(java.lang.Integer value) {
        this.irepayMode = value;
    }

    public java.lang.Integer getIrepayMode() {
        return this.irepayMode;
    }

    public void setDtCollectEnd(java.util.Date value) {
        this.dtCollectEndStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
        this.dtCollectEnd = value;
    }

    public java.util.Date getDtCollectEnd() {
        return this.dtCollectEnd;
    }

    public java.lang.String getDtCollectEndStr() {
        return this.dtCollectEndStr;
    }

    public void setVcContractNo(java.lang.String value) {
        this.vcContractNo = value;
    }

    public java.lang.String getVcContractNo() {
        return this.vcContractNo;
    }

}
