package com.jxtzw.app.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.R.layout;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.CyptoUtils;
import com.jxtzw.app.common.EncryptionAES;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.view.WebMemberView;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MemberPrivilegeActivity extends BaseActivity {
	protected AppContext mAppContext;
	protected Activity mActivity;
	/**
	 * 数据
	 */
	private String[] mSubTitles;
	private int mMemberIndex;
	private String mMainTitle;
	private String mCatName;
	private String[] mMemberAPIs;
	private String mMemberAPI;
	/**
	 * UI标题栏
	 */
	protected Button mHeadLeft;
	protected Button mHeadRight;
	protected TextView mCatNameTextView;
	protected OnClickListener mHLClickListener;
	protected OnClickListener mHRClickListener;
	/**
	 * WebView
	 */
	protected WebMemberView mWebMemberView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_privilege);
		init();
	}

	/**
	 * 调用父类构造函数供子类使用
	 * 
	 * @param savedInstanceState
	 */
	protected void onCreateEx(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化
	 */
	protected void init() {
		initCommonMemberVar();
		initMemberVar();
		initSelfMemVar();
		initTitle();
		//由于同步httpclient和webview的cookie出现问题，所以initCookie被注释
		//initCookie();
		initWebView();
	}

	/**
	 * 初始化通用成员变量
	 */
	protected void initCommonMemberVar() {
		// 上下文
		mContext = this;
		mActivity = (Activity) mContext;
		mAppContext = (AppContext) mActivity.getApplication();
		// 资源
		mResources = getResources();
		// 布局
		mLayoutInflater = LayoutInflater.from(this);
	}

	/**
	 * 初始化成员变量
	 */
	protected void initMemberVar() {
		// 获取Intent
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		mMemberIndex = bundle.getInt("MemberIndex");
		mMainTitle = bundle.getString("MainTitle");
	}

	/**
	 * 初始化本类成员变量
	 */
	protected void initSelfMemVar() {
		mSubTitles = mResources.getStringArray(R.array.member_title);
		mCatName = mSubTitles[mMemberIndex];
		mMemberAPIs = mResources.getStringArray(R.array.member_api);
		mMemberAPI = mMemberAPIs[mMemberIndex];
	}

	/**
	 * 标题栏初始化
	 */
	protected void initTitle() {
		mHeadLeft = (Button) findViewById(R.id.head_left);
		mHeadRight = (Button) findViewById(R.id.head_right);
		mCatNameTextView = (TextView) findViewById(R.id.head_middle);
		String titleString = mMainTitle + " • " + mCatName;
		mCatNameTextView.setText(titleString);
		// 初始化监听
		initTilteListner();
		mHeadLeft.setOnClickListener(mHLClickListener);
		mHeadRight.setOnClickListener(mHRClickListener);
	}

	/**
	 * 标题栏事件监听初始化
	 */
	protected void initTilteListner() {
		mHLClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		};
		mHRClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UIHelper.showMainPopMenu(mContext, R.layout.pop_users,
						mHeadRight);
			}
		};
	}

	/**
	 * WebView初始化
	 */
	protected void initWebView() {
		mWebMemberView = new WebMemberView(mContext);
		View view = ((ViewGroup) findViewById(android.R.id.content))
				.getChildAt(0);
		mWebMemberView.init(view, mMemberAPI, mMemberIndex);
		mWebMemberView.update();
	}

	/**
	 * 登录并设置session
	 */
	/**private void initCookie() {
		mSharedPreferences = AppConfig.getSharedPreferences(mContext);
		mUsernameString = mSharedPreferences.getString("username", "");
		String encryPassword = mSharedPreferences.getString("password", "");
		if (mUsernameString.length() == 0 || encryPassword.length() == 0) {
			UIHelper.ToastMessage(mContext, "登录信息有误，请重新登录！");
		} else {
			mPasswordString = CyptoUtils.decode("maru09743233aoze",
					encryPassword);
			if (mPasswordString == null) {
				UIHelper.ToastMessage(mContext, "登录信息有误，请重新登录！[密码]");
			} else {
				// 判断是否有可用网络
				if (!mAppContext.isNetworkConnected()) {
					// 如果无可用网络则直接返回
					UIHelper.ToastMessage(mContext,
							R.string.network_not_connected);
					return;
				}
				loginProcess();
				//loginProcessHttp();
			}
		}
	}*/

	/**
	 * 登录过程
	 */
	/**private void loginProcess() {
		String apiLoginCookie = mResources.getString(R.string.api_login_cookie);
		AjaxParams ajaxParams = new AjaxParams();
		ajaxParams.put("username", mUsernameString);
		ajaxParams.put("password", mPasswordString);
		final FinalHttp finalHttp = new FinalHttp();
		finalHttp.post(apiLoginCookie, ajaxParams, new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				UIHelper.ToastMessage(mContext, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				DefaultHttpClient client = (DefaultHttpClient) finalHttp
						.getHttpClient();
				// HttpContext httpContext=finalHttp.getHttpContext();
				List<Cookie> cookies = client.getCookieStore().getCookies();
				for (int i = 0; i < cookies.size(); i++) {
					Cookie cookie = cookies.get(i);
					if (cookie.getName().equalsIgnoreCase("ci_session")) {
						AppConfig.COOKIE = cookie;
					}
				}
				mWebMemberView.update();
			}
		});
	}**/

	/**
	 * 登录过程httpclient
	 */
	/**@SuppressLint("HandlerLeak")
	private void loginProcessHttp() {
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if (msg.what==0) {
					mWebMemberView.update();
				}
			}

		};

		Thread thread = new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String apiLoginCookie = mResources.getString(R.string.api_login_cookie);
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(apiLoginCookie);
				ArrayList<NameValuePair> nvps = new ArrayList<NameValuePair>();  
		        nvps.add(new BasicNameValuePair("username", mUsernameString));  
		        nvps.add(new BasicNameValuePair("password", mPasswordString));  
		        try {
					httpPost.setEntity(new UrlEncodedFormEntity(nvps));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
				HttpContext context = new BasicHttpContext();
				CookieStore cookieStore = new BasicCookieStore();
				context.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
				HttpResponse response = null;
				try {
					response = client.execute(httpPost, context);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					// 根据你的逻辑，判断返回的值是不是表示已经登录成功
					List<Cookie> cookies = cookieStore.getCookies();
					if (!cookies.isEmpty()) {
						for (int i = cookies.size(); i > 0; i--) {
							Cookie cookie = cookies.get(i - 1);
							if (cookie.getName().equalsIgnoreCase("ci_session")) {
								// 使用一个常量来保存这个cookie，用于做session共享之用
								AppConfig.COOKIE = cookie;
								handler.sendEmptyMessage(0);
							}
						}
					}
				}
			}

		};
		thread.start();
	}**/
}
