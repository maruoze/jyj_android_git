package com.jxtzw.app.view;

import java.util.Hashtable;

import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.common.UIHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebQuoView extends BaseView {
	/**
	 * 布局
	 */
	protected View mListView;
	/**
	 * 分类ID, 数据
	 */
	protected Hashtable<String, String> mHashtable;
	protected String mCatID;
	protected int mIndex;							//切换页索引
	/**
	 * 控件UI
	 */
	protected WebView mWebView;
	protected AppContext mAppContext;
	protected View mLoadingView;
	protected ProgressBar mWebviewLoadingBar;
	protected View mLoadedView;
	protected TextView mLoadedTextView;
	protected Button mWVRefreshButton;
	
	public WebQuoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化
	 */
	public void init(View view, Hashtable<String, String> hashtable,int index){
		this.mListView=view;
		this.mHashtable=hashtable;
		this.mCatID=mHashtable.get("mCatID");
		this.mIndex=index;
		this.mAppContext=(AppContext) mActivity.getApplication();
		initMemberVar();
	}
	
	/**
	 * 初始化相关控件
	 */
	protected void initMemberVar() {
		mLoadingView=mListView.findViewById(R.id.rl_loading_container);
		mLoadedView=mListView.findViewById(R.id.rl_loaded_failure);
		mWebviewLoadingBar=(ProgressBar) mListView.findViewById(R.id.webview_loading_prbar);
		mLoadedTextView=(TextView) mListView.findViewById(R.id.textview_loaded_failure);
		mWVRefreshButton=(Button) mListView.findViewById(R.id.webview_refresh);
		initListner();
	}
	
	
	/**
	 * 初始化响应事件
	 */
	protected void initListner() {
		mLoadedView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLoadingView.setVisibility(View.VISIBLE);
				mLoadedView.setVisibility(View.GONE);
				update();
			}
		});
		
		mWVRefreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mLoadingView.setVisibility(View.VISIBLE);
				mLoadedView.setVisibility(View.GONE);
				update();
			}
		});
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
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				mLoadingView.setVisibility(View.GONE);
				mLoadedView.setVisibility(View.GONE);
			}
		});
		mWebView.loadUrl(mCatID);
	}
}
