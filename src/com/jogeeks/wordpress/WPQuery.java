package com.jogeeks.wordpress;

import java.io.Serializable;
import java.util.ArrayList;

import com.loopj.android.http.RequestParams;

public class WPQuery extends RequestParams implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7758657104483782419L;
	
	//http://codex.wordpress.org/Class_Reference/WP_Query#Parameters
	//===================================================
	final public static String ORDER = "order";
	final public static String DESC = "ASC";
	final public static String ASC = "DESC";
	private boolean orderResult = false; //false is default DESC, true is ASC

	//===================================================
	final public static String ORDER_BY = "orderby";
	RequestParams query = new RequestParams();
	//===================================================
	
	public WPQuery(){

	}
	
	public void orderDescending(){
		orderResult = false;
	}
	
	public void orderAscending(){
		orderResult = true;
		query.add(ORDER, ASC);
	}
	
	public RequestParams getOrderByQuery(){
		return query;
	}
	
	public void orderByID(){
		query.add(ORDER_BY, OrderBy.ID);
	}
	
	public void orderByAuthor(){
		query.add(ORDER_BY, OrderBy.AUTHOR);
	}
	
	public void orderByTitle(){
		query.add(ORDER_BY, OrderBy.TITLE);
	}
	
	public void orderByName(){
		query.add(ORDER_BY, OrderBy.NAME);
	}
	
	public void orderByDate(){
		query.add(ORDER_BY, OrderBy.DATE);
	}
	
	public void orderByModificationDate(){
		query.add(ORDER_BY, OrderBy.MODIFIED);
	}
	
	public void orderByParent(){
		query.add(ORDER_BY, OrderBy.PARENT);
	}
	
	public void orderByRandom(){
		query.add(ORDER_BY, OrderBy.RANDOM);
	}
	
	public void orderByCommentCount(){
		query.add(ORDER_BY, OrderBy.COMMENT_COUNT);
	}
	
	public void orderByMenuOrder(){
		query.add(ORDER_BY, OrderBy.MENU_ORDER);
	}
	
	public void orderByMetaValue(String key){
		query.add(ORDER_BY, OrderBy.META_VALUE);
		query.add("meta_key", key);
	}
	
	public void orderByMetaValueNumber(){
		query.add(ORDER_BY, OrderBy.META_VALUE_NUM);
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
