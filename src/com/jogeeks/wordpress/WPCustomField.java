package com.jogeeks.wordpress;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class WPCustomField implements Serializable, NameValuePair{
	public static final String ADD_POST_META = "meta/add_post_meta/";
	public static final String UPDATE_POST_META = "meta/update_post_meta/";
	public static final String DELETE_POST_META = "meta/delete_post_meta/";
	public static final String GET_POST_CUSTOM = "meta/get_post_custom/";
	public static final String GET_POST_KEYS = "meta/get_post_custom_keys/";
	public static final String GET_POST_VALUES = "meta/get_post_custom_values/";

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private BasicNameValuePair customField;

	public WPCustomField(String name, String value) {
		customField = new BasicNameValuePair(name, value);
	}

	@Override
	public String getName() {
		return customField.getName();
	}

	@Override
	public String getValue() {
		return customField.getValue();
	}
	

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeChars(customField.getName());
        out.writeChars(customField.getValue());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    	customField = new BasicNameValuePair(in.readLine(), in.readLine());
    }

    private void readObjectNoData() throws ObjectStreamException {
        // nothing to do
    }
}
