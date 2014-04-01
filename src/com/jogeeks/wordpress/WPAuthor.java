package com.jogeeks.wordpress;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

public class WPAuthor extends WPUser {

	static final String AUTHOR_INDEX_URL = "get_author_index";

	// Note: You can include additional values by setting the author_meta
	// argument to a comma-separated list of metadata fields.

	private String slug;
	private String name;

	public WPAuthor(JSONObject author) {
		try {
			setId(Integer.parseInt(author.getString("id")));
			setSlug(author.getString("slug"));
			setName(author.getString("name"));
			setFirstname(author.getString("first_name"));
			setLastname(author.getString("last_name"));
			setNickname(author.getString("nickname"));
			setUrl(author.getString("url"));
			setDescription(author.getString("description"));

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public WPAuthor(Bundle bundle) {
		super(bundle);
		// id = bundle.getInt("id");
		// username = bundle.getString("username");
		// nicename = bundle.getString("nicename");
		// url = bundle.getString("url");
		// email = bundle.getString("email");
		// registered = bundle.getString("registerd");
		// displayname = bundle.getString("display_name");
		// firstname = bundle.getString("firstname");
		// lastname = bundle.getString("lastname");
		// nickname = bundle.getString("nickname");
		// description = bundle.getString("desc");
		slug = bundle.getString("slug");
		name = bundle.getString("name");

	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Bundle getBundle(WPAuthor author) {

		Bundle authorBundle = new Bundle();
		/*
		 * private int id; private String username; private String nicename;
		 * private String email; private String url; private String registered;
		 * private String displayname; private String firstname; private String
		 * lastname; private String nickname; private String description;
		 * private String slug; private String name;
		 */

		authorBundle.putInt("id", author.getId());
		authorBundle.putString("username", author.getUsername());
		authorBundle.putString("nicename", author.getNicename());
		authorBundle.putString("url", author.getUrl());
		authorBundle.putString("email", author.getEmail());
		authorBundle.putString("registerd", author.getRegistered());
		authorBundle.putString("display_name", author.getDisplayname());
		authorBundle.putString("firstname", author.getFirstname());
		authorBundle.putString("lastname", author.getLastname());
		authorBundle.putString("nickname", author.getNickname());
		authorBundle.putString("desc", author.getDescription());
		authorBundle.putString("slug", author.getSlug());
		authorBundle.putString("name", author.getName());

		return authorBundle;

	}
}
