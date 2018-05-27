package com.tzg.entitys.kff.suggest;

import com.tzg.common.base.BaseRequest;
import com.tzg.common.utils.DateUtil;

public class SuggestRequest extends BaseRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2512090074525373764L;
	
    /**
     * 建议内容
     */ 	
	private java.lang.String content;
    /**
     * 用户联系方式
     */ 	
	private java.lang.String contactInfo;
   
    /**
     * createUserId
     */ 	
	private java.lang.Integer createUserId;

	public java.lang.String getContent() {
		return content;
	}

	public void setContent(java.lang.String content) {
		this.content = content;
	}

	public java.lang.String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(java.lang.String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public java.lang.Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(java.lang.Integer createUserId) {
		this.createUserId = createUserId;
	}


	
	
}

