package com.jogeeks.wordpress;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

public class WPTag {

	static final String PAGE_TAG_URL = "get_TAG_index";

	private int id;
	private String slug;
	private String title;
	private String description;
	private int postCount;

	public WPTag() {

	}

	public WPTag(JSONObject tag) {
		try {
			setId(Integer.parseInt(tag.getString("id")));
			setSlug(tag.getString("slug"));
			setTitle(tag.getString("title"));
			setDescription(tag.getString("description"));
			setPostCount(Integer.parseInt(tag.getString("post_count")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public WPTag(Bundle bundle) {
		id = bundle.getInt("id");
		slug = bundle.getString("slug");
		title = bundle.getString("title");
		description = bundle.getString("desc");
		postCount = bundle.getInt("post_count");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getTitle() {
		return title;
	}

	private void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public static Bundle getBundle(WPTag tag) {
		Bundle tagBundle = new Bundle();
		/*
		 * private int id; private String slug; private String title; private
		 * String description; private int postCount;
		 */

		tagBundle.putInt("id", tag.getId());
		tagBundle.putString("slug", tag.getSlug());
		tagBundle.putString("title", tag.getSlug());
		tagBundle.putString("desc", tag.getDescription());
		tagBundle.putInt("post_count", tag.getPostCount());

		return tagBundle;

	}

}
