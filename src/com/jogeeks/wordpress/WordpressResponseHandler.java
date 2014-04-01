package com.jogeeks.wordpress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.jogeeks.wordpress.listeners.OnCategoriesListener;
import com.jogeeks.wordpress.listeners.OnCommentSubmittedListener;
import com.jogeeks.wordpress.listeners.OnCommentsReceivedListener;
import com.jogeeks.wordpress.listeners.OnConnectionFailureListener;
import com.jogeeks.wordpress.listeners.OnCreatePostListener;
import com.jogeeks.wordpress.listeners.OnPostReceivedListener;
import com.jogeeks.wordpress.listeners.OnPostsReceivedListener;
import com.loopj.android.http.JsonHttpResponseHandler;

public class WordpressResponseHandler<OBJ_TYPE> extends JsonHttpResponseHandler {

	private OnCategoriesListener onCategoriesListener;
	private OnPostsReceivedListener onPostsReceivedListener;
	private OnPostReceivedListener onPostReceivedListener;
	private OnCommentsReceivedListener onCommentsReceivedListener;
	private OnCommentSubmittedListener onCommentSubmittedListener;
	private OnCreatePostListener onCreatePostListener;
	private OnConnectionFailureListener onConnectionFailureListener;

	public void setOnConnectionFailureListener(OnConnectionFailureListener ocfl) {
		onConnectionFailureListener = ocfl;
	}

	public void setOnCategoriesListener(OnCategoriesListener ocl) {
		onCategoriesListener = ocl;
	}

	public void setOnPostsReceivedListener(OnPostsReceivedListener oprl) {
		onPostsReceivedListener = oprl;
	}

	public void setOnPostReceivedListener(OnPostReceivedListener oprl) {
		onPostReceivedListener = oprl;
	}

	public void setOnCommentsReceivedListener(OnCommentsReceivedListener ocrl) {
		onCommentsReceivedListener = ocrl;
	}

	public void setOnCommentsSubmittedListener(OnCommentSubmittedListener ocsl) {
		onCommentSubmittedListener = ocsl;
	}

	public void setOnCreatePostListener(OnCreatePostListener opcl) {
		onCreatePostListener = opcl;
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
		super.onSuccess(statusCode, headers, response);

		try {
			Log.d("requesturi", super.getRequestURI().toString());

			String requestUri = super.getRequestURI().toString();
			String[] splits = requestUri
					.split("http://arabappz.net/secrets/api/");
			String controler = splits[1].split(Pattern.quote("?"))[0];

			Log.d("contorler", controler);

			/***** posts controller *****/
			if (controler.equalsIgnoreCase(WPPost.POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							parsePosts(response), parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.DATE_POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							parsePosts(response), parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.RECENT_POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							parsePosts(response), parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.SEARCH_POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							parsePosts(response), parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.CATEGORY_POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							parsePosts(response), parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.AUTHOR_POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							parsePosts(response), parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.TAG_POSTS_URL)) {
				onPostsReceivedListener.onPostsReceived(parsePosts(response),
						parseResponseMeta(response));
				/***** Post controller *****/
			} else if (controler.equalsIgnoreCase(WPPost.CREATE_POST_URL)) {
				onCreatePostListener.OnPostCreated(new WPPost(response
						.getJSONObject("post")));
			} else if (controler.equalsIgnoreCase(WPPost.DELTE_POST_URL)) {
				onPostDeleted();
			} else if (controler.equalsIgnoreCase(WPPost.UPDATE_POST_URL)) {
				onPostEdited(new WPPost(response));
			} else if (controler.equalsIgnoreCase(WPPost.POST_URL)) {
				WPPost post = new WPPost(response.getJSONObject("post"));
				onPostReceivedListener.onPostReceived(post);
				// TODO: maybe later make a parser just for comments (if there
				// is not object post then go to the comments listner)
				// onCommentsReceivedListener.onCommentsReceived(post,
				// post.getComments());
				/***** Page controller *****/
			} else if (controler.equalsIgnoreCase(WPPost.PAGE_URL)) {
				onCreatePostListener.OnPostCreated(new WPPost(response));
			}
			/***** Page controller *****/
			else if (controler.equalsIgnoreCase(Wordpress.NONCE_URL + "/")) {
				onNonceRecieved(parseNonce(response), "create_post");
			}
			/***** Comments controller *****/
			else if (controler.equalsIgnoreCase(WPComment.SUBMIT_COMMENT_URL)) {
				onCommentSubmittedListener.onCommentSubmitted(new WPComment(
						response, 0));

				/***** Users controller *****/

				/***** Categories controller *****/
			} else if (controler.equalsIgnoreCase(WPCategory.CATEGORY_INDEX)) {
				onCategoriesListener
						.onCategoriesReceived(parseCategories(response));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void onNonceRecieved(String nonce, String method) {

	}

	public void onPostEdited(WPPost post) {
		//
	}

	public void onPostDeleted() {
		//
	}

	public void onCommentRecieved(WPComment posts) {
		//
	}

	public void onCommentsRecieved(List<WPComment> comments) {
		//
	}

	public void onCommentSubmited(WPComment comment) {
		//
	}

	public void onCommentReported(WPComment comment) {
		//
	}

	public void onUserUpdated(WPUser user) {
		//
	}

	public void onUsersRecieved(List<WPUser> users) {
		//
	}

	public void onCategoriesReceived(ArrayList<WPCategory> cats) {
		//
	}

	protected ArrayList<WPCategory> parseCategories(JSONObject response)
			throws JSONException {
		JSONObject wpCats = response;
		int count = wpCats.getJSONArray("categories").length();
		ArrayList<WPCategory> cats = new ArrayList<WPCategory>();

		for (int i = 0; i < count; i++) {
			JSONObject jObj = wpCats.getJSONArray("categories")
					.getJSONObject(i);
			cats.add(new WPCategory(jObj));
		}

		return cats;
	}

	protected List<WPPost> parsePosts(JSONObject response) throws JSONException {
		JSONObject wpPosts = response;
		int count = wpPosts.getJSONArray("posts").length();
		List<WPPost> posts = new ArrayList<WPPost>();

		for (int i = 0; i < count; i++) {
			JSONObject jObj = wpPosts.getJSONArray("posts").getJSONObject(i);
			posts.add(new WPPost(jObj));
		}

		return posts;
	}

	protected HashMap<String, String> parseResponseMeta(JSONObject response)
			throws JSONException {
		int count = 0, countTotal = 0, pages = 0;
		count = response.getInt("count");

		// TODO fix from server side
		// count_total is not included in getByCategory response and when the
		// response is only one page
		try {
			countTotal = response.getInt("count_total");
		} catch (JSONException e) {

		}
		pages = response.getInt("pages");

		HashMap<String, String> responseMeta = new HashMap<String, String>();
		responseMeta.put("count", Integer.toString(count));
		responseMeta.put("count_total", Integer.toString(countTotal));
		responseMeta.put("pages", Integer.toString(pages));

		return responseMeta;
	}

	protected String parseNonce(JSONObject response) throws JSONException {
		String nonce;
		nonce = response.getString("nonce");
		Log.d("CreatePostNonceResponse", nonce);

		return nonce;
	}

	@Override
	public void onFailure(Throwable arg0, JSONObject arg1) {

		onConnectionFailureListener.OnConnectionFailed();
	}

}
