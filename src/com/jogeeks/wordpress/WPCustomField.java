package com.jogeeks.wordpress;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WPCustomField implements Serializable, NameValuePair {
	public static final String ADD_POST_META = "meta/add_post_meta/";
	public static final String UPDATE_POST_META = "meta/update_post_meta/";
	public static final String DELETE_POST_META = "meta/delete_post_meta/";
	public static final String GET_POST_CUSTOM = "meta/get_post_custom/";
	public static final String GET_POST_KEYS = "meta/get_post_custom_keys/";
	public static final String GET_POST_VALUES = "meta/get_post_custom_values/";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BasicNameValuePair customField;

	public WPCustomField(String name, String value) {
		customField = new BasicNameValuePair(name, value);
	}

	@Override
	public String getName() {
		return customField.getName();
	}

	@Override
	public String getValue() {
		return customField.getValue();
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeChars(customField.getName());
		out.writeChars(customField.getValue());
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		customField = new BasicNameValuePair(in.readLine(), in.readLine());
	}

	private void readObjectNoData() throws ObjectStreamException {
		// nothing to do
	}

	protected static ArrayList<WPCustomField> parseMetaResponse(
			JSONObject response) {
		ArrayList<WPCustomField> customeFields = new ArrayList<WPCustomField>();
		Iterator<?> iterator;
		try {
			JSONObject customFields = response;
			iterator = customFields.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				JSONArray customfield = customFields.getJSONArray(key);
				customeFields.add(new WPCustomField(key, customfield
						.getString(0)));

				Log.d(key, customfield.getString(0));
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return customeFields;
	}

	protected static ArrayList<String> parseCustomValues(JSONArray response) {
		ArrayList<String> customValues = new ArrayList<String>();
		JSONArray customFields = response;

		try {
			for (int i = 0; i <= customFields.length(); i++) {
				customValues.add(customFields.getString(i));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customValues;
	}
	
	protected static ArrayList<String> parseCustomKeys(JSONArray response) {
		ArrayList<String> customKeys = new ArrayList<String>();
		JSONArray customFields = response;

		try {
			for (int i = 0; i <= customFields.length(); i++) {
				customKeys.add(customFields.getString(i));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customKeys;
	}
}
