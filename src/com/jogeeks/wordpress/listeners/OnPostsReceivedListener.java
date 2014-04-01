package com.jogeeks.wordpress.listeners;

import java.util.HashMap;
import java.util.List;

import com.jogeeks.wordpress.WPPost;

public interface OnPostsReceivedListener {
	public void onPostsReceived(List<WPPost> posts,
			HashMap<String, String> wpResponseMeta);

	public void onNoPosts();
}
