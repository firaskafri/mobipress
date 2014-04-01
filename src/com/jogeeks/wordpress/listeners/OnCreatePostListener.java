package com.jogeeks.wordpress.listeners;

import com.jogeeks.wordpress.WPPost;

public interface OnCreatePostListener {
	public void OnPostCreated(WPPost post);

	public void OnPostCreatedFailed();

}
