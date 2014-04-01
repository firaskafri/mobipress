package com.jogeeks.wordpress.listeners;

import java.util.ArrayList;

import com.jogeeks.wordpress.WPComment;
import com.jogeeks.wordpress.WPPost;

public interface OnCommentsReceivedListener {
	public void onCommentsReceived(WPPost post, ArrayList<WPComment> comments);
}
