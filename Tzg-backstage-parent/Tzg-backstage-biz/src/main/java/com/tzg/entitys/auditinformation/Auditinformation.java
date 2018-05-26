package com.tzg.entitys.auditinformation;

import java.util.Date;

import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.BaseModel;

/**
 * 
 * @Description：项目或标的审核信息pojo
 * @author wxg
 * @Date 2014-12-15
 * 
 */
public class Auditinformation extends BaseModel {

	private Integer iSourceD;
	
	/**
	 * 审核等级
	 */
	private String vcApproveLevel;
	private String vcApprovalName;
	
	/**
	 * 审核人
	 */
	private String vcApproverName;

	private Date dtApprove;
	private String dtApproveStr;

	private Integer iState;

	/**
	 * 审核意见
	 */
	private String vcApproveIdea;

	private Date dtCreate;
	private String dtCreateStr;

	private Integer iType;

	public Integer getiSourceD() {
		return iSourceD;
	}

	public void setiSourceD(Integer iSourceD) {
		this.iSourceD = iSourceD;
	}

	public String getVcApproveLevel() {
		return vcApproveLevel;
	}

	public void setVcApproveLevel(String vcApproveLevel) {
		this.vcApproveLevel = vcApproveLevel;
	}

	public String getVcApprovalName() {
		return vcApprovalName;
	}

	public void setVcApprovalName(String vcApprovalName) {
		this.vcApprovalName = vcApprovalName;
	}

	public String getVcApproverName() {
		return vcApproverName;
	}

	public void setVcApproverName(String vcApproverName) {
		this.vcApproverName = vcApproverName;
	}

	public Date getDtApprove() {
		return dtApprove;
	}

	public void setDtApprove(Date dtApprove) {
		this.dtApproveStr =DateUtil.getDate(dtApprove, "yyyy-MM-dd HH:mm:ss");
		this.dtApprove = dtApprove;
	}

	public Integer getiState() {
		return iState;
	}

	public void setiState(Integer iState) {
		this.iState = iState;
	}

	public String getVcApproveIdea() {
		return vcApproveIdea;
	}

	public void setVcApproveIdea(String vcApproveIdea) {
		this.vcApproveIdea = vcApproveIdea;
	}

	public Date getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Date dtCreate) {
		this.dtCreateStr =DateUtil.getDate(dtCreate, "yyyy-MM-dd HH:mm:ss");
		this.dtCreate = dtCreate;
	}

	public Integer getiType() {
		return iType;
	}

	public void setiType(Integer iType) {
		this.iType = iType;
	}

	public String getDtApproveStr() {
		return dtApproveStr;
	}

	public String getDtCreateStr() {
		return dtCreateStr;
	}

}
