package com.jogeeks.wordpress;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.params.ClientPNames;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.jogeeks.internet.Communication;
import com.jogeeks.mobipress.R;
import com.jogeeks.wordpress.listeners.OnCategoriesListener;
import com.jogeeks.wordpress.listeners.OnCommentSubmittedListener;
import com.jogeeks.wordpress.listeners.OnCommentsReceivedListener;
import com.jogeeks.wordpress.listeners.OnConnectionFailureListener;
import com.jogeeks.wordpress.listeners.OnCreatePostListener;
import com.jogeeks.wordpress.listeners.OnCustomFieldsListener;
import com.jogeeks.wordpress.listeners.OnLoginListener;
import com.jogeeks.wordpress.listeners.OnPostReceivedListener;
import com.jogeeks.wordpress.listeners.OnPostsReceivedListener;
import com.jogeeks.wordpress.listeners.OnRegisterListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class Wordpress implements OnLoginListener, OnRegisterListener {

	public static final int LOGIN_FAILED = -1;
	public static final int LOGIN_SUCCESS = 0;
	public static final int LOGIN_USER_NAME_ERROR = 1;
	public static final int LOGIN_PASSWORD_ERROR = 2;
	public static final int LOGIN_CHECK_PASSWORD_AND_OR_USERNAME = 3;

	public static final int REGISTRATION_FAILED = -1;
	public static final int REGISTRATION_SUCCESS = 0;
	public static final int REGISTRATION_INVALID_USER_NAME = 1;
	public static final int REGISTRATION_INVALID_DISPLAY_NAME = 2;
	public static final int REGISTRATION_USER_NAME_IN_USE = 3;
	public static final int REGISTRATION_INVALID_EMAIL = 4;
	public static final int REGISTRATION_EMAIL_IN_USE = 5;
	public static final int REGISTRATION_INVALID_PASSWORD = 6;

	// http://wordpress.org/plugins/json-api/other_notes/#3.2.-Content-modifying-arguments
	static final String BASE_URL = "http://arabappz.net/secrets/api/";
	static final String NONCE_URL = "get_nonce";
	static final String DATE_INDEX_URL = "get_date_index";

	private Context context;

	private String username;
	private String password;

	private OnLoginListener loginListener;
	private OnRegisterListener registerListener;
	private OnConnectionFailureListener onConnectionFailureListener;
	private WordpressResponseHandler<WPPost> postHandler;
	private WordpressResponseHandler<WPComment> commentHandler;

	private AsyncHttpClient httpClient = new AsyncHttpClient();

	/**
	 * <h1>WordPress constructor. After initializing this constructor, you can call
	 * several core methods: login(Bundle userData), register(Bundle userData)</h1>
	 * 
	 * @param context
	 *            Pass the application's context to get this initialized.
	 */
	public Wordpress(Context context, OnConnectionFailureListener listener) {
		this.context = context;
		httpClient.getHttpClient().getParams()
				.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		postHandler = new WordpressResponseHandler<WPPost>();
		commentHandler = new WordpressResponseHandler<WPComment>();
		onConnectionFailureListener = listener;

		postHandler.setOnConnectionFailureListener(onConnectionFailureListener);
		commentHandler
				.setOnConnectionFailureListener(onConnectionFailureListener);
	}

	/**
	 * <h1>Set OnLoginListener to your instance before calling this function</h1>
	 * 
	 * @param userData
	 *            Pass a bundle with two String values that represent user
	 *            credentials "username", "password"
	 */
	public void login(Bundle userData) {
		username = userData.getString("username");
		password = userData.getString("password");
		new WPLogin(username, password);
	}

	/**
	 * <h1>Set OnRegisterListener to your instance before calling this function</h1>
	 * 
	 * @param userData
	 *            Pass a bundle with four String values that represent user info
	 *            "username", "password", "email", "nickname"
	 */
	public void register(Bundle userData) {
		new WPRegister(userData);
	}

	public void finish(Context context) {
		httpClient.cancelRequests(context, true);
	}

	/**
	 * <h1>Adds a custom field (also called meta-data) to a specified post
	 * which could be of any post type</h1>
	 * @param pid
	 *            integer Post ID
	 * @param meta
	 * 			  WPCustomeField object
	 * @param unique 
	 * 			  When set to true, the custom field will 
	 * 			  not be added if the given key already exists among 
	 * 			  custom fields of the specified post.
	 */
	public void addPostMeta(int pid, WPCustomField meta, boolean unique, OnCustomFieldsListener listener){
		postHandler.setOnCustomFieldsListener(listener);
		
		RequestParams reqParams = new RequestParams();
		reqParams.add("post_id", Integer.toString(pid));
		reqParams.add("meta_key", meta.getName());
		reqParams.add("meta_value", meta.getValue());
		reqParams.add("unique", Boolean.toString(unique));
		
		httpClient.get(BASE_URL + WPCustomField.ADD_POST_META, reqParams, postHandler);
	}
	
	/**
	 * <h1>Adds a custom field (also called meta-data) to a specified post
	 * which could be of any post type</h1>
	 * @param pid
	 *            integer Post ID
	 * @param meta
	 * 			  WPCustomeField object with key and the new value
	 * @param previousValue 
	 * 			  The old value of the custom field you wish to change. 
	 * 			  This is to differentiate between several fields with the same key. 
	 * 			  If omitted, and there are multiple rows for this post and meta key, 
	 * 			  all meta values will be updated.
	 */
	public void updatePostMeta(int pid, WPCustomField meta, String previousValue, OnCustomFieldsListener listener){
		postHandler.setOnCustomFieldsListener(listener);
		
		RequestParams reqParams = new RequestParams();
		reqParams.add("post_id", Integer.toString(pid));
		reqParams.add("meta_key", meta.getName());
		reqParams.add("meta_value", meta.getValue());
		reqParams.add("prev_value", previousValue);
		
		httpClient.get(BASE_URL + WPCustomField.UPDATE_POST_META, reqParams, postHandler);
	}
	
	/**
	 * <h1>Adds a custom field (also called meta-data) to a specified post 
	 * which could be of any post type</h1>
	 * @param pid
	 *            integer Post ID
	 * @param meta
	 * 			  WPCustomeField object with key and the new value
	 */
	public void updatePostMeta(int pid, WPCustomField meta, OnCustomFieldsListener listener){
		postHandler.setOnCustomFieldsListener(listener);
		
		RequestParams reqParams = new RequestParams();
		reqParams.add("post_id", Integer.toString(pid));
		reqParams.add("meta_key", meta.getName());
		reqParams.add("meta_value", meta.getValue());
		
		httpClient.get(BASE_URL + WPCustomField.UPDATE_POST_META, reqParams, postHandler);
	}
	
	/**
	 * <h1>Delete a custom field (also called meta-data) to a specified post 
	 * which could be of any post type</h1>
	 * @param pid
	 *            integer Post ID
	 * @param meta
	 * 			  WPCustomeField object with key and a specific value.
	 * 			  value is provided to differentiate between several 
	 * 			  fields with the same key. If left blank, all fields 
	 * 			  with the given key will be deleted.
	 */
	public void deletePostMeta(int pid, WPCustomField meta, OnCustomFieldsListener listener){
		postHandler.setOnCustomFieldsListener(listener);
		
		RequestParams reqParams = new RequestParams();
		reqParams.add("post_id", Integer.toString(pid));
		reqParams.add("meta_key", meta.getName());
		reqParams.add("meta_value", meta.getValue());
		
		httpClient.get(BASE_URL + WPCustomField.DELETE_POST_META, reqParams, postHandler);
	}
	
	/**
	 * <h1>Delete a custom field (also called meta-data) to a specified post 
	 * which could be of any post type</h1>
	 * @param pid
	 *            integer Post ID
	 * @param String
	 * 			  The key of the field you will delete.
	 */
	public void deletePostMeta(int pid, String key, OnCustomFieldsListener listener){
		postHandler.setOnCustomFieldsListener(listener);
		
		RequestParams reqParams = new RequestParams();
		reqParams.add("post_id", Integer.toString(pid));
		reqParams.add("meta_key", key);
		
		httpClient.get(BASE_URL + WPCustomField.DELETE_POST_META, reqParams, postHandler);
	}
	
	/**
	 * <h1>Returns an ArrayList with all custom fields of a particular post or page.</h1>
	 * @param pid
	 *            integer Post ID whose custom fields will be retrieved.
	 * 
	 * @return
	 * 		 ArrayList<WPCustomData>
	 * 
	 * @see getPostCustomKeys, getPostCustomValues
	 */
	public void getPostCustom(int pid, OnCustomFieldsListener listener){
		postHandler.setOnCustomFieldsListener(listener);
		httpClient.get(BASE_URL + WPCustomField.GET_POST_CUSTOM, postHandler);
	}
	
	/**
	 * @param pid
	 *            integer Post ID whose custom fields will be retrieved.
	 * 
	 * @return
	 * 		 Returns an ArrayList<String> containing the keys of all custom fields of a particular post or page.
	 * 
	 * @see getPostCustom, getPostCustomValues
	 */
	public void getPostCustomKeys(int pid, OnCustomFieldsListener listener){
		postHandler.setOnCustomFieldsListener(listener);
		httpClient.get(BASE_URL + WPCustomField.GET_POST_KEYS, postHandler);
	}
	
	/**
	 * <h1>This function is useful if you wish to access a custom field that is not unique, i.e. has more than 1 value associated with it.
	 * </h1>
	 * 
	 * @param key
	 * 			  The key whose values you want returned.
	 * @param pid
	 *            integer Post ID whose custom fields will be retrieved.
	 * 
	 * @return
	 * 		 </h1>Returns an ArrayList<String> containing the values of all custom fields of a particular post or page.</h2>
	 * 
	 * @see getPostCustomKeys, getPostCustom
	 */
	public void getPostCustomValues(String key, int pid, OnCustomFieldsListener listener){
		postHandler.setOnCustomFieldsListener(listener);
		httpClient.get(BASE_URL + WPCustomField.GET_POST_KEYS, postHandler);
	}
	
	
	// TODO: most probably its best to use the Params, rather than supplying an
	// overloaded function for each case
	public void getPosts(OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);
		httpClient.get(BASE_URL + WPPost.POSTS_URL, postHandler);
	}

	public void getPosts(WPQuery query, OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);
		
		RequestParams reqParams = query.getQuery();
		httpClient.get(BASE_URL + WPPost.POSTS_URL, reqParams, postHandler);
	}
	
	public void getPosts(WPQuery query, int count, int page, OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);
		RequestParams reqParams = query.getQuery();
		
		httpClient.get(BASE_URL + WPPost.POSTS_URL + "?" + "count=" + count
				+ "&" + "page=" + page, reqParams, postHandler);
	}
	
	public void getPosts(int count, int page, OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);
		httpClient.get(BASE_URL + WPPost.POSTS_URL + "?" + "count=" + count
				+ "&" + "page=" + page, postHandler);
	}

	public void getRecentPosts(int count, int page,
			OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);
		httpClient.get(BASE_URL + WPPost.RECENT_POSTS_URL + "?" + "count="
				+ count + "&" + "page=" + page, postHandler);
	}

	public void getCustomPosts(int count, int page, String postType,
			OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);
		httpClient.get(BASE_URL + WPPost.POSTS_URL, postHandler);
	}

	public void getPostsByCategory(int cId, OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("id", Integer.toString(cId));
		httpClient.get(BASE_URL + WPPost.CATEGORY_POSTS_URL, reqParams,
				postHandler);
	}

	public void getPostsByCategory(int cId, int count, int page,
			OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("id", Integer.toString(cId));
		httpClient.get(BASE_URL + WPPost.CATEGORY_POSTS_URL + "?" + "count="
				+ count + "&" + "page=" + page, reqParams, postHandler);
	}

	public void getPostsByTag(int tId, OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("id", Integer.toString(tId));
		httpClient.get(BASE_URL + WPPost.TAG_POSTS_URL, reqParams, postHandler);
	}

	public void getPostsByTag(int tId, int count, int page,
			OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("id", Integer.toString(tId));
		httpClient.get(BASE_URL + WPPost.TAG_POSTS_URL + "?" + "count=" + count
				+ "&" + "page=" + page, reqParams, postHandler);
	}

	public void getPostsByAuthor(int aId, OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("id", Integer.toString(aId));
		httpClient.get(BASE_URL + WPPost.AUTHOR_POSTS_URL, reqParams,
				postHandler);
	}

	public void getPostsByAuthor(int aId, int count, int page,
			OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("id", Integer.toString(aId));
		httpClient.get(BASE_URL + WPPost.AUTHOR_POSTS_URL + "?" + "count="
				+ count + "&" + "page=" + page, reqParams, postHandler);
	}

	public void getPostsByDate(String date, OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("date", date);
		httpClient
				.get(BASE_URL + WPPost.DATE_POSTS_URL, reqParams, postHandler);
	}

	public void getPostsByDate(String date, int count, int page,
			OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("date", date);
		httpClient.get(BASE_URL + WPPost.DATE_POSTS_URL + "?" + "count="
				+ count + "&" + "page=" + page, reqParams, postHandler);
	}

	public void getPostsBySearch(String query, OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("search", query);
		httpClient.get(BASE_URL + WPPost.SEARCH_POSTS_URL, reqParams,
				postHandler);
	}

	public void getPostsBySearch(String query, int count, int page,
			OnPostsReceivedListener listener) {
		postHandler.setOnPostsReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("search", query);
		httpClient.get(BASE_URL + WPPost.SEARCH_POSTS_URL + "?" + "count="
				+ count + "&" + "page=" + page, reqParams, postHandler);
	}

	public void getPost(int pId, OnPostReceivedListener listener) {
		postHandler.setOnPostReceivedListener(listener);

		RequestParams reqParams = new RequestParams();
		reqParams.add("id", Integer.toString(pId));
		httpClient.get(BASE_URL + WPPost.POST_URL, reqParams, postHandler);
	}

	public void getPage(int pId,
			WordpressResponseHandler<WPPost> responseHandler) {
		RequestParams reqParams = new RequestParams();
		reqParams.add("id", Integer.toString(pId));
		httpClient.get(BASE_URL + WPPost.PAGE_URL, reqParams, responseHandler);
	}

	public void createPost(final WPPost post, final String attachment,
			OnCreatePostListener listener) {
		postHandler.setOnCreatePostListener(listener);

		httpClient.get(BASE_URL + NONCE_URL
				+ "/?controller=posts&method=create_post",
				new WordpressResponseHandler<WPPost>() {
					@Override
					public void onNonceRecieved(String nonce, String method) {
						this.setOnConnectionFailureListener(onConnectionFailureListener);

						String title, content, status;
						String categories = "";
						String tags = "";

						title = post.getTitle();
						content = post.getContent();
						status = post.getStatus();

						for (int i = 0; i < post.getCategories().size(); i++) {
							if (i == post.getCategories().size() - 1) {
								categories = categories
										+ post.getCategories().get(i).getSlug();
							} else {
								categories = categories
										+ post.getCategories().get(i).getSlug()
										+ ", ";
							}
						}

						for (int i = 0; i < post.getTags().size(); i++) {
							if (i == post.getTags().size() - 1) {
								tags = tags + post.getTags().get(i).getSlug();
							} else {
								tags = tags + post.getTags().get(i).getSlug()
										+ ", ";
							}
						}

						Log.d("cookie", new WPSession(context).getCookie());
						RequestParams reqParams = new RequestParams();
						reqParams.add("nonce", nonce);
						reqParams.add("cookie",
								new WPSession(context).getCookie());
						reqParams.add("title", title);
						reqParams.add("content", content);
						reqParams.add("status", status);
						reqParams.add("categories", categories);
						reqParams.add("tags", tags);

						if (!attachment.equals(null) || !attachment.equals("")) {
							File image = new File(attachment);
							Log.d("ATT2", image.getAbsolutePath());
							try {
								reqParams.put("attachment", image);
							} catch (FileNotFoundException e) {
							}
						}
						httpClient.post(BASE_URL + WPPost.CREATE_POST_URL,
								reqParams, postHandler);

						super.onNonceRecieved(nonce, method);
					}
				});
	}

	public void updatePost(final WPPost post, int userId, final String status,
			final WordpressResponseHandler<WPPost> responseHandler) {
		// TODO: add the cookie
		httpClient.get(BASE_URL + NONCE_URL
				+ "?controller=posts&method=update_post",
				new WordpressResponseHandler<WPPost>() {
					@Override
					public void onNonceRecieved(String nonce, String method) {
						RequestParams reqParams = new RequestParams();
						// reqParams.add("title", Integer.toString(pId));
						reqParams.add("title", post.getTitle());
						reqParams.add("content", post.getContent());
						reqParams.add("status", status);
						reqParams.add("nonce", nonce);

						httpClient.get(BASE_URL + WPPost.CREATE_POST_URL,
								reqParams, responseHandler);

						super.onNonceRecieved(nonce, method);
					}
				});
	}

	public void getCategoryIndex(OnCategoriesListener listener) {
		WordpressResponseHandler<WPCategory> responseHandler = new WordpressResponseHandler<WPCategory>();
		responseHandler.setOnCategoriesListener(listener);
		responseHandler
				.setOnConnectionFailureListener(onConnectionFailureListener);
		httpClient.get(BASE_URL + WPCategory.CATEGORY_INDEX, responseHandler);
	}

	public void getComments(int pId, OnCommentsReceivedListener listener) {
		commentHandler.setOnCommentsReceivedListener(listener);
		RequestParams reqParams = new RequestParams();
		reqParams.add("id", Integer.toString(pId));
		httpClient.get(BASE_URL + WPPost.POST_URL, reqParams, commentHandler);
	}

	public void submitComment(WPComment comment,
			OnCommentSubmittedListener listener) {
		commentHandler.setOnCommentsSubmittedListener(listener);
		RequestParams reqParams = new RequestParams();
		reqParams.add("name", comment.getName());
		reqParams.add("content", comment.getContent());
		reqParams.add("email", comment.getUrl());
		reqParams.add("post_id", Integer.toString(comment.getPostId()));
		httpClient.get(BASE_URL + WPComment.SUBMIT_COMMENT_URL, reqParams,
				commentHandler);
	}

	private class WPLogin {

		private String nonceURL;
		private String cookieURL;

		private String userName;
		private String password;

		private Communication json;

		public WPLogin(String un, String pass) {
			userName = un;
			password = pass;

			cookieURL = context.getString(R.string.url).concat(
					context.getString(R.string.cookie_request));
			nonceURL = context.getString(R.string.url).concat(
					context.getString(R.string.loginNonce));

			json = new Communication();

			ProcessLogin login = new ProcessLogin();
			login.execute(userName, password);
		}

		private class ProcessLogin extends
				AsyncTask<String, WPSession, WPSession> {

			private OnLoginListener listener;

			public ProcessLogin() {
				listener = loginListener;
			}

			@Override
			protected void onPostExecute(WPSession result) {
				if (result.getStatus() == Wordpress.LOGIN_SUCCESS) {
					listener.OnLoginSuccess(result);
				} else {
					listener.OnLoginFailure(result);
				}
			}

			@Override
			protected void onPreExecute() {
				listener.OnLoginStart();
			}

			@Override
			protected WPSession doInBackground(String... params) {

				JSONObject nonceRequest = json.getJSONResponse(nonceURL);

				WPSession userSession = new WPSession(context);

				// Check nonce status
				if (isNonceOk(nonceRequest)) {
					try {
						String nonce = nonceRequest.getString("nonce");
						Log.d("LoginNonce", nonce);

						userSession.setNonce(nonce);
						// replace URL variables
						cookieURL = cookieURL.replace("NONCE", nonce);
						cookieURL = cookieURL.replace("USER", params[0]);
						cookieURL = cookieURL.replace("PASSWORD", params[1]);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					JSONObject cookieRequest = json.getJSONResponse(cookieURL);

					// Check cookie status
					if (isCookieOk(cookieRequest)) {
						try {
							userSession.setSession(cookieRequest);
						} catch (JSONException e) {
						}

						userSession.setStatus(Wordpress.LOGIN_SUCCESS);
						Log.d("LoginCookie", userSession.getCookie());

						return userSession;
					} else {
						int code = Wordpress.LOGIN_FAILED;
						try {
							code = cookieRequest.getInt("code");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						switch (code) {
						case 1:
							userSession
									.setStatus(Wordpress.LOGIN_USER_NAME_ERROR);
							return userSession;
						case 2:
							userSession
									.setStatus(Wordpress.LOGIN_PASSWORD_ERROR);
							return userSession;
						case 3:
							userSession
									.setStatus(Wordpress.LOGIN_CHECK_PASSWORD_AND_OR_USERNAME);
							return userSession;
						default:
							userSession.setStatus(Wordpress.LOGIN_FAILED);
							return userSession;
						}
					}
				}

				userSession.setStatus(Wordpress.LOGIN_FAILED);
				return userSession;
			}

			private boolean isNonceOk(JSONObject nr) {
				String status = null;

				try {
					status = nr.get("status").toString();
				} catch (Exception s) {

				}

				if (status.equals("ok")) {
					return true;
				} else {
					return false;
				}
			}

			private boolean isCookieOk(JSONObject nr) {
				String status = null;

				try {
					status = nr.get("status").toString();
				} catch (Exception s) {

				}

				if (status.equals("ok")) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	private class WPRegister {

		private String nonceURL;
		private String registerURL;

		private String userName;
		private String displayName;
		private String email;
		private String password;

		private Communication json;

		public WPRegister(Bundle regData) {
			userName = regData.getString("username");
			password = regData.getString("password");
			displayName = regData.getString("displayname");
			email = regData.getString("email");

			nonceURL = context.getString(R.string.url).concat(
					context.getString(R.string.registerNonce));
			registerURL = context.getString(R.string.url).concat(
					context.getString(R.string.register_request));

			json = new Communication();

			new ProcessRegister().execute(userName, password, displayName,
					email);

		}

		private class ProcessRegister extends
				AsyncTask<String, Integer, Integer> {
			private OnRegisterListener listener;

			public ProcessRegister() {
				listener = registerListener;
			}

			@Override
			protected void onPostExecute(Integer result) {
				if (result == Wordpress.REGISTRATION_SUCCESS) {
					listener.OnRegisterSuccess(result);
				} else {
					listener.OnRegisterFailure(result);
				}
			}

			@Override
			protected void onPreExecute() {
				listener.onRegisterStart();
			}

			@Override
			protected Integer doInBackground(String... params) {
				int code = Wordpress.REGISTRATION_FAILED;

				JSONObject nonceRequest = json.getJSONResponse(nonceURL);

				// Check nonce status
				if (isNonceOk(nonceRequest)) {
					try {
						String nonce = nonceRequest.getString("nonce");
						new WPSession(context).setNonce(nonce);
						// replace URL variables
						registerURL = registerURL.replace("NONCE", nonce);
						registerURL = registerURL.replace("USER", params[0]);
						registerURL = registerURL
								.replace("PASSWORD", params[1]);
						registerURL = registerURL.replace("DISPLAYNAME",
								params[2]);
						registerURL = registerURL.replace("UMAIL", params[3]);

					} catch (JSONException e) {
						e.printStackTrace();
						return Wordpress.REGISTRATION_FAILED;
					}

					Log.d("URL", registerURL);
					JSONObject registerRequest = json
							.getJSONResponse(registerURL);

					return registrationStatus(registerRequest);
				}

				return code;
			}

			private boolean isNonceOk(JSONObject nr) {
				String status = null;

				try {
					status = nr.get("status").toString();
				} catch (Exception s) {

				}

				if (status.equals("ok")) {
					return true;
				} else {
					return false;
				}
			}

			private int registrationStatus(JSONObject nr) {
				int code = -1;

				try {
					code = Integer.parseInt(nr.get("code").toString());
				} catch (Exception s) {

				}
				return code;
			}
		}

	}

	public void setOnLoginListener(OnLoginListener l) {
		loginListener = l;
	}

	public void setOnRegisterListener(OnRegisterListener l) {
		registerListener = l;
	}

	@Override
	public void OnLoginSuccess(WPSession session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnLoginFailure(WPSession session) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnRegisterSuccess(int error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnRegisterFailure(int error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRegisterStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnLoginStart() {
		// TODO Auto-generated method stub

	}
	
	protected static String parseNonce(JSONObject response) throws JSONException {
		String nonce;
		nonce = response.getString("nonce");
		Log.d("CreatePostNonceResponse", nonce);

		return nonce;
	}
	
	protected static HashMap<String, String> parseResponseMeta(JSONObject response)
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
}
