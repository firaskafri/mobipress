package com.jogeeks.wordpress;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

import com.loopj.android.http.RequestParams;

public class WPQuery extends RequestParams implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	//http://codex.wordpress.org/Class_Reference/WP_this#Parameters
	//===================================================
	final public static String ORDER = "order";
	final public static String DESC = "ASC";
	final public static String ASC = "DESC";
	private boolean orderResult = false; //false is default DESC, true is ASC

	//===================================================
	final public static String ORDER_BY = "orderby";
	//===================================================
	
	public WPQuery(){

	}
	
	public void orderDescending(){
		orderResult = false;
	}
	
	public void orderAscending(){
		orderResult = true;
		add(ORDER, ASC);
	}
	
	public RequestParams getQuery(){
		return this;
	}
	
	public void orderByID(){
		this.add(ORDER_BY, OrderBy.ID);
	}
	
	public void orderByAuthor(){
		this.add(ORDER_BY, OrderBy.AUTHOR);
	}
	
	public void orderByTitle(){
		this.add(ORDER_BY, OrderBy.TITLE);
	}
	
	public void orderByName(){
		this.add(ORDER_BY, OrderBy.NAME);
	}
	
	public void orderByDate(){
		this.add(ORDER_BY, OrderBy.DATE);
	}
	
	public void orderByModificationDate(){
		this.add(ORDER_BY, OrderBy.MODIFIED);
	}
	
	public void orderByParent(){
		this.add(ORDER_BY, OrderBy.PARENT);
	}
	
	public void orderByRandom(){
		this.add(ORDER_BY, OrderBy.RANDOM);
	}
	
	public void orderByCommentCount(){
		this.add(ORDER_BY, OrderBy.COMMENT_COUNT);
	}
	
	public void orderByMenuOrder(){
		this.add(ORDER_BY, OrderBy.MENU_ORDER);
	}
	
	public void orderByMetaValue(String key){
		this.add(ORDER_BY, OrderBy.META_VALUE);
		this.add("meta_key", key);
	}
	
	public void orderByMetaValueNumber(){
		this.add(ORDER_BY, OrderBy.META_VALUE_NUM);
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		for(int i = 0; i < this.getParamsList().size();i++){
			out.writeChars(this.getParamsList().get(i).getName());
			out.writeChars(this.getParamsList().get(i).getValue());
		}
	}

	private void readObject(ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		for(int i = 0; i < this.getParamsList().size();i++){
			add(in.readLine(), in.readLine());
		}
	}

	private void readObjectNoData() throws ObjectStreamException {
		// nothing to do
	}

	private class OrderBy{
		final protected static String NONE = "none";
		final protected static String ID = "ID";
		final protected static String AUTHOR = "author";
		final protected static String TITLE = "title";
		final protected static String NAME = "name";
		final protected static String DATE = "date";
		final protected static String MODIFIED = "modified";
		final protected static String PARENT = "parent";
		final protected static String RANDOM = "rand";
		final protected static String COMMENT_COUNT = "comment_count";
		final protected static String MENU_ORDER = "menu_order";
		final protected static String META_VALUE = "meta_value";
		final protected static String META_VALUE_NUM = "meta_value_num";
		final protected static String POST_IN = "post__in"; //TODO
	}
}
