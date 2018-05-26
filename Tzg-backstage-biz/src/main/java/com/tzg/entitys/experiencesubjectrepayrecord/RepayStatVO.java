package com.tzg.entitys.experiencesubjectrepayrecord;

import java.io.Serializable;
import java.math.BigDecimal;

public class RepayStatVO implements Serializable {

    private static final long serialVersionUID = -3436505544704843922L;
    private Integer           istate;
    private BigDecimal        interest;

    public Integer getIstate() {
        return istate;
    }

    public void setIstate(Integer istate) {
        this.istate = istate;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }
}
