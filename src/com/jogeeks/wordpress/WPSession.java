package com.jogeeks.wordpress;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class WPSession extends JSONObject {
	private int status;

	private Context context;

	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	private WPUser currentUser;

	/**
	 * Pass context to initialize
	 * 
	 * @param context
	 */
	public WPSession(Context context) {
		this.context = context;
		preferences = this.context.getSharedPreferences(
				"libdroid-wordpress-session", 0);
		editor = preferences.edit();

	}

	protected void setSession(JSONObject userdata) throws JSONException {
		JSONObject userObject = userdata.getJSONObject("user");

		editor.putString("cookie", userdata.getString("cookie"));
		editor.putInt("id", userObject.getInt("id"));
		editor.putString("username", userObject.getString("username"));
		editor.putString("nicename", userObject.getString("nicename"));
		editor.putString("email", userObject.getString("email"));
		editor.putString("url", userObject.getString("url"));
		editor.putString("registered", userObject.getString("registered"));
		editor.putString("displayname", userObject.getString("displayname"));
		editor.putString("firstname", userObject.getString("firstname"));
		editor.putString("lastname", userObject.getString("lastname"));
		editor.putString("nickname", userObject.getString("nickname"));
		editor.putString("description", userObject.getString("description"));
		editor.putString("avatar", userObject.getString("avatar"));
		editor.putString("capabilities",
				userObject.getJSONObject("capabilities").toString());

		editor.commit();
	}

	private Bundle prepareUserBundle() {
		Bundle userData = new Bundle();
		userData.putInt("id", getId());
		userData.putString("cookie", getCookie());
		userData.putString("username", getUsername());
		userData.putString("nicename", getNicename());
		userData.putString("url", getUrl());
		userData.putString("email", getEmail());
		userData.putString("registerd", getRegistrationDate());
		userData.putString("display_name", getDisplayname());
		userData.putString("firstname", getFirstname());
		userData.putString("lastname", getLastname());
		userData.putString("nickname", getNickname());
		userData.putString("desc", getDescription());
		userData.putString("avatar", getAvatar());
		userData.putString("capabilities", getCapabilities());
		return userData;
	}

	public WPUser getCurrentUser() {
		currentUser = new WPUser(prepareUserBundle());
		return currentUser;
	}

	/**
	 * Returns status code
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @return true if session is valid
	 */
	public boolean isValid() {
		// TODO api/auth/get_currentuserinfo/?cookie=
		return false;
	}

	/**
	 * @return true if this session is valid
	 */
	public boolean isLoggedIn() {
		// TODO api/auth/get_currentuserinfo/?cookie=
		if (getId() != -1) {
			return true;
		} else {
			return false;
		}
	}

	public void logOut() {
		editor.putString("cookie", "-1");
		editor.putInt("id", -1);
		editor.putString("username", "-1");
		editor.putString("nicename", "-1");
		editor.putString("email", "-1");
		editor.putString("url", "-1");
		editor.putString("registered", "-1");
		editor.putString("displayname", "-1");
		editor.putString("firstname", "-1");
		editor.putString("lastname", "-1");
		editor.putString("nickname", "-1");
		editor.putString("description", "-1");
		editor.putString("avatar", "-1");
		editor.putString("capabilities", "-1");

		editor.commit();
	}

	/**
	 * @return true if session was valid and got complete data of the current
	 *         authenticated user.
	 */
	public boolean getCurrentSessionInfo() {
		// TODO api/auth/get_currentuserinfo/?cookie=
		return false;
	}

	protected void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Returns WordPress cookie, -1 if failed
	 */
	public String getCookie() {
		return preferences.getString("cookie", "-1");
	}

	/**
	 * Returns avatar url, -1 if failed
	 */
	public String getAvatar() {
		return preferences.getString("avatar", "-1");
	}

	/**
	 * Returns capabilities as JSONObject, -1 if failed
	 */
	private String getCapabilities() {
		return preferences.getString("capabilities", "-1");
	}

	/**
	 * Use to set/update user's cookie
	 */
	protected void setCookie(String cookie) {
		editor.putString("cookie", cookie);
	}

	/**
	 * Returns authenticated user ID
	 */
	public int getId() {
		return preferences.getInt("id", -1);
	}

	/**
	 * Returns user name
	 */
	public String getUsername() {
		return preferences.getString("username", "-1");
	}

	public String getNicename() {
		return preferences.getString("nicename", "-1");
	}

	/**
	 * Returns user's email
	 */
	public String getEmail() {
		return preferences.getString("email", "-1");
	}

	public String getUrl() {
		return preferences.getString("url", "-1");
	}

	public String getRegistrationDate() {
		return preferences.getString("registered", "-1");
	}

	public String getDisplayname() {
		return preferences.getString("displayname", "-1");
	}

	public String getFirstname() {
		return preferences.getString("firstname", "-1");
	}

	public String getLastname() {
		return preferences.getString("firstname", "-1");
	}

	public String getNickname() {
		return preferences.getString("nickname", "-1");
	}

	public String getDescription() {
		return preferences.getString("description", "-1");
	}

	protected void setNonce(String nonce) {
		editor.putString("nonce", nonce);
	}

	public String getNonce() {
		return preferences.getString("nonce", "-1");
	}

	public void updateUserData(WPUser newData) {
		editor.putInt("id", newData.getId());
		editor.putString("username", newData.getUsername());
		editor.putString("nicename", newData.getNicename());
		editor.putString("email", newData.getEmail());
		editor.putString("url", newData.getUrl());
		editor.putString("registered", newData.getRegistered());
		editor.putString("displayname", newData.getDisplayname());
		editor.putString("firstname", newData.getFirstname());
		editor.putString("lastname", newData.getLastname());
		editor.putString("nickname", newData.getNickname());
		editor.putString("description", newData.getDescription());
	}

}
