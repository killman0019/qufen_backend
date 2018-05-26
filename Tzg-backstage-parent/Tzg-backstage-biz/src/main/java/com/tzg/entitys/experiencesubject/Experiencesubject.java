package com.tzg.entitys.experiencesubject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.Numbers;

public class Experiencesubject implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Integer           id;
    /**
     * vcName
     */
    private String            vcName;
    /**
     * vcShortDesp
     */
    private String            vcShortDesp;
    /**
     * vcAwardsDesp
     */
    private String            vcAwardsDesp;
    /**
     * numInterestRate
     */
    private BigDecimal        numInterestRate;
    /**
     * numRewardRate
     */
    private BigDecimal        numRewardRate;

    private String            totalRate;

    /**
     * iquota
     */
    private Integer           iquota;
    private Integer           iactualNum;
    /**
     * numInvest
     */
    private BigDecimal        numInvest;
    /**
     * dtTrailer
     */
    private Date              dtTrailer;
    private String            dtTrailerStr;
    /**
     * dtCollectStart
     */
    private Date              dtCollectStart;
    private String            dtCollectStartStr;
    /**
     * dtCollectEnd
     */
    private Date              dtCollectEnd;
    private String            dtCollectEndStr;
    /**
     * numPeriod
     */
    private Integer           numPeriod;

    /**
     * dtActualCollectEnd
     */
    private Date              dtActualCollectEnd;
    private String            dtActualCollectEndStr;
    /**
     * dtRepayment
     */
    private java.util.Date    dtRepayment;
    private java.lang.String  dtRepaymentStr;
    /**
     * 1-- 新录入 2-- 审核中 3-- 退回 4-- 终止 5-- 审核通过 6-- 待预告 -----------与第三方交互 7--处理中(与第三方交互中)； 8--处理失败（第三方处理失败）； 9--处理成功
     * ----------- 10-- 待开标 11-- 投标中 12-- 还款中 13-- 结束 14--投标结束，等待划款
     */
    private Integer           istate;
    /**
     * icontractId
     */
    private Integer           icontractId;
    /**
     * 该条记录第一次创建的时间。
     */
    private Date              dtCreate;
    private String            dtCreateStr;
    /**
     * vcCurrentFlow
     */
    private String            vcCurrentFlow;
    /**
     * ApprovalRating主键;ApprovalRating主键;ApprovalRating主键
     */
    private String            vcApprovalFlow;
    /**
     * 1 -- 到期还本付息； 2 -- 每日付息到期还本； 3 -- 每月付息到期还本； 4 -- 等额本息。
     */
    private Integer           irepayMode;
    /**
     * 1-新客 2-老客 可以多选，用“;”隔开
     */
    private String            iaccountType;
    /**
     * numInterest
     */
    private BigDecimal        numInterest;

    private BigDecimal        numTotalFinancing;
    private String            numTotalFinancingStr;

    /**
     * 可投金额
     */
    private BigDecimal        canAmount;

    /**
     * 募集进度 百分比
     */
    private int               percent;

    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return this.id;
    }

    public void setVcName(String value) {
        this.vcName = value;
    }

    public String getVcName() {
        return this.vcName;
    }

    public void setVcShortDesp(String value) {
        this.vcShortDesp = value;
    }

    public String getVcShortDesp() {
        return this.vcShortDesp;
    }

    public void setVcAwardsDesp(String value) {
        this.vcAwardsDesp = value;
    }

    public String getVcAwardsDesp() {
        return this.vcAwardsDesp;
    }

    public void setNumInterestRate(BigDecimal value) {
        this.numInterestRate = value;
    }

    public String getTotalRate() {
        this.totalRate = Numbers.add(numInterestRate, numRewardRate).toString();
        return totalRate;
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
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

    public void setIquota(Integer value) {
        this.iquota = value;
    }

    public Integer getIquota() {
        return this.iquota;
    }

    public Integer getIactualNum() {
        return iactualNum;
    }

    public void setIactualNum(Integer iactualNum) {
        this.iactualNum = iactualNum;
    }

    public void setNumInvest(BigDecimal value) {
        this.numInvest = value;
    }

    public BigDecimal getNumInvest() {
        return this.numInvest;
    }

    public void setDtTrailer(Date value) {
        this.dtTrailerStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
        this.dtTrailer = value;
    }

    public Date getDtTrailer() {
        return this.dtTrailer;
    }

    public String getDtTrailerStr() {
        return this.dtTrailerStr;
    }

    public java.lang.Integer getNumPeriod() {
        return numPeriod;
    }

    public void setNumPeriod(java.lang.Integer numPeriod) {
        this.numPeriod = numPeriod;
    }

    public void setDtCollectStart(Date value) {
        this.dtCollectStartStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
        this.dtCollectStart = value;
    }

    public Date getDtCollectStart() {
        return this.dtCollectStart;
    }

    public String getDtCollectStartStr() {
        return this.dtCollectStartStr;
    }

    public void setDtCollectEnd(Date value) {
        this.dtCollectEndStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
        this.dtCollectEnd = value;
    }

    public Date getDtCollectEnd() {
        return this.dtCollectEnd;
    }

    public String getDtCollectEndStr() {
        return this.dtCollectEndStr;
    }

    public void setDtActualCollectEnd(Date value) {
        this.dtActualCollectEndStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
        this.dtActualCollectEnd = value;
    }

    public Date getDtActualCollectEnd() {
        return this.dtActualCollectEnd;
    }

    public String getDtActualCollectEndStr() {
        return this.dtActualCollectEndStr;
    }

    public Date getDtRepayment() {
        return dtRepayment;
    }

    public void setDtRepayment(java.util.Date value) {
        this.dtRepaymentStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
        this.dtRepayment = value;
    }

    public void setIstate(Integer value) {
        this.istate = value;
    }

    public Integer getIstate() {
        return this.istate;
    }

    public void setIcontractId(Integer value) {
        this.icontractId = value;
    }

    public Integer getIcontractId() {
        return this.icontractId;
    }

    public void setDtCreate(Date value) {
        this.dtCreateStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
        this.dtCreate = value;
    }

    public Date getDtCreate() {
        return this.dtCreate;
    }

    public String getDtCreateStr() {
        return this.dtCreateStr;
    }

    public void setVcCurrentFlow(String value) {
        this.vcCurrentFlow = value;
    }

    public String getVcCurrentFlow() {
        return this.vcCurrentFlow;
    }

    public void setVcApprovalFlow(String value) {
        this.vcApprovalFlow = value;
    }

    public String getVcApprovalFlow() {
        return this.vcApprovalFlow;
    }

    public void setIrepayMode(Integer value) {
        this.irepayMode = value;
    }

    public Integer getIrepayMode() {
        return this.irepayMode;
    }

    public void setIaccountType(String value) {
        this.iaccountType = value;
    }

    public String getIaccountType() {
        return this.iaccountType;
    }

    public void setNumInterest(BigDecimal value) {
        this.numInterest = value;
    }

    public BigDecimal getNumInterest() {
        return this.numInterest;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setDtTrailerStr(java.lang.String dtTrailerStr) {
        this.dtTrailer = DateUtil.getDate(dtTrailerStr, "yyyy-MM-dd HH:mm:ss");
        this.dtTrailerStr = dtTrailerStr;
    }

    public void setDtCollectStartStr(java.lang.String dtCollectStartStr) {
        this.dtCollectStart = DateUtil.getDate(dtCollectStartStr, "yyyy-MM-dd HH:mm:ss");
        this.dtCollectStartStr = dtCollectStartStr;
    }

    public void setDtCollectEndStr(java.lang.String dtCollectEndStr) {
        this.dtCollectEnd = DateUtil.getDate(dtCollectEndStr, "yyyy-MM-dd HH:mm:ss");
        this.dtCollectEndStr = dtCollectEndStr;
    }

    public void setDtRepaymentStr(java.lang.String dtRepaymentStr) {
        this.dtRepayment = DateUtil.getDate(dtRepaymentStr, "yyyy-MM-dd");
        this.dtRepaymentStr = dtRepaymentStr;
    }

    public java.lang.String getDtRepaymentStr() {
        if (this.dtRepayment != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            this.dtRepaymentStr = format.format(this.dtRepayment);
        }
        return this.dtRepaymentStr;
    }

    public BigDecimal getNumTotalFinancing() {
        this.numTotalFinancing = numInvest.multiply(new BigDecimal(iquota));
        return numTotalFinancing;
    }

    public void setNumTotalFinancing(BigDecimal numTotalFinancing) {
        this.numTotalFinancing = numTotalFinancing;
    }

    public String getNumTotalFinancingStr() {
        this.numTotalFinancingStr = Numbers.formatWan(this.getNumTotalFinancing());
        return numTotalFinancingStr;
    }

    public void setNumTotalFinancingStr(String numTotalFinancingStr) {
        this.numTotalFinancingStr = numTotalFinancingStr;
    }

    public BigDecimal getCanAmount() {
        if (this.iquota != null) {
            if (this.iactualNum != null) {
                int peopleNum = iquota.intValue() - iactualNum.intValue();
                this.canAmount = this.numInvest.multiply(new BigDecimal(peopleNum));
            } else {
                this.canAmount = this.numInvest.multiply(new BigDecimal(iquota.intValue()));
            }
        }
        return canAmount;
    }

    public void setCanAmount(BigDecimal canAmount) {
        this.canAmount = canAmount;
    }

}
