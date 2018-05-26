package com.tzg.entitys.dayinvest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 统计昨天投资大于一万的50条记录
 * 
 * @author lz-h@tzg.cn
 * @version $Id: DayInvest.java, v 0.1 2015年8月27日 下午6:07:01 lz-h@tzg.cn Exp $
 */
public class DayInvest implements Serializable {

    private static final long serialVersionUID = 1L;

    private int               id;
    private int               iLoginAccountId;
    private BigDecimal        numAmount;
    private Date              dtDate;
    private String            vcSubjectName;
    private String            vcProjectName;
    private String            vcUserName;
    private BigDecimal        numRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getiLoginAccountId() {
        return iLoginAccountId;
    }

    public void setiLoginAccountId(int iLoginAccountId) {
        this.iLoginAccountId = iLoginAccountId;
    }

    public BigDecimal getNumAmount() {
        return numAmount;
    }

    public void setNumAmount(BigDecimal numAmount) {
        this.numAmount = numAmount;
    }

    public Date getDtDate() {
        return dtDate;
    }

    public void setDtDate(Date dtDate) {
        this.dtDate = dtDate;
    }

    public String getVcSubjectName() {
        return vcSubjectName;
    }

    public void setVcSubjectName(String vcSubjectName) {
        this.vcSubjectName = vcSubjectName;
    }

    public String getVcUserName() {
        return vcUserName;
    }

    public void setVcUserName(String vcUserName) {
        this.vcUserName = vcUserName;
    }

    public BigDecimal getNumRate() {
        return numRate;
    }

    public void setNumRate(BigDecimal numRate) {
        this.numRate = numRate;
    }

    public String getVcProjectName() {
        return vcProjectName;
    }

    public void setVcProjectName(String vcProjectName) {
        this.vcProjectName = vcProjectName;
    }
}
