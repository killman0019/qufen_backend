package com.tzg.entitys.photo;

import java.io.Serializable;

public class PhotoParams implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8239763615289197283L;

	private String fileName;

	private String fileUrl;

	private String size;

	private String extension;

	private Boolean isExist;

	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
