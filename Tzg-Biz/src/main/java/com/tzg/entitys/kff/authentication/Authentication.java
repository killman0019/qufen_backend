package com.tzg.entitys.kff.authentication;

import java.io.Serializable;
import java.util.Date;

public class Authentication implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1471801807161763037L;

	private Integer accountauthenticationid;

	private Integer userid;

	private Integer type;

	private String qufennickname;

	private String authinformation;

	private String company;

	private String registrationnum;

	private String licencepic;

	private String missivepic;

	private String operatorname;

	private String assistpic;

	private String link;

	private String wechat;

	private String number;

	private String mail;

	private String medianame;

	private String mediachannel;

	private String mediaintroduce;

	private Integer status;

	private Integer valid;

	private Date createdata;

	private String notpassreason;

	public Integer getAccountauthenticationid() {
		return accountauthenticationid;
	}

	public void setAccountauthenticationid(Integer accountauthenticationid) {
		this.accountauthenticationid = accountauthenticationid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getQufennickname() {
		return qufennickname;
	}

	public void setQufennickname(String qufennickname) {
		this.qufennickname = qufennickname == null ? null : qufennickname
				.trim();
	}

	public String getAuthinformation() {
		return authinformation;
	}

	public void setAuthinformation(String authinformation) {
		this.authinformation = authinformation == null ? null : authinformation
				.trim();
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company == null ? null : company.trim();
	}

	public String getRegistrationnum() {
		return registrationnum;
	}

	public void setRegistrationnum(String registrationnum) {
		this.registrationnum = registrationnum == null ? null : registrationnum
				.trim();
	}

	public String getLicencepic() {
		return licencepic;
	}

	public void setLicencepic(String licencepic) {
		this.licencepic = licencepic == null ? null : licencepic.trim();
	}

	public String getMissivepic() {
		return missivepic;
	}

	public void setMissivepic(String missivepic) {
		this.missivepic = missivepic == null ? null : missivepic.trim();
	}

	public String getOperatorname() {
		return operatorname;
	}

	public void setOperatorname(String operatorname) {
		this.operatorname = operatorname == null ? null : operatorname.trim();
	}

	public String getAssistpic() {
		return assistpic;
	}

	public void setAssistpic(String assistpic) {
		this.assistpic = assistpic == null ? null : assistpic.trim();
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link == null ? null : link.trim();
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat == null ? null : wechat.trim();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number == null ? null : number.trim();
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail == null ? null : mail.trim();
	}

	public String getMedianame() {
		return medianame;
	}

	public void setMedianame(String medianame) {
		this.medianame = medianame == null ? null : medianame.trim();
	}

	public String getMediachannel() {
		return mediachannel;
	}

	public void setMediachannel(String mediachannel) {
		this.mediachannel = mediachannel == null ? null : mediachannel.trim();
	}

	public String getMediaintroduce() {
		return mediaintroduce;
	}

	public void setMediaintroduce(String mediaintroduce) {
		this.mediaintroduce = mediaintroduce == null ? null : mediaintroduce
				.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Date getCreatedata() {
		return createdata;
	}

	public void setCreatedata(Date createdata) {
		this.createdata = createdata;
	}

	public String getNotpassreason() {
		return notpassreason;
	}

	public void setNotpassreason(String notpassreason) {
		this.notpassreason = notpassreason == null ? null : notpassreason
				.trim();
	}
}