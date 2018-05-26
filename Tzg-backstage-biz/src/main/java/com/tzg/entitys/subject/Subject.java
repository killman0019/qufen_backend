package com.tzg.entitys.subject;

import com.tzg.common.utils.DateUtil;

import java.io.Serializable;
import java.math.BigDecimal;

public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * iprojectId
     */ 	
	private Integer iprojectId;
	/**
	 * linjie 新增字段iProjectCategyID
	 */
	private Integer iProjectCategyID;//
	
	private String projectName; //项目名称
	private String projectCode; //项目编码
	private String projectType; //项目类型 00 : 抵押;   01 : 担保 ;  02 : 质押;   03 : 信用
	private Integer iguaranteeId; //担保机构ID
	private Integer borrowerType; //借款人类型
	private BigDecimal projectNumTotalFinancing; //项目融资金额
    /**
     * 标的名称
     */ 	
	private java.lang.String vcName;
	
	/**
	 * 标的概括
	 */
	private java.lang.String vcShortDesp;
    /**
     * iloginAccountId
     */ 	
	private java.lang.Integer iloginAccountId;
	
    /**
     * 预计年利率
     */ 	
	private BigDecimal numInterestRate;
    /**
     * 奖励年利率
     */ 	
	private BigDecimal numRewardRate;
    /**
     * 管理费利率
     */ 	
	private BigDecimal numManagementRate;
	private String totalRate; //利率总和
    /**
     * 融资金额
     */ 	
	private BigDecimal numTotalFinancing;
	private String numTotalFinancingStr; //融资金额万元
	private String numCanFinancingStr;  //可募集金额
    /**
     *  1 -- T+0; 2 -- T+1;3 -- T+2; 4 -- T+3;              5 -- 结束时计息；              6 -- 满标计息(如果结束时未募集满，则已结束时计息为准。)；
     */ 	
	private java.lang.Integer ivalueDateType;
	private String ivalueDateTypeStr;
    /**
     * 递投金额
     */ 	
	private BigDecimal numDelivery;
    /**
     * 起投金额
     */ 	
	private BigDecimal numMinInvest;
    /**
     * 投资人单标最大可投资金额
     */ 	
	private BigDecimal numMaxInvest;
    /**
     * 平台服务费率
     */ 	
	private BigDecimal numPlatFormFee;
    /**
     * 1--募集总额的百分比；             2--年利率。(一年按365天算)
     */ 	
	private java.lang.Integer iplatFormFeeType;
    /**
     * 保证金费率
     */ 	
	private BigDecimal numBond;
    /**
     * 1--募集总额的百分比；             2--年利率。
     */ 	
	private java.lang.Integer ibondType;
    /**
     * 借款期限(天数)
     */ 	
	private java.lang.Integer numPeriod;
    /**
     * 预告开始时间
     */ 	
	private java.util.Date dtTrailer;
	private java.lang.String dtTrailerStr;
    /**
     * 募集开始时间
     */ 	
	private java.util.Date dtCollectStart;
	private java.lang.String dtCollectStartStr;
    /**
     * 募集结束时间
     */ 	
	private java.util.Date dtCollectEnd;
	private java.lang.String dtCollectEndStr;
    /**
     * 实际募集结束时间
     */ 	
	private java.util.Date dtActualCollectEnd;
	private java.lang.String dtActualCollectEndStr;
    /**
     * 实际募集金额
     */ 	
	private BigDecimal numActualFinancing;
	/**
	 * 募集金额 百分比
	 */
	private int percent;
    /**
     * 实际支付募集期利息
     */ 	
	private BigDecimal numActualExtraInterest;
    /**
     * 实际支付借款利息(不含募集期利息)
     */ 	
	private BigDecimal numActualInterest;
    /**
     * 1-- 新录入             2-- 待审核             3-- 退回             4--作废             5-- 审核通过             6-- 待预告             -----------与第三方交互             7--处理中(与第三方交互中)；             8--处理失败（第三方处理失败）；             9。处理成功             -----------             9-- 待开标             10-- 投标中             11-- 还款中             12-- 结束             
     */ 	
	private java.lang.Integer istate;
	
    /**
     * 与项目的最终还款日期一致
     */ 	
	private java.util.Date dtRepayment;
	private java.lang.String dtRepaymentStr;
    /**
     * 合同模板主键
     */ 	
	private Integer icontractId;
    /**
     * 下一期还款总额
     */ 	
	private BigDecimal numNextRepayAmount;
    /**
     * 下一期还款时间
     */ 	
	private java.util.Date dtNextRepay;
	private java.lang.String dtNextRepayStr;
    /**
     * 该条记录第一次创建的时间。
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
	/**
     * 该条记录修改时间
     */
    private java.util.Date dtModify;
    private java.lang.String dtModifyStr;
    /**
     * 当前审核等级
     */ 	
	private java.lang.String vcCurrentFlow;
    /**
     * 项目审核流程
     */ 	
	private java.lang.String vcApprovalFlow;
    /**
     * 融资模式 1-- 直融;2-- 债权转让
     */ 	
	private java.lang.Integer imode;
    /**
     * 1 -- 到期还本付息；2 -- 每日付息到期还本；3 -- 每月付息到期还本；4 -- 等额本息。
     */ 	
	private java.lang.Integer irepayMode;
	private String irepayModeStr;

	/**
     * 1 -- 普通标；2 -- 新手标。
     */ 	
	private java.lang.Integer itype;
	/**
     * 1 -- 显示 100%             2 -- 正常显示
     */ 	
	private java.lang.Integer idisplayState;
	
    //自旋次数
    private int spinTime = 3;
    
    private  java.lang.Integer numTotalPeriod;
    private  java.lang.Integer numCurrentPeriod; 
    
	/**
	 * 原始状态
	 */
	private java.lang.Integer iornstate;
	
	/**
	 * 贴现额
	 */
	private BigDecimal numDiscount;
	
	private String vcAwardsDesp ;

	/**
	 * 借款人提现费率 
	 */
	private BigDecimal numWithdrawFee;
	private String numWithdrawFeestr;
	/**
	 * 标的分类
	 */
	private Integer iClassType;
	/**
	 * 利率券配置id
	 */
	private Integer iInterestConfigId;
	
	/**
	 * 是否使用利率券
	 */
	private Integer iUseInterestTicket;
	/**
	 * linjie  是否开启自动开标  1：是，0：否
	 */
	private Integer iIsAutoOpenSubject;
	/**
	 * linjie 期限等级
	 */
	private Integer iSubjectPeriodGradeID;//
	private String iSubjectPeriodGradeName;//
	/**
	 * linjie 标的置顶数
	 */
	private Integer iAutoOpenPriority;//
	/**
	 * linjie 自动开标募集期限
	 */
	private Integer iAutoOpenDay;
	
    public String getNumWithdrawFeestr() {
		return numWithdrawFeestr;
	}

	public void setNumWithdrawFeestr(BigDecimal numWithdrawFee) {
		if(numWithdrawFee!=null){
			this.numWithdrawFeestr=numWithdrawFee.multiply(BigDecimal.valueOf(100)).setScale(3,BigDecimal.ROUND_HALF_UP).toString();
		}
	}

	public BigDecimal getNumWithdrawFee() {
		return numWithdrawFee;
	}

	public void setNumWithdrawFee(BigDecimal numWithdrawFee) {
		this.numWithdrawFee = numWithdrawFee;
		setNumWithdrawFeestr(numWithdrawFee);
	}
	
	public int getSpinTime() {
        return spinTime;
    }

    public void setSpinTime(int spinTime) {
        this.spinTime = spinTime;
    }

	
	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIprojectId(java.lang.Integer value) {
		this.iprojectId = value;
	}
	
	public java.lang.Integer getIprojectId() {
		return this.iprojectId;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setVcName(java.lang.String value) {
		this.vcName = value;
	}
	
	public java.lang.String getVcName() {
		return this.vcName;
	}
	
	public java.lang.String getVcShortDesp() {
		return vcShortDesp;
	}

	public void setVcShortDesp(java.lang.String vcShortDesp) {
		this.vcShortDesp = vcShortDesp;
	}

	public void setIloginAccountId(java.lang.Integer value) {
		this.iloginAccountId = value;
	}
	
	public java.lang.Integer getIloginAccountId() {
		return this.iloginAccountId;
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
	
	public void setNumManagementRate(BigDecimal value) {
		this.numManagementRate = value;
	}
	
	public BigDecimal getNumManagementRate() {
		return this.numManagementRate;
	}
	
	public void setNumTotalFinancing(BigDecimal value) {
		this.numTotalFinancing = value;
	}
	
	public BigDecimal getNumTotalFinancing() {
		return this.numTotalFinancing;
	}
	
	public void setIvalueDateType(java.lang.Integer value) {
		this.ivalueDateType = value;
	}
	
	public java.lang.Integer getIvalueDateType() {
		return this.ivalueDateType;
	}
	
	public void setNumDelivery(BigDecimal value) {
		this.numDelivery = value;
	}
	
	public BigDecimal getNumDelivery() {
		return this.numDelivery;
	}
	
	public void setNumMinInvest(BigDecimal value) {
		this.numMinInvest = value;
	}
	
	public BigDecimal getNumMinInvest() {
		return this.numMinInvest;
	}
	
	public void setNumMaxInvest(BigDecimal value) {
		this.numMaxInvest = value;
	}
	
	public BigDecimal getNumMaxInvest() {
		return this.numMaxInvest;
	}
	
	public void setNumPlatFormFee(BigDecimal value) {
		this.numPlatFormFee = value;
	}
	
	public BigDecimal getNumPlatFormFee() {
		return this.numPlatFormFee;
	}
	
	public void setIplatFormFeeType(java.lang.Integer value) {
		this.iplatFormFeeType = value;
	}
	
	public java.lang.Integer getIplatFormFeeType() {
		return this.iplatFormFeeType;
	}
	
	public void setNumBond(BigDecimal value) {
		this.numBond = value;
	}
	
	public BigDecimal getNumBond() {
		return this.numBond;
	}
	
	public void setIbondType(java.lang.Integer value) {
		this.ibondType = value;
	}
	
	public java.lang.Integer getIbondType() {
		return this.ibondType;
	}
	
	public void setNumPeriod(java.lang.Integer value) {
		this.numPeriod = value;
	}
	
	public java.lang.Integer getNumPeriod() {
		return this.numPeriod;
	}
	
	public void setDtTrailer(java.util.Date value) {
		this.dtTrailerStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtTrailer = value;
	}
	
	public java.util.Date getDtTrailer() {
		return this.dtTrailer;
	}
	
	public java.lang.String getDtTrailerStr() {
		return this.dtTrailerStr;
	}
	
	public void setDtCollectStart(java.util.Date value) {
		this.dtCollectStartStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtCollectStart = value;
	}
	
	public java.util.Date getDtCollectStart() {
		return this.dtCollectStart;
	}
	
	public java.lang.String getDtCollectStartStr() {
		return this.dtCollectStartStr;
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
	
	public void setDtActualCollectEnd(java.util.Date value) {
		this.dtActualCollectEndStr =DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtActualCollectEnd = value;
	}
	
	public java.util.Date getDtActualCollectEnd() {
		return this.dtActualCollectEnd;
	}
	
	public java.lang.String getDtActualCollectEndStr() {
		return this.dtActualCollectEndStr;
	}
	
	public void setNumActualFinancing(BigDecimal value) {
		this.numActualFinancing = value;
	}
	
	public BigDecimal getNumActualFinancing() {
		return this.numActualFinancing;
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
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
	}
	
	public void setDtRepayment(java.util.Date value) {
		this.dtRepaymentStr =DateUtil.getDate(value, "yyyy-MM-dd");
		this.dtRepayment = value;
	}
	
	public java.util.Date getDtRepayment() {
		return this.dtRepayment;
	}
	
	public java.lang.String getDtRepaymentStr() {
		return this.dtRepaymentStr;
	}
	
	public void setDtRepaymentStr(java.lang.String dtRepaymentStr) {
		this.dtRepaymentStr = dtRepaymentStr;
	}

	public Integer getIcontractId() {
		return icontractId;
	}

	public void setIcontractId(Integer icontractId) {
		this.icontractId = icontractId;
	}

	public void setNumNextRepayAmount(BigDecimal value) {
		this.numNextRepayAmount = value;
	}
	
	public BigDecimal getNumNextRepayAmount() {
		return this.numNextRepayAmount;
	}
	
	public void setDtNextRepay(java.util.Date value) {
		this.dtNextRepayStr =DateUtil.getDate(value, "yyyy-MM-dd");
		this.dtNextRepay = value;
	}
	
	public java.util.Date getDtNextRepay() {
		return this.dtNextRepay;
	}
	
	public java.lang.String getDtNextRepayStr() {
		return this.dtNextRepayStr;
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
	
	public void setVcCurrentFlow(java.lang.String value) {
		this.vcCurrentFlow = value;
	}
	
	public java.lang.String getVcCurrentFlow() {
		return this.vcCurrentFlow;
	}
	
	public void setVcApprovalFlow(java.lang.String value) {
		this.vcApprovalFlow = value;
	}
	
	public java.lang.String getVcApprovalFlow() {
		return this.vcApprovalFlow;
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

	public java.lang.Integer getImode() {
		return imode;
	}

	public void setImode(java.lang.Integer imode) {
		this.imode = imode;
	}

	public java.lang.Integer getIrepayMode() {
		return irepayMode;
	}

	public void setIrepayMode(java.lang.Integer irepayMode) {
		this.irepayMode = irepayMode;
	}

	public java.lang.Integer getItype() {
		return itype;
	}

	public void setItype(java.lang.Integer itype) {
		this.itype = itype;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getIvalueDateTypeStr() {
		return ivalueDateTypeStr;
	}

	public void setIvalueDateTypeStr(String ivalueDateTypeStr) {
		this.ivalueDateTypeStr = ivalueDateTypeStr;
	}

	public String getIrepayModeStr() {
		return irepayModeStr;
	}

	public void setIrepayModeStr(String irepayModeStr) {
		this.irepayModeStr = irepayModeStr;
	}

	public String getNumTotalFinancingStr() {
		return numTotalFinancingStr;
	}

	public void setNumTotalFinancingStr(String numTotalFinancingStr) {
		this.numTotalFinancingStr = numTotalFinancingStr;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(String totalRate) {
		this.totalRate = totalRate;
	}

	public Integer getIguaranteeId() {
		return iguaranteeId;
	}

	public void setIguaranteeId(Integer iguaranteeId) {
		this.iguaranteeId = iguaranteeId;
	}

	public java.lang.Integer getIornstate() {
		return iornstate;
	}

	public void setIornstate(java.lang.Integer iornstate) {
		this.iornstate = iornstate;
	}

	public String getNumCanFinancingStr() {
		return numCanFinancingStr;
	}

	public void setNumCanFinancingStr(String numCanFinancingStr) {
		this.numCanFinancingStr = numCanFinancingStr;
	}

	public java.lang.Integer getIdisplayState() {
		return idisplayState;
	}

	public void setIdisplayState(java.lang.Integer idisplayState) {
		this.idisplayState = idisplayState;
	}

	public java.lang.Integer getNumTotalPeriod() {
		return numTotalPeriod;
	}

	public void setNumTotalPeriod(java.lang.Integer numTotalPeriod) {
		this.numTotalPeriod = numTotalPeriod;
	}

	public java.lang.Integer getNumCurrentPeriod() {
		return numCurrentPeriod;
	}

	public void setNumCurrentPeriod(java.lang.Integer numCurrentPeriod) {
		this.numCurrentPeriod = numCurrentPeriod;
	}

	public Integer getBorrowerType() {
		return borrowerType;
	}

	public void setBorrowerType(Integer borrowerType) {
		this.borrowerType = borrowerType;
	}

	public BigDecimal getProjectNumTotalFinancing() {
		return projectNumTotalFinancing;
	}

	public void setProjectNumTotalFinancing(BigDecimal projectNumTotalFinancing) {
		this.projectNumTotalFinancing = projectNumTotalFinancing;
	}

	public BigDecimal getNumDiscount() {
		return numDiscount;
	}

	public void setNumDiscount(BigDecimal numDiscount) {
		this.numDiscount = numDiscount;
	}

	public String getVcAwardsDesp() {
		return vcAwardsDesp;
	}

	public void setVcAwardsDesp(String vcAwardsDesp) {
		this.vcAwardsDesp = vcAwardsDesp;
	}
	public Integer getiClassType() {
		return iClassType;
	}

	public void setiClassType(Integer iClassType) {
		this.iClassType = iClassType;
	}

	public Integer getiInterestConfigId() {
		return iInterestConfigId;
	}

	public void setiInterestConfigId(Integer iInterestConfigId) {
		this.iInterestConfigId = iInterestConfigId;
	}

	public Integer getiUseInterestTicket() {
		return iUseInterestTicket;
	}

	public void setiUseInterestTicket(Integer iUseInterestTicket) {
		this.iUseInterestTicket = iUseInterestTicket;
	}
	public Integer getiIsAutoOpenSubject() {
		return iIsAutoOpenSubject;
	}

	public void setiIsAutoOpenSubject(Integer iIsAutoOpenSubject) {
		this.iIsAutoOpenSubject = iIsAutoOpenSubject;
	}

	public Integer getiSubjectPeriodGradeID() {
		return iSubjectPeriodGradeID;
	}

	public void setiSubjectPeriodGradeID(Integer iSubjectPeriodGradeID) {
		this.iSubjectPeriodGradeID = iSubjectPeriodGradeID;
	}

	public Integer getiProjectCategyID() {
		return iProjectCategyID;
	}

	public void setiProjectCategyID(Integer iProjectCategyID) {
		this.iProjectCategyID = iProjectCategyID;
	}

	public Integer getiAutoOpenPriority() {
		return iAutoOpenPriority;
	}

	public void setiAutoOpenPriority(Integer iAutoOpenPriority) {
		this.iAutoOpenPriority = iAutoOpenPriority;
	}

	public String getiSubjectPeriodGradeName() {
		return iSubjectPeriodGradeName;
	}

	public void setiSubjectPeriodGradeName(String iSubjectPeriodGradeName) {
		this.iSubjectPeriodGradeName = iSubjectPeriodGradeName;
	}

    public Integer getiAutoOpenDay() {
        return iAutoOpenDay;
    }

    public void setiAutoOpenDay(Integer iAutoOpenDay) {
        this.iAutoOpenDay = iAutoOpenDay;
    }

    public java.util.Date getDtModify() {
        return dtModify;
    }

    public void setDtModify(java.util.Date dtModify) {
        this.dtModify = dtModify;
    }

    public java.lang.String getDtModifyStr() {
        return dtModifyStr;
    }

    public void setDtModifyStr(java.lang.String dtModifyStr) {
        this.dtModifyStr = dtModifyStr;
    }

	
}

