package com.tzg.entitys.news;

import java.io.Serializable;
import com.tzg.common.utils.DateUtil;

public class News implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	private java.lang.Integer id;
	/**
	 * vcTitle
	 */
	private java.lang.String vcTitle;
	/**
	 * vcSubtitle
	 */
	private java.lang.String vcSubtitle;
	/**
	 * vcSource
	 */
	private java.lang.String vcSource;
	/**
	 * vcOrgLink
	 */
	private java.lang.String vcOrgLink;
	/**
	 * vcImgUrl
	 */
	private java.lang.String vcImgUrl;
	/**
	 * vcSummary
	 */
	private java.lang.String vcSummary;
	/**
	 * vcContent
	 */
	private java.lang.String vcContent;
	/**
	 * vcKey
	 */
	private java.lang.String vcKey;
	/**
	 * dtPublish
	 */
	private java.util.Date dtPublish;
	private java.lang.String dtPublishStr;
	/**
	 * 1 -- 最新动态； 2 -- 媒体报道。
	 */
	private java.lang.Integer itype;
	/**
	 * dtCreate
	 */
	private java.util.Date dtCreate;
	private java.lang.String dtCreateStr;
	/**
	 * 1 -- 未发布； 2 -- 已发布
	 */
	private java.lang.Integer istate;

	private Integer ihomedisplay;

	private Integer ihomesort;

	public Integer getIhomedisplay() {
		return ihomedisplay;
	}

	public void setIhomedisplay(Integer ihomedisplay) {
		this.ihomedisplay = ihomedisplay;
	}

	public Integer getIhomesort() {
		return ihomesort;
	}

	public void setIhomesort(Integer ihomesort) {
		this.ihomesort = ihomesort;
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}

	public java.lang.Integer getId() {
		return this.id;
	}

	public void setVcTitle(java.lang.String value) {
		this.vcTitle = value;
	}

	public java.lang.String getVcTitle() {
		return this.vcTitle;
	}

	public void setVcSubtitle(java.lang.String value) {
		this.vcSubtitle = value;
	}

	public java.lang.String getVcSubtitle() {
		return this.vcSubtitle;
	}

	public void setVcSource(java.lang.String value) {
		this.vcSource = value;
	}

	public java.lang.String getVcSource() {
		return this.vcSource;
	}

	public void setVcOrgLink(java.lang.String value) {
		this.vcOrgLink = value;
	}

	public java.lang.String getVcOrgLink() {
		return this.vcOrgLink;
	}

	public void setVcImgUrl(java.lang.String value) {
		this.vcImgUrl = value;
	}

	public java.lang.String getVcImgUrl() {
		return this.vcImgUrl;
	}

	public void setVcSummary(java.lang.String value) {
		this.vcSummary = value;
	}

	public java.lang.String getVcSummary() {
		return this.vcSummary;
	}

	public void setVcContent(java.lang.String value) {
		this.vcContent = value;
	}

	public java.lang.String getVcContent() {
		return this.vcContent;
	}

	public void setVcKey(java.lang.String value) {
		this.vcKey = value;
	}

	public java.lang.String getVcKey() {
		return this.vcKey;
	}

	public void setDtPublish(java.util.Date value) {
		this.dtPublishStr = DateUtil.getDate(value, "yyyy-MM-dd HH:mm:ss");
		this.dtPublish = value;
	}

	public java.util.Date getDtPublish() {
		return this.dtPublish;
	}

	public java.lang.String getDtPublishStr() {
		return this.dtPublishStr;
	}

	public void setItype(java.lang.Integer value) {
		this.itype = value;
	}

	public java.lang.Integer getItype() {
		return this.itype;
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

	public void setIstate(java.lang.Integer value) {
		this.istate = value;
	}

	public java.lang.Integer getIstate() {
		return this.istate;
	}

}
