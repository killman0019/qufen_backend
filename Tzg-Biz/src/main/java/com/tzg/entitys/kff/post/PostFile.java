package com.tzg.entitys.kff.post;

import java.io.Serializable;

import com.tzg.common.utils.DateUtil;

public class PostFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7329951839351859954L;

	/**
	 * fileUrl
	 */
	private java.lang.String fileUrl;
	/**
	 * fileName
	 */
	private java.lang.String fileName;
	/**
	 * extension
	 */
	private java.lang.String extension;
	/**
	 * 是否存在图片服务器
	 */
	private Boolean isExist;

	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}

	public java.lang.String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(java.lang.String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public java.lang.String getFileName() {
		return fileName;
	}

	public void setFileName(java.lang.String fileName) {
		this.fileName = fileName;
	}

	public java.lang.String getExtension() {
		return extension;
	}

	public void setExtension(java.lang.String extension) {
		this.extension = extension;
	}

}
