package com.jogeeks.wordpress;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.jogeeks.internet.Communication;
import com.jogeeks.mobipress.R;

public class WPComment {

	static final String SUBMIT_COMMENT_URL = "submit_comment";
	static final String NO_COMMENTS = "No comments";
	static final String ONE_COMMENT = "One comment";

	private int id;
	private String name;
	private String url;
	private String date;
	private String content;
	private int parent;
	private int postId;

	public WPComment() {

	}

	public WPComment(JSONObject comment, int postId) {
		try {
			setId(Integer.parseInt(comment.getString("id")));
			setName(comment.getString("name"));
			setUrl(comment.getString("url"));
			setDate(comment.getString("date"));
			setContent(comment.getString("content"));
			setParent(Integer.parseInt(comment.getString("parent")));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public WPComment(Bundle bundle) {
		id = bundle.getInt("id");
		name = bundle.getString("name");
		url = bundle.getString("url");
		date = bundle.getString("date");
		content = bundle.getString("content");
		parent = bundle.getInt("parent");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public void reportComment(int commentId, String comment, Context context) {
		new ReportComment().execute(commentId, comment, context);
	}

	private class ReportComment extends AsyncTask<Object, Integer, Integer> {
		private String reportCommentURL;

		@Override
		protected void onPostExecute(Integer result) {

		}

		@Override
		protected Integer doInBackground(Object... params) {
			Context con = (Context) params[3];
			reportCommentURL = con.getString(R.string.url).concat(
					con.getString(R.string.report_comment));

			reportCommentURL = reportCommentURL.replace("CID",
					Integer.toString((Integer) params[0]));
			reportCommentURL = reportCommentURL.replace("COMMENT",
					(String) params[1]);

			new Communication().getJSONResponse(reportCommentURL);

			return 0;
		}

	}

	public static Bundle getBundle(WPComment comment) {
		Bundle commentBundle = new Bundle();

		commentBundle.putInt("id", comment.getId());
		commentBundle.putString("name", comment.getName());
		commentBundle.putString("url", comment.getUrl());
		commentBundle.putString("date", comment.getDate());
		commentBundle.putString("content", comment.getContent());
		commentBundle.putInt("parent", comment.getParent());

		return commentBundle;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}
}
