package com.jogeeks.wordpress.listeners;

import com.jogeeks.wordpress.WPSession;

public interface OnLoginListener {

	void OnLoginStart();

	void OnLoginSuccess(WPSession session);

	void OnLoginFailure(WPSession session);

}
