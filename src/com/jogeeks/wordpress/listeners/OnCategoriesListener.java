package com.jogeeks.wordpress.listeners;

import java.util.ArrayList;

import com.jogeeks.wordpress.WPCategory;

public interface OnCategoriesListener {
	public void onCategoriesReceived(ArrayList<WPCategory> cats);
}