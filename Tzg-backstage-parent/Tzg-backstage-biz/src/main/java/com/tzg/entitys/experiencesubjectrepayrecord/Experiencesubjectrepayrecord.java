package com.tzg.entitys.experiencesubjectrepayrecord;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzg.common.utils.DateUtil;

public class Experiencesubjectrepayrecord implements Serializable {

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

    private String            vcSubjectName;
    /**
     * iinvestRecordId
     */
    private java.lang.Integer iinvestRecordId;
    /**
     * 1 -- 本息； 2 -- 本金； 3 -- 利息。
     */
    private java.lang.Integer itype;
    /**
     * numRepayInterest
     */
    private BigDecimal        numRepayInterest;
    /**
     * dtRepay
     */
    private java.util.Date    dtRepay;
    private java.lang.String  dtRepayStr;
    /**
     * dtActualRepay
     */
    private java.util.Date    dtActualRepay;
    private java.lang.String  dtActualRepayStr;
    /**
     * 1、未还； 2、已还；
     */
    private java.lang.Integer istate;
    /**
     * 该条记录第一次创建的时间。
     */
    private java.util.Date    dtCreate;
    private java.lang.String  dtCreateStr;

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

    public String getVcSubjectName() {
        return vcSubjectName;
    }

    public void setVcSubjectName(String vcSubjectName) {
        this.vcSubjectName = vcSubjectName;
    }

    public void setIinvestRecordId(java.lang.Integer value) {
        this.iinvestRecordId = value;
    }

    public java.lang.Integer getIinvestRecordId() {
        return this.iinvestRecordId;
    }

    public void setItype(java.lang.Integer value) {
        this.itype = value;
    }

    public java.lang.Integer getItype() {
        return this.itype;
    }

    public void setNumRepayInterest(BigDecimal value) {
        this.numRepayInterest = value;
    }

    public BigDecimal getNumRepayInterest() {
        return this.numRepayInterest;
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

    public void setDtActualRepay(java.util.Date value) {
        this.dtActualRepayStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
        this.dtActualRepay = value;
    }

    public java.util.Date getDtActualRepay() {
        return this.dtActualRepay;
    }

    public java.lang.String getDtActualRepayStr() {
        return this.dtActualRepayStr;
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

}
