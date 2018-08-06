package com.tzg.entitys.kff.projecttrade;

import java.io.Serializable;
import java.util.Date;

public class ProjectTrade implements Serializable {
	/**
	 * TODO
	 */
	private static final long serialVersionUID = -6943481839088578517L;

	private Integer projectTradeId;

	private Integer projectId;

	private Integer cmcId;

	private Integer rank;

	private Double price;

	private Double volume24h;

	private Double marketCap;

	private Double percentChange1h;

	private Double percentChange24h;

	private Double percentChange7d;

	private Date createTime;

	private Date updataTime;

	public Integer getProjectTradeId() {
		return projectTradeId;
	}

	public void setProjectTradeId(Integer projectTradeId) {
		this.projectTradeId = projectTradeId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getCmcId() {
		return cmcId;
	}

	public void setCmcId(Integer cmcId) {
		this.cmcId = cmcId;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getVolume24h() {
		return volume24h;
	}

	public void setVolume24h(Double volume24h) {
		this.volume24h = volume24h;
	}

	public Double getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(Double marketCap) {
		this.marketCap = marketCap;
	}

	public Double getPercentChange1h() {
		return percentChange1h;
	}

	public void setPercentChange1h(Double percentChange1h) {
		this.percentChange1h = percentChange1h;
	}

	public Double getPercentChange24h() {
		return percentChange24h;
	}

	public void setPercentChange24h(Double percentChange24h) {
		this.percentChange24h = percentChange24h;
	}

	public Double getPercentChange7d() {
		return percentChange7d;
	}

	public void setPercentChange7d(Double percentChange7d) {
		this.percentChange7d = percentChange7d;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdataTime() {
		return updataTime;
	}

	public void setUpdataTime(Date updataTime) {
		this.updataTime = updataTime;
	}
}