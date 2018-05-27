package com.tzg.entitys.kff.userInvation;

import java.io.Serializable;
import java.util.Date;

public class UserInvation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3250368479384413831L;

	private Integer userinvationid;

	private String user2code;

	private String user2codepic;

	private String userposterpic;

	private Integer status;

	private Date createtime;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUserinvationid() {
		return userinvationid;
	}

	public void setUserinvationid(Integer userinvationid) {
		this.userinvationid = userinvationid;
	}

	public String getUser2code() {
		return user2code;
	}

	public void setUser2code(String user2code) {
		this.user2code = user2code == null ? null : user2code.trim();
	}

	public String getUser2codepic() {
		return user2codepic;
	}

	public void setUser2codepic(String user2codepic) {
		this.user2codepic = user2codepic == null ? null : user2codepic.trim();
	}

	public String getUserposterpic() {
		return userposterpic;
	}

	public void setUserposterpic(String userposterpic) {
		this.userposterpic = userposterpic == null ? null : userposterpic.trim();
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
}