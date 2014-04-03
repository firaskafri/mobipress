package com.jogeeks.wordpress;

import java.util.ArrayList;

public class WPQuery extends ArrayList<String>{
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
	private String orderBy;
	//===================================================
	
	public WPQuery(){
		
	}
	
	public void orderDescending(){
		orderResult = false;
	}
	
	public void orderAscending(){
		orderResult = true;
	}
	
	public String getOrderByQuery(){
		return orderBy;
	}
	
	public void orderByID(){
		orderBy = OrderBy.ID;
	}
	
	public void orderByAuthor(){
		orderBy = OrderBy.AUTHOR;
	}
	
	public void orderByTitle(){
		orderBy = OrderBy.TITLE;
	}
	
	public void orderByName(){
		orderBy = OrderBy.NAME;
	}
	
	public void orderByDate(){
		orderBy = OrderBy.DATE;
	}
	
	public void orderByModificationDate(){
		orderBy = OrderBy.MODIFIED;
	}
	
	public void orderByParent(){
		orderBy = OrderBy.PARENT;
	}
	
	public void orderByRandom(){
		orderBy = OrderBy.RANDOM;
	}
	
	public void orderByCommentCount(){
		orderBy = OrderBy.COMMENT_COUNT;
	}
	
	public void orderByMenuOrder(){
		orderBy = OrderBy.MENU_ORDER;
	}
	
	public void orderByMetaValue(){
		orderBy = OrderBy.META_VALUE;
	}
	
	public void orderByMetaValueNumber(){
		orderBy = OrderBy.META_VALUE_NUM;
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
