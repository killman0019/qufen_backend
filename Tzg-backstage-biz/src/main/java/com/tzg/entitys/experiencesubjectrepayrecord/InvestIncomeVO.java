package com.tzg.entitys.experiencesubjectrepayrecord;

import java.io.Serializable;
import java.math.BigDecimal;

public class InvestIncomeVO implements Serializable {

    private static final long serialVersionUID = 4365467804615703330L;
    private BigDecimal        invest;
    private BigDecimal        interest;

    public BigDecimal getInvest() {
        return invest;
    }

    public void setInvest(BigDecimal invest) {
        this.invest = invest;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }
}
