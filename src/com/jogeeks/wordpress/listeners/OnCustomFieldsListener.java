package com.jogeeks.wordpress.listeners;

import java.util.ArrayList;

import com.jogeeks.wordpress.WPCustomField;

public interface OnCustomFieldsListener {
	public void OnMetaDataAdded();
	public void OnMetaDataDeleted();
	public void OnMetaDataUpdated();
	public void OnCustomPostMetaReceived(ArrayList<WPCustomField> metaData);
	public void OnCustomPostMetaKeysReceived(ArrayList<String> metaData);
	public void OnCustomPostMetaValuesReceived(ArrayList<String> metaData);
}
