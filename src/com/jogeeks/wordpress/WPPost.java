package com.jogeeks.wordpress;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;

public class WPPost {
	public static final boolean COMMENT_OPEN = true;
	public static final boolean COMMENT_CLOSED = false;

	public static final String POST_URL = "get_post";
	public static final String PAGE_URL = "get_page";

	public static final String POSTS_URL = "get_posts";
	public static final String RECENT_POSTS_URL = "get_recent_posts";
	public static final String DATE_POSTS_URL = "get_date_posts";
	public static final String CATEGORY_POSTS_URL = "get_category_posts";
	public static final String TAG_POSTS_URL = "get_tag_posts";
	public static final String AUTHOR_POSTS_URL = "get_author_posts";

	public static final String SEARCH_POSTS_URL = "get_search_results";

	public static final String PAGE_INDEX_URL = "get_page_index";

	public static final String CREATE_POST_URL = "posts/create_post/";
	public static final String UPDATE_POST_URL = "posts/update_post";
	public static final String DELTE_POST_URL = "posts/delete_post";

	public static final String PUBLISHED = "publish";
	public static final String DRAFT = "draft";

	public static final String NO_TITLE = "No title";
	public static final String NO_PLAIN_TITLE = "No plain title";
	public static final String NO_CONTENT = "No content";
	public static final String NO_EXCERPT = "No excerpt";

	String nonce;

	// Prime elements
	private int id;
	private String type; // page or post
	private String slug;
	private String url;
	private String title;
	private String titlePlain;
	private String content;
	private String excerpt;
	private String date;
	private String modified;

	private String status;

	private ArrayList<WPCategory> categories;
	private ArrayList<WPTag> tags;

	private WPAuthor author;

	private ArrayList<WPComment> comments;

	private WPAtachment attachments;

	private int commentsCount;
	private boolean commentingStatus; // ("open" or "closed")

	private ArrayList<WPCustomField> customeFields;
	
	public ArrayList<WPCustomField> getCustomeFields() {
		return customeFields;
	}

	public void setCustomeFields(ArrayList<WPCustomField> customeFields) {
		this.customeFields = customeFields;
	}

	private String taxonomy; // ?? dont know what is this.

	public WPPost() {
		
	}

	public WPPost(Bundle postBundle) {
		ArrayList<Bundle> catsBundle = new ArrayList<Bundle>();
		ArrayList<Bundle> tagsBundle = new ArrayList<Bundle>();
		Bundle authorBundle = new Bundle();
		ArrayList<Bundle> commentsBundle = new ArrayList<Bundle>();

		categories = new ArrayList<WPCategory>();
		tags = new ArrayList<WPTag>();
		attachments = new WPAtachment();
		comments = new ArrayList<WPComment>();
		customeFields = new ArrayList<WPCustomField>();

		id = postBundle.getInt("id");
		type = postBundle.getString("type");
		slug = postBundle.getString("slug");
		url = postBundle.getString("url");
		title = postBundle.getString("title");
		titlePlain = postBundle.getString("title_plain");
		content = postBundle.getString("content");
		excerpt = postBundle.getString("excerpt");
		date = postBundle.getString("date");
		modified = postBundle.getString("modified");

		postBundle.getParcelableArrayList("cats");
		postBundle.getParcelableArrayList("tags");
		postBundle.getParcelableArrayList("comments");
		postBundle.getSerializable("custom_fields");
		catsBundle = postBundle.getParcelableArrayList("cats");
		for (int i = 0; i < catsBundle.size(); i++) {
			categories.add(new WPCategory(catsBundle.get(i)));
		}

		tagsBundle = postBundle.getParcelableArrayList("tags");
		for (int i = 0; i < tagsBundle.size(); i++) {
			tags.add(new WPTag(tagsBundle.get(i)));
		}

		authorBundle = postBundle.getBundle("author");

		author = new WPAuthor(authorBundle);

		commentsBundle = postBundle.getParcelableArrayList("comments");
		for (int i = 0; i < commentsBundle.size(); i++) {
			comments.add(new WPComment(commentsBundle.get(i)));
		}

		ArrayList<String> atts = new ArrayList<String>();
		atts = postBundle.getStringArrayList("attachments");
		attachments.setFullImage(atts.get(0));
		attachments.setThumbnailImage(atts.get(1));
		attachments.setMediumImage(atts.get(2));
		attachments.setPostThumbnailImage(atts.get(3));

		commentingStatus = postBundle.getBoolean("commenting_status");
		commentsCount = postBundle.getInt("comment_count");
		// postData.put custom fields
	}

