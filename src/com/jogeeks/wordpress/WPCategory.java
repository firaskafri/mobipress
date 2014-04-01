package com.jogeeks.wordpress;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

public class WPCategory {

	static final String CATEGORY_INDEX = "get_category_index";
	static final String UNCATEGORIZED = "Uncategorized";

	private int id;
	private String slug;
	private String title;
	private String description;
	private int parent;
	private int postCount;

	public WPCategory() {

	}

	public WPCategory(JSONObject category) {
		try {
			setId(Integer.parseInt(category.getString("id")));
			setSlug(category.getString("slug"));
			setTitle(category.getString("title"));
			setDescription(category.getString("description"));
			setParent(Integer.parseInt(category.getString("parent")));
			setPostCount(Integer.parseInt(category.getString("post_count")));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public WPCategory(Bundle bundle) {
		id = bundle.getInt("id");
		slug = bundle.getString("slug");
		title = bundle.getString("title");
		description = bundle.getString("desc");
		parent = bundle.getInt("parent");
		postCount = bundle.getInt("post_count");
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getSlug() {
		return slug;
	}

	private void setSlug(String slug) {
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

	private void setDescription(String description) {
		this.description = description;
	}

	public int getParent() {
		return parent;
	}

	private void setParent(int parent) {
		this.parent = parent;
	}

	public int getPostCount() {
		return postCount;
	}

	private void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public static Bundle getBundle(WPCategory cat) {
		Bundle catBundle = new Bundle();

		catBundle.putInt("id", cat.getId());
		catBundle.putString("slug", cat.getSlug());
		catBundle.putString("title", cat.getSlug());
		catBundle.putString("desc", cat.getDescription());
		catBundle.putInt("parent", cat.getParent());
		catBundle.putInt("post_count", cat.getPostCount());

		return catBundle;
	}

}
