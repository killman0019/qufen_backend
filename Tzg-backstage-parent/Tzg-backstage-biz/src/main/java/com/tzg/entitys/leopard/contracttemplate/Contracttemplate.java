package com.tzg.entitys.leopard.contracttemplate;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Description：合同模板pojo
 * @author wxg
 * @Date 2014-12-17 
 * 
 */
public class Contracttemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6118227542275965011L;

	private Integer id;

	private String vcName;

	private String vcURL;

	private Integer iValid;

	private Date dtModify;

	private Date dtCreate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVcName() {
		return vcName;
	}

	public void setVcName(String vcName) {
		this.vcName = vcName;
	}

	public String getVcURL() {
		return vcURL;
	}

	public void setVcURL(String vcURL) {
		this.vcURL = vcURL;
	}

	public Integer getiValid() {
		return iValid;
	}

	public void setiValid(Integer iValid) {
		this.iValid = iValid;
	}

	public Date getDtModify() {
		return dtModify;
	}

	public void setDtModify(Date dtModify) {
		this.dtModify = dtModify;
	}

	public Date getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(Date dtCreate) {
		this.dtCreate = dtCreate;
	}

}
