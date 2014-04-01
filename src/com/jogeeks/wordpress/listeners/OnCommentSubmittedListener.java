package com.jogeeks.wordpress.listeners;

import com.jogeeks.wordpress.WPComment;

public interface OnCommentSubmittedListener {
	public void onCommentSubmitted(WPComment comment);
}
