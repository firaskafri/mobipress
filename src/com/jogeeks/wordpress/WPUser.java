package com.jogeeks.wordpress;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

public class WPUser {

	public static String ADMINISTRATOR = "administrator";
	public static String EDITOR = "editor";
	public static String AUTHOR = "author";

	public static String CONTRIBUTOR = "contributor";
	public static String SUBSCRIBER = "subscriber";

	protected int id;
	protected String username;
	protected String nicename;
	protected String email;
	protected String url;
	protected String registered;
	protected String displayname;
	protected String firstname;
	protected String lastname;
	protected String nickname;
	protected String description;
	protected WPRole capabilities;
	protected String avatar;

	public WPUser() {

	}

	public WPUser(Bundle bundle) {

		id = bundle.getInt("id");
		username = bundle.getString("username");
		nicename = bundle.getString("nicename");
		url = bundle.getString("url");
		email = bundle.getString("email");
		registered = bundle.getString("registerd");
		displayname = bundle.getString("display_name");
		firstname = bundle.getString("firstname");
		lastname = bundle.getString("lastname");
		nickname = bundle.getString("nickname");
		description = bundle.getString("desc");
		avatar = bundle.getString("avatar");
		try {
			capabilities = new WPRole(new JSONObject(
					bundle.getString("capabilities")));
		} catch (JSONException e) {
		} catch (NullPointerException e) {
		}
	}

	public String getAvatar() {
		return avatar;
	}

	public String getDescription() {
		return description;
	}

	protected String getDisplayname() {
		return displayname;
	}

	protected String getEmail() {
		return email;
	}

	public String getFirstname() {
		return firstname;
	}

	public int getId() {
		return id;
	}

	public String getLastname() {
		return lastname;
	}

	protected String getNicename() {
		return nicename;
	}

	public String getNickname() {
		return nickname;
	}

	protected String getRegistered() {
		return registered;
	}

	public String getUrl() {
		return url;
	}

	protected String getUsername() {
		return username;
	}

	public boolean isAdministrator() {
		return capabilities.administrator;
	}

	public boolean isAuthor() {
		return capabilities.author;
	}

	public boolean isContributor() {
		return capabilities.contributor;
	}

	public boolean isEditor() {
		return capabilities.editor;
	}

	public boolean isSubscriber() {
		return capabilities.subscriber;
	}

	public void setAdministrator(boolean s) {
		capabilities.administrator = s;
	}

	public void setAuthor(boolean s) {
		capabilities.author = s;
	}

	protected void setAvatar(String url) {
		this.avatar = url;
	}

	public void setContributor(boolean s) {
		capabilities.contributor = s;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	protected void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public void setEditor(boolean s) {
		capabilities.editor = s;
	}

	protected void setEmail(String email) {
		this.email = email;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	protected void setNicename(String nicename) {
		this.nicename = nicename;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	protected void setRegistered(String registered) {
		this.registered = registered;
	}

	public void setSubscriber(boolean s) {
		capabilities.subscriber = s;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param userData
	 *            pass a bundle with all of the required values: username,
	 *            nicename, email, url, registered, displayname, firstname,
	 *            lastname, nickname, description
	 * 
	 * @throws WPMissingDataException
	 *             if any of the data fields is missing.
	 */
	public void setUserData(Bundle userData) {
		if (userData.getString("username") != null) {
			setUsername(userData.getString("username"));
		} else {
			throw new WPMissingDataException("username");
		}

		if (userData.getString("nicename") != null) {
			setNicename(userData.getString("nicename"));
		} else {
			throw new WPMissingDataException("nicename");
		}

		if (userData.getString("email") != null) {
			setEmail(userData.getString("email"));
		} else {
			throw new WPMissingDataException("email");
		}

		if (userData.getString("url") != null) {
			setUrl(userData.getString("url"));
		} else {
			throw new WPMissingDataException("url");
		}

		if (userData.getString("registered") != null) {
			setRegistered(userData.getString("registered"));
		} else {
			throw new WPMissingDataException("registered");
		}

		if (userData.getString("displayname") != null) {
			setDisplayname(userData.getString("displayname"));
		} else {
			throw new WPMissingDataException("displayname");
		}

		if (userData.getString("firstname") != null) {
			setFirstname(userData.getString("firstname"));
		} else {
			throw new WPMissingDataException("firstname");
		}

		if (userData.getString("lastname") != null) {
			setLastname(userData.getString("lastname"));
		} else {
			throw new WPMissingDataException("lastname");
		}

		if (userData.getString("nickname") != null) {
			setNickname(userData.getString("nickname"));
		} else {
			throw new WPMissingDataException("nickname");
		}

		if (userData.getString("description") != null) {
			setDescription(userData.getString("description"));
		} else {
			throw new WPMissingDataException("description");
		}

		if (userData.getString("avatar") != null) {
			setAvatar(userData.getString("avatar"));
		} else {
			throw new WPMissingDataException("avatar");
		}
	}

	protected void setUsername(String username) {
		this.username = username;
	}

	public static Bundle getBundle(WPUser user) {
		Bundle userBundle = new Bundle();

		userBundle.putInt("id", user.getId());
		userBundle.putString("username", user.getUsername());
		userBundle.putString("nicename", user.getNicename());
		userBundle.putString("url", user.getUrl());
		userBundle.putString("email", user.getEmail());
		userBundle.putString("registerd", user.getRegistered());
		userBundle.putString("display_name", user.getDisplayname());
		userBundle.putString("firstname", user.getFirstname());
		userBundle.putString("lastname", user.getLastname());
		userBundle.putString("nickname", user.getNickname());
		userBundle.putString("desc", user.getDescription());
		userBundle.putString("avatar", user.getAvatar());
		userBundle.putString("capabilities",
				user.capabilities.getJsonCapabilities());
		return userBundle;

	}

	private class WPMissingDataException extends RuntimeException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public WPMissingDataException(String message) {
			super("Missing data field \"" + message
					+ "\", please check your bundle.");

		}
	}

	private class WPRole {
		private String jsonRoles;

		private boolean administrator = false;
		private boolean editor = false;
		private boolean subscriber = false;
		private boolean contributor = false;
		private boolean author = false;

		WPRole(JSONObject capabilities) {
			jsonRoles = capabilities.toString();

			try {
				if (capabilities.get(WPUser.ADMINISTRATOR).equals("true")) {
					administrator = true;
				}
			} catch (JSONException e) {
			}

			try {
				if (capabilities.get(WPUser.EDITOR).equals("true")) {
					editor = true;
				}
			} catch (JSONException e) {
			}

			try {
				if (capabilities.get(WPUser.CONTRIBUTOR).equals("true")) {
					contributor = true;
				}
			} catch (JSONException e) {
			}

			try {
				if (capabilities.get(WPUser.AUTHOR).equals("true")) {
					author = true;
				}
			} catch (JSONException e) {
			}

			try {
				if (capabilities.get(WPUser.SUBSCRIBER).equals("true")) {
					subscriber = true;
				}
			} catch (JSONException e) {
			}
		}

		public String getJsonCapabilities() {
			return jsonRoles.toString();
		}
	}
}
