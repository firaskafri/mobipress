package com.jogeeks.wordpress.listeners;

import org.json.JSONObject;

public interface OnApiRequestListener {
	public void OnApiRequestResponse(JSONObject response);
}
