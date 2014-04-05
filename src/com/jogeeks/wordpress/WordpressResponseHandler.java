package com.jogeeks.wordpress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.jogeeks.wordpress.listeners.OnApiRequestListener;
import com.jogeeks.wordpress.listeners.OnCategoriesListener;
import com.jogeeks.wordpress.listeners.OnCommentSubmittedListener;
import com.jogeeks.wordpress.listeners.OnCommentsReceivedListener;
import com.jogeeks.wordpress.listeners.OnConnectionFailureListener;
import com.jogeeks.wordpress.listeners.OnCreatePostListener;
import com.jogeeks.wordpress.listeners.OnCustomFieldsListener;
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
	private OnCustomFieldsListener onCustomFieldsListener;
	private OnApiRequestListener onApiRequestListener;

	public void setOnApiRequestListener(OnApiRequestListener oar) {
		onApiRequestListener = oar;
	}
	
	public void setOnCustomFieldsListener(OnCustomFieldsListener ocfl) {
		onCustomFieldsListener = ocfl;
	}

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
							WPPost.parsePosts(response),
							Wordpress.parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.DATE_POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							WPPost.parsePosts(response),
							Wordpress.parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.RECENT_POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							WPPost.parsePosts(response),
							Wordpress.parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.SEARCH_POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							WPPost.parsePosts(response),
							Wordpress.parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.CATEGORY_POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							WPPost.parsePosts(response),
							Wordpress.parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.AUTHOR_POSTS_URL)) {
				if (response.getInt("count") != 0) {
					onPostsReceivedListener.onPostsReceived(
							WPPost.parsePosts(response),
							Wordpress.parseResponseMeta(response));
				} else {
					onPostsReceivedListener.onNoPosts();
				}
			} else if (controler.equalsIgnoreCase(WPPost.TAG_POSTS_URL)) {
				onPostsReceivedListener.onPostsReceived(
						WPPost.parsePosts(response),
						Wordpress.parseResponseMeta(response));
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
				onNonceRecieved(Wordpress.parseNonce(response), "create_post");
			}
			/***** Comments controller *****/
			else if (controler.equalsIgnoreCase(WPComment.SUBMIT_COMMENT_URL)) {
				onCommentSubmittedListener.onCommentSubmitted(new WPComment(
						response, 0));

				/***** Users controller *****/

				/***** Categories controller *****/
			} else if (controler.equalsIgnoreCase(WPCategory.CATEGORY_INDEX)) {
				onCategoriesListener.onCategoriesReceived(WPCategory
						.parseCategories(response));
				/***** Post custom fields controller *****/
				// TODO handle response
			} else if (controler.equalsIgnoreCase(WPCustomField.ADD_POST_META)) {
				onCustomFieldsListener.OnMetaDataAdded();
			} else if (controler
					.equalsIgnoreCase(WPCustomField.DELETE_POST_META)) {
				onCustomFieldsListener.OnMetaDataDeleted();
			} else if (controler
					.equalsIgnoreCase(WPCustomField.GET_POST_CUSTOM)) {
				onCustomFieldsListener.OnCustomPostMetaReceived(WPCustomField
						.parseMetaResponse(response
								.getJSONObject("custom_fields")));
			} else if (controler.equalsIgnoreCase(WPCustomField.GET_POST_KEYS)) {
				onCustomFieldsListener.OnCustomPostMetaKeysReceived(WPCustomField
						.parseCustomKeys(response
								.getJSONArray("custom_fields_keys")));
			} else if (controler
					.equalsIgnoreCase(WPCustomField.GET_POST_VALUES)) {
				onCustomFieldsListener.OnCustomPostMetaValuesReceived(WPCustomField
						.parseCustomValues(response
								.getJSONArray("custom_fields_values")));
			} else if (controler
					.equalsIgnoreCase(WPCustomField.UPDATE_POST_META)) {
				onCustomFieldsListener.OnMetaDataUpdated();
				
				//Custom API request
			} else{
				onApiRequestListener.OnApiRequestResponse(response);
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

	@Override
	public void onFailure(Throwable arg0, JSONObject arg1) {

		onConnectionFailureListener.OnConnectionFailed();
	}

}
