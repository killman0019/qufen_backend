package com.tzg.entitys.discussImages;

import java.io.Serializable;
import java.util.List;

public class DiscussImages implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -492449188882442387L;

	private List<String> discussImages;

	public List<String> getDiscussImages() {
		return discussImages;
	}

	public void setDiscussImages(List<String> discussImages) {
		this.discussImages = discussImages;
	}

}
