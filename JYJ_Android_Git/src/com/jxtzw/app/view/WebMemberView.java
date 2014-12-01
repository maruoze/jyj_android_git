package com.jxtzw.app.view;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.common.CyptoUtils;
import com.jxtzw.app.common.UIHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebMemberView extends WebQuoView {
	/**
	 * SharedPreferences
	 */
	private SharedPreferences mSharedPreferences;
	/**
	 * 登录验证数据
	 */
	private String mUsernameString;
	private String mPasswordString;
	public WebMemberView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化
	 */
	public void init(View view, String url,int index) {
		this.mListView=view;
		this.mCatID=url;
		this.mIndex=index;
		this.mAppContext=(AppContext) mActivity.getApplication();
		initMemberVar();
	}
	
	/**
	 * 刷新WebView
	 */
	@SuppressLint("SetJavaScriptEnabled")
	public void update() {
		//判断是否有可用网络
		if (!mAppContext.isNetworkConnected()) {
			//如果无可用网络则直接返回
			UIHelper.ToastMessage(mContext, R.string.network_not_connected);
			mLoadingView.setVisibility(View.GONE);
			mLoadedView.setVisibility(View.VISIBLE);
			return;
		}
				
		//页面加载
		mWebView=(WebView) mListView.findViewById(R.id.webview_quotation);
		mWebView.getSettings().setJavaScriptEnabled(true);
		//mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		//WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度
		mWebView.setWebChromeClient(new WebChromeClient());
		//WebViewClient处理基本页面效果
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				mLoadingView.setVisibility(View.GONE);
				mLoadedView.setVisibility(View.GONE);
			}
		});
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
				String postData="username="+mUsernameString+"&&password="+mPasswordString;
				mWebView.postUrl(mCatID, postData.getBytes());
			}
		}
		
		
		
		//cookie同步
		/**String apiLoginCookie=mResources.getString(R.string.api_login_cookie);
		CookieSyncManager.createInstance(mContext);
		CookieManager cookieManager=CookieManager.getInstance();
		Cookie sessionCookie=AppConfig.COOKIE;
		if (sessionCookie!=null) {
			//String cookieString = sessionCookie.getName() + "=" + URLDecoder.decode(sessionCookie.getValue()) + "; domain=" + sessionCookie.getDomain()+";path="+sessionCookie.getPath();;
			String cookieString = sessionCookie.getName() + "=" + sessionCookie.getValue() + "; domain=" + sessionCookie.getDomain()+";path="+sessionCookie.getPath();
			//String cookieString=sessionCookie.toString();
			cookieManager.setAcceptCookie(true);
			cookieManager.removeSessionCookie();//移除
			CookieSyncManager.getInstance().sync();
			cookieManager.setCookie(sessionCookie.getDomain(), cookieString);
			CookieSyncManager.getInstance().sync();
			String cookie=cookieManager.getCookie(sessionCookie.getDomain());
			if(cookie!=null){
				mWebView.loadUrl(mCatID);
			}
		}**/
	}
}
