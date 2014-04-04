package com.jogeeks.wordpress.listeners;

public interface OnCustomFieldsListener {
	public void OnMetaDataAdded();
	public void OnMetaDataDeleted();
	public void OnMetaDataUpdated();
	public void OnCustomPostMetaReceived();
	public void OnCustomPostMetaKeysReceived();
	public void OnCustomPostMetaValuesReceived();
}
