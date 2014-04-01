package com.jogeeks.wordpress;

import java.util.ArrayList;

public class WPAtachment extends ArrayList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String full, thumbnail, medium, postThumbnail = "";

	public void setFullImage(String url) {
		full = url;
		try {
			set(0, full);
		} catch (Exception e) {
			add(full);
		}
	}

	public void setThumbnailImage(String url) {
		thumbnail = url;
		try {
			set(1, thumbnail);
		} catch (Exception e) {
			add(thumbnail);
		}
	}

	public void setMediumImage(String url) {
		medium = url;
		try {
			set(2, medium);
		} catch (Exception e) {
			add(medium);
		}
	}

	public void setPostThumbnailImage(String url) {
		postThumbnail = url;
		try {
			set(3, postThumbnail);
		} catch (Exception e) {
			add(postThumbnail);
		}
	}

	public String getFullImage() {
		if (this.isEmpty()) {
			return "";
		}
		return get(0);
	}

	public String getThumbnailImage() {
		if (this.isEmpty()) {
			return "";
		}
		return get(1);
	}

	public String getMediumImage() {
		if (this.isEmpty()) {
			return "";
		}
		return get(2);
	}

	public String getPostThumbnailImage() {
		if (this.isEmpty()) {
			return "";
		}
		return get(3);
	}
}
