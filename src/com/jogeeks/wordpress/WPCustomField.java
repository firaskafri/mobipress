package com.jogeeks.wordpress;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class WPCustomField implements Serializable, NameValuePair{

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
