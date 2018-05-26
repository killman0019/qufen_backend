package com.tzg.entitys.helpdetail;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class Helpdetail implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * id
     */ 	
	private java.lang.Integer id;
    /**
     * ihelpCategyId
     */ 	
	private java.lang.Integer ihelpCategyId;
    /**
     * vcQuestion
     */ 	
	private java.lang.String vcQuestion;
    /**
     * vcAnswer
     */ 	
	private java.lang.String vcAnswer;
    /**
     * 1 -- 有效； 2 -- 无效。
     */ 	
	private java.lang.Integer istate;
    /**
     * dtCreate
     */ 	
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
	
	private java.lang.Integer iSort;

	
	public java.lang.Integer getiSort() {
		return iSort;
	}

	public void setiSort(java.lang.Integer iSort) {
		this.iSort = iSort;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public void setIhelpCategyId(java.lang.Integer value) {
		this.ihelpCategyId = value;
	}
	
	public java.lang.Integer getIhelpCategyId() {
		return this.ihelpCategyId;
	}
	
	public void setVcQuestion(java.lang.String value) {
		this.vcQuestion = value;
	}
	
	public java.lang.String getVcQuestion() {
		return this.vcQuestion;
	}
	
	public void setVcAnswer(java.lang.String value) {
		this.vcAnswer = value;
	}
	
	public java.lang.String getVcAnswer() {
		return this.vcAnswer;
	}
	
	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}
	
	public java.lang.Integer getIstate() {
		return this.istate;
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

	
}