	public void setCategories(ArrayList<WPCategory> cats) {
		categories = cats;
	}

	public boolean hasOneComment() {
		if (commentsCount == 1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean noComments() {
		if (commentsCount == 0) {
			return true;
		} else {
			return false;
		}
	}

	public WPAtachment getAttachments() {
		return attachments;
	}

	public WPPost(JSONObject data) {
		try {
			setId(Integer.parseInt(data.getString("id")));
			setType(data.getString("type"));
			setSlug(data.getString("slug"));
			setUrl(data.getString("url"));
			setTitle(data.getString("title"));
			setTitlePlain(data.getString("title_plain"));
			setContent(data.getString("content"));
			setExcerpt(data.getString("excerpt"));
			setDate(data.getString("date"));
			setmodified(data.getString("modified"));

			comments = new ArrayList<WPComment>();
			categories = new ArrayList<WPCategory>();
			tags = new ArrayList<WPTag>();
			attachments = new WPAtachment();
			customeFields = new ArrayList<WPCustomField>();
			
			if (data.getJSONArray("attachments") != null
					&& data.getJSONArray("attachments").length() > 0
					&& !data.getJSONArray("attachments").isNull(0)) {
				JSONObject images = new JSONObject();
				images = data.getJSONArray("attachments").getJSONObject(0)
						.getJSONObject("images");

				attachments.setFullImage(images.getJSONObject("full")
						.getString("url").toString());
				attachments.setThumbnailImage(images.getJSONObject("thumbnail")
						.getString("url").toString());
				attachments.setMediumImage(images.getJSONObject("medium")
						.getString("url").toString());
				attachments.setPostThumbnailImage(images
						.getJSONObject("post-thumbnail").getString("url")
						.toString());
			}

			author = new WPAuthor(data.getJSONObject("author"));

			if (data.getString("comment_status").equals("open")) {
				setCommentingStatus(WPPost.COMMENT_OPEN);
			} else {
				setCommentingStatus(WPPost.COMMENT_CLOSED);
			}

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONArray catsArray = null;
		JSONArray tagsArray = null;
		JSONArray commentsArray = null;

		try {
			catsArray = data.getJSONArray("categories");
			tagsArray = data.getJSONArray("tags");
			commentsArray = data.getJSONArray("comments");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

		Iterator<?> iterator;
		try {
			JSONObject customFields = data.getJSONObject("custom_fields");
			iterator = customFields.keys();
			while (iterator.hasNext()) {
				   String key = (String)iterator.next();
				   JSONArray customfield = customFields.getJSONArray(key);
				   customeFields.add(new WPCustomField(key, customfield.getString(0)));
				   
				   Log.d(key, customfield.getString(0));
				}  
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// in case post doesn't have tags or cats, or returned from a newly
		// created post.
		int numCats = 0;
		int numTags = 0;
		setCommentsCount(0);
		try {
			numCats = catsArray.length();
		} catch (NullPointerException s) {
		}
		try {

			numTags = tagsArray.length();
		} catch (NullPointerException s) {
		}
		try {
			setCommentsCount(commentsArray.length());
		} catch (NullPointerException s) {
		}
		try {

			for (int i = 0; i < numCats; i++) {
				categories.add(new WPCategory(catsArray.getJSONObject(i)));
			}

			for (int i = 0; i < numTags; i++) {
				getTags().add(new WPTag(tagsArray.getJSONObject(i)));
			}

			for (int i = 0; i < getCommentsCount(); i++) {
				getComments().add(
						new WPComment(commentsArray.getJSONObject(i), this
								.getId()));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<WPCategory> getCategories() {
		return categories;
	}

	public WPAuthor getAuthor() {
		return author;
	}

	public String getCategoriesAsString() {
		String cats = "";
		if (categories.size() == 0) {
			return WPCategory.UNCATEGORIZED;
		} else {
			for (int i = 0; i < categories.size(); i++) {
				if (i == categories.size() - 1) {
					cats = cats + categories.get(i).getTitle();
				} else {
					cats = cats + categories.get(i).getTitle() + ", ";
				}
			}
		}
		return cats;
	}

	public ArrayList<WPTag> getTags() {
		return tags;
	}

	public void setTags(ArrayList<WPTag> tags) {
		this.tags = tags;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		if (title == null) {
			return NO_TITLE;
		}
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitlePlain() {
		if (titlePlain == null) {
			return NO_PLAIN_TITLE;
		}
		return titlePlain;
	}

	public void setTitlePlain(String titlePlain) {
		this.titlePlain = titlePlain;
	}

	public String getContent() {
		if (content == null) {
			return NO_CONTENT;
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExcerpt() {
		if (titlePlain == null) {
			return NO_EXCERPT;
		}
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getModified() {
		return modified;
	}

	public void setmodified(String modified) {
		this.modified = modified;
	}

	public boolean isCommentingStatus() {
		return commentingStatus;
	}

	public void setCommentingStatus(boolean commentingStatus) {
		this.commentingStatus = commentingStatus;
	}

	public static Bundle getBundle(WPPost post) {
		Bundle postData = new Bundle();
		ArrayList<Bundle> catsData = new ArrayList<Bundle>();
		ArrayList<Bundle> tagsData = new ArrayList<Bundle>();
		// Bundle authorData = new Bundle();
		ArrayList<Bundle> commentsData = new ArrayList<Bundle>();
		// TODO: finish adding all of the
		/*
		 * 
		 * private ArrayList<WPCategory> categories; private ArrayList<WPTag>
		 * tags;
		 * 
		 * private WPAuthor author;
		 * 
		 * private ArrayList<WPComment> comments;
		 * 
		 * private ArrayList<String> attachments; // ?? should I make a class
		 * for this
		 * 
		 * private int commentsCount; private boolean commentingStatus; //
		 * ("open" or "closed")
		 * 
		 * private String thumbURL; // (only included if a post thumbnail has
		 * been // specified)
		 * 
		 * private ArrayList<BasicNameValuePair> customeFields; private String *
		 * taxonomy; // ?? dont know what is this.
		 */
		postData.putInt("id", post.getId());
		postData.putString("type", post.getType());
		postData.putString("slug", post.getSlug());
		postData.putString("url", post.getUrl());
		postData.putString("slug", post.getSlug());
		postData.putString("title", post.getTitle());
		// postData.putString("title_plain", post.getTitlePlain());
		postData.putString("content", post.getContent());
		postData.putString("excerpt", post.getExcerpt());
		postData.putString("date", post.getDate());
		postData.putString("modified", post.getModified());

		for (int i = 0; post.getCategories() != null
				&& i < post.getCategories().size(); i++) {
			catsData.add(WPCategory.getBundle(post.getCategories().get(i)));
		}

		for (int i = 0; post.getTags() != null && i < post.getTags().size(); i++) {
			tagsData.add(WPTag.getBundle(post.getTags().get(i)));
		}

		postData.putBundle("author", WPAuthor.getBundle(post.getAuthor()));

		for (int i = 0; post.getComments() != null
				&& i < post.getComments().size(); i++) {
			commentsData.add(WPComment.getBundle(post.getComments().get(i)));
		}
		
		postData.putParcelableArrayList("cats", catsData);
		postData.putParcelableArrayList("tags", tagsData);
		postData.putParcelableArrayList("comments", commentsData);
		postData.putSerializable("custom_fields", post.getCustomeFields());
		
		ArrayList<String> atts = new ArrayList<String>();
		atts.add(post.getAttachments().getFullImage());
		atts.add(post.getAttachments().getThumbnailImage());
		atts.add(post.getAttachments().getMediumImage());
		atts.add(post.getAttachments().getPostThumbnailImage());

		postData.putStringArrayList("attachments", atts);
		postData.putBoolean("commenting_status", post.isCommentingStatus());
		postData.putInt("comment_count", post.getCommentsCount());

		// postData.put custom fields

		return postData;

	}

	public ArrayList<WPComment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<WPComment> comments) {
		this.comments = comments;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
