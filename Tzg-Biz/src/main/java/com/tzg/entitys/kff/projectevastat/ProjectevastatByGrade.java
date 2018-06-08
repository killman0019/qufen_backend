package com.tzg.entitys.kff.projectevastat;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProjectevastatByGrade implements Serializable {


	
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 4685576597977642126L;
	
    /**
     * 每个等级打分总人数
     */ 	
	private java.lang.Integer raterNum = 0;
	
	private java.lang.Integer grade;
	
	private java.lang.String gradeLable;
    /**
     *项目id
     */ 	
	private java.lang.Integer projectId;
    /**
     * 百分比
     */ 	
	private java.lang.Integer percent = 0;
    /**
     * 分项总得分
     */ 	
	private BigDecimal totalScore;
	public java.lang.Integer getRaterNum() {
		return raterNum;
	}
	public void setRaterNum(java.lang.Integer raterNum) {
		this.raterNum = raterNum;
	}
	public java.lang.Integer getGrade() {
		return grade;
	}
	public void setGrade(java.lang.Integer grade) {
		this.grade = grade;
	}
	public java.lang.String getGradeLable() {
		return gradeLable;
	}
	public void setGradeLable(java.lang.String gradeLable) {
		this.gradeLable = gradeLable;
	}
	public java.lang.Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(java.lang.Integer projectId) {
		this.projectId = projectId;
	}
	public java.lang.Integer getPercent() {
		return percent;
	}
	public void setPercent(java.lang.Integer percent) {
		this.percent = percent;
	}
	public BigDecimal getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}
	
	
	
	

	
}

