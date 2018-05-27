package com.tzg.entitys.kff.usercard;

import java.io.Serializable;
import java.util.Date;

public class UserCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 396590637884322134L;

	private Integer cardid;

	private Integer userid;

	private String userrealname;

	private String usercardNum;

	private String positiveofcard;

	private Integer status;

	private Date createtime;

	private Date updatatime;

	private Integer valid;

	private String phone;

	private String notPassReason;

	public String getNotPassReason() {
		return notPassReason;
	}

	public void setNotPassReason(String notPassReason) {
		this.notPassReason = notPassReason;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCardid() {
		return cardid;
	}

	public void setCardid(Integer cardid) {
		this.cardid = cardid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUserrealname() {
		return userrealname;
	}

	public void setUserrealname(String userrealname) {
		this.userrealname = userrealname == null ? null : userrealname.trim();
	}

	public String getUsercardNum() {
		return usercardNum;
	}

	public void setUsercardNum(String usercardNum) {
		this.usercardNum = usercardNum;
	}

	public String getPositiveofcard() {
		return positiveofcard;
	}

	public void setPositiveofcard(String positiveofcard) {
		this.positiveofcard = positiveofcard == null ? null : positiveofcard.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getUpdatatime() {
		return updatatime;
	}

	public void setUpdatatime(Date updatatime) {
		this.updatatime = updatatime;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

}