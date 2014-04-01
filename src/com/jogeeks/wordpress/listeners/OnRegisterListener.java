package com.jogeeks.wordpress.listeners;

public interface OnRegisterListener {
	void onRegisterStart();

	void OnRegisterSuccess(int error);

	void OnRegisterFailure(int error);

}
