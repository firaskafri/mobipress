package com.jogeeks.internet;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Communication {

	public Communication() {

	}

	/**
	 * Returns true if device is connected to intenret The ACCESS_NETWORK_STATE
	 * permission should be added to the manifest.
	 */
	public boolean isOnline(Context context) {

		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the response as a JSONObject after requesting the passed URL The
	 * INTERNET permission should be added to the manifest.
	 */
	public JSONObject getJSONResponse(String url) {
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.getJSONFromUrl(url,
				new ArrayList<NameValuePair>());
		return json;
	}

	/**
	 * Returns the response as a JSONObject after requesting the passed URL The
	 * INTERNET permission should be added to the manifest.
	 * 
	 * 
	 * @param url
	 *            The URL to request and POST values to.
	 * @param parameters
	 *            ArrayList<NameValuePair> POST values.
	 */
	public JSONObject getJSONResponse(String url,
			ArrayList<NameValuePair> parameters) {
		JSONParser jsonParser = new JSONParser();
		JSONObject json = jsonParser.getJSONFromUrl(url, parameters);
		return json;
	}

	/**
	 * Returns the HTTP POST response The INTERNET permission should be added to
	 * the manifest.
	 * 
	 * 
	 * @param url
	 *            The URL to request and POST values to.
	 * @param parameters
	 *            ArrayList<NameValuePair> POST values.
	 */
	public HttpResponse postData(String url, ArrayList<NameValuePair> parameters) {
		HttpResponse response = null;
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(parameters));

			// Execute HTTP Post Request
			response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		return response;
	}
}
